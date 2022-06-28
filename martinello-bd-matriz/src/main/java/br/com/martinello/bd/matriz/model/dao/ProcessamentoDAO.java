/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.Connection;
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
public class ProcessamentoDAO {

    private String sqlSelectProcessamento, sqlInsertProcesso, sqlUpdateProcesso, sqlListProcessamento, sqlListProcessoExtracao, sqlSelectProcessos;
    private List<ProcessamentoModel> lprocessamentoModel;
    private PreparedStatement psProcessamento, psProcessamentoPrescrever;
    private ResultSet rsProcessamento;
    private int resultado;

    public ProcessamentoDAO() {
        sqlSelectProcessamento = "SELECT ID_PROCESSAMENTO, PROVEDOR,TIPO,QUANTIDADE,DATA_INICIO,DATA_FIM,USUARIO FROM PROCESSAMENTO WHERE TO_DATE(DATA_INICIO) = SYSDATE AND DATA_FIM = '31/12/1900' OR DATA_FIM <> '31/12/1900'";
        sqlListProcessamento = "SELECT ID_PROCESSAMENTO, PROVEDOR,TIPO,QUANTIDADE,DATA_INICIO,DATA_FIM,USUARIO FROM PROCESSAMENTO WHERE TO_DATE(DATA_INICIO) = ? AND DATA_FIM = '31/12/1900' AND PROVEDOR = ? AND TIPO = ?";

    }

    public List<ProcessamentoModel> listarProcessos() throws ErroSistemaException {
        ProcessamentoModel retunrProcessamentoModel;
        try {
            psProcessamento = Conexao.getConnection().prepareStatement(sqlSelectProcessamento);

            lprocessamentoModel = new ArrayList<>();
            rsProcessamento = psProcessamento.executeQuery();

            while (rsProcessamento.next()) {

                retunrProcessamentoModel = new ProcessamentoModel();
                retunrProcessamentoModel.setIndice(rsProcessamento.getInt("ID_PROCESSAMENTO"));
                retunrProcessamentoModel.setProvedor(rsProcessamento.getString("PROVEDOR"));
                retunrProcessamentoModel.setTipo(rsProcessamento.getString("TIPO"));
                retunrProcessamentoModel.setItensTotal(rsProcessamento.getInt("QUANTIDADE"));
                retunrProcessamentoModel.setDataInicioEnvio(rsProcessamento.getDate("DATA_INICIO"));
                retunrProcessamentoModel.setDataFimEnvio(rsProcessamento.getDate("DATA_FIM"));
                retunrProcessamentoModel.setUser(rsProcessamento.getString("USUARIO"));
                lprocessamentoModel.add(retunrProcessamentoModel);
            }
            psProcessamento.close();
            rsProcessamento.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lprocessamentoModel;

    }

    public boolean consultarProcesso(String provedor, String tipoRegistro) throws ErroSistemaException {
        // ProcessamentoModel retunrProcessamentoModel;
        try {
            psProcessamento = Conexao.getConnection().prepareStatement(sqlListProcessamento);

            psProcessamento.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
            psProcessamento.setString(2, provedor);
            psProcessamento.setString(3, tipoRegistro);

            lprocessamentoModel = new ArrayList<>();
            rsProcessamento = psProcessamento.executeQuery();

            while (rsProcessamento.next()) {
                return true;

            }
            psProcessamento.close();
            rsProcessamento.close();

        } catch (SQLException es) {
            es.printStackTrace();
            throw new ErroSistemaException(es.getMessage(), es.getCause());
        }
        return false;
    }

    public boolean consultarProcessoExtracao() throws ErroSistemaException {
        // ProcessamentoModel retunrProcessamentoModel;
        try {
            sqlListProcessoExtracao = "SELECT *FROM PROCESSAMENTO WHERE DATA_FIM = '31/12/1900' ";
            psProcessamento = Conexao.getConnection().prepareStatement(sqlListProcessoExtracao);
            rsProcessamento = psProcessamento.executeQuery();
            while (rsProcessamento.next()) {
                return true;

            }
            psProcessamento.close();
            rsProcessamento.close();

        } catch (SQLException es) {
            throw new ErroSistemaException(es.getMessage(), es.getCause());
        }
        return false;
    }

    public int salvarProcesso(ProcessamentoModel processamentoModel) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            sqlInsertProcesso = "INSERT INTO PROCESSAMENTO(ID_PROCESSAMENTO, PROVEDOR, TIPO, QUANTIDADE, DATA_INICIO, DATA_FIM, USUARIO)"
                    + " VALUES (ID_PROCESSAMENTO.nextval, ?, ?, ?, ?, ?, ?)";
            psProcessamento = connection.prepareStatement(sqlInsertProcesso);

            psProcessamento.setString(1, processamentoModel.getProvedor());
            psProcessamento.setString(2, processamentoModel.getTipo());
            psProcessamento.setInt(3, processamentoModel.getItensTotal());
            psProcessamento.setDate(4, Utilitarios.converteData(new Date()));
            psProcessamento.setDate(5, Utilitarios.getDataZero());
            psProcessamento.setString(6, processamentoModel.getUser());

            resultado = psProcessamento.executeUpdate();
            // System.out.println(sqlInsertProcesso);
            if (resultado == -1) {
                connection.rollback();
                psProcessamento.close();
                return 0;
            } else {
                connection.commit();
                psProcessamento.close();
                return 1;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        }
    }

