/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.consulta.Domain.ContasReceber_Movimentos;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
import br.com.martinello.consulta.Domain.ParcelasModel;
import br.com.martinello.consulta.Domain.VendasItensModel;
import br.com.martinello.consulta.Domain.VendasModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luiz.almeida
 */
public class ContasReceberDAO {

    private String sqlSelectVendas, sqlSelectVendasItens, sqlSelect, sqlParcela, sqlSelectE301TCR;
    private ResultSet rsSelectVenda, rsSelect, rsSelectParcela, rsE301TCR, rsCrMov;
    private List<VendasModel> lVendas;
    private List<VendasItensModel> lVendasItens;
    private List<ContasReceberModel> lContasReceber;
    private List<ParcelasModel> lParcelas;
    private List<ParcelaModel> lE301TCR;
    private List<ContasReceber_Movimentos> LContasReceber_Movivento;
    private VendasItensModel vendasItens;
    private VendasModel vendas;
    private ContasReceberModel contasReceber;
    private ParcelasModel parcela;

    public List<VendasModel> buscarVendas(String sCodFil, int codCli) throws ErroSistemaException {
        lVendas = new LinkedList<>();
        lVendasItens = new LinkedList<>();
        lContasReceber = new LinkedList<>();
        sqlSelectVendas = "SELECT VENDAS.ID_REGISTRO,\n"
                + "       VENDAS.CLIENTE,\n"
                + "       VENDAS.CLIENTE_ENDERECO,\n"
                + "       VENDAS.ID_VENDA,\n"
                + "       VENDAS.DATA,\n"
                + "       VENDAS.NOME_CLIENTE,\n"
                + "       VENDAS.OPERACAO,\n"
                + "       VENDAS.SITUACAO,\n"
                + "       VENDAS.TIPO_VENDA,\n"
                + "       VENDAS.DEVOLVIDA, \n"
                + "       USUARIOS.NOME,\n"
                + "       VENDAS.VALOR_A_PRAZO,\n"
                + "       VENDAS.VALOR_A_VISTA\n"
                + "  FROM INTEGRACAOFL.VENDAS\n"
                + " INNER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS CLIEND\n"
                + "    ON CLIEND.CLIENTE = VENDAS.CLIENTE\n"
                + " INNER JOIN INTEGRACAOFL.USUARIOS\n"
                + "    ON (USUARIOS.ID_USUARIO = VENDAS.VENDEDOR)"
                + "WHERE CODCLI = " + codCli + " AND VENDAS.LOJA = " + sCodFil + " ORDER BY VENDAS.DATA";
        try (PreparedStatement psSelectVenda = Conexao.getConnection().prepareStatement(sqlSelectVendas)) {
            rsSelectVenda = psSelectVenda.executeQuery();
            while (rsSelectVenda.next()) {
                vendas = new VendasModel();
                vendas.setIdRegistro(rsSelectVenda.getString("ID_REGISTRO"));
                vendas.setCliente(rsSelectVenda.getString("CLIENTE"));
                vendas.setClienteEndereco(rsSelectVenda.getString("CLIENTE_ENDERECO"));
                vendas.setIdVenda(rsSelectVenda.getInt("ID_VENDA"));
                vendas.setNomeCliente(rsSelectVenda.getString("NOME_CLIENTE"));
                vendas.setOperacao(rsSelectVenda.getString("OPERACAO"));
                vendas.setSituacao(rsSelectVenda.getString("SITUACAO"));
                vendas.setTipoVenda(rsSelectVenda.getString("TIPO_VENDA"));
                vendas.setVendedor(rsSelectVenda.getString("NOME"));
                vendas.setVlrAprazo(rsSelectVenda.getDouble("VALOR_A_PRAZO"));
                vendas.setVlrAvista(rsSelectVenda.getDouble("VALOR_A_VISTA"));
                vendas.setDevolvida(rsSelectVenda.getString("DEVOLVIDA"));
                vendas.setDataVenda(rsSelectVenda.getDate("DATA"));
                lVendasItens = buscarVendaItens(rsSelectVenda.getString("ID_REGISTRO"));
                vendas.setlVendasItens(lVendasItens);
                lParcelas = buscarParcelas(rsSelectVenda.getString("ID_REGISTRO"));
                vendas.setlParcela(lParcelas);
                lContasReceber = buscarContasReceberVenda(sCodFil, codCli, rsSelectVenda.getString("ID_REGISTRO"));
                vendas.setlContasReceber(lContasReceber);
                lVendas.add(vendas);

            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lVendas;

    }

    private List<VendasItensModel> buscarVendaItens(String idVenda) throws ErroSistemaException {
        lVendasItens = new LinkedList<>();
        lParcelas = new LinkedList<>();
        sqlSelectVendasItens = "   SELECT VENDAS_ITENS.CALCULA_JUROS,\n"
                + "                       VENDAS_ITENS.ESTOQUE_DEPOSITO,\n"
                + "                       VENDAS_ITENS.LOCAL_ITEM,\n"
                + "                       VENDAS_ITENS.LOCAL_ITEM_LOJA,\n"
                + "                       VENDAS_ITENS.NUMERO_SERIE,\n"
                + "                       VENDAS_ITENS.OBSERVACAO,\n"
                + "                       VENDAS_ITENS.PRODUTO ||' - ' ||PRODUTOS.DESCRICAO AS PRODUTO,\n"
                + "                       VENDAS_ITENS.QUANTIDADE,\n"
                + "                       VENDAS_ITENS.SERIAL,\n"
                + "                       VENDAS_ITENS.TIPO_ENTREGA,\n"
                + "                       VENDAS_ITENS.VALOR_TOTAL_A_PRAZO,\n"
                + "                       VENDAS_ITENS.VALOR_A_VISTA,\n"
                + "                       VENDAS_ITENS.VALOR_TOTAL_A_VISTA\n"
                + "                  FROM INTEGRACAOFL.VENDAS_ITENS\n"
                + "                  LEFT OUTER JOIN INTEGRACAOFL.PRODUTOS\n"
                + "                    ON VENDAS_ITENS.PRODUTO = PRODUTOS.CODIGO\n"
                + "                 WHERE VENDA = '" + idVenda + "'";
        try (PreparedStatement psSelectVendaItens = Conexao.getConnection().prepareStatement(sqlSelectVendasItens)) {
            rsSelect = psSelectVendaItens.executeQuery();
            while (rsSelect.next()) {
                vendasItens = new VendasItensModel();
                vendasItens.setCalcJuros(rsSelect.getString("CALCULA_JUROS"));
                vendasItens.setEstoque(rsSelect.getString("ESTOQUE_DEPOSITO"));
                vendasItens.setLocal(rsSelect.getString("LOCAL_ITEM"));
                vendasItens.setLocalLoja(rsSelect.getString("LOCAL_ITEM_LOJA"));
                vendasItens.setNumeroSerie(rsSelect.getString("NUMERO_SERIE"));
                vendasItens.setObservacao(rsSelect.getString("OBSERVACAO"));
                vendasItens.setProduto(rsSelect.getString("PRODUTO"));
                vendasItens.setQuantidade(rsSelect.getInt("QUANTIDADE"));
                vendasItens.setSerial(rsSelect.getString("SERIAL"));
                vendasItens.setTipoEntrega(rsSelect.getString("TIPO_ENTREGA"));
                vendasItens.setVlrTotAprazo(rsSelect.getDouble("VALOR_TOTAL_A_PRAZO"));
                vendasItens.setVlrAvista(rsSelect.getDouble("VALOR_A_VISTA"));
                vendasItens.setVlrTotAvista(rsSelect.getDouble("VALOR_TOTAL_A_VISTA"));
                lVendasItens.add(vendasItens);

            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lVendasItens;
    }

    public List<ContasReceberModel> buscarContasReceber(String sCodFil, int codCli) throws ErroSistemaException {
        sqlSelect = "SELECT AVA.NOME_FANTASIA AS AVALISTA,\n"
                + "       CLI.NOME_FANTASIA AS CLIENTE,\n"
                + "       CR.CLIENTE_ENDERECO,\n"
                + "       CR.DATA_LANCAMENTO,\n"
                + "       NVL(CR.DATA_PAGAMENTO, TO_DATE('31/12/1900')) AS DATA_PAGAMENTO,\n"
                + "       CR.VENCIMENTO,\n"
                + "       CR.NUMERO_PARCELA,\n"
                + "       CR.SITUACAO,\n"
                + "       CR.TIPO,\n"
                + "       NVL(CRM.TIPO_BAIXA, ' ') AS TIPO_BAIXA,\n"
                + "       CR.VALOR,\n"
                + "       CR.VALOR_ABERTO,\n"
                + "       CR.VENDA,\n"
                + "       CR.PREVISAO_PAGAMENTO,\n"
                + "       CR.DOCUMENTO_SGL,\n"
                + "       CASE\n"
                + "         WHEN CR.SITUACAO = 'A' AND CR.VENCIMENTO < SYSDATE THEN\n"
                + "          TO_DATE(SYSDATE) - TO_DATE(CR.VENCIMENTO)\n"
                + "         WHEN CR.SITUACAO = 'L' THEN\n"
                + "          TO_DATE(CR.DATA_PAGAMENTO) - TO_DATE(CR.VENCIMENTO)\n"
                + "         WHEN CR.SITUACAO = 'A' AND CR.VENCIMENTO > SYSDATE THEN\n"
                + "          0\n"
                + "         WHEN CR.SITUACAO NOT IN ('L', 'A') THEN\n"
                + "          0\n"
                + "       END DIAS\n"
                + "  FROM INTEGRACAOFL.CONTAS_RECEBER CR\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS CLIEND\n"
                + "    ON CLIEND.ID_REGISTRO = CR.CLIENTE_ENDERECO\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES AVA\n"
                + "    ON AVA.ID_REGISTRO = CR.AVALISTA\n"
                + "  LEFT JOIN INTEGRACAOFL.CLIENTES CLI\n"
                + "    ON CLI.ID_REGISTRO = CR.CLIENTE\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CONTAS_RECEBER_MOVIMENTOS CRM\n"
                + "    ON CR.ID_REGISTRO = CRM.CONTA_RECEBER\n"
                + " WHERE CRM.ID_CONTA_RECEBER_MOVIMENTO =\n"
                + "       (SELECT MAX(ID_CONTA_RECEBER_MOVIMENTO)\n"
                + "          FROM INTEGRACAOFL.CONTAS_RECEBER_MOVIMENTOS\n"
                + "         WHERE CONTAS_RECEBER_MOVIMENTOS.CONTA_RECEBER = CR.ID_REGISTRO) \n"
                + "     AND CLIEND.CODCLI = " + codCli + " AND CR.LOJA = " + sCodFil + "ORDER BY VENCIMENTO";
        lContasReceber = new LinkedList<>();
        try (PreparedStatement psSelectCR = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelectCR.executeQuery();
            while (rsSelect.next()) {
                contasReceber = new ContasReceberModel();
                contasReceber.setAvalista(rsSelect.getString("AVALISTA"));
                contasReceber.setCliente(rsSelect.getString("CLIENTE"));
                contasReceber.setClienteEndereco(rsSelect.getString("CLIENTE_ENDERECO"));
                contasReceber.setDataLancamento(rsSelect.getDate("DATA_LANCAMENTO"));
                contasReceber.setDataPagamento(rsSelect.getDate("DATA_PAGAMENTO"));
                contasReceber.setDataVencimento(rsSelect.getDate("VENCIMENTO"));
                contasReceber.setNumeroParcela(rsSelect.getString("NUMERO_PARCELA"));
                contasReceber.setPrevisaoPagamento(rsSelect.getDate("PREVISAO_PAGAMENTO"));
                contasReceber.setSitucao(rsSelect.getString("SITUACAO"));
                contasReceber.setTipo(rsSelect.getString("TIPO"));
                contasReceber.setValor(rsSelect.getDouble("VALOR"));
                contasReceber.setValorAberto(rsSelect.getDouble("VALOR_ABERTO"));
                contasReceber.setVenda(rsSelect.getString("VENDA"));
                contasReceber.setDias(rsSelect.getInt("DIAS"));
                contasReceber.setTipoBaixa(rsSelect.getString("TIPO_BAIXA"));
                contasReceber.setDocSgl(rsSelect.getString("DOCUMENTO_SGL"));
                lContasReceber.add(contasReceber);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lContasReceber;
    }

    private List<ParcelasModel> buscarParcelas(String idVenda) throws ErroSistemaException {
        lParcelas = new LinkedList<>();
        sqlParcela = "SELECT VP.SEQUENCIA, \n"
                + " VP.TIPO,\n"
                + " VP.VALOR,\n"
                + " VP.VENCIMENTO,\n"
                + " FPGTO.DESCRICAO AS FORMA_PAGAMENTO \n"
                + " FROM INTEGRACAOFL.VENDAS_PARCELAS VP \n"
                + " INNER JOIN INTEGRACAOFL.FORMAS_PAGAMENTOS FPGTO \n"
                + " ON FPGTO.ID_FORMA_PAGAMENTO = VP.FORMA_PAGAMENTO \n"
                + " WHERE VP.VENDA = '" + idVenda + "'";
        System.out.println(" parcela =" + sqlParcela);
        try (PreparedStatement psSelectParcela = Conexao.getConnection().prepareStatement(sqlParcela)) {
            rsSelectParcela = psSelectParcela.executeQuery();
            while (rsSelectParcela.next()) {
                parcela = new ParcelasModel();
                parcela.setSequencia(rsSelectParcela.getInt("SEQUENCIA"));
                parcela.setValor(rsSelectParcela.getDouble("VALOR"));
                parcela.setVencimento(rsSelectParcela.getDate("VENCIMENTO"));
                parcela.setFormaPgto(rsSelectParcela.getString("FORMA_PAGAMENTO"));
                parcela.setTipoParcela(rsSelectParcela.getString("TIPO"));
                lParcelas.add(parcela);
            }
            rsSelectParcela.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lParcelas;
    }

    public List<ParcelaModel> BuscarE301TCR(ConsultaModel filtroConsulta) throws ErroSistemaException {

        lE301TCR = new LinkedList<>();
        LContasReceber_Movivento = new LinkedList<>();
        String sqlSelectE301 = "SELECT  PARCELA.CODEMP, \n"
                + "       FILIAL.ID_FILIAL,\n"
                + "       PARCELA.CODFIL,\n"
                + "       PARCELA.USU_CODCTR AS NUMERO_CONTRATO,\n"
                + "       PARCELA.NUMTIT AS ID_PARCELA,\n"
                + "       PARCELA.CODTPT AS CODTPT,\n"
                + "       PARCELA.USU_IDETCR, \n"
                + "       PARCELA.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       PARCELA.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       LISTAS_ITENS.DESCRICAO AS SITUACAO_CLIENTE,\n"
                + "       PARCELA.DATEMI AS DATA_LANCAMENTO,\n"
                + "       PARCELA.VCTORI AS DATA_VENCIMENTO,\n"
                + "       PARCELA.VLRABE,\n"
                + "       PARCELA.VLRORI,\n"
                + "       PARCELA.ULTPGT AS DATA_PAGAMENTO,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITAL_PAGO,\n"
                + "       PARCELA.SITTIT AS SITUACAO_PARCELA,\n"
                + "       PARCELA.USU_DATNEG AS DATA_NEGATIVACAO,\n"
                + "       PARCELA.USU_DATBAI AS DATA_BAIXA,\n"
                + "       PARCELA.USU_CODAVA AS AVALISTA,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA\n"
                + "  FROM SAPIENS.E301TCR PARCELA\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = PARCELA.NUMTIT\n"
                + "   AND E301MCR.CODEMP = PARCELA.CODEMP\n"
                + "   AND E301MCR.CODFIL = PARCELA.CODFIL\n"
                + " INNER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = PARCELA.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = PARCELA.USU_CODAVA\n"
                + " INNER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = PARCELA.CODFIL\n"
                + "    LEFT OUTER JOIN INTEGRACAOFL.LISTAS_ITENS\n"
                + "    ON LISTAS_ITENS.LISTA = 102\n"
                + "   AND LISTAS_ITENS.VALOR = CLIENTE.USU_SITCLI \n";

        String sqlSelectE301CMR = "AND E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "           FROM SAPIENS.E301MCR\n"
                + "          WHERE E301MCR.NUMTIT = PARCELA.NUMTIT\n"
                + "            AND E301MCR.CODEMP = PARCELA.CODEMP\n"
                + "            AND E301MCR.CODFIL = PARCELA.CODFIL)";

        sqlSelectE301TCR = sqlSelectE301 + getWhereE301(filtroConsulta) + sqlSelectE301CMR;

        System.out.println("slect" + sqlSelectE301TCR);
        try (PreparedStatement psE301TCR = Conexao.getConnection().prepareStatement(sqlSelectE301TCR);) {
            /*    + "       CASE\n"
                + "         WHEN PARCELA.VLRABE < PARCELA.VLRORI AND PARCELA.VLRABE > 0 THEN\n"
                + "          PARCELA.VLRABE\n"
                + "         WHEN PARCELA.VLRABE = PARCELA.VLRORI OR PARCELA.VLRABE = 0 THEN\n"
                + "          PARCELA.VLRORI\n"
                + "       END VALOR,\n"*/
            rsE301TCR = psE301TCR.executeQuery();
            while (rsE301TCR.next()) {
                ParcelaModel parcelasModel = new ParcelaModel();
                parcelasModel.setPontoFilial(rsE301TCR.getString("ID_FILIAL"));
                parcelasModel.setCodCliente(rsE301TCR.getString("CLIENTE"));
                parcelasModel.setNumeroDoContrato(rsE301TCR.getString("NUMERO_CONTRATO"));
                parcelasModel.setNomeDoDevedor(rsE301TCR.getString("NOME_CLIENTE"));
                parcelasModel.setCpfCnpj(rsE301TCR.getString("CGCCPF"));
                parcelasModel.setDataLancamento(rsE301TCR.getDate("DATA_LANCAMENTO"));
                parcelasModel.setDataVencimento(rsE301TCR.getDate("DATA_VENCIMENTO"));
                parcelasModel.setDataAlteracao(rsE301TCR.getDate("DATA_PAGAMENTO"));
                parcelasModel.setDataNegativacao(rsE301TCR.getDate("DATA_NEGATIVACAO"));
                parcelasModel.setDataBaixaNegativacao(rsE301TCR.getDate("DATA_BAIXA"));
                parcelasModel.setValorParcela(rsE301TCR.getDouble("VLRORI"));
                parcelasModel.setValorAberto(rsE301TCR.getDouble("VLRABE"));
                parcelasModel.setCapitalPago(rsE301TCR.getDouble("CAPITAL_PAGO"));
                parcelasModel.setDataPagamento(rsE301TCR.getDate("DATA_PAGAMENTO"));
                parcelasModel.setSituacaoParcela(rsE301TCR.getString("SITUACAO_PARCELA"));
                parcelasModel.setSituacaoDoCliente(rsE301TCR.getString("SITUACAO_CLIENTE"));
                parcelasModel.setCodigoClientParcela(rsE301TCR.getString("CHAVE_SGL"));
                parcelasModel.setAvalista(rsE301TCR.getString("NOME_AVALISTA"));
                if (filtroConsulta.getOrigem_BD().equalsIgnoreCase("NOVO_SGL")) {
                    parcelasModel.setIdParcela(rsE301TCR.getString("ID_PARCELA"));
                    parcelasModel.setCodemp(rsE301TCR.getInt("CODEMP"));
                    parcelasModel.setCodfil(rsE301TCR.getInt("CODFIL"));
                    parcelasModel.setCodTpt(rsE301TCR.getString("CODTPT"));
                }
                if (filtroConsulta.getListMovimento().equalsIgnoreCase("S")) {
                    //  LContasReceber_Movivento = buscarMovimentoNew(rsE301TCR.getString("ID_FILIAL"), rsE301TCR.getString("ID_PARCELA"), rsE301TCR.getString("CLIENTE"), rsE301TCR.getString("USU_IDETCR"));
                    //  parcelasModel.setlMovimentos(LContasReceber_Movivento);
                }
                lE301TCR.add(parcelasModel);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
        return lE301TCR;
    }

    public String getWhereE301(ConsultaModel filtroConsulta) {
        String where = "";

        if (filtroConsulta.getPontoFilial() != null) {
            where += ((filtroConsulta.getPontoFilial() != null) ? " AND FILIAL.FILIAL_SGL = '" + filtroConsulta.getPontoFilial() + "'" : "");
        }
        if (filtroConsulta.getDataInicial() != null && filtroConsulta.getDataFinal() != null) {
            where += ((filtroConsulta.getDataInicial() != null) ? " AND PARCELA.VCTORI  BETWEEN '" + filtroConsulta.getDataInicial() + "'" : "");
            where += ((filtroConsulta.getDataFinal() != null) ? " AND '" + filtroConsulta.getDataFinal() + "'" : "");

        }
        if (filtroConsulta.getDataIniciaPag() != null && filtroConsulta.getDataFinalPag() != null) {
            where += ((filtroConsulta.getDataIniciaPag() != null) ? " AND (PARCELA.ULTPGT BETWEEN '" + filtroConsulta.getDataIniciaPag() + "'" : "");
            where += ((filtroConsulta.getDataFinalPag() != null) ? " AND '" + filtroConsulta.getDataFinalPag() + "'"
                    + "OR(E301MCR.DATPGT BETWEEN '" + filtroConsulta.getDataIniciaPag() + "' AND '" + filtroConsulta.getDataFinalPag() + "' AND E301MCR.TPTRLC = '012'))" : "");
        }
        if (filtroConsulta.getDataInicialNeg() != null && filtroConsulta.getDataFinalNeg() != null) {
            if (filtroConsulta.getDataInicialNeg().equals("31/12/1900")) {
                where += ((filtroConsulta.getDataInicialNeg() != null) ? " AND (PARCELA.USU_DATNEG = '31/12/1900' OR (PARCELA.USU_DATNEG IS NULL))" : "");
            } else {
                where += ((filtroConsulta.getDataInicialNeg() != null) ? " AND PARCELA.USU_DATNEG BETWEEN '" + filtroConsulta.getDataInicialNeg() + "'" : "");
                where += ((filtroConsulta.getDataFinalNeg() != null) ? " AND '" + filtroConsulta.getDataFinalNeg() + "'" : "");
            }
        }
        if (filtroConsulta.getDataInicialBai() != null && filtroConsulta.getDataFinalBai() != null) {
            if (filtroConsulta.getDataInicialBai().equals("31/12/1900")) {
                where += ((filtroConsulta.getDataInicialBai() != null) ? " AND (PARCELA.USU_DATBAI = '31/12/1900' OR(USU_DATBAI IS NULL))" : "");
            } else {
                where += ((filtroConsulta.getDataInicialBai() != null) ? " AND PARCELA.USU_DATBAI BETWEEN '" + filtroConsulta.getDataInicialBai() + "'" : "");
                where += ((filtroConsulta.getDataFinalBai() != null) ? " AND '" + filtroConsulta.getDataFinalBai() + "'" : "");
            }
        }
        if (filtroConsulta.getNumeroDoContrato() != null) {
            where += ((filtroConsulta.getNumeroDoContrato() != null) ? " AND PARCELA.USU_CODCTR LIKE '" + filtroConsulta.getNumeroDoContrato() + "'" : "");
        }
        if (filtroConsulta.getSituacao() != null) {
            where += ((filtroConsulta.getSituacao() != null) ? " AND PARCELA.SITTIT = '" + filtroConsulta.getSituacao() + "'" : "");
        }
        if (filtroConsulta.getCodCliente() != null) {
            where += ((filtroConsulta.getCodCliente() != null) ? " AND PARCELA.CODCLI = '" + filtroConsulta.getCodCliente() + "'" : "");
        }
        if (filtroConsulta.getCpfCnpj() != null) {
            long cpf = Long.parseLong(filtroConsulta.getCpfCnpj());
            where += " AND CLIENTE.CGCCPF = " + cpf;
        }
        if (filtroConsulta.getNome() != null) {
            where += ((filtroConsulta.getNome() != null) ? " AND CLIENTE.NOMCLI LIKE '" + filtroConsulta.getNome() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public boolean extratoCliente(long cgcCpf, String usuario, String loginSO) throws ErroSistemaException {

        try {
            Connection con = Conexao.getConnection();

            HashMap parameters = new HashMap();
            parameters.put("CGCCPF", cgcCpf);
            parameters.put("SITIT", "'AB'");
            parameters.put("usuario", usuario);
            String jasper = "";
            if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
                jasper = "/home/" + loginSO + "/Público/negativador/Relatorios/ExtratoCliente.jasper";
            } else {
               jasper = "\\\\serveraplicacao\\SistemaNegativador\\Relatorios\\ExtratoCliente.jasper";            
            }
            System.out.println("" + jasper);

            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(jasper, parameters, con);
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
            return true;
        } catch (JRException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    private List<ContasReceber_Movimentos> buscarMovimento(String sFilial, String sNumTit, String sCodCli, String sIdeTcr) throws ErroSistemaException {
        int nCodCli = Integer.parseInt(sCodCli);
        String sqlCrMovimento = "SELECT *FROM VCR_MOV WHERE USU_CODFLO = '" + sFilial + "' AND (NUMTIT = '" + sNumTit + "'OR ID_CONTA_RECEBER = '" + sIdeTcr + "') AND CODCLI = " + nCodCli;
        LContasReceber_Movivento = new LinkedList<>();
        ContasReceber_Movimentos crMov;
        try (PreparedStatement psCrMov = Conexao.getConnection().prepareStatement(sqlCrMovimento);) {
            rsCrMov = psCrMov.executeQuery();

            while (rsCrMov.next()) {
                crMov = new ContasReceber_Movimentos();
                crMov.setIdContasReceberMov(rsCrMov.getString("ID_REGISTRO"));
                crMov.setCartao(" ");
                crMov.setCheque(rsCrMov.getString("CHEQUE"));
                crMov.setVoucher(" ");
                crMov.setDataMov(rsCrMov.getDate("DATA"));
                crMov.setTipoMovimento(rsCrMov.getString("TIPO"));
                crMov.setValorDesconto(rsCrMov.getDouble("VALOR_DESCONTO"));
                crMov.setValorJuros(rsCrMov.getDouble("VALOR_JUROS"));
                crMov.setValorCalculado(rsCrMov.getDouble("VALOR_CALCULADO"));
                crMov.setValorPago(rsCrMov.getDouble("VALOR_PAGO"));
                crMov.setCapitalPago(rsCrMov.getDouble("VALOR_CAPITAL_PAGO"));
                crMov.setValorJurosPago(rsCrMov.getDouble("VALOR_JUROS_PAGO"));

                crMov.setSituacao(rsCrMov.getString("SITUACAO_MOVIMENTO"));
                LContasReceber_Movivento.add(crMov);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
        return LContasReceber_Movivento;

    }

    private List<ContasReceberModel> buscarContasReceberVenda(String sCodFil, int codCli, String IdVenda) throws ErroSistemaException {
        sqlSelect = "SELECT AVA.NOME_FANTASIA AS AVALISTA,\n"
                + "         CLI.NOME_FANTASIA AS CLIENTE,\n"
                + "         CR.ID_REGISTRO AS ID_REGISTRO_CR,\n"
                + "         CR.ID_CONTA_RECEBER AS ID_CONTA_RECEBER_CR,\n"
                + "         CR.CLIENTE_ENDERECO AS CLIENTE_ENDERECO_CR,\n"
                + "         CR.DATA_LANCAMENTO AS DATA_LANCAMENTO_CR,\n"
                + "         NVL(CR.DATA_PAGAMENTO, TO_DATE('31/12/1900')) AS DATA_PAGAMENTO_CR,\n"
                + "         CR.VENCIMENTO AS VENCIMENTO_CR,\n"
                + "         CR.NUMERO_PARCELA AS NUMERO_PARCELA_CR,\n"
                + "         CR.SITUACAO AS SITUACAO_CR,\n"
                + "         CR.TIPO AS TIPO_CR,\n"
                + "         CR.OBSERVACAO AS OBSERVACAO_CR,\n"
                + "         NVL(CRM.TIPO_BAIXA, ' ') AS TIPO_BAIXA,\n"
                + "         CASE WHEN CRM.TIPO_BAIXA = 'P' THEN\n"
                + "         'Parcial'\n"
                + "          WHEN CRM.TIPO_BAIXA = 'Q' THEN\n"
                + "         'Quitação'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'D' THEN\n"
                + "         'Devolução'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'C' THEN\n"
                + "         'Cancelado'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'N' THEN\n"
                + "         'Novação'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'E' THEN\n"
                + "         'Perdida'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'R' THEN\n"
                + "         'Recuperada Perdida'\n"
                + "         WHEN CRM.TIPO_BAIXA = 'A' THEN\n"
                + "         'App'\n"
                + "         END DES_TIPO_BAIXA,\n"
                + "         CR.VALOR AS VALOR_CR,\n"
                + "         CR.VALOR_ABERTO AS VALOR_ABERTO_CR,\n"
                + "         CR.VENDA AS VENDA_CR,                      \n"
                + "         CR.NUMTIT AS NUMTIT_CR,\n"
                + "         CR.PREVISAO_PAGAMENTO AS PREVISAO_PAGAMENTO_CR,\n"
                + "         CR.DOCUMENTO_SGL AS DOCUMENTO_SGL_CR,\n"
                + "         CASE\n"
                + "          WHEN CR.SITUACAO = 'A' AND CR.VENCIMENTO < SYSDATE THEN\n"
                + "         TO_DATE(SYSDATE) - TO_DATE(CR.VENCIMENTO)\n"
                + "          WHEN CR.SITUACAO = 'L' THEN\n"
                + "         TO_DATE(CR.DATA_PAGAMENTO) - TO_DATE(CR.VENCIMENTO)\n"
                + "          WHEN CR.SITUACAO = 'A' AND CR.VENCIMENTO > SYSDATE THEN\n"
                + "         0\n"
                + "          WHEN CR.SITUACAO NOT IN ('L', 'A') THEN\n"
                + "         0\n"
                + "         END DIAS,\n"
                + "         CRM.ID_REGISTRO AS ID_REGIDTRO_CRM,\n"
                + "         CRM.ID_CONTA_RECEBER_MOVIMENTO AS ID_CRM,\n"
                + "         CRM.SITUACAO AS SIT_CRM,\n"
                + "         CRM.OBSERVACAO AS OBS_CRM,\n"
                + "         CRM.CHEQUE AS CHEQUE_CRM,\n"
                + "         CRM.VOUCHER AS VOUCHER_CRM,\n"
                + "         CRM.CARTAO_CREDITO AS CARTAO_CREDITO_CRM,\n"
                + "         CRM.DATA AS DATA_CRM,\n"
                + "         CRM.TIPO AS TIPO_CRM,      \n"
                + "         CRM.VALOR_DESCONTO AS VALOR_DESCONTO_CRM,\n"
                + "         CRM.VALOR_JUROS AS VALOR_JUROS_CRM,\n"
                + "         CRM.VALOR_CALCULADO AS VALOR_CALCULADO_CRM,\n"
                + "         CRM.VALOR_PAGO AS VALOR_PAGO_CRM,\n"
                + "         CRM.VALOR_CAPITAL_PAGO AS VALOR_CAPITAL_PAGO_CRM,\n"
                + "         CRM.VALOR_JUROS_PAGO AS VALOR_JUROS_PAGO_CRM,\n"
                + "         CRM.OBSERVACAO AS OBSERVACAO_CRM"
                + "  FROM INTEGRACAOFL.CONTAS_RECEBER CR\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS CLIEND\n"
                + "    ON CLIEND.ID_REGISTRO = CR.CLIENTE_ENDERECO\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES AVA\n"
                + "    ON AVA.ID_REGISTRO = CR.AVALISTA\n"
                + "  LEFT JOIN INTEGRACAOFL.CLIENTES CLI\n"
                + "    ON CLI.ID_REGISTRO = CR.CLIENTE\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CONTAS_RECEBER_MOVIMENTOS CRM\n"
                + "    ON CR.ID_REGISTRO = CRM.CONTA_RECEBER\n"
                + " WHERE CLIEND.CODCLI = " + codCli + " AND CR.LOJA  = '" + sCodFil + "' AND CR.VENDA = '" + IdVenda + "' ORDER BY VENCIMENTO, ID_CONTA_RECEBER_CR, ID_CRM";
        lContasReceber = new LinkedList<>();
        contasReceber = new ContasReceberModel();
        LContasReceber_Movivento = new LinkedList<>();
        contasReceber.setTitulo(" ");
        System.out.println("select vendacr" + sqlSelect);
        try (PreparedStatement psSelectCR = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelectCR.executeQuery();
            while (rsSelect.next()) {
                if (!contasReceber.getTitulo().equalsIgnoreCase(rsSelect.getString("NUMTIT_CR"))) {
                    contasReceber = new ContasReceberModel();
                    LContasReceber_Movivento = new LinkedList<>();
                    contasReceber.setAvalista(rsSelect.getString("AVALISTA"));
                    contasReceber.setCliente(rsSelect.getString("CLIENTE"));
                    contasReceber.setTitulo(rsSelect.getString("NUMTIT_CR"));
                    contasReceber.setClienteEndereco(rsSelect.getString("CLIENTE_ENDERECO_CR"));
                    contasReceber.setDataLancamento(rsSelect.getDate("DATA_LANCAMENTO_CR"));
                    contasReceber.setDataPagamento(rsSelect.getDate("DATA_PAGAMENTO_CR"));
                    contasReceber.setDataVencimento(rsSelect.getDate("VENCIMENTO_CR"));
                    contasReceber.setNumeroParcela(rsSelect.getString("NUMERO_PARCELA_CR"));
                    contasReceber.setPrevisaoPagamento(rsSelect.getDate("PREVISAO_PAGAMENTO_CR"));
                    contasReceber.setSitucao(rsSelect.getString("SITUACAO_CR"));
                    contasReceber.setTipo(rsSelect.getString("TIPO_CR"));
                    contasReceber.setValor(rsSelect.getDouble("VALOR_CR"));
                    contasReceber.setValorAberto(rsSelect.getDouble("VALOR_ABERTO_CR"));
                    contasReceber.setVenda(rsSelect.getString("VENDA_CR"));
                    contasReceber.setTipoBaixa(rsSelect.getString("TIPO_BAIXA"));
                    contasReceber.setDocSgl(rsSelect.getString("DOCUMENTO_SGL_CR"));
                    contasReceber.setObservacao(rsSelect.getString("OBSERVACAO_CR"));
                    ContasReceber_Movimentos crMov = new ContasReceber_Movimentos();
                    crMov.setIdContasReceberMov(rsSelect.getString("ID_CRM"));
                    crMov.setCartao(rsSelect.getString("CARTAO_CREDITO_CRM"));
                    crMov.setCheque(rsSelect.getString("CHEQUE_CRM"));
                    crMov.setVoucher(rsSelect.getString("VOUCHER_CRM"));
                    crMov.setDataMov(rsSelect.getDate("DATA_CRM"));
                    crMov.setTipoMovimento(rsSelect.getString("TIPO_CRM"));
                    crMov.setTipoBaixa(rsSelect.getString("DES_TIPO_BAIXA"));
                    crMov.setValorDesconto(rsSelect.getDouble("VALOR_DESCONTO_CRM"));
                    crMov.setValorJuros(rsSelect.getDouble("VALOR_JUROS_CRM"));
                    crMov.setValorCalculado(rsSelect.getDouble("VALOR_CALCULADO_CRM"));
                    crMov.setValorPago(rsSelect.getDouble("VALOR_PAGO_CRM"));
                    crMov.setCapitalPago(rsSelect.getDouble("VALOR_CAPITAL_PAGO_CRM"));
                    crMov.setValorJurosPago(rsSelect.getDouble("VALOR_JUROS_PAGO_CRM"));
                    crMov.setSituacao(rsSelect.getString("SIT_CRM"));
                    crMov.setObservacao(rsSelect.getString("OBSERVACAO_CRM"));
                    LContasReceber_Movivento.add(crMov);
                } else {
                    ContasReceber_Movimentos crMov = new ContasReceber_Movimentos();
                    crMov.setIdContasReceberMov(rsSelect.getString("ID_CRM"));
                    crMov.setCartao(rsSelect.getString("CARTAO_CREDITO_CRM"));
                    crMov.setCheque(rsSelect.getString("CHEQUE_CRM"));
                    crMov.setVoucher(rsSelect.getString("VOUCHER_CRM"));
                    crMov.setDataMov(rsSelect.getDate("DATA_CRM"));
                    crMov.setTipoMovimento(rsSelect.getString("TIPO_CRM"));
                    crMov.setTipoBaixa(rsSelect.getString("DES_TIPO_BAIXA"));
                    crMov.setValorDesconto(rsSelect.getDouble("VALOR_DESCONTO_CRM"));
                    crMov.setValorJuros(rsSelect.getDouble("VALOR_JUROS_CRM"));
                    crMov.setValorCalculado(rsSelect.getDouble("VALOR_CALCULADO_CRM"));
                    crMov.setValorPago(rsSelect.getDouble("VALOR_PAGO_CRM"));
                    crMov.setCapitalPago(rsSelect.getDouble("VALOR_CAPITAL_PAGO_CRM"));
                    crMov.setValorJurosPago(rsSelect.getDouble("VALOR_JUROS_PAGO_CRM"));
                    crMov.setSituacao(rsSelect.getString("SIT_CRM"));
                    crMov.setObservacao(rsSelect.getString("OBSERVACAO_CRM"));
                    contasReceber.setTipoBaixa(rsSelect.getString("TIPO_BAIXA"));
                    LContasReceber_Movivento.add(crMov);
                }
                contasReceber.setlContasReceber_mov(LContasReceber_Movivento);
                if (lContasReceber.contains(contasReceber)) {
                    lContasReceber.get(lContasReceber.indexOf(contasReceber)).equals(contasReceber);
                } else {
                    lContasReceber.add(contasReceber);
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lContasReceber;
    }

    private List<ContasReceber_Movimentos> buscarMovimentoNew(String sFilial, String sNumTit, String sCodCli, String sIdeTcr) throws ErroSistemaException {
        int nCodCli = Integer.parseInt(sCodCli);
        String sqlCrMovimento = "SELECT *FROM INTEGRACAOFL.CONTAS_RECEBER_MOVIMENTOS CRM WHERE LOJA = '" + sFilial + "' AND (NUMTIT = '" + sNumTit + "'OR CONTA_RECEBER = '" + sIdeTcr + "')";
        LContasReceber_Movivento = new LinkedList<>();
        ContasReceber_Movimentos crMov;
        try (PreparedStatement psCrMov = Conexao.getConnection().prepareStatement(sqlCrMovimento);) {
            rsCrMov = psCrMov.executeQuery();

            while (rsCrMov.next()) {
                crMov = new ContasReceber_Movimentos();
                crMov.setIdContasReceberMov(rsCrMov.getString("ID_REGISTRO"));
                crMov.setCartao(rsCrMov.getString("CARTAO_CREDITO"));
                crMov.setCheque(rsCrMov.getString("CHEQUE"));
                crMov.setVoucher(rsCrMov.getString("VOUCHER"));
                crMov.setDataMov(rsCrMov.getDate("DATA"));
                crMov.setTipoMovimento(rsCrMov.getString("TIPO"));
                crMov.setValorDesconto(rsCrMov.getDouble("VALOR_DESCONTO"));
                crMov.setValorJuros(rsCrMov.getDouble("VALOR_JUROS"));
                crMov.setValorCalculado(rsCrMov.getDouble("VALOR_CALCULADO"));
                crMov.setValorPago(rsCrMov.getDouble("VALOR_PAGO"));
                crMov.setCapitalPago(rsCrMov.getDouble("VALOR_CAPITAL_PAGO"));
                crMov.setValorJurosPago(rsCrMov.getDouble("VALOR_JUROS_PAGO"));
                crMov.setSituacao(rsCrMov.getString("SITUACAO"));
                crMov.setObservacao(rsCrMov.getString("OBSERVACAO"));
                LContasReceber_Movivento.add(crMov);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
        return LContasReceber_Movivento;

    }

    public double buscarValorDevolucao(String idRegistro, String cliente) throws ErroSistemaException {
        sqlSelect = "SELECT SUM(VALOR) AS VALOR_DEVOLVIDO FROM INTEGRACAOFL.DEVOLUCOES WHERE VENDA = '" + idRegistro + "' AND CLIENTE = '" + cliente + "'";
        double VlrDevolvido = 0;
        try (PreparedStatement psSelectVlrDev = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelectVlrDev.executeQuery();
            if (rsSelect.next()) {
                VlrDevolvido = rsSelect.getDouble("VALOR_DEVOLVIDO");
            }
            rsSelect.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return VlrDevolvido;
    }

    public void AtualizarContratoE301TCR(ParcelaModel parcela) throws ErroSistemaException {
        String sqlUpdateE301 = ""
                + "UPDATE SAPIENS.E301TCR \n"
                + "   SET USU_CODCTR = ? \n"
                + " WHERE CODEMP = ? \n"
                + "   AND CODFIL = ? \n"
                + "   AND NUMTIT = ? \n"
                + "   AND CODCLI = ? ";
        
        try (PreparedStatement psUpdateE301TCR = Conexao.getConnection().prepareStatement(sqlUpdateE301)) {
           psUpdateE301TCR.setString(1, parcela.getNumeroDoContrato());
           psUpdateE301TCR.setInt(2, parcela.getCodemp());
           psUpdateE301TCR.setInt(3, parcela.getCodfil());
           psUpdateE301TCR.setString(4, parcela.getIdParcela());
           psUpdateE301TCR.setString(5, parcela.getCodCliente());
           psUpdateE301TCR.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();           
        }
    }
}
