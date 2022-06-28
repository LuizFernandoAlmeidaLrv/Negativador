/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view;

import br.com.martinello.bd.matriz.control.ConsultaNegativacaoControl;
import br.com.martinello.bd.matriz.control.EnderecoController;
import br.com.martinello.bd.matriz.control.ExportarExcelControl;
import br.com.martinello.bd.matriz.control.ExtracaoTableController;
import br.com.martinello.bd.matriz.control.FilialController;
import br.com.martinello.bd.matriz.control.NotificacaoControl;
import br.com.martinello.bd.matriz.control.ParcelaController;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.control.ParcelasEnviarController;
import br.com.martinello.bd.matriz.control.PessoaController;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ConsultaNegativacaoModel;
import br.com.martinello.bd.matriz.model.domain.EnderecoModel;
import br.com.martinello.bd.matriz.model.domain.LogParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.bd.matriz.model.domain.PessoaModel;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.componentesbasicos.TabelaPadrao;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class TelaConsultaNegativacao extends TelaPadrao {

    public int idExtrator, linha;
    public ConsultaModel filtro = new ConsultaModel();
    public FilialModel filiaisModel = new FilialModel();
    public FilialModel filtroFilialModel = new FilialModel();
    public PessoaModel pessoaModel = new PessoaModel();
    public List<PessoaModel> lpessoaModel = new ArrayList<>();
    public List<PessoaModel> lpessoaAvalModel = new ArrayList<>();
    public PessoaController pessoaController = new PessoaController();
    public List<ConsultaNegativacaoModel> lConsultaNeg;
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
    public ConsultaNegativacaoControl consultaNegativacaoControl;
    public static List<FilialModel> lfiliaisModel = new ArrayList();
    public ExtracaoTableController extracaoTableController = new ExtracaoTableController();
    private ExtracaoTableModel filtroConsultaExtracao = new ExtracaoTableModel();
    private List<ExtracaoTableModel> lextracaoTableModel = new ArrayList();
    public static int quantidade, contFiltro;
    private TelaPrincipal telaPrincipal;
    public String codCli, codFil, idExtracao;
    public int idCliente, qtdRegistros, qtd, qtdCliente, totCli;
    public BigDecimal media, mediaTotal;
    public double valorTotal;
    private boolean retornoPesquisar;

    //private String provedor, tipoRegistro;
    private String valorCopiado;

    private final ObjectTableModel<ExtracaoTableModel> otmExtracao = new ObjectTableModel<>(ExtracaoTableModel.class, "indice,idExtracao,idFilial,"
            + "idCliente,nome,tipoPessoa,cpfCnpj,codigoCliente,numeroDoc,tipoAcao,status,pago,dataLancamento,dataVencimento,valor,numeroParcela,idParcela,dataPagamento,statusProvedor,situacao,"
            + "dataSpcInclusao,dataSpcAvalistaInclusao,dataFacmatInclusao,dataSpcBaixa,dataSpcAvalistaBaixa,dataFacmatBaixa,statusSpc,statusSpcAval,statusFacmat,"
            + "dataNegativada,dataBaixa,dataAlteracao,dataExtracao,dataUltimaAtualizacao,incluirAval,codAvalista,nomeAvalista,origemRegistro,dataRetorno,cidade,cep");

    private final ObjectTableModel<ParcelaModel> otmParcela = new ObjectTableModel<>(ParcelaModel.class, "idParcela,codCliente,numeroDoContrato,numeroParcela,dataExtracao,dataLancamento,dataVencimento,"
            + "valorParcela,dataPagamento,tipoPagamento,capitalPago,taxaDeJuros,valorJuros,valorCalc,valorPago,jurosPago,situacaoParcela,dataNegativacao,dataBaixaNegativacao,idRegistroBvs,motivoBaixaBvs,motivoBaixaSpc,"
            + "naturezaBvs,naturezaSpc,dataEnvioFacmat,dataEnvioSpc,dataEnvioAvalistaSpc,dataBaixaFacmat,dataBaixaSpc,dataBaixaAvalistaSpc,dataAlteracao,dataAtualizacao");

    private final ObjectTableModel<PessoaModel> otmInformacoePessoal = new ObjectTableModel<>(PessoaModel.class, "indice,idExtrator,pontoFilial,codigo,nomeRazaoSocial,situacao,nomeDoPai,nomeDaMae,"
            + "numeroRG,orgaoEmissorRG,dataEmissaoRG,email,estadoCivil,dataNascimento,tipoPessoa,cpf,numeroTel,idPessoa,idAvalista");

    private final ObjectTableModel<EnderecoModel> otmEndereco = new ObjectTableModel<>(EnderecoModel.class, "endLogradouro,endIdLogradouro,endNumero,endComplemento,endBairro,cidade,ufEstado,codigoIbge,cep");

    private final ObjectTableModel<PessoaModel> otmAvalista = new ObjectTableModel<>(PessoaModel.class, "indice,idExtrator,pontoFilial,codigo,nomeRazaoSocial,situacao,nomeDoPai,nomeDaMae,"
            + "numeroRG,orgaoEmissorRG,dataEmissaoRG,email,estadoCivil,dataNascimento,tipoPessoa,cpf,numeroTel,idPessoa,idAvalista");

    private final ObjectTableModel<EnderecoModel> otmEnderecoAvalista = new ObjectTableModel<>(EnderecoModel.class, "endLogradouro,endIdLogradouro,endNumero,endComplemento,endBairro,cidade,ufEstado,codigoIbge,cep");

    private final ObjectTableModel<ConsultaNegativacaoModel> otmNegativacao = new ObjectTableModel<>(ConsultaNegativacaoModel.class, "id_Filial,filial,quantidade,qtdCliente,mediaClienteParcela,valor");

    private final ObjectTableModel<LogParcelaModel> otmLogParcela = new ObjectTableModel<>(LogParcelaModel.class, "indice,idLogExtrator,idExtrator,idParcela,provedor,dataLog,observacao,status,dataRetorno,descricaoLog");

    /**
     * Creates new form Extracao
     */
    public TelaConsultaNegativacao() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();

        carregarFiliais();

        //    SinclonizaRetorno();
        cdFiltroDataExtracaoInicio.setDate(new Date());
        cdFiltroDataExtracaoFinal.setDate(new Date());
        cdFiltroDataExtracaoInicioSint.setDate(new Date());
        cdFiltroDataExtracaoFinalSint.setDate(new Date());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpsConsultaNegativacao = new br.com.martinello.componentesbasicos.paineis.JPStatus();
        paExtrator = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppConsultaSintetica = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroAnalitico = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rEmpresa1 = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroEmpresaSint = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroPagamentoSint = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroPagamentoSint = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cdFiltroDataExtracaoFinalSint = new br.com.martinello.componentesbasicos.CampoDataHora();
        cdFiltroDataVencInicioSint = new br.com.martinello.componentesbasicos.CampoDataHora();
        rFiltroDataVencSint = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDataVencFinalSint = new br.com.martinello.componentesbasicos.CampoDataHora();
        rFiltroDataExtracaoSint = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDataExtracaoInicioSint = new br.com.martinello.componentesbasicos.CampoDataHora();
        bPesquisarSint = new br.com.martinello.componentesbasicos.Botao();
        bLimparSint = new br.com.martinello.componentesbasicos.Botao();
        bGerarExcelSint = new br.com.martinello.componentesbasicos.Botao();
        ppTabela = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpConsultaSint = new br.com.martinello.componentesbasicos.TabelaPadrao();
        painelPadrao2 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rValorTotalSint = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotalRSint = new br.com.martinello.componentesbasicos.Rotulo();
        rMediaSint = new br.com.martinello.componentesbasicos.Rotulo();
        rMediaRSint = new br.com.martinello.componentesbasicos.Rotulo();
        rTotCliRSint = new br.com.martinello.componentesbasicos.Rotulo();
        rTotCliSint = new br.com.martinello.componentesbasicos.Rotulo();
        rTotNegRSint = new br.com.martinello.componentesbasicos.Rotulo();
        rTotNegSint = new br.com.martinello.componentesbasicos.Rotulo();
        ppExtrator = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroExtrator = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rEmpresa = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroEmpresa = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroPagamento = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroPagamento = new br.com.martinello.componentesbasicos.CampoListaSimples();
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
        bGerarExcel = new br.com.martinello.componentesbasicos.Botao();
        ppTabelas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        paTabelas = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppConsultaNegTb = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jspConsultaNeg = new javax.swing.JScrollPane();
        tpConsultaNeg = new br.com.martinello.componentesbasicos.TabelaPadrao();
        ppBotoes = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rNomeQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotalR = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotal = new br.com.martinello.componentesbasicos.Rotulo();
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta Negativações");
        setPreferredSize(new java.awt.Dimension(1241, 706));
        getContentPane().add(jpsConsultaNegativacao, java.awt.BorderLayout.PAGE_END);

        paExtrator.setPreferredSize(new java.awt.Dimension(1020, 633));
        paExtrator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paExtratorMouseClicked(evt);
            }
        });

        ppConsultaSintetica.setLayout(new java.awt.BorderLayout());

        ppFiltroAnalitico.setPreferredSize(new java.awt.Dimension(1088, 110));

        rEmpresa1.setText("Empresa:");

        rFiltroPagamentoSint.setText("Pagamento:");

        clsFiltroPagamentoSint.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não pago", "Pago", " " }));

        cdFiltroDataVencInicioSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDataVencInicioSintActionPerformed(evt);
            }
        });

        rFiltroDataVencSint.setText("Data de Vencimento:");

        rFiltroDataExtracaoSint.setText("Data de Extração:");

        cdFiltroDataExtracaoInicioSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDataExtracaoInicioSintActionPerformed(evt);
            }
        });

        bPesquisarSint.setText("Pesquisar");
        bPesquisarSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPesquisarSintActionPerformed(evt);
            }
        });

        bLimparSint.setText("Cancelar");
        bLimparSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimparSintActionPerformed(evt);
            }
        });

        bGerarExcelSint.setText("Gerar Excel");
        bGerarExcelSint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarExcelSintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroAnaliticoLayout = new javax.swing.GroupLayout(ppFiltroAnalitico);
        ppFiltroAnalitico.setLayout(ppFiltroAnaliticoLayout);
        ppFiltroAnaliticoLayout.setHorizontalGroup(
            ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rFiltroDataVencSint, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(rFiltroDataExtracaoSint, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(rEmpresa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                        .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cdFiltroDataExtracaoInicioSint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroDataVencInicioSint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cdFiltroDataExtracaoFinalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroDataVencFinalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(clsFiltroEmpresaSint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(rFiltroPagamentoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroAnaliticoLayout.createSequentialGroup()
                        .addComponent(clsFiltroPagamentoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
                        .addComponent(bGerarExcelSint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bLimparSint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisarSint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ppFiltroAnaliticoLayout.setVerticalGroup(
            ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                        .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rEmpresa1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroEmpresaSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(cdFiltroDataVencInicioSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroDataVencSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(rFiltroDataExtracaoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDataExtracaoInicioSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroAnaliticoLayout.createSequentialGroup()
                                .addComponent(cdFiltroDataVencFinalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cdFiltroDataExtracaoFinalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ppFiltroAnaliticoLayout.createSequentialGroup()
                        .addGroup(ppFiltroAnaliticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bGerarExcelSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroPagamentoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroPagamentoSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bLimparSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bPesquisarSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        ppConsultaSintetica.add(ppFiltroAnalitico, java.awt.BorderLayout.NORTH);

        ppTabela.setLayout(new java.awt.BorderLayout());

        tpConsultaSint.setModel(otmNegativacao);
        jScrollPane1.setViewportView(tpConsultaSint);

        ppTabela.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        painelPadrao2.setPreferredSize(new java.awt.Dimension(1236, 35));

        rValorTotalSint.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rValorTotalSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotalRSint.setText("Valor Total:");
        rValorTotalRSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rMediaSint.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rMediaSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rMediaRSint.setText("Média Total");
        rMediaRSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rTotCliRSint.setText("Total Clientes:");
        rTotCliRSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rTotCliSint.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rTotCliSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rTotNegRSint.setText("Total Negativação:");
        rTotNegRSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rTotNegSint.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rTotNegSint.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        javax.swing.GroupLayout painelPadrao2Layout = new javax.swing.GroupLayout(painelPadrao2);
        painelPadrao2.setLayout(painelPadrao2Layout);
        painelPadrao2Layout.setHorizontalGroup(
            painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPadrao2Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(rTotNegRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rTotNegSint, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rTotCliRSint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rTotCliSint, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rMediaRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rMediaSint, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rValorTotalRSint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rValorTotalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        painelPadrao2Layout.setVerticalGroup(
            painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rTotNegRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rTotNegSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rTotCliRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rTotCliSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rValorTotalSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelPadrao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rMediaRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rMediaSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rValorTotalRSint, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        ppTabela.add(painelPadrao2, java.awt.BorderLayout.PAGE_END);

        ppConsultaSintetica.add(ppTabela, java.awt.BorderLayout.CENTER);

        paExtrator.addTab("Sintética", ppConsultaSintetica);

        ppExtrator.setLayout(new java.awt.BorderLayout());

        ppFiltroExtrator.setPreferredSize(new java.awt.Dimension(1088, 210));

        rEmpresa.setText("Empresa:");

        rFiltroPagamento.setText("Pagamento:");

        clsFiltroPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não pago", "Pago", " " }));

        rFiltroCodigoCliente.setText("Código Cliente:");

        rFiltroContrato.setText("Contrato:");

        cmFiltroContrato.setMascara("**********");

        rFiltroCpfCnpj.setText("CPF/CNPJ:");

        cdFiltroDataEnvioInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDataEnvioInicioActionPerformed(evt);
            }
        });

        rFiltroDataEnvio.setText("Data de Vencimento:");

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

        bLimpar.setText("Cancelar");
        bLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLimparActionPerformed(evt);
            }
        });

        rFiltroIdEtracao.setText("ID Extração:");

        rFiltroIdParcela.setText("ID Parcela:");

        rNome.setText("Nome:");

        bGerarExcel.setText("Gerar Excel");
        bGerarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroExtratorLayout = new javax.swing.GroupLayout(ppFiltroExtrator);
        ppFiltroExtrator.setLayout(ppFiltroExtratorLayout);
        ppFiltroExtratorLayout.setHorizontalGroup(
            ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(rNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clsFiltroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                        .addComponent(rFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(clsFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rFiltroDataEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rFiltroDataExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cdFiltroDataExtracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cdFiltroDataEnvioInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cdFiltroDataExtracaoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cdFiltroDataEnvioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(csFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addComponent(rFiltroCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ccccFiltroCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addComponent(rFiltroIdEtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csFiltroIdExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bLimpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bGerarExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ppFiltroExtratorLayout.setVerticalGroup(
            ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ppFiltroExtratorLayout.createSequentialGroup()
                            .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(cdFiltroDataEnvioInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rFiltroDataEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(rFiltroDataExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroDataExtracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroExtratorLayout.createSequentialGroup()
                            .addComponent(cdFiltroDataEnvioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cdFiltroDataExtracaoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmFiltroContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(csFiltroIdExtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroIdEtracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroIdParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroExtratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroCpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ccccFiltroCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        ppExtrator.add(ppFiltroExtrator, java.awt.BorderLayout.NORTH);

        ppTabelas.setPreferredSize(new java.awt.Dimension(1024, 620));
        ppTabelas.setLayout(new java.awt.BorderLayout());

        paTabelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                paTabelasFocusLost(evt);
            }
        });

        ppConsultaNegTb.setLayout(new java.awt.BorderLayout());

        tpConsultaNeg.setModel(otmExtracao);
        tpConsultaNeg.setPreferredScrollableViewportSize(new java.awt.Dimension(0, 0));
        tpConsultaNeg.setShowGrid(true);
        tpConsultaNeg.setVisibleRowCount(25);
        tpConsultaNeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpConsultaNegFocusLost(evt);
            }
        });
        tpConsultaNeg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpConsultaNegMouseClicked(evt);
            }
        });
        tpConsultaNeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpConsultaNegKeyPressed(evt);
            }
        });
        tpConsultaNeg.setAutoResizeMode(TabelaPadrao.AUTO_RESIZE_OFF);
        jspConsultaNeg.setViewportView(tpConsultaNeg);

        ppConsultaNegTb.add(jspConsultaNeg, java.awt.BorderLayout.CENTER);
        jspConsultaNeg.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        ppBotoes.setMaximumSize(new java.awt.Dimension(32767, 150));
        ppBotoes.setPreferredSize(new java.awt.Dimension(1020, 40));

        rNomeQuantidade.setText("Quant:");
        rNomeQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rQuantidade.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotalR.setText("Valor:");
        rValorTotalR.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rValorTotal.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

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
                .addComponent(rValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addGap(452, 452, 452))
        );
        ppBotoesLayout.setVerticalGroup(
            ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rNomeQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rValorTotalR, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        ppConsultaNegTb.add(ppBotoes, java.awt.BorderLayout.PAGE_END);

        paTabelas.addTab("Dados Gerais", ppConsultaNegTb);

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

        paExtrator.addTab("Analítica", ppExtrator);

        getContentPane().add(paExtrator, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paExtratorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paExtratorMouseClicked
        String s;
    }//GEN-LAST:event_paExtratorMouseClicked

    private void paTabelasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paTabelasFocusLost
        copiarColar();
    }//GEN-LAST:event_paTabelasFocusLost

    private void jspLogRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jspLogRegistroFocusLost
        copiarColar();
    }//GEN-LAST:event_jspLogRegistroFocusLost

    private void tpLoRegistroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpLoRegistroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmLogParcela.getValueAt(tpLoRegistro.getSelectedRow(), tpLoRegistro.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpLoRegistroKeyPressed

    private void tpLoRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpLoRegistroFocusLost
        copiarColar();
    }//GEN-LAST:event_tpLoRegistroFocusLost

    private void tpEnderecoAvalistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpEnderecoAvalistaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmEnderecoAvalista.getValueAt(tpEnderecoAvalista.getSelectedRow(), tpEnderecoAvalista.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpEnderecoAvalistaKeyPressed

    private void tpAvalistaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpAvalistaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmAvalista.getValueAt(tpAvalista.getSelectedRow(), tpAvalista.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpAvalistaKeyPressed

    private void tpEnderecoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpEnderecoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmEndereco.getValueAt(tpEndereco.getSelectedRow(), tpEndereco.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpEnderecoKeyPressed

    private void tpEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpEnderecoFocusLost
        copiarColar();
    }//GEN-LAST:event_tpEnderecoFocusLost

    private void tpInfomacoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpInfomacoesKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmInformacoePessoal.getValueAt(tpInfomacoes.getSelectedRow(), tpInfomacoes.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpInfomacoesKeyPressed

    private void tpInfomacoesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpInfomacoesFocusLost
        copiarColar();
    }//GEN-LAST:event_tpInfomacoesFocusLost

    private void tpParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpParcelaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            valorCopiado = otmParcela.getValueAt(tpParcela.getSelectedRow(), tpParcela.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpParcelaKeyPressed

    private void tpParcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpParcelaFocusLost
        copiarColar();
    }//GEN-LAST:event_tpParcelaFocusLost

    private void tpConsultaNegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpConsultaNegKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_C) {
            ////if (evt.isControlDown()) {

            valorCopiado = otmExtracao.getValueAt(tpConsultaNeg.getSelectedRow(), tpConsultaNeg.getSelectedColumn()).toString();
        }
    }//GEN-LAST:event_tpConsultaNegKeyPressed

    private void tpConsultaNegMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpConsultaNegMouseClicked

        if (evt.getClickCount() == 2 && tpConsultaNeg.getSelectedColumn() != 1) {

            final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");
            pessoaModel = new PessoaModel();
            enderecoModel = new EnderecoModel();
            new Thread() {
                @Override
                public void run() {
                    try {
                        linha = tpConsultaNeg.getSelectedRow();

                        if (linha < 0) {
                            jpsConsultaNegativacao.setStatus("Selecione uma Filial na Tabela, antes de clicar em alterar", JPStatus.ALERTA);
                        } else {
                            pessoaModel.setIdExtrator(tpConsultaNeg.getValueAt(linha, 1).toString());
                            parcelaModel.setIdParcela(tpConsultaNeg.getValueAt(linha, 16).toString());
                            // parcelaModel.setIdParcela(tpExtrator.getValueAt(linha, 16).toString());
                            lpessoaModel = pessoaController.extrairInfoPessoa(pessoaModel);
                            lparcelaModel = parcelaController.extrairParcela(parcelaModel);
                            lenderecoModel = enderecoController.extrairEndereco(parcelaModel, enderecoModel);
                            lLogParcela = parcelaController.extrairLogParcela(parcelaModel);

                            if (tpConsultaNeg.getValueAt(linha, 31) != null) {
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

                        jpsConsultaNegativacao.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

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
                        jpsConsultaNegativacao.setStatus("Erro ao pesquisar cliente.", JPStatus.ERRO, ex);
                        System.out.println(ex);
                        SwingUtilities.invokeLater(() -> {
                            telaProcessamentoteste.dispose();
                        });
                    } catch (SQLException ex) {
                        jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                    } catch (ErroSistemaException ex) {
                        jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                    }

                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });
                }
            }.start();

            telaProcessamentoteste.setVisible(true);
            telaProcessamentoteste.requestFocusInWindow();

        }
        if (evt.getClickCount() == 2 && tpConsultaNeg.getSelectedColumn() == 0) {

        }
    }//GEN-LAST:event_tpConsultaNegMouseClicked

    private void tpConsultaNegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpConsultaNegFocusLost
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpConsultaNegFocusLost

    private void bGerarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarExcelActionPerformed
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Exportando Excel...");
        ExportarExcelControl exportarExcelControl = new ExportarExcelControl();

        new Thread() {
            @Override
            public void run() {
                try {
                    exportarExcelControl.expNegativacoesAnalitico(otmExtracao.getData(), TelaPrincipal.diretorio);
                    jpsConsultaNegativacao.setStatus("Arquivo gerado com sucesso.", jpsConsultaNegativacao.NORMAL);

                } catch (ErroSistemaException ex) {
                    jpsConsultaNegativacao.setStatus("Erro", jpsConsultaNegativacao.ERRO, ex);
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

    private void bLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimparActionPerformed
        limparFilro();
    }//GEN-LAST:event_bLimparActionPerformed

    private void bPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarActionPerformed
        boolean buscarExtracoes = buscarExtracoes();
        paTabelas.setSelectedComponent(ppConsultaNegTb);

        System.out.println("Hora" + Utilitarios.dataHoraAtual());
    }//GEN-LAST:event_bPesquisarActionPerformed

    private void cdFiltroDataExtracaoInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataExtracaoInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataExtracaoInicioActionPerformed

    private void cdFiltroDataEnvioInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataEnvioInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataEnvioInicioActionPerformed

    private void cdFiltroDataVencInicioSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataVencInicioSintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataVencInicioSintActionPerformed

    private void cdFiltroDataExtracaoInicioSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDataExtracaoInicioSintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDataExtracaoInicioSintActionPerformed

    private void bPesquisarSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarSintActionPerformed
        consultaNegativacaoSintetica();
    }//GEN-LAST:event_bPesquisarSintActionPerformed

    private void bLimparSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLimparSintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bLimparSintActionPerformed

    private void bGerarExcelSintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarExcelSintActionPerformed
        gerarExcelSint();
    }//GEN-LAST:event_bGerarExcelSintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Botao bGerarExcel;
    private br.com.martinello.componentesbasicos.Botao bGerarExcelSint;
    private br.com.martinello.componentesbasicos.Botao bLimpar;
    private br.com.martinello.componentesbasicos.Botao bLimparSint;
    private br.com.martinello.componentesbasicos.Botao bPesquisar;
    private br.com.martinello.componentesbasicos.Botao bPesquisarSint;
    private br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta ccccFiltroCPF;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataEnvioFinal;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataEnvioInicio;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoFinal;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoFinalSint;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoInicio;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataExtracaoInicioSint;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataVencFinalSint;
    private br.com.martinello.componentesbasicos.CampoDataHora cdFiltroDataVencInicioSint;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroEmpresa;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroEmpresaSint;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroPagamento;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroPagamentoSint;
    private br.com.martinello.componentesbasicos.CampoMascara cmFiltroContrato;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.CampoString csFiltroIdExtracao;
    private br.com.martinello.componentesbasicos.CampoString csFiltroIdParcela;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroNome;
    private javax.swing.JScrollPane jScrollPane1;
    private br.com.martinello.componentesbasicos.paineis.JPStatus jpsConsultaNegativacao;
    private javax.swing.JScrollPane jspAvalista;
    private javax.swing.JScrollPane jspCliente;
    private javax.swing.JScrollPane jspConsultaNeg;
    private javax.swing.JScrollPane jspEndecoCliente;
    private javax.swing.JScrollPane jspEnderecoAvalista;
    private javax.swing.JScrollPane jspLogRegistro;
    private javax.swing.JScrollPane jspParcela;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paTabelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao2;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppBotoes;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppConsultaNegTb;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppConsultaSintetica;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroAnalitico;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroExtrator;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppTabela;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppTabelas;
    private br.com.martinello.componentesbasicos.Rotulo rEmpresa;
    private br.com.martinello.componentesbasicos.Rotulo rEmpresa1;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroContrato;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCpfCnpj;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataEnvio;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataExtracao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataExtracaoSint;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataVencSint;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdEtracao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdParcela;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroPagamento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroPagamentoSint;
    private br.com.martinello.componentesbasicos.Rotulo rMediaRSint;
    private br.com.martinello.componentesbasicos.Rotulo rMediaSint;
    private br.com.martinello.componentesbasicos.Rotulo rNome;
    private br.com.martinello.componentesbasicos.Rotulo rNomeQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rTotCliRSint;
    private br.com.martinello.componentesbasicos.Rotulo rTotCliSint;
    private br.com.martinello.componentesbasicos.Rotulo rTotNegRSint;
    private br.com.martinello.componentesbasicos.Rotulo rTotNegSint;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotal;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalR;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalRSint;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalSint;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpAvalista;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaNeg;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaSint;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpEndereco;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpEnderecoAvalista;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpInfomacoes;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpLoRegistro;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpParcela;
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
                jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);

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
 /* Data de Envio */
        if (cdFiltroDataEnvioInicio.getDate() != null) {
            if (cdFiltroDataEnvioFinal.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioInicio(Utilitarios.converteDataString(cdFiltroDataEnvioInicio.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsConsultaNegativacao.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        if (cdFiltroDataEnvioFinal.getDate() != null) {
            if (cdFiltroDataEnvioInicio.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioFim(Utilitarios.converteDataString(cdFiltroDataEnvioFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jpsConsultaNegativacao.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        /* Data Extração */
        if (cdFiltroDataExtracaoInicio.getDate() != null) {
            if (cdFiltroDataExtracaoFinal.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoInicio(Utilitarios.converteDataString(cdFiltroDataExtracaoInicio.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsConsultaNegativacao.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        if (cdFiltroDataExtracaoFinal.getDate() != null) {
            if (cdFiltroDataExtracaoInicio.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoFim(Utilitarios.converteDataString(cdFiltroDataExtracaoFinal.getDate(), "dd/MM/yyyy"));
            } else {
                jpsConsultaNegativacao.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        new Thread() {
            @Override
            public void run() {
                try {
                    if (contFiltro >= 1) {
                        consultaNegativacaoControl = new ConsultaNegativacaoControl();
                        lextracaoTableModel = consultaNegativacaoControl.consultaNegativacaoControl(filtroConsultaExtracao);
                        otmExtracao.setData(lextracaoTableModel);
                        otmExtracao.fireTableDataChanged();
                        quantidade = (lextracaoTableModel.size());
                        rQuantidade.setText("" + quantidade);

                        tpConsultaNeg.setForeground(Color.BLACK);

                        jpsConsultaNegativacao.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

                    } else {
                        jpsConsultaNegativacao.setStatus("Erro ao pesquisar cliente é necessário informar ao menos 1 campo ", JPStatus.ERRO, 20);
                    }
                } catch (NullPointerException ex) {
                    jpsConsultaNegativacao.setStatus("Erro ao pesquisar cliente.", JPStatus.ERRO, ex);
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }

                SwingUtilities.invokeLater(() -> {
                    if (tpConsultaNeg.getRowCount() > 0) {
                        tpConsultaNeg.packAll();
                        tpConsultaNeg.addRowSelectionInterval(tpConsultaNeg.convertRowIndexToView(0), tpConsultaNeg.convertRowIndexToView(0));
                        tpConsultaNeg.grabFocus();
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
            for (int i = 0; i < tpConsultaNeg.getRowCount(); i++) {
                Double valorAux;
                valorAux = (Double) tpConsultaNeg.getValueAt(i, 14);
                valorTotal += valorAux.doubleValue();
            }

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            rValorTotal.setText(nf.format(valorTotal));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular Total Produtos: " + e.getMessage());
        }
    }

    private void calculaTotalSintetico() {
        try {
            valorTotal = 0;
            qtdRegistros = 0;
            totCli = 0;
            double tot = 0;
            for (int i = 0; i < tpConsultaSint.getRowCount(); i++) {
                Double valorAux;
                valorAux = (Double) tpConsultaSint.getValueAt(i, 5);
                qtd = (int) tpConsultaSint.getValueAt(i, 2);
                totCli = (int) tpConsultaSint.getValueAt(i, 3);
                media = (BigDecimal) tpConsultaSint.getValueAt(i, 4);
                valorTotal += valorAux.doubleValue();
                qtdRegistros += qtd;
                qtdCliente += totCli;
            }

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            rValorTotalSint.setText(nf.format(valorTotal));
            rTotNegSint.setText("" + qtdRegistros);
            rTotCliSint.setText("" + qtdCliente);
            if (qtdRegistros > 0) {
                tot = ((double) qtdRegistros / qtdCliente);
                mediaTotal = new BigDecimal(tot);
                mediaTotal = mediaTotal.setScale(2, RoundingMode.HALF_UP);
            }
            rMediaSint.setText("" + mediaTotal);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular Total Produtos: " + e.getMessage());
        }
    }

    private void carregarFiliais() {
        filtroFilialModel = new FilialModel();
        try {
            lfiliaisModel = filiaisControler.listarFilialController();
        } catch (ErroSistemaException ex) {
            jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }

        for (FilialModel filiais : lfiliaisModel) {
            clsFiltroEmpresa.addItem(filiais);
            clsFiltroEmpresaSint.addItem(filiais);
        }
    }

    private void carregarMotivos() {
        filtroFilialModel = new FilialModel();
        try {
            lfiliaisModel = filiaisControler.listarFilialController();
        } catch (ErroSistemaException ex) {
            jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }

        for (FilialModel filiais : lfiliaisModel) {
            clsFiltroEmpresa.addItem(filiais);
        }
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
        cdFiltroDataEnvioInicio.setDate(null);
        cdFiltroDataEnvioFinal.setDate(null);
        cdFiltroDataExtracaoInicio.setDate(null);
        cdFiltroDataExtracaoFinal.setDate(null);
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
                    jpsConsultaNegativacao.setStatus("Selecione um registro na Tabela, antes de clicar em Atualizar", JPStatus.ALERTA);
                } else {
                    idCliente = Integer.parseInt(tpInfomacoes.getValueAt(linha, 17).toString());
                    codFil = tpInfomacoes.getValueAt(linha, 2).toString().substring(2);
                    codCli = tpInfomacoes.getValueAt(linha, 3).toString();
                }
            }
            if (result == 1) {
                linha = tpAvalista.getSelectedRow();

                if (linha < 0) {
                    jpsConsultaNegativacao.setStatus("Selecione um registro na Tabela, antes de clicar em Atualizar", JPStatus.ALERTA);
                } else {
                    idCliente = Integer.parseInt(tpAvalista.getValueAt(linha, 18).toString());
                    codFil = tpAvalista.getValueAt(linha, 2).toString();
                    codCli = tpAvalista.getValueAt(linha, 3).toString();
                }
            }
            pessoaController = new PessoaController();
            pessoaController.AtualizaPessoa(codCli, codFil, idCliente);
        } catch (ErroSistemaException ex) {
            jpsConsultaNegativacao.setStatus("Erro ao Atualizar Registros!.", JPStatus.ERRO, ex);
        }
        jpsConsultaNegativacao.setStatus("Registro Atualizado com Sucesso!.", JPStatus.NORMAL);
    }

    private Component getInstance() {
        return this;
    }

    private boolean consultaNegativacaoSintetica() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Realizando consulta...");
        filtroConsultaExtracao = new ExtracaoTableModel();
        contFiltro = 0;

        /* Empresa */
        if (!clsFiltroEmpresaSint.getSelectedItem().toString().equals("TODAS")) {

            FilialModel filialModel = new FilialModel();
            String filial = clsFiltroEmpresaSint.getSelectedItem().toString();
            try {
                filialModel = filiaisControler.listarFilialExtracao(filial);
                filtroConsultaExtracao.setIdFilial(filialModel.getPontoFilial());

            } catch (ErroSistemaException ex) {
                jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);

            }

        }

        /* Tipo de Pagamento (Pago / Não Pago) da Parcela */
        if (clsFiltroPagamentoSint.getSelectedItem().toString().equals("Pago")) {
            filtroConsultaExtracao.setPago("S");
            contFiltro++;
        } else if (clsFiltroPagamentoSint.getSelectedItem().toString().equals("Não pago")) {
            filtroConsultaExtracao.setPago("N");
            contFiltro++;
        }

        /* Data de Envio */
        if (cdFiltroDataVencInicioSint.getDate() != null) {
            if (cdFiltroDataVencFinalSint.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioInicio(Utilitarios.converteDataString(cdFiltroDataVencInicioSint.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsConsultaNegativacao.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        if (cdFiltroDataVencFinalSint.getDate() != null) {
            if (cdFiltroDataVencInicioSint.getDate() != null) {
                filtroConsultaExtracao.setDataEnvioFim(Utilitarios.converteDataString(cdFiltroDataVencFinalSint.getDate(), "dd/MM/yyyy"));
            } else {
                jpsConsultaNegativacao.setStatus("O campo data envio precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        /* Data Extração */
        if (cdFiltroDataExtracaoInicioSint.getDate() != null) {
            if (cdFiltroDataExtracaoFinalSint.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoInicio(Utilitarios.converteDataString(cdFiltroDataExtracaoInicioSint.getDate(), "dd/MM/yyyy"));
                contFiltro++;
            } else {
                jpsConsultaNegativacao.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        if (cdFiltroDataExtracaoFinalSint.getDate() != null) {
            if (cdFiltroDataExtracaoInicioSint.getDate() != null) {
                filtroConsultaExtracao.setDataExtracaoFim(Utilitarios.converteDataString(cdFiltroDataExtracaoFinalSint.getDate(), "dd/MM/yyyy"));
            } else {
                jpsConsultaNegativacao.setStatus("O campo data extracão precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoPesquisar = false;
            }

        }

        new Thread() {
            @Override
            public void run() {
                try {
                    if (contFiltro >= 1) {
                        lConsultaNeg = new LinkedList<>();
                        consultaNegativacaoControl = new ConsultaNegativacaoControl();
                        lConsultaNeg = consultaNegativacaoControl.listarNegativacaoSintetica(filtroConsultaExtracao);
                        otmNegativacao.setData(lConsultaNeg);
                        otmNegativacao.fireTableDataChanged();

                        tpConsultaSint.setForeground(Color.BLACK);

                        jpsConsultaNegativacao.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

                    } else {
                        jpsConsultaNegativacao.setStatus("Erro ao pesquisar é necessário informar ao menos 1 campo ", JPStatus.ERRO, 20);
                    }
                } catch (NullPointerException ex) {
                    jpsConsultaNegativacao.setStatus("Erro ao pesquisar cliente.", JPStatus.ERRO, ex);
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamentoteste.dispose();
                    });

                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jpsConsultaNegativacao.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }

                SwingUtilities.invokeLater(() -> {
                    if (tpConsultaSint.getRowCount() > 0) {
                        tpConsultaSint.packAll();
                        tpConsultaSint.addRowSelectionInterval(tpConsultaSint.convertRowIndexToView(0), tpConsultaSint.convertRowIndexToView(0));
                        tpConsultaSint.grabFocus();
                    }
                    telaProcessamentoteste.dispose();
                });
            }
        }
                .start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
        calculaTotalSintetico();
        return retornoPesquisar;

    }

    private void gerarExcelSint() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Exportando Excel...");
        ExportarExcelControl exportarExcelControl = new ExportarExcelControl();

        new Thread() {
            @Override
            public void run() {
                try {
                    String venc, extracao;
                    if (cdFiltroDataVencInicioSint.getDate() == null) {
                        venc = "31/12/1900-31/12/1900";
                    } else {
                        venc = "" + Utilitarios.converteDataString(cdFiltroDataVencInicioSint.getDate(), "dd-MM-yyyy") + "-"
                                + Utilitarios.converteDataString(cdFiltroDataVencFinalSint.getDate(), "dd-MM-yyyy");
                    }
                    if (cdFiltroDataExtracaoInicioSint.getDate() == null) {
                        extracao = "31/12/1900-31/12/1900";
                    } else {

                        extracao = "" + Utilitarios.converteDataString(cdFiltroDataExtracaoInicioSint.getDate(), "dd-MM-yyyy") + "-" + Utilitarios.converteDataString(cdFiltroDataExtracaoFinalSint.getDate(), "dd-MM-yyyy");
                    }
                    String tipo = clsFiltroPagamentoSint.getSelectedItem().toString();
                    exportarExcelControl.expNegativacoesSint(otmNegativacao.getData(), TelaPrincipal.diretorio, venc, extracao, tipo);
                    jpsConsultaNegativacao.setStatus("Arquivo gerado com sucesso.", jpsConsultaNegativacao.NORMAL);

                } catch (ErroSistemaException ex) {
                    jpsConsultaNegativacao.setStatus("Erro", jpsConsultaNegativacao.ERRO, ex);
                }

                SwingUtilities.invokeLater(
                        () -> {
                            telaProcessamentoteste.dispose();
                        }
                );
            }
        }.start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
    }

}
