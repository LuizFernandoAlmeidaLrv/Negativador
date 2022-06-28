/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.almeida
 */

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



public class PropriedadesBDP {

    private String localServidor;
    private String nomeBanco;
    private String usuario;
    private String senha;
    private String url;
    private String outro;
    private String bancoDados;
    private String driver;
    private final String MYSQL = "MYSQL";
    private final String ORACLE = "ORACLE";
    private final String POSTGRES = "POSTGRES";

    public PropriedadesBDP() {
        try {
            FileReader ler = new FileReader(System.getProperty("user.dir") + "\\configP.txt");
            BufferedReader leitor = new BufferedReader(ler);
            while ((outro = leitor.readLine()) != null) {
                if (outro.equals("BANCODADOS")) {
                    bancoDados = leitor.readLine();
                }
                if (outro.equals("ENDERECO")) {
                    localServidor = leitor.readLine();
                }
                if (outro.equals("BANCO")) {
                    nomeBanco = leitor.readLine();
                }
                if (outro.equals("USUARIO")) {
                    usuario = leitor.readLine();
                }
                if (outro.equals("SENHA")) {
                    senha = leitor.readLine();
                }
            }

            if (bancoDados.equalsIgnoreCase(ORACLE)) {
                url = "jdbc:oracle:thin:@" + localServidor.trim() + ":1521:" + nomeBanco.trim() + "";
                driver = "oracle.jdbc.OracleDriver";
            } else if (bancoDados.equalsIgnoreCase(MYSQL)) {
                url = "jdbc:mysql://" + localServidor + ":3306/" + nomeBanco + "";
                driver = "com.mysql.jdbc.Driver";
             } else if (bancoDados.equalsIgnoreCase(POSTGRES)) {
                url = "jdbc:postgresql://" + localServidor + ":5432/" + nomeBanco + "";
                driver = "org.postgresql.Driver";
                     }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao consultar o endereço do banco.");
        }
    }

    public String getUrlBanco() {
        return url;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public String getLocalServidor() {
        return localServidor;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

}


