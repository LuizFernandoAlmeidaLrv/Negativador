/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
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
public class ExportarExcelDAO {

    private PreparedStatement psStatus, psMovimentoDiario;
    private ResultSet rsStatus, rsMovimentoDiario;
    private List<ExtracaoTableModel> lMovimentoDiario;
    private ExtracaoTableModel movimentoDiarioModel;

    public ExportarExcelDAO() {

    }

    public void statusRegistros(String statusExtracao, String statusSpc, String statusFacmat, String idExtrator) throws ErroSistemaException {
        String selectStausSpc = "SELECT *FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + "// WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "//   AND ID_EXTRATOR = " + idExtrator + "\n"
                + "//   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "//                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "//                    AND TIPO = 'S')";
        
        String selectStausExtracao = "SELECT *FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + "// WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "//   AND ID_EXTRATOR = " + idExtrator + "\n"
                + "//   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "//                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "//                    AND TIPO = 'E')";
        
        String selectStausFacmat = "SELECT *FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + "// WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "//   AND ID_EXTRATOR = " + idExtrator + "\n"
                + "//   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "//                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "//                    AND TIPO IN ('B', 'C'))";
        
        try {
            psStatus = Conexao.getConnection().prepareStatement(selectStausSpc);
            rsStatus = psStatus.executeQuery();
            if (rsStatus.next()) {
                statusSpc = rsStatus.getString("DESCRICAO");
            }
            psStatus.close();
            rsStatus.close();
            psStatus = Conexao.getConnection().prepareStatement(selectStausFacmat);
            rsStatus = psStatus.executeQuery();
            if (rsStatus.next()) {
                statusFacmat = rsStatus.getString("DESCRICAO");
            }
            psStatus.close();
            rsStatus.close();
            psStatus = Conexao.getConnection().prepareStatement(selectStausExtracao);
            rsStatus = psStatus.executeQuery();
            if (rsStatus.next()) {
                statusExtracao = rsStatus.getString("DESCRICAO");
            }
            psStatus.close();
            rsStatus.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
             throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
             throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public List<ExtracaoTableModel> MovimentoDiario() throws ErroSistemaException {
        lMovimentoDiario = new ArrayList<>();
        String movimentoDiario = "SELECT PARCELA.ID_FILIAL,\n"
                + "       PARCELA.CLIENTE,\n"
                + "       PESSOA.NOME,\n"
                + "       PESSOA.CPF_RAZAO,\n"
                + "       CASE\n"
                + "         WHEN (TIPO) = 'I' THEN\n"
                + "          'N'\n"
                + "         WHEN (TIPO) = 'B' THEN\n"
                + "          'P'\n"
                + "       END PAGAMENTO,\n"
                + "       EXTRATOR.STATUS,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.DATALAN,\n"
                + "       PARCELA.VENC,\n"
                + "       PARCELA.VALOR,\n"
                + "       AVALISTA.NOME AS NOME_AVALISTA,\n"
                + "       PARCELA.DATAPAG     \n"
                + "  FROM PARCELA\n"
                + " INNER JOIN PESSOA\n"
                + "    ON PARCELA.ID_CLIENTE = PESSOA.ID_PESSOA\n"
                + " LEFT OUTER JOIN PESSOA AVALISTA\n"
                + "    ON PARCELA.ID_AVALISTA = AVALISTA.ID_PESSOA\n"
                + " INNER JOIN EXTRATOR\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " WHERE TO_DATE(EXTRATOR.DATA_EXTRACAO) = TO_DATE(SYSDATE)";

        try {
            psMovimentoDiario = Conexao.getConnection().prepareStatement(movimentoDiario);
            rsMovimentoDiario = psMovimentoDiario.executeQuery();
            while (rsMovimentoDiario.next()) {
                movimentoDiarioModel = new ExtracaoTableModel();
                movimentoDiarioModel.setIdFilial(rsMovimentoDiario.getString("ID_FILIAL"));
                movimentoDiarioModel.setCodigoCliente(rsMovimentoDiario.getString("CLIENTE"));
                movimentoDiarioModel.setNome(rsMovimentoDiario.getString("NOME"));
                movimentoDiarioModel.setCpfCnpj(rsMovimentoDiario.getString("CPF_RAZAO"));
                movimentoDiarioModel.setPago(rsMovimentoDiario.getString("PAGAMENTO"));
                movimentoDiarioModel.setStatus(rsMovimentoDiario.getString("STATUS"));
                movimentoDiarioModel.setNumeroDoc(rsMovimentoDiario.getString("NUMERO_DOC"));
                movimentoDiarioModel.setDataLancamento(rsMovimentoDiario.getDate("DATALAN"));
                movimentoDiarioModel.setDataVencimento(rsMovimentoDiario.getDate("VENC"));
                movimentoDiarioModel.setValor(rsMovimentoDiario.getDouble("VALOR"));
                movimentoDiarioModel.setNomeAvalista(rsMovimentoDiario.getString("NOME_AVALISTA"));
                movimentoDiarioModel.setDataPagamento(rsMovimentoDiario.getDate("DATAPAG"));
                lMovimentoDiario.add(movimentoDiarioModel);

            }
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
        return lMovimentoDiario;

    }

}
