/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luiz.almeida
 */
public class RetornoEnvioDAO {

    private PreparedStatement psAtualizaStatus, psBuscaRegistro, psRetornoContasReceber, psSelectProcessados, psRetornoExtrator, psSelectAprocessar, psLogExtrator, psUpdateParcela, psGerPendencia;
    private ResultSet rsAtualizaStatus, rsBuscaRegistro, rsRetornoContasReceber, rsSelectProcessados, rsRetornoExtrator, rsSelectAprocessar, rsGerPendencia;
    private String sqlAtualizaStatus, sqlSelectStatus, sqlSelectProcessados, sqlUpdateContasReceberInc, sqlUpdateContasReceberBaix, sqlUpdateExtratorFinal, sqlUpdateExtratorFinalPp,
            sqlSelectAprocessar, sqlUpdateExtratorFinalSemData, sqlUpdateInclusaoE301TCR, sqlUpdateBaixaE301TCR, sqlUpdateE085CLI, sqlValidaSitCli, sqlGerPendencia;
    private volatile String status, statusSpc, statusSpcA, statusFacmat;
    private volatile int numStatusSpc, numStatusSpcA, numStatusFacmat, numSoma;
    private int resultado;
    private int idExtrator;
    private int codigo;
    private String msgLog;
    private String updateParcelaBaixa, updateParcelaInclusao;

    public RetornoEnvioDAO() {
        sqlSelectStatus = "SELECT EXTRATOR.STATUS,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.INCLUIR_AVAL,\n"
                + "       PARCELA.ID_REGISTRO_BVS,\n"
                + "       EXTRATOR.STATUS_SPC,\n"
                + "       EXTRATOR.TIPO,\n"
                + "       EXTRATOR.STATUS_FACMAT,\n"
                + "       EXTRATOR.STATUS_SPC_AVAL,\n"
                + "       EXTRATOR.DATA_SPC,\n"
                + "       EXTRATOR.DATA_SPC_AVALISTA,\n"
                + "       EXTRATOR.DATA_FACMAT\n"
                + "  FROM EXTRATOR\n"
                + " INNER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " WHERE ID_EXTRATOR = ?\n"
                + " FOR UPDATE";

        sqlAtualizaStatus = ("UPDATE EXTRATOR SET STATUS = ? WHERE ID_EXTRATOR = ?");

        sqlSelectAprocessar = "SELECT EXTRATOR.ID_EXTRATOR,"
                + "       EXTRATOR.STATUS,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.CLIENTE,"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       EXTRATOR.STATUS_SPC,\n"
                + "       EXTRATOR.STATUS_FACMAT,\n"
                + "       EXTRATOR.STATUS_SPC_AVAL,\n"
                + "       EXTRATOR.DATA_SPC,\n"
                + "       EXTRATOR.DATA_SPC_AVALISTA,\n"
                + "       EXTRATOR.DATA_FACMAT,"
                + "       EXTRATOR.TIPO,"
                + "       PESSOA.CPF_RAZAO,"
                + "       FILIAIS.FILIAL_SGL\n"
                + "  FROM EXTRATOR\n"
                + " INNER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA"
                + "INNER JOIN FILIAIS"
                + "ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL"
                + "INNER JOIN PESSOA"
                + "ON PESSOA.ID_CLIENTE = PARCELA.ID_CLIENTE"
                + "WHERE EXTRATOR.STATUS IN('P', 'PP', 'PE', 'I') AND EXTRATOR.DATA_RETORNO <> '31/12/1900'\n";

    }

