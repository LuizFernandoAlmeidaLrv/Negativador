/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view;

import br.com.martinello.bd.matriz.control.ExportarExcelControl;
import br.com.martinello.bd.matriz.control.ExtracaoTableController;
import br.com.martinello.bd.matriz.control.FilialController;
import br.com.martinello.bd.matriz.control.ParcelasExtracaoController;
import br.com.martinello.bd.matriz.model.domain.ConsultaClienteControl;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ContasReceberControl;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.Parcela_Movimentos;
import br.com.martinello.bd.matriz.model.domain.UsuarioLogin;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.consulta.Domain.ConsultaClienteModel;
import br.com.martinello.consulta.Domain.ConsultaSpcBvsModel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
import br.com.martinello.consulta.Domain.DadosComerciaisModel;
import br.com.martinello.consulta.Domain.EnderecoModel;
import br.com.martinello.consulta.Domain.HistoricoCliente;
import br.com.martinello.consulta.Domain.ObservacaoModel;
import br.com.martinello.consulta.Domain.ParcelasModel;
import br.com.martinello.consulta.Domain.ReferenciasModel;
import br.com.martinello.consulta.Domain.VendasItensModel;
import br.com.martinello.consulta.Domain.VendasModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author luiz.almeida
 */
public class TelaContasReceber extends TelaPadrao {

    public static String retornoExtracao;
    private boolean retornoConsulta;
    private int contFiltro;
    private ConsultaModel filtroConsulta;
    public List<ConsultaModel> lmotivoBaixaBvs, lmotivoInclusao, lmotivoBaixaSpc, lnaturezaRegistro, lnaturezaInclusao;
    public ExtracaoTableController extracaoTableController;
    public FilialController filiaisControler = new FilialController();
    public static List<FilialModel> lfiliaisModel = new ArrayList();
    private List<ParcelaModel> lParcelaManual;
    public List<ExtracaoModel> lExtracaoModel;
    public static List<VendasModel> lVendas;
    public static List<VendasItensModel> lVendasItens;
    public static List<ContasReceberModel> lContasReceber;
    public static List<ParcelasModel> lParcelas;
    public ExtracaoModel extracaoModel;
    public ParcelasExtracaoController parcelaExtracao;
    public ConsultaClienteControl consultaClienteControl;
    public ContasReceberControl contasReceberControl;
    public static ConsultaClienteModel consultaCliente;
    public static VendasModel vendas;
    public ParcelaModel parcela;
    public int tQuitacao, tParcial, tRenegociacao, tDevolucao, tTransf, tPerdida;
    public double valorTraz, valorCalc, valorAberto, valorParcela, valorOriginal, valorVencer, valorDevolucao;
    private boolean resultado;
    private String valorCopiado;
    private static String sCodFil;
    private static int codCli;

    private final ObjectTableModel<ParcelaModel> otmParcelasManual = new ObjectTableModel<>(ParcelaModel.class, "pontoFilial,idParcela,numeroDoContrato,codCliente,cpfCnpj,"
            + "nomeDoDevedor,situacaoDoCliente,dataLancamento,dataVencimento,valorParcela,valorAberto,dataPagamento,capitalPago,situacaoParcela,dataNegativacao,dataBaixaNegativacao,"
            + "dataAlteracao,avalista,codigoClientParcela");
    private final ObjectTableModel<VendasModel> otmVendas = new ObjectTableModel<>(VendasModel.class, "idVenda,operacao,tipoVenda,dataVenda,cliente,clienteEndereco,"
            + "nomeCliente,vendedor,vlrAvista,vlrAprazo,situacao,devolvida");
    private final ObjectTableModel<VendasItensModel> otmVendasItens = new ObjectTableModel<>(VendasItensModel.class, "tipoEntrega,produto,estoque,calcJuros,serial,"
            + "numeroSerie,observacao,local,localLoja,vlrAvista,vlrTotAvista,vlrTotAprazo,quantidade");
    private final ObjectTableModel<EnderecoModel> otmClienteEndereco = new ObjectTableModel<>(EnderecoModel.class, "tipoDocumento,documento,descricao,endLogradouro,endNumero,endComplemento,"
            + "endBairro,cidade,ufEstado,cep,codigoIbge");
    private final ObjectTableModel<ReferenciasModel> otmReferencias = new ObjectTableModel<>(ReferenciasModel.class, "tipo,dataConsulta,referencia,resultado,dataCadastro,dataUltCompra,"
            + "diasMedios,valorUltCompra,valorAberto,valorQuitado,valorEmAberto,tipoPagamento");
    private final ObjectTableModel<ConsultaSpcBvsModel> otmConsulta = new ObjectTableModel<>(ConsultaSpcBvsModel.class, "tipo,data,resultado,usuario");

    private final ObjectTableModel<ParcelasModel> otmParcela = new ObjectTableModel<>(ParcelasModel.class, "sequencia,formaPgto,tipoParcela,vencimento,valor");

    private final ObjectTableModel<DadosComerciaisModel> otmDadosComerciais = new ObjectTableModel<>(DadosComerciaisModel.class, "empresa,ocupacao,cargo,dataAdmicao,telefone,contato,dataGeracao,"
            + "usuarioGeracao,DataAlteracao,usuarioAlteracao");
    private final ObjectTableModel<HistoricoCliente> otmHistorico = new ObjectTableModel<>(HistoricoCliente.class, "tipo,data,usuario");
    private final ObjectTableModel<ObservacaoModel> otmObservacao = new ObjectTableModel<>(ObservacaoModel.class, "clienteEndereco,tipo,descricao,dataGeracao,usuarioGer");
    private final ObjectTableModel<ContasReceberModel> otmContasReceber = new ObjectTableModel<>(ContasReceberModel.class, "venda,tipo,dataLancamento,cliente,clienteEndereco,numeroParcela,dataVencimento,dataPagamento,valor,valorAberto,situcao,avalista");
    private final ObjectTableModel<Parcela_Movimentos> otmCrMov = new ObjectTableModel<>(Parcela_Movimentos.class, "idContasReceberMov,cartao,cheque,voucher,dataMov,tipoMovimento,valorDesconto,valorJuros,valorCalculado,valorPago,capitalPago,valorJurosPago,situacao,observacao");

    private final ObjectTableModel<ContasReceberModel> otmContasReceberVendas = new ObjectTableModel<>(ContasReceberModel.class,
            "venda,tipo,dataLancamento,cliente,clienteEndereco,docSgl,numeroParcela,dataVencimento,dataPagamento,previsaoPagamento,valor,valorAberto,situcao,avalista");

