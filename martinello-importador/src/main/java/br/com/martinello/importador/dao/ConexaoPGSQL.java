/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;

/*
 * ConexaoPGSQL.java
 * 
 * Classe usada para realizar a conexão com o banco de dados PostregreSQL
 * 
 * Autor: Claudio A. Colares  05 de Maio de 2010 07:24
 * 
 ********************************************************************************************/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Essa classe tem a finalidade de realizar uma conexao com uma base de dados PostegreSQL. Nela
 * existe dois metodos, o primeiro Conectar(), realiza a conexao com o banco de dados e o segundo,
 * Desconectar(), realiza a desconexao (Desconecta) o banco de dados.
 */
public class ConexaoPGSQL {

    /**
     * Usada para a conexao com o banco de dados
     */
    private static Connection conexaoP;

    /**
     * Usada para realizar as instrucoes SQL
     */
    private static Statement statement;
    private static ResultSet resultSet;
    private static Conexao instancia;
    private static PropriedadesBDP propriedadesBDP;
    //  private static Logger logger = Logger.getLogger(ParcelasDAO.class);

    /**
     * Esse metodo realiza a conexao com o banco, ele precisa de tres argumentos, o primeiro, recebe
     * o endereço do banco, o segundo recebe o nome do usuario e o terceiro recebe a senha do banco
     * de dados.
     *
     * EXP: "jdbc:postgresql://localhost:5432/projeto_01", "sa", "sa"
     *
     * *************************************************************************************************
     */
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
            if (conexaoP == null) {
                propriedadesBDP = new PropriedadesBDP();
                Class.forName(propriedadesBDP.getDriver());
                
                conexaoP = DriverManager.getConnection(propriedadesBDP.getUrlBanco(), propriedadesBDP.getUsuario(), propriedadesBDP.getSenha());
                

            }
        } catch (Exception e) {
            e.printStackTrace();
//   logger.error("Erro ao conectar com o Banco de Dados (POSTGRES).", e);
        }
        return conexaoP;
    }

    public static void fechaConexao() {
        try {
            if (conexaoP != null) {
                conexaoP.close();
                conexaoP = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //   logger.error("Erro ao fechar o Banco de Dados. (POSTGRES)", e);
        }
    }

    public static Boolean executaSql(String sql) {
        try {
            statement = getInstacia().getConnection().createStatement();
            statement.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //  logger.error("Não foi possível efetuar atualização no banco de dados (POSTGRES)" + "\n" + sql, e);
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
            // logger.error("Não foi possível efetuar consulta no banco de dados (POSTGRES)" + "\n" + sql, e);
        }
        return resultSet;
    }

}
