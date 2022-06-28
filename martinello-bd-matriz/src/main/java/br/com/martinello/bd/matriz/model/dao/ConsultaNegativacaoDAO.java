/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ConsultaNegativacaoModel;
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
public class ConsultaNegativacaoDAO {

    private String sqlSelect;
    public List<ConsultaNegativacaoModel> lConsultaNeg;
    private PreparedStatement psTablePrincipal;
    private ResultSet rsTableprincipal;
    public List<ExtracaoTableModel> lextracaoTableModel;
    public int indice;
    private String sqlSelectExtracao;

    public ConsultaNegativacaoDAO() {

    }

    public List<ConsultaNegativacaoModel> listarNegativacaoSintetica(ExtracaoTableModel filtroConsulta) throws ErroSistemaException {
        ConsultaNegativacaoModel consultaNegativacaoModel;

        sqlSelect = "SELECT ID_FILIAL,\n"
                + "       NOME_FILIAL,\n"
                + "       VALOR,\n"
                + "       QUANTIDADE,\n"
                + "       CLIENTES,\n"
                + "       CASE\n"
                + "         WHEN QUANTIDADE > 0 AND CLIENTES > 0 THEN\n"
                + "        cast(QUANTIDADE / CLIENTES as  number(6,2))\n"
                + "         ELSE\n"
                + "          0\n"
                + "       END MEDIA\n"
                + "  FROM (SELECT ROUND((SELECT SUM(VALOR)\n"
                + "                       FROM PARCELA\n"
                + "                      INNER JOIN EXTRATOR\n"
                + "                         ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                     " + getWhere(filtroConsulta, "SINTETICA") + " AND PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL),2) AS VALOR,\n"
                + "               NVL((SELECT COUNT(*)\n"
                + "                     FROM PARCELA\n"
                + "                    INNER JOIN EXTRATOR\n"
                + "                       ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                " + getWhere(filtroConsulta, "SINTETICA") + " AND PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL),0) AS QUANTIDADE,\n"
                + "               (SELECT COUNT(MAX(ID_PESSOA))\n"
                + "                  FROM PESSOA\n"
                + "                 INNER JOIN PARCELA\n"
                + "                    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                 INNER JOIN EXTRATOR\n"
                + "                    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                 " + getWhere(filtroConsulta, "SINTETICA") + " AND PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL \n"
                + "                 GROUP BY PESSOA.CPF_RAZAO) AS CLIENTES,\n"
                + "               FILIAIS.ID_FILIAL,\n"
                + "               FILIAIS.NOME_FILIAL\n"
                + "          FROM FILIAIS\n"
                + "        " + getWhereFilial(filtroConsulta) + "AND FILIAIS.ATIVO = 'A')";
        System.out.println("select: " + sqlSelect);
        try (PreparedStatement psConsulta = Conexao.getConnection().prepareStatement(sqlSelect)) {

            lConsultaNeg = null;
            lConsultaNeg = new ArrayList<>();
            try (ResultSet rsConsulta = psConsulta.executeQuery()) {
                int indice = 1;
                while (rsConsulta.next()) {
                    consultaNegativacaoModel = new ConsultaNegativacaoModel();
                    consultaNegativacaoModel.setIndice(indice++);
                    consultaNegativacaoModel.setId_Filial(rsConsulta.getString("ID_FILIAL"));
                    consultaNegativacaoModel.setFilial(rsConsulta.getString("NOME_FILIAL"));
                    consultaNegativacaoModel.setValor(rsConsulta.getDouble("VALOR"));
                    consultaNegativacaoModel.setQuantidade(rsConsulta.getInt("QUANTIDADE"));
                    consultaNegativacaoModel.setQtdCliente(rsConsulta.getInt("CLIENTES"));
                    consultaNegativacaoModel.setMediaClienteParcela(rsConsulta.getBigDecimal("MEDIA"));
                    lConsultaNeg.add(consultaNegativacaoModel);
                }
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lConsultaNeg;

    }

    public List<ExtracaoTableModel> consultaNegativacao(ExtracaoTableModel filtroConsultaExtracao) throws ErroSistemaException {
        ExtracaoTableModel extracaoTableModel;
        try {
            sqlSelectExtracao = "SELECT PESSOA.ID_PESSOA,\n"
                    + "       PESSOA.NOME,\n"
                    + "       PESSOA.TIPO_PESSOA,\n"
                    + "       PESSOA.CPF_RAZAO,\n"
                    + "       PESSOA.NUM_RG,\n"
                    + "       PESSOA.DATE_NASC,\n"
                    + "       PESSOA.CIDADE,\n"
                    + "       PESSOA.CODIGO_IBGE,\n"
                    + "       PESSOA.CEP,\n"
                    + "       PESSOA.UF_ESTADO,\n"
                    + "       PESSOA.TIPO_DEVEDOR,\n"
                    + "       PESSOA.SITUACAO,\n"
                    + "       PARCELA.ID_PARCELA,\n"
                    + "       PARCELA.ID_FILIAL,\n"
                    + "       PARCELA.CODIGO,\n"
                    + "       PARCELA.NUMERO_DOC,\n"
                    + "       PARCELA.DATALAN,\n"
                    + "       PARCELA.VENC,\n"
                    + "       PARCELA.VALOR,\n"
                    + "       PARCELA.DATAPAG,\n"
                    + "       PARCELA.CAPITPAG,\n"
                    + "       PARCELA.SIT,\n"
                    + "       PARCELA.ORIGEM_PROVEDOR,\n"
                    + "       PARCELA.DATA_NEGATIVADA,\n"
                    + "       PARCELA.DATA_BAIXA,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.VALOR_PAG,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.NUMPAR,\n"
                    + "       PARCELA.TIPOPAG,\n"
                    + "       PARCELA.ID_CLIENTE,\n"
                    + "       PARCELA.CLIENTE,\n"
                    + "       PARCELA.AVALISTA,\n"
                    + "       PARCELA.DATA_ULT_ATUALIZACAO,\n"
                    + "       PARCELA.PRESCRITO,\n"
                    + "       PARCELA.INCLUIR_AVAL,\n"
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
                    + "         (SELECT NOME FROM PESSOA WHERE PESSOA.ID_PESSOA = PARCELA.ID_AVALISTA) NOME_AVALISTA,\n"
                    + "       EXTRATOR.ID_EXTRATOR,\n"
                    + "       EXTRATOR.TIPO,\n"
                    + "       EXTRATOR.STATUS,\n"
                    + "       EXTRATOR.STATUS_FACMAT,\n"
                    + "       EXTRATOR.STATUS_SPC,\n"
                    + "       EXTRATOR.STATUS_SPC_AVAL,\n"
                    + "       EXTRATOR.ID_PARCELA AS ID_PARCELA_EXTRATOR,\n"
                    + "       EXTRATOR.ORIGEM,\n"
                    + "       EXTRATOR.RETORNO,\n"
                    + "       EXTRATOR.DATA_RETORNO,\n"
                    + "       EXTRATOR.DATA_EXTRACAO\n"
                    + "  FROM PARCELA\n"
                    + " INNER JOIN PESSOA\n"
                    + "    ON PARCELA.ID_CLIENTE = PESSOA.ID_PESSOA\n"
                    + " INNER JOIN EXTRATOR\n"
                    + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n" + getWhere(filtroConsultaExtracao, "ANALITICA");

            psTablePrincipal = Conexao.getConnection().prepareStatement(sqlSelectExtracao);
            System.out.println("Table: " + sqlSelectExtracao);
            lextracaoTableModel = null;
            lextracaoTableModel = new ArrayList<>();
            rsTableprincipal = psTablePrincipal.executeQuery();
            indice = 1;

            while (rsTableprincipal.next()) {
                extracaoTableModel = new ExtracaoTableModel();
                extracaoTableModel.setIndice(indice++);
                extracaoTableModel.setIdExtracao(rsTableprincipal.getString("ID_EXTRATOR"));
                extracaoTableModel.setIdFilial(rsTableprincipal.getString("ID_FILIAL"));
                extracaoTableModel.setIdCliente(rsTableprincipal.getString("ID_PESSOA"));
                extracaoTableModel.setIdParcela(rsTableprincipal.getString("ID_PARCELA"));
                extracaoTableModel.setNome(rsTableprincipal.getString("NOME"));
                extracaoTableModel.setCodigoCliente(rsTableprincipal.getString("CLIENTE"));
                extracaoTableModel.setTipoPessoa(rsTableprincipal.getString("TIPO_PESSOA"));
                extracaoTableModel.setCpfCnpj(rsTableprincipal.getString("CPF_RAZAO"));
                extracaoTableModel.setNumeroRg(rsTableprincipal.getString("NUM_RG"));
                extracaoTableModel.setTipoDevedor(rsTableprincipal.getString("TIPO_DEVEDOR"));
                extracaoTableModel.setNumeroDoc(rsTableprincipal.getString("NUMERO_DOC"));
                extracaoTableModel.setTipoAcao(rsTableprincipal.getString("TIPO"));
                extracaoTableModel.setPago(rsTableprincipal.getString("SIT"));
                extracaoTableModel.setValor(rsTableprincipal.getDouble("VALOR"));
                extracaoTableModel.setDataLancamento(rsTableprincipal.getDate("DATALAN"));
                extracaoTableModel.setDataVencimento(rsTableprincipal.getDate("VENC"));
                extracaoTableModel.setNumeroParcela(rsTableprincipal.getString("NUMPAR"));
                extracaoTableModel.setDataPagamento(rsTableprincipal.getDate("DATAPAG"));
                extracaoTableModel.setDataNegativada(rsTableprincipal.getDate("DATA_NEGATIVADA"));
                extracaoTableModel.setDataBaixa(rsTableprincipal.getDate("DATA_BAIXA"));
                extracaoTableModel.setDataAlteracao(rsTableprincipal.getDate("DATAALT"));
                extracaoTableModel.setDataExtracao(rsTableprincipal.getDate("DATA_EXTRACAO"));
                extracaoTableModel.setDataUltimaAtualizacao(rsTableprincipal.getDate("DATA_ULT_ATUALIZACAO"));
                extracaoTableModel.setCodAvalista(rsTableprincipal.getString("AVALISTA"));
                System.out.println("" + rsTableprincipal.getString("INCLUIR_AVAL"));
                System.out.println("" + rsTableprincipal.getString("AVALISTA"));
                if (rsTableprincipal.getString("INCLUIR_AVAL").equals("N")) {
                    extracaoTableModel.setIncluirAval("Não Incluir Avalista");
                } else if (rsTableprincipal.getString("INCLUIR_AVAL").equals("S")) {
                    extracaoTableModel.setIncluirAval("Incluir Avalista");
                }

                extracaoTableModel.setStatusSpc(rsTableprincipal.getString("STATUS_SPC"));
                extracaoTableModel.setStatusSpcAval(rsTableprincipal.getString("STATUS_SPC_AVAL"));
                extracaoTableModel.setStatusFacmat(rsTableprincipal.getString("STATUS_FACMAT"));

//                if (rsTableprincipal.getString("TIPO").equals("I") && rsTableprincipal.getDate("DATA_INCLUSAO_SPC").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataSpcInclusao(rsTableprincipal.getDate("DATA_INCLUSAO_SPC"));
                //} 
                //if (rsTableprincipal.getString("TIPO").equals("I") && rsTableprincipal.getDate("DATA_INCLUSAO_AVALISTA_SPC").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataSpcAvalistaInclusao(rsTableprincipal.getDate("DATA_INCLUSAO_AVALISTA_SPC"));

                //if (rsTableprincipal.getString("TIPO").equals("I") && rsTableprincipal.getDate("DATA_INCLUSAO_FACMAT").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataFacmatInclusao(rsTableprincipal.getDate("DATA_INCLUSAO_FACMAT"));
                //} 
                //if (rsTableprincipal.getString("TIPO").equals("B") && rsTableprincipal.getDate("DATA_BAIXA_FACMAT").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataFacmatBaixa(rsTableprincipal.getDate("DATA_BAIXA_FACMAT"));
                //} 
                //if (rsTableprincipal.getString("TIPO").equals("B") && rsTableprincipal.getDate("DATA_BAIXA_SPC").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataSpcBaixa(rsTableprincipal.getDate("DATA_BAIXA_SPC"));
                // } 
                // if (rsTableprincipal.getString("TIPO").equals("B") && rsTableprincipal.getDate("DATA_BAIXA_SPC_AVALISTA").after(Utilitarios.getDataZero())) {
                extracaoTableModel.setDataSpcAvalistaBaixa(rsTableprincipal.getDate("DATA_BAIXA_SPC_AVALISTA"));
                // } 
                extracaoTableModel.setOrigemRegistro(rsTableprincipal.getString("ORIGEM_PROVEDOR"));
                extracaoTableModel.setCidade(rsTableprincipal.getString("CIDADE"));
                extracaoTableModel.setCep(rsTableprincipal.getString("CEP"));
                //extracaoTableModel.setDescricaoRetorno(rsTableprincipal.getString("DESCRICAO"));
                extracaoTableModel.setDataRetorno(rsTableprincipal.getDate("DATA_RETORNO"));
//                if (filtroConsultaExtracao.getStatus().equals("A ENVIAR")) {
//                    extracaoTableModel.setStatus("A ENVIAR");
//                } else 
                if (filtroConsultaExtracao.getStatus() != null) {
                    if (filtroConsultaExtracao.getStatus().equals("INVALIDO")) {
                        extracaoTableModel.setStatus("INVALIDO");
                    } else if (filtroConsultaExtracao.getStatus().equals("FINALIZADO")) {
                        extracaoTableModel.setStatus("FINALIZADO");
                    } else if (filtroConsultaExtracao.getStatus().equals("PRESCRITO")) {
                        extracaoTableModel.setStatus("PRESCRITO");
                    } else if (filtroConsultaExtracao.getStatus().equals("ORIGEM MANUAL")) {
                        extracaoTableModel.setStatus("ORIGEM MANUAL");
                    } else if (filtroConsultaExtracao.getStatus().equals("A ENVIAR")) {
                        extracaoTableModel.setStatus("A ENVIAR");
                    } else if (filtroConsultaExtracao.getStatus().equals("PROCESSADO PARCIAL")) {
                        extracaoTableModel.setStatus("PROCESSADO PARCIAL");
                    } else if (filtroConsultaExtracao.getStatus().equals("PROCESSADO")) {
                        extracaoTableModel.setStatus("PROCESSADO");
                    } else if (filtroConsultaExtracao.getStatus().equals("ERRO PROCESSAMENTO")) {
                        extracaoTableModel.setStatus("ERRO PROCESSAMENTO");
                    }
                } else {
                    if (rsTableprincipal.getString("STATUS").equals("P")) {
                        extracaoTableModel.setStatus("A ENVIAR");
                    } else if (rsTableprincipal.getString("STATUS").equals("I")) {
                        extracaoTableModel.setStatus("INVALIDO");
                    } else if (rsTableprincipal.getString("STATUS").equals("EP")) {
                        extracaoTableModel.setStatus("ERRO PROCESSAMENTO");
                    } else if (rsTableprincipal.getString("STATUS").equals("PR")) {
                        extracaoTableModel.setStatus("PROCESSADO");
                    } else if (rsTableprincipal.getString("STATUS").equals("PP")) {
                        extracaoTableModel.setStatus("PROCESSADO PARCIAL");
                    } else if (rsTableprincipal.getString("STATUS").equals("F") && rsTableprincipal.getString("PRESCRITO").equals("S")) {
                        extracaoTableModel.setStatus("PRESCRITO");
                    } else if (rsTableprincipal.getString("STATUS").equals("F") && rsTableprincipal.getString("PRESCRITO").equals("N")) {
                        extracaoTableModel.setStatus("FINALIZADO");
                    }
                }
                if (filtroConsultaExtracao.getStatusProvedor() != null) {
                    if (filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO FACMAT")) {
                        extracaoTableModel.setStatusProvedor("ENVIADO FACMAT");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("AGUARDANDO FACMAT")) {
                        extracaoTableModel.setStatusProvedor("AGUARDANDO FACMAT");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("ERRO FACMAT")) {
                        extracaoTableModel.setStatusProvedor("ERRO FACMAT");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR FACMAT")) {
                        extracaoTableModel.setStatusProvedor("A ENVIAR FACMAT");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR SPC")) {
                        extracaoTableModel.setStatusProvedor("A ENVIAR SPC");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("ERRO SPC")) {
                        extracaoTableModel.setStatusProvedor("ERRO SPC");
                    } else if (filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO SPC")) {
                        extracaoTableModel.setStatusProvedor("ENVIADO SPC");
                    }
                }

                extracaoTableModel.setNomeAvalista(rsTableprincipal.getString("NOME_AVALISTA"));

                lextracaoTableModel.add(extracaoTableModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());

        } finally {
            try {
                psTablePrincipal.close();
                rsTableprincipal.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return lextracaoTableModel;

    }

    private String getWhere(ExtracaoTableModel filtroConsultaExtracao, String origem) {
        String where = "";
        if (filtroConsultaExtracao != null) {
            //  where += " WHERE 0 = 0 AND ";

            if (filtroConsultaExtracao.getCpfCnpj() != null) {
                String cpfs = filtroConsultaExtracao.getCpfCnpj();
                System.out.println("cpfString:" + cpfs);
                long cpf = Long.parseLong(cpfs);
                System.out.println("cpf:" + cpf);
                where += " AND TO_NUMBER(CPF_RAZAO) = " + cpf;
            }
            if (filtroConsultaExtracao.getNumeroDoc() != ("**********")) {
                where += ((filtroConsultaExtracao.getNumeroDoc() != null) ? " AND PARCELA.NUMERO_DOC = '" + filtroConsultaExtracao.getNumeroDoc() + "'" : "");
            }
            if (filtroConsultaExtracao.getIdCliente() != null) {
                where += ((filtroConsultaExtracao.getIdCliente() != null) ? " AND PARCELA.CLIENTE = '" + filtroConsultaExtracao.getIdCliente() + "'" : "");
            }
            if (filtroConsultaExtracao.getIdExtracao() != null) {
                where += ((filtroConsultaExtracao.getIdExtracao() != null) ? " AND EXTRATOR.ID_EXTRATOR = '" + filtroConsultaExtracao.getIdExtracao() + "'" : "");
            }
            if (filtroConsultaExtracao.getIdParcela() != null) {
                where += ((filtroConsultaExtracao.getIdParcela() != null) ? " AND EXTRATOR.ID_PARCELA = '" + filtroConsultaExtracao.getIdParcela() + "'" : "");
            }

            if (filtroConsultaExtracao.getPago() != null) {
                if (filtroConsultaExtracao.getPago().equals("S")) {
                    where += ((filtroConsultaExtracao.getPago() != null) ? " AND EXTRATOR.TIPO = 'B'" : "");
                } else {
                    where += ((filtroConsultaExtracao.getPago() != null) ? " AND EXTRATOR.TIPO = 'I' AND NOT EXISTS(SELECT 1 FROM EXTRATOR WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA AND TIPO = 'B') " : "");
                }

            }

            if (filtroConsultaExtracao.getDataEnvioInicio() != null) {
                where += ((filtroConsultaExtracao.getDataEnvioInicio() != null) ? " AND ((TO_DATE(PARCELA.VENC) BETWEEN '" + filtroConsultaExtracao.getDataEnvioInicio() + "'  AND '" + filtroConsultaExtracao.getDataEnvioFim() + "'))" : "");
            }
//           
            if (filtroConsultaExtracao.getDataExtracaoInicio() != null) {
                where += ((filtroConsultaExtracao.getDataExtracaoInicio() != null) ? " AND TO_DATE(EXTRATOR.DATA_EXTRACAO) BETWEEN '" + filtroConsultaExtracao.getDataExtracaoInicio() + "'" : "");
            }
            if (filtroConsultaExtracao.getDataExtracaoFim() != null) {
                where += ((filtroConsultaExtracao.getDataExtracaoFim() != null) ? " AND '" + filtroConsultaExtracao.getDataExtracaoFim() + "'" : "");
            }
            if (origem.equalsIgnoreCase("ANALITICA")) {
                if (filtroConsultaExtracao.getIdFilial() != null) {
                    where += ((filtroConsultaExtracao.getIdFilial() != null) ? " AND PARCELA.ID_FILIAL = '" + filtroConsultaExtracao.getIdFilial() + "'" : "");
                }
            }

        }
        System.out.println("WHERE: " + where);
        return where.trim().length() > 0 ? " WHERE 0 = 0 " + where : "";

    }

    private String getWhereFilial(ExtracaoTableModel filtroConsultaExtracao) {
        String where = "WHERE 0 = 0 ";
        if (filtroConsultaExtracao.getIdFilial() != null) {
            where += ((filtroConsultaExtracao.getIdFilial() != null) ? " AND FILIAIS.ID_FILIAL = '" + filtroConsultaExtracao.getIdFilial() + "'" : "");
        }
        return where.trim().length() > 0 ? where : "";
    }
}
