/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luiz.almeida
 */
public class Conexao {

    private static Connection conexao;
    private static Statement statement;
    private static ResultSet resultSet;
    private static Conexao instancia;
    private static PropriedadesBD propriedadesBD;

    public static Conexao getInstacia() {

        if (instancia == null) {
            instancia = new Conexao();
            return instancia;
        } else {
            return instancia;
        }
    }

    public static Connection getConnection() throws ErroSistemaException {
        try {
            if (conexao == null) {
                propriedadesBD = new PropriedadesBD();
                Class.forName(propriedadesBD.getDriver());
                conexao = DriverManager.getConnection(propriedadesBD.getUrlBanco(), propriedadesBD.getUsuario(), propriedadesBD.getSenha());
                conexao.setAutoCommit(false);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return conexao;
    }

    public static void fechaConexao() throws ErroSistemaException {
        try {
            if (conexao != null) {
                conexao.close();
                conexao = null;
            }
        } catch (Exception e) {
            throw new ErroSistemaException("Erro ao fechar o Banco de Dados. (ORACLE)", e);
        }
    }

    public static Boolean executaSql(String sql) throws ErroSistemaException {
        try {
            statement = getInstacia().getConnection().createStatement();
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            throw new ErroSistemaException("Não foi possível efetuar atualização no banco de dados (ORACLE)" + "\n" + sql, e);
        }
    }

    public static ResultSet executaQuery(String sql) throws ErroSistemaException {
        resultSet = null;
        try {
            statement = getInstacia().getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            throw new ErroSistemaException("Não foi possível efetuar consulta no banco de dados (ORACLE)" + "\n" + sql, e);
        }
        return resultSet;
    }
}
