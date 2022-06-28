/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.control.RetornoEnvioController;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.LogParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
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
public class ParcelaDAO {

    public List<ParcelaModel> lparcelaModel;
    public List<LogParcelaModel> lLogParcela;
    public PreparedStatement psParcela, psLogParcela, psParcelasPImport, psParcelasPExtrator, psParcelasPImpcdl, psUpParcelasPImport, psUpParcelasPExtrator, psUpParcelasPImpcdl,
            psParcelaUpdate, psSelectParcelasAtualizar, psExtracao, psRetornoExtrator, psLogExtrator, psParcelasPrescrita, psExtornoContasReceber, psFinalizaRegistro, psFinalizaInclusaoPendente;
    public ResultSet rsFinalizaRegistro, rsParcela, rsLogParcela, rsParcelasPImport, rsParcelasPExtrator, rsParcelasPImpcdl, rsSelectParcelasAtualizar, rsExtracao, rsParcelasPrescrita, rsExtornoContasReceber;
    public String sqlSelectParcela, sqlSelectLogParcela, sqlSelectParcelasPExtrator, sqlSelectParcelasPImport, sqlSelectParcelasPImpcdl, sqlUpdateParcelasPExtrator, sqlUpdateParcelasPImport, sqlUpdateParcelasPImpcdl;
    public String sqlSelectLog;
    private Connection connection;
    private int resultado;
    private int contTotal;

