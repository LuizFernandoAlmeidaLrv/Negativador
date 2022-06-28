/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.RecebimentoApp;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RecebimentoAppDAO {

    private PreparedStatement psSelectRec, psSelectItens;
    private ResultSet rsSelectRec, rsSelectItens;
    private List<RecebimentoApp> lRecebimentoApp;
    private List<ParcelaModel> lParcelas;
    private RecebimentoApp recebimentoApp;
    private ParcelaModel parcelas;

    public List<RecebimentoApp> buscarRecebimento(RecebimentoApp recebimentoApp) throws ErroSistemaException {

        lRecebimentoApp = new LinkedList<>();
        String sqlSelect = "Select * From INTEGRACAO_RECEBIMENTO.RECEBIMENTOS REC, SAPIENS.E085CLI CLIENTE WHERE REC.USUARIO = CLIENTE.CGCCPF " + getWhere(recebimentoApp) + " ORDER BY TO_DATE(DATA_GERACAO), NOMCLI";
        System.out.println("Select Recebimento = " + sqlSelect);
        try (PreparedStatement psSelectRec = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelectRec = psSelectRec.executeQuery();
            while (rsSelectRec.next()) {
                recebimentoApp = new RecebimentoApp();
                recebimentoApp.setIdRecebimento(rsSelectRec.getInt("ID_RECEBIMENTO"));
                recebimentoApp.setCgcCpf(rsSelectRec.getString("CGCCPF"));
                recebimentoApp.setNomeCliente(rsSelectRec.getString("NOMCLI"));
                recebimentoApp.setBandeira(rsSelectRec.getString("BANDEIRA_CARTAO"));
                recebimentoApp.setDataGerInicio(rsSelectRec.getString("DATA_GERACAO"));
                recebimentoApp.setDataConInicio(rsSelectRec.getString("DATA_CONCILIACAO"));
                recebimentoApp.setDataRecInicio(rsSelectRec.getString("DATA_RECEBIMENTO"));
                recebimentoApp.setDataVenInicio(rsSelectRec.getString("DATA_VENCIMENTO_BOLETO"));
                recebimentoApp.setValor(rsSelectRec.getDouble("VALOR_RECEBIDO"));
                recebimentoApp.setSitRecebimento(rsSelectRec.getString("SITUACAO_RECEBIMENTO"));
                recebimentoApp.setFormaPagamento(rsSelectRec.getString("FORMA_PAGAMENTO"));
                recebimentoApp.setDocumento(rsSelectRec.getString("DOCUMENTO_AUTORIZACAO"));
                recebimentoApp.setCodigoAutorizacao(rsSelectRec.getString("CODIGO_AUTORIZACAO"));

                recebimentoApp.setlRecebimentoAppParcela(buscarParcelasRecebimento(rsSelectRec.getInt("ID_RECEBIMENTO")));

                lRecebimentoApp.add(recebimentoApp);

            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {

        }
        return lRecebimentoApp;

    }

    private String getWhere(RecebimentoApp recebimentoApp) {
        String where = "";

        if (recebimentoApp.getDataVenInicio() != null && recebimentoApp.getDataVenFim() != null) {
            where += ((recebimentoApp.getDataVenInicio() != null) ? " AND REC.DATA_VENCIMENTO_BOLETO  BETWEEN '" + recebimentoApp.getDataVenInicio() + "'" : "");
            where += ((recebimentoApp.getDataVenFim() != null) ? " AND '" + recebimentoApp.getDataVenFim() + "'" : "");
        }
        if (recebimentoApp.getDataRecInicio() != null && recebimentoApp.getDataRecFim() != null) {
            if (recebimentoApp.getDataRecInicio().equals("31/12/1900")) {
                where += ((recebimentoApp.getDataRecInicio() != null) ? " AND (REC.DATA_RECEBIMENTO = '31/12/1900' OR (REC.DATA_RECEBIMENTO IS NULL))" : "");
            } else {
                where += ((recebimentoApp.getDataRecInicio() != null) ? " AND REC.DATA_RECEBIMENTO BETWEEN '" + recebimentoApp.getDataRecInicio() + "'" : "");
                where += ((recebimentoApp.getDataRecFim() != null) ? " AND '" + recebimentoApp.getDataRecFim() + "'" : "");
            }
        }
        if (recebimentoApp.getDataGerInicio() != null && recebimentoApp.getDataGerFim() != null) {
            where += ((recebimentoApp.getDataGerInicio() != null) ? " AND REC.DATA_GERACAO BETWEEN '" + recebimentoApp.getDataGerInicio() + "'" : "");
            where += ((recebimentoApp.getDataGerFim() != null) ? " AND '" + recebimentoApp.getDataGerFim() + "'" : "");
        }
        if (recebimentoApp.getDataConInicio() != null && recebimentoApp.getDataConFim() != null) {
            if (recebimentoApp.getDataConInicio().equals("31/12/1900")) {
                where += ((recebimentoApp.getDataConInicio() != null) ? " AND (REC.DATA_CONCILIACAO = '31/12/1900' OR (REC.DATA_CONCILIACAO IS NULL))" : "");
            } else {
                where += ((recebimentoApp.getDataConInicio() != null) ? " AND REC.DATA_CONCILIACAO BETWEEN '" + recebimentoApp.getDataConInicio() + "'" : "");
                where += ((recebimentoApp.getDataConFim() != null) ? " AND '" + recebimentoApp.getDataConFim() + "'" : "");
            }
        }
        if (!recebimentoApp.getBandeira().equalsIgnoreCase("")) {
            where += ((recebimentoApp.getBandeira() != null) ? " AND REC.BANDEIRA_CARTAO = '" + recebimentoApp.getBandeira() + "'" : "");
        }
        if (!recebimentoApp.getSitRecebimento().equalsIgnoreCase("")) {
            where += ((recebimentoApp.getSitRecebimento() != null) ? " AND REC.SITUACAO_RECEBIMENTO = '" + recebimentoApp.getSitRecebimento() + "'" : "");
        }
        if (!recebimentoApp.getFormaPagamento().equalsIgnoreCase("")) {
            where += ((recebimentoApp.getFormaPagamento() != null) ? " AND REC.FORMA_PAGAMENTO = '" + recebimentoApp.getFormaPagamento() + "'" : "");
        }
        if (!recebimentoApp.getDocumento().equalsIgnoreCase("")) {
            where += ((recebimentoApp.getDocumento() != null) ? " AND REC.DOCUMENTO_AUTORIZACAO = '" + recebimentoApp.getDocumento() + "'" : "");
        }
        if (!recebimentoApp.getCodigoAutorizacao().equalsIgnoreCase("")) {
            where += ((recebimentoApp.getCodigoAutorizacao() != null) ? " AND REC.CODIGO_AUTORIZACAO = '" + recebimentoApp.getCodigoAutorizacao() + "'" : "");
        }
        if (recebimentoApp.getCodigoCliente() > 0) {
            where += " AND CLIENTE.CODCLI = " + recebimentoApp.getCodigoCliente();
        }
        if (recebimentoApp.getIdRecebimento() > 0) {
            where += " AND REC.ID_RECEBIMENTO = " + recebimentoApp.getIdRecebimento();
        }
        if (recebimentoApp.getCgcCpf() != null) {
            long cpf = Long.parseLong(recebimentoApp.getCgcCpf());
            where += " AND CLIENTE.CGCCPF = " + cpf;
        }
        if (recebimentoApp.getNomeCliente() != null) {
            where += ((recebimentoApp.getNomeCliente() != null) ? " AND CLIENTE.NOMCLI LIKE '" + recebimentoApp.getNomeCliente() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " AND  0 = 0 " + where;
        }
        return where;
    }

    private List<ParcelaModel> buscarParcelasRecebimento(int aInt) throws ErroSistemaException {

        lParcelas = new LinkedList<>();
        String sqlSelectParc = "Select * From INTEGRACAO_RECEBIMENTO.RECEBIMENTOS_PARCELAS WHERE RECEBIMENTO = ?";
        try (PreparedStatement psSelectItens = Conexao.getConnection().prepareStatement(sqlSelectParc)) {
            psSelectItens.setInt(1, aInt);
            rsSelectItens = psSelectItens.executeQuery();
            while (rsSelectItens.next()) {
                parcelas = new ParcelaModel();
                parcelas.setPontoFilial(rsSelectItens.getString("LOJA_RETORNO"));
                parcelas.setNumeroDoContrato(rsSelectItens.getString("ID_CARNE"));
                parcelas.setDataLancamento(rsSelectItens.getDate("DATA_LANCAMENTO"));
                parcelas.setDataDoRetorno(rsSelectItens.getDate("DATA_MOV_RETORNAR"));
                parcelas.setDataVencimento(rsSelectItens.getDate("VENCIMENTO"));
                parcelas.setDataPagamento(rsSelectItens.getDate("DATA_PAGAMENTO"));
                parcelas.setValorParcela(rsSelectItens.getDouble("VALOR_ORIGINAL"));
                parcelas.setValorCalc(rsSelectItens.getDouble("VALOR_CALCULADO"));
                parcelas.setValorPago(rsSelectItens.getDouble("VALOR_RECEBIDO"));
                parcelas.setNumeroParcela(rsSelectItens.getString("NUMERO_PARCELA"));
                parcelas.setCodfil(rsSelectItens.getInt("ID_FILIAL"));
                parcelas.setIdParcela(rsSelectItens.getString("ID_RECEBIMENTO_PARCELA"));
                lParcelas.add(parcelas);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lParcelas;
    }

    public void gerarPendenciaRecApp(ParcelaModel parcelaApp) throws ErroSistemaException {
        String sChave, sOperacao, sProcesso;
        sChave = "idRecebimentoParcela\":\"" + parcelaApp.getIdParcela();
        String sqlSeleqt = "Select ID_RECEBIMENTO_PARCELA \n"
                + "                       From INTEGRACAO_RECEBIMENTO.RECEBIMENTOS_PARCELAS P ,INTEGRACAO_RECEBIMENTO.RECEBIMENTOS R \n"
                + "                       Where R.ID_RECEBIMENTO = P.RECEBIMENTO \n"
                + "                         And P.DATA_MOV_RETORNAR = '" + Utilitarios.dataParaString(parcelaApp.getDataDoRetorno()) + "' \n"
                + "                         And P.LOJA_RETORNO = " + parcelaApp.getPontoFilial() + "  \n"
                + "                         And R.FORMA_PAGAMENTO = 'C' \n"
                + "                         And R.SITUACAO_RECEBIMENTO = 'R' \n"
                + "                         And Not Exists (select 1 from INTEGRACAOFL.PENDENCIAS WHERE CHAVE LIKE '%"+sChave+"%')";

        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSeleqt)) {
            rsSelectRec = psSelect.executeQuery();
            if (rsSelectRec.next()) {
                String sqlGerPendencia = "";
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.delete(0, stringBuffer.length());
                stringBuffer.append("{call SAPIENS.SP_GERAR_PENDENCIA_V3(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                sqlGerPendencia = stringBuffer.toString();              
                sChave = "{\"idRecebimentoParcela\":\"" + parcelaApp.getIdParcela() + "\"}";
                sProcesso = "RECEBIMENTO_PARCELA_CARTAO_APP";

                String sFila = "ERP";
                try (CallableStatement callableStatement = Conexao.getConnection().prepareCall(sqlGerPendencia)) {
                    int index = 1;
                    callableStatement.setString(index++, sChave);
                    callableStatement.setString(index++, "I");
                    callableStatement.setInt(index++, 0);
                    callableStatement.setString(index++, sProcesso);
                    callableStatement.setString(index++, "F");
                    callableStatement.setInt(index++, parcelaApp.getCodfil());
                    callableStatement.setString(index++, "N");
                    callableStatement.setString(index++, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                    callableStatement.setString(index++, sFila);
                    callableStatement.setInt(index++, 0);
                    int retorno = callableStatement.executeUpdate();
                    callableStatement.getResultSet();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

}
