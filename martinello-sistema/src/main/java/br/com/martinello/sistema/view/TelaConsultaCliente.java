/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view;

import br.com.martinello.bd.matriz.control.ExtracaoTableController;
import br.com.martinello.bd.matriz.control.FilialController;
import br.com.martinello.bd.matriz.control.ParcelasExtracaoController;
import br.com.martinello.bd.matriz.model.domain.ConsultaClienteControl;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ContasReceberControl;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioLogin;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.consulta.Domain.ConsultaClienteModel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
import br.com.martinello.consulta.Domain.ParcelasModel;
import br.com.martinello.consulta.Domain.PessoaModel;
import br.com.martinello.consulta.Domain.VendasItensModel;
import br.com.martinello.consulta.Domain.VendasModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.SwingUtilities;
import br.com.martinello.sistema.view.painel.PainelCliente;
import br.com.martinello.sistema.view.painel.PainelCliente2;
import br.com.martinello.sistema.view.painel.PainelContasReceber;
import br.com.martinello.sistema.view.painel.PainelVenda;
import br.com.martinello.util.filtro.Filtro;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.LinkedList;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author luiz.almeida
 */
public class TelaConsultaCliente extends TelaPadrao {

    public static String retornoExtracao;
    private boolean retornoConsulta;
    private int contFiltro;
    private ConsultaModel filtroConsulta;
    public List<ConsultaModel> lmotivoBaixaBvs, lmotivoInclusao, lmotivoBaixaSpc, lnaturezaRegistro, lnaturezaInclusao;
    public ExtracaoTableController extracaoTableController;
    public FilialController filiaisControler = new FilialController();
    public static List<FilialModel> lfiliaisModel = new ArrayList();
    public List<ExtracaoModel> lExtracaoModel;
    public static List<VendasModel> lVendas;
    public static List<VendasItensModel> lVendasItens;
    public static List<ContasReceberModel> lContasReceber;
    public static List<ParcelasModel> lParcelas;
    public static List<ConsultaClienteModel> lConsultaCliente;
    public static List<PessoaModel> lPessoa;
    public ExtracaoModel extracaoModel;
    public ParcelasExtracaoController parcelaExtracao;
    public ConsultaClienteControl consultaClienteControl;
    public ContasReceberControl contasReceberControl;
    public PainelCliente2 painelCliente2;
    public PainelContasReceber painelContasReceber = new PainelContasReceber();
    public PainelVenda painelVenda = new PainelVenda();
    public static ConsultaClienteModel consultaCliente;
    public static VendasModel vendas;
    public int tQuitacao, tParcial, tRenegociacao, tDevolucao, tTransf, tPerdida;
    public double valorTraz, valorCalc, valorAberto, valorParcela, valorOriginal, valorVencer;
    private boolean resultado;
    private String valorCopiado;

    private static String sCodFil;
    private static int codCli;

    private final ObjectTableModel<PessoaModel> otmConsultaCliente = new ObjectTableModel<>(PessoaModel.class, "indice,pontoFilial,idPessoa,cpf,nomeRazaoSocial,situacao,nomeDoPai,nomeDaMae,"
            + "numeroRG,orgaoEmissorRG,dataEmissaoRG,email,estadoCivil,dataNascimento,tipoPessoa,numeroTel,idAvalista");

