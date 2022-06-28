/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ParametrosModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author luiz.almeida
 */
public class ParametrosDAO {

    private String sqlSelectParametros, sqlInsertParametros, sqlUpdateParametros;
    private PreparedStatement psSelectParametros, psInsertParametros, psUpdateParametros;
    private ResultSet rsSelectParametros, rsInsertParametros, rsUpdateParametros;
    private List<ParametrosModel> LParametros = new ArrayList<>();
    private ParametrosModel parametros = new ParametrosModel();

    public ParametrosDAO() {
        sqlSelectParametros = "SELECT PARAMETRO, "
                + "VALOR, "
                + "ULTIMA_ATUALIZACAO, "
                + "USUARIO_ALTERACAO, "
                + "DESCRICAO "
                + "FROM PARAMETROS";

        sqlInsertParametros = "INSERT INTO PARAMETROS("
                + "PARAMETRO, "
                + "VALOR, "
                + "ULTIMA_ATUALIZACAO, "
                + "USUARIO_ALTERACAO, "
                + "DESCRICAO) "
                + "VALUES(?, ?, ?, ?, ?)";

        sqlUpdateParametros = ("UPDATE PARAMETROS SET "
                + "VALOR = ?, "
                + "ULTIMA_ATUALIZACAO = ?, "
                + "USUARIO_ALTERACAO = ?, "
                + "DESCRICAO = ?"
                + "WHERE PARAMETRO = ?");
    }

    public List<ParametrosModel> ListParametros() throws ErroSistemaException {
        try {
            LParametros = new ArrayList<>();

            psSelectParametros = Conexao.getConnection().prepareStatement(sqlSelectParametros);
            rsSelectParametros = psSelectParametros.executeQuery();
            while (rsSelectParametros.next()) {
                parametros = new ParametrosModel();
                parametros.setParametro(rsSelectParametros.getString("PARAMETRO"));
                parametros.setValor(rsSelectParametros.getString("VALOR"));
                parametros.setUltimaAtualizacao(rsSelectParametros.getString("ULTIMA_ATUALIZACAO"));
                parametros.setUsuarioAlteracao(rsSelectParametros.getString("USUARIO_ALTERACAO"));
                parametros.setDescricao(rsSelectParametros.getString("DESCRICAO"));
                LParametros.add(parametros);

            }
            return LParametros;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void insertParametro(ParametrosModel parametro) throws ErroSistemaException {

        try {
            psInsertParametros = Conexao.getConnection().prepareStatement(sqlInsertParametros);
            psInsertParametros.setString(1, parametro.getParametro());
            psInsertParametros.setString(2, parametro.getValor());
            psInsertParametros.setDate(3, Utilitarios.converteData(new Date()));
            psInsertParametros.setString(4, parametro.getUsuarioAlteracao());
            psInsertParametros.setString(5, parametro.getDescricao());
            psInsertParametros.execute();
            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void updateParametro(ParametrosModel parametro) throws ErroSistemaException {
        try {
            psUpdateParametros = Conexao.getConnection().prepareStatement(sqlUpdateParametros);
            psUpdateParametros.setString(1, parametro.getValor());
            psUpdateParametros.setDate(2, Utilitarios.converteData(new Date()));
            psUpdateParametros.setString(3, parametro.getUsuarioAlteracao());
            psUpdateParametros.setString(4, parametro.getDescricao());
            psUpdateParametros.setString(5, parametro.getParametro());
            psUpdateParametros.execute();
            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }
    public String validaVensao(String versao) throws ErroSistemaException{
        String retorno = "N";
        try(PreparedStatement pValidaVersion = Conexao.getConnection().prepareStatement("SELECT VALOR FROM PARAMETROS WHERE PARAMETRO = 'Versao do sistema'")){
            rsSelectParametros = pValidaVersion.executeQuery();
            if(rsSelectParametros.next()){
               if(versao.equalsIgnoreCase(rsSelectParametros.getString("VALOR"))){
                retorno = "S";    
               }else{
                    throw new ErroSistemaException("Não foi possivel conectar a versão do sistema esta desatualizado!");
               }
               
            }
        } catch (SQLException ex) {
             throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return retorno;
    }
}