    public List<ParcelaModel> extrairParcela(ParcelaModel parcelaModel) throws ErroSistemaException {

        sqlSelectParcela = "SELECT PARCELA.ID_PARCELA,\n"
                + "       PARCELA.ID_FILIAL,\n"
                + "       PARCELA.CODIGO,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.DATALAN,\n"
                + "       PARCELA.VENC,\n"
                + "       PARCELA.VALOR,\n"
                + "       PARCELA.DATAPAG,\n"
                + "       PARCELA.CAPITPAG,\n"
                + "       PARCELA.SIT,\n"
                + "       PARCELA.DATA_NEGATIVADA,\n"
                + "       PARCELA.DATA_BAIXA,\n"
                + "       (SELECT EXTRATOR.DATA_SPC\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'B') DATA_BAIXA_SPC,\n"
                + "       (SELECT EXTRATOR.DATA_SPC_AVALISTA\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'B') DATA_BAIXA_SPC_AVALISTA,\n"
                + "       (SELECT EXTRATOR.DATA_FACMAT\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'B') DATA_BAIXA_FACMAT,\n"
                + "       (SELECT EXTRATOR.DATA_SPC\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'I') DATA_INCLUSAO_SPC,\n"
                + "       (SELECT EXTRATOR.DATA_SPC_AVALISTA\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'I') DATA_INCLUSAO_AVALISTA_SPC,\n"
                + "       (SELECT EXTRATOR.DATA_FACMAT\n"
                + "          FROM EXTRATOR\n"
                + "         WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           AND EXTRATOR.TIPO = 'I') DATA_INCLUSAO_FACMAT,\n"
                + "       (SELECT NOME FROM PESSOA WHERE PESSOA.ID_PESSOA = PARCELA.ID_AVALISTA) NOME_AVALISTA,\n"
                + "       PARCELA.DATA_EXTRACAO,\n"
                + "       NATUREZA_SPC.DESCRICAO AS NATUREZA_SPC,\n"
                + "       NATUREZA_BVS.DESCRICAO AS NATUREZA_BVS,\n"
                + "       MOTIVO_BAIXA_SPC.MOTIVO_BAIXA AS MOTIVO_BAIXA_SPC,\n"
                + "       MOTIVO_BAIXA_BVS.MOTIVO_BAIXA AS MOTIVO_BAIXA_BVS,\n"
                + "       PARCELA.TAXA,\n"
                + "       PARCELA.JUROS,\n"
                + "       PARCELA.VALOR_CALC,\n"
                + "       PARCELA.VALOR_PAG,\n"
                + "       PARCELA.JUROS_PAG,\n"
                + "       PARCELA.DATAALT,\n"
                + "       PARCELA.NUMPAR,\n"
                + "       PARCELA.TIPOPAG,\n"
                + "       PARCELA.ID_CLIENTE,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.DATA_ULT_ATUALIZACAO,\n"
                + "       PARCELA.CLIENTE,\n"
                + "       PARCELA.ID_REGISTRO_BVS,\n"
                + "       PARCELA.AVALISTA,\n"
                + "       CASE\n"
                + "         WHEN (EXTRATOR.ID_MOTIVO_INC_BVS) = '1' THEN\n"
                + "          'DIVIDA'\n"
                + "         ELSE\n"
                + "          'VAZIO'\n"
                + "       END MOTIVO_INCLUSAO_BVS\n"
                + "  FROM PARCELA\n"
                + " INNER JOIN EXTRATOR\n"
                + "    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + " INNER JOIN NATUREZA_INCLUSAO NATUREZA_SPC\n"
                + "    ON NATUREZA_SPC.ID_NATUREZA_INCLUSAO = EXTRATOR.ID_NATUREZA_INC_SPC\n"
                + "   AND NATUREZA_SPC.PROVEDOR = 'SPC'\n"
                + "  LEFT OUTER JOIN NATUREZA_INCLUSAO NATUREZA_BVS\n"
                + "    ON NATUREZA_BVS.ID_NATUREZA_INCLUSAO = EXTRATOR.ID_NATUREZA_INC_BVS\n"
                + "   AND NATUREZA_BVS.PROVEDOR = 'BVS'\n"
                + " INNER JOIN MOTIVO_BAIXA MOTIVO_BAIXA_SPC\n"
                + "    ON MOTIVO_BAIXA_SPC.ID_MTV_BAIXA = EXTRATOR.ID_MOTIVO_EXCLUSAO_SPC\n"
                + "   AND MOTIVO_BAIXA_SPC.LOCAL_BAIXA = 'SPC'\n"
                + "  LEFT OUTER JOIN MOTIVO_BAIXA MOTIVO_BAIXA_BVS\n"
                + "    ON MOTIVO_BAIXA_BVS.ID_MTV_BAIXA = EXTRATOR.ID_MOTIVO_EXCLUSAO_BVS\n"
                + "   AND MOTIVO_BAIXA_BVS.LOCAL_BAIXA = 'BVS'\n"
                + " WHERE EXTRATOR.TIPO = 'I' AND PARCELA.ID_PARCELA = ?";

        ParcelaModel parcelaModelTable = new ParcelaModel();
        lparcelaModel = new ArrayList<>();
        try {
            psParcela = Conexao.getConnection().prepareStatement(sqlSelectParcela);
            psParcela.setString(1, parcelaModel.getIdParcela());
            rsParcela = psParcela.executeQuery();
            while (rsParcela.next()) {
                parcelaModelTable = new ParcelaModel();
                parcelaModelTable.setIdParcela(rsParcela.getString("ID_PARCELA"));
                parcelaModelTable.setCodigoClientParcela(rsParcela.getString("CODIGO"));
                parcelaModelTable.setPontoFilial(rsParcela.getString("ID_FILIAL"));
                parcelaModelTable.setNumeroDoContrato(rsParcela.getString("NUMERO_DOC"));
                parcelaModelTable.setDataLancamento(rsParcela.getDate("DATALAN"));
                parcelaModelTable.setDataVencimento(rsParcela.getDate("VENC"));
                parcelaModelTable.setValorParcela(rsParcela.getDouble("VALOR"));
                parcelaModelTable.setDataPagamento(rsParcela.getDate("DATAPAG"));
                parcelaModelTable.setCapitalPago(rsParcela.getDouble("CAPITPAG"));
                parcelaModelTable.setSituacaoParcela(rsParcela.getString("SIT"));
                parcelaModelTable.setDataNegativacao(rsParcela.getDate("DATA_NEGATIVADA"));
                parcelaModelTable.setDataBaixaNegativacao(rsParcela.getDate("DATA_BAIXA"));
                parcelaModelTable.setDataBaixaSpc(rsParcela.getDate("DATA_BAIXA_SPC"));
                parcelaModelTable.setDataBaixaAvalistaSpc(rsParcela.getDate("DATA_BAIXA_SPC_AVALISTA"));
                parcelaModelTable.setDataBaixaFacmat(rsParcela.getDate("DATA_BAIXA_FACMAT"));
                parcelaModelTable.setDataEnvioSpc(rsParcela.getDate("DATA_INCLUSAO_SPC"));
                parcelaModelTable.setDataEnvioAvalistaSpc(rsParcela.getDate("DATA_INCLUSAO_AVALISTA_SPC"));
                parcelaModelTable.setDataEnvioFacmat(rsParcela.getDate("DATA_INCLUSAO_FACMAT"));
                parcelaModelTable.setDataExtracao(rsParcela.getDate("DATA_EXTRACAO"));
                parcelaModelTable.setNaturezaBvs(rsParcela.getString("NATUREZA_BVS"));
                parcelaModelTable.setNaturezaSpc(rsParcela.getString("NATUREZA_SPC"));
                parcelaModelTable.setMotivoBaixaBvs(rsParcela.getString("MOTIVO_BAIXA_BVS"));
                parcelaModelTable.setMotivoBaixaSpc(rsParcela.getString("MOTIVO_BAIXA_SPC"));
                parcelaModelTable.setDataNegativacao(rsParcela.getDate("DATA_NEGATIVADA"));
                parcelaModelTable.setTaxaDeJuros(rsParcela.getBigDecimal("TAXA"));
                parcelaModelTable.setJurosPago(rsParcela.getDouble("JUROS_PAG"));
                parcelaModelTable.setValorJuros(rsParcela.getDouble("JUROS"));
                parcelaModelTable.setValorCalc(rsParcela.getDouble("VALOR_CALC"));
                parcelaModelTable.setValorPago(rsParcela.getDouble("VALOR_PAG"));
                parcelaModelTable.setDataAlteracao(rsParcela.getDate("DATAALT"));
                parcelaModelTable.setNumeroParcela(rsParcela.getString("NUMPAR"));
                parcelaModelTable.setTipoPagamento(rsParcela.getString("TIPOPAG"));
                parcelaModelTable.setCodCliente(rsParcela.getString("CLIENTE"));
                parcelaModelTable.setDataAtualizacao(rsParcela.getDate("DATA_ULT_ATUALIZACAO"));
                parcelaModelTable.setIdRegistroBvs(rsParcela.getString("ID_REGISTRO_BVS"));

                lparcelaModel.add(parcelaModelTable);
            }
            psParcela.close();
            rsParcela.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lparcelaModel;

    }

    public List<LogParcelaModel> buscaLogParcela(ParcelaModel parcelaModel) throws SQLException, ErroSistemaException {
        sqlSelectLogParcela = "SELECT LOG_EXTRATOR.ID_LOG,\n"
                + "       LOG_EXTRATOR.ID_EXTRATOR,\n"
                + "       LOG_EXTRATOR.CODIGO_RETORNO,\n"
                + "       LOG_EXTRATOR.DATA_EXECUCAO,\n"
                + "       LOG_EXTRATOR.OBSERVACAO,\n"
                + "       LOG_EXTRATOR.STATUS, \n"
                + "       EXTRATOR.ID_PARCELA,\n"
                + "       EXTRATOR.DATA_RETORNO,\n"
                + "       RETORNOS_INTEGRACAO.DESCRICAO,\n"
                + "       CASE\n"
                + "         WHEN (RETORNOS_INTEGRACAO.TIPO) = 'S' THEN\n"
                + "          'SPC'\n"
                + "         WHEN (RETORNOS_INTEGRACAO.TIPO) = 'B' THEN\n"
                + "          'FACMAT'\n"
                + "          WHEN (RETORNOS_INTEGRACAO.TIPO) = 'C' THEN\n"
                + "          'FACMAT'\n"
                + "          WHEN (RETORNOS_INTEGRACAO.TIPO) = 'E' THEN\n"
                + "          'EXTRATOR'\n"
                + "       END PROVEDOR\n"
                + "  FROM LOG_EXTRATOR\n"
                + " INNER JOIN EXTRATOR\n"
                + "    ON EXTRATOR.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "  LEFT OUTER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " INNER JOIN RETORNOS_INTEGRACAO\n"
                + "    ON RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + " WHERE PARCELA.ID_PARCELA = ? ORDER BY LOG_EXTRATOR.ID_LOG ASC";
        lLogParcela = new ArrayList<>();
        LogParcelaModel parcelaLog = new LogParcelaModel();
        int indice = 1;
        try {
            psLogParcela = Conexao.getConnection().prepareStatement(sqlSelectLogParcela);
            psLogParcela.setString(1, parcelaModel.getIdParcela());
            rsLogParcela = psLogParcela.executeQuery();
            while (rsLogParcela.next()) {
                parcelaLog = new LogParcelaModel();
                parcelaLog.setIndice(indice++);
                parcelaLog.setIdLogExtrator(rsLogParcela.getInt("ID_LOG"));
                parcelaLog.setIdExtrator(rsLogParcela.getInt("ID_EXTRATOR"));
                parcelaLog.setIdParcela(rsLogParcela.getInt("ID_PARCELA"));
                parcelaLog.setCogigoRetorno(rsLogParcela.getInt("CODIGO_RETORNO"));
                parcelaLog.setDataLog(rsLogParcela.getDate("DATA_EXECUCAO"));
                parcelaLog.setDataRetorno(rsLogParcela.getDate("DATA_RETORNO"));
                parcelaLog.setDescricaoLog(rsLogParcela.getString("DESCRICAO"));
                parcelaLog.setObservacao(rsLogParcela.getString("OBSERVACAO"));
                parcelaLog.setStatus(rsLogParcela.getString("STATUS"));
                if (rsLogParcela.getString("PROVEDOR") == null) {
                    parcelaLog.setProvedor("");
                } else {
                    parcelaLog.setProvedor(rsLogParcela.getString("PROVEDOR"));
                }

                lLogParcela.add(parcelaLog);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            psLogParcela.close();
            rsLogParcela.close();
        }

        return lLogParcela;

    }

    public List<LogParcelaModel> buscaLog(LogParcelaModel filtroLogExtracao) throws ErroSistemaException {
        sqlSelectLog = "SELECT LOG_EXTRATOR.ID_LOG,\n"
                + "       LOG_EXTRATOR.ID_EXTRATOR,\n"
                + "       LOG_EXTRATOR.CODIGO_RETORNO,\n"
                + "       LOG_EXTRATOR.DATA_EXECUCAO,\n"
                + "       LOG_EXTRATOR.OBSERVACAO,\n"
                + "       LOG_EXTRATOR.STATUS,\n"
                + "       LOG_EXTRATOR.ORIGEM,\n"
                + "       EXTRATOR.ID_PARCELA,\n"
                + "       EXTRATOR.STATUS_SPC,\n"
                + "       EXTRATOR.STATUS_FACMAT,\n"
                + "       EXTRATOR.DATA_RETORNO,\n"
                + "       EXTRATOR.TIPO AS TIPO_PARCELA,\n"
                + "       RETORNOS_INTEGRACAO.DESCRICAO,\n"
                + "       RETORNOS_INTEGRACAO.TIPO,\n"
                + "       PESSOA.CPF_RAZAO,\n"
                + "       PESSOA.NOME,\n"
                + "       PARCELA.CLIENTE,\n"
                + "       PARCELA.ID_FILIAL,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.PRESCRITO,\n"
                + "       CASE\n"
                + "         WHEN (RETORNOS_INTEGRACAO.TIPO) = 'S' THEN\n"
                + "          'SPC'\n"
                + "         WHEN (RETORNOS_INTEGRACAO.TIPO) = 'B' THEN\n"
                + "          'FACMAT'\n"
                + "         WHEN (RETORNOS_INTEGRACAO.TIPO) = 'E' THEN\n"
                + "          'EXTRACAO'\n"
                + "       END PROVEDOR\n"
                + "  FROM LOG_EXTRATOR\n"
                + " INNER JOIN EXTRATOR\n"
                + "    ON EXTRATOR.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "  LEFT OUTER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " INNER JOIN RETORNOS_INTEGRACAO\n"
                + "    ON RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + " INNER JOIN PESSOA\n"
                + "    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n" + getWhere(filtroLogExtracao);
        lLogParcela = new ArrayList<>();
        System.out.println("" + sqlSelectLog);
        LogParcelaModel parcelaLog = new LogParcelaModel();
        int indice = 1;
        try {
            psLogParcela = Conexao.getConnection().prepareStatement(sqlSelectLog);
            rsLogParcela = psLogParcela.executeQuery();
            while (rsLogParcela.next()) {
                parcelaLog = new LogParcelaModel();
                parcelaLog.setIndice(indice++);
                parcelaLog.setIdLogExtrator(rsLogParcela.getInt("ID_LOG"));
                parcelaLog.setIdExtrator(rsLogParcela.getInt("ID_EXTRATOR"));
                parcelaLog.setIdParcela(rsLogParcela.getInt("ID_PARCELA"));
                parcelaLog.setCogigoRetorno(rsLogParcela.getInt("CODIGO_RETORNO"));
                parcelaLog.setDataLog(rsLogParcela.getDate("DATA_EXECUCAO"));
                parcelaLog.setDataRetorno(rsLogParcela.getDate("DATA_RETORNO"));
                parcelaLog.setDescricaoLog(rsLogParcela.getString("DESCRICAO"));
                parcelaLog.setObservacao(rsLogParcela.getString("OBSERVACAO"));
                parcelaLog.setStatusSpc(rsLogParcela.getString("STATUS_SPC"));
                parcelaLog.setStatusFacmat(rsLogParcela.getString("STATUS_FACMAT"));
                parcelaLog.setCliente(rsLogParcela.getString("CLIENTE"));
                parcelaLog.setNome(rsLogParcela.getString("NOME"));
                parcelaLog.setContrato(rsLogParcela.getString("NUMERO_DOC"));
                parcelaLog.setFilial(rsLogParcela.getString("ID_FILIAL"));
                parcelaLog.setOrigem(rsLogParcela.getString("ORIGEM"));
                parcelaLog.setTipo(rsLogParcela.getString("TIPO_PARCELA"));
                if (rsLogParcela.getString("STATUS") == null) {
                    parcelaLog.setStatus("");
                } else {
                    parcelaLog.setStatus(rsLogParcela.getString("STATUS"));
                }

                if (rsLogParcela.getString("PROVEDOR") == null) {
                    parcelaLog.setProvedor("");
                } else {
                    parcelaLog.setProvedor(rsLogParcela.getString("PROVEDOR"));
                }

                lLogParcela.add(parcelaLog);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psLogParcela.close();
                rsLogParcela.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }
        return lLogParcela;

    }

    public String getWhere(LogParcelaModel filtroLogExtracao) {

        String where = "";
        where += ((filtroLogExtracao.getFilial() != null) ? " AND PARCELA.ID_FILIAL = '" + filtroLogExtracao.getFilial() + "'" : "");
        where += ((filtroLogExtracao.getCliente() != null) ? " AND CLIENTE = '" + filtroLogExtracao.getCliente() + "'" : "");
        where += ((filtroLogExtracao.getContrato() != null) ? " AND NUM_DOC = '" + filtroLogExtracao.getContrato() + "'" : "");
        where += ((filtroLogExtracao.getStatus() != null) ? " AND LOG_EXTRATOR.STATUS = '" + filtroLogExtracao.getStatus() + "'" : "");
        where += ((filtroLogExtracao.getDataLog() != null) ? " AND TO_DATE(DATA_EXECUCAO) BETWEEN '" + Utilitarios.converteDataString(filtroLogExtracao.getDataLog(), "dd/MM/yyyy") + "'" : "");
        where += ((filtroLogExtracao.getDataLogFim() != null) ? " AND '" + Utilitarios.converteDataString(filtroLogExtracao.getDataLogFim(), "dd/MM/yyyy") + "'" : "");
        where += ((filtroLogExtracao.getIdExtrator() > 0) ? " AND ID_EXTRATOR = '" + filtroLogExtracao.getIdExtrator() + "'" : "");
        where += ((filtroLogExtracao.getProvedor() != null) ? "AND RETORNOS_INTEGRACAO.TIPO = '" + filtroLogExtracao.getProvedor() + "'" : "");
        where += ((filtroLogExtracao.getOrigem() != null) ? "AND ORIGEM = '" + filtroLogExtracao.getOrigem() + "'" : "");

        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public int prescreverRegistroExtrator() throws ErroSistemaException {
        sqlSelectParcelasPExtrator = "SELECT ID_PARCELA, VENC, DATA_PRESCRITO, PRESCRITO FROM PARCELA WHERE VENC < SYSDATE - 1826 AND PRESCRITO = 'N'";
        sqlUpdateParcelasPExtrator = ("UPDATE PARCELA SET DATA_PRESCRITO = ?, PRESCRITO = 'S' WHERE ID_PARCELA = ?");
        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            psParcelasPExtrator = Conexao.getConnection().prepareStatement(sqlSelectParcelasPExtrator);
            psUpParcelasPExtrator = connection.prepareStatement(sqlUpdateParcelasPExtrator);
            rsParcelasPExtrator = psParcelasPExtrator.executeQuery();
            contTotal = 0;
            while (rsParcelasPExtrator.next()) {
                contTotal++;
                psUpParcelasPExtrator.setDate(1, Utilitarios.converteData(new Date()));
                psUpParcelasPExtrator.setInt(2, rsParcelasPExtrator.getInt("ID_PARCELA"));
                psUpParcelasPExtrator.executeUpdate();
                connection.commit();
            }

            psParcelasPExtrator.close();
            psUpParcelasPExtrator.close();
            rsParcelasPExtrator.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return contTotal;
    }

    public void prescreverRegistroImpcdl() throws ErroSistemaException {
        sqlSelectParcelasPImpcdl = "SELECT IMPCDLIDENTIFICACAO, IMPCDLCONTRATO, TO_DATE(IMPCDLDATAVENCIMENTO), IMPCDLSTATUS FROM IMPCDL WHERE TO_DATE(IMPCDLDATAVENCIMENTO) < SYSDATE - 1826 AND IMPCDLSTATUS <> 'P'";
        sqlUpdateParcelasPImpcdl = ("UPDATE IMPCDL SET IMPCDLSTATUS = 'P', IMPCDLSITUACAO = 'P' WHERE IMPCDLIDENTIFICACAO = ?");

        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psParcelasPImpcdl = Conexao.getConnection().prepareStatement(sqlSelectParcelasPImpcdl);
            psUpParcelasPImpcdl = connection.prepareStatement(sqlUpdateParcelasPImpcdl);
            rsParcelasPImpcdl = psParcelasPImpcdl.executeQuery();
            while (rsParcelasPImpcdl.next()) {
                psUpParcelasPImpcdl.setInt(1, rsParcelasPImpcdl.getInt("IMPCDLIDENTIFICACAO"));
                psUpParcelasPImpcdl.executeUpdate();
                connection.commit();
            }

            psParcelasPExtrator.close();
            psUpParcelasPImpcdl.close();
            rsParcelasPImpcdl.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void prescreverRegistroImport() throws ErroSistemaException {
        sqlSelectParcelasPImport = "SELECT ID_IMPORT, CONTRATO, DATA_VENC FROM IMPORT_PROVEDOR WHERE TO_DATE(DATA_VENC) < SYSDATE - 1826 AND STATUS <> 'P'";
        sqlUpdateParcelasPImport = ("UPDATE IMPORT_PROVEDOR SET STATUS = 'P', DATA_PRESCRITO = ? WHERE ID_IMPORT = ?");
        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psParcelasPImport = Conexao.getConnection().prepareStatement(sqlSelectParcelasPImport);
            psUpParcelasPImport = connection.prepareStatement(sqlUpdateParcelasPImport);
            rsParcelasPImport = psParcelasPImport.executeQuery();
            while (rsParcelasPImport.next()) {
                psUpParcelasPImport.setDate(1, Utilitarios.converteData(new Date()));
                psUpParcelasPImport.setInt(2, rsParcelasPImport.getInt("ID_IMPORT"));
            }

            psParcelasPImport.close();
            psUpParcelasPImport.close();
            rsParcelasPImport.close();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void baixarPrescritoPago() {
        String sqlUpdatePrescritoPago = "UPDATE DB_INTEGRACAO.MR02\n"
                + "   SET DATA_MOV_RETORNAR = TO_DATE(SYSDATE), RETORNADO = 'N', BAIXANEG = ?\n"
                + " WHERE PTO = ?\n"
                + "   AND DOC = ?\n"
                + "   AND CLI = ?";

    }

//    public void atualizaParcela() {
//        String sqlUpdateParcelas = ("UPDATE PARCELA SET ID_FILIAL = ?, DATALAN = ?, VENC = ?, VALOR = ?, DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?,"
//                + " JUROS_PAG = ?, DATA_NEGATIVADA = ?, DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, NUMPAR = ?, TIPOPAG = ?"
//                + " WHERE ID_PARCELA = ?");
//        String sqlWhereManualExtracao = " WHERE MR02.PTO = ?\n"
//                + "   AND MR02.CLI = ?\n"
//                + "   AND MR02.DOC = ?"
//                + " AND MR02.CODIGO = ?";
//
//        try {
//            psSelectParcelasAtualizar = Conexao.getConnection().prepareStatement(getSqlSelectParcelas());
//            rsSelectParcelasAtualizar = psSelectParcelasAtualizar.executeQuery();
//            while (rsSelectParcelasAtualizar.next()) {
//                int idParcelaBanco = rsSelectParcelasAtualizar.getInt("ID_PARCELA");
//                psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + sqlWhereManualExtracao);
//                psExtracao.setString(1, rsSelectParcelasAtualizar.getString("FILIAL_SGL"));
//                psExtracao.setString(2, rsSelectParcelasAtualizar.getString("CLIENTE"));
//                psExtracao.setString(3, rsSelectParcelasAtualizar.getString("NUMERO_DOC"));
//                psExtracao.setString(4, rsSelectParcelasAtualizar.getString("CODIGO"));
//                rsExtracao = psExtracao.executeQuery();
//                if (rsExtracao.next()) {
//                    psParcelaUpdate = Conexao.getConnection().prepareStatement(sqlUpdateParcelas);
//                    psParcelaUpdate.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
//
//                    psParcelaUpdate.setDate(2, rsExtracao.getDate("DATALAN"));
//                    psParcelaUpdate.setDate(3, rsExtracao.getDate("VENC"));
//                    psParcelaUpdate.setBigDecimal(4, rsExtracao.getBigDecimal("VALOR"));
//                    psParcelaUpdate.setDate(5, rsExtracao.getDate("DATAPAG"));
//                    psParcelaUpdate.setBigDecimal(6, rsExtracao.getBigDecimal("CAPITPAG"));
//                    psParcelaUpdate.setString(7, rsExtracao.getString("SIT_MR02"));
//                    psParcelaUpdate.setBigDecimal(8, rsExtracao.getBigDecimal("TAXA"));
//                    psParcelaUpdate.setBigDecimal(9, rsExtracao.getBigDecimal("JUROS"));
//                    psParcelaUpdate.setBigDecimal(10, rsExtracao.getBigDecimal("VALORCALC"));
//                    psParcelaUpdate.setBigDecimal(11, rsExtracao.getBigDecimal("VALORPAG"));
//                    psParcelaUpdate.setBigDecimal(12, rsExtracao.getBigDecimal("JUROSPAG"));
//                    psParcelaUpdate.setDate(13, rsExtracao.getDate("NEGATIVADA"));
//                    psParcelaUpdate.setDate(14, rsExtracao.getDate("BAIXANEG"));
//                    psParcelaUpdate.setDate(15, Utilitarios.converteData(new Date()));
//                    psParcelaUpdate.setDate(16, rsExtracao.getDate("DATAALT"));
//                    psParcelaUpdate.setString(17, rsExtracao.getString("NUMPAR"));
//                    psParcelaUpdate.setString(18, rsExtracao.getString("TPGTO"));
//                    psParcelaUpdate.setInt(19, idParcelaBanco);
//                    psParcelaUpdate.execute();
//                    Conexao.getConnection().commit();
//                }
//            }
//
//        } catch (SQLException ex) {
//            try {
//                Conexao.getConnection().rollback();
//            } catch (ErroSistemaException ex1) {
//                ex1.printStackTrace();
//            } catch (SQLException ex1) {
//                ex1.printStackTrace();
//            }
//            ex.printStackTrace();
//        } catch (ErroSistemaException ex) {
//            ex.printStackTrace();
//        }
//    }
    public String getSqlSelectExtracao() {
        String sqlSelectExtracao = "SELECT MR02.PTO,\n"
                + "       MR02.DOC,\n"
                + "       MR02.ORIGEM,\n"
                + "       MR02.CODIGO,\n"
                + "       MR02.CLI,\n"
                + "       MR02.NUMPAR,\n"
                + "       MR02.DATALAN,\n"
                + "       MR02.VENC,\n"
                + "       MR02.VALOR,\n"
                + "       MR02.DATAPAG,\n"
                + "       MR02.DATAALT,\n"
                + "       MR02.DATA_MOV_RETORNAR,\n"
                + "       MR02.TAXA,\n"
                + "       MR02.JUROS,\n"
                + "       MR02.VALORCALC,\n"
                + "       MR02.VALORPAG,\n"
                + "       MR02.CAPITPAG,\n"
                + "       MR02.JUROSPAG,\n"
                + "       MR02.SIT AS SIT_MR02,\n"
                + "       MR02.TPGTO,\n"
                + "       MR02.NEGATIVADA,\n"
                + "       MR02.BAIXANEG,\n"
                + "       MR02.AVALISTA,\n"
                + "       MR02.RETORNADO\n"
                + "  FROM DB_INTEGRACAO.MR02\n"
                + "  INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.FILIAL_SGL = MR02.PTO\n";
        return sqlSelectExtracao;
    }

    public String getSqlSelectParcelas() {
        String sqlSelectParcelas = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "                                      EXTRATOR.STATUS,\n"
                + "                                      PARCELA.ID_PARCELA,\n"
                + "                                      PARCELA.ID_AVALISTA,\n"
                + "                                      PARCELA.CLIENTE,\n"
                + "                                      PARCELA.NUMERO_DOC,\n"
                + "                                      PARCELA.CODIGO,\n"
                + "                                      EXTRATOR.STATUS_SPC,\n"
                + "                                      EXTRATOR.STATUS_FACMAT,\n"
                + "                                      EXTRATOR.STATUS_SPC_AVAL,\n"
                + "                                      EXTRATOR.DATA_SPC,\n"
                + "                                      EXTRATOR.DATA_SPC_AVALISTA,\n"
                + "                                      EXTRATOR.DATA_FACMAT,\n"
                + "                                      EXTRATOR.TIPO,\n"
                + "                                      EXTRATOR.DATA_RETORNO,\n"
                + "                                      PESSOA.CPF_RAZAO,\n"
                + "                                      FILIAIS.FILIAL_SGL\n"
                + "                                 FROM EXTRATOR\n"
                + "                                INNER JOIN PARCELA\n"
                + "                                   ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                                INNER JOIN FILIAIS \n"
                + "                                ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL \n"
                + "                                INNER JOIN PESSOA ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE \n"
                + "                                WHERE EXTRATOR.DATA_RETORNO = TO_DATE(SYSDATE)";
        return sqlSelectParcelas;
    }

    public void retornoPrescrito(ParcelasEnviarModel parcelasEnviarBvsModel) {

//        try {
//            psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
//            psRetornoExtrator.setString(1, "S");
//            psRetornoExtrator.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
//            psRetornoExtrator.execute();
//            psRetornoExtrator.close();
//
//            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
//            psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
//            psLogExtrator.setInt(2, 143);
//            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
//            psLogExtrator.setString(4, "CLIENTE ENVIADO PARA FACMAT");
//            psLogExtrator.setString(5, "B");
//            psLogExtrator.setString(6, "S");
//            psLogExtrator.execute();
//            psLogExtrator.close();
//
//        } catch (ErroSistemaException ex) {
//            Logger.getLogger(ParcelasEnviarBvsFacmatDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(ParcelasEnviarBvsFacmatDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void updatePrcesso(int quantidade) throws ErroSistemaException {
        ProcessamentoModel processamentoModel = new ProcessamentoModel();
        processamentoModel.setProvedor("EXTRATOR");
        processamentoModel.setTipo("PRESCREVER");
        processamentoModel.setItensTotal(quantidade);
        ProcessamentoDAO processamento = new ProcessamentoDAO();
        processamento.updateProcessoDBQuantidade(processamentoModel);
    }

    public void extornarContasReceber(ExtracaoTableModel extracaoModel) throws ErroSistemaException {
        String sqlUpdateContasReceber = ("UPDATE DB_INTEGRACAO.MR02\n"
                + "   SET BAIXANEG = ?,\n"
                + "   NEGATIVADA = ?\n"
                + " WHERE PTO = ?\n"
                + "   AND DOC = ?\n"
                + "   AND CLI = ?");
        try {
            psExtornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateContasReceber);
            psExtornoContasReceber.setString(1, Utilitarios.converteDataString(extracaoModel.getDataBaixa(), "dd/MM/yyyy"));
            psExtornoContasReceber.setString(2, Utilitarios.converteDataString(extracaoModel.getDataNegativada(), "dd/MM/yyyy"));
            psExtornoContasReceber.setString(3, extracaoModel.getIdFilial());
            psExtornoContasReceber.setString(4, extracaoModel.getNumeroDoc());
            psExtornoContasReceber.setString(5, extracaoModel.getCodigoCliente());
            psExtornoContasReceber.executeUpdate();
            Conexao.getConnection().commit();
        } catch (ErroSistemaException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void extornarContasReceberE301TCR(ExtracaoTableModel extracaoModel, String Origem) throws ErroSistemaException {
        String sqlUpdateE301TCR = ("UPDATE SAPIENS.E301TCR\n"
                + "   SET USU_DATBAI = ?,\n"
                + "   USU_DATNEG = ?\n"
                + " WHERE CODFIL = ?\n"
                + "   AND NUMTIT = ? \n"
                + "   AND CODTPT = ? \n");
        try {
            psExtornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateE301TCR);
            psExtornoContasReceber.setString(1, Utilitarios.converteDataString(extracaoModel.getDataBaixa(), "dd/MM/yyyy"));
            psExtornoContasReceber.setString(2, Utilitarios.converteDataString(extracaoModel.getDataNegativada(), "dd/MM/yyyy"));
            psExtornoContasReceber.setInt(3, extracaoModel.getCodfil());
            psExtornoContasReceber.setString(4, extracaoModel.getNumtit());
            psExtornoContasReceber.setString(5, extracaoModel.getCodTpt());
            psExtornoContasReceber.executeUpdate();
            RetornoEnvioDAO retornoEnvioDAO = new RetornoEnvioDAO();
            retornoEnvioDAO.gerarPendenciaManual(extracaoModel, Origem);

            Conexao.getConnection().commit();
        } catch (ErroSistemaException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public ExtracaoTableModel buscarRegistro(String idExtracao) throws ErroSistemaException {
        String sqlFinaizaRegistro = "SELECT PARCELA.ID_FILIAL,\n"
                + "                                PARCELA.CLIENTE,\n"
                + "                                PESSOA.NOME,\n"
                + "                                PESSOA.CPF_RAZAO,\n"
                + "                                AVALISTA.NOME AS NOME_AVAL,\n"
                + "                                PARCELA.NUMERO_DOC,\n"
                + "                                PARCELA.VALOR,\n"
                + "                                PARCELA.AVALISTA,\n"
                + "                                PARCELA.VENC,\n"
                + "                                RETORNOS_INTEGRACAO.DESCRICAO,\n"
                + "                                EXTRATOR.STATUS,\n"
                + "                                EXTRATOR.STATUS_SPC,\n"
                + "                                EXTRATOR.STATUS_SPC_AVAL,\n"
                + "                                EXTRATOR.STATUS_FACMAT\n"
                + "                           FROM PARCELA\n"
                + "                          INNER JOIN EXTRATOR\n"
                + "                             ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "                          INNER JOIN LOG_EXTRATOR\n"
                + "                             ON LOG_EXTRATOR.ID_EXTRATOR = EXTRATOR.ID_EXTRATOR\n"
                + "                          INNER JOIN RETORNOS_INTEGRACAO\n"
                + "                             ON RETORNOS_INTEGRACAO.ID_RETORNO =\n"
                + "                                LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "                          INNER JOIN PESSOA\n"
                + "                             ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                           LEFT JOIN PESSOA AVALISTA\n"
                + "                             ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + "                          WHERE EXTRATOR.STATUS IN ('EP', 'I', 'F', 'H')\n"
                + "                                      AND ID_LOG =\n"
                + "                                (SELECT MAX(ID_LOG)\n"
                + "                                   FROM LOG_EXTRATOR LOG1\n"
                + "                                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                                    AND RETORNOS_INTEGRACAO.TIPO in ('C', 'B', 'S', 'E')\n"
                + "                                   -- AND LOG1.STATUS = 'E'\n"
                + "                                   )\n"
                + "                            --AND RETORNOS_INTEGRACAO.NOTIFICAR = 'S'\n"
                + "                            AND EXTRATOR.ID_EXTRATOR = ?";
        ExtracaoTableModel regFinaliza = new ExtracaoTableModel();;
        try {
            psFinalizaRegistro = Conexao.getConnection().prepareStatement(sqlFinaizaRegistro);
            psFinalizaRegistro.setString(1, idExtracao);
            rsFinalizaRegistro = psFinalizaRegistro.executeQuery();
            if (rsFinalizaRegistro.next()) {
                regFinaliza = new ExtracaoTableModel();
                regFinaliza.setIdFilial(rsFinalizaRegistro.getString("ID_FILIAL"));
                regFinaliza.setCodigoCliente(rsFinalizaRegistro.getString("CLIENTE"));
                regFinaliza.setNome(rsFinalizaRegistro.getString("NOME"));
                regFinaliza.setCpfCnpj(rsFinalizaRegistro.getString("CPF_RAZAO"));
                regFinaliza.setNomeAvalista(rsFinalizaRegistro.getString("NOME_AVAL"));
                regFinaliza.setNumeroDoc(rsFinalizaRegistro.getString("NUMERO_DOC"));
                regFinaliza.setValor(rsFinalizaRegistro.getDouble("VALOR"));
                regFinaliza.setCodAvalista(rsFinalizaRegistro.getString("AVALISTA").trim());
                regFinaliza.setDataVencimento(rsFinalizaRegistro.getDate("VENC"));
                regFinaliza.setDescricaoRetorno(rsFinalizaRegistro.getString("DESCRICAO"));
                regFinaliza.setStatus(rsFinalizaRegistro.getString("STATUS"));
                regFinaliza.setStatusSpc(rsFinalizaRegistro.getString("STATUS_SPC"));
                regFinaliza.setStatusSpcAval(rsFinalizaRegistro.getString("STATUS_SPC_AVAL"));
                regFinaliza.setStatusFacmat(rsFinalizaRegistro.getString("STATUS_FACMAT"));
            } else {
                regFinaliza.setDescricaoRetorno("N");
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return regFinaliza;

    }

    public void finalizarRegistro(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
        String statusSpc = regFinalizar.getStatusSpc();
        String statusSpcAval = regFinalizar.getStatusSpcAval();
        String statusFacmat = regFinalizar.getStatusFacmat();
        String status = regFinalizar.getStatus();

        try {
            /* Verifica se possuí avalista. */
            if (regFinalizar.getCodAvalista().isEmpty()) {

                /* Se todos os registros da inclusão for de ERRO ou Pendente, vamos finalizar o registro com Finalizado Erro FE.  */
                if ((statusSpc.equals("E") && statusFacmat.equals("E"))
                        || (statusSpc.equals("P") && statusFacmat.equals("P"))) {

                    finalizaInclusaoPendente(regFinalizar, idExtracao);

                } else if ((statusFacmat.equals("E") || statusSpc.equals("E"))) {

                    /* Apenas um dos provedores foi enviado. */
                    if (statusFacmat.equals("E")) {

                        finalizaInclusaoPendenteFacmat(regFinalizar, idExtracao);
                    }
                    if (statusSpc.equals("E")) {

                        finalizaInclusaoPendenteSpc(regFinalizar, idExtracao);
                    }

                }
            } else if (!regFinalizar.getCodAvalista().isEmpty()) {

                /* Se todos os registros da inclusão for de ERRO ou Pendente, vamos finalizar o registro com Finalizado Erro FE.  */
                if ((statusSpc.equals("E") && statusSpcAval.equals("E") && statusFacmat.equals("E"))
                        || (statusSpc.equals("P") && statusFacmat.equals("P") && statusSpcAval.equals("P"))) {
                    finalizaInclusaoPendente(regFinalizar, idExtracao);

                } else if ((statusFacmat.equals("E") || statusSpc.equals("E") || statusSpcAval.equals("E"))) {
                    /* Apenas um dos provedores foi enviado. */
                    if (statusFacmat.equals("E")) {

                        finalizaInclusaoPendenteFacmat(regFinalizar, idExtracao);
                    }
                    if (statusSpc.equals("E")) {
                        finalizaInclusaoPendenteSpc(regFinalizar, idExtracao);
                    }
                    if (statusSpcAval.equals("E")) {
                        finalizaInclusaoPendenteSpcAval(regFinalizar, idExtracao);
                    }

                }
            }
            String sqlFinaizaRegistro = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, ORIGEM, STATUS) "
                    + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";

            psFinalizaRegistro = Conexao.getConnection().prepareStatement(sqlFinaizaRegistro);
            psFinalizaRegistro.setInt(1, idExtracao);
            psFinalizaRegistro.setInt(2, regFinalizar.getIndice());
            psFinalizaRegistro.setDate(3, Utilitarios.converteData(new Date()));
            psFinalizaRegistro.setString(4, regFinalizar.getDescricaoRetorno());
            psFinalizaRegistro.setString(5, "E");
            psFinalizaRegistro.setString(6, "E");
            psFinalizaRegistro.executeQuery();
            Conexao.getConnection().commit();

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendente(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
        String updateStatusInclusaoPendente = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC = 'FE', STATUS_SPC_AVAL = 'FE', STATUS_FACMAT = 'FE' WHERE ID_EXTRATOR = " + idExtracao + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendente);
            psFinalizaInclusaoPendente.execute();
            Conexao.getConnection().commit();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteFacmat(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_FACMAT = 'FE' WHERE ID_EXTRATOR = " + idExtracao + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteSpc(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC = 'FE' WHERE ID_EXTRATOR = " + idExtracao + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteSpcAval(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC_AVAL = 'FE' WHERE ID_EXTRATOR = " + idExtracao + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }
}
