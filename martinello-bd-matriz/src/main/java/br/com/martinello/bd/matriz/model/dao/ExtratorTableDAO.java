/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

/**
 *
 * @author luiz.almeida
 *
 */
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExtratorTableDAO {

    private PreparedStatement psTablePrincipal, psMotivoBaixaBvs, psMotivoBaixaSpc, psNaturezaRegistro, psNaturezaInclusao, psMotivoInclusao;
    private ResultSet rsTableprincipal, rsMotivoBaixaBvs, rsMotivoBaixaSpc, rsNaturezaRegistro, rsNaturezaInclusao, rsMotivoInclusao;
    public List<ExtracaoTableModel> lextracaoTableModel, lextracaoTableModelManual;
    public List<ConsultaModel> lmotivoBaixaBvs, lmotivoInclusao, lmotivoBaixaSpc, lnaturezaRegistro, ltableOrigemManual, lnaturezaInclusao;
    public int indice;
    private String sqlSelectExtracao, sqlSelectMotivoBaixaBvs, sqlSelectMotivoInclusaoBvs, sqlSelectMotivoBaixaSpc, sqlSelectNaturezaInclusao, sqlSelectNaturezaRegistro, sqlSelectExtracaoOrigemManual;

    public List<ExtracaoTableModel> listarExtracaoTable(ExtracaoTableModel filtroConsultaExtracao) throws ErroSistemaException {
        ExtracaoTableModel extracaoTableModel;
        try {
            sqlSelectExtracao = "SELECT FILIAIS.CODFIL, \n"
                    + "      CASE WHEN FILIAIS.DATA_INICIO_DBNOVO > '31/12/1900' THEN \n"
                    + "      'NOVO_SGL'\n"
                    + "         ELSE 'SGL'\n"
                    + "       END BD_ORIGEM, \n"
                    + "       PESSOA.ID_PESSOA,\n"
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
                    + "       PARCELA.NUMTIT,\n"
                    + "       PARCELA.CODTPT,\n"
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
                    + " INNER JOIN FILIAIS\n"
                    + "     ON PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                    + " INNER JOIN PESSOA\n"
                    + "    ON PARCELA.ID_CLIENTE = PESSOA.ID_PESSOA\n"
                    + " INNER JOIN EXTRATOR\n"
                    + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n" + getWhere(filtroConsultaExtracao);

            psTablePrincipal = Conexao.getConnection().prepareStatement(sqlSelectExtracao);
            lextracaoTableModel = null;
            lextracaoTableModel = new ArrayList<>();
            rsTableprincipal = psTablePrincipal.executeQuery();
            indice = 1;

            while (rsTableprincipal.next()) {
                extracaoTableModel = new ExtracaoTableModel();
                extracaoTableModel.setIndice(indice++);
                extracaoTableModel.setIdExtracao(rsTableprincipal.getString("ID_EXTRATOR"));
                extracaoTableModel.setIdFilial(rsTableprincipal.getString("ID_FILIAL"));
                extracaoTableModel.setCodfil(rsTableprincipal.getInt("CODFIL"));
                extracaoTableModel.setIdCliente(rsTableprincipal.getString("ID_PESSOA"));
                extracaoTableModel.setIdParcela(rsTableprincipal.getString("ID_PARCELA"));
                extracaoTableModel.setNome(rsTableprincipal.getString("NOME"));
                extracaoTableModel.setCodigoCliente(rsTableprincipal.getString("CLIENTE"));
                extracaoTableModel.setTipoPessoa(rsTableprincipal.getString("TIPO_PESSOA"));
                extracaoTableModel.setCpfCnpj(rsTableprincipal.getString("CPF_RAZAO"));
                extracaoTableModel.setNumeroRg(rsTableprincipal.getString("NUM_RG"));
                extracaoTableModel.setTipoDevedor(rsTableprincipal.getString("TIPO_DEVEDOR"));
                extracaoTableModel.setOrigemBd(rsTableprincipal.getString("BD_ORIGEM"));
                extracaoTableModel.setNumeroDoc(rsTableprincipal.getString("NUMERO_DOC"));
                extracaoTableModel.setNumtit(rsTableprincipal.getString("NUMTIT"));
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
                extracaoTableModel.setCodTpt(rsTableprincipal.getString("CODTPT"));
              
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

    public List<ConsultaModel> listarMotivoBaixaBvs() throws ErroSistemaException {
        try {
            ConsultaModel motivobaixa;
            sqlSelectMotivoBaixaBvs = "SELECT MOTIVO_BAIXA.ID, MOTIVO_BAIXA.ID_MTV_BAIXA, MOTIVO_BAIXA.MOTIVO_BAIXA  FROM MOTIVO_BAIXA WHERE MOTIVO_BAIXA.LOCAL_BAIXA = 'BVS'";
            psMotivoBaixaBvs = Conexao.getConnection().prepareStatement(sqlSelectMotivoBaixaBvs);
            lmotivoBaixaBvs = new ArrayList<>();
            rsMotivoBaixaBvs = psMotivoBaixaBvs.executeQuery();

            while (rsMotivoBaixaBvs.next()) {
                motivobaixa = new ConsultaModel();
                motivobaixa.setId(rsMotivoBaixaBvs.getInt("ID"));
                motivobaixa.setIdMotivoBaixaBvs(rsMotivoBaixaBvs.getString("ID_MTV_BAIXA"));
                motivobaixa.setMotivoBaixaBvs(rsMotivoBaixaBvs.getString("MOTIVO_BAIXA"));
                lmotivoBaixaBvs.add(motivobaixa);
            }
            psMotivoBaixaBvs.close();
            rsMotivoBaixaBvs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lmotivoBaixaBvs;
    }

    public List<ConsultaModel> listarMotivoBaixaSpc() throws ErroSistemaException {
        try {
            ConsultaModel motivoBaixaSpc;
            sqlSelectMotivoBaixaSpc = "SELECT MOTIVO_BAIXA.ID_MTV_BAIXA, MOTIVO_BAIXA.MOTIVO_BAIXA  FROM MOTIVO_BAIXA WHERE MOTIVO_BAIXA.LOCAL_BAIXA = 'SPC' ORDER BY ID_MTV_BAIXA";
            psMotivoBaixaSpc = Conexao.getConnection().prepareStatement(sqlSelectMotivoBaixaSpc);
            lmotivoBaixaSpc = new ArrayList<>();
            rsMotivoBaixaSpc = psMotivoBaixaSpc.executeQuery();

            while (rsMotivoBaixaSpc.next()) {
                motivoBaixaSpc = new ConsultaModel();
                motivoBaixaSpc.setIdMotivoBaixaSpc(rsMotivoBaixaSpc.getString("ID_MTV_BAIXA"));
                motivoBaixaSpc.setMotivoBaixaSpc(rsMotivoBaixaSpc.getString("MOTIVO_BAIXA"));
                lmotivoBaixaSpc.add(motivoBaixaSpc);
            }
            psMotivoBaixaSpc.close();
            rsMotivoBaixaSpc.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lmotivoBaixaSpc;
    }

    public List<ConsultaModel> listarMotivoInclusaoBvs() throws ErroSistemaException {
        try {
            ConsultaModel motivobaixa;
            sqlSelectMotivoInclusaoBvs = "SELECT MOTIVO_BAIXA.ID_MTV_BAIXA, MOTIVO_BAIXA.MOTIVO_BAIXA  FROM MOTIVO_BAIXA WHERE MOTIVO_BAIXA.LOCAL_BAIXA = 'BVS' ORDER BY ID_MTV_BAIXA";
            psMotivoBaixaBvs = Conexao.getConnection().prepareStatement(sqlSelectMotivoInclusaoBvs);
            lmotivoBaixaBvs = new ArrayList<>();
            rsMotivoBaixaBvs = psMotivoBaixaBvs.executeQuery();

            while (rsMotivoBaixaBvs.next()) {
                motivobaixa = new ConsultaModel();
                motivobaixa.setIdMotivoBaixaBvs(rsMotivoBaixaBvs.getString("ID_MTV_BAIXA"));
                motivobaixa.setMotivoBaixaBvs(rsMotivoBaixaBvs.getString("MOTIVO_BAIXA"));
                lmotivoBaixaBvs.add(motivobaixa);
            }
            psMotivoBaixaBvs.close();
            rsMotivoBaixaBvs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lmotivoBaixaBvs;
    }

    public List<ConsultaModel> listarNaturezaInclusao() throws ErroSistemaException {
        try {
            ConsultaModel naturezaInclusao;
            sqlSelectNaturezaInclusao = "SELECT ID_NATUREZA_INCLUSAO, DESCRICAO FROM NATUREZA_INCLUSAO WHERE PROVEDOR = 'SPC' ORDER BY ID_NATUREZA_INCLUSAO";
            psNaturezaInclusao = Conexao.getConnection().prepareStatement(sqlSelectNaturezaInclusao);
            lnaturezaInclusao = new ArrayList<>();
            rsNaturezaInclusao = psNaturezaInclusao.executeQuery();

            while (rsNaturezaInclusao.next()) {
                naturezaInclusao = new ConsultaModel();
                naturezaInclusao.setIdNaturezaInclusaoSpc(rsNaturezaInclusao.getString("ID_NATUREZA_INCLUSAO"));
                naturezaInclusao.setNaturezaInclusaoSpc(rsNaturezaInclusao.getString("DESCRICAO"));
                lnaturezaInclusao.add(naturezaInclusao);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psNaturezaInclusao.close();
                rsNaturezaInclusao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return lnaturezaInclusao;
    }

    public List<ConsultaModel> listarNaturezaRegistro() throws ErroSistemaException {
        try {
            ConsultaModel naturezaRegistro;
            sqlSelectNaturezaRegistro = "SELECT ID_NATUREZA_INCLUSAO, DESCRICAO FROM NATUREZA_INCLUSAO WHERE PROVEDOR = 'BVS' ORDER BY ID_NATUREZA_INCLUSAO";
            psNaturezaRegistro = Conexao.getConnection().prepareStatement(sqlSelectNaturezaRegistro);
            lnaturezaRegistro = new ArrayList<>();
            rsNaturezaRegistro = psNaturezaRegistro.executeQuery();

            while (rsNaturezaRegistro.next()) {
                naturezaRegistro = new ConsultaModel();
                naturezaRegistro.setIdNaturezaRegistroBvs(rsNaturezaRegistro.getString("ID_NATUREZA_INCLUSAO"));
                naturezaRegistro.setNaturezaRegistroBvs(rsNaturezaRegistro.getString("DESCRICAO"));
                lnaturezaRegistro.add(naturezaRegistro);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psNaturezaRegistro.close();
                rsNaturezaRegistro.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return lnaturezaRegistro;
    }

    public String getWhere(ExtracaoTableModel filtroConsultaExtracao) {

        String where = "";
        if (filtroConsultaExtracao != null) {
            //  where += " WHERE 0 = 0 AND ";
            if (filtroConsultaExtracao.getIdFilial() != null) {
                where += ((filtroConsultaExtracao.getIdFilial() != null) ? " AND PARCELA.ID_FILIAL = '" + filtroConsultaExtracao.getIdFilial() + "'" : "");
            }
            if (filtroConsultaExtracao.getNome() != null) {
                where += ((filtroConsultaExtracao.getNome() != null) ? " AND PESSOA.NOME LIKE '" + filtroConsultaExtracao.getNome() + "'" : "");
            }
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
            if (filtroConsultaExtracao.getStatus() != null) {
                if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("FINALIZADO") || filtroConsultaExtracao.getStatus().equalsIgnoreCase("INVALIDO")) {
                    where += ((filtroConsultaExtracao.getStatus().equalsIgnoreCase("INVALIDO")) ? " AND EXTRATOR.STATUS = 'I'" : " AND EXTRATOR.STATUS IN ('F', 'FE')");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("PRESCRITO")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND PARCELA.PRESCRITO = 'S'" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("ORIGEM MANUAL")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND EXTRATOR.ORIGEM = 'M'" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("A ENVIAR")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND (EXTRATOR.STATUS_FACMAT IN ('P','E') "
                            + "OR EXTRATOR.STATUS_SPC IN ('P','E') OR (EXTRATOR.STATUS_SPC_AVAL IN ('P','E') AND PARCELA.ID_AVALISTA IS NOT NULL)) "
                            + "AND EXTRATOR.STATUS IN ('P', 'PP', 'EP')" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("ENVIADO")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND EXTRATOR.STATUS = 'PR'" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("ERRO PROCESSAMENTO")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND EXTRATOR.STATUS = 'EP'" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("PROCESSADO")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND EXTRATOR.STATUS = 'PR'" : "");
                } else if (filtroConsultaExtracao.getStatus().equalsIgnoreCase("PROCESSADO PARCIAL")) {
                    where += ((filtroConsultaExtracao.getStatus() != null) ? " AND EXTRATOR.STATUS = 'PP'" : "");
                }
            }
            if (filtroConsultaExtracao.getStatusProvedor() != null) {
                if (filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO FACMAT") || filtroConsultaExtracao.getStatusProvedor().equals("AGUARDANDO FACMAT")
                        || filtroConsultaExtracao.getStatusProvedor().equals("ERRO FACMAT") || filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR FACMAT")) {

                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO FACMAT")) ? " AND EXTRATOR.STATUS_FACMAT = 'S'" : "");
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("AGUARDANDO FACMAT")) ? " AND EXTRATOR.STATUS_FACMAT = 'A'" : "");
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("ERRO FACMAT")) ? " AND EXTRATOR.STATUS_FACMAT = 'E'" : "");
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR FACMAT")) ? " AND EXTRATOR.STATUS_FACMAT = 'P'" : "");

                } else if (filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO SPC") || filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR SPC")
                        || filtroConsultaExtracao.getStatusProvedor().equals("ERRO SPC")) {
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("ENVIADO SPC")) ? " AND EXTRATOR.STATUS_SPC = 'S'" : "");
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("ERRO SPC")) ? " AND EXTRATOR.STATUS_SPC = 'E'" : "");
                    where += ((filtroConsultaExtracao.getStatusProvedor().equals("A ENVIAR SPC")) ? " AND EXTRATOR.STATUS_SPC = 'P'" : "");
                }
            }
            if (filtroConsultaExtracao.getPago() != null) {
                if (filtroConsultaExtracao.getPago().equals("S")) {
                    where += ((filtroConsultaExtracao.getPago() != null) ? " AND EXTRATOR.TIPO = 'B'" : "");
                } else {
                    where += ((filtroConsultaExtracao.getPago() != null) ? " AND EXTRATOR.TIPO = 'I' AND NOT EXISTS(SELECT 1 FROM EXTRATOR WHERE EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA AND TIPO = 'B') " : "");
                }

            }
            if (filtroConsultaExtracao.getOrigemRegistro() != null) {
                where += ((filtroConsultaExtracao.getOrigemRegistro() != null) ? " AND PARCELA.ORIGEM_PROVEDOR = '" + filtroConsultaExtracao.getOrigemRegistro() + "'" : "");
            }
            if (filtroConsultaExtracao.getDataEnvioInicio() != null) {
                where += ((filtroConsultaExtracao.getDataEnvioInicio() != null) ? " AND ((TO_DATE(EXTRATOR.DATA_FACMAT) BETWEEN '" + filtroConsultaExtracao.getDataEnvioInicio() + "'  AND '" + filtroConsultaExtracao.getDataEnvioFim() + "')" + "OR (TO_DATE(EXTRATOR.DATA_SPC) BETWEEN '" + filtroConsultaExtracao.getDataEnvioInicio() + "'" + " AND '" + filtroConsultaExtracao.getDataEnvioFim() + "'))" : "");
            }
//            if (filtroConsultaExtracao.getDataEnvioFim() != null) {
//                where += ((filtroConsultaExtracao.getDataEnvioFim() != null) ? " AND '" + filtroConsultaExtracao.getDataEnvioFim() + "'" : "");
//            }
            if (filtroConsultaExtracao.getDataExtracaoInicio() != null) {
                where += ((filtroConsultaExtracao.getDataExtracaoInicio() != null) ? " AND TO_DATE(EXTRATOR.DATA_EXTRACAO) BETWEEN '" + filtroConsultaExtracao.getDataExtracaoInicio() + "'" : "");
            }
            if (filtroConsultaExtracao.getDataExtracaoFim() != null) {
                where += ((filtroConsultaExtracao.getDataExtracaoFim() != null) ? " AND '" + filtroConsultaExtracao.getDataExtracaoFim() + "'" : "");
            }

        }
       
        return where.trim().length() > 0 ? " WHERE 0 = 0 " + where : "";

    }

}