    public int sinclonizaRetorno(int idExtrator) throws ErroSistemaException {

        try {
            psAtualizaStatus = Conexao.getConnection().prepareStatement(sqlAtualizaStatus);
            psBuscaRegistro = Conexao.getConnection().prepareStatement(sqlSelectStatus);
            psBuscaRegistro.setInt(1, idExtrator);
            rsBuscaRegistro = psBuscaRegistro.executeQuery();
            if (rsBuscaRegistro.next()) {
                statusSpc = rsBuscaRegistro.getString("STATUS_SPC");
                statusSpcA = rsBuscaRegistro.getString("STATUS_SPC_AVAL");
                statusFacmat = rsBuscaRegistro.getString("STATUS_FACMAT");
                int idRegistroBvs = rsBuscaRegistro.getInt("ID_REGISTRO_BVS");
                int avalista = rsBuscaRegistro.getInt("ID_AVALISTA");
                if (rsBuscaRegistro.getString("TIPO").equals("I") || idRegistroBvs != 0) {
                    if ((avalista == 0) || (rsBuscaRegistro.getInt("ID_AVALISTA") > 0 && rsBuscaRegistro.getString("INCLUIR_AVAL").equalsIgnoreCase("N"))) {
                        if (statusSpc.equalsIgnoreCase("E") || statusFacmat.equalsIgnoreCase("E")) {
                            status = "EP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if ((!statusSpc.equalsIgnoreCase("P") || !statusFacmat.equalsIgnoreCase("P")) && (!statusSpc.equalsIgnoreCase("S") || !statusFacmat.equalsIgnoreCase("S"))) {
                            status = "PP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if (statusSpc.equalsIgnoreCase("S") && statusFacmat.equalsIgnoreCase("S")) {
                            status = "PR";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        }

                    } else {
                        if (statusSpc.equalsIgnoreCase("E") || statusFacmat.equalsIgnoreCase("E") || statusSpcA.equalsIgnoreCase("E")) {
                            status = "EP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if ((!statusSpcA.equalsIgnoreCase("P") || !statusSpc.equalsIgnoreCase("P") || !statusFacmat.equalsIgnoreCase("P")) && (!statusSpc.equalsIgnoreCase("S") || !statusFacmat.equalsIgnoreCase("S") || !statusSpcA.equalsIgnoreCase("S"))) {
                            status = "PP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if (statusSpc.equalsIgnoreCase("S") && statusFacmat.equalsIgnoreCase("S") && statusSpcA.equalsIgnoreCase("S")) {
                            status = "PR";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        }

                    }
                } else {
                    if ((avalista == 0) || (rsBuscaRegistro.getInt("ID_AVALISTA") > 0 && rsBuscaRegistro.getString("INCLUIR_AVAL").equalsIgnoreCase("N"))) {
                        if (statusSpc.equalsIgnoreCase("E")) {
                            status = "EP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if ((!statusSpc.equalsIgnoreCase("P")) && (!statusSpc.equalsIgnoreCase("S"))) {
                            status = "PP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if (statusSpc.equalsIgnoreCase("S")) {
                            status = "PR";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        }

                    } else {
                        if (statusSpc.equalsIgnoreCase("E") || statusSpcA.equalsIgnoreCase("E")) {
                            status = "EP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if ((!statusSpcA.equalsIgnoreCase("P") || !statusSpc.equalsIgnoreCase("P")) && (!statusSpc.equalsIgnoreCase("S") || !statusSpcA.equalsIgnoreCase("S"))) {
                            status = "PP";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        } else if (statusSpc.equalsIgnoreCase("S") && statusSpcA.equalsIgnoreCase("S")) {
                            status = "PR";
                            psAtualizaStatus.setString(1, status);
                            psAtualizaStatus.setInt(2, idExtrator);
                            psAtualizaStatus.execute();
                        }

                    }
                }
            }
            resultado = psAtualizaStatus.executeUpdate();
            if (resultado == -1) {
                psAtualizaStatus.close();
                psBuscaRegistro.close();
                rsBuscaRegistro.close();
            } else if (resultado == 1) {
                Conexao.getConnection().commit();
                psAtualizaStatus.close();
                psBuscaRegistro.close();
                rsBuscaRegistro.close();
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return 0;
    }

    public void retornoContasReceber() throws ErroSistemaException {
        /**
         * Gera o retorno das negativações ao contas Receber atravéis da ação
         * gerar retorno. Esse processo é feito diariamente após o envio dos
         * registro aos provedores e conferencia pela equipe de crédito
         * cobrança.
         */
        sqlSelectProcessados = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "       EXTRATOR.STATUS,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.CLIENTE,\n"
                + "       PARCELA.ID_PARCELA,\n"
                + "       PARCELA.VENC,\n"
                + "       CASE \n"
                + "         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' AND PARCELA.DATALAN > FILIAIS.DATA_INICIO_DBNOVO THEN\n"
                + "          PARCELA.NUMERO_DOC \n"
                + "         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' AND PARCELA.DATALAN < FILIAIS.DATA_INICIO_DBNOVO THEN \n"
                + "         LPAD(PARCELA.NUMERO_DOC, 12, '0')\n"
                + "         WHEN FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' THEN\n"
                + "         PARCELA.NUMERO_DOC\n"
                + "         END NUMERO_DOC,\n"
                + "       PARCELA.VALOR,\n"
                + "       PARCELA.CAPITPAG,\n"
                + "       PARCELA.NUMTIT,\n"
                + "       PARCELA.USU_IDETCR,\n"
                + "       PARCELA.CODTPT,\n"
                + "       EXTRATOR.STATUS_SPC,\n"
                + "       EXTRATOR.STATUS_FACMAT,\n"
                + "       EXTRATOR.STATUS_SPC_AVAL,\n"
                + "       EXTRATOR.DATA_SPC,\n"
                + "       EXTRATOR.DATA_SPC_AVALISTA,\n"
                + "       EXTRATOR.DATA_FACMAT,\n"
                + "       EXTRATOR.TIPO,\n"
                + "       EXTRATOR.DATA_RETORNO,\n"
                + "       PESSOA.CPF_RAZAO,\n"
                + "       FILIAIS.FILIAL_SGL,\n"
                + "       FILIAIS.CODFIL,\n"
                + "       CASE \n"
                + "         WHEN FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' THEN \n"
                + "         'SGL'\n"
                + "         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' THEN \n"
                + "         'SGLM'\n"
                + "       END ORIGEM \n"
                + "  FROM EXTRATOR\n"
                + " INNER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + " INNER JOIN PESSOA\n"
                + "    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + " WHERE (EXTRATOR.TIPO = 'I' AND EXTRATOR.STATUS NOT IN ('F', 'FE') AND\n"
                + "       (EXTRATOR.DATA_SPC <> '31/12/1900' OR\n"
                + "       EXTRATOR.DATA_SPC_AVALISTA <> '31/12/1900' OR\n"
                + "       EXTRATOR.DATA_FACMAT <> '31/12/1900') )\n"
                + "    OR (EXTRATOR.TIPO = 'B' AND EXTRATOR.STATUS = 'PR')";

        sqlUpdateContasReceberInc = ("UPDATE DB_INTEGRACAO.MR02\n"
                + "   SET DATA_MOV_RETORNAR = TO_DATE(SYSDATE),\n"
                + "       RETORNADO = 'N',\n"
                + "       NEGATIVADA = ?\n"
                + " WHERE PTO = ?\n"
                + "   AND DOC = ?\n"
                + "   AND CLI = ?");

        sqlUpdateContasReceberBaix = ("UPDATE DB_INTEGRACAO.MR02\n"
                + "   SET DATA_MOV_RETORNAR = TO_DATE(SYSDATE), RETORNADO = 'N', BAIXANEG = ?\n"
                + " WHERE PTO = ?\n"
                + "   AND DOC = ?\n"
                + "   AND CLI = ?");

        sqlUpdateInclusaoE301TCR = ""
                + "     UPDATE SAPIENS.E301TCR \n"
                + "        SET USU_DATNEG = ?,\n"
                + "            USU_DATALT = ?\n"
                + "      WHERE CODFIL = ?\n"
                + "        AND NUMTIT = ?\n"
                + "        AND CODTPT = ?";

        sqlUpdateBaixaE301TCR = ""
                + "     UPDATE SAPIENS.E301TCR \n"
                + "        SET USU_DATBAI = ?,\n"
                + "            USU_DATALT = ?\n"
                + "      WHERE CODFIL = ?\n"
                + "        AND NUMTIT = ?\n"
                + "        AND CODTPT = ?";

        sqlGerPendencia = "INSERT INTO INTEGRACAOFL.PENDENCIAS\n"
                + "      (ID_PENDENCIA,\n"
                + "       CHAVE,\n"
                + "       DATA_GERACAO,\n"
                + "       OBSERVACAO,\n"
                + "       OPERACAO,\n"
                + "       PRIORIDADE,\n"
                + "       SITUACAO,\n"
                + "       PROCESSO,\n"
                + "       USUARIO,\n"
                + "       LOJA,\n"
                + "       TIPO,\n"
                + "       VERSAO,\n"
                + "       DATA_ENVIO,\n"
                + "       DATA_ATUALIZACAO,\n"
                + "       DATA_PROCESSAMENTO)\n"
                + "    VALUES\n"
                + "      (INTEGRACAOFL.SEQ_PENDENCIAS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlUpdateExtratorFinal = ("UPDATE EXTRATOR SET STATUS = 'F', DATA_RETORNO = ? WHERE EXTRATOR.ID_EXTRATOR = ?");

        sqlUpdateExtratorFinalPp = ("UPDATE EXTRATOR SET  DATA_RETORNO = ? WHERE EXTRATOR.ID_EXTRATOR = ? ");

        sqlUpdateExtratorFinalSemData = ("UPDATE EXTRATOR SET STATUS = 'F' WHERE EXTRATOR.ID_EXTRATOR = ? ");

        updateParcelaBaixa = ("UPDATE PARCELA SET PARCELA.DATA_BAIXA = ? WHERE PARCELA.ID_PARCELA = ?");

        updateParcelaInclusao = ("UPDATE PARCELA SET PARCELA.DATA_NEGATIVADA = ? WHERE PARCELA.ID_PARCELA = ?");

        try {
            /**
             * Buscar todas as negativações processadas com Status diferente de
             * ("F", "FE", "I") onde haja pelo menos um registro com sucesso!
             */
            psSelectProcessados = Conexao.getConnection().prepareStatement(sqlSelectProcessados);

            rsSelectProcessados = psSelectProcessados.executeQuery();
            while (rsSelectProcessados.next()) {

                /**
                 * Se o registro é de inclusão e possuí data de retorno, não faz
                 * nada no sapiens. As inclusões retornam data de Negativação ao
                 * contas receber mesmo que tenha sido incluido em apenas um
                 * provedor. Diferentemente das baixas que só retornam ao contas
                 * receber após a baixa em todos os provedores Incluidos
                 * anteriormente. Verifica se o status está PR e finaliza.
                 */
                if (rsSelectProcessados.getDate("DATA_RETORNO").after(Utilitarios.getDataZero())) {

                    if (rsSelectProcessados.getString("STATUS").equalsIgnoreCase("PR")) {
                        psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtratorFinalSemData);
                        psRetornoExtrator.setInt(1, rsSelectProcessados.getInt("ID_EXTRATOR"));
                        psRetornoExtrator.execute();
                        /* Retorno no Extrator, finaliza o registro se todos estiverem OK. */
                    } else {

                    }
                } else {
                    if (rsSelectProcessados.getString("ORIGEM").equalsIgnoreCase("SGL")) {
                        if ((rsSelectProcessados.getString("STATUS").equalsIgnoreCase("PR") && rsSelectProcessados.getString("TIPO").equals("B"))
                                || (rsSelectProcessados.getString("TIPO").equals("I"))) {
                            if (rsSelectProcessados.getString("TIPO").equalsIgnoreCase("I")) {
                                psRetornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateContasReceberInc);
                                psUpdateParcela = Conexao.getConnection().prepareStatement(updateParcelaInclusao);
                            }
                            if (rsSelectProcessados.getString("TIPO").equalsIgnoreCase("B")) {
                                psRetornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateContasReceberBaix);
                                psUpdateParcela = Conexao.getConnection().prepareStatement(updateParcelaBaixa);
                            }

                            psRetornoContasReceber.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                            psRetornoContasReceber.setString(2, rsSelectProcessados.getString("FILIAL_SGL"));
                            psRetornoContasReceber.setString(3, rsSelectProcessados.getString("NUMERO_DOC"));
                            psRetornoContasReceber.setString(4, rsSelectProcessados.getString("CLIENTE"));
                            psRetornoContasReceber.execute();
                            psRetornoContasReceber.close();

                            /**
                             * Atualiza as informações da parcela no Negativador
                             */
                            psUpdateParcela.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                            psUpdateParcela.setInt(2, rsSelectProcessados.getInt("ID_PARCELA"));
                            psUpdateParcela.execute();
                            psUpdateParcela.close();

                        }
                    } else {
                        /**
                         * Retorno na tabela E301TCR e E085CLI das lojas novo
                         * frente de loja. Retornar a data de negativação e
                         * baixa para o novo frente de loja. Gerar pendencia no
                         * sistema de integração para que a informação possa
                         * retornar para a loja.
                         */
                        if ((rsSelectProcessados.getString("STATUS").equalsIgnoreCase("PR") && rsSelectProcessados.getString("TIPO").equals("B"))
                                || (rsSelectProcessados.getString("TIPO").equals("I"))) {
                            if (rsSelectProcessados.getString("TIPO").equalsIgnoreCase("I")) {
                                psRetornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateInclusaoE301TCR);
                                psRetornoContasReceber.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                                psRetornoContasReceber.setString(2, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                                psRetornoContasReceber.setInt(3, rsSelectProcessados.getInt("CODFIL"));
                                psRetornoContasReceber.setString(4, rsSelectProcessados.getString("NUMTIT"));
                                psRetornoContasReceber.setString(5, rsSelectProcessados.getString("CODTPT"));
                                psRetornoContasReceber.execute();
                                psRetornoContasReceber.close();
                                psUpdateParcela = Conexao.getConnection().prepareStatement(updateParcelaInclusao);
                            }
                            if (rsSelectProcessados.getString("TIPO").equalsIgnoreCase("B")) {
                                System.out.println("registro" + rsSelectProcessados.getInt("CODFIL") + "/" + rsSelectProcessados.getString("NUMTIT"));
                                psRetornoContasReceber = Conexao.getConnection().prepareStatement(sqlUpdateBaixaE301TCR);
                                psRetornoContasReceber.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                                psRetornoContasReceber.setString(2, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                                psRetornoContasReceber.setInt(3, rsSelectProcessados.getInt("CODFIL"));
                                psRetornoContasReceber.setString(4, rsSelectProcessados.getString("NUMTIT"));
                                psRetornoContasReceber.setString(5, rsSelectProcessados.getString("CODTPT"));
                                psRetornoContasReceber.execute();
                                psRetornoContasReceber.close();
                                psUpdateParcela = Conexao.getConnection().prepareStatement(updateParcelaBaixa);
                            }

                            /**
                             * Atualiza as informações da parcela no Negativador
                             */
                            psUpdateParcela.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                            psUpdateParcela.setInt(2, rsSelectProcessados.getInt("ID_PARCELA"));
                            psUpdateParcela.execute();
                            psUpdateParcela.close();

                            /**
                             * Gerar Pendencia no Integrador
                             */
                            gerarPendencia();

                        }
                    }

                    /* Retorno no Extrator, finaliza o registro se todos estiverem OK. Senão atualizsa apenas a data. */
                    if (!rsSelectProcessados.getString("STATUS").equalsIgnoreCase("PR")) {
                        psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtratorFinalPp);
                        psRetornoExtrator.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                        psRetornoExtrator.setInt(2, rsSelectProcessados.getInt("ID_EXTRATOR"));
                        psRetornoExtrator.execute();
                        /* Retorno no Extrator, finaliza o registro se todos estiverem OK. */
                    } else {
                        psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtratorFinal);
                        psRetornoExtrator.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                        psRetornoExtrator.setInt(2, rsSelectProcessados.getInt("ID_EXTRATOR"));
                        psRetornoExtrator.execute();
                    }
                    psRetornoExtrator.close();
                    if (rsSelectProcessados.getString("TIPO").equals("I")) {
                        codigo = 354;
                        msgLog = "Registro de Inclusão retornado ao contas receber";
                    } else {
                        codigo = 355;
                        msgLog = "Registro de Baixa retornado ao contas receber";
                    }
                    gravarLogExtrator(rsSelectProcessados.getInt("ID_EXTRATOR"), codigo, msgLog, "S", rsSelectProcessados.getString("TIPO"));
                }
                Conexao.getConnection().commit();

            }
            psSelectProcessados.close();
            rsSelectProcessados.close();

        } catch (ErroSistemaException | SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public int buscaASinclonizar() throws ErroSistemaException {
        try {
            psSelectAprocessar = Conexao.getConnection().prepareStatement(sqlSelectAprocessar);

            rsSelectAprocessar = psSelectAprocessar.executeQuery();
            while (rsSelectAprocessar.next()) {
                idExtrator = rsSelectAprocessar.getInt("ID_EXTRATOR");
                sinclonizaRetorno(idExtrator);

            }
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return 0;

    }

    public void gravarLogExtrator(int idExtrator, int codigo, String msgLog, String statusMsg, String origem) throws ErroSistemaException {
        String sqlInsertLogRetorno = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, STATUS, ORIGEM)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";
        try {
            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
            psLogExtrator.setInt(1, idExtrator);
            psLogExtrator.setInt(2, codigo);
            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
            psLogExtrator.setString(4, msgLog);
            psLogExtrator.setString(5, statusMsg);
            psLogExtrator.setString(6, origem);
            psLogExtrator.execute();
            psLogExtrator.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    private void gerarPendencia() throws ErroSistemaException {
        try {
            sqlGerPendencia = "";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append("{call SAPIENS.SP_GERAR_PENDENCIA_V3(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            sqlGerPendencia = stringBuffer.toString();
            String sChave, sOperacao, sProcesso;
            sChave = "{\"idRegistro\":\"1|" + rsSelectProcessados.getInt("CODFIL") + "|" + rsSelectProcessados.getString("CODTPT") + "|" + rsSelectProcessados.getString("NUMTIT") + "\"}";
            if (rsSelectProcessados.getString("TIPO").equalsIgnoreCase("B")) {
                sProcesso = "NEGATIVADOR_BAIXAR_NEGATIVACAO";
            } else {
                sProcesso = "NEGATIVADOR_INCLUIR_NEGATIVACAO";
            }
            String sFila = "ERP";
            try (CallableStatement callableStatement = Conexao.getConnection().prepareCall(sqlGerPendencia)) {
                int index = 1;
                callableStatement.setString(index++, sChave);
                callableStatement.setString(index++, "I");
                callableStatement.setInt(index++, 0);
                callableStatement.setString(index++, sProcesso);
                callableStatement.setString(index++, "F");
                callableStatement.setInt(index++, rsSelectProcessados.getInt("CODFIL"));
                callableStatement.setString(index++, "S");
                callableStatement.setString(index++, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                callableStatement.setString(index++, sFila);
                callableStatement.setInt(index++, 0);
                int retorno = callableStatement.executeUpdate();
                callableStatement.getResultSet();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void ForcarPendencia() throws ErroSistemaException {
        try {
            String SelectTitulos = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                    + "                       EXTRATOR.STATUS,\n"
                    + "                       PARCELA.ID_AVALISTA,\n"
                    + "                       PARCELA.CLIENTE,\n"
                    + "                       PARCELA.ID_PARCELA,\n"
                    + "                       PARCELA.VENC,\n"
                    + "                       CASE \n"
                    + "                         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' AND PARCELA.DATALAN > FILIAIS.DATA_INICIO_DBNOVO THEN\n"
                    + "                          PARCELA.NUMERO_DOC \n"
                    + "                         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' AND PARCELA.DATALAN < FILIAIS.DATA_INICIO_DBNOVO THEN \n"
                    + "                         LPAD(PARCELA.NUMERO_DOC, 12, '0')\n"
                    + "                         WHEN FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' THEN\n"
                    + "                         PARCELA.NUMERO_DOC\n"
                    + "                         END NUMERO_DOC,\n"
                    + "                       PARCELA.VALOR,\n"
                    + "                       PARCELA.CAPITPAG,\n"
                    + "                       PARCELA.NUMTIT,\n"
                    + "                       PARCELA.USU_IDETCR,\n"
                    + "                       PARCELA.CODTPT,\n"
                    + "                       EXTRATOR.STATUS_SPC,\n"
                    + "                       EXTRATOR.STATUS_FACMAT,\n"
                    + "                       EXTRATOR.STATUS_SPC_AVAL,\n"
                    + "                       EXTRATOR.DATA_SPC,\n"
                    + "                       EXTRATOR.DATA_SPC_AVALISTA,\n"
                    + "                       EXTRATOR.DATA_FACMAT,\n"
                    + "                       EXTRATOR.TIPO,\n"
                    + "                       EXTRATOR.DATA_RETORNO,\n"
                    + "                       PESSOA.CPF_RAZAO,\n"
                    + "                       FILIAIS.FILIAL_SGL,\n"
                    + "                       FILIAIS.CODFIL,\n"
                    + "                       CASE \n"
                    + "                         WHEN FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' THEN \n"
                    + "                         'SGL'\n"
                    + "                         WHEN FILIAIS.DATA_INICIO_DBNOVO <> '31/12/1900' THEN \n"
                    + "                         'SGLM'\n"
                    + "                       END ORIGEM \n"
                    + "                  FROM EXTRATOR\n"
                    + "                 INNER JOIN PARCELA\n"
                    + "                    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                    + "                 INNER JOIN FILIAIS\n"
                    + "                    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                    + "                 INNER JOIN PESSOA\n"
                    + "                    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                    + "                 WHERE (EXTRATOR.TIPO = 'I' AND\n"
                    + "                       (EXTRATOR.DATA_SPC <> '31/12/1900' OR\n"
                    + "                       EXTRATOR.DATA_SPC_AVALISTA <> '31/12/1900' OR\n"
                    + "                       EXTRATOR.DATA_FACMAT <> '31/12/1900') )\n"
                    + "                  AND NOT EXISTS (SELECT 1 FROM INTEGRACAOFL.PENDENCIAS WHERE CHAVE = '{\"idRegistro\":\"1|' || FILIAIS.CODFIL ||'|' || PARCELA.CODTPT ||'|' ||PARCELA.NUMTIT || '\"}' )\n"
                    + "                  AND FILIAIS.DATA_INICIO_DBNOVO > '01/01/2020' AND EXTRATOR.DATA_RETORNO > FILIAIS.DATA_INICIO_DBNOVO AND EXTRATOR.DATA_RETORNO > '10/10/2020'\n"
                    + "                  AND NOT EXISTS (SELECT ID_PARCELA FROM EXTRATOR WHERE PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA AND EXTRATOR.TIPO = 'B' )";
            psSelectProcessados = Conexao.getConnection().prepareStatement(SelectTitulos);

            rsSelectProcessados = psSelectProcessados.executeQuery();
            while (rsSelectProcessados.next()) {
                gerarPendencia();
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }
        public void gerarPendenciaManual(ExtracaoTableModel extracaoModel, String Origem) throws ErroSistemaException {
        try {
            sqlGerPendencia = "";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append("{call SAPIENS.SP_GERAR_PENDENCIA_V3(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            sqlGerPendencia = stringBuffer.toString();
            String sChave, sOperacao, sProcesso;
            sChave = "{\"idRegistro\":\"1|" + extracaoModel.getCodfil() + "|" + extracaoModel.getCodTpt() + "|" + extracaoModel.getNumtit() + "\"}";
            if (Origem.equalsIgnoreCase("Baixa")) {
                sProcesso = "NEGATIVADOR_BAIXAR_NEGATIVACAO";
            } else {
                sProcesso = "NEGATIVADOR_INCLUIR_NEGATIVACAO";
            }
            String sFila = "ERP";
            try (CallableStatement callableStatement = Conexao.getConnection().prepareCall(sqlGerPendencia)) {
                int index = 1;
                callableStatement.setString(index++, sChave);
                callableStatement.setString(index++, "I");
                callableStatement.setInt(index++, 0);
                callableStatement.setString(index++, sProcesso);
                callableStatement.setString(index++, "F");
                callableStatement.setInt(index++, extracaoModel.getCodfil());
                callableStatement.setString(index++, "S");
                callableStatement.setString(index++, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                callableStatement.setString(index++, sFila);
                callableStatement.setInt(index++, 0);
                int retorno = callableStatement.executeUpdate();
                callableStatement.getResultSet();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

}
