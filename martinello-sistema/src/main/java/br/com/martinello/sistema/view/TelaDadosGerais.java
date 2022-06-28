/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view;

import br.com.martinello.bd.matriz.control.BvsEnvioController;
import br.com.martinello.bd.matriz.control.SpcEnvioController;
import br.com.martinello.bd.matriz.control.EnderecoController;
import br.com.martinello.bd.matriz.control.ExportarExcelControl;
import br.com.martinello.bd.matriz.control.ExtracaoTableController;
import br.com.martinello.bd.matriz.control.FilialController;
import br.com.martinello.bd.matriz.control.LogExtracaoController;
import br.com.martinello.bd.matriz.control.NotificacaoControl;
import br.com.martinello.bd.matriz.control.ParcelaController;
import br.com.martinello.bd.matriz.control.ParcelasEnviarInclusaoBvsController;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.control.ParcelasEnviarBaixaBvsController;
import br.com.martinello.bd.matriz.control.ParcelasEnviarController;
import br.com.martinello.bd.matriz.control.ParcelasEnviarInclusaoSpcController;
import br.com.martinello.bd.matriz.control.ParcelasExtracaoController;
import br.com.martinello.bd.matriz.control.PessoaController;
import br.com.martinello.bd.matriz.control.ProcessamentoController;
import br.com.martinello.bd.matriz.control.RetornoEnvioController;
import br.com.martinello.bd.matriz.control.RetornoProvedorControl;
import br.com.martinello.bd.matriz.control.SendEmail;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.EnderecoModel;
import br.com.martinello.bd.matriz.model.domain.LogParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.bd.matriz.model.domain.PessoaModel;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.bd.matriz.model.domain.RetornoProvedorModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.componentesbasicos.TabelaPadrao;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.util.FixedLengthDocument;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author luiz.almeida
 */
public class TelaDadosGerais extends TelaPadrao {

    public int idExtrator, linha;
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();
    public ConsultaModel filtro = new ConsultaModel();
    public ParcelasEnviarModel parcelasEnviarBvsModel;
    public ConsultaModel parametros = new ConsultaModel();
    public FilialModel filiaisModel = new FilialModel();
    public FilialModel filtroFilialModel = new FilialModel();
    public PessoaModel pessoaModel = new PessoaModel();
    public List<PessoaModel> lpessoaModel = new ArrayList<>();
    public List<PessoaModel> lpessoaAvalModel = new ArrayList<>();
    public PessoaController pessoaController = new PessoaController();
    public ParcelaModel parcelaModel = new ParcelaModel();
    public List<ParcelaModel> lparcelaModel = new ArrayList();
    public ParcelaController parcelaController = new ParcelaController();
    public EnderecoModel enderecoModel = new EnderecoModel();
    public List<EnderecoModel> lenderecoModel = new ArrayList();
    public List<EnderecoModel> lenderecoAvalModel = new ArrayList();
    public List<LogParcelaModel> lLogParcela = new ArrayList();
    public List<LogParcelaModel> lLogExtracaoModel = new ArrayList();
    public EnderecoController enderecoController = new EnderecoController();
    public FilialController filiaisControler = new FilialController();
    public ProcessamentoController processamentoController;
    public RetornoProvedorControl retornoProvedorControl;
    public static List<FilialModel> lfiliaisModel = new ArrayList();
    public ExtracaoTableController extracaoTableController = new ExtracaoTableController();
    private ExtracaoTableModel filtroConsultaExtracao = new ExtracaoTableModel();
    private ParcelasExtracaoController parcelasExtracao;
    public ParcelasEnviarController bvsController = new ParcelasEnviarController();
    private static ParcelasEnviarBaixaBvsController baixaBvsController;
    private ParcelasEnviarInclusaoSpcController inclusaoSpcController;
    private ParcelasEnviarInclusaoBvsController inclusaoBvsController;
    private List<ExtracaoTableModel> lextracaoTableModel = new ArrayList();
    private static List<ParcelasEnviarModel> lbaixaBvsModel, lconsultarInclusaoBvs;
    public List<ParcelasEnviarModel> linclusaoSpcModel;
    private static List<ProcessamentoModel> lProcessamentoModel, lProcessoDBModel;
    private static List<RetornoProvedorModel> lRetornoProvedor;
    private static int enviadosBaixaBvs, enviadosBaixaBvsErro, enviadosBaixaBvsSucesso, valor, valorEnviadosErro;
    private static int enviadosConsultaBvs, enviadosConsultaBvsErro, enviadosConsultaBvsSucesso;
    private static int enviadosBaixaSpc, enviadosBaixaErroSpc, enviadosBaixaSucessoSpc;
    private ProcessamentoModel processamentoModel;
    private static ProcessamentoModel processamentoBaixaSpcModel, processamentoInclusaoSpcModel;
    private static ProcessamentoModel processamentoBaixaBvsModel, processamentoInclusaoBvsModel, processamentoConsultarBvsModel;
    private static ProcessamentoModel processamentoTableBvsModel;
    private static ProcessamentoModel processamentoTableSpcModel;
    public List<ParcelasEnviarModel> linclusaoBvsModel = new ArrayList();
    public ParcelasEnviarController spcController = new ParcelasEnviarController();
    public List<ParcelasEnviarModel> lbaixaSpcModel;
    private static BvsEnvioController bvsEnvioController;
    private static SpcEnvioController spcEnvioController;
    private static int enviadosInclusaoBvs, enviadosInclusaoBvsErro, enviadosInclusaoBvsSucesso;
    private static int enviadosInclusaoSpc, enviadosInclusaoErroSpc, enviadosInclusaoSucessoSpc;
    private static String tempInicioBaixaBvs, tempInicioBaixaSpc, tempFimBaixaBvs, tempFimBaixaSpc, tempInicioConsultaBvs, tempFimConsultaBvs;
    private static String tempInicioInclusaoBvs, tempInicioInclusaoSpc, tempFimInclusaoBvs, tempFimInclusaoSpc, tabela;
    private boolean processoDBFacmat, processoDBExtracaoFacmat, processoDBSpc, processoDBExtracaoSpc;
    private boolean retornoPesquisar, retornoHistorico;
    private int contFiltro;
    private boolean inclusaoSpc, inclusaoFacmat, baixaSpc, baixaFacmat, retornoContasReceber;
    private volatile int retornoIncSpc, retornoBxSpc;
    public volatile int retornoIncBvs, retornoBxBvs;
    public volatile int retornoConsBvs;
    public static int quantidade;
    private ExtracaoTableModel regFinalizar;
    private ExtracaoTableModel extracaoModel = new ExtracaoTableModel();
    private List<FilialModel> ListFiliasComErro = new ArrayList();
    private List<ParcelasEnviarModel> ListRegistrosComErro = new ArrayList();
    private NotificacaoControl notificacaoControl = new NotificacaoControl();
    private SendEmail sendEmail = new SendEmail();
    private TelaFinalizaRegistro telaFinalizaRegistro;
    private TelaPrincipal telaPrincipal;
    private TelaExtornoContasReceber telaExtorno;
    private ProcessamentoModel processamentoBvs = new ProcessamentoModel();
    public String codCli, codFil, idExtracao;
    public int idCliente;
    public double valorTotal;

    //private String provedor, tipoRegistro;
    private String valorCopiado;

    private final ObjectTableModel<ExtracaoTableModel> otmExtracao = new ObjectTableModel<>(ExtracaoTableModel.class, "indice,idExtracao,idFilial,"
            + "idCliente,nome,tipoPessoa,cpfCnpj,codigoCliente,numeroDoc,tipoAcao,status,pago,dataLancamento,dataVencimento,valor,numeroParcela,idParcela,dataPagamento,statusProvedor,situacao,"
            + "dataSpcInclusao,dataSpcAvalistaInclusao,dataFacmatInclusao,dataSpcBaixa,dataSpcAvalistaBaixa,dataFacmatBaixa,statusSpc,statusSpcAval,statusFacmat,"
            + "dataNegativada,dataBaixa,dataAlteracao,dataExtracao,dataUltimaAtualizacao,incluirAval,codAvalista,nomeAvalista,origemRegistro,dataRetorno,cidade,cep,numtit,codTpt,codfil,origemBd");

    private final ObjectTableModel<ParcelaModel> otmParcela = new ObjectTableModel<>(ParcelaModel.class, "idParcela,codCliente,numeroDoContrato,numeroParcela,dataExtracao,dataLancamento,dataVencimento,"
            + "valorParcela,dataPagamento,tipoPagamento,capitalPago,taxaDeJuros,valorJuros,valorCalc,valorPago,jurosPago,situacaoParcela,dataNegativacao,dataBaixaNegativacao,idRegistroBvs,motivoBaixaBvs,motivoBaixaSpc,"
            + "naturezaBvs,naturezaSpc,dataEnvioFacmat,dataEnvioSpc,dataEnvioAvalistaSpc,dataBaixaFacmat,dataBaixaSpc,dataBaixaAvalistaSpc,dataAlteracao,dataAtualizacao");

    private final ObjectTableModel<PessoaModel> otmInformacoePessoal = new ObjectTableModel<>(PessoaModel.class, "indice,idExtrator,pontoFilial,codigo,nomeRazaoSocial,situacao,nomeDoPai,nomeDaMae,"
            + "numeroRG,orgaoEmissorRG,dataEmissaoRG,email,estadoCivil,dataNascimento,tipoPessoa,cpf,numeroTel,idPessoa,idAvalista");

    private final ObjectTableModel<EnderecoModel> otmEndereco = new ObjectTableModel<>(EnderecoModel.class, "endLogradouro,endIdLogradouro,endNumero,endComplemento,endBairro,cidade,ufEstado,codigoIbge,cep");

    private final ObjectTableModel<PessoaModel> otmAvalista = new ObjectTableModel<>(PessoaModel.class, "indice,idExtrator,pontoFilial,codigo,nomeRazaoSocial,situacao,nomeDoPai,nomeDaMae,"
            + "numeroRG,orgaoEmissorRG,dataEmissaoRG,email,estadoCivil,dataNascimento,tipoPessoa,cpf,numeroTel,idPessoa,idAvalista");

    private final ObjectTableModel<EnderecoModel> otmEnderecoAvalista = new ObjectTableModel<>(EnderecoModel.class, "endLogradouro,endIdLogradouro,endNumero,endComplemento,endBairro,cidade,ufEstado,codigoIbge,cep");

    private final ObjectTableModel<ProcessamentoModel> otmProcessoBaixa = new ObjectTableModel<>(ProcessamentoModel.class, "provedor,tipo,itensEnviados,itensSucesso,itensErro,itensTotal,dataInicio,dataFim");

    private final ObjectTableModel<ProcessamentoModel> otmProcessoDB = new ObjectTableModel<>(
            ProcessamentoModel.class, "indice,provedor,tipo,itensTotal,dataInicioEnvio,dataFimEnvio,user");

    private final ObjectTableModel<RetornoProvedorModel> otmRetornoProvedor = new ObjectTableModel<>(RetornoProvedorModel.class, "idRetorno,codigo,descricao,comentario,tipo,notificar");

    private final ObjectTableModel<LogParcelaModel> otmLogParcela = new ObjectTableModel<>(LogParcelaModel.class, "indice,idLogExtrator,idExtrator,idParcela,provedor,dataLog,observacao,status,dataRetorno,descricaoLog");

    private final ObjectTableModel<LogParcelaModel> otmHistorico = new ObjectTableModel<>(LogParcelaModel.class, "indice,status,origem,filial,cliente,nome,idLogExtrator,idExtrator,dataLog,idParcela,tipo,statusSpc,statusFacmat,dataRetorno,cogigoRetorno,descricaoLog,provedor,observacao");

