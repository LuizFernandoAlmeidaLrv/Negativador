/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author luiz.almeida
 */
/**
 *
 * @author Sidnei Favoreto Vieira
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

    public static Connection getConnection() throws FileNotFoundException, IOException {
        try {
            if (conexao == null) {
                propriedadesBD = new PropriedadesBD();
                Class.forName(propriedadesBD.getDriver());
                conexao = DriverManager.getConnection(propriedadesBD.getUrlBanco(), propriedadesBD.getUsuario(), propriedadesBD.getSenha());
            }
        } catch (Exception e) {
                 e.printStackTrace();
            //logger.error("Erro ao conectar com o Banco de Dados (ORACLE).", e);
        }
        return conexao;
    }

    public static void fechaConexao() {
        try {
            if (conexao != null) {
                conexao.close();
                conexao = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
           // logger.error("Erro ao fechar o Banco de Dados. (ORACLE)", e);
        }
    }

    public static Boolean executaSql(String sql) {
        try {
            statement = getInstacia().getConnection().createStatement();
            statement.execute(sql);
            return true;
        } catch (Exception e) {
                 e.printStackTrace();
           // logger.error("Não foi possível efetuar atualização no banco de dados (ORACLE)" + "\n" + sql, e);
            return false;
        }
    }

    public static ResultSet executaQuery(String sql) {
        resultSet = null;
        try {
            statement = getInstacia().getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
                 e.printStackTrace();
           // logger.error("Não foi possível efetuar consulta no banco de dados (ORACLE)" + "\n" + sql, e);
        }
        return resultSet;
    }
}