    public int updateProcessoDB(ProcessamentoModel processamentoModel) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();

            connection.setAutoCommit(false);
            sqlUpdateProcesso = "UPDATE PROCESSAMENTO SET DATA_FIM = ? WHERE PROVEDOR = ? AND TIPO = ? AND DATA_FIM = '31/12/1900'";

            psProcessamento = connection.prepareStatement(sqlUpdateProcesso);
            psProcessamento.setDate(1, Utilitarios.converteData(new Date()));
            psProcessamento.setString(2, processamentoModel.getProvedor());
            psProcessamento.setString(3, processamentoModel.getTipo());
            resultado = psProcessamento.executeUpdate();
            System.out.println(sqlInsertProcesso);
            if (resultado == -1) {
                connection.rollback();
                psProcessamento.close();
                return 0;
            } else {
                connection.commit();
                psProcessamento.close();
                return 1;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public int updateProcessoDBQuantidade(ProcessamentoModel processamentoModel) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();

            connection.setAutoCommit(false);
            sqlUpdateProcesso = "UPDATE PROCESSAMENTO SET QUANTIDADE = ? WHERE PROVEDOR = ? AND TIPO = ? AND DATA_FIM = '31/12/1900'";

            psProcessamento = connection.prepareStatement(sqlUpdateProcesso);
            psProcessamento.setInt(1, processamentoModel.getItensTotal());
            psProcessamento.setString(2, processamentoModel.getProvedor());
            psProcessamento.setString(3, processamentoModel.getTipo());
            resultado = psProcessamento.executeUpdate();
            System.out.println(sqlInsertProcesso);
            if (resultado == -1) {
                connection.rollback();
                psProcessamento.close();
                return 0;
            } else {
                connection.commit();
                psProcessamento.close();
                return 1;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("Erro ao fazer update do processo de extração", ex.getCause());
        }
    }

    public void updateProcessoDBSpc(ProcessamentoModel processamentoModelSpc) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();

            connection.setAutoCommit(false);
            sqlUpdateProcesso = "UPDATE PROCESSAMENTO SET DATA_FIM = ? WHERE PROVEDOR = ? AND TIPO = ? AND DATA_FIM = '31/12/1900'";