    /**
     * Creates new form ExtracaoManual
     */
    public TelaContasReceber() {

        //super("Tela Extração Manual");
        initComponents();
        carregarFiliais();
        Calendar agora, cincoAnos, trintaDias, ontem;
        agora = Calendar.getInstance();
        cincoAnos = Calendar.getInstance();
        trintaDias = Calendar.getInstance();
        ontem = Calendar.getInstance();
        // formata e exibe a data e hora atual
        Format formato = new SimpleDateFormat("dd/MM/yyyy");
        agora.add(Calendar.DATE, 0);
        ontem.add(Calendar.DATE, -1);
        trintaDias.add(Calendar.DATE, -30);
        cincoAnos.add(Calendar.DATE, -1825);
        otmParcelasManual.setColEditable(2, true);
        // formata e exibe o resultado
        formato = new SimpleDateFormat("dd/MM/yyyy");
        bFiltroPesquisar.setMnemonic(KeyEvent.VK_P);
        bFiltroCancelar.setMnemonic(KeyEvent.VK_C);
        bFiltroVisualizar.setMnemonic(KeyEvent.VK_V);
        if (TelaPrincipal.permissao.equalsIgnoreCase("N")) {
            jbAlterar.setEnabled(false);
        }
        tpVendas.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpVendas.getSelectedRow() >= 0) {
                setVendaItens();
                setParcelas();
                setContasReceber();
            }
        });

        tpExtrManual.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpExtrManual.getSelectedRow() >= 0) {
                setCrMov();
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPStatusExtracaoManual = new br.com.martinello.componentesbasicos.paineis.JPStatus();
        paExtracaoManual = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppBuscaExtrManual = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroBuscaExtrManual = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rFiltroFilial = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroNome = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroCpf = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroCodigoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroContrato = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroDataVencimento = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroDataPagamento = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroFilial = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cscFiltroNome = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        ccccFiltroCpf = new br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta();
        csFiltroCodigoCliente = new br.com.martinello.componentesbasicos.CampoString();
        csFiltroContrato = new br.com.martinello.componentesbasicos.CampoString();
        cdFiltroVencInicial = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroPagInicio = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroVencFinal = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroPagFinal = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroDataNegativacao = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroNegInicio = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroNegFinal = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroDataBaixa = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroBaixaInicio = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroBaixaFinal = new br.com.martinello.componentesbasicos.CampoData();
        bFiltroPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroVisualizar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroCancelar = new br.com.martinello.componentesbasicos.Botao();
        rFiltroSituacao = new br.com.martinello.componentesbasicos.Rotulo();
        clsSituacao = new br.com.martinello.componentesbasicos.CampoListaSimples();
        bFiltroPlanilhaGrid = new br.com.martinello.componentesbasicos.Botao();
        bFiltroVisualizar2 = new br.com.martinello.componentesbasicos.Botao();
        rFiltroSituacao1 = new br.com.martinello.componentesbasicos.Rotulo();
        clsSituacao1 = new br.com.martinello.componentesbasicos.CampoListaSimples();
        jbAlterar = new br.com.martinello.componentesbasicos.Botao();
        painelPadrao1 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rNomeQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotalR = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotal = new br.com.martinello.componentesbasicos.Rotulo();
        paParcelas = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppParcelas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspBuscaExtrManual = new javax.swing.JScrollPane();
        tpExtrManual = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppMovimentos = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane8 = new javax.swing.JScrollPane();
        tpCrMov = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppClienteAba = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        paCliente = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppCliente = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rotulo2 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo3 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo4 = new br.com.martinello.componentesbasicos.Rotulo();
        csRazaoSocial = new br.com.martinello.componentesbasicos.CampoString();
        rotulo6 = new br.com.martinello.componentesbasicos.Rotulo();
        csNomeFantazia = new br.com.martinello.componentesbasicos.CampoString();
        rotulo7 = new br.com.martinello.componentesbasicos.Rotulo();
        csEmail = new br.com.martinello.componentesbasicos.CampoString();
        rotulo8 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo9 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo10 = new br.com.martinello.componentesbasicos.Rotulo();
        csContato = new br.com.martinello.componentesbasicos.CampoString();
        rotulo11 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo14 = new br.com.martinello.componentesbasicos.Rotulo();
        clsTipoPessoa = new br.com.martinello.componentesbasicos.CampoListaSimples();
        clsNegativado = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cccCpfCnpj = new br.com.martinello.componentesbasicos.CampoCpfCnpj();
        ctTelefone = new br.com.martinello.componentesbasicos.CampoTelefone();
        ctCelular = new br.com.martinello.componentesbasicos.CampoTelefone();
        ciCodCli = new br.com.martinello.componentesbasicos.CampoInteiro();
        rotulo1 = new br.com.martinello.componentesbasicos.Rotulo();
        clsSitCli = new br.com.martinello.componentesbasicos.CampoListaSimples();
        ppEndereco = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pTableEndereco = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpEndereco = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppDadosCompl = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pDadosCompl = new br.com.martinello.componentesbasicos.paineis.Painel();
        PDadosConjugue = new br.com.martinello.componentesbasicos.paineis.Painel();
        rCpf = new br.com.martinello.componentesbasicos.Rotulo();
        rNaturalidaConj = new br.com.martinello.componentesbasicos.Rotulo();
        rDocumento = new br.com.martinello.componentesbasicos.Rotulo();
        rNomePaiConj = new br.com.martinello.componentesbasicos.Rotulo();
        rNomeConj = new br.com.martinello.componentesbasicos.Rotulo();
        rDataNascConj = new br.com.martinello.componentesbasicos.Rotulo();
        rNomeMaeConj = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloDocumento = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNaturalidadeConj = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNomePaiConj = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNomeConj = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNomeMaeConj = new br.com.martinello.componentesbasicos.Rotulo();
        cdNascConj = new br.com.martinello.componentesbasicos.CampoData();
        cccCpfConjuge = new br.com.martinello.componentesbasicos.CampoCpfCnpj();
        pDadosPessoais = new br.com.martinello.componentesbasicos.paineis.Painel();
        rSexo = new br.com.martinello.componentesbasicos.Rotulo();
        rNomePai = new br.com.martinello.componentesbasicos.Rotulo();
        rEstadoCivil = new br.com.martinello.componentesbasicos.Rotulo();
        clsSexo = new br.com.martinello.componentesbasicos.CampoListaSimples();
        clsEstadoCivil = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rDataNascimento = new br.com.martinello.componentesbasicos.Rotulo();
        cdNasc = new br.com.martinello.componentesbasicos.CampoData();
        rNaturalidade = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNomePai = new br.com.martinello.componentesbasicos.Rotulo();
        rNomeMae = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNomeMae = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloNaturalidade = new br.com.martinello.componentesbasicos.Rotulo();
        pDadosRendaMoradia = new br.com.martinello.componentesbasicos.paineis.Painel();
        pDadosRenda = new br.com.martinello.componentesbasicos.paineis.Painel();
        rRenda = new br.com.martinello.componentesbasicos.Rotulo();
        rRendaCompl = new br.com.martinello.componentesbasicos.Rotulo();
        rDespesasMoradia = new br.com.martinello.componentesbasicos.Rotulo();
        rDespesasMensal = new br.com.martinello.componentesbasicos.Rotulo();
        rNumeroDependentes = new br.com.martinello.componentesbasicos.Rotulo();
        rValorPatrimonio = new br.com.martinello.componentesbasicos.Rotulo();
        ciNumeroDependentes = new br.com.martinello.componentesbasicos.CampoInteiro();
        cvRenda = new br.com.martinello.componentesbasicos.CampoValor();
        cvRendaCompl = new br.com.martinello.componentesbasicos.CampoValor();
        cvDespesasMoradia = new br.com.martinello.componentesbasicos.CampoValor();
        cvDespesasMensal = new br.com.martinello.componentesbasicos.CampoValor();
        cvValorParimonio = new br.com.martinello.componentesbasicos.CampoValor();
        rDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        rotuloDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        pDadosResidencia = new br.com.martinello.componentesbasicos.paineis.Painel();
        rTipoMoradia = new br.com.martinello.componentesbasicos.Rotulo();
        rDataMoradia = new br.com.martinello.componentesbasicos.Rotulo();
        clsTipoMorradia = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cdMoradia = new br.com.martinello.componentesbasicos.CampoData();
        ppReferencia = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pTableReferencias = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tpReferencias = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppConsultas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pTableConsultas = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpConsulta = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppDadosComerciais = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pTableDadosComerciais = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpDadosComerciais = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppHistorico = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane5 = new javax.swing.JScrollPane();
        tpHistorico = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppObservacao = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane6 = new javax.swing.JScrollPane();
        tpObservacao = new br.com.martinello.componentesbasicos.TabelaPadrao();
        pCliente = new br.com.martinello.componentesbasicos.paineis.Painel();
        botao1 = new br.com.martinello.componentesbasicos.Botao();
        botao2 = new br.com.martinello.componentesbasicos.Botao();
        ppVenda = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppVendas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspVendas = new javax.swing.JScrollPane();
        tpVendas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        paItensParcelas = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppVendasItens = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspVendasItens = new javax.swing.JScrollPane();
        tpVendasItens = new br.com.martinello.componentesbasicos.TabelaPadrao();
        painelPadrao2 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        tp = new javax.swing.JScrollPane();
        tpParcelas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppCR = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane9 = new javax.swing.JScrollPane();
        tpCrVendas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppContasReceber = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        pContasReceber = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tpContasReceber = new br.com.martinello.componentesbasicos.TabelaPadrao();
        pTotais = new br.com.martinello.componentesbasicos.paineis.Painel();
        pParcelasCliente = new br.com.martinello.componentesbasicos.paineis.Painel();
        rGeralCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rVencidosCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rDiasMediosCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rIndDebitoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        cvGeralCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvVencidosCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvDiasMedioCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvIndDebitoCliente = new br.com.martinello.componentesbasicos.CampoValor();
        rVencerCli = new br.com.martinello.componentesbasicos.Rotulo();
        rAbertoCli = new br.com.martinello.componentesbasicos.Rotulo();
        rIndAtrazoCli = new br.com.martinello.componentesbasicos.Rotulo();
        cvAbertoCli = new br.com.martinello.componentesbasicos.CampoValor();
        cvVencerCli = new br.com.martinello.componentesbasicos.CampoValor();
        cvIndAtrazoCli = new br.com.martinello.componentesbasicos.CampoValor();
        pParcelasAvalista = new br.com.martinello.componentesbasicos.paineis.Painel();
        cvVencidosAva = new br.com.martinello.componentesbasicos.CampoValor();
        rVencidosAva = new br.com.martinello.componentesbasicos.Rotulo();
        rGeralAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvGeralAva = new br.com.martinello.componentesbasicos.CampoValor();
        rDiasMediosAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvDiasMedioAva = new br.com.martinello.componentesbasicos.CampoValor();
        rVencerAva = new br.com.martinello.componentesbasicos.Rotulo();
        rAbertoAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvVencerAva = new br.com.martinello.componentesbasicos.CampoValor();
        cvAbertoAva = new br.com.martinello.componentesbasicos.CampoValor();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta Contas Receber");
        setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().add(jPStatusExtracaoManual, java.awt.BorderLayout.PAGE_END);

        paExtracaoManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paExtracaoManualMouseClicked(evt);
            }
        });

        ppBuscaExtrManual.setLayout(new java.awt.BorderLayout());

        ppFiltroBuscaExtrManual.setPreferredSize(new java.awt.Dimension(1038, 180));
        ppFiltroBuscaExtrManual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppFiltroBuscaExtrManualKeyPressed(evt);
            }
        });

        rFiltroFilial.setText("Empresa:");

        rFiltroNome.setText("Nome:");

        rFiltroCpf.setText("CPF/CNPJ:");

        rFiltroCodigoCliente.setText("Código Cliente:");

        rFiltroContrato.setText("Contrato:");

        rFiltroDataVencimento.setText("Data Vencimento:");

        rFiltroDataPagamento.setText("Data Pagamento:");

        csFiltroCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csFiltroCodigoClienteActionPerformed(evt);
            }
        });

        cdFiltroVencInicial.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroVencInicial.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroVencInicial.setOpaque(true);
        cdFiltroVencInicial.setPreferredSize(null);
        cdFiltroVencInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroVencInicialActionPerformed(evt);
            }
        });

        cdFiltroPagInicio.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroPagInicio.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroPagInicio.setOpaque(true);
        cdFiltroPagInicio.setPreferredSize(null);

        cdFiltroVencFinal.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroVencFinal.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroVencFinal.setOpaque(true);
        cdFiltroVencFinal.setPreferredSize(null);

        cdFiltroPagFinal.setMaximumSize(new java.awt.Dimension(99, 21));
        cdFiltroPagFinal.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroPagFinal.setOpaque(true);
        cdFiltroPagFinal.setPreferredSize(new java.awt.Dimension(105, 21));

        rFiltroDataNegativacao.setText("Data Negativação:");

        cdFiltroNegInicio.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroNegInicio.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroNegInicio.setOpaque(true);
        cdFiltroNegInicio.setPreferredSize(null);

        cdFiltroNegFinal.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroNegFinal.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroNegFinal.setOpaque(true);
        cdFiltroNegFinal.setPreferredSize(null);

        rFiltroDataBaixa.setText("Data Baixa:");

        cdFiltroBaixaInicio.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroBaixaInicio.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroBaixaInicio.setOpaque(true);
        cdFiltroBaixaInicio.setPreferredSize(null);

        cdFiltroBaixaFinal.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroBaixaFinal.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroBaixaFinal.setOpaque(true);
        cdFiltroBaixaFinal.setPreferredSize(null);

        bFiltroPesquisar.setText("Pesquisar");
        bFiltroPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPesquisarActionPerformed(evt);
            }
        });

        bFiltroVisualizar.setText("Visualizar");
        bFiltroVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroVisualizarActionPerformed(evt);
            }
        });

        bFiltroCancelar.setText("Cancelar");
        bFiltroCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroCancelarActionPerformed(evt);
            }
        });

        rFiltroSituacao.setText("Situação:");

        clsSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aberto", "Liquidado Quitação", "Liquidado Substituição", "Cancelado", " " }));

        bFiltroPlanilhaGrid.setText("Planilha Grid");
        bFiltroPlanilhaGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPlanilhaGridActionPerformed(evt);
            }
        });

        bFiltroVisualizar2.setText("Extrato");
        bFiltroVisualizar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroVisualizar2ActionPerformed(evt);
            }
        });

        rFiltroSituacao1.setText("Movimento:");

        clsSituacao1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sim", "Nao", " " }));
        clsSituacao1.setSelectedIndex(1);

        jbAlterar.setText("Alterar");
        jbAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroBuscaExtrManualLayout = new javax.swing.GroupLayout(ppFiltroBuscaExtrManual);
        ppFiltroBuscaExtrManual.setLayout(ppFiltroBuscaExtrManualLayout);
        ppFiltroBuscaExtrManualLayout.setHorizontalGroup(
            ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(csFiltroContrato, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rFiltroSituacao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rFiltroDataPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rFiltroDataNegativacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rFiltroDataBaixa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rFiltroDataVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rFiltroSituacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cdFiltroBaixaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroNegInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroPagInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroVencInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsSituacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cdFiltroVencFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(cdFiltroPagFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroNegFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroBaixaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 468, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addComponent(bFiltroPlanilhaGrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bFiltroVisualizar2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bFiltroVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        ppFiltroBuscaExtrManualLayout.setVerticalGroup(
            ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroVencInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroVencFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroPagInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroPagFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addComponent(rFiltroDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rFiltroDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroDataNegativacao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroNegInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroNegFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rFiltroDataBaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroBaixaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroBaixaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(clsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bFiltroVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bFiltroVisualizar2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bFiltroPlanilhaGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroSituacao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsSituacao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(rFiltroSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(csFiltroContrato, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroContrato, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        ppBuscaExtrManual.add(ppFiltroBuscaExtrManual, java.awt.BorderLayout.PAGE_START);

        painelPadrao1.setPreferredSize(new java.awt.Dimension(1038, 30));

        rNomeQuantidade.setText("Quant:");
        rNomeQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rQuantidade.setForeground(new java.awt.Color(204, 0, 0));
        rQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotalR.setText("Valor:");
        rValorTotalR.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotal.setForeground(new java.awt.Color(204, 0, 0));
        rValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rValorTotal.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        javax.swing.GroupLayout painelPadrao1Layout = new javax.swing.GroupLayout(painelPadrao1);
        painelPadrao1.setLayout(painelPadrao1Layout);
        painelPadrao1Layout.setHorizontalGroup(
            painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rNomeQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rValorTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1032, Short.MAX_VALUE))
        );
        painelPadrao1Layout.setVerticalGroup(
            painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rValorTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rNomeQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppBuscaExtrManual.add(painelPadrao1, java.awt.BorderLayout.SOUTH);

        ppParcelas.setLayout(new java.awt.BorderLayout());

        tpExtrManual.setModel(otmParcelasManual);
        tpExtrManual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpExtrManualFocusLost(evt);
            }
        });
        tpExtrManual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpExtrManualKeyPressed(evt);
            }
        });
        jspBuscaExtrManual.setViewportView(tpExtrManual);

        ppParcelas.add(jspBuscaExtrManual, java.awt.BorderLayout.CENTER);

        paParcelas.addTab("Parcelas", ppParcelas);

        ppMovimentos.setLayout(new java.awt.BorderLayout());

        tpCrMov.setModel(otmCrMov);
        jScrollPane8.setViewportView(tpCrMov);

        ppMovimentos.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        paParcelas.addTab("Parcelas Movimento", ppMovimentos);

        ppBuscaExtrManual.add(paParcelas, java.awt.BorderLayout.CENTER);

        paExtracaoManual.addTab("Consulta", ppBuscaExtrManual);

        ppClienteAba.setLayout(new java.awt.BorderLayout());

        rotulo2.setText("Id. Cliente:");

        rotulo3.setText("Tipo:");

        rotulo4.setText("Cpf/Cnpj:");

        csRazaoSocial.setEditable(false);

        rotulo6.setText("Razão Social:");

        csNomeFantazia.setEditable(false);

        rotulo7.setText("Nome Fantazia:");

        csEmail.setEditable(false);

        rotulo8.setText("Email:");

        rotulo9.setText("Telefone:");

        rotulo10.setText("Celular:");

        csContato.setEditable(false);

        rotulo11.setText("Contato:");

        rotulo14.setText("Negativado:");

        clsTipoPessoa.setToolTipText("Fisica\nJurídica");

        clsNegativado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Baixado" }));
        clsNegativado.setEnabled(false);

        cccCpfCnpj.setEditable(false);

        ctTelefone.setEditable(false);

        ctCelular.setEditable(false);

        ciCodCli.setEditable(false);

        rotulo1.setText("Situação:");

        clsSitCli.setDescricaoRotulo("");
        clsSitCli.setEnabled(false);

        javax.swing.GroupLayout ppClienteLayout = new javax.swing.GroupLayout(ppCliente);
        ppCliente.setLayout(ppClienteLayout);
        ppClienteLayout.setHorizontalGroup(
            ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppClienteLayout.createSequentialGroup()
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotulo4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotulo14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cccCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csNomeFantazia, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ctTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ctCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csContato, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsNegativado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ciCodCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clsTipoPessoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                    .addGroup(ppClienteLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsSitCli, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(645, Short.MAX_VALUE))
        );
        ppClienteLayout.setVerticalGroup(
            ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsTipoPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cccCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csNomeFantazia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rotulo11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csContato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsNegativado, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsSitCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(404, Short.MAX_VALUE))
        );

        paCliente.addTab("Cliente", ppCliente);

        ppEndereco.setLayout(new java.awt.BorderLayout());

        pTableEndereco.setLayout(new java.awt.BorderLayout());

        tpEndereco.setModel(otmClienteEndereco);
        jScrollPane1.setViewportView(tpEndereco);

        pTableEndereco.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        ppEndereco.add(pTableEndereco, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Endereços", ppEndereco);

        ppDadosCompl.setLayout(new java.awt.BorderLayout());

        pDadosCompl.setPreferredSize(new java.awt.Dimension(1024, 350));
        pDadosCompl.setLayout(new java.awt.BorderLayout());

        PDadosConjugue.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Cônjuge"));

        rCpf.setText("CPF:");

        rNaturalidaConj.setText("Naturalidade:");

        rDocumento.setText("Documento:");

        rNomePaiConj.setText("Nome Pai:");

        rNomeConj.setText("Nome:");

        rDataNascConj.setText("Data Nascimento:");

        rNomeMaeConj.setText("Nome Mãe:");

        rotuloDocumento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rotuloNaturalidadeConj.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rotuloNomePaiConj.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rotuloNomeConj.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rotuloNomeMaeConj.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        cdNascConj.setEditable(false);

        cccCpfConjuge.setEditable(false);

        javax.swing.GroupLayout PDadosConjugueLayout = new javax.swing.GroupLayout(PDadosConjugue);
        PDadosConjugue.setLayout(PDadosConjugueLayout);
        PDadosConjugueLayout.setHorizontalGroup(
            PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PDadosConjugueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addComponent(rDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotuloDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addComponent(rCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cccCpfConjuge, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addComponent(rNaturalidaConj, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotuloNaturalidadeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addComponent(rNomePaiConj, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotuloNomePaiConj, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56)
                .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rNomeConj, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(rDataNascConj, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(rNomeMaeConj, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cdNascConj, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotuloNomeMaeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotuloNomeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(389, Short.MAX_VALUE))
        );
        PDadosConjugueLayout.setVerticalGroup(
            PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PDadosConjugueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cccCpfConjuge, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rNaturalidaConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNaturalidadeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rNomePaiConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNomePaiConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PDadosConjugueLayout.createSequentialGroup()
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rNomeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNomeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rDataNascConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdNascConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PDadosConjugueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rNomeMaeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNomeMaeConj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        pDadosCompl.add(PDadosConjugue, java.awt.BorderLayout.CENTER);

        pDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));
        pDadosPessoais.setPreferredSize(new java.awt.Dimension(1040, 120));

        rSexo.setText("Sexo:");

        rNomePai.setText("Nome do Pai:");

        rEstadoCivil.setText("Estado Civil:");

        clsSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Feminino" }));
        clsSexo.setEnabled(false);

        clsEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "S - Solteiro(a)", "C - Casado(a)", "J - Separado(a) Judicialmente", "D - Divorciado(a)", "V - Viúvo(a)", "U - União estavel", "O - Outros" }));
        clsEstadoCivil.setEnabled(false);

        rDataNascimento.setText("Data Nascimento:");

        cdNasc.setEditable(false);

        rNaturalidade.setText("Naturalidade:");

        rotuloNomePai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rNomeMae.setText("Nome Mãe:");

        rotuloNomeMae.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rotuloNaturalidade.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout pDadosPessoaisLayout = new javax.swing.GroupLayout(pDadosPessoais);
        pDadosPessoais.setLayout(pDadosPessoaisLayout);
        pDadosPessoaisLayout.setHorizontalGroup(
            pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clsEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(rNomeMae, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rotuloNomeMae, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(clsSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cdNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rotuloNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        pDadosPessoaisLayout.setVerticalGroup(
            pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rNomeMae, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNomeMae, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                        .addComponent(rSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(pDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clsSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rotuloNaturalidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotuloNomePai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pDadosCompl.add(pDadosPessoais, java.awt.BorderLayout.NORTH);

        ppDadosCompl.add(pDadosCompl, java.awt.BorderLayout.NORTH);

        pDadosRendaMoradia.setLayout(new java.awt.BorderLayout());

        pDadosRenda.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Renda"));
        pDadosRenda.setPreferredSize(new java.awt.Dimension(1024, 200));

        rRenda.setText("Renda:");

        rRendaCompl.setText("Renda Complementar");

        rDespesasMoradia.setText("Despesas Moradia:");

        rDespesasMensal.setText("Despesas Mensal:");

        rNumeroDependentes.setText("Número Dependentes:");

        rValorPatrimonio.setText("Valor Patrimônio:");

        ciNumeroDependentes.setEditable(false);
        ciNumeroDependentes.setDescricaoRotulo("");

        cvRenda.setEditable(false);

        cvRendaCompl.setEditable(false);

        cvDespesasMoradia.setEditable(false);

        cvDespesasMensal.setEditable(false);

        cvValorParimonio.setEditable(false);

        rDescricao.setText("Descrição:");

        rotuloDescricao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout pDadosRendaLayout = new javax.swing.GroupLayout(pDadosRenda);
        pDadosRenda.setLayout(pDadosRendaLayout);
        pDadosRendaLayout.setHorizontalGroup(
            pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosRendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rRenda, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rRendaCompl, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDespesasMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDespesasMensal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rNumeroDependentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rValorPatrimonio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ciNumeroDependentes, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvRenda, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(cvRendaCompl, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(cvDespesasMoradia, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(cvDespesasMensal, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(cvValorParimonio, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rotuloDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(343, Short.MAX_VALUE))
        );
        pDadosRendaLayout.setVerticalGroup(
            pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosRendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rRenda, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvRenda, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rRendaCompl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvRendaCompl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotuloDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDespesasMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDespesasMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDespesasMensal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDespesasMensal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rNumeroDependentes, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciNumeroDependentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDadosRendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rValorPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvValorParimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pDadosRendaMoradia.add(pDadosRenda, java.awt.BorderLayout.CENTER);

        pDadosResidencia.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Residência"));
        pDadosResidencia.setPreferredSize(new java.awt.Dimension(1359, 50));

        rTipoMoradia.setText("Tipo:");

        rDataMoradia.setText("Data:");

        clsTipoMorradia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 - Própria", "2 - Alugada", "3 - Financiada", "4 - Cessão", "5 - Outros" }));
        clsTipoMorradia.setEnabled(false);

        cdMoradia.setEditable(false);

        javax.swing.GroupLayout pDadosResidenciaLayout = new javax.swing.GroupLayout(pDadosResidencia);
        pDadosResidencia.setLayout(pDadosResidenciaLayout);
        pDadosResidenciaLayout.setHorizontalGroup(
            pDadosResidenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosResidenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rTipoMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clsTipoMorradia, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178)
                .addComponent(rDataMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(cdMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(609, Short.MAX_VALUE))
        );
        pDadosResidenciaLayout.setVerticalGroup(
            pDadosResidenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosResidenciaLayout.createSequentialGroup()
                .addGroup(pDadosResidenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rTipoMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDataMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsTipoMorradia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cdMoradia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        pDadosRendaMoradia.add(pDadosResidencia, java.awt.BorderLayout.NORTH);

        ppDadosCompl.add(pDadosRendaMoradia, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Dados Complementares", ppDadosCompl);

        ppReferencia.setLayout(new java.awt.BorderLayout());

        pTableReferencias.setLayout(new java.awt.BorderLayout());

        tpReferencias.setModel(otmReferencias);
        jScrollPane2.setViewportView(tpReferencias);

        pTableReferencias.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        ppReferencia.add(pTableReferencias, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Referências", ppReferencia);

        ppConsultas.setLayout(new java.awt.BorderLayout());

        pTableConsultas.setLayout(new java.awt.BorderLayout());

        tpConsulta.setModel(otmConsulta);
        jScrollPane3.setViewportView(tpConsulta);

        pTableConsultas.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        ppConsultas.add(pTableConsultas, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Consultas", ppConsultas);

        ppDadosComerciais.setLayout(new java.awt.BorderLayout());

        pTableDadosComerciais.setLayout(new java.awt.BorderLayout());

        tpDadosComerciais.setModel(otmDadosComerciais);
        jScrollPane4.setViewportView(tpDadosComerciais);

        pTableDadosComerciais.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        ppDadosComerciais.add(pTableDadosComerciais, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Dados Comerciais", ppDadosComerciais);

        ppHistorico.setLayout(new java.awt.BorderLayout());

        tpHistorico.setModel(otmHistorico);
        jScrollPane5.setViewportView(tpHistorico);

        ppHistorico.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Histórico", ppHistorico);

        ppObservacao.setPreferredSize(new java.awt.Dimension(2, 350));
        ppObservacao.setLayout(new java.awt.BorderLayout());

        tpObservacao.setModel(otmObservacao);
        jScrollPane6.setViewportView(tpObservacao);

        ppObservacao.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        paCliente.addTab("Observações", ppObservacao);

        ppClienteAba.add(paCliente, java.awt.BorderLayout.CENTER);

        pCliente.setPreferredSize(new java.awt.Dimension(1345, 30));

        botao1.setText("Cancelar");
        botao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao1ActionPerformed(evt);
            }
        });

        botao2.setText("Salvar");
        botao2.setEnabled(false);

        javax.swing.GroupLayout pClienteLayout = new javax.swing.GroupLayout(pCliente);
        pCliente.setLayout(pClienteLayout);
        pClienteLayout.setHorizontalGroup(
            pClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pClienteLayout.createSequentialGroup()
                .addContainerGap(534, Short.MAX_VALUE)
                .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(594, 594, 594))
        );
        pClienteLayout.setVerticalGroup(
            pClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pClienteLayout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addGroup(pClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ppClienteAba.add(pCliente, java.awt.BorderLayout.SOUTH);

        paExtracaoManual.addTab("Cliente", ppClienteAba);

        ppVenda.setLayout(new java.awt.BorderLayout());

        ppVendas.setPreferredSize(new java.awt.Dimension(1364, 350));
        ppVendas.setLayout(new java.awt.BorderLayout());

        tpVendas.setModel(otmVendas);
        jspVendas.setViewportView(tpVendas);

        ppVendas.add(jspVendas, java.awt.BorderLayout.CENTER);

        ppVenda.add(ppVendas, java.awt.BorderLayout.NORTH);

        ppVendasItens.setPreferredSize(new java.awt.Dimension(1345, 250));
        ppVendasItens.setLayout(new java.awt.BorderLayout());

        tpVendasItens.setModel(otmVendasItens        );
        jspVendasItens.setViewportView(tpVendasItens);

        ppVendasItens.add(jspVendasItens, java.awt.BorderLayout.CENTER);

        paItensParcelas.addTab("Itens", ppVendasItens);

        painelPadrao2.setLayout(new java.awt.BorderLayout());

        tpParcelas.setModel(otmParcela);
        tp.setViewportView(tpParcelas);

        painelPadrao2.add(tp, java.awt.BorderLayout.CENTER);

        paItensParcelas.addTab("Parcelas", painelPadrao2);

        ppCR.setLayout(new java.awt.BorderLayout());

        tpCrVendas.setModel(otmContasReceberVendas);
        tpCrVendas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpCrVendasFocusLost(evt);
            }
        });
        jScrollPane9.setViewportView(tpCrVendas);

        ppCR.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        paItensParcelas.addTab("Contas Receber", ppCR);

        ppVenda.add(paItensParcelas, java.awt.BorderLayout.CENTER);

        paExtracaoManual.addTab("Vendas", ppVenda);

        ppContasReceber.setLayout(new java.awt.BorderLayout());

        pContasReceber.setPreferredSize(new java.awt.Dimension(1364, 550));
        pContasReceber.setLayout(new java.awt.BorderLayout());

        tpContasReceber.setModel(otmContasReceber);
        jScrollPane7.setViewportView(tpContasReceber);

        pContasReceber.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        ppContasReceber.add(pContasReceber, java.awt.BorderLayout.CENTER);

        pTotais.setBorder(javax.swing.BorderFactory.createTitledBorder("Totais"));
        pTotais.setPreferredSize(new java.awt.Dimension(1364, 180));
        pTotais.setLayout(new java.awt.BorderLayout());

        pParcelasCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcelas do Cliente"));
        pParcelasCliente.setPreferredSize(new java.awt.Dimension(650, 150));

        rGeralCliente.setText("Geral:");

        rVencidosCliente.setText("Vencidos:");

        rDiasMediosCliente.setText("Dias Médios:");

        rIndDebitoCliente.setText("Ind.Débito:");

        rVencerCli.setText("A Vencer:");

        rAbertoCli.setText("Aberto:");

        rIndAtrazoCli.setText("Ind. Atrazo:");

        javax.swing.GroupLayout pParcelasClienteLayout = new javax.swing.GroupLayout(pParcelasCliente);
        pParcelasCliente.setLayout(pParcelasClienteLayout);
        pParcelasClienteLayout.setHorizontalGroup(
            pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pParcelasClienteLayout.createSequentialGroup()
                        .addComponent(rIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cvIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pParcelasClienteLayout.createSequentialGroup()
                        .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rDiasMediosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvDiasMedioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        pParcelasClienteLayout.setVerticalGroup(
            pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDiasMediosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDiasMedioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pTotais.add(pParcelasCliente, java.awt.BorderLayout.WEST);

        pParcelasAvalista.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcelas do Avalista"));
        pParcelasAvalista.setPreferredSize(new java.awt.Dimension(650, 150));

        rVencidosAva.setText("Vencidos:");

        rGeralAva.setText("Geral:");

        rDiasMediosAva.setText("Dias Médios:");

        rVencerAva.setText("A Vencer:");

        rAbertoAva.setText("Aberto:");

        javax.swing.GroupLayout pParcelasAvalistaLayout = new javax.swing.GroupLayout(pParcelasAvalista);
        pParcelasAvalista.setLayout(pParcelasAvalistaLayout);
        pParcelasAvalistaLayout.setHorizontalGroup(
            pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                        .addComponent(rDiasMediosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cvDiasMedioAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                        .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        pParcelasAvalistaLayout.setVerticalGroup(
            pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDiasMediosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDiasMedioAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pTotais.add(pParcelasAvalista, java.awt.BorderLayout.EAST);

        ppContasReceber.add(pTotais, java.awt.BorderLayout.SOUTH);

        paExtracaoManual.addTab("Contas Receber", ppContasReceber);

        getContentPane().add(paExtracaoManual, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void csFiltroCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csFiltroCodigoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csFiltroCodigoClienteActionPerformed

    private void bFiltroVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroVisualizarActionPerformed
        resultado = visualizarCliente();
        resultado = buscarContasReceber();
        if (resultado == true) {
            paExtracaoManual.setSelectedComponent(ppClienteAba);
            paExtracaoManual.setEnabledAt(0, false);
            paExtracaoManual.setEnabledAt(1, true);
            paExtracaoManual.setEnabledAt(2, true);
            paExtracaoManual.setEnabledAt(3, true);
        }

    }//GEN-LAST:event_bFiltroVisualizarActionPerformed

    private void bFiltroCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroCancelarActionPerformed
        limparPesquisar();
    }//GEN-LAST:event_bFiltroCancelarActionPerformed

    private void cdFiltroVencInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroVencInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroVencInicialActionPerformed

    private void bFiltroPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPesquisarActionPerformed
        lContasReceber = new LinkedList<>();
        buscarRegistro();

    }//GEN-LAST:event_bFiltroPesquisarActionPerformed

    private void paExtracaoManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paExtracaoManualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_paExtracaoManualMouseClicked

    private void ppFiltroBuscaExtrManualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppFiltroBuscaExtrManualKeyPressed
        ppClienteAba.setEnabled(false);
        ppVenda.setEnabled(false);
        ppContasReceber.setEnabled(false);
    }//GEN-LAST:event_ppFiltroBuscaExtrManualKeyPressed

    private void botao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao1ActionPerformed
        paExtracaoManual.setSelectedComponent(ppBuscaExtrManual);
        paExtracaoManual.setEnabledAt(0, true);
        paExtracaoManual.setEnabledAt(1, false);
        paExtracaoManual.setEnabledAt(2, false);
        paExtracaoManual.setEnabledAt(3, false);
    }//GEN-LAST:event_botao1ActionPerformed

    private void bFiltroPlanilhaGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPlanilhaGridActionPerformed
        try {
            ExportarExcelControl exportarExcelControl = new ExportarExcelControl();
            exportarExcelControl.exportarExtrato(otmParcelasManual.getData(), otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada()).getNomeDoDevedor(), TelaPrincipal.diretorio);
            jPStatusExtracaoManual.setStatus("Arquivo gerado com sucesso.", jPStatusExtracaoManual.NORMAL);
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }//GEN-LAST:event_bFiltroPlanilhaGridActionPerformed

    private void bFiltroVisualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroVisualizar2ActionPerformed
        gerarExtrato();
    }//GEN-LAST:event_bFiltroVisualizar2ActionPerformed

    private void tpCrVendasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpCrVendasFocusLost
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpCrVendasFocusLost

    private void tpExtrManualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpExtrManualFocusLost
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpExtrManualFocusLost

    private void tpExtrManualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpExtrManualKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            // Cut, copy, paste and duplicate
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmParcelasManual.getValueAt(tpExtrManual.getSelectedRow(), tpExtrManual.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpExtrManualKeyPressed

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
        try {
            int linha = tpExtrManual.getSelectedRow();
            if (linha < 0) {
                jPStatusExtracaoManual.setStatus("Selecione uma Filial na Tabela, antes de clicar em alterar", JPStatus.ALERTA);
            } else {
                int result;
                Object[] options = {"Confirmar", "Cancelar"};
                result = JOptionPane.showOptionDialog(null, "Será Alterado o Numero do contrato do cliente. \n Clique Confirmar para Continuar!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    parcela = otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada());
                    contasReceberControl.AtualizarContrato(parcela);
                    jPStatusExtracaoManual.setStatus("Alteração realizada com sucesso.", JPStatus.NORMAL);
                }
            }
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }//GEN-LAST:event_jbAlterarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.paineis.Painel PDadosConjugue;
    private br.com.martinello.componentesbasicos.Botao bFiltroCancelar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPesquisar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPlanilhaGrid;
    private br.com.martinello.componentesbasicos.Botao bFiltroVisualizar;
    private br.com.martinello.componentesbasicos.Botao bFiltroVisualizar2;
    private br.com.martinello.componentesbasicos.Botao botao1;
    private br.com.martinello.componentesbasicos.Botao botao2;
    private br.com.martinello.componentesbasicos.CampoCpfCnpj cccCpfCnpj;
    private br.com.martinello.componentesbasicos.CampoCpfCnpj cccCpfConjuge;
    private br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta ccccFiltroCpf;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroBaixaFinal;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroBaixaInicio;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroNegFinal;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroNegInicio;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroPagFinal;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroPagInicio;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroVencFinal;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroVencInicial;
    private br.com.martinello.componentesbasicos.CampoData cdMoradia;
    private br.com.martinello.componentesbasicos.CampoData cdNasc;
    private br.com.martinello.componentesbasicos.CampoData cdNascConj;
    private br.com.martinello.componentesbasicos.CampoInteiro ciCodCli;
    private br.com.martinello.componentesbasicos.CampoInteiro ciNumeroDependentes;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsEstadoCivil;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroFilial;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsNegativado;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsSexo;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsSitCli;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsSituacao;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsSituacao1;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsTipoMorradia;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsTipoPessoa;
    private br.com.martinello.componentesbasicos.CampoString csContato;
    private br.com.martinello.componentesbasicos.CampoString csEmail;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.CampoString csFiltroContrato;
    private br.com.martinello.componentesbasicos.CampoString csNomeFantazia;
    private br.com.martinello.componentesbasicos.CampoString csRazaoSocial;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroNome;
    private br.com.martinello.componentesbasicos.CampoTelefone ctCelular;
    private br.com.martinello.componentesbasicos.CampoTelefone ctTelefone;
    private br.com.martinello.componentesbasicos.CampoValor cvAbertoAva;
    private br.com.martinello.componentesbasicos.CampoValor cvAbertoCli;
    private br.com.martinello.componentesbasicos.CampoValor cvDespesasMensal;
    private br.com.martinello.componentesbasicos.CampoValor cvDespesasMoradia;
    private br.com.martinello.componentesbasicos.CampoValor cvDiasMedioAva;
    private br.com.martinello.componentesbasicos.CampoValor cvDiasMedioCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvGeralAva;
    private br.com.martinello.componentesbasicos.CampoValor cvGeralCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvIndAtrazoCli;
    private br.com.martinello.componentesbasicos.CampoValor cvIndDebitoCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvRenda;
    private br.com.martinello.componentesbasicos.CampoValor cvRendaCompl;
    private br.com.martinello.componentesbasicos.CampoValor cvValorParimonio;
    private br.com.martinello.componentesbasicos.CampoValor cvVencerAva;
    private br.com.martinello.componentesbasicos.CampoValor cvVencerCli;
    private br.com.martinello.componentesbasicos.CampoValor cvVencidosAva;
    private br.com.martinello.componentesbasicos.CampoValor cvVencidosCliente;
    private static br.com.martinello.componentesbasicos.paineis.JPStatus jPStatusExtracaoManual;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private br.com.martinello.componentesbasicos.Botao jbAlterar;
    private javax.swing.JScrollPane jspBuscaExtrManual;
    private javax.swing.JScrollPane jspVendas;
    private javax.swing.JScrollPane jspVendasItens;
    private br.com.martinello.componentesbasicos.paineis.Painel pCliente;
    private br.com.martinello.componentesbasicos.paineis.Painel pContasReceber;
    private br.com.martinello.componentesbasicos.paineis.Painel pDadosCompl;
    private br.com.martinello.componentesbasicos.paineis.Painel pDadosPessoais;
    private br.com.martinello.componentesbasicos.paineis.Painel pDadosRenda;
    private br.com.martinello.componentesbasicos.paineis.Painel pDadosRendaMoradia;
    private br.com.martinello.componentesbasicos.paineis.Painel pDadosResidencia;
    private br.com.martinello.componentesbasicos.paineis.Painel pParcelasAvalista;
    private br.com.martinello.componentesbasicos.paineis.Painel pParcelasCliente;
    private br.com.martinello.componentesbasicos.paineis.Painel pTableConsultas;
    private br.com.martinello.componentesbasicos.paineis.Painel pTableDadosComerciais;
    private br.com.martinello.componentesbasicos.paineis.Painel pTableEndereco;
    private br.com.martinello.componentesbasicos.paineis.Painel pTableReferencias;
    private br.com.martinello.componentesbasicos.paineis.Painel pTotais;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paCliente;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paExtracaoManual;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paItensParcelas;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paParcelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao2;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppCR;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppCliente;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppClienteAba;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppConsultas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppContasReceber;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppDadosComerciais;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppDadosCompl;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppEndereco;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppHistorico;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppMovimentos;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppObservacao;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppParcelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppReferencia;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppVenda;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppVendas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppVendasItens;
    private br.com.martinello.componentesbasicos.Rotulo rAbertoAva;
    private br.com.martinello.componentesbasicos.Rotulo rAbertoCli;
    private br.com.martinello.componentesbasicos.Rotulo rCpf;
    private br.com.martinello.componentesbasicos.Rotulo rDataMoradia;
    private br.com.martinello.componentesbasicos.Rotulo rDataNascConj;
    private br.com.martinello.componentesbasicos.Rotulo rDataNascimento;
    private br.com.martinello.componentesbasicos.Rotulo rDescricao;
    private br.com.martinello.componentesbasicos.Rotulo rDespesasMensal;
    private br.com.martinello.componentesbasicos.Rotulo rDespesasMoradia;
    private br.com.martinello.componentesbasicos.Rotulo rDiasMediosAva;
    private br.com.martinello.componentesbasicos.Rotulo rDiasMediosCliente;
    private br.com.martinello.componentesbasicos.Rotulo rDocumento;
    private br.com.martinello.componentesbasicos.Rotulo rEstadoCivil;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroContrato;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCpf;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataBaixa;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataNegativacao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataPagamento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataVencimento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroFilial;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroNome;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroSituacao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroSituacao1;
    private br.com.martinello.componentesbasicos.Rotulo rGeralAva;
    private br.com.martinello.componentesbasicos.Rotulo rGeralCliente;
    private br.com.martinello.componentesbasicos.Rotulo rIndAtrazoCli;
    private br.com.martinello.componentesbasicos.Rotulo rIndDebitoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rNaturalidaConj;
    private br.com.martinello.componentesbasicos.Rotulo rNaturalidade;
    private br.com.martinello.componentesbasicos.Rotulo rNomeConj;
    private br.com.martinello.componentesbasicos.Rotulo rNomeMae;
    private br.com.martinello.componentesbasicos.Rotulo rNomeMaeConj;
    private br.com.martinello.componentesbasicos.Rotulo rNomePai;
    private br.com.martinello.componentesbasicos.Rotulo rNomePaiConj;
    private br.com.martinello.componentesbasicos.Rotulo rNomeQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rNumeroDependentes;
    private br.com.martinello.componentesbasicos.Rotulo rQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rRenda;
    private br.com.martinello.componentesbasicos.Rotulo rRendaCompl;
    private br.com.martinello.componentesbasicos.Rotulo rSexo;
    private br.com.martinello.componentesbasicos.Rotulo rTipoMoradia;
    private br.com.martinello.componentesbasicos.Rotulo rValorPatrimonio;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotal;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalR;
    private br.com.martinello.componentesbasicos.Rotulo rVencerAva;
    private br.com.martinello.componentesbasicos.Rotulo rVencerCli;
    private br.com.martinello.componentesbasicos.Rotulo rVencidosAva;
    private br.com.martinello.componentesbasicos.Rotulo rVencidosCliente;
    private br.com.martinello.componentesbasicos.Rotulo rotulo1;
    private br.com.martinello.componentesbasicos.Rotulo rotulo10;
    private br.com.martinello.componentesbasicos.Rotulo rotulo11;
    private br.com.martinello.componentesbasicos.Rotulo rotulo14;
    private br.com.martinello.componentesbasicos.Rotulo rotulo2;
    private br.com.martinello.componentesbasicos.Rotulo rotulo3;
    private br.com.martinello.componentesbasicos.Rotulo rotulo4;
    private br.com.martinello.componentesbasicos.Rotulo rotulo6;
    private br.com.martinello.componentesbasicos.Rotulo rotulo7;
    private br.com.martinello.componentesbasicos.Rotulo rotulo8;
    private br.com.martinello.componentesbasicos.Rotulo rotulo9;
    private br.com.martinello.componentesbasicos.Rotulo rotuloDescricao;
    private br.com.martinello.componentesbasicos.Rotulo rotuloDocumento;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNaturalidade;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNaturalidadeConj;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNomeConj;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNomeMae;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNomeMaeConj;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNomePai;
    private br.com.martinello.componentesbasicos.Rotulo rotuloNomePaiConj;
    private javax.swing.JScrollPane tp;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsulta;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpContasReceber;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCrMov;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCrVendas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpDadosComerciais;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpEndereco;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpExtrManual;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpHistorico;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpObservacao;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpParcelas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpReferencias;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpVendas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpVendasItens;
    // End of variables declaration//GEN-END:variables
 private void carregarFiliais() {
        try {
            lfiliaisModel = filiaisControler.listarFilialController();
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }

        for (FilialModel filiais : lfiliaisModel) {
            clsFiltroFilial.addItem(filiais);
            //jcb_FiliaisExtracaoManual.addItem(filiais);
        }
    }

    private void limparSalvar() {
        paExtracaoManual.setSelectedIndex(0);
        paExtracaoManual.setEnabledAt(1, false);

    }

    private void limparPesquisar() {
        csFiltroCodigoCliente.setText("");
        cscFiltroNome.setaValorFiltro("");
        csFiltroContrato.setText("");
        ccccFiltroCpf.setaValorFiltro("");
        cdFiltroVencInicial.setDate(null);
        cdFiltroPagFinal.setDate(null);
        cdFiltroPagInicio.setDate(null);
        cdFiltroVencFinal.setDate(null);
    }

    private boolean buscarRegistro() {
        // TODO add your handling code here:
        final TelaProcessamento telaProcessamento = new TelaProcessamento("Realizando consulta...");

        filtroConsulta = new ConsultaModel();
        if (clsFiltroFilial.getSelectedItem().toString().equals("TODAS")) {
            filtroConsulta.setOrigem_BD("NOVO_SGL");
            // retornoConsulta = false;
            // jPStatusExtracaoManual.setStatus("É necessário selecionar uma filial!", JPStatus.ERRO);
            //  return retornoConsulta;
        } else {
            for (FilialModel filial : lfiliaisModel) {
                String nome = clsFiltroFilial.getSelectedItem().toString();
                if (filial.getNomeLoja().equalsIgnoreCase(nome)) {
                    filtroConsulta.setPontoFilial(filial.getFilialSgl());
                    filtroConsulta.setOrigem_BD("NOVO_SGL");
                }
            }
        }
        if (cscFiltroNome.getFiltroOld() != null) {
            filtroConsulta.setNome(cscFiltroNome.getFiltroOld().toUpperCase());
        }
        if (!csFiltroContrato.getText().equals("")) {
            filtroConsulta.setNumeroDoContrato("%" + csFiltroContrato.getText() + "%");
        }
        if (!csFiltroCodigoCliente.getText().equals("")) {
            filtroConsulta.setCodCliente(csFiltroCodigoCliente.getText());
        }
        String cpfCnpj = ccccFiltroCpf.getFiltroOld() != null ? ccccFiltroCpf.getFiltroOld().replaceAll("[^0-9]", "").trim() : "";
        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            filtroConsulta.setCpfCnpj(cpfCnpj);
        }
        if (cdFiltroVencInicial.getDate() != null | cdFiltroVencFinal.getDate() != null) {
            if (cdFiltroVencFinal.getDate() != null) {
                filtroConsulta.setDataInicial(Utilitarios.converteDataString(cdFiltroVencInicial.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data vencimento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroVencInicial.getDate() != null) {
                filtroConsulta.setDataFinal(Utilitarios.converteDataString(cdFiltroVencFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data vencimento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }

        if (cdFiltroPagInicio.getDate() != null | cdFiltroPagFinal.getDate() != null) {
            if (cdFiltroPagFinal.getDate() != null) {
                filtroConsulta.setDataIniciaPag(Utilitarios.converteDataString(cdFiltroPagInicio.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data pagamento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroPagInicio.getDate() != null) {
                filtroConsulta.setDataFinalPag(Utilitarios.converteDataString(cdFiltroPagFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data pagamento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (cdFiltroNegInicio.getDate() != null | cdFiltroNegFinal.getDate() != null) {
            if (cdFiltroNegFinal.getDate() != null) {
                filtroConsulta.setDataInicialNeg(Utilitarios.converteDataString(cdFiltroNegInicio.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data Negativação precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroNegInicio.getDate() != null) {
                filtroConsulta.setDataFinalNeg(Utilitarios.converteDataString(cdFiltroNegFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data Negativação precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (cdFiltroBaixaInicio.getDate() != null | cdFiltroBaixaFinal.getDate() != null) {
            if (cdFiltroBaixaFinal.getDate() != null) {
                filtroConsulta.setDataInicialBai(Utilitarios.converteDataString(cdFiltroBaixaInicio.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data da Baixa precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroBaixaInicio.getDate() != null) {
                filtroConsulta.setDataFinalBai(Utilitarios.converteDataString(cdFiltroBaixaFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusExtracaoManual.setStatus("O campo data da Baixa precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (clsSituacao.getSelectedIndex() == 0) {
            filtroConsulta.setSituacao("AB");
        }
        if (clsSituacao.getSelectedIndex() == 1) {
            filtroConsulta.setSituacao("LQ");
        }
        if (clsSituacao.getSelectedIndex() == 2) {
            filtroConsulta.setSituacao("LS");
        }
        if (clsSituacao.getSelectedIndex() == 3) {
            filtroConsulta.setSituacao("CA");
        }

        if (clsSituacao1.getSelectedIndex() == 1) {
            filtroConsulta.setListMovimento("N");
        }
        if (clsSituacao1.getSelectedIndex() == 0) {
            filtroConsulta.setListMovimento("S");
        }
        new Thread() {
            @Override
            public void run() {
                lParcelaManual = new ArrayList<>();
                contasReceberControl = new ContasReceberControl();
                try {
                    lParcelaManual = contasReceberControl.buscarE301TCR(filtroConsulta);
                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jPStatusExtracaoManual.setStatus(retornoExtracao, JPStatus.ERRO, ex);
                }
                if (lParcelaManual == null) {
                    jPStatusExtracaoManual.setStatus("Nenhum resultado encontrado para a pesquisa refaça a busca", JPStatus.ERRO);
                } else {

                    otmParcelasManual.setData(lParcelaManual);
                    rQuantidade.setText("" + lParcelaManual.size());

                    otmParcelasManual.fireTableDataChanged();
                    jPStatusExtracaoManual.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
                    if (tpExtrManual.getRowCount() > 0) {
                        tpExtrManual.packAll();
                        tpExtrManual.addRowSelectionInterval(tpExtrManual.convertRowIndexToView(0), tpExtrManual.convertRowIndexToView(0));
                        tpExtrManual.grabFocus();
                    }

                }
                SwingUtilities.invokeLater(() -> {
                    telaProcessamento.dispose();
                });
            }
        }.start();
        telaProcessamento.setVisible(true);
        telaProcessamento.requestFocusInWindow();
        calculaValorTotal();

        return retornoConsulta;

    }

    public void inserirObservacao() {

    }

    private void calculaValorTotal() {

        try {
            int valorTotal = 0;
            for (int i = 0; i < tpExtrManual.getRowCount(); i++) {
                Double valorAux;
                valorAux = (Double) tpExtrManual.getValueAt(i, 9);
                valorTotal += valorAux.doubleValue();
            }

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            rValorTotal.setText(nf.format(valorTotal));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular Total Produtos: " + e.getMessage());
        }
    }

    private boolean visualizarCliente() {
        resultado = false;
        try {
            consultaClienteControl = new ConsultaClienteControl();
            consultaCliente = new ConsultaClienteModel();
            sCodFil = otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada()).getPontoFilial();
            codCli = Integer.parseInt(otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada()).getCodCliente());
            consultaCliente = consultaClienteControl.ConsultarCliente(sCodFil, codCli);
            csRazaoSocial.setString(consultaCliente.getCliente().getNomeRazaoSocial());
            cccCpfCnpj.setText(consultaCliente.getCliente().getCpf());
            csEmail.setString(consultaCliente.getCliente().getEmail());
            ctTelefone.setText(consultaCliente.getCliente().getNumeroTel());
            ctCelular.setText(consultaCliente.getCliente().getNumeroCelular());
            clsSitCli.getModel().setSelectedItem(consultaCliente.getCliente().getSituacao());
            if (consultaCliente.getCliente().getNegativacao().equalsIgnoreCase("B")) {
                clsNegativado.setInt(1);
            } else {
                clsNegativado.setInt(0);
            }
            if (consultaCliente.getCliente().getSexo().equalsIgnoreCase("M")) {
                clsSexo.setInt(0);
            } else {
                clsSexo.setInt(1);
            }
            if (consultaCliente.getCliente().getTipoPessoa().equalsIgnoreCase("F")) {
                clsTipoPessoa.setInt(0);
            } else {
                clsTipoPessoa.setInt(1);
            }
            cdNasc.setDate(consultaCliente.getCliente().getDataNascimento());
            rotuloNomePai.setText(consultaCliente.getCliente().getNomeDoPai());
            rotuloNomeMae.setText(consultaCliente.getCliente().getNomeDaMae());
            rotuloNaturalidade.setText(consultaCliente.getCliente().getNaturalidade());

            cccCpfConjuge.setText(consultaCliente.getClienteConjuge().getCpf());
            cdNascConj.setDate(consultaCliente.getClienteConjuge().getDataNascimento());
            rotuloNomePaiConj.setText(consultaCliente.getClienteConjuge().getNomeDoPai());
            rotuloNomeMaeConj.setText(consultaCliente.getClienteConjuge().getNomeDaMae());
            rotuloNaturalidadeConj.setText(consultaCliente.getClienteConjuge().getNaturalidade());

            cdMoradia.setDate(consultaCliente.getDataResidencia());

            cvRenda.setDouble(consultaCliente.getRenda());
            cvRendaCompl.setDouble(consultaCliente.getRendaComplementar());
            rotuloDescricao.setText(consultaCliente.getDescRendaComplementar());
            cvDespesasMoradia.setDouble(consultaCliente.getDespesasMoradia());
            cvDespesasMensal.setDouble(consultaCliente.getDespesasMensal());
            cvValorParimonio.setDouble(consultaCliente.getValorPatrimnonio());
            otmClienteEndereco.setData(consultaCliente.getlEnderecos());

            otmClienteEndereco.fireTableDataChanged();
            if (tpEndereco.getRowCount() > 0) {
                tpEndereco.packAll();
                tpEndereco.addRowSelectionInterval(tpEndereco.convertRowIndexToView(0), tpEndereco.convertRowIndexToView(0));
                tpEndereco.grabFocus();
            }
            otmReferencias.setData(consultaCliente.getlReferencias());
            otmReferencias.fireTableDataChanged();
            if (tpReferencias.getRowCount() > 0) {
                tpReferencias.packAll();
                tpReferencias.addRowSelectionInterval(tpReferencias.convertRowIndexToView(0), tpReferencias.convertRowIndexToView(0));
                tpReferencias.grabFocus();
            }
            otmObservacao.setData(consultaCliente.getlObservacoes());
            otmObservacao.fireTableDataChanged();
            if (tpObservacao.getRowCount() > 0) {
                tpObservacao.packAll();
                tpObservacao.addRowSelectionInterval(tpObservacao.convertRowIndexToView(0), tpObservacao.convertRowIndexToView(0));
                tpObservacao.grabFocus();
            }
            otmConsulta.setData(consultaCliente.getlConsulta());
            otmConsulta.fireTableDataChanged();
            if (tpConsulta.getRowCount() > 0) {
                tpConsulta.packAll();
                tpConsulta.addRowSelectionInterval(tpConsulta.convertRowIndexToView(0), tpConsulta.convertRowIndexToView(0));
                tpConsulta.grabFocus();
            }
            otmDadosComerciais.setData(consultaCliente.getlDadosComericiais());
            otmDadosComerciais.fireTableDataChanged();
            if (tpDadosComerciais.getRowCount() > 0) {
                tpDadosComerciais.packAll();
                tpDadosComerciais.addRowSelectionInterval(tpDadosComerciais.convertRowIndexToView(0), tpDadosComerciais.convertRowIndexToView(0));
                tpDadosComerciais.grabFocus();
            }
            resultado = true;
            jPStatusExtracaoManual.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus("Erro ao Visualizar Cliente!", JPStatus.ERRO, ex);
        }
        return resultado;
    }

    private boolean buscarContasReceber() {
        resultado = false;
        valorOriginal = 0;
        valorDevolucao = 0;
        try {
            contasReceberControl = new ContasReceberControl();
            lVendas = new LinkedList<>();
            lContasReceber = new LinkedList<>();
            lVendas = contasReceberControl.buscarVendas(sCodFil, codCli);
            for (VendasModel venda : lVendas) {
                if (venda.getDevolvida().equals("N") && venda.getTipoVenda().equals("P") && venda.getSituacao().equals("L")) {
                    valorOriginal += Double.valueOf(venda.getVlrAprazo());
                }
                if (venda.getDevolvida().equals("N") && venda.getTipoVenda().equals("V") && venda.getSituacao().equals("L")) {
                    valorOriginal += Double.valueOf(venda.getVlrAvista());
                }
                if (venda.getDevolvida().equals("N") && venda.getTipoVenda().equals("R") && venda.getSituacao().equals("L")) {
                    valorOriginal += Double.valueOf(venda.getVlrAvista());
                }
                if (venda.getDevolvida().equals("S") || venda.getDevolvida().equals("P")) {
                    valorDevolucao = contasReceberControl.buscarValorDevolucao(venda.getIdRegistro(), venda.getCliente());
                    if (venda.getTipoVenda().equals("P") && venda.getSituacao().equals("L")) {
                        valorOriginal += Double.valueOf(venda.getVlrAprazo()) - valorDevolucao;
                    } else if (venda.getDevolvida().equals("S") && venda.getTipoVenda().equals("V") && venda.getSituacao().equals("L")) {
                        valorOriginal += Double.valueOf(venda.getVlrAvista()) - valorDevolucao;
                    } else if (venda.getDevolvida().equals("S") && venda.getTipoVenda().equals("R") && venda.getSituacao().equals("L")) {
                        valorOriginal += Double.valueOf(venda.getVlrAvista()) - valorDevolucao;
                    }
                }
            }

            otmVendas.setData(lVendas);
            otmVendas.fireTableDataChanged();
            if (tpVendas.getRowCount() > 0) {
                tpVendas.packAll();
                tpVendas.addRowSelectionInterval(tpVendas.convertRowIndexToView(0), tpVendas.convertRowIndexToView(0));
                tpVendas.grabFocus();
            }

            lContasReceber = contasReceberControl.buscarContasReceber(sCodFil, codCli);
            otmContasReceber.setData(lContasReceber);
            otmContasReceber.fireTableDataChanged();
            if (tpContasReceber.getRowCount() > 0) {
                tpContasReceber.packAll();
                tpContasReceber.addRowSelectionInterval(tpContasReceber.convertRowIndexToView(0), tpContasReceber.convertRowIndexToView(0));
                tpContasReceber.grabFocus();
            }
            totalizadorContasReceber(lContasReceber);
            resultado = true;
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus("Erro ao Buscar Contas Receber!", JPStatus.ERRO, ex);
        }
        return resultado;
    }

    private void setVendaItens() {
        lVendasItens = new LinkedList<>();
        lVendasItens = otmVendas.getValue(tpVendas.getLinhaSelecionada()).getlVendasItens();
        otmVendasItens.setData(lVendasItens);
        otmVendasItens.fireTableDataChanged();
        tpVendasItens.setForeground(Color.BLACK);
        if (tpVendasItens.getRowCount() > 0) {
            tpVendasItens.packAll();
            tpVendasItens.addRowSelectionInterval(tpVendasItens.convertRowIndexToView(0), tpVendasItens.convertRowIndexToView(0));
        }

    }

    private void totalizadorContasReceber(List<ContasReceberModel> lContasReceber) {
        tQuitacao = 0;
        tParcial = 0;
        tRenegociacao = 0;
        tDevolucao = 0;
        tTransf = 0;
        tPerdida = 0;
        int dias = 0;
        valorTraz = 0;
        valorCalc = 0;
        valorAberto = 0;
        valorParcela = 0;
        valorVencer = 0;
        Date dataFinal = new Date();
        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(dataFinal);
        calendarData.add(Calendar.DATE, 0);
        Date sysdate = calendarData.getTime();
        for (ContasReceberModel contaReceber : lContasReceber) {
            String situacao = contaReceber.getSitucao();

            if (situacao.equals("A") | (situacao.equals("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N")))) {
                valorParcela += contaReceber.getValor();
            }
            if (contaReceber.getDataVencimento().before(sysdate)) {
                if (contaReceber.getDataPagamento().equals("31/12/1900")
                        || (situacao.equalsIgnoreCase("A") && (contaReceber.getTipoBaixa().equals("T") | contaReceber.getTipoBaixa().equals("P"))) | contaReceber.getTipoBaixa().equals(" ")) {
                    dias = contaReceber.getDias();
                    valorTraz += contaReceber.getValor();
                } else if (situacao.equalsIgnoreCase("L") && contaReceber.getTipoBaixa().equals("D")) {
                    dias = 0;
                    //  valorCalc += contaReceber.getValor() * dias;
                } else if (situacao.equalsIgnoreCase("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N"))) {
                    dias = contaReceber.getDias();
                    //  valorCalc += contaReceber.getValor() * dias;
                }
                valorCalc += contaReceber.getValor() * dias;
            } else {
                if (contaReceber.getDataPagamento().after(Utilitarios.getDataZero())) {
                    if (situacao.equals("L") && contaReceber.getTipoBaixa().equals("D")) {
                        dias = 0;
                    } else if (situacao.equals("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N"))) {
                        valorCalc += contaReceber.getValor() * dias;
                    }
                }
                if (situacao.equals("A")) {
                    valorVencer += contaReceber.getValor();
                }
            }
            if (situacao.equals("A")) {
                valorAberto += contaReceber.getValor();
            }
        }
        cvDiasMedioCliente.setDouble(valorCalc / valorParcela);
        cvIndDebitoCliente.setDouble((valorAberto / valorOriginal) * 100);
        cvIndAtrazoCli.setDouble((valorTraz / valorOriginal) * 100);
        cvAbertoCli.setDouble(valorAberto);
        cvGeralCliente.setDouble(valorOriginal);
        cvVencerCli.setDouble(valorVencer);
        cvVencidosCliente.setDouble(valorTraz);
    }

    private void setParcelas() {
        lParcelas = new LinkedList<>();
        lParcelas = otmVendas.getValue(tpVendas.getLinhaSelecionada()).getlParcela();
        otmParcela.setData(lParcelas);
        otmParcela.fireTableDataChanged();
        tpParcelas.setForeground(Color.BLACK);
        if (tpParcelas.getRowCount() > 0) {
            tpParcelas.packAll();
            tpParcelas.addRowSelectionInterval(tpParcelas.convertRowIndexToView(0), tpParcelas.convertRowIndexToView(0));
        }
    }

    private void gerarExtrato() {
        try {
            long cgcCpf;
            cgcCpf = Long.parseLong(otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada()).getCpfCnpj());
            contasReceberControl = new ContasReceberControl();
            String loginSO = "";
            for (UsuarioLogin userLogin : TelaPrincipal.usuario.getLogins()) {
                loginSO = userLogin.getNomeUsuarioSO();
            }

            contasReceberControl.ExtratoCliente(cgcCpf, TelaPrincipal.usuario.getLogin(), loginSO);
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus("Erro ao Gerar Extrato do Cliente!", JPStatus.ERRO, ex);
        }
    }

    private void setCrMov() {
        List<Parcela_Movimentos> lMovimentos = new LinkedList<>();

        if (clsSituacao1.getSelectedIndex() == 0) {
            lMovimentos = otmParcelasManual.getValue(tpExtrManual.getLinhaSelecionada()).getlMovimentos();
            otmCrMov.setData(lMovimentos);
            otmCrMov.fireTableDataChanged();
            tpCrMov.setForeground(Color.BLACK);
            if (tpCrMov.getRowCount() > 0) {
                tpCrMov.packAll();
                tpCrMov.addRowSelectionInterval(tpCrMov.convertRowIndexToView(0), tpCrMov.convertRowIndexToView(0));
            }
        }
    }

    private void setContasReceber() {
        lContasReceber = new LinkedList<>();
        lContasReceber = otmVendas.getValue(tpVendas.getLinhaSelecionada()).getlContasReceber();
        otmContasReceberVendas.setData(lContasReceber);
        otmContasReceberVendas.fireTableDataChanged();
        tpCrVendas.setForeground(Color.BLACK);
        if (tpCrVendas.getRowCount() > 0) {
            tpCrVendas.packAll();
            tpCrVendas.addRowSelectionInterval(tpCrVendas.convertRowIndexToView(0), tpCrVendas.convertRowIndexToView(0));
        }
    }

}
