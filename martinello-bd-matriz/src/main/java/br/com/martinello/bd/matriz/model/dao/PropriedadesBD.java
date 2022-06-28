/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.almeida
 */
public class PropriedadesBD {

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
      private String localArquivoPTO;

    private String enderecoWsReceberXML = "192.168.0.248";
    private String portaWsReceberXML = "8080";
    private String usuarioWsReceberXML;
    private String senhaWsReceberXML;

    public PropriedadesBD() {
        try {
            FileReader ler = new FileReader(System.getProperty("user.dir") + "/config.txt");
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
 public String getLocalArquivoPTO() {
        return localArquivoPTO;
    }

    public void setLocalArquivoPTO(String localArquivoPTO) {
        this.localArquivoPTO = localArquivoPTO;
    }

    public String getEnderecoWsReceberXML() {
        return enderecoWsReceberXML;
    }

    public void setEnderecoWsReceberXML(String enderecoWsReceberXML) {
        this.enderecoWsReceberXML = enderecoWsReceberXML;
    }

    public String getPortaWsReceberXML() {
        return portaWsReceberXML;
    }

    public void setPortaWsReceberXML(String portaWsReceberXML) {
        this.portaWsReceberXML = portaWsReceberXML;
    }

    public String getUsuarioWsReceberXML() {
        return usuarioWsReceberXML;
    }

    public void setUsuarioWsReceberXML(String usuarioWsReceberXML) {
        this.usuarioWsReceberXML = usuarioWsReceberXML;
    }

    public String getSenhaWsReceberXML() {
        return senhaWsReceberXML;
    }

    public void setSenhaWsReceberXML(String senhaWsReceberXML) {
        this.senhaWsReceberXML = senhaWsReceberXML;
    }

}



