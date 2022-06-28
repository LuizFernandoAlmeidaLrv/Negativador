/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
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
public class NotificacaoDAO {

    private List<FilialModel> ListLojasErro = new ArrayList();
    private List<ParcelasEnviarModel> ListRegistrosComErro = new ArrayList();
    private PreparedStatement psListLoja, psListErro;
    private ResultSet rsListErro, rsListLoja;

    public NotificacaoDAO() {

    }

    public List<FilialModel> ListLojasComErro() throws ErroSistemaException {
        try {
            ListLojasErro = new ArrayList();
            psListLoja = Conexao.getConnection().prepareStatement(getSqlSelectLojasErro());
            rsListLoja = psListLoja.executeQuery();
            while (rsListLoja.next()) {
                FilialModel filial = new FilialModel();
                filial.setPontoFilial(rsListLoja.getString("ID_FILIAL"));
                filial.setEmail(rsListLoja.getString("EMAIL"));
                ListLojasErro.add(filial);
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return ListLojasErro;
    }

    public List<ParcelasEnviarModel> ListRegistrosComErro(String filial) throws ErroSistemaException {
        try {
            ListRegistrosComErro = new ArrayList();
            psListErro = Conexao.getConnection().prepareStatement(getSqlSelectRegistrosErro());
            psListErro.setString(1, filial);
            rsListErro = psListErro.executeQuery();
            while (rsListErro.next()) {
                ParcelasEnviarModel registro = new ParcelasEnviarModel();
                registro.setPontoFilial(rsListErro.getString("ID_FILIAL"));
                registro.setCodCliente(rsListErro.getString("CLIENTE"));
                registro.setNomeRazaoSocial(rsListErro.getString("NOME"));
                registro.setCpfCnpj(rsListErro.getString("CPF_RAZAO"));
                if (rsListErro.getString("NOME_AVAL") != null) {
                    registro.setNomeRazaoSocialAval(rsListErro.getString("NOME_AVAL"));
                } else {
                    registro.setNomeRazaoSocialAval("");
                }
                registro.setNumeroDoContrato(rsListErro.getString("NUMERO_DOC"));
                registro.setValorParcela(rsListErro.getBigDecimal("VALOR"));
                registro.setStatusSpc(rsListErro.getString("DESCRICAO"));
                registro.setDataVencimento(rsListErro.getDate("VENC"));
                registro.setAvalista(rsListErro.getString("AVALISTA"));
                ListRegistrosComErro.add(registro);
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return ListRegistrosComErro;
    }

    public String getSqlSelectLojasErro() {
        String sqlSelectLojasErro = "SELECT FILIAIS.ID_FILIAL, FILIAIS.EMAIL\n"
                + "  FROM FILIAIS, PARCELA P\n"
                + " WHERE FILIAIS.ID_FILIAL = P.ID_FILIAL\n"
                + "   AND EXISTS\n"
                + " (SELECT 1\n"
                + "          FROM PARCELA, EXTRATOR, RETORNOS_INTEGRACAO, LOG_EXTRATOR\n"
                + "         WHERE PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "           AND PARCELA.ID_PARCELA = P.ID_PARCELA\n"
                + "           AND EXTRATOR.STATUS IN ('EP', 'I')\n"
                + "           AND LOG_EXTRATOR.ID_EXTRATOR = EXTRATOR.ID_EXTRATOR\n"
                + "           AND RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "           AND ID_LOG =\n"
                + "               (SELECT MAX(ID_LOG)\n"
                + "                  FROM LOG_EXTRATOR LOG1\n"
                + "                 WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                   AND RETORNOS_INTEGRACAO.TIPO IN ('C', 'B', 'S', 'E')\n"
                + "                   AND LOG1.STATUS = 'E')\n"
                + "           AND RETORNOS_INTEGRACAO.NOTIFICAR = 'S')\n"
                + " GROUP BY FILIAIS.ID_FILIAL, FILIAIS.EMAIL";
        return sqlSelectLojasErro;
    }

    public String getSqlSelectRegistrosErro() {
        String sqlSelectRegistrosErro = " SELECT PARCELA.ID_FILIAL,\n"
                + "                 PARCELA.CLIENTE,\n"
                + "                 PESSOA.NOME,\n"
                + "                 PESSOA.CPF_RAZAO,\n"
                + "                 AVALISTA.NOME AS NOME_AVAL,\n"
                + "                 PARCELA.NUMERO_DOC,\n"
                + "                 PARCELA.VALOR,\n"
                + "                 PARCELA.AVALISTA,\n"
                + "                 PARCELA.VENC,\n"
                + "                 RETORNOS_INTEGRACAO.DESCRICAO\n"
                + "            FROM PARCELA\n"
                + "           INNER JOIN EXTRATOR\n"
                + "              ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "           INNER JOIN LOG_EXTRATOR\n"
                + "              ON LOG_EXTRATOR.ID_EXTRATOR = EXTRATOR.ID_EXTRATOR\n"
                + "           INNER JOIN RETORNOS_INTEGRACAO\n"
                + "              ON RETORNOS_INTEGRACAO.ID_RETORNO =\n"
                + "                 LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "           INNER JOIN PESSOA\n"
                + "              ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "            LEFT JOIN PESSOA AVALISTA\n"
                + "              ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + "           WHERE EXTRATOR.STATUS IN ('EP', 'I')\n"
                + "                       AND ID_LOG =\n"
                + "                 (SELECT MAX(ID_LOG)\n"
                + "                    FROM LOG_EXTRATOR LOG1\n"
                + "                   WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                     AND RETORNOS_INTEGRACAO.TIPO in ('C', 'B', 'S', 'E')\n"
                + "                     AND LOG1.STATUS = 'E')\n"
                + "             AND RETORNOS_INTEGRACAO.NOTIFICAR = 'S'\n"
                + "             AND PARCELA.ID_FILIAL = ?";

        return sqlSelectRegistrosErro;

    }

}
