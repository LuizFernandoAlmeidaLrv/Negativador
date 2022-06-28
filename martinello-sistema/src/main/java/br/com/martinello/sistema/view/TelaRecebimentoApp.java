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
import br.com.martinello.bd.matriz.control.RecebimentoAppControl;
import br.com.martinello.bd.matriz.model.domain.ConsultaClienteControl;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ContasReceberControl;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.RecebimentoApp;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.consulta.Domain.ConsultaClienteModel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author luiz.almeida
 */
public class TelaRecebimentoApp extends TelaPadrao {
    
    public static String retornoExtracao;
    private boolean retornoConsulta;
    private RecebimentoApp recebimentoApp;
    public List<ConsultaModel> lmotivoBaixaBvs, lmotivoInclusao, lmotivoBaixaSpc, lnaturezaRegistro, lnaturezaInclusao;
    public ExtracaoTableController extracaoTableController;
    public FilialController filiaisControler = new FilialController();
    public static List<FilialModel> lfiliaisModel = new ArrayList();
    public List<ExtracaoModel> lExtracaoModel;
    public static List<ParcelaModel> lParcelas;
    public static List<VendasModel> lVendas;
    public static List<VendasItensModel> lVendasItens;
    public static List<ContasReceberModel> lContasReceber;
    public static List<RecebimentoApp> lRecebimentoApp;
    public static ParcelaModel parcelaApp;
    public ExtracaoModel extracaoModel;
    public ParcelasExtracaoController parcelaExtracao;
    public ConsultaClienteControl consultaClienteControl;
    public ContasReceberControl contasReceberControl;
    public RecebimentoAppControl recebimentoAppControl;
    public static ConsultaClienteModel consultaCliente;
    public static VendasModel vendas;
    public int tQuitacao, tParcial, tRenegociacao, tDevolucao, tTransf, tPerdida;
    public double valorTraz, valorCalc, valorAberto, valorParcela, valorOriginal, valorVencer, valorDevolucao;
    private boolean resultado;
    private String valorCopiado;    
    private static String sCodFil;
    private static int codCli;
    
    private final ObjectTableModel<RecebimentoApp> otmRecebimentoApp = new ObjectTableModel<>(RecebimentoApp.class, "idRecebimento,sitRecebimento,formaPagamento,valor,bandeira,codigoAutorizacao,"
            + "cgcCpf,documento,nomeCliente,dataGerInicio,dataRecInicio,dataVenInicio,dataConInicio");
    private final ObjectTableModel<ParcelaModel> otmParcelas = new ObjectTableModel<>(ParcelaModel.class, "codfil,pontoFilial,numeroDoContrato,numeroParcela,dataLancamento,dataVencimento,dataDoRetorno,valorParcela,valorCalc,valorPago,"
            + "dataPagamento");

