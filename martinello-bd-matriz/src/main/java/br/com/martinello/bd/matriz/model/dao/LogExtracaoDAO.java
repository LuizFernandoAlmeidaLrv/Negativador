/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.LogExtracaoModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author luiz.almeida
 */
public class LogExtracaoDAO {

    private PreparedStatement psLogExtracao;
    private ResultSet rsLogExtracao;
    public List<LogExtracaoModel> lLogExttracao;
    public String sqlSelectLog;
    public int resultado, indice;
    public  LogExtracaoModel logExtracaoModel; 

    public LogExtracaoDAO() {

    }

    public List<LogExtracaoModel> logExtracao(LogExtracaoModel filtroLogExtracao) throws ErroSistemaException {

        sqlSelectLog = "SELECT ID_LOG_EXTRACAO, ID_EXTRATOR, NUM_DOC, VENCIMENTO, CLIENTE, PONTO, MENSAGEM, DATA_EXTRACAO, STATUS, CGCCPF FROM LOG_EXTRACAO" + getWhere(filtroLogExtracao);

        try {
            psLogExtracao = Conexao.getConnection().prepareStatement(sqlSelectLog);
            rsLogExtracao = psLogExtracao.executeQuery();
            lLogExttracao = new ArrayList<>();
            indice = 1;
            while (rsLogExtracao.next()) {
                logExtracaoModel = new LogExtracaoModel();
                logExtracaoModel.setCliente(rsLogExtracao.getString("CLIENTE"));
                logExtracaoModel.setContrato(rsLogExtracao.getString("NUM_DOC"));
                logExtracaoModel.setVencimento(rsLogExtracao.getString("VENCIMENTO"));
                logExtracaoModel.setFilial(rsLogExtracao.getString("PONTO"));
                logExtracaoModel.setIdExtracao(rsLogExtracao.getString("ID_LOG_EXTRACAO"));
                logExtracaoModel.setMenssagem(rsLogExtracao.getString("MENSAGEM"));
                logExtracaoModel.setIndice(indice++);
                logExtracaoModel.setIdExtrator(rsLogExtracao.getString("ID_EXTRATOR"));
                logExtracaoModel.setStatus(rsLogExtracao.getString("STATUS"));
                logExtracaoModel.setDataExtracao(rsLogExtracao.getString("DATA_EXTRACAO"));
                logExtracaoModel.setCgcCpf(rsLogExtracao.getString("CGCCPF"));
                lLogExttracao.add(logExtracaoModel);
            }
            psLogExtracao.close();
            rsLogExtracao.close();
        } catch (SQLException ex) {
             ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lLogExttracao;

    }

    public String getWhere(LogExtracaoModel filtroLogExtracao) {
        String where = "";
        where += ((filtroLogExtracao.getFilial() != null) ? " AND PONTO = '" + filtroLogExtracao.getFilial() + "'" : "");
        where += ((filtroLogExtracao.getCliente() != null) ? " AND CLIENTE = '" + filtroLogExtracao.getCliente() + "'" : "");
        where += ((filtroLogExtracao.getContrato() != null) ? " AND NUM_DOC = '" + filtroLogExtracao.getContrato() + "'" : "");
        where += ((filtroLogExtracao.getStatus() != null) ? " AND STATUS = '" + filtroLogExtracao.getStatus() + "'" : "");
        where += ((filtroLogExtracao.getDataExtracao() != null) ? " AND TO_DATE(DATA_EXTRACAO) BETWEEN '" + filtroLogExtracao.getDataExtracao() + "'" : "");
         where += ((filtroLogExtracao.getDataExtracaoFim()!= null) ? " AND '" + filtroLogExtracao.getDataExtracaoFim() + "'" : "");
        where += ((filtroLogExtracao.getIdExtrator() != null) ? " AND ID_EXTRATOR = '" + filtroLogExtracao.getIdExtrator() + "'" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }
}