    /**
     * Creates new form Extracao
     */
    public TelaDadosGerais() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
        carregarFiliais();
        paExtrator.setEnabledAt(4, false);
        //    SinclonizaRetorno();
        lProcessamentoModel = new LinkedList<>();
        cdFiltroDataExtracaoInicio.setDate(new Date());
        cdFiltroDataExtracaoFinal.setDate(new Date());
        cdInicioFiltroProcesso.setDate(new Date());
        cdFimFiltroProcesso.setDate(new Date());
        cdInicioFiltroHistorico.setDate(new Date());
        cdFinalFiltroHistorico.setDate(new Date());
        bPesquisar.setMnemonic(KeyEvent.VK_P);
        bGerarRetorno.setMnemonic(KeyEvent.VK_R);
        bGerarExcel.setMnemonic(KeyEvent.VK_E);
        bLimpar.setMnemonic(KeyEvent.VK_C);
        bGerarRetorno.setMnemonic(KeyEvent.VK_R);
        bEnviarEmail.setMnemonic(KeyEvent.VK_N);
        bExtornarBaixaContasReceber.setMnemonic(KeyEvent.VK_G);
        bConsutarRetornoBvs.setMnemonic(KeyEvent.VK_F);
        bEnviarInclusao.setMnemonic(KeyEvent.VK_I);
        bEnviarbaixa.setMnemonic(KeyEvent.VK_B);
        bFiltroCancelar.setMnemonic(KeyEvent.VK_C);
        bFiltroPesquisar.setMnemonic(KeyEvent.VK_P);
        bFiltroRetPesquisar.setMnemonic(KeyEvent.VK_P);
        bPesquisarProc.setMnemonic(KeyEvent.VK_P);
        bCancelProc.setMnemonic(KeyEvent.VK_C);
        bFinalizarProc.setMnemonic(KeyEvent.VK_F);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpsExtrator = new br.com.martinello.componentesbasicos.paineis.JPStatus();
        paExtrator = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppExtrator = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroExtrator = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rEmpresa = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroEmpresa = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroRegistro = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroOrigem = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroPagamento = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroPagamento = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroStatus = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroStatus = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroCodigoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        csFiltroCodigoCliente = new br.com.martinello.componentesbasicos.CampoString();
        rFiltroContrato = new br.com.martinello.componentesbasicos.Rotulo();
        cmFiltroContrato = new br.com.martinello.componentesbasicos.CampoMascara();
        ccccFiltroCPF = new br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta();
        rFiltroCpfCnpj = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDataExtracaoFinal = new br.com.martinello.componentesbasicos.CampoDataHora();
        cdFiltroDataEnvioInicio = new br.com.martinello.componentesbasicos.CampoDataHora();
        rFiltroDataEnvio = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDataEnvioFinal = new br.com.martinello.componentesbasicos.CampoDataHora();
        rFiltroDataExtracao = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDataExtracaoInicio = new br.com.martinello.componentesbasicos.CampoDataHora();
        bPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bLimpar = new br.com.martinello.componentesbasicos.Botao();
        rFiltroIdEtracao = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroIdParcela = new br.com.martinello.componentesbasicos.Rotulo();
        csFiltroIdExtracao = new br.com.martinello.componentesbasicos.CampoString();
        csFiltroIdParcela = new br.com.martinello.componentesbasicos.CampoString();
        rNome = new br.com.martinello.componentesbasicos.Rotulo();
        cscFiltroNome = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        rotulo1 = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroStatusProvedor = new br.com.martinello.componentesbasicos.CampoListaSimples();
        bExtornarBaixaContasReceber = new br.com.martinello.componentesbasicos.Botao();
        bGerarExcel = new br.com.martinello.componentesbasicos.Botao();
        bGerarRetorno = new br.com.martinello.componentesbasicos.Botao();
        bEnviarEmail = new br.com.martinello.componentesbasicos.Botao();
        bGerarPendencia = new br.com.martinello.componentesbasicos.Botao();
        botao1 = new br.com.martinello.componentesbasicos.Botao();
        botao2 = new br.com.martinello.componentesbasicos.Botao();
        ppTabelas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        paTabelas = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppExtratortb = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspExtrator = new javax.swing.JScrollPane();
        tpExtrator = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppBotoes = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        bEnviarbaixa = new br.com.martinello.componentesbasicos.Botao();
        bEnviarInclusao = new br.com.martinello.componentesbasicos.Botao();
        bConsutarRetornoBvs = new br.com.martinello.componentesbasicos.Botao();
        rNomeQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotalR = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotal = new br.com.martinello.componentesbasicos.Rotulo();
        bFinalizarRegistro = new br.com.martinello.componentesbasicos.Botao();
        jspParcela = new javax.swing.JScrollPane();
        tpParcela = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspCliente = new javax.swing.JScrollPane();
        tpInfomacoes = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspEndecoCliente = new javax.swing.JScrollPane();
        tpEndereco = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspAvalista = new javax.swing.JScrollPane();
        tpAvalista = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspEnderecoAvalista = new javax.swing.JScrollPane();
        tpEnderecoAvalista = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspLogRegistro = new javax.swing.JScrollPane();
        tpLoRegistro = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppProcessoEnvio = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppControleEnvioRegistro = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        paControleEnvioRegistro = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        jspControleEnvioRegistro = new javax.swing.JScrollPane();
        tpControleEnvioRegistro = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppProcessos = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppTabelaProcesso = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspProcessoDB = new javax.swing.JScrollPane();
        tpProcessoDB = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppFiltroProcesso = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        bPesquisarProc = new br.com.martinello.componentesbasicos.Botao();
        bCancelProc = new br.com.martinello.componentesbasicos.Botao();
        cdInicioFiltroProcesso = new br.com.martinello.componentesbasicos.CampoData();
        cdFimFiltroProcesso = new br.com.martinello.componentesbasicos.CampoData();
        rotulo2 = new br.com.martinello.componentesbasicos.Rotulo();
        rotulo3 = new br.com.martinello.componentesbasicos.Rotulo();
        clsOrigemFiltroProcesso = new br.com.martinello.componentesbasicos.CampoListaSimples();
        bFinalizarProc = new br.com.martinello.componentesbasicos.Botao();
        ppHistoricoRegistro = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspHistoricoRegistro = new javax.swing.JScrollPane();
        tpHistoricoRegistro = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppFiltroHistorico = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rFiltroCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroContratro = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroFilial = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroIdExtrator = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroStatus1 = new br.com.martinello.componentesbasicos.Rotulo();
        csFiltroCliente = new br.com.martinello.componentesbasicos.CampoString();
        csFiltroContrato = new br.com.martinello.componentesbasicos.CampoString();
        clsFiltroFilial = new br.com.martinello.componentesbasicos.CampoListaSimples();
        csFiltroIdExtrator = new br.com.martinello.componentesbasicos.CampoString();
        clsFiltroStatusHR = new br.com.martinello.componentesbasicos.CampoListaSimples();
        bFiltroPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroCancelar = new br.com.martinello.componentesbasicos.Botao();
        rFiltroDataExtracao1 = new br.com.martinello.componentesbasicos.Rotulo();
        cdInicioFiltroHistorico = new br.com.martinello.componentesbasicos.CampoData();
        cdFinalFiltroHistorico = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroOrigem = new br.com.martinello.componentesbasicos.Rotulo();
        clsOrigem = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroProvedor = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroProvedor = new br.com.martinello.componentesbasicos.CampoListaSimples();
        painelPadrao1 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ciFiltro = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ciFiltroRetId = new br.com.martinello.componentesbasicos.CampoInteiro();
        rFiltroRetId = new br.com.martinello.componentesbasicos.Rotulo();
        bFiltroRetPesquisar = new br.com.martinello.componentesbasicos.Botao();
        clsFiltroRetProvedor = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroRetProvedor = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroRetCodigo = new br.com.martinello.componentesbasicos.Rotulo();
        csFiltroRetCodigo = new br.com.martinello.componentesbasicos.CampoString();
        ppTbRetornos = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpRetornoProvedor = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppFinalizarRegistro = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rFinalizarRegistroFR = new br.com.martinello.componentesbasicos.Rotulo();
        rClienteFr = new br.com.martinello.componentesbasicos.Rotulo();
        rCodigoCliFr = new br.com.martinello.componentesbasicos.Rotulo();
        rNomeCliFR = new br.com.martinello.componentesbasicos.Rotulo();
        rCpfCliFR = new br.com.martinello.componentesbasicos.Rotulo();
        rCpfClienteFR = new br.com.martinello.componentesbasicos.Rotulo();
        rLojaFR = new br.com.martinello.componentesbasicos.Rotulo();
        clsMotivoFR = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rMotivoFR = new br.com.martinello.componentesbasicos.Rotulo();
        rObservacaoFR = new br.com.martinello.componentesbasicos.Rotulo();
        rContratoFR = new br.com.martinello.componentesbasicos.Rotulo();
        rIdExtratorFR = new br.com.martinello.componentesbasicos.Rotulo();
        rLojaDescFR = new br.com.martinello.componentesbasicos.Rotulo();
        rContratoDescFR = new br.com.martinello.componentesbasicos.Rotulo();
        rIdExtratoDescFR = new br.com.martinello.componentesbasicos.Rotulo();
        bCancelarFR = new br.com.martinello.componentesbasicos.Botao();
        bSalvarFR = new br.com.martinello.componentesbasicos.Botao();
        rotulo4 = new br.com.martinello.componentesbasicos.Rotulo();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaObservacao = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Dados Gerais");
        setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().add(jpsExtrator, java.awt.BorderLayout.PAGE_END);