    /**
     * Creates new form ExtracaoManual
     */
    public TelaRecebimentoApp() {

        //super("Tela Extração Manual");
        initComponents();
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
        // formata e exibe o resultado
        formato = new SimpleDateFormat("dd/MM/yyyy");
        bFiltroPesquisar.setMnemonic(KeyEvent.VK_P);
        bFiltroCancelar.setMnemonic(KeyEvent.VK_C);
        if (TelaPrincipal.usuario.getIdUsuario() == 117) {
            bFiltroPesquisar1.setEnabled(true);
        } else {
            bFiltroPesquisar1.setEnabled(false);
        }
        tpPagamentoApp.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpPagamentoApp.getSelectedRow() >= 0) {
                setParcelas();
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

        jPStatusRecebimentoApp = new br.com.martinello.componentesbasicos.paineis.JPStatus();
        paExtracaoManual = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppBuscaExtrManual = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroBuscaRecApp = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rFiltroNome = new br.com.martinello.componentesbasicos.Rotulo();
        cscFiltroNome = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        rFiltroCpf = new br.com.martinello.componentesbasicos.Rotulo();
        ccccFiltroCpf = new br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta();
        rFiltroCodigoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroDataConciliacao = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDatConIni = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroDatConFin = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroDataRecebimento = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDatRecIni = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroDatRecFin = new br.com.martinello.componentesbasicos.CampoData();
        bFiltroCancelar = new br.com.martinello.componentesbasicos.Botao();
        rFiltroDataVencimento = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDatVenIni = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroDatVenFin = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroDataGeracao = new br.com.martinello.componentesbasicos.Rotulo();
        cdFiltroDatGerIni = new br.com.martinello.componentesbasicos.CampoData();
        cdFiltroDatGerFin = new br.com.martinello.componentesbasicos.CampoData();
        rFiltroBandeira = new br.com.martinello.componentesbasicos.Rotulo();
        clsBandeira = new br.com.martinello.componentesbasicos.CampoListaSimples();
        bFiltroPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroPlanilhaGrid = new br.com.martinello.componentesbasicos.Botao();
        bFiltroVisualizar2 = new br.com.martinello.componentesbasicos.Botao();
        rFiltroIdRegistro = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroSitReceb = new br.com.martinello.componentesbasicos.Rotulo();
        clsSitReceb = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroTipPag = new br.com.martinello.componentesbasicos.Rotulo();
        clsFormPag = new br.com.martinello.componentesbasicos.CampoListaSimples();
        rFiltroAut = new br.com.martinello.componentesbasicos.Rotulo();
        csAut = new br.com.martinello.componentesbasicos.CampoString();
        rFiltroDoc = new br.com.martinello.componentesbasicos.Rotulo();
        csDoc = new br.com.martinello.componentesbasicos.CampoString();
        ciCodCli = new br.com.martinello.componentesbasicos.CampoInteiro();
        ciIdRegistro = new br.com.martinello.componentesbasicos.CampoInteiro();
        bFiltroPesquisar1 = new br.com.martinello.componentesbasicos.Botao();
        painelPadrao1 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rNomeQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rQuantidade = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotalR = new br.com.martinello.componentesbasicos.Rotulo();
        rValorTotal = new br.com.martinello.componentesbasicos.Rotulo();
        paParcelas = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppParcelas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppMovimentos = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jScrollPane8 = new javax.swing.JScrollPane();
        tpCrMov = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspBuscaExtrManual = new javax.swing.JScrollPane();
        tpPagamentoApp = new br.com.martinello.componentesbasicos.TabelaPadrao();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta Recebimentos App");
        setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().add(jPStatusRecebimentoApp, java.awt.BorderLayout.PAGE_END);

        paExtracaoManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paExtracaoManualMouseClicked(evt);
            }
        });

        ppBuscaExtrManual.setLayout(new java.awt.BorderLayout());

        ppFiltroBuscaRecApp.setPreferredSize(new java.awt.Dimension(1038, 180));
        ppFiltroBuscaRecApp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppFiltroBuscaRecAppKeyPressed(evt);
            }
        });

        rFiltroNome.setText("Nome:");

        rFiltroCpf.setText("CPF/CNPJ:");

        rFiltroCodigoCliente.setText("Código Cliente:");

        rFiltroDataConciliacao.setText("Data Conciliação:");

        cdFiltroDatConIni.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatConIni.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatConIni.setOpaque(true);
        cdFiltroDatConIni.setPreferredSize(null);
        cdFiltroDatConIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdFiltroDatConIniActionPerformed(evt);
            }
        });

        cdFiltroDatConFin.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatConFin.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatConFin.setOpaque(true);
        cdFiltroDatConFin.setPreferredSize(null);

        rFiltroDataRecebimento.setText("Data Recebimento:");

        cdFiltroDatRecIni.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatRecIni.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatRecIni.setOpaque(true);
        cdFiltroDatRecIni.setPreferredSize(null);

        cdFiltroDatRecFin.setMaximumSize(new java.awt.Dimension(99, 21));
        cdFiltroDatRecFin.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatRecFin.setOpaque(true);
        cdFiltroDatRecFin.setPreferredSize(new java.awt.Dimension(105, 21));

        bFiltroCancelar.setText("Cancelar");
        bFiltroCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroCancelarActionPerformed(evt);
            }
        });

        rFiltroDataVencimento.setText("Data Vencimento:");

        cdFiltroDatVenIni.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatVenIni.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatVenIni.setOpaque(true);
        cdFiltroDatVenIni.setPreferredSize(null);

        cdFiltroDatVenFin.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatVenFin.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatVenFin.setOpaque(true);
        cdFiltroDatVenFin.setPreferredSize(null);

        rFiltroDataGeracao.setText("Data Geração:");

        cdFiltroDatGerIni.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatGerIni.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatGerIni.setOpaque(true);
        cdFiltroDatGerIni.setPreferredSize(null);

        cdFiltroDatGerFin.setMaximumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatGerFin.setMinimumSize(new java.awt.Dimension(95, 21));
        cdFiltroDatGerFin.setOpaque(true);
        cdFiltroDatGerFin.setPreferredSize(null);

        rFiltroBandeira.setText("Bandeira:");

        clsBandeira.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hipercard", "Amex", "Diners", "Master", "Visa", "Elo", " " }));
        clsBandeira.setSelectedIndex(6);

        bFiltroPesquisar.setText("Pesquisar");
        bFiltroPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPesquisarActionPerformed(evt);
            }
        });

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

        rFiltroIdRegistro.setText("Id_Recebimento:");

        rFiltroSitReceb.setText("Sit. Recebimento:");

        clsSitReceb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "R - Recebido", "D - Digitado", "C - Cancelado", "A - Aguardando", "" }));
        clsSitReceb.setSelectedIndex(4);

        rFiltroTipPag.setText("Tipo Pagamento:");

        clsFormPag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "P - Pix", "C - Cartão", "B - Boleto", " " }));
        clsFormPag.setSelectedIndex(3);

        rFiltroAut.setText("Autorização:");

        rFiltroDoc.setText("Documento:");

        bFiltroPesquisar1.setText("Ger. Pendencia");
        bFiltroPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPesquisar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroBuscaRecAppLayout = new javax.swing.GroupLayout(ppFiltroBuscaRecApp);
        ppFiltroBuscaRecApp.setLayout(ppFiltroBuscaRecAppLayout);
        ppFiltroBuscaRecAppLayout.setHorizontalGroup(
            ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ciCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rFiltroSitReceb, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroIdRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                        .addComponent(clsSitReceb, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rFiltroBandeira, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clsBandeira, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rFiltroAut, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(csAut, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                        .addComponent(ciIdRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(51, 51, 51)
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rFiltroDataRecebimento, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(rFiltroDataVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rFiltroDataConciliacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rFiltroDataGeracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rFiltroDoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                        .addComponent(rFiltroTipPag, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsFormPag, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cdFiltroDatVenIni, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroDatRecIni, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroDatGerIni, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cdFiltroDatVenFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(cdFiltroDatRecFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroDatGerFin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ppFiltroBuscaRecAppLayout.createSequentialGroup()
                            .addComponent(cdFiltroDatConIni, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cdFiltroDatConFin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bFiltroPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ppFiltroBuscaRecAppLayout.createSequentialGroup()
                            .addComponent(csDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(264, 264, 264)
                            .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                    .addComponent(bFiltroPlanilhaGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bFiltroVisualizar2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                    .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        ppFiltroBuscaRecAppLayout.setVerticalGroup(
            ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rFiltroDataGeracao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rFiltroDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rFiltroDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDatVenIni, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDatVenFin, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ciCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cdFiltroDatGerIni, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdFiltroDatGerFin, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(cdFiltroDatRecIni, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cdFiltroDatRecFin, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cdFiltroDatConIni, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cdFiltroDatConFin, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bFiltroPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rFiltroDataConciliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rFiltroIdRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ciIdRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsSitReceb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroSitReceb, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clsBandeira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csAut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroBandeira, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rFiltroAut, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroBuscaRecAppLayout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bFiltroPlanilhaGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ppFiltroBuscaRecAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rFiltroTipPag, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(clsFormPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bFiltroVisualizar2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        ppBuscaExtrManual.add(ppFiltroBuscaRecApp, java.awt.BorderLayout.PAGE_START);

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
                .addContainerGap(1010, Short.MAX_VALUE))
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

        ppMovimentos.setPreferredSize(new java.awt.Dimension(2, 250));
        ppMovimentos.setLayout(new java.awt.BorderLayout());

        tpCrMov.setModel(otmParcelas);
        jScrollPane8.setViewportView(tpCrMov);

        ppMovimentos.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        ppParcelas.add(ppMovimentos, java.awt.BorderLayout.SOUTH);

        tpPagamentoApp.setModel(otmRecebimentoApp);
        tpPagamentoApp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpPagamentoAppFocusLost(evt);
            }
        });
        tpPagamentoApp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpPagamentoAppKeyPressed(evt);
            }
        });
        jspBuscaExtrManual.setViewportView(tpPagamentoApp);

        ppParcelas.add(jspBuscaExtrManual, java.awt.BorderLayout.CENTER);

        paParcelas.addTab("Parcelas", ppParcelas);

        ppBuscaExtrManual.add(paParcelas, java.awt.BorderLayout.CENTER);

        paExtracaoManual.addTab("Consulta", ppBuscaExtrManual);

        getContentPane().add(paExtracaoManual, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paExtracaoManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paExtracaoManualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_paExtracaoManualMouseClicked

    private void tpPagamentoAppKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpPagamentoAppKeyPressed
        if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            // Cut, copy, paste and duplicate
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmRecebimentoApp.getValueAt(tpPagamentoApp.getSelectedRow(), tpPagamentoApp.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpPagamentoAppKeyPressed

    private void tpPagamentoAppFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpPagamentoAppFocusLost
        StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpPagamentoAppFocusLost

    private void ppFiltroBuscaRecAppKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppFiltroBuscaRecAppKeyPressed

    }//GEN-LAST:event_ppFiltroBuscaRecAppKeyPressed

    private void bFiltroVisualizar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroVisualizar2ActionPerformed
        // gerarExtrato();
    }//GEN-LAST:event_bFiltroVisualizar2ActionPerformed

    private void bFiltroPlanilhaGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPlanilhaGridActionPerformed
        try {
            ExportarExcelControl exportarExcelControl = new ExportarExcelControl();
            exportarExcelControl.exportarRecebimento(otmRecebimentoApp.getData(), otmRecebimentoApp.getValue(tpPagamentoApp.getLinhaSelecionada()).getNomeCliente(), TelaPrincipal.diretorio);
            jPStatusRecebimentoApp.setStatus("Arquivo gerado com sucesso.", jPStatusRecebimentoApp.NORMAL);
        } catch (ErroSistemaException ex) {
            Logger.getLogger(TelaRecebimentoApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bFiltroPlanilhaGridActionPerformed

    private void bFiltroPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPesquisarActionPerformed
        lContasReceber = new LinkedList<>();
        buscarRegistro();
    }//GEN-LAST:event_bFiltroPesquisarActionPerformed

    private void bFiltroCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroCancelarActionPerformed
        limparPesquisar();
    }//GEN-LAST:event_bFiltroCancelarActionPerformed

    private void cdFiltroDatConIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdFiltroDatConIniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cdFiltroDatConIniActionPerformed

    private void bFiltroPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPesquisar1ActionPerformed
        int linha = tpPagamentoApp.getSelectedRow();
        if (linha < 0) {
            jPStatusRecebimentoApp.setStatus("Selecione um registro na Tabela, antes de clicar em Gravar data", JPStatus.ALERTA);
        } else {
            int result;
            Object[] options = {"Sim", "Não"};
            result = JOptionPane.showOptionDialog(null, "Gostaria de gerar pendência manual para o registro Selecionado?", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (result == 0) {
                try {
                    parcelaApp = new ParcelaModel();
                    parcelaApp = otmParcelas.getValue(tpCrMov.getLinhaSelecionada());
                    recebimentoAppControl = new RecebimentoAppControl();
                    recebimentoAppControl.gerarPendenciaRecApp(parcelaApp);
                    jPStatusRecebimentoApp.setStatus("Pendência gerada com sucesso!", jPStatusRecebimentoApp.NORMAL);
                } catch (ErroSistemaException ex) {
                    jPStatusRecebimentoApp.setStatus("Erro: " + ex.getMessage(), JPStatus.ERRO);
                }
            }
        }

    }//GEN-LAST:event_bFiltroPesquisar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Botao bFiltroCancelar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPesquisar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPesquisar1;
    private br.com.martinello.componentesbasicos.Botao bFiltroPlanilhaGrid;
    private br.com.martinello.componentesbasicos.Botao bFiltroVisualizar2;
    private br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta ccccFiltroCpf;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatConFin;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatConIni;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatGerFin;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatGerIni;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatRecFin;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatRecIni;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatVenFin;
    private br.com.martinello.componentesbasicos.CampoData cdFiltroDatVenIni;
    private br.com.martinello.componentesbasicos.CampoInteiro ciCodCli;
    private br.com.martinello.componentesbasicos.CampoInteiro ciIdRegistro;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsBandeira;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFormPag;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsSitReceb;
    private br.com.martinello.componentesbasicos.CampoString csAut;
    private br.com.martinello.componentesbasicos.CampoString csDoc;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroNome;
    private static br.com.martinello.componentesbasicos.paineis.JPStatus jPStatusRecebimentoApp;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jspBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paExtracaoManual;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paParcelas;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroBuscaRecApp;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppMovimentos;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppParcelas;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroAut;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroBandeira;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCpf;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataConciliacao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataGeracao;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataRecebimento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDataVencimento;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroDoc;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroIdRegistro;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroNome;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroSitReceb;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroTipPag;
    private br.com.martinello.componentesbasicos.Rotulo rNomeQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rQuantidade;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotal;
    private br.com.martinello.componentesbasicos.Rotulo rValorTotalR;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCrMov;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpPagamentoApp;
    // End of variables declaration//GEN-END:variables

    private void limparSalvar() {
        paExtracaoManual.setSelectedIndex(0);
        paExtracaoManual.setEnabledAt(1, false);
        
    }
    
    private void limparPesquisar() {
        cscFiltroNome.setaValorFiltro("");
        ccccFiltroCpf.setaValorFiltro("");
        cdFiltroDatConIni.setDate(null);
        cdFiltroDatRecFin.setDate(null);
        cdFiltroDatRecIni.setDate(null);
        cdFiltroDatConFin.setDate(null);
    }
    
    private boolean buscarRegistro() {
        // TODO add your handling code here:
        final TelaProcessamento telaProcessamento = new TelaProcessamento("Realizando consulta...");
        
        recebimentoApp = new RecebimentoApp();
        if (cscFiltroNome.getFiltroOld() != null) {
            recebimentoApp.setNomeCliente(cscFiltroNome.getFiltroOld().toUpperCase());
        }
        
        String cpfCnpj = ccccFiltroCpf.getFiltroOld() != null ? ccccFiltroCpf.getFiltroOld().replaceAll("[^0-9]", "").trim() : "";
        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            recebimentoApp.setCgcCpf(cpfCnpj);
        }
        if (cdFiltroDatConIni.getDate() != null | cdFiltroDatConFin.getDate() != null) {
            if (cdFiltroDatConFin.getDate() != null) {
                recebimentoApp.setDataConInicio(Utilitarios.converteDataString(cdFiltroDatConIni.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Conciliação precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroDatConIni.getDate() != null) {
                recebimentoApp.setDataConFim(Utilitarios.converteDataString(cdFiltroDatConFin.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Conciliação precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        
        if (cdFiltroDatRecIni.getDate() != null | cdFiltroDatRecFin.getDate() != null) {
            if (cdFiltroDatRecFin.getDate() != null) {
                recebimentoApp.setDataRecInicio(Utilitarios.converteDataString(cdFiltroDatRecIni.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Recebimento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroDatRecIni.getDate() != null) {
                recebimentoApp.setDataRecFim(Utilitarios.converteDataString(cdFiltroDatRecFin.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Recebimento precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (cdFiltroDatVenIni.getDate() != null | cdFiltroDatVenFin.getDate() != null) {
            if (cdFiltroDatVenFin.getDate() != null) {
                recebimentoApp.setDataVenInicio(Utilitarios.converteDataString(cdFiltroDatVenIni.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Vencimeto precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroDatVenIni.getDate() != null) {
                recebimentoApp.setDataVenFim(Utilitarios.converteDataString(cdFiltroDatVenFin.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data Vencimeto precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (cdFiltroDatGerIni.getDate() != null | cdFiltroDatGerFin.getDate() != null) {
            if (cdFiltroDatGerFin.getDate() != null) {
                recebimentoApp.setDataGerInicio(Utilitarios.converteDataString(cdFiltroDatGerIni.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data da Geração precisa ser preenchido nos dois campos, por favor preencha o campo ao lado direito!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
            if (cdFiltroDatGerIni.getDate() != null) {
                recebimentoApp.setDataGerFim(Utilitarios.converteDataString(cdFiltroDatGerFin.getDate(), "dd/MM/yyyy"));
            } else {
                jPStatusRecebimentoApp.setStatus("O campo data da Geração precisa ser preenchido nos dois campos, por favor preencha o campo ao lado esquerdo!", JPStatus.ALERTA, 20);
                return retornoConsulta = false;
            }
        }
        if (clsBandeira.getSelectedIndex() == 6) {
            recebimentoApp.setBandeira("");
        } else {
            recebimentoApp.setBandeira(clsBandeira.getString());
        }
        if (clsFormPag.getSelectedIndex() == 3) {
            recebimentoApp.setFormaPagamento("");
        } else {
            recebimentoApp.setFormaPagamento(clsFormPag.getString().substring(0, 1));
        }
        if (clsSitReceb.getSelectedIndex() == 4) {
            recebimentoApp.setSitRecebimento("");
        } else {
            recebimentoApp.setSitRecebimento(clsSitReceb.getString().substring(0, 1));
        }
        recebimentoApp.setCodigoAutorizacao(csAut.getString());
        recebimentoApp.setDocumento(csDoc.getString());
        recebimentoApp.setIdRecebimento(ciIdRegistro.getInteger());
        recebimentoApp.setCodigoCliente(ciCodCli.getInteger());
        
        new Thread() {
            @Override
            public void run() {
                lRecebimentoApp = new LinkedList<>();
                recebimentoAppControl = new RecebimentoAppControl();
                try {
                    lRecebimentoApp = recebimentoAppControl.buscarRecebimento(recebimentoApp);
                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    jPStatusRecebimentoApp.setStatus(retornoExtracao, JPStatus.ERRO, ex);
                }
                if (lRecebimentoApp == null) {
                    jPStatusRecebimentoApp.setStatus("Nenhum resultado encontrado para a pesquisa refaça a busca", JPStatus.ERRO);
                } else {
                    
                    otmRecebimentoApp.setData(lRecebimentoApp);
                    rQuantidade.setText("" + lRecebimentoApp.size());
                    
                    otmRecebimentoApp.fireTableDataChanged();
                    jPStatusRecebimentoApp.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
                    if (tpPagamentoApp.getRowCount() > 0) {
                        tpPagamentoApp.packAll();
                        tpPagamentoApp.addRowSelectionInterval(tpPagamentoApp.convertRowIndexToView(0), tpPagamentoApp.convertRowIndexToView(0));
                        tpPagamentoApp.grabFocus();
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
            for (int i = 0; i < tpPagamentoApp.getRowCount(); i++) {
                Double valorAux;
                valorAux = (Double) tpPagamentoApp.getValueAt(i, 3);
                valorTotal += valorAux.doubleValue();
            }
            
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            rValorTotal.setText(nf.format(valorTotal));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular Total Produtos: " + e.getMessage());
        }
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
                if (venda.getDevolvida().equals("S") || venda.getDevolvida().equals("P")) {
                    valorDevolucao = contasReceberControl.buscarValorDevolucao(venda.getIdRegistro(), venda.getCliente());
                    if (venda.getTipoVenda().equals("P") && venda.getSituacao().equals("L")) {
                        valorOriginal += Double.valueOf(venda.getVlrAprazo()) - valorDevolucao;
                    } else if (venda.getDevolvida().equals("S") && venda.getTipoVenda().equals("V") && venda.getSituacao().equals("L")) {
                        valorOriginal += Double.valueOf(venda.getVlrAvista()) - valorDevolucao;
                    }
                }
            }
            
            resultado = true;
        } catch (ErroSistemaException ex) {
            jPStatusRecebimentoApp.setStatus("Erro ao Buscar Contas Receber!", JPStatus.ERRO, ex);
        }
        return resultado;
    }

//    private void gerarExtrato() {
//        try {
//            codCli = Integer.parseInt(otm.getValue(tpExtrManual.getLinhaSelecionada()).getCodCliente());
//            contasReceberControl = new ContasReceberControl();
//            String loginSO = "";
//            for (UsuarioLogin userLogin : TelaPrincipal.usuario.getLogins()) {
//                loginSO = userLogin.getNomeUsuarioSO();
//            }
//
//            contasReceberControl.ExtratoCliente(codCli, TelaPrincipal.usuario.getLogin(), loginSO);
//        } catch (ErroSistemaException ex) {
//            jPStatusExtracaoManual.setStatus("Erro ao Gerar Extrato do Cliente!", JPStatus.ERRO, ex);
//        }
//    }
    private void setParcelas() {
        lParcelas = new LinkedList<>();
        lParcelas = otmRecebimentoApp.getValue(tpPagamentoApp.getLinhaSelecionada()).getlRecebimentoAppParcela();
        otmParcelas.setData(lParcelas);
        otmParcelas.fireTableDataChanged();
        tpCrMov.setForeground(Color.BLACK);
        if (tpCrMov.getRowCount() > 0) {
            tpCrMov.packAll();
            tpCrMov.addRowSelectionInterval(tpCrMov.convertRowIndexToView(0), tpCrMov.convertRowIndexToView(0));
        }
    }
}