    /**
     * Creates new form ExtracaoManual
     */
    public TelaConsultaCliente() {

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
        // formata e exibe o resultado
        formato = new SimpleDateFormat("dd/MM/yyyy");
//        cdFiltroPagInicio.setDate(ontem.getTime());
//        cdFiltroPagFinal.setDate(agora.getTime());
//        cdFiltroVencInicial.setDate(cincoAnos.getTime());
//        cdFiltroVencFinal.setDate(trintaDias.getTime());
        bFiltroPesquisar.setMnemonic(KeyEvent.VK_P);
        bFiltroCancelar.setMnemonic(KeyEvent.VK_C);
        bFiltroVisualizar.setMnemonic(KeyEvent.VK_V);
        painelCliente2 = new PainelCliente2();
        paExtracaoManual.add(painelCliente2, "Cliente");
        paExtracaoManual.setEnabledAt(1, false);

        paExtracaoManual.add(painelVenda, "Vendas");
        paExtracaoManual.setEnabledAt(2, false);

      /*  paExtracaoManual.add(painelContasReceber, "Contas Receber");
        paExtracaoManual.setEnabledAt(3, false);*/
        
        
        

    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPadrao2 = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jPStatusExtracaoManual = new br.com.martinello.componentesbasicos.paineis.JPStatus();
        pCliente = new br.com.martinello.componentesbasicos.paineis.Painel();
        botao1 = new br.com.martinello.componentesbasicos.Botao();
        botao2 = new br.com.martinello.componentesbasicos.Botao();
        paExtracaoManual = new br.com.martinello.componentesbasicos.paineis.PainelAba();
        ppBuscaExtrManual = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        ppFiltroBuscaExtrManual = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        rFiltroFilial = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroNome = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroCpf = new br.com.martinello.componentesbasicos.Rotulo();
        rFiltroCodigoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        clsFiltroFilial = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cscFiltroNome = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        ccccFiltroCpf = new br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta();
        csFiltroCodigoCliente = new br.com.martinello.componentesbasicos.CampoString();
        bFiltroVisualizar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroCancelar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bFiltroVisualizar1 = new br.com.martinello.componentesbasicos.Botao();
        rFiltroCodigoClienteLoja = new br.com.martinello.componentesbasicos.Rotulo();
        csFiltroCodigoClienteLoja = new br.com.martinello.componentesbasicos.CampoString();
        jspBuscaExtrManual = new javax.swing.JScrollPane();
        tpExtrManual = new br.com.martinello.componentesbasicos.TabelaPadrao();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Consulta Cliente");
        setPreferredSize(new java.awt.Dimension(1366, 768));

        painelPadrao2.setLayout(new java.awt.BorderLayout());
        painelPadrao2.add(jPStatusExtracaoManual, java.awt.BorderLayout.SOUTH);

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
                .addContainerGap(515, Short.MAX_VALUE)
                .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(594, 594, 594))
        );
        pClienteLayout.setVerticalGroup(
            pClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pClienteLayout.createSequentialGroup()
                .addGroup(pClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPadrao2.add(pCliente, java.awt.BorderLayout.LINE_START);

        getContentPane().add(painelPadrao2, java.awt.BorderLayout.SOUTH);

        paExtracaoManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paExtracaoManualMouseClicked(evt);
            }
        });

        ppBuscaExtrManual.setLayout(new java.awt.BorderLayout());

        ppFiltroBuscaExtrManual.setPreferredSize(new java.awt.Dimension(1038, 150));
        ppFiltroBuscaExtrManual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ppFiltroBuscaExtrManualKeyPressed(evt);
            }
        });

        rFiltroFilial.setText("Empresa:");

        rFiltroNome.setText("Nome:");

        rFiltroCpf.setText("CPF/CNPJ:");

        rFiltroCodigoCliente.setText("Código Sapiens:");

        csFiltroCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csFiltroCodigoClienteActionPerformed(evt);
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

        bFiltroPesquisar.setText("Pesquisar");
        bFiltroPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroPesquisarActionPerformed(evt);
            }
        });

        bFiltroVisualizar1.setText("Extrato");
        bFiltroVisualizar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFiltroVisualizar1ActionPerformed(evt);
            }
        });

        rFiltroCodigoClienteLoja.setText("Código Loja:");

        csFiltroCodigoClienteLoja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csFiltroCodigoClienteLojaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroBuscaExtrManualLayout = new javax.swing.GroupLayout(ppFiltroBuscaExtrManual);
        ppFiltroBuscaExtrManual.setLayout(ppFiltroBuscaExtrManualLayout);
        ppFiltroBuscaExtrManualLayout.setHorizontalGroup(
            ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(106, 785, Short.MAX_VALUE))
                            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                                        .addGap(106, 106, 106)
                                        .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                        .addComponent(rFiltroCodigoClienteLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(csFiltroCodigoClienteLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bFiltroVisualizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bFiltroVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ppFiltroBuscaExtrManualLayout.setVerticalGroup(
            ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroBuscaExtrManualLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(clsFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroFilial, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cscFiltroNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ccccFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(csFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bFiltroCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(csFiltroCodigoClienteLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroCodigoClienteLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ppFiltroBuscaExtrManualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bFiltroVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bFiltroVisualizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppBuscaExtrManual.add(ppFiltroBuscaExtrManual, java.awt.BorderLayout.PAGE_START);

        tpExtrManual.setModel(otmConsultaCliente);
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

        ppBuscaExtrManual.add(jspBuscaExtrManual, java.awt.BorderLayout.CENTER);

        paExtracaoManual.addTab("Consulta", ppBuscaExtrManual);

        getContentPane().add(paExtracaoManual, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void paExtracaoManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paExtracaoManualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_paExtracaoManualMouseClicked

    private void botao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao1ActionPerformed
        paExtracaoManual.setSelectedComponent(ppBuscaExtrManual);
        paExtracaoManual.setEnabledAt(0, true);
        paExtracaoManual.setEnabledAt(1, false);
        paExtracaoManual.setEnabledAt(2, false);
      //  paExtracaoManual.setEnabledAt(3, false);


    }//GEN-LAST:event_botao1ActionPerformed

    private void ppFiltroBuscaExtrManualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ppFiltroBuscaExtrManualKeyPressed

    }//GEN-LAST:event_ppFiltroBuscaExtrManualKeyPressed

    private void bFiltroVisualizar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroVisualizar1ActionPerformed
        gerarExtrato();
    }//GEN-LAST:event_bFiltroVisualizar1ActionPerformed

    private void bFiltroPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroPesquisarActionPerformed
        buscarRegistro();
    }//GEN-LAST:event_bFiltroPesquisarActionPerformed

    private void bFiltroCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroCancelarActionPerformed
        limparPesquisar();
    }//GEN-LAST:event_bFiltroCancelarActionPerformed

    private void bFiltroVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFiltroVisualizarActionPerformed
        try {
            sCodFil = otmConsultaCliente.getValue(tpExtrManual.getLinhaSelecionada()).getPontoFilial();
            codCli = (otmConsultaCliente.getValue(tpExtrManual.getLinhaSelecionada()).getIdPessoa());

            resultado = painelCliente2.visualizarCliente(sCodFil, codCli);
            resultado = painelVenda.buscarVendas(sCodFil, codCli);
           // resultado = painelContasReceber.buscarContasReceber(sCodFil, codCli);
            if (resultado == true) {
                paExtracaoManual.setEnabledAt(0, false);
                paExtracaoManual.setEnabledAt(1, true);
                paExtracaoManual.setEnabledAt(2, true);
            //    paExtracaoManual.setEnabledAt(3, true);
                paExtracaoManual.setSelectedComponent(painelVenda);
            }
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus("Erro ao Visualizar Cliente!", JPStatus.ERRO, ex);
        }
    }//GEN-LAST:event_bFiltroVisualizarActionPerformed

    private void csFiltroCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csFiltroCodigoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csFiltroCodigoClienteActionPerformed

    private void csFiltroCodigoClienteLojaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csFiltroCodigoClienteLojaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csFiltroCodigoClienteLojaActionPerformed

    private void tpExtrManualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpExtrManualFocusLost
      StringSelection stringSelection = new StringSelection(valorCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_tpExtrManualFocusLost

    private void tpExtrManualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpExtrManualKeyPressed
      if ((evt.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            // Cut, copy, paste and duplicate
            if (evt.getKeyCode() == KeyEvent.VK_C) {
                valorCopiado = otmConsultaCliente.getValueAt(tpExtrManual.getSelectedRow(), tpExtrManual.getSelectedColumn()).toString();
            }
        }
    }//GEN-LAST:event_tpExtrManualKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Botao bFiltroCancelar;
    private br.com.martinello.componentesbasicos.Botao bFiltroPesquisar;
    private br.com.martinello.componentesbasicos.Botao bFiltroVisualizar;
    private br.com.martinello.componentesbasicos.Botao bFiltroVisualizar1;
    private br.com.martinello.componentesbasicos.Botao botao1;
    private br.com.martinello.componentesbasicos.Botao botao2;
    private br.com.martinello.componentesbasicos.consulta.CampoCpfCnpjConsulta ccccFiltroCpf;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsFiltroFilial;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.CampoString csFiltroCodigoClienteLoja;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroNome;
    private static br.com.martinello.componentesbasicos.paineis.JPStatus jPStatusExtracaoManual;
    private javax.swing.JScrollPane jspBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.Painel pCliente;
    private br.com.martinello.componentesbasicos.paineis.PainelAba paExtracaoManual;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao painelPadrao2;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppBuscaExtrManual;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppFiltroBuscaExtrManual;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCodigoClienteLoja;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroCpf;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroFilial;
    private br.com.martinello.componentesbasicos.Rotulo rFiltroNome;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpExtrManual;
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
        ccccFiltroCpf.setaValorFiltro("");
    }

    private boolean buscarRegistro() {
        // TODO add your handling code here:
        final TelaProcessamento telaProcessamento = new TelaProcessamento("Realizando consulta...");
        List<Filtro> lFiltros = new LinkedList<>();

        if (clsFiltroFilial.getSelectedItem().toString().equals("TODAS")) {
            lFiltros.add(new Filtro("ORIGEM", Filtro.STRING, Filtro.IGUAL, "SGLM"));
        } else {
            for (FilialModel filial : lfiliaisModel) {
                String nome = clsFiltroFilial.getSelectedItem().toString();
                if (filial.getNomeLoja().equalsIgnoreCase(nome)) {
                    lFiltros.add(new Filtro("LOJA", Filtro.STRING, Filtro.IGUAL, filial.getPontoFilial()));
                    if (filial.getOrigemBD().equalsIgnoreCase("NOVO_SGL")) {
                        lFiltros.add(new Filtro("ORIGEM", Filtro.STRING, Filtro.IGUAL, "SGLM"));
                    } else {
                        lFiltros.add(new Filtro("ORIGEM", Filtro.STRING, Filtro.IGUAL, "SGL"));
                    }

                }
            }
        }
        if (cscFiltroNome.getFiltroOld() != null) {

            lFiltros.add(new Filtro("NOME", Filtro.STRING, Filtro.IGUAL, cscFiltroNome.getFiltroOld().toUpperCase()));
        }

        if (!csFiltroCodigoCliente.getText().equals("")) {
            lFiltros.add(new Filtro("CODCLI", Filtro.INTEGER, Filtro.IGUAL, csFiltroCodigoCliente.getText()));
        }
         if (!csFiltroCodigoClienteLoja.getText().equals("")) {
            lFiltros.add(new Filtro("CODCLI_LOJA", Filtro.INTEGER, Filtro.IGUAL, csFiltroCodigoClienteLoja.getText()));
        }
        
        String cpfCnpj = ccccFiltroCpf.getFiltroOld() != null ? ccccFiltroCpf.getFiltroOld().replaceAll("[^0-9]", "").trim() : "";
        if (cpfCnpj != null && !cpfCnpj.isEmpty()) {
            long cpf = Long.parseLong(cpfCnpj);
            lFiltros.add(new Filtro("CGCCPF", Filtro.LONG, Filtro.IGUAL, cpf));

        }

        new Thread() {
            @Override
            public void run() {
                try {
                    lPessoa = new LinkedList<>();
                    consultaClienteControl = new ConsultaClienteControl();
                    lPessoa = consultaClienteControl.listCliente(lFiltros);
                    if (lPessoa == null) {
                        jPStatusExtracaoManual.setStatus("Nenhum resultado encontrado para a pesquisa refaça a busca", JPStatus.ERRO);
                    } else {
                        otmConsultaCliente.setData(lPessoa);
                        otmConsultaCliente.fireTableDataChanged();
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
                } catch (ErroSistemaException ex) {
                    jPStatusExtracaoManual.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
                }
            }
        }.start();
        telaProcessamento.setVisible(true);
        telaProcessamento.requestFocusInWindow();

        return retornoConsulta;

    }

    public void inserirObservacao() {

    }

    private void gerarExtrato() {
        try {
            long cgccpf = Long.parseLong((otmConsultaCliente.getValue(tpExtrManual.getLinhaSelecionada()).getCpf()));          
            contasReceberControl = new ContasReceberControl();
             String loginSO = "";
            for (UsuarioLogin userLogin : TelaPrincipal.usuario.getLogins()) {
                loginSO = userLogin.getNomeUsuarioSO();
            }

            contasReceberControl.ExtratoCliente(cgccpf, TelaPrincipal.usuario.getLogin(), loginSO);
          
        } catch (ErroSistemaException ex) {
            jPStatusExtracaoManual.setStatus("Erro ao Gerar Extrato do Cliente!", JPStatus.ERRO, ex);
        }
    }

}