        paExtrator.setPreferredSize(new java.awt.Dimension(1020, 633));
        paExtrator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paExtratorMouseClicked(evt);
            }
        });

        ppExtrator.setLayout(new java.awt.BorderLayout());

        ppFiltroExtrator.setPreferredSize(new java.awt.Dimension(1088, 210));

        rEmpresa.setText("Empresa:");

        rFiltroRegistro.setText("Origem:");

        clsFiltroOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Provedor", "Contas receber", "" }));
        clsFiltroOrigem.setSelectedIndex(2);

        rFiltroPagamento.setText("Pagamento:");

        clsFiltroPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não pago", "Pago", " " }));
        clsFiltroPagamento.setSelectedIndex(2);

        rFiltroStatus.setText("Status Extrator:");

        clsFiltroStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A enviar", "Erro Processamento", "Processado", "Processado Parcial", "Finalizado", "Invalido", "Prescrito", " " }));
        clsFiltroStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsFiltroStatusActionPerformed(evt);
            }
        });

        rFiltroCodigoCliente.setText("Código Cliente:");

        rFiltroContrato.setText("Contrato:");

        cmFiltroContrato.setMascara("**********");

        rFiltroCpfCnpj.setText("CPF/CNPJ:");

        cdFiltroDataEnvioInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDataEnvioInicioActionPerformed(evt);
            }
        });

        rFiltroDataEnvio.setText("Data de Envio:");

        rFiltroDataExtracao.setText("Data de Extração:");

        cdFiltroDataExtracaoInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDataExtracaoInicioActionPerformed(evt);
            }
        });

        bPesquisar.setText("Pesquisar");
        bPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPesquisarActionPerformed(evt);
            }
        });
        bPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bPesquisarKeyPressed(evt);
            }
        });

        bLimpar.setText("Cancelar");
        bLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimparActionPerformed(evt);
            }
        });

        rFiltroIdEtracao.setText("ID Extração:");

        rFiltroIdParcela.setText("ID Parcela:");

        rNome.setText("Nome:");

        rotulo1.setText("Status Provedor:");

        clsFiltroStatusProvedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enviado Spc", "Enviado Facmat", "Aguardando Facmat", "A Enviar Spc", "A Enviar Facmat", "Erro Facmat", "Erro Spc", " " }));
        clsFiltroStatusProvedor.setSelectedIndex(7);

        bExtornarBaixaContasReceber.setText("Gravar Data");
        bExtornarBaixaContasReceber.setAlignmentY(0.0F);
        bExtornarBaixaContasReceber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        bExtornarBaixaContasReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExtornarBaixaContasReceberActionPerformed(evt);
            }
        });

        bGerarExcel.setText("Excel");
        bGerarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarExcelActionPerformed(evt);
            }
        });

        bGerarRetorno.setText("Retorno CR");
        bGerarRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarRetornoActionPerformed(evt);
            }
        });

        bEnviarEmail.setText("Notificar Loja");
        bEnviarEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnviarEmailActionPerformed(evt);
            }
        });

        bGerarPendencia.setText("Gerar Pendência");
        bGerarPendencia.setEnabled(false);
        bGerarPendencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarPendenciaActionPerformed(evt);
            }
        });
        bGerarPendencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bGerarPendenciaKeyPressed(evt);
            }
        });

        botao1.setText("Alt. Parcela");
        botao1.setEnabled(false);
        botao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao1ActionPerformed(evt);
            }
        });

        botao2.setText("Alt. Endereço");
        botao2.setEnabled(false);
        botao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroExtratorLayout = new javax.swing.GroupLayout(ppFiltroExtrator);
        ppFiltroExtrator.setLayout(ppFiltroExtratorLayout);
        ppFiltroExtratorLayout.setHorizontalGroup(
            ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(rFiltroIdEtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(rNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rFiltroCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(csFiltroIdExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clsFiltroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ccccFiltroCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ppFiltroExtratorLayout.createSequentialGroup()
                                    .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(clsFiltroStatusProvedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                    .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(rFiltroStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rFiltroRegistro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rFiltroPagamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(clsFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clsFiltroOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clsFiltroStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rFiltroDataEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroDataExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cdFiltroDataExtracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDataEnvioInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cdFiltroDataExtracaoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDataEnvioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(csFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bExtornarBaixaContasReceber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bLimpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bGerarExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                        .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bGerarRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                        .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bEnviarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bGerarPendencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        ppFiltroExtratorLayout.setVerticalGroup(
            ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(rFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clsFiltroStatusProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(rFiltroIdEtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(csFiltroIdExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(rFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(csFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(cdFiltroDataEnvioInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rFiltroDataEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(rFiltroDataExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cdFiltroDataExtracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                                .addComponent(cdFiltroDataEnvioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cdFiltroDataExtracaoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ccccFiltroCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(rFiltroCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bGerarRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bEnviarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                .addComponent(bExtornarBaixaContasReceber, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                                .addComponent(bLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bGerarPendencia, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        ppExtrator.add(ppFiltroExtrator, java.awt.BorderLayout.NORTH);

        ppTabelas.setPreferredSize(new java.awt.Dimension(1024, 620));
        ppTabelas.setLayout(new java.awt.BorderLayout());

        paTabelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                paTabelasFocusLost(evt);
            }
        });

        ppExtratortb.setLayout(new java.awt.BorderLayout());

        tpExtrator.setModel(otmExtracao);
        tpExtrator.setPreferredScrollableViewportSize(new java.awt.Dimension(0, 0));
        tpExtrator.setShowGrid(true);
        tpExtrator.setVisibleRowCount(25);
        tpExtrator.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpExtratorFocusLost(evt);
            }
        });
        tpExtrator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpExtratorMouseClicked(evt);
            }
        });
        tpExtrator.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpExtratorKeyPressed(evt);
            }
        });
        tpExtrator.setAutoResizeMode(TabelaPadrao.AUTO_RESIZE_OFF);
        jspExtrator.setViewportView(tpExtrator);

        ppExtratortb.add(jspExtrator, java.awt.BorderLayout.CENTER);
        jspExtrator.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        ppBotoes.setMaximumSize(new java.awt.Dimension(32767, 150));
        ppBotoes.setPreferredSize(new java.awt.Dimension(1020, 40));

        bEnviarbaixa.setText("Enviar Baixa");
        bEnviarbaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnviarbaixaActionPerformed(evt);
            }
        });

        bEnviarInclusao.setText("Enviar Inclusão");
        bEnviarInclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnviarInclusaoActionPerformed(evt);
            }
        });

        bConsutarRetornoBvs.setText("Consultar Facmat");
        bConsutarRetornoBvs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsutarRetornoBvsActionPerformed(evt);
            }
        });

        rNomeQuantidade.setText("Quant:");
        rNomeQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotalR.setText("Valor:");
        rValorTotalR.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rValorTotal.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        bFinalizarRegistro.setText("Finalizar Registro");
        bFinalizarRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFinalizarRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppBotoesLayout = new javax.swing.GroupLayout(ppBotoes);
        ppBotoes.setLayout(ppBotoesLayout);
        ppBotoesLayout.setHorizontalGroup(
            ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppBotoesLayout.createSequentialGroup()
                .addComponent(rNomeQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(rValorTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addComponent(bFinalizarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bConsutarRetornoBvs, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bEnviarInclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bEnviarbaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ppBotoesLayout.setVerticalGroup(
            ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bEnviarbaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bEnviarInclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rNomeQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bConsutarRetornoBvs, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bFinalizarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rValorTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        ppExtratortb.add(ppBotoes, java.awt.BorderLayout.PAGE_END);

        paTabelas.addTab("Dados Gerais", ppExtratortb);

        tpParcela.setModel(otmParcela);
        tpParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpParcelaFocusLost(evt);
            }
        });
        tpParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpParcelaKeyPressed(evt);
            }
        });
        jspParcela.setViewportView(tpParcela);

        jspParcela.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        paTabelas.addTab("Parcela", jspParcela);

        tpInfomacoes.setModel(otmInformacoePessoal);
        tpInfomacoes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpInfomacoesFocusLost(evt);
            }
        });
        tpInfomacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpInfomacoesKeyPressed(evt);
            }
        });
        jspCliente.setViewportView(tpInfomacoes);

        paTabelas.addTab("Info. Cliente", jspCliente);

        tpEndereco.setModel(otmEndereco);
        tpEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpEnderecoFocusLost(evt);
            }
        });
        tpEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpEnderecoKeyPressed(evt);
            }
        });
        jspEndecoCliente.setViewportView(tpEndereco);

        paTabelas.addTab("Endereço Cliente", jspEndecoCliente);

        tpAvalista.setModel(otmAvalista);
        tpAvalista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpAvalistaKeyPressed(evt);
            }
        });
        jspAvalista.setViewportView(tpAvalista);

        paTabelas.addTab("Info. Avalista", jspAvalista);

        tpEnderecoAvalista.setModel(otmEnderecoAvalista);
        tpEnderecoAvalista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpEnderecoAvalistaKeyPressed(evt);
            }
        });
        jspEnderecoAvalista.setViewportView(tpEnderecoAvalista);

        paTabelas.addTab("Endereço Avalista", jspEnderecoAvalista);

        jspLogRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jspLogRegistroFocusLost(evt);
            }
        });

        tpLoRegistro.setModel(otmLogParcela);
        tpLoRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpLoRegistroFocusLost(evt);
            }
        });
        tpLoRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpLoRegistroKeyPressed(evt);
            }
        });
        jspLogRegistro.setViewportView(tpLoRegistro);

        paTabelas.addTab("Log Registro", jspLogRegistro);

        ppTabelas.add(paTabelas, java.awt.BorderLayout.CENTER);

        ppExtrator.add(ppTabelas, java.awt.BorderLayout.CENTER);

        paExtrator.addTab("Extrator", ppExtrator);

        ppProcessoEnvio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ppProcessoEnvioMouseClicked(evt);
            }
        });
        ppProcessoEnvio.setLayout(new java.awt.BorderLayout());

        ppControleEnvioRegistro.setLayout(new java.awt.BorderLayout());

        tpControleEnvioRegistro.setModel(otmProcessoBaixa);
        tpControleEnvioRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpControleEnvioRegistroFocusLost(evt);
            }
        });
        tpControleEnvioRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpControleEnvioRegistroKeyPressed(evt);
            }
        });
        jspControleEnvioRegistro.setViewportView(tpControleEnvioRegistro);

        paControleEnvioRegistro.addTab("Fila de Registros", jspControleEnvioRegistro);

        ppProcessos.setLayout(new java.awt.BorderLayout());

        ppTabelaProcesso.setLayout(new java.awt.BorderLayout());

        jspProcessoDB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jspProcessoDBMouseClicked(evt);
            }
        });

        tpProcessoDB.setModel(otmProcessoDB);
        tpProcessoDB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpProcessoDBFocusLost(evt);
            }
        });
        tpProcessoDB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpProcessoDBKeyPressed(evt);
            }
        });
        jspProcessoDB.setViewportView(tpProcessoDB);

        ppTabelaProcesso.add(jspProcessoDB, java.awt.BorderLayout.CENTER);

        bPesquisarProc.setText("Pesquisar");
        bPesquisarProc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPesquisarProcActionPerformed(evt);
            }
        });

        bCancelProc.setText("Cancelar");

        rotulo2.setText("Data Processo:");

        rotulo3.setText("Origem:");

        clsOrigemFiltroProcesso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Extrator", "Facmat", "Spc", " " }));

        bFinalizarProc.setText("Finalizar");
        bFinalizarProc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFinalizarProcActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroProcessoLayout = new javax.swing.GroupLayout(ppFiltroProcesso);
        ppFiltroProcesso.setLayout(ppFiltroProcessoLayout);
        ppFiltroProcessoLayout.setHorizontalGroup(
            ppFiltroProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroProcessoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(ppFiltroProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroProcessoLayout.createSequentialGroup()
                        .addComponent(rotulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsOrigemFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ppFiltroProcessoLayout.createSequentialGroup()
                        .addComponent(rotulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cdInicioFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cdFimFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 544, Short.MAX_VALUE)
                        .addComponent(bPesquisarProc, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancelProc, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(bFinalizarProc, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ppFiltroProcessoLayout.setVerticalGroup(
            ppFiltroProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroProcessoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsOrigemFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroProcessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cdInicioFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cdFimFiltroProcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisarProc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCancelProc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bFinalizarProc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        ppTabelaProcesso.add(ppFiltroProcesso, java.awt.BorderLayout.PAGE_START);

        ppProcessos.add(ppTabelaProcesso, java.awt.BorderLayout.CENTER);

        paControleEnvioRegistro.addTab("Processos", ppProcessos);

        ppControleEnvioRegistro.add(paControleEnvioRegistro, java.awt.BorderLayout.CENTER);

        ppProcessoEnvio.add(ppControleEnvioRegistro, java.awt.BorderLayout.CENTER);

        paExtrator.addTab("Processo Envio", ppProcessoEnvio);

        ppHistoricoRegistro.setLayout(new java.awt.BorderLayout());

        tpHistoricoRegistro.setModel(otmHistorico);
        tpHistoricoRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpHistoricoRegistroFocusLost(evt);
            }
        });
        tpHistoricoRegistro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpHistoricoRegistroKeyPressed(evt);
            }
        });
        jspHistoricoRegistro.setViewportView(tpHistoricoRegistro);

        ppHistoricoRegistro.add(jspHistoricoRegistro, java.awt.BorderLayout.CENTER);

        ppFiltroHistorico.setPreferredSize(new java.awt.Dimension(1220, 172));
        ppFiltroHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ppFiltroHistoricoMouseClicked(evt);
            }
        });

        rFiltroCliente.setText("Código Cliente:");

        rFiltroContratro.setText("Contrato:");

        rFiltroFilial.setText("Filial:");

        rFiltroIdExtrator.setText("Id Extrator:");

        rFiltroStatus1.setText("Status:");

        clsFiltroStatusHR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Erro", "Sucesso", "" }));
        clsFiltroStatusHR.setSelectedIndex(2);

        bFiltroPesquisar.setText("Pesquisar");
        bFiltroPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPesquisarActionPerformed(evt);
            }
        });

        bFiltroCancelar.setText("Cancelar");

        rFiltroDataExtracao1.setText("Data:");

        rFiltroOrigem.setText("Origem:");

        clsOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Extracao", "Inclusao", "Baixa", "Prescrito", "" }));
        clsOrigem.setSelectedIndex(4);

        rFiltroProvedor.setText("Provedor:");

        clsFiltroProvedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spc", "Facmat", "Extrator", "" }));
        clsFiltroProvedor.setSelectedIndex(3);
        clsFiltroProvedor.setToolTipText("");

        javax.swing.GroupLayout ppFiltroHistoricoLayout = new javax.swing.GroupLayout(ppFiltroHistorico);
        ppFiltroHistorico.setLayout(ppFiltroHistoricoLayout);
        ppFiltroHistoricoLayout.setHorizontalGroup(
            ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rFiltroOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroIdExtrator, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroContratro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                        .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(clsOrigem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clsFiltroStatusHR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                            .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(csFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rFiltroProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroDataExtracao1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(clsFiltroProvedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cdInicioFiltroHistorico, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                                        .addGap(426, 426, 426)
                                        .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cdFinalFiltroHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(bFiltroCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                        .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(csFiltroIdExtrator, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ppFiltroHistoricoLayout.setVerticalGroup(
            ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroHistoricoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsFiltroProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cdInicioFiltroHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroDataExtracao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cdFinalFiltroHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rFiltroContratro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rFiltroIdExtrator, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroIdExtrator, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsFiltroStatusHR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppHistoricoRegistro.add(ppFiltroHistorico, java.awt.BorderLayout.PAGE_START);

        paExtrator.addTab("Histórico Registro", ppHistoricoRegistro);

        painelPadrao1.setLayout(new java.awt.BorderLayout());

        ciFiltroRetId.setText("idRetorno");

        rFiltroRetId.setText("Id Retorno:");

        bFiltroRetPesquisar.setText("Pesquisar");
        bFiltroRetPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroRetPesquisarActionPerformed(evt);
            }
        });

        clsFiltroRetProvedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spc", "Facmat", "Extrator", "" }));
        clsFiltroRetProvedor.setSelectedIndex(3);
        clsFiltroRetProvedor.setToolTipText("");

        rFiltroRetProvedor.setText("Provedor:");

        rFiltroRetCodigo.setText("Código:");

        javax.swing.GroupLayout ciFiltroLayout = new javax.swing.GroupLayout(ciFiltro);
        ciFiltro.setLayout(ciFiltroLayout);
        ciFiltroLayout.setHorizontalGroup(
            ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ciFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rFiltroRetCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroRetId, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroRetProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ciFiltroLayout.createSequentialGroup()
                        .addComponent(clsFiltroRetProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 922, Short.MAX_VALUE)
                        .addComponent(bFiltroRetPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ciFiltroLayout.createSequentialGroup()
                        .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ciFiltroRetId, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(csFiltroRetCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ciFiltroLayout.setVerticalGroup(
            ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ciFiltroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ciFiltroRetId, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroRetId, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rFiltroRetCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroRetCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bFiltroRetPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ciFiltroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rFiltroRetProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clsFiltroRetProvedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        painelPadrao1.add(ciFiltro, java.awt.BorderLayout.PAGE_START);

        ppTbRetornos.setLayout(new java.awt.BorderLayout());

        tpRetornoProvedor.setModel(otmRetornoProvedor);
        tpRetornoProvedor.setColumnControl(bFiltroRetPesquisar);
        jScrollPane1.setViewportView(tpRetornoProvedor);

        ppTbRetornos.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        painelPadrao1.add(ppTbRetornos, java.awt.BorderLayout.CENTER);

        paExtrator.addTab("Tabela Retornos", painelPadrao1);

        rFinalizarRegistroFR.setText("FINALIZAR REGISTRO EXTRATOR");

        rClienteFr.setText("Cliente:");

        rNomeCliFR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        rCpfCliFR.setText("CPF:");

        rLojaFR.setText("Loja:");

        clsMotivoFR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RESTRIÇÃO JUDICIAL SPC", "RESTRIÇÃO JUDICIAL BVS", "DADOS INCOMPLETOS" }));

        rMotivoFR.setText("Motivo:");

        rObservacaoFR.setText("Observação:");

        rContratoFR.setText("Contrato:");

        rIdExtratorFR.setText("IdExtrator:");

        bCancelarFR.setText("Cancelar");
        bCancelarFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarFRActionPerformed(evt);
            }
        });

        bSalvarFR.setText("Salvar");
        bSalvarFR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalvarFRActionPerformed(evt);
            }
        });

        rotulo4.setText("Nome:");

        jtaObservacao.setColumns(20);
        jtaObservacao.setDocument(new FixedLengthDocument(250));
        jtaObservacao.setRows(5);
        jScrollPane2.setViewportView(jtaObservacao);

        javax.swing.GroupLayout ppFinalizarRegistroLayout = new javax.swing.GroupLayout(ppFinalizarRegistro);
        ppFinalizarRegistro.setLayout(ppFinalizarRegistroLayout);
        ppFinalizarRegistroLayout.setHorizontalGroup(
            ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                        .addComponent(rLojaFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rLojaDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rFinalizarRegistroFR, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                        .addComponent(rObservacaoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                        .addComponent(rContratoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rContratoDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                        .addComponent(rIdExtratorFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rIdExtratoDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                        .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rotulo4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rClienteFr, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                                        .addGap(353, 353, 353)
                                        .addComponent(bCancelarFR, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bSalvarFR, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(rNomeCliFR, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFinalizarRegistroLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rCodigoCliFr, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(479, 479, 479))))
                    .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ppFinalizarRegistroLayout.createSequentialGroup()
                            .addComponent(rMotivoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(clsMotivoFR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ppFinalizarRegistroLayout.createSequentialGroup()
                            .addComponent(rCpfCliFR, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rCpfClienteFR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(60, 60, 60))))
                .addContainerGap(567, Short.MAX_VALUE))
        );
        ppFinalizarRegistroLayout.setVerticalGroup(
            ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFinalizarRegistroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rFinalizarRegistroFR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rLojaFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rLojaDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rClienteFr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rCodigoCliFr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rotulo4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rNomeCliFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rCpfCliFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rCpfClienteFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rContratoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rContratoDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIdExtratorFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rIdExtratoDescFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clsMotivoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rMotivoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rObservacaoFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                .addGroup(ppFinalizarRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancelarFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bSalvarFR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        paExtrator.addTab("Finalizar Registro", ppFinalizarRegistro);

        getContentPane().add(paExtrator, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paExtratorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paExtratorMouseClicked
        String s;
    }//GEN-LAST:event_paExtratorMouseClicked

    private void ppFiltroHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ppFiltroHistoricoMouseClicked

    }//GEN-LAST:event_ppFiltroHistoricoMouseClicked

    private void bFiltroPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPesquisarActionPerformed
        buscarHistorico();
    }//GEN-LAST:event_bFiltroPesquisarActionPerformed

    private void tpHistoricoRegistroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpHistoricoRegistroKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmHistorico.getValueAt(tpHistoricoRegistro.getSelectedRow(), tpHistoricoRegistro.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpHistoricoRegistroKeyPressed

    private void tpHistoricoRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpHistoricoRegistroFocusLost
        copiarColar();
    }//GEN-LAST:event_tpHistoricoRegistroFocusLost

    private void ppProcessoEnvioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ppProcessoEnvioMouseClicked
        otmProcessoBaixa.setData(lProcessamentoModel);
        otmProcessoBaixa.fireTableDataChanged();
        if (tpControleEnvioRegistro.getRowCount() > 0) {
            tpControleEnvioRegistro.packAll();
            tpControleEnvioRegistro.addRowSelectionInterval(tpControleEnvioRegistro.convertRowIndexToView(0), tpControleEnvioRegistro.convertRowIndexToView(0));
            tpControleEnvioRegistro.grabFocus();
        }
    }//GEN-LAST:event_ppProcessoEnvioMouseClicked

    private void jspProcessoDBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jspProcessoDBMouseClicked
        carregarProcessoDB();
        otmProcessoDB.setData(lProcessoDBModel);
        otmProcessoDB.fireTableDataChanged();
        if (tpProcessoDB.getRowCount() > 0) {
            tpProcessoDB.packAll();
            tpProcessoDB.addRowSelectionInterval(tpProcessoDB.convertRowIndexToView(0), tpProcessoDB.convertRowIndexToView(0));
            tpProcessoDB.grabFocus();
        }
    }//GEN-LAST:event_jspProcessoDBMouseClicked

    private void tpProcessoDBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpProcessoDBKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmProcessoDB.getValueAt(tpProcessoDB.getSelectedRow(), tpProcessoDB.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpProcessoDBKeyPressed

    private void tpProcessoDBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpProcessoDBFocusLost
        copiarColar();
    }//GEN-LAST:event_tpProcessoDBFocusLost

    private void tpControleEnvioRegistroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpControleEnvioRegistroKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmProcessoBaixa.getValueAt(tpControleEnvioRegistro.getSelectedRow(), tpControleEnvioRegistro.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpControleEnvioRegistroKeyPressed

    private void tpControleEnvioRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpControleEnvioRegistroFocusLost

    }//GEN-LAST:event_tpControleEnvioRegistroFocusLost

    private void paTabelasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paTabelasFocusLost
        copiarColar();
    }//GEN-LAST:event_paTabelasFocusLost

    private void jspLogRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspLogRegistroFocusLost
        copiarColar();
    }//GEN-LAST:event_jspLogRegistroFocusLost

    private void tpLoRegistroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpLoRegistroKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmLogParcela.getValueAt(tpLoRegistro.getSelectedRow(), tpLoRegistro.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpLoRegistroKeyPressed

    private void tpLoRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpLoRegistroFocusLost
        copiarColar();
    }//GEN-LAST:event_tpLoRegistroFocusLost

    private void tpEnderecoAvalistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpEnderecoAvalistaKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmEnderecoAvalista.getValueAt(tpEnderecoAvalista.getSelectedRow(), tpEnderecoAvalista.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpEnderecoAvalistaKeyPressed

    private void tpAvalistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpAvalistaKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmAvalista.getValueAt(tpAvalista.getSelectedRow(), tpAvalista.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpAvalistaKeyPressed

    private void tpEnderecoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpEnderecoKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmEndereco.getValueAt(tpEndereco.getSelectedRow(), tpEndereco.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpEnderecoKeyPressed

    private void tpEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpEnderecoFocusLost
        copiarColar();
    }//GEN-LAST:event_tpEnderecoFocusLost

    private void tpInfomacoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpInfomacoesKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmInformacoePessoal.getValueAt(tpInfomacoes.getSelectedRow(), tpInfomacoes.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpInfomacoesKeyPressed

    private void tpInfomacoesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpInfomacoesFocusLost
        copiarColar();
    }//GEN-LAST:event_tpInfomacoesFocusLost

    private void tpParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpParcelaKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmParcela.getValueAt(tpParcela.getSelectedRow(), tpParcela.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpParcelaKeyPressed

    private void tpParcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpParcelaFocusLost
        copiarColar();
    }//GEN-LAST:event_tpParcelaFocusLost

    private void bEnviarEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnviarEmailActionPerformed

        int result;
        Object[] options = {"Sim", "Não"};
        result = JOptionPane.showOptionDialog(null, "Gostaria de enviar notificação do erros de registros para as lojas?", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {
            final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando envio de e-mail...");
            new Thread() {
                @Override
                public void run() {
                    try {
                        notificacaoControl = new NotificacaoControl();
                        ListFiliasComErro = new ArrayList<>();
                        ListFiliasComErro = notificacaoControl.ListFiliasComErro();
                        for (FilialModel filialErro : ListFiliasComErro) {
                            notificacaoControl = new NotificacaoControl();
                            ListRegistrosComErro = new ArrayList<>();
                            ListRegistrosComErro = notificacaoControl.ListRegistrosComErro(filialErro.getPontoFilial());
                            sendEmail.SendEmail(ListRegistrosComErro, filialErro.getEmail(), TelaPrincipal.userEmail, TelaPrincipal.senhaEmail, TelaPrincipal.emailPcopia, TelaPrincipal.email, TelaPrincipal.assEmail, TelaPrincipal.diretorio);
                            /*Teste de envio*/
                            //   sendEmail.SendEmailTeste("Erro teste", "nandog19@live.com", TelaPrincipal.userEmail, TelaPrincipal.senhaEmail, TelaPrincipal.emailPcopia, TelaPrincipal.email, TelaPrincipal.assEmail, TelaPrincipal.diretorio);

                        }
                        jpsExtrator.setStatus("Notificação enviada com sucesso.", jpsExtrator.NORMAL);
                    } catch (ErroSistemaException ex) {
                        jpsExtrator.setStatus("Erro ao notificar loja.", JPStatus.ERRO, ex);
                    }
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });
                }
            }.start();

            telaProcessamentoteste.setVisible(true);
            telaProcessamentoteste.requestFocusInWindow();
        } else {
            jpsExtrator.setStatus("Notificação não enviada.", jpsExtrator.NORMAL);
        }
    }//GEN-LAST:event_bEnviarEmailActionPerformed

    private void bGerarRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarRetornoActionPerformed
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando retorno...");
        retornoEnvio = new RetornoEnvioController();
        new Thread() {
            @Override
            public void run() {
                try {
                    retornoEnvio.retornoContasReceber();
                    //retornoEnvio.forcarPendencia();
                    jpsExtrator.setStatus("Retorno realizado com sucesso!", JPStatus.ALERTA);

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsExtrator.setStatus("Falha ao realizar retorno ao Contas Receber!", JPStatus.ERRO, ex);
                }
                SwingUtilities.invokeLater(() -> {
                    telaProcessamentoteste.dispose();
                });
            }
        }.start();

        telaProcessamentoteste.setVisible(true);
        telaProcessamentoteste.requestFocusInWindow();
    }//GEN-LAST:event_bGerarRetornoActionPerformed

    private void bGerarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarExcelActionPerformed
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Exportando Excel...");
        ExportarExcelControl exportarExcelControl = new ExportarExcelControl();

        new Thread() {
            @Override
            public void run() {
                try {
                    int result;
                    Object[] options = {"Movimento Diário", "Planilha Grid"};
                    result = JOptionPane.showOptionDialog(null, "Escolha tipo de documento a ser gerado!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (result == 0) {
                        exportarExcelControl.exportarMovimentoDiario(TelaPrincipal.diretorio);
                        jpsExtrator.setStatus("Arquivo gerado com sucesso.", jpsExtrator.NORMAL);
                    }
                    if (result == 1) {
                        exportarExcelControl.exportarDadosGerais(otmExtracao.getData(), TelaPrincipal.diretorio);
                        jpsExtrator.setStatus("Arquivo gerado com sucesso.", jpsExtrator.NORMAL);
                    }
                } catch (ErroSistemaException ex) {
                    jpsExtrator.setStatus("Erro", jpsExtrator.ERRO, ex);
                }
                SwingUtilities.invokeLater(() -> {
                    telaProcessamentoteste.dispose();
                });
            }
        }.start();

        telaProcessamentoteste.setVisible(true);
        telaProcessamentoteste.requestFocusInWindow();

        //CriaExcel();
    }//GEN-LAST:event_bGerarExcelActionPerformed

    private void bConsutarRetornoBvsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsutarRetornoBvsActionPerformed
        /**
         * Envio de registro de baixa para os provedores SPC e Facmat Caso
         * exista um processo em execução não sera possível realizar um novo até
         * que o atual termine
         */
        new Thread() {
            @Override
            public void run() {

                try {
                    lconsultarInclusaoBvs = new ArrayList<>();
                    bvsController = new ParcelasEnviarController();
                    lconsultarInclusaoBvs = bvsController.consultarInclusao();

                    enviadosConsultaBvs = 0;
                    enviadosConsultaBvsErro = 0;
                    enviadosConsultaBvsSucesso = 0;
                    tempInicioConsultaBvs = Utilitarios.dataHoraAtual();
                    tempFimConsultaBvs = "";

                    /* Adiciona na lista de Processamentos em Andamento */
                    processamentoModel = new ProcessamentoModel();
                    processamentoModel.setProvedor("FACMAT");
                    processamentoModel.setTipo("Consulta");
                    processamentoModel.setItensEnviados(0);
                    processamentoModel.setItensSucesso(0);
                    processamentoModel.setItensErro(0);
                    processamentoModel.setItensTotal(0);
                    processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
                    processamentoModel.setItensTotal(lconsultarInclusaoBvs != null ? lconsultarInclusaoBvs.size() : 0);
                    processamentoModel.setDataInicio(Utilitarios.dataHoraAtual());
                    processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
                    lProcessamentoModel.add(processamentoModel);
                    gravarProcessoDB(processamentoModel);

                    for (ParcelasEnviarModel parcelasConsultarBvsModel : lconsultarInclusaoBvs) {

                        bvsEnvioController = new BvsEnvioController();
                        processamentoConsultarBvsModel = new ProcessamentoModel();
                        retornoConsBvs = bvsEnvioController.consultarInclusaoBvs(parcelasConsultarBvsModel);
                        enviadosConsultaBvs++;
                        if (retornoConsBvs == 0) {
                            enviadosConsultaBvsErro++;
                        }
                        if (retornoConsBvs == 1) {
                            enviadosConsultaBvsSucesso++;
                        }
                        processamento();

                    }
                } catch (ErroSistemaException ex) {
                    jpsExtrator.setStatus("Erro " + ex.getMessage() + "", jpsExtrator.ERRO, ex);
                }

                for (ProcessamentoModel processamentoModel : lProcessamentoModel) {
                    if ("FACMAT".equals(processamentoModel.getProvedor()) && "Consulta".equals(processamentoModel.getTipo())) {
                        processamentoModel.setDataFim(Utilitarios.dataHoraAtual());
                        processamentoBvs = new ProcessamentoModel();
                        processamentoBvs.setProvedor("FACMAT");
                        processamentoBvs.setTipo("Consulta");
                        updateProcessoDBBvs(processamentoBvs);
                    }
                }

            }
        }.start();
        paExtrator.setSelectedComponent(ppProcessoEnvio);
    }//GEN-LAST:event_bConsutarRetornoBvsActionPerformed

    private void bEnviarInclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnviarInclusaoActionPerformed
        int result;
        Object[] options = {"Confirmar", "Cancelar"};
        result = JOptionPane.showOptionDialog(null, "Será Enviado Registros de Inclusão aos Provedores. \n Clique Confirmar para Continuar!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {
            new Thread() {
                @Override
                public void run() {
                    TelaPrincipal.barraStatus.setStatus("Realizando Envio de Registros aos provedores");
                    inclusaoFacmat();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    TelaPrincipal.barraStatus.setStatus("Realizando Envio de Registros aos provedores");
                    inclusaoSpc();
                    TelaPrincipal.barraStatus.setStatus("Ativo");

                }
            }.start();
        }
    }//GEN-LAST:event_bEnviarInclusaoActionPerformed

    private void bEnviarbaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnviarbaixaActionPerformed
        /**
         * Envio de registro de baixa para os provedores SPC e Facmat Caso
         * exista um processo em execução não sera possível realizar um novo até
         * que o atual termine
         */
        int result;
        Object[] options = {"Confirmar", "Cancelar"};
        result = JOptionPane.showOptionDialog(null, "Será Enviado Registros de Baixa aos Provedores. \n Clique Confirmar para Continuar!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 0) {

            new Thread() {
                @Override
                public void run() {
                    TelaPrincipal.barraStatus.setStatus("Realizando Envio de Registros aos provedores");
                    baixaFacmat();

                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    TelaPrincipal.barraStatus.setStatus("Realizando Envio de Registros aos provedores");
                    baixarSpc();
                }
            }.start();
        }
    }//GEN-LAST:event_bEnviarbaixaActionPerformed

    private void tpExtratorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpExtratorKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            // Cut, copy, paste and duplicate
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmExtracao.getValueAt(tpExtrator.getSelectedRow(), tpExtrator.getSelectedColumn()).toString();
            }
        }

    }//GEN-LAST:event_tpExtratorKeyPressed

    private void tpExtratorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpExtratorMouseClicked

        if (evt.getClickCount() == 2 && tpExtrator.getSelectedColumn() != 1) {

            final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");
            pessoaModel = new PessoaModel();
            enderecoModel = new EnderecoModel();
            new Thread() {
                @Override
                public void run() {
                    try {
                        linha = tpExtrator.getSelectedRow();

                        if (linha < 0) {
                            jpsExtrator.setStatus("Selecione uma Filial na Tabela, antes de clicar em alterar", JPStatus.ALERTA);
                        } else {
                            pessoaModel.setIdExtrator(tpExtrator.getValueAt(linha, 1).toString());
                            parcelaModel.setIdParcela(tpExtrator.getValueAt(linha, 16).toString());
                            // parcelaModel.setIdParcela(tpExtrator.getValueAt(linha, 16).toString());
                            lpessoaModel = pessoaController.extrairInfoPessoa(pessoaModel);
                            lparcelaModel = parcelaController.extrairParcela(parcelaModel);
                            lenderecoModel = enderecoController.extrairEndereco(parcelaModel, enderecoModel);
                            lLogParcela = parcelaController.extrairLogParcela(parcelaModel);

                            if (tpExtrator.getValueAt(linha, 31) != null) {
                                enderecoController = new EnderecoController();
                                enderecoModel = new EnderecoModel();
                                lenderecoAvalModel = enderecoController.extrairEnderecoAvalista(parcelaModel, enderecoModel);
                                lpessoaAvalModel = pessoaController.extrairInfoAvalista(pessoaModel);
                            }
                            paTabelas.setSelectedComponent(jspParcela);
                        }

                        otmInformacoePessoal.setData(lpessoaModel);
                        otmInformacoePessoal.fireTableDataChanged();

                        otmParcela.setData(lparcelaModel);
                        otmParcela.fireTableDataChanged();

                        otmEndereco.setData(lenderecoModel);
                        otmEndereco.fireTableDataChanged();

                        otmEnderecoAvalista.setData(lenderecoAvalModel);
                        otmEnderecoAvalista.fireTableDataChanged();

                        otmAvalista.setData(lpessoaAvalModel);
                        otmAvalista.fireTableDataChanged();

                        otmLogParcela.setData(lLogParcela);
                        otmLogParcela.fireTableDataChanged();

                        jpsExtrator.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

                        if (tpInfomacoes.getRowCount() > 0) {
                            tpInfomacoes.packAll();
                            tpInfomacoes.addRowSelectionInterval(tpInfomacoes.convertRowIndexToView(0), tpInfomacoes.convertRowIndexToView(0));
                            tpInfomacoes.grabFocus();
                        }
                        if (tpParcela.getRowCount() > 0) {
                            tpParcela.packAll();
                            tpParcela.addRowSelectionInterval(tpParcela.convertRowIndexToView(0), tpParcela.convertRowIndexToView(0));
                            tpParcela.grabFocus();
                        }
                        if (tpEndereco.getRowCount() > 0) {
                            tpEndereco.packAll();
                            tpEndereco.addRowSelectionInterval(tpEndereco.convertRowIndexToView(0), tpEndereco.convertRowIndexToView(0));
                            tpEndereco.grabFocus();

                        }
                        if (tpAvalista.getRowCount() > 0) {
                            tpAvalista.packAll();
                            tpAvalista.addRowSelectionInterval(tpAvalista.convertRowIndexToView(0), tpAvalista.convertRowIndexToView(0));
                            tpAvalista.grabFocus();

                        }

                        if (tpEnderecoAvalista.getRowCount() > 0) {
                            tpEnderecoAvalista.packAll();
                            tpEnderecoAvalista.addRowSelectionInterval(tpEnderecoAvalista.convertRowIndexToView(0), tpEnderecoAvalista.convertRowIndexToView(0));
                            tpEnderecoAvalista.grabFocus();
                        }
                        if (tpLoRegistro.getRowCount() > 0) {
                            tpLoRegistro.packAll();
                            tpLoRegistro.addRowSelectionInterval(tpLoRegistro.convertRowIndexToView(0), tpLoRegistro.convertRowIndexToView(0));
                            tpLoRegistro.grabFocus();
                        }
                    } catch (NullPointerException ex) {
                        jpsExtrator.setStatus("Erro ao pesquisar cliente.", JPStatus.ERRO, ex);
                        System.out.println(ex);
                        SwingUtilities.invokeLater(() -> {
                            telaProcessamentoteste.dispose();
                        });
                    } catch (SQLException ex) {
                        jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                    } catch (ErroSistemaException ex) {
                        jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                    }

                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });
                }
            }.start();

            telaProcessamentoteste.setVisible(true);
            telaProcessamentoteste.requestFocusInWindow();

        }
        if (evt.getClickCount() == 2 && tpExtrator.getSelectedColumn() == 0) {

        }
    }//GEN-LAST:event_tpExtratorMouseClicked

    private void tpExtratorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpExtratorFocusLost
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpExtratorFocusLost

    private void bLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimparActionPerformed
        limparFilro();
    }//GEN-LAST:event_bLimparActionPerformed

    private void bPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarActionPerformed
        buscarExtracoes();
        paTabelas.setSelectedComponent(ppExtratortb);

        System.out.println("Hora" + Utilitarios.dataHoraAtual());
    }//GEN-LAST:event_bPesquisarActionPerformed

    private void cdFiltroDataExtracaoInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataExtracaoInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataExtracaoInicioActionPerformed

    private void cdFiltroDataEnvioInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataEnvioInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataEnvioInicioActionPerformed

    private void clsFiltroStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsFiltroStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsFiltroStatusActionPerformed

    private void bPesquisarProcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarProcActionPerformed
        buscarProcessoDB();
    }//GEN-LAST:event_bPesquisarProcActionPerformed

    private void bExtornarBaixaContasReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExtornarBaixaContasReceberActionPerformed
        linha = tpExtrator.getSelectedRow();
        if (linha < 0) {
            jpsExtrator.setStatus("Selecione um registro na Tabela, antes de clicar em Gravar data", JPStatus.ALERTA);
        } else {

            extracaoModel = otmExtracao.getValue(tpExtrator.getLinhaSelecionada());
            extracaoModel.setIdFilial(extracaoModel.getIdFilial().substring(2, 4));
            java.awt.EventQueue.invokeLater(() -> {
                TelaExtorno telaExtorno = new TelaExtorno(new javax.swing.JFrame(), true, extracaoModel);
                telaExtorno.setSize(1150, 700);
                telaExtorno.setLocationRelativeTo(null);
                telaExtorno.setVisible(true);

            });

        }

    }//GEN-LAST:event_bExtornarBaixaContasReceberActionPerformed

    private void bFiltroRetPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroRetPesquisarActionPerformed
        buscarRetornoProvedor();
    }//GEN-LAST:event_bFiltroRetPesquisarActionPerformed

    private void bFinalizarRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFinalizarRegistroActionPerformed
        finalizarRegistro();
    }//GEN-LAST:event_bFinalizarRegistroActionPerformed

    private void bCancelarFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarFRActionPerformed
        // TODO add your handling code here:
        limparFinalizaRegistro();
    }//GEN-LAST:event_bCancelarFRActionPerformed

    private void bSalvarFRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalvarFRActionPerformed
        // TODO add your handling code here:
        if (jtaObservacao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Os campo observação deve ser preenchido");
        } else {
            try {
                parcelaController = new ParcelaController();
                int idRetorno = 0;
                String observacao;
                if (clsMotivoFR.getSelectedItem().toString().equalsIgnoreCase("DADOS INCOMPLETOS")) {
                    idRetorno = 361;
                } else if (clsMotivoFR.getSelectedItem().toString().equalsIgnoreCase("RESTRIÇÃO JUDICIAL BVS")) {
                    idRetorno = 360;
                } else if (clsMotivoFR.getSelectedItem().toString().equalsIgnoreCase("RESTRIÇÃO JUDICIAL SPC")) {
                    idRetorno = 359;
                }
                observacao = jtaObservacao.getText();
                regFinalizar.setDescricaoRetorno(observacao);
                regFinalizar.setIndice(idRetorno);
                int id = Integer.parseInt(idExtracao);
                parcelaController.finalizarRegistro(regFinalizar, id);
                JOptionPane.showMessageDialog(null, "Registro finalizado com sucesso");
                limparFinalizaRegistro();
            } catch (ErroSistemaException ex) {
                jpsExtrator.setStatus(ex.getMessage(), JPStatus.ERRO, 20);
            }

        }
    }//GEN-LAST:event_bSalvarFRActionPerformed

    private void bPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bPesquisarKeyPressed

    }//GEN-LAST:event_bPesquisarKeyPressed

    private void bFinalizarProcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFinalizarProcActionPerformed
        finalizarProcesso();
    }//GEN-LAST:event_bFinalizarProcActionPerformed

    private void bGerarPendenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarPendenciaActionPerformed
//        try {
//            retornoEnvio = new RetornoEnvioController();
//            retornoEnvio.gerarPendencia();
//        } catch (ErroSistemaException ex) {
//            jpsExtrator.setStatus("Erro ao gerar Pendencia.", JPStatus.ERRO, ex);
//        }
    }//GEN-LAST:event_bGerarPendenciaActionPerformed

    private void bGerarPendenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bGerarPendenciaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bGerarPendenciaKeyPressed

    private void botao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botao1ActionPerformed

    private void botao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao2ActionPerformed
        enderecoController = new EnderecoController();
        if (tabela.equalsIgnoreCase("End_Cli")) {
            int linha = tpEndereco.getSelectedRow();
            if (linha < 0) {
                jpsExtrator.setStatus("Selecione uma Filial na Tabela, antes de clicar em alterar", JPStatus.ALERTA);
            } else {
                int result;
                Object[] options = {"Confirmar", "Cancelar"};
                result = JOptionPane.showOptionDialog(null, "Será Alterado o Numero do contrato do cliente. \n Clique Confirmar para Continuar!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    enderecoModel = new EnderecoModel();
                    enderecoModel = otmEndereco.getValue(tpEndereco.getLinhaSelecionada());
                    enderecoController.AtualizarEndereco(enderecoModel);
                    jpsExtrator.setStatus("Alteração realizada com sucesso.", JPStatus.NORMAL);
                }
            }
        }
    }//GEN-LAST:event_botao2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Botao bCancelProc;
    private br.com.martinello.componentesbasicos.Botao bCancelarFR;
    private br.com.martinello.componentesbasicos.Botao bConsutarRetornoBvs;
    private br.com.martinello.componentesbasicos.Botao bEnviarEmail;
    private br.com.martinello.componentesbasicos.Botao bEnviarInclusao;
    private br.com.martinello.componentesbasicos.Botao bEnviarbaixa;
    private br.com.martinello.componentesbasicos.Botao bExtornarBaixaContasReceber;
    private br.com.martinello.componentesbasicos.Botao bFiltroCancelar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPesquisar;
    private br.com.martinello.componentesbasicos.Botao bFiltroRetPesquisar;
    private br.com.martinello.componentesbasicos.Botao bFinalizarProc;
    private br.com.martinello.componentesbasicos.Botao bFinalizarRegistro;
    private br.com.martinello.componentesbasicos.Botao bGerarExcel;
    private br.com.martinello.componentesbasicos.Botao bGerarPendencia;
    private br.com.martinello.componentesbasicos.Botao bGerarRetorno;
    private br.com.martinello.componentesbasicos.Botao bLimpar;
    private br.com.martinello.componentesbasicos.Botao bPesquisar;
    private br.com.martinello.componentesbasicos.Botao bPesquisarProc;
    private br.com.martinello.componentesbasicos.Botao bSalvarFR;
    private br.com.martinello.componentesbasicos.Botao botao1;
    private br.com.martinello.componentesbasicos.Botao botao2;
    private br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta ccccFiltroCPF;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataEnvioFinal;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataEnvioInicio;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoFinal;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoInicio;
    private br.com.martinello.componentesbasicos.CampoData cdFimFiltroProcesso;
    private br.com.martinello.componentesbasicos.CampoData cdFinalFiltroHistorico;
    private br.com.martinello.componentesbasicos.CampoData cdInicioFiltroHistorico;
    private br.com.martinello.componentesbasicos.CampoData cdInicioFiltroProcesso;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ciFiltro;
    private br.com.martinello.componentesbasicos.CampoInteiro ciFiltroRetId;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroEmpresa;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroFilial;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroOrigem;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroPagamento;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroProvedor;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroRetProvedor;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroStatus;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroStatusHR;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroStatusProvedor;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsMotivoFR;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsOrigem;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsOrigemFiltroProcesso;
    private br.com.martinello.componentesbasicos.CampoMascara cmFiltroContrato;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCliente;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.CampoString csFiltroContrato;
    private br.com.martinello.componentesbasicos.CampoString csFiltroIdExtracao;
    private br.com.martinello.componentesbasicos.CampoString csFiltroIdExtrator;
    private br.com.martinello.componentesbasicos.CampoString csFiltroIdParcela;
    private br.com.martinello.componentesbasicos.CampoString csFiltroRetCodigo;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroNome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private br.com.martinello.componentesbasicos.paineis.JPStatus jpsExtrator;
    private javax.swing.JScrollPane jspAvalista;
    private javax.swing.JScrollPane jspCliente;
    private javax.swing.JScrollPane jspControleEnvioRegistro;
    private javax.swing.JScrollPane jspEndecoCliente;
    private javax.swing.JScrollPane jspEnderecoAvalista;
    private javax.swing.JScrollPane jspExtrator;
    private javax.swing.JScrollPane jspHistoricoRegistro;
    private javax.swing.JScrollPane jspLogRegistro;
    private javax.swing.JScrollPane jspParcela;
    private javax.swing.JScrollPane jspProcessoDB;
    private javax.swing.JTextArea jtaObservacao;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paControleEnvioRegistro;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paTabelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppBotoes;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppControleEnvioRegistro;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppExtratortb;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroHistorico;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroProcesso;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFinalizarRegistro;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppHistoricoRegistro;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppProcessoEnvio;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppProcessos;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppTabelaProcesso;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppTabelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppTbRetornos;
    private br.com.martinello.componentesbasicos.Rotulo rClienteFr;
    private br.com.martinello.componentesbasicos.Rotulo rCodigoCliFr;
    private br.com.martinello.componentesbasicos.Rotulo rContratoDescFR;
    private br.com.martinello.componentesbasicos.Rotulo rContratoFR;
    private br.com.martinello.componentesbasicos.Rotulo rCpfCliFR;
    private br.com.martinello.componentesbasicos.Rotulo rCpfClienteFR;
    private br.com.martinello.componentesbasicos.Rotulo rEmpresa;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroContrato;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroContratro;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCpfCnpj;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataEnvio;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataExtracao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataExtracao1;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroFilial;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdEtracao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdExtrator;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdParcela;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroOrigem;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroPagamento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroProvedor;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroRegistro;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroRetCodigo;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroRetId;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroRetProvedor;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroStatus;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroStatus1;
    private br.com.martinello.componentesbasicos.Rotulo rFinalizarRegistroFR;
    private br.com.martinello.componentesbasicos.Rotulo rIdExtratoDescFR;
    private br.com.martinello.componentesbasicos.Rotulo rIdExtratorFR;
    private br.com.martinello.componentesbasicos.Rotulo rLojaDescFR;
    private br.com.martinello.componentesbasicos.Rotulo rLojaFR;
    private br.com.martinello.componentesbasicos.Rotulo rMotivoFR;
    private br.com.martinello.componentesbasicos.Rotulo rNome;
    private br.com.martinello.componentesbasicos.Rotulo rNomeCliFR;
    private br.com.martinello.componentesbasicos.Rotulo rNomeQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rObservacaoFR;
    private br.com.martinello.componentesbasicos.Rotulo rQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotal;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalR;
    private br.com.martinello.componentesbasicos.Rotulo rotulo1;
    private br.com.martinello.componentesbasicos.Rotulo rotulo2;
    private br.com.martinello.componentesbasicos.Rotulo rotulo3;
    private br.com.martinello.componentesbasicos.Rotulo rotulo4;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpAvalista;
    private static br.com.martinello.componentesbasicos.TabelaPadrao tpControleEnvioRegistro;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpEndereco;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpEnderecoAvalista;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpExtrator;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpHistoricoRegistro;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpInfomacoes;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpLoRegistro;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpParcela;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpProcessoDB;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpRetornoProvedor;
    // End of variables declaration//GEN-END:variables

    private boolean buscarExtracoes() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");
        filtroConsultaExtracao = new ExtracaoTableModel();
        contFiltro = 0;

        /* Empresa */
        if (!clsFiltroEmpresa.getSelectedItem().toString().equals("TODAS")) {

            FilialModel filialModel = new FilialModel();
            String filial = clsFiltroEmpresa.getSelectedItem().toString();
            try {
                filialModel = filiaisControler.listarFilialExtracao(filial);
                filtroConsultaExtracao.setIdFilial(filialModel.getPontoFilial());

            } catch (ErroSistemaException ex) {
                jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);

            }

        }

        /* CPF */
        if (ccccFiltroCPF.getFiltroOld() != null) {
            filtroConsultaExtracao.setCpfCnpj(ccccFiltroCPF.getFiltroOld().replaceAll("[^0-9]", ""));
            contFiltro++;
        }

        /* Nome */
        if (cscFiltroNome.getFiltroOld() != null) {
            filtroConsultaExtracao.setNome(cscFiltroNome.getFiltroOld().toString().toUpperCase());
            contFiltro++;
        }

        /* Contrato */
        if (cmFiltroContrato.getText().trim().length() > 1) {
            filtroConsultaExtracao.setNumeroDoc(cmFiltroContrato.getText());
            contFiltro++;
        }

        /* Código do Cliente */
        if (csFiltroCodigoCliente.getText().length() > 0) {
            filtroConsultaExtracao.setIdCliente(csFiltroCodigoCliente.getText());
            contFiltro++;
        }

        /* Id. do Extrator */
        if (csFiltroIdExtracao.getText().length() > 0) {
            filtroConsultaExtracao.setIdExtracao(csFiltroIdExtracao.getText());
            contFiltro++;
        }

        /* Id. Parcela */
        if (csFiltroIdParcela.getText().length() > 0) {
            filtroConsultaExtracao.setIdParcela(csFiltroIdParcela.getText());
            contFiltro++;
        }

        /* Tipo de Pagamento (Pago / Não Pago) da Parcela */
        if (clsFiltroPagamento.getSelectedItem().toString().equals("Pago")) {
            filtroConsultaExtracao.setPago("S");
            contFiltro++;
        } else if (clsFiltroPagamento.getSelectedItem().toString().equals("Não pago")) {
            filtroConsultaExtracao.setPago("N");
            contFiltro++;
        }


        /* Origem - P: Importado na Implantação, C - Contas a Receber Extração Normal. */
        if (clsFiltroOrigem.getSelectedItem().toString().equals("Contas receber")) {
            filtroConsultaExtracao.setOrigemRegistro("C");
            contFiltro++;
        } else if (clsFiltroOrigem.getSelectedItem().toString().equals("Provedor")) {
            filtroConsultaExtracao.setOrigemRegistro("P");
            contFiltro++;
        }

        /* Status Extrator */
        if (clsFiltroStatus.getSelectedItem().toString().length() > 1) {
            filtroConsultaExtracao.setStatus(clsFiltroStatus.getSelectedItem().toString().toUpperCase());
            contFiltro++;
        }

        /* Status Provedor */
        if (clsFiltroStatusProvedor.getSelectedItem().toString().length() > 1) {
            filtroConsultaExtracao.setStatusProvedor(clsFiltroStatusProvedor.getSelectedItem().toString().toUpperCase());
            contFiltro++;
        }

        /* Data de Envio */
        if (cdFiltroDataEnvioInicio.getDate() != null) {
            if (cdFiltroDataEnvioFinal.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioInicio(Utilitarios.converteDataString(cdFiltroDataEnvioInicio.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsExtrator.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }
        }

        if (cdFiltroDataEnvioFinal.getDate() != null) {
            if (cdFiltroDataEnvioInicio.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioFim(Utilitarios.converteDataString(cdFiltroDataEnvioFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jpsExtrator.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        /* Data Extração */
        if (cdFiltroDataExtracaoInicio.getDate() != null) {
            if (cdFiltroDataExtracaoFinal.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoInicio(Utilitarios.converteDataString(cdFiltroDataExtracaoInicio.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsExtrator.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        if (cdFiltroDataExtracaoFinal.getDate() != null) {
            if (cdFiltroDataExtracaoInicio.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoFim(Utilitarios.converteDataString(cdFiltroDataExtracaoFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jpsExtrator.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        new Thread() {
            @Override
            public void run() {
                try {
                    if (contFiltro >= 1) {
                        lextracaoTableModel = extracaoTableController.listarExtracaoTableController(filtroConsultaExtracao);
                        otmExtracao.setData(lextracaoTableModel);
                        otmExtracao.fireTableDataChanged();
                        quantidade = (lextracaoTableModel.size());
                        rQuantidade.setText("" + quantidade);

                        tpExtrator.setForeground(Color.BLACK);

                        jpsExtrator.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

                    } else {
                        jpsExtrator.setStatus("Erro ao pesquisar cliente é necessário informar ao menos 1 campo ", JPStatus.ERRO, 20);
                    }
                } catch (NullPointerException ex) {
                    jpsExtrator.setStatus("Erro ao pesquisar cliente.", JPStatus.ERRO, ex);
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }

                SwingUtilities.invokeLater(() -> {
                    if (tpExtrator.getRowCount() > 0) {
                        tpExtrator.packAll();
                        tpExtrator.addRowSelectionInterval(tpExtrator.convertRowIndexToView(0), tpExtrator.convertRowIndexToView(0));
                        tpExtrator.grabFocus();
                    }
                    telaProcessamentoteste.dispose();
                });
            }
        }
                .start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
        Date dataFinalAval = new Date();
        Calendar calendarDataAval = Calendar.getInstance();
        calendarDataAval.setTime(dataFinalAval);
        int numeroDiasParaSubtrairAval = 5843;
        // achar data de início
        calendarDataAval.add(Calendar.DATE, numeroDiasParaSubtrairAval);
        Date dataInicialAval = calendarDataAval.getTime();
        Calendar agora = Calendar.getInstance();

        // formata e exibe a data e hora atual
        Format formato = new SimpleDateFormat("dd/MM/yyyy");
        // vamos subtrair 5 minutos a esta data
        agora.add(Calendar.DATE, -6574);

        // formata e exibe o resultado
        formato = new SimpleDateFormat("dd/MM/yyyy");

        calculaTotalProdutos();
        return retornoPesquisar;
    }

    private void calculaTotalProdutos() {
        try {
            valorTotal = 0;
            for (int i = 0; i < tpExtrator.getRowCount(); i++) {
                Double valorAux;
                valorAux = (Double) tpExtrator.getValueAt(i, 14);
                valorTotal += valorAux.doubleValue();
            }

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            rValorTotal.setText(nf.format(valorTotal));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular Total Produtos: " + e.getMessage());
        }
    }

    private boolean buscarHistorico() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");

        FilialController filialController = new FilialController();

        LogExtracaoController logExtracaoController = new LogExtracaoController();
        LogParcelaModel filtroLogExtracao = new LogParcelaModel();
        if (clsFiltroFilial.getSelectedItem().toString().equals("TODAS")) {
        } else {
            FilialModel filialModel = new FilialModel();
            String filial = clsFiltroFilial.getSelectedItem().toString();
            try {
                filialModel = filialController.listarFilialExtracao(filial);

            } catch (ErroSistemaException ex) {
                jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
            }
            filtroLogExtracao.setFilial(filialModel.getPontoFilial());
        }
        if (csFiltroCliente.getText().length() > 0) {
            filtroLogExtracao.setCliente(csFiltroCliente.getText());
        }
        if (csFiltroContrato.getText().length() > 0) {
            filtroLogExtracao.setContrato(csFiltroContrato.getText());

        }
        if (csFiltroIdExtrator.getText().length() > 0) {
            filtroLogExtracao.setIdExtrator((Integer.parseInt(csFiltroIdExtrator.getText())));

        }
        if (cdInicioFiltroHistorico.getDate() != null | cdFinalFiltroHistorico.getDate() != null) {
            if (cdFinalFiltroHistorico.getDate() != null) {
                filtroLogExtracao.setDataLog(cdInicioFiltroHistorico.getDate());
                filtroLogExtracao.setDataLogFim(cdFinalFiltroHistorico.getDate());
            } else {
                jpsExtrator.setStatus("O campo data precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoHistorico = false;
            }
            if (cdInicioFiltroHistorico.getDate() != null) {
                filtroLogExtracao.setDataLog(cdInicioFiltroHistorico.getDate());
                filtroLogExtracao.setDataLogFim(cdFinalFiltroHistorico.getDate());
            } else {
                jpsExtrator.setStatus("O campo data precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoHistorico = false;
            }
        } else {
            jpsExtrator.setStatus("O campo data precisa ser preenchido por favor preencha o campo!", JPStatus.ALERTA, 20);
            return retornoHistorico = false;
        }

        if (clsFiltroStatusHR.getSelectedItem().toString().equals("")) {

        } else if (clsFiltroStatusHR.getSelectedItem().toString().equalsIgnoreCase("sucesso")) {
            filtroLogExtracao.setStatus("S");
        } else if (clsFiltroStatusHR.getSelectedItem().toString().equalsIgnoreCase("erro")) {
            filtroLogExtracao.setStatus("E");
        }
        if (clsFiltroProvedor.getSelectedItem().toString().equals("")) {

        } else {
            if (clsFiltroProvedor.getSelectedItem().toString().equalsIgnoreCase("Spc")) {
                filtroLogExtracao.setProvedor("S");
            } else if (clsFiltroProvedor.getSelectedItem().toString().equalsIgnoreCase("Facmat")) {
                filtroLogExtracao.setProvedor("B");
            } else if (clsFiltroProvedor.getSelectedItem().toString().equalsIgnoreCase("Extrator")) {
                filtroLogExtracao.setProvedor("E");
            }
        }
        if (clsFiltroOrigem.getSelectedItem().toString().equals("")) {

        } else {
            if (clsFiltroOrigem.getSelectedItem().toString().equalsIgnoreCase("Inclusão")) {
                filtroLogExtracao.setOrigem("I");
            } else if (clsFiltroOrigem.getSelectedItem().toString().equalsIgnoreCase("Baixa")) {
                filtroLogExtracao.setOrigem("B");
            } else if (clsFiltroOrigem.getSelectedItem().toString().equalsIgnoreCase("Prescrito")) {
                filtroLogExtracao.setOrigem("P");
            } else if (clsFiltroOrigem.getSelectedItem().toString().equalsIgnoreCase("Extração")) {
                filtroLogExtracao.setOrigem("E");
            }
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    lLogExtracaoModel = parcelaController.extrairLog(filtroLogExtracao);

                    otmHistorico.setData(lLogExtracaoModel);
                    otmHistorico.fireTableDataChanged();

                    jpsExtrator.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

                    if (tpHistoricoRegistro.getRowCount() > 0) {
                        /* tpHistoricoRegistro.packAll();
                        tpHistoricoRegistro.addRowSelectionInterval(tpHistoricoRegistro.convertRowIndexToView(0), tpHistoricoRegistro.convertRowIndexToView(0));
                        tpHistoricoRegistro.grabFocus(); */
                    }

                } catch (NullPointerException ex) {
                    jpsExtrator.setStatus("Erro ao pesquisar Log_Extração.", JPStatus.ERRO, ex);
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });
                } catch (SQLException ex) {
                    jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                } catch (ErroSistemaException ex) {
                    jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }

                SwingUtilities.invokeLater(() -> {
                    tpHistoricoRegistro.packAll();
                    tpHistoricoRegistro.addRowSelectionInterval(tpHistoricoRegistro.convertRowIndexToView(0), tpHistoricoRegistro.convertRowIndexToView(0));
                    tpHistoricoRegistro.grabFocus();
                    telaProcessamentoteste.dispose();
                });
            }
        }.start();

        telaProcessamentoteste.setVisible(true);
        telaProcessamentoteste.requestFocusInWindow();
        return retornoHistorico;
    }

    private void carregarFiliais() {
        filtroFilialModel = new FilialModel();
        try {
            lfiliaisModel = filiaisControler.listarFilialController();
        } catch (ErroSistemaException ex) {
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }

        for (FilialModel filiais : lfiliaisModel) {
            clsFiltroEmpresa.addItem(filiais);
            clsFiltroFilial.addItem(filiais);
        }
    }

    private void carregarMotivos() {
        filtroFilialModel = new FilialModel();
        try {
            lfiliaisModel = filiaisControler.listarFilialController();
        } catch (ErroSistemaException ex) {
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }

        for (FilialModel filiais : lfiliaisModel) {
            clsFiltroEmpresa.addItem(filiais);
            clsFiltroFilial.addItem(filiais);
        }
    }

    private void carregarProcessoDB() {
        processamentoController = new ProcessamentoController();
        try {
            lProcessoDBModel = processamentoController.listProcessoDB();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus("Erro ao carregar processos abertos " + ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
        otmProcessoDB.setData(lProcessoDBModel);
        otmProcessoDB.fireTableDataChanged();
        if (tpProcessoDB.getRowCount() > 0) {
            tpProcessoDB.packAll();
            tpProcessoDB.addRowSelectionInterval(tpProcessoDB.convertRowIndexToView(0), tpProcessoDB.convertRowIndexToView(0));
            tpProcessoDB.grabFocus();
        }
    }

    private void buscarProcessoDB() {
        processamentoController = new ProcessamentoController();
        try {
            ProcessamentoModel consultaProcesso = new ProcessamentoModel();
            consultaProcesso.setProvedor(clsOrigemFiltroProcesso.getString().toUpperCase());
            consultaProcesso.setDataInicio(Utilitarios.converteDataString(cdInicioFiltroProcesso.getDate(), "dd-MM-yyyy"));
            consultaProcesso.setDataFim(Utilitarios.converteDataString(cdFimFiltroProcesso.getDate(), "dd-MM-yyyy"));
            lProcessoDBModel = processamentoController.buscaProcessoDB(consultaProcesso);

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus("Erro ao carregar processos abertos " + ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
        otmProcessoDB.setData(lProcessoDBModel);
        otmProcessoDB.fireTableDataChanged();
        if (tpProcessoDB.getRowCount() > 0) {
            tpProcessoDB.packAll();
            tpProcessoDB.addRowSelectionInterval(tpProcessoDB.convertRowIndexToView(0), tpProcessoDB.convertRowIndexToView(0));
            tpProcessoDB.grabFocus();
        }
    }

//    private void insertProcessoDB(ProcessamentoModel processamentoModel, String provedor, String tipoRegistro) {
//        processamentoController = new ProcessamentoController();
//        processamentoModel.setProvedor(provedor);
//        processamentoModel.setTipo(tipoRegistro);
//        processamentoModel.setDataInicioEnvio(Utilitarios.formataData(new Date()));
//        processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
//        processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
//        processamentoController.insertProcessoDB(processamentoModel);
//    }
    private void gravarProcessoDB(ProcessamentoModel processamentoModel) {
        processamentoController = new ProcessamentoController();
        try {
            processamentoController.insertProcessoDB(processamentoModel);
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }

    private void updateProcessoDB() {
        processamentoController = new ProcessamentoController();
        try {
            processamentoController.updateProcessoDB(processamentoModel);

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }

    private void updateProcessoDBBvs(ProcessamentoModel processamentoBvs) {
        processamentoController = new ProcessamentoController();
        try {
            processamentoController.updateProcessoDB(processamentoBvs);

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }

    private void updateProcessoDBSpc(ProcessamentoModel processamentoModelSpc) {
        processamentoController = new ProcessamentoController();
        try {
            processamentoController.updateProcessoDBSpc(processamentoModelSpc);

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }

    private void processamento() {
        lProcessamentoModel.forEach((processamentoModel) -> {
            if ("SPC".equals(processamentoModel.getProvedor()) && "BAIXA".equals(processamentoModel.getTipo())) {
                processamentoModel.setItensSucesso(enviadosBaixaSucessoSpc);
                processamentoModel.setItensErro(enviadosBaixaErroSpc);
                processamentoModel.setItensEnviados(enviadosBaixaSpc);
            } else if ("FACMAT".equals(processamentoModel.getProvedor()) && "BAIXA".equals(processamentoModel.getTipo())) {
                processamentoModel.setItensSucesso(enviadosBaixaBvsSucesso);
                processamentoModel.setItensErro(valorEnviadosErro);
                processamentoModel.setItensEnviados(enviadosBaixaBvs);
            } else if ("SPC".equals(processamentoModel.getProvedor()) && "INCLUSAO".equals(processamentoModel.getTipo())) {
                processamentoModel.setItensSucesso(enviadosInclusaoSucessoSpc);
                processamentoModel.setItensErro(enviadosInclusaoErroSpc);
                processamentoModel.setItensEnviados(enviadosInclusaoSpc);
            } else if ("FACMAT".equals(processamentoModel.getProvedor()) && "INCLUSAO".equals(processamentoModel.getTipo())) {
                processamentoModel.setItensSucesso(enviadosInclusaoBvsSucesso);
                processamentoModel.setItensErro(enviadosInclusaoBvsErro);
                processamentoModel.setItensEnviados(enviadosInclusaoBvs);
            } else if ("FACMAT".equals(processamentoModel.getProvedor()) && "Consulta".equals(processamentoModel.getTipo())) {
                processamentoModel.setItensSucesso(enviadosConsultaBvsSucesso);
                processamentoModel.setItensErro(enviadosConsultaBvsErro);
                processamentoModel.setItensEnviados(enviadosConsultaBvs);
            }
        });

        otmProcessoBaixa.setData(lProcessamentoModel);
        otmProcessoBaixa.fireTableDataChanged();
        if (tpControleEnvioRegistro.getRowCount() > 0) {
            tpControleEnvioRegistro.packAll();
            tpControleEnvioRegistro.addRowSelectionInterval(tpControleEnvioRegistro.convertRowIndexToView(0), tpControleEnvioRegistro.convertRowIndexToView(0));
            tpControleEnvioRegistro.grabFocus();
        }
    }

    private void inclusaoFacmat() {
        inclusaoFacmat = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    /**
                     * Processo de envio de Inclusões ao SPC, só tera inicio ao
                     * processo caso não tenha um outro processo em andamento.
                     */

                    processamentoController = new ProcessamentoController();
                    processoDBFacmat = processamentoController.consultarProcesso("FACMAT", "INCLUSAO");
                    processamentoController = new ProcessamentoController();
                    processoDBExtracaoFacmat = processamentoController.consultarProcesso("EXTRATOR", "EXTRACAO");
                    if (processoDBFacmat == false && processoDBExtracaoFacmat == false) {
                        linclusaoBvsModel = new ArrayList();
                        linclusaoBvsModel = bvsController.listarInclusaoBvs();
                        for (int i = 0; i < lProcessamentoModel.size(); i++) {
                            ProcessamentoModel p = lProcessamentoModel.get(i);
                            if (p.getProvedor().equals("FACMAT") && p.getTipo().equals("INCLUSAO")) {
                                System.out.println("index" + i);
                                lProcessamentoModel.remove(i);
                                break;
                            }
                        }

                        processamentoModel = new ProcessamentoModel();
                        processamentoModel.setProvedor("FACMAT");
                        processamentoModel.setTipo("INCLUSAO");
                        processamentoModel.setItensEnviados(0);
                        processamentoModel.setItensSucesso(0);
                        processamentoModel.setItensErro(0);
                        processamentoModel.setItensTotal(0);
                        processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
                        processamentoModel.setItensTotal(linclusaoBvsModel != null ? linclusaoBvsModel.size() : 0);
                        processamentoModel.setDataInicio(Utilitarios.dataHoraAtual());
                        processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
                        lProcessamentoModel.add(processamentoModel);

                        gravarProcessoDB(processamentoModel);

                        /* Contadores do Processamento */
                        enviadosInclusaoBvs = 0;
                        enviadosInclusaoBvsErro = 0;
                        enviadosInclusaoBvsSucesso = 0;

                        for (ParcelasEnviarModel parcelasEnviarInclusaoBvsModel : linclusaoBvsModel) {
                            try {
                                bvsEnvioController = new BvsEnvioController();
                                processamentoInclusaoBvsModel = new ProcessamentoModel();
                                if (parcelasEnviarInclusaoBvsModel.getStatusFacmat().equalsIgnoreCase("A")) {
                                    retornoIncBvs = bvsEnvioController.enviarInclusaoBVS(parcelasEnviarInclusaoBvsModel);

                                    enviadosInclusaoBvs++;

                                    if (retornoIncBvs == 0) {
                                        enviadosInclusaoBvsErro++;
                                    } else if (retornoIncBvs == 1) {
                                        enviadosInclusaoBvsSucesso++;
                                    }
                                    processamento();
                                } else {
                                    retornoIncBvs = bvsEnvioController.naoEnviarInclusaoBVS(parcelasEnviarInclusaoBvsModel);
                                    retornoEnvio.atualizaStatus(parcelasEnviarInclusaoBvsModel.getIdExtrator());
                                }
                                // TimeUnit.SECONDS.sleep(1);

//                            } catch (InterruptedException ex) {
//                                ex.printStackTrace();
//                                jpsExtrator.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
                            } catch (ErroSistemaException ex) {
                                /* Gravar no log da Parcela */
                                ex.printStackTrace();
                                jpsExtrator.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
                            }

                        }

                        for (ProcessamentoModel processamentoModel : lProcessamentoModel) {
                            if ("FACMAT".equals(processamentoModel.getProvedor()) && "INCLUSAO".equals(processamentoModel.getTipo())) {
                                processamentoModel.setDataFim(Utilitarios.dataHoraAtual());
                                ProcessamentoModel processamentoBvs = new ProcessamentoModel();
                                processamentoBvs.setProvedor("FACMAT");
                                processamentoBvs.setTipo("INCLUSAO");
                                updateProcessoDBBvs(processamentoBvs);
                            }
                        }
                    } else {
                        if (processoDBExtracaoFacmat == true) {
                            jpsExtrator.setStatus("Erro! Existe um processo de extração em andamento. Aguarde o término", JPStatus.ERRO);
                        } else {
                            jpsExtrator.setStatus("Erro já existe um processo de envio de inclusão em andamento", JPStatus.ERRO);
                        }

                        carregarProcessoDB();
                        paExtrator.setSelectedComponent(ppProcessoEnvio);
                        paControleEnvioRegistro.setSelectedComponent(jspProcessoDB);

                    }
                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    private void inclusaoSpc() {
        new Thread() {
            @Override
            public void run() {

                /**
                 * Processo de envio de Inclusões ao SPC, só tera inicio ao
                 * processo caso não tenha um outro processo em andamento.
                 */
                try {
                    processamentoController = new ProcessamentoController();
                    processoDBSpc = processamentoController.consultarProcesso("SPC", "INCLUSAO");
                    processoDBExtracaoSpc = processamentoController.consultarProcesso("EXTRATOR", "EXTRACAO");
                    if (processoDBSpc == false && processoDBExtracaoSpc == false) {

                        /* Carrega os registros para envio. */
                        linclusaoSpcModel = new ArrayList();
                        linclusaoSpcModel = spcController.listarInclusaoSpc();

                        inclusaoSpcController = new ParcelasEnviarInclusaoSpcController();
                        for (int i = 0; i < lProcessamentoModel.size(); i++) {
                            ProcessamentoModel pSpc = lProcessamentoModel.get(i);
                            if (pSpc.getProvedor().equals("SPC") && pSpc.getTipo().equals("INCLUSAO")) {
                                lProcessamentoModel.remove(i);
                                break;
                            }
                        }
                        /* Adiciona na lista de Processamentos em Andamento */
                        processamentoModel = new ProcessamentoModel();
                        processamentoModel.setProvedor("SPC");
                        processamentoModel.setTipo("INCLUSAO");
                        processamentoModel.setItensEnviados(0);
                        processamentoModel.setItensSucesso(0);
                        processamentoModel.setItensErro(0);
                        processamentoModel.setItensTotal(0);
                        processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
                        processamentoModel.setItensTotal(linclusaoSpcModel != null ? linclusaoSpcModel.size() : 0);
                        processamentoModel.setDataInicio(Utilitarios.dataHoraAtual());
                        processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
                        lProcessamentoModel.add(processamentoModel);
                        gravarProcessoDB(processamentoModel);


                        /* Contadores do Processamento */
                        enviadosInclusaoSpc = 0;
                        enviadosInclusaoErroSpc = 0;
                        enviadosInclusaoSucessoSpc = 0;

                        for (ParcelasEnviarModel parcelasEnviarSpcModel : linclusaoSpcModel) {

                            try {
                                spcEnvioController = new SpcEnvioController();
                                processamentoInclusaoSpcModel = new ProcessamentoModel();
                                retornoIncSpc = spcEnvioController.enviarInclusaoSPC(parcelasEnviarSpcModel, "C");
                                enviadosInclusaoSpc++;
                                if (retornoIncSpc == 0) {
                                    enviadosInclusaoErroSpc++;
                                }
                                if (retornoIncSpc == 1) {
                                    enviadosInclusaoSucessoSpc++;
                                }
                                if (parcelasEnviarSpcModel.getAvalista() != "0") {
                                    processamentoInclusaoSpcModel = new ProcessamentoModel();
                                    spcEnvioController = new SpcEnvioController();
                                    spcEnvioController.enviarInclusaoSPC(parcelasEnviarSpcModel, "A");
                                }
                                processamento();

                            } catch (ErroSistemaException ex) {
                                ex.printStackTrace();
                                jpsExtrator.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
                            }

                        }
                        for (ProcessamentoModel processamentoModel : lProcessamentoModel) {
                            if ("SPC".equals(processamentoModel.getProvedor()) && "INCLUSAO".equals(processamentoModel.getTipo())) {
                                processamentoModel.setDataFim(Utilitarios.dataHoraAtual());
                                ProcessamentoModel processamentoModelSpc = new ProcessamentoModel();
                                processamentoModelSpc.setProvedor("SPC");
                                processamentoModelSpc.setTipo("INCLUSAO");
                                updateProcessoDBSpc(processamentoModelSpc);
                            }
                        }
                    } else {
                        if (processoDBExtracaoSpc == true) {
                            jpsExtrator.setStatus("Erro! Existe um processo de extração em andamento. Aguarde o término", JPStatus.ERRO);
                        } else {
                            jpsExtrator.setStatus("Erro já existe um processo de envio de inclusão Spc em andamento", JPStatus.ERRO);
                        }
                        carregarProcessoDB();
                        paControleEnvioRegistro.setSelectedComponent(jspProcessoDB);

                    }
                } catch (ErroSistemaException ex) {
                    jpsExtrator.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
                }
            }
        }.start();
    }

    private void baixaFacmat() {
        new Thread() {
            @Override
            public void run() {
                try {

                    processamentoController = new ProcessamentoController();
                    processoDBFacmat = processamentoController.consultarProcesso("FACMAT", "BAIXA");
                    processamentoController = new ProcessamentoController();
                    processoDBExtracaoFacmat = processamentoController.consultarProcesso("EXTRATOR", "EXTRACAO");

                    if (processoDBFacmat == false && processoDBExtracaoFacmat == false) {
                        tempInicioBaixaBvs = Utilitarios.dataHoraAtual();
                        tempFimBaixaBvs = "";
                        lbaixaBvsModel = bvsController.listarBaixaBvs();

                        for (int i = 0; i < lProcessamentoModel.size(); i++) {
                            ProcessamentoModel p = lProcessamentoModel.get(i);
                            if (p.getProvedor().equals("FACMAT") && p.getTipo().equals("BAIXA")) {
                                System.out.println("index" + i);
                                lProcessamentoModel.remove(i);
                                break;
                            }
                        }
                        /* Adiciona na lista de Processamentos em Andamento */
                        processamentoModel = new ProcessamentoModel();
                        processamentoModel.setProvedor("FACMAT");
                        processamentoModel.setTipo("BAIXA");
                        processamentoModel.setItensEnviados(0);
                        processamentoModel.setItensSucesso(0);
                        processamentoModel.setItensErro(0);
                        processamentoModel.setItensTotal(0);
                        processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
                        processamentoModel.setItensTotal(lbaixaBvsModel != null ? lbaixaBvsModel.size() : 0);
                        processamentoModel.setDataInicio(Utilitarios.dataHoraAtual());
                        processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
                        lProcessamentoModel.add(processamentoModel);
                        gravarProcessoDB(processamentoModel);

                        /* Contadores do Envio */
                        enviadosBaixaBvs = 0;
                        enviadosBaixaBvsErro = 0;
                        enviadosBaixaBvsSucesso = 0;

                        for (ParcelasEnviarModel parcelasEnviarBvsModel : lbaixaBvsModel) {
                            try {
                                bvsEnvioController = new BvsEnvioController();
                                processamentoBaixaBvsModel = new ProcessamentoModel();
                                retornoBxBvs = bvsEnvioController.enviarBaixaBVS(parcelasEnviarBvsModel);
                                valor = enviadosBaixaBvs++;
                                if (retornoBxBvs == 0) {
                                    valorEnviadosErro = enviadosBaixaBvsErro++;
                                }
                                if (retornoBxBvs == 1) {
                                    enviadosBaixaBvsSucesso++;
                                }
                                processamento();

                            } catch (ErroSistemaException ex) {
                                ex.printStackTrace();
                                jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                            }

                        }

                        for (ProcessamentoModel processamentoModel : lProcessamentoModel) {
                            if ("FACMAT".equals(processamentoModel.getProvedor()) && "BAIXA".equals(processamentoModel.getTipo())) {
                                processamentoModel.setDataFim(Utilitarios.dataHoraAtual());
                                processamentoModel = new ProcessamentoModel();
                                processamentoModel.setProvedor("FACMAT");
                                processamentoModel.setTipo("BAIXA");
                                updateProcessoDB();
                            }
                        }
                    } else {
                        if (processoDBExtracaoFacmat == true) {
                            jpsExtrator.setStatus("Erro! Existe um processo de extração em andamento. Aguarde o término", JPStatus.ERRO);
                        } else {
                            jpsExtrator.setStatus("Erro já existe um processo de envio de baixa Facmat em andamento", JPStatus.ERRO);
                        }
                        carregarProcessoDB();
                        paControleEnvioRegistro.setSelectedComponent(jspProcessoDB);
                    }

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }

            }
        }.start();
        paExtrator.setSelectedComponent(ppProcessoEnvio);

    }

    private void baixarSpc() {
        new Thread() {
            @Override
            public void run() {
                try {
                    processamentoController = new ProcessamentoController();
                    processoDBSpc = processamentoController.consultarProcesso("SPC", "BAIXA");
                    processamentoController = new ProcessamentoController();
                    processoDBExtracaoSpc = processamentoController.consultarProcesso("EXTRATOR", "EXTRACAO");
                    if (processoDBSpc == false && processoDBExtracaoSpc == false) {
                        lbaixaSpcModel = new ArrayList();
                        lbaixaSpcModel = spcController.listarBaixaSpc();

                        for (int i = 0; i < lProcessamentoModel.size(); i++) {
                            ProcessamentoModel pSpc = lProcessamentoModel.get(i);
                            if (pSpc.getProvedor().equals("SPC") && pSpc.getTipo().equals("BAIXA")) {
                                lProcessamentoModel.remove(i);
                                break;
                            }
                        }
                        /* Adiciona na lista de Processamentos em Andamento */
                        processamentoModel = new ProcessamentoModel();
                        processamentoModel.setProvedor("SPC");
                        processamentoModel.setTipo("BAIXA");
                        processamentoModel.setItensEnviados(0);
                        processamentoModel.setItensSucesso(0);
                        processamentoModel.setItensErro(0);
                        processamentoModel.setItensTotal(0);
                        processamentoModel.setDataFimEnvio(Utilitarios.getDataZero());
                        processamentoModel.setItensTotal(lbaixaSpcModel != null ? lbaixaSpcModel.size() : 0);
                        processamentoModel.setDataInicio(Utilitarios.dataHoraAtual());
                        processamentoModel.setUser(TelaPrincipal.usuario.getLogin());
                        lProcessamentoModel.add(processamentoModel);
                        gravarProcessoDB(processamentoModel);

                        /* Contadores do Envio */
                        enviadosBaixaErroSpc = 0;
                        enviadosBaixaSpc = 0;
                        enviadosBaixaSucessoSpc = 0;

                        for (ParcelasEnviarModel parcelasEnviarSpcModel : lbaixaSpcModel) {
                            try {
                                spcEnvioController = new SpcEnvioController();
                                processamentoBaixaSpcModel = new ProcessamentoModel();
                                retornoBxSpc = spcEnvioController.enviarBaixaSPC(parcelasEnviarSpcModel, "C");
                                enviadosBaixaSpc++;
                                if (retornoBxSpc == 0) {
                                    enviadosBaixaErroSpc++;
                                }
                                if (retornoBxSpc == 1) {
                                    enviadosBaixaSucessoSpc++;
                                }
                                if (parcelasEnviarSpcModel.getAvalista() != "0") {
                                    processamentoInclusaoSpcModel = new ProcessamentoModel();
                                    spcEnvioController = new SpcEnvioController();
                                    retornoBxSpc = spcEnvioController.enviarBaixaSPC(parcelasEnviarSpcModel, "A");
                                }
                                processamento();
                                // TimeUnit.SECONDS.sleep(1);
                            } catch (ErroSistemaException ex) {
                                ex.printStackTrace();
                                jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                            }
                        }

                        for (ProcessamentoModel processamentoModel : lProcessamentoModel) {
                            if ("SPC".equals(processamentoModel.getProvedor()) && "BAIXA".equals(processamentoModel.getTipo())) {
                                processamentoModel.setDataFim(Utilitarios.dataHoraAtual());
                                ProcessamentoModel processamentoModelSpc = new ProcessamentoModel();
                                processamentoModelSpc.setProvedor("SPC");
                                processamentoModelSpc.setTipo("BAIXA");
                                updateProcessoDBSpc(processamentoModelSpc);
                            }
                        }
                        /**
                         * Enviar Baixa Para Facmat. Se não existir um processo
                         * de envio de baixa em
                         */
                    } else {
                        if (processoDBExtracaoFacmat == true) {
                            jpsExtrator.setStatus("Erro! Existe um processo de extração em andamento. Aguarde o término", JPStatus.ERRO);
                        } else {
                            jpsExtrator.setStatus("Erro já existe um processo de envio de Baixa Spc em andamento", JPStatus.ERRO);
                        }

                        carregarProcessoDB();
                        paExtrator.setSelectedComponent(ppProcessoEnvio);
                        paControleEnvioRegistro.setSelectedComponent(jspProcessoDB);
                    }

                } catch (ErroSistemaException ex) {
                    jpsExtrator.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }
            }
        }.start();

    }

    private void limparFilro() {
        clsFiltroEmpresa.setSelectedIndex(0);
        ccccFiltroCPF.setaValorFiltro("");
        cscFiltroNome.setaValorFiltro("");
        cmFiltroContrato.setString("");
        csFiltroCodigoCliente.setString("");
        csFiltroIdExtracao.setString("");
        csFiltroIdParcela.setString("");
        clsFiltroPagamento.setString("");
        clsFiltroOrigem.setString("");
        clsFiltroStatus.setString("");
        cdFiltroDataEnvioInicio.setDate(null);
        cdFiltroDataEnvioFinal.setDate(null);
        cdFiltroDataExtracaoInicio.setDate(null);
        cdFiltroDataExtracaoFinal.setDate(null);
    }

    public void SinclonizaRetorno() {
        try {
            retornoEnvio.SinclonizaStatus();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            jpsExtrator.setStatus("Erro ao sinclonizar registros!.", JPStatus.ERRO, ex);
        }

    }

    public void copiarColar() {
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public void AtualizarCadastro() {

        try {
            int result;
            Object[] options = {"Cliente", "Avalista"};
            result = JOptionPane.showOptionDialog(null, "Escolha o Cadastro a ser Atualizado!", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                linha = tpInfomacoes.getSelectedRow();

                if (linha < 0) {
                    jpsExtrator.setStatus("Selecione um registro na Tabela, antes de clicar em Atualizar", JPStatus.ALERTA);
                } else {
                    idCliente = Integer.parseInt(tpInfomacoes.getValueAt(linha, 17).toString());
                    codFil = tpInfomacoes.getValueAt(linha, 2).toString().substring(2);
                    codCli = tpInfomacoes.getValueAt(linha, 3).toString();
                }
            }
            if (result == 1) {
                linha = tpAvalista.getSelectedRow();

                if (linha < 0) {
                    jpsExtrator.setStatus("Selecione um registro na Tabela, antes de clicar em Atualizar", JPStatus.ALERTA);
                } else {
                    idCliente = Integer.parseInt(tpAvalista.getValueAt(linha, 18).toString());
                    codFil = tpAvalista.getValueAt(linha, 2).toString();
                    codCli = tpAvalista.getValueAt(linha, 3).toString();
                }
            }
            pessoaController = new PessoaController();
            pessoaController.AtualizaPessoa(codCli, codFil, idCliente);
        } catch (ErroSistemaException ex) {
            jpsExtrator.setStatus("Erro ao Atualizar Registros!.", JPStatus.ERRO, ex);
        }
        jpsExtrator.setStatus("Registro Atualizado com Sucesso!.", JPStatus.NORMAL);
    }

    public boolean buscarRetornoProvedor() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");

        new Thread() {
            @Override
            public void run() {
                retornoProvedorControl = new RetornoProvedorControl();
                try {
                    RetornoProvedorModel retonoProvedor = new RetornoProvedorModel();
                    lRetornoProvedor = new ArrayList<>();
                    retonoProvedor.setTipo(clsFiltroRetProvedor.getString().toUpperCase());
                    retonoProvedor.setCodigo(csFiltroRetCodigo.getString());
                    retonoProvedor.setIdRetorno(ciFiltroRetId.getInteger());
                    lRetornoProvedor = retornoProvedorControl.buscaProcessoDB(retonoProvedor);

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsExtrator.setStatus("Erro ao carregar processos abertos " + ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }
                otmRetornoProvedor.setData(lRetornoProvedor);
                otmRetornoProvedor.fireTableDataChanged();
                if (tpRetornoProvedor.getRowCount() > 0) {
                    tpRetornoProvedor.packAll();
                    tpRetornoProvedor.addRowSelectionInterval(tpRetornoProvedor.convertRowIndexToView(0), tpRetornoProvedor.convertRowIndexToView(0));
                    tpRetornoProvedor.grabFocus();
                }
                jpsExtrator.setStatus("Busca realisada com Sucesso!.", JPStatus.NORMAL);
                SwingUtilities.invokeLater(() -> {
                    telaProcessamentoteste.dispose();
                });
            }
        }.start();

        telaProcessamentoteste.setVisible(true);
        telaProcessamentoteste.requestFocusInWindow();

        return false;
    }

    public void finalizarRegistro() {
        regFinalizar = new ExtracaoTableModel();

        linha = tpExtrator.getSelectedRow();
        parcelaController = new ParcelaController();

        if (linha < 0) {
            jpsExtrator.setStatus("Selecione um registro na Tabela, antes de clicar em Finalizar Registro!", JPStatus.ALERTA);
        } else {
            idExtracao = (tpExtrator.getValueAt(linha, 1).toString());
            try {
                regFinalizar = parcelaController.buscarRegistro(idExtracao);
                if (!(regFinalizar.getDescricaoRetorno().equalsIgnoreCase("N"))) {
                    if (telaFinalizaRegistro == null) {
                        rCodigoCliFr.setText(regFinalizar.getCodigoCliente());
                        rNomeCliFR.setText(regFinalizar.getNome());
                        rCpfClienteFR.setText(regFinalizar.getCpfCnpj());
                        rLojaDescFR.setText(regFinalizar.getIdFilial());
                        rContratoDescFR.setText(regFinalizar.getNumeroDoc());
                        rIdExtratoDescFR.setText(idExtracao);
                        paExtrator.setSelectedComponent(ppFinalizarRegistro);
                        paExtrator.setEnabledAt(4, true);

                    }

                } else {
                    String nomeCliente = (tpExtrator.getValueAt(linha, 1).toString());
                    jpsExtrator.setStatus("Nenhum Registro Encontrado com o Status de Erro para o Cliente:" + nomeCliente, JPStatus.ALERTA);
                }
            } catch (ErroSistemaException ex) {
                jpsExtrator.setStatus("Erro ao Selecionar Registro para finalizar" + ex.getLocalizedMessage(), JPStatus.ERRO, ex);

            }
        }

    }

    public void limparFinalizaRegistro() {
        rCodigoCliFr.setText("");
        rNomeCliFR.setText("");
        rCpfClienteFR.setText("");
        rLojaDescFR.setText("");
        rContratoDescFR.setText("");
        rIdExtratoDescFR.setText("");
        jtaObservacao.setText(null);
        paExtrator.setSelectedComponent(ppExtrator);
        paExtrator.setEnabledAt(4, false);

    }

    private Component getInstance() {
        return this;
    }

    private void finalizarProcesso() {
        UsuarioModel usuarioModel = TelaPrincipal.usuario;
        linha = tpProcessoDB.getSelectedRow();
        if (linha < 0) {
            jpsExtrator.setStatus("Selecione um registro na Tabela, antes de clicar em Finalizar Processo!", JPStatus.ALERTA);
        } else {
            String user = otmProcessoDB.getValue(tpProcessoDB.getLinhaSelecionada()).getUser();
            if (otmProcessoDB.getValue(tpProcessoDB.getLinhaSelecionada()).getDataFimEnvio().equals(Utilitarios.getDataZero())) {
                if (usuarioModel.getLogin().equals(user)) {
                    processamentoModel = new ProcessamentoModel();
                    processamentoModel = otmProcessoDB.getValue(tpProcessoDB.getLinhaSelecionada());
                    updateProcessoDB();
                    buscarProcessoDB();
                    jpsExtrator.setStatus("Processo Finalizado com Sucesso.", JPStatus.NORMAL);
                } else {
                    jpsExtrator.setStatus("Erro ao Finalizar Processo! Somente o Usuário: " + user + " podera finalizar o Processo.", JPStatus.ERRO);
                }
            } else {
                jpsExtrator.setStatus("Erro ao Finalizar Processo! Processo já se encontra finalizado em: " + otmProcessoDB.getValue(tpProcessoDB.getLinhaSelecionada()).getDataFimEnvio(), JPStatus.ALERTA);
            }
        }
    }

    public void evento() {
        tpEndereco.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                /*filtrar as teclas a seguir, pois essas teclas estão relacionadas a movimentos na tabela, se uma delas for acionada atualizar 
            o combobox, e a lista de Alunos, por exemplo
                 */
                //se for pressionado KeyEvent.VK_ENTER; KeyEvent.VK_LEFT; KeyEvent.VK_RIGHT; KeyEvent.VK_LEFT;  
                //atualize alguma coisa
                //se não
                tabela = "End_Cli";
                //fim se
            }

            @Override
            public void keyTyped(KeyEvent ke) {
                //algumas entradas devem ser consumidas, exemplo, em caso de notas, letras devem perecer
            }
        });
    }

}