            psProcessamento = connection.prepareStatement(sqlUpdateProcesso);
            psProcessamento.setDate(1, Utilitarios.converteData(new Date()));
            psProcessamento.setString(2, processamentoModelSpc.getProvedor());
            psProcessamento.setString(3, processamentoModelSpc.getTipo());
            resultado = psProcessamento.executeUpdate();
            System.out.println(sqlInsertProcesso);
            if (resultado == -1) {
                connection.rollback();
                psProcessamento.close();
            } else {
                connection.commit();
                psProcessamento.close();

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public boolean consultarProcessoPrecrever(String provedor, String tipoRegistro) throws ErroSistemaException {
        // ProcessamentoModel retunrProcessamentoModel;
        String sqlListProcessoPrecrever = "SELECT ID_PROCESSAMENTO, PROVEDOR,TIPO,QUANTIDADE,DATA_INICIO,DATA_FIM,USUARIO FROM PROCESSAMENTO WHERE TO_DATE(DATA_INICIO) = ? AND PROVEDOR = ? AND TIPO = ?";

        try {
            psProcessamento = Conexao.getConnection().prepareStatement(sqlListProcessoPrecrever);

            psProcessamento.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
            psProcessamento.setString(2, provedor);
            psProcessamento.setString(3, tipoRegistro);
            rsProcessamento = psProcessamento.executeQuery();

            while (rsProcessamento.next()) {
                return true;

            }
            psProcessamento.close();
            rsProcessamento.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int salvarProcessoPrecrever(ProcessamentoModel processamentoModel) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            sqlInsertProcesso = "INSERT INTO PROCESSAMENTO(ID_PROCESSAMENTO, PROVEDOR, TIPO, QUANTIDADE, DATA_INICIO, DATA_FIM, USUARIO)"
                    + " VALUES (ID_PROCESSAMENTO.nextval, ?, ?, ?, ?, ?, ?)";
            psProcessamentoPrescrever = connection.prepareStatement(sqlInsertProcesso);

            psProcessamentoPrescrever.setString(1, processamentoModel.getProvedor());
            psProcessamentoPrescrever.setString(2, processamentoModel.getTipo());
            psProcessamentoPrescrever.setInt(3, processamentoModel.getItensTotal());
            psProcessamentoPrescrever.setDate(4, Utilitarios.converteData(new Date()));
            psProcessamentoPrescrever.setDate(5, Utilitarios.converteData(new Date()));
            psProcessamentoPrescrever.setString(6, processamentoModel.getUser());

            resultado = psProcessamentoPrescrever.executeUpdate();
            // System.out.println(sqlInsertProcesso);
            if (resultado == -1) {
                connection.rollback();
                psProcessamentoPrescrever.close();
                return 0;
            } else {
                connection.commit();
                psProcessamentoPrescrever.close();
                return 1;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public List<ProcessamentoModel> buscarProcessos(ProcessamentoModel consultaProcesso) throws ErroSistemaException {
        ProcessamentoModel processamentoModel;
        sqlSelectProcessos = "SELECT ID_PROCESSAMENTO, PROVEDOR, TIPO, QUANTIDADE, DATA_INICIO, DATA_FIM, USUARIO FROM PROCESSAMENTO" + getWhere(consultaProcesso);

        try {
            psProcessamento = Conexao.getConnection().prepareStatement(sqlSelectProcessos);

            lprocessamentoModel = new ArrayList<>();
            rsProcessamento = psProcessamento.executeQuery();

            while (rsProcessamento.next()) {

                processamentoModel = new ProcessamentoModel();
                processamentoModel.setIndice(rsProcessamento.getInt("ID_PROCESSAMENTO"));
                processamentoModel.setProvedor(rsProcessamento.getString("PROVEDOR"));
                processamentoModel.setTipo(rsProcessamento.getString("TIPO"));
                processamentoModel.setItensTotal(rsProcessamento.getInt("QUANTIDADE"));
                processamentoModel.setDataInicioEnvio(rsProcessamento.getDate("DATA_INICIO"));
                processamentoModel.setDataFimEnvio(rsProcessamento.getDate("DATA_FIM"));
                processamentoModel.setUser(rsProcessamento.getString("USUARIO"));
                lprocessamentoModel.add(processamentoModel);
            }
            psProcessamento.close();
            rsProcessamento.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lprocessamentoModel;

    }

    public String getWhere(ProcessamentoModel consultaProcesso) {
        String where = "";
        if (!consultaProcesso.getProvedor().trim().equals("")) {
            where += ((consultaProcesso.getProvedor() != null) ? " AND PROVEDOR = '" + consultaProcesso.getProvedor() + "'" : "");
        }
        if (!consultaProcesso.getDataInicio().equals("")) {
            where += ((consultaProcesso.getDataInicio() != null) ? " AND TO_DATE(DATA_INICIO) BETWEEN '" + consultaProcesso.getDataInicio() + "'" : "");
            where += ((consultaProcesso.getDataFim() != null) ? " AND '" + consultaProcesso.getDataFim() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public void encerrarProcessos(UsuarioModel usuario) throws ErroSistemaException {
        try {
            String sqlSelectProcessosAbertos = "UPDATE PROCESSAMENTO SET DATA_FIM = SYSDATE WHERE DATA_FIM = '31/12/1900' AND USUARIO = ?";

            psProcessamento = Conexao.getConnection().prepareStatement(sqlSelectProcessosAbertos);
            psProcessamento.setString(1, usuario.getLogin());
            psProcessamento.execute();
            Conexao.getConnection().commit();
            psProcessamento.close();
        } catch (SQLException ex) {
            try {
                psProcessamento.close();
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }
}
