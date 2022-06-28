/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view;

import br.com.martinello.bd.matriz.control.ParametrosControl;
import br.com.martinello.bd.matriz.control.PrescreverRegistroControl;
import br.com.martinello.bd.matriz.control.ProcessamentoController;
import br.com.martinello.bd.matriz.control.UsuarioController;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.ParametrosModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.componentesbasicos.paineis.PainelDesktop;
import br.com.martinello.componentesbasicos.view.TelaPadrao;
import br.com.martinello.sistema.BarraStatus;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author luiz.almeida
 */
public class TelaPrincipal extends JFrame implements InternalFrameListener {

    private PrescreverRegistroControl prescrever = new PrescreverRegistroControl();
    protected static BarraStatus barraStatus;
    public static UsuarioModel usuario;
    public static Date dataExtrator;
    public static ParametrosModel parametros;
    public static List<ParametrosModel> Lparametros = new ArrayList<>();
    public static String email;
    public static String userEmail;
    public static String senhaEmail;
    public static String emailPcopia;
    public static String assEmail;
    public static String diretorio;
    public static String permissao;
    private ParametrosControl parametroControl = new ParametrosControl();
    protected UsuarioController usuarioControl;
    private TelaPadrao telaFilial;
    private TelaPadrao telaExtrator;
    private TelaPadrao telaExtracaoManual;
    private TelaPadrao telaExtracao;
    private TelaPadrao telaUsuario;
    private TelaPadrao telaEmail;
    private TelaPadrao telaParametros;
    private TelaPadrao telaDiversos;
    private TelaPadrao telaRelacionados;
    private TelaPadrao telaExtorno;
    private TelaPadrao telaContasReceber;
    private TelaPadrao telaConsultaNegativacao;
    private TelaPadrao telaConsultaCliente;
    private TelaPadrao telaRecebimentoApp;

    protected BufferedImage img;
    protected PainelDesktop jDesktopPane;// = new JDesktopPane();
    private boolean atualizaLogin;
    private ExecutorService executorAtualizaLogin = Executors.newFixedThreadPool(1);

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal(UsuarioModel usuarioModel) {
        atualizaLogin = true;
        TelaPrincipal.usuario = usuarioModel;
        TelaPrincipal.dataExtrator = new Date();

        usuarioControl = new UsuarioController();
        barraStatus = new BarraStatus(usuario, "ELETROMÓVEIS MARTINELLO", "Conectado", dataExtrator);
        carregarParametros();
        initComponents();
        this.add(barraStatus, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validaMenu();

        setVisible(true);

        setSize(1600, 900);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                try {
                    excluirLoginUsuario();
                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        });
        if (permissao == "S") {
            telaExtrator = new TelaDadosGerais();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaExtrator.getSize();
            telaExtrator.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaExtrator.addInternalFrameListener(this);
            jdpPainelPrincipal.add(telaExtrator);
            telaExtrator.setVisible(true);
        }
        try {
            prescrever.Prescrever(usuario.getLogin());
        } catch (ErroSistemaException ex) {
            barraStatus.setStatus("Erro ao Prescrever registros: " + ex.getMessage() + "");
        }
        new Thread() {
            @Override
            public void run() {
                Atualizalogin();
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpPainelPrincipal = new javax.swing.JDesktopPane();
        jmbMenuPrincipal = new javax.swing.JMenuBar();
        jmCadastro = new javax.swing.JMenu();
        jmiFilial = new javax.swing.JMenuItem();
        jmiUsuario = new javax.swing.JMenuItem();
        jmMovimentos = new javax.swing.JMenu();
        jmiExtracao = new javax.swing.JMenuItem();
        jmiExtrator = new javax.swing.JMenuItem();
        jmiExtracaoManual = new javax.swing.JMenuItem();
        jmiRegistrosImportados = new javax.swing.JMenuItem();
        jMFinancas = new javax.swing.JMenu();
        jMiConsultaContasReceber = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jmiConsultaCliente = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jmSistema = new javax.swing.JMenu();
        jmiAjuda = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        JmiDiversos = new javax.swing.JMenuItem();
        jmiSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Negativador");

        jdpPainelPrincipal.setPreferredSize(new java.awt.Dimension(1570, 860));

        javax.swing.GroupLayout jdpPainelPrincipalLayout = new javax.swing.GroupLayout(jdpPainelPrincipal);
        jdpPainelPrincipal.setLayout(jdpPainelPrincipalLayout);
        jdpPainelPrincipalLayout.setHorizontalGroup(
            jdpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
        );
        jdpPainelPrincipalLayout.setVerticalGroup(
            jdpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
        );

        try {
            img = ImageIO.read(new File("papel-de-parede4.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jdpPainelPrincipal = new PainelDesktop(img);
        this.add(jdpPainelPrincipal, BorderLayout.CENTER);
        this.add(barraStatus, BorderLayout.SOUTH);
        //this.add(jpsTelaPricipal, BorderLayout.SOUTH);
        setVisible(true);
        getContentPane().add(jdpPainelPrincipal, java.awt.BorderLayout.CENTER);

        jmbMenuPrincipal.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jmCadastro.setText("Cadastros");

        jmiFilial.setText("Filial");
        jmiFilial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFilialActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiFilial);

        jmiUsuario.setText("Cadastro de Usuário");
        jmiUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiUsuarioActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiUsuario);

        jmbMenuPrincipal.add(jmCadastro);

        jmMovimentos.setText("Operações");

        jmiExtracao.setText("Extração");
        jmiExtracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExtracaoActionPerformed(evt);
            }
        });
        jmMovimentos.add(jmiExtracao);

        jmiExtrator.setText("Extrator Dados Gerais");
        jmiExtrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExtratorActionPerformed(evt);
            }
        });
        jmMovimentos.add(jmiExtrator);

        jmiExtracaoManual.setText("Extração Individual");
        jmiExtracaoManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExtracaoManualActionPerformed(evt);
            }
        });
        jmMovimentos.add(jmiExtracaoManual);

        jmiRegistrosImportados.setText("Registros Importados");
        jmiRegistrosImportados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRegistrosImportadosActionPerformed(evt);
            }
        });
        jmMovimentos.add(jmiRegistrosImportados);

        jmbMenuPrincipal.add(jmMovimentos);

        jMFinancas.setText("Finanças");

        jMiConsultaContasReceber.setText("Consulta Contas Receber");
        jMiConsultaContasReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMiConsultaContasReceberActionPerformed(evt);
            }
        });
        jMFinancas.add(jMiConsultaContasReceber);

        jMenuItem2.setText("Consulta Negativações");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMFinancas.add(jMenuItem2);

        jmiConsultaCliente.setText("Consulta Cliente");
        jmiConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiConsultaClienteActionPerformed(evt);
            }
        });
        jMFinancas.add(jmiConsultaCliente);

        jMenuItem3.setText("Consulta RecebimentoApp");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMFinancas.add(jMenuItem3);

        jmbMenuPrincipal.add(jMFinancas);

        jmSistema.setText("Sistema");

        jmiAjuda.setText("Ajuda");
        jmiAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAjudaActionPerformed(evt);
            }
        });
        jmSistema.add(jmiAjuda);

        jMenuItem1.setText("Parametros");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jmSistema.add(jMenuItem1);

        JmiDiversos.setText("Diversos");
        JmiDiversos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmiDiversosActionPerformed(evt);
            }
        });
        jmSistema.add(JmiDiversos);

        jmiSair.setText("Sair");
        jmiSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSairActionPerformed(evt);
            }
        });
        jmSistema.add(jmiSair);

        jmbMenuPrincipal.add(jmSistema);

        setJMenuBar(jmbMenuPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiFilialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFilialActionPerformed

        if (telaFilial == null) {
            telaFilial = new TelaFilial();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaFilial.getSize();
            telaFilial.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaFilial.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaFilial);
            telaFilial.setVisible(true);
            // centralizarTela(telaFilial);
        }
        jdpPainelPrincipal.moveToFront(telaFilial);

    }//GEN-LAST:event_jmiFilialActionPerformed

    private void jmiExtratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExtratorActionPerformed
        if (telaExtrator == null) {
            telaExtrator = new TelaDadosGerais();
            telaExtrator.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);

            jdpPainelPrincipal.add(telaExtrator);
            telaExtrator.setVisible(true);

            // centralizarTela(telaExtrator);
        }
        //private void centralizaForm(JInternalFrame frame) {
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaExtrator.getSize();
        telaExtrator.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        //}
        jdpPainelPrincipal.moveToFront(telaExtrator);
        // centralizarTela(telaExtrator);
//telaExtrator.
    }//GEN-LAST:event_jmiExtratorActionPerformed

    private void jmiExtracaoManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExtracaoManualActionPerformed
        if (telaExtracaoManual == null) {
            telaExtracaoManual = new TelaExtracaoManual();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaExtracaoManual.getSize();
            telaExtracaoManual.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaExtracaoManual.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaExtracaoManual);
            telaExtracaoManual.setVisible(true);

            // centralizarTela(telaExtracaoManual);
        }
        jdpPainelPrincipal.moveToFront(telaExtracaoManual);
    }//GEN-LAST:event_jmiExtracaoManualActionPerformed

    private void jmiExtracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExtracaoActionPerformed
        if (telaExtracao == null) {
            telaExtracao = new TelaExtracao();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaExtracao.getSize();
            telaExtracao.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaExtracao.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaExtracao);
            telaExtracao.setVisible(true);

            // centralizarTela(telaExtracao);
        }
        jdpPainelPrincipal.moveToFront(telaExtracao);

    }//GEN-LAST:event_jmiExtracaoActionPerformed

    private void jmiUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiUsuarioActionPerformed
        if (telaUsuario == null) {
            telaUsuario = new TelaCadastroUsuario();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaUsuario.getSize();
            telaUsuario.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaUsuario.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaUsuario);
            telaUsuario.setVisible(true);

        }
        jdpPainelPrincipal.moveToFront(telaUsuario);
        // centralizarTela(telaUsuario);

    }//GEN-LAST:event_jmiUsuarioActionPerformed

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        try {
            excluirLoginUsuario();
            System.exit(0);
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAjudaActionPerformed
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://eletromoveismartinello.net.br/glpi/front/knowbaseitem.form.php?id=3"));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jmiAjudaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        if (telaParametros == null) {
            telaParametros = new TelaParametros();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaParametros.getSize();
            telaParametros.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaParametros.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaParametros);
            telaParametros.setVisible(true);

            // centralizarTela(telaExtracao);
        }
        jdpPainelPrincipal.moveToFront(telaParametros);        // TODO add your handling code here:

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jmiRegistrosImportadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRegistrosImportadosActionPerformed
        if (telaRelacionados == null) {
            telaRelacionados = new TelaRegistroImportado();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaRelacionados.getSize();
            telaRelacionados.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaRelacionados.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaRelacionados);
            telaRelacionados.setVisible(true);

            // centralizarTela(telaExtracao);
        }
        jdpPainelPrincipal.moveToFront(telaRelacionados);        // TODO add your handling code here:
    }//GEN-LAST:event_jmiRegistrosImportadosActionPerformed

    private void jMiConsultaContasReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMiConsultaContasReceberActionPerformed
        if (telaContasReceber == null) {
            telaContasReceber = new TelaContasReceber();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaContasReceber.getSize();
            telaContasReceber.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaContasReceber.addInternalFrameListener(this);          
            jdpPainelPrincipal.add(telaContasReceber);
            telaContasReceber.setVisible(true);           
        }
        jdpPainelPrincipal.moveToFront(telaContasReceber);       
    }//GEN-LAST:event_jMiConsultaContasReceberActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (telaConsultaNegativacao == null) {
            telaConsultaNegativacao = new TelaConsultaNegativacao();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaConsultaNegativacao.getSize();
            telaConsultaNegativacao.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaConsultaNegativacao.addInternalFrameListener(this);         
            jdpPainelPrincipal.add(telaConsultaNegativacao);
            telaConsultaNegativacao.setVisible(true);            
        }
        jdpPainelPrincipal.moveToFront(telaConsultaNegativacao);     
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jmiConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiConsultaClienteActionPerformed
        if (telaConsultaCliente == null) {
            telaConsultaCliente = new TelaConsultaCliente();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaConsultaCliente.getSize();
            telaConsultaCliente.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaConsultaCliente.addInternalFrameListener(this);
            jdpPainelPrincipal.add(telaConsultaCliente);
            telaConsultaCliente.setVisible(true);
        }
        jdpPainelPrincipal.moveToFront(telaConsultaCliente);
    }//GEN-LAST:event_jmiConsultaClienteActionPerformed

    private void JmiDiversosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmiDiversosActionPerformed
        if (telaDiversos == null) {
            telaDiversos = new TelaDiversos();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaDiversos.getSize();
            telaDiversos.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaDiversos.addInternalFrameListener(this);           
            jdpPainelPrincipal.add(telaDiversos);
            telaDiversos.setVisible(true);           
        }
        jdpPainelPrincipal.moveToFront(telaDiversos);      
    }//GEN-LAST:event_JmiDiversosActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       if (telaRecebimentoApp == null) {
            telaRecebimentoApp = new TelaRecebimentoApp();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaRecebimentoApp.getSize();
            telaRecebimentoApp.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaRecebimentoApp.addInternalFrameListener(this);
            jdpPainelPrincipal.add(telaRecebimentoApp);
            telaRecebimentoApp.setVisible(true);
        }
        jdpPainelPrincipal.moveToFront(telaRecebimentoApp);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
//public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
////        try {
////            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
////                if ("Nimbus".equals(info.getName())) {
////                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
////                    break;
////                }
////            }
////        } catch (ClassNotFoundException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (InstantiationException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (IllegalAccessException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        }
//        //</editor-fold>
//
//        try {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
//            System.out.println(e);
//        }
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaPrincipal().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JmiDiversos;
    private javax.swing.JMenu jMFinancas;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMiConsultaContasReceber;
    private javax.swing.JDesktopPane jdpPainelPrincipal;
    private javax.swing.JMenu jmCadastro;
    private javax.swing.JMenu jmMovimentos;
    private javax.swing.JMenu jmSistema;
    private javax.swing.JMenuBar jmbMenuPrincipal;
    private javax.swing.JMenuItem jmiAjuda;
    private javax.swing.JMenuItem jmiConsultaCliente;
    private javax.swing.JMenuItem jmiExtracao;
    private javax.swing.JMenuItem jmiExtracaoManual;
    private javax.swing.JMenuItem jmiExtrator;
    private javax.swing.JMenuItem jmiFilial;
    private javax.swing.JMenuItem jmiRegistrosImportados;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JMenuItem jmiUsuario;
    // End of variables declaration//GEN-END:variables
//    public void setposicao(){
//        Dimension d = .get getSize();
//    }

//    public static UsuarioModel getUsuario() {
//        return UsuarioController.find(usuario);
//    }
    public static void setUsuario(UsuarioModel usuario) {
        TelaPrincipal.usuario = usuario;
    }

    public void centralizarTela(JInternalFrame jInternalFrame) {
        jInternalFrame.setLocation((jdpPainelPrincipal.getToolkit().getScreenSize().width - jInternalFrame.getSize().width) / 2,
                (jdpPainelPrincipal.getToolkit().getScreenSize().height - jInternalFrame.getSize().height) / 2);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

        if (e.getInternalFrame() == telaFilial) {
            telaFilial = null;
        }
        if (e.getInternalFrame() == telaExtrator) {
            telaExtrator = null;
        }
        if (e.getInternalFrame() == telaExtracaoManual) {
            telaExtracaoManual = null;
        }
        if (e.getInternalFrame() == telaExtracao) {
            telaExtracao = null;
        }
        if (e.getInternalFrame() == telaUsuario) {
            telaUsuario = null;
        }
        if (e.getInternalFrame() == telaEmail) {
            telaEmail = null;
        }
        if (e.getInternalFrame() == telaParametros) {
            telaParametros = null;
        }
        if (e.getInternalFrame() == telaDiversos) {
            telaDiversos = null;
        }
        if (e.getInternalFrame() == telaRelacionados) {
            telaRelacionados = null;
        }
        if (e.getInternalFrame() == telaExtorno) {
            telaExtorno = null;
        }
        if (e.getInternalFrame() == telaContasReceber) {
            telaContasReceber = null;
        }
        if (e.getInternalFrame() == telaConsultaNegativacao) {
            telaConsultaNegativacao = null;
        }
        if (e.getInternalFrame() == telaConsultaCliente) {
            telaConsultaCliente = null;
        }
         if (e.getInternalFrame() == telaRecebimentoApp) {
            telaRecebimentoApp = null;
        }

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void excluirLoginUsuario() throws ErroSistemaException {
        usuarioControl.setUsuario(usuario);
        usuario.getLogins().clear();
        try {
            ProcessamentoController processamento = new ProcessamentoController();
            processamento.encerrarProcessos(usuario);
            usuarioControl.AtualizarLogin();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void Atualizalogin() {

        while (atualizaLogin == true) {
            try {
                usuarioControl.setUsuario(usuario);
                usuarioControl.AtualizaLogin();
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                barraStatus.setStatus(ex.getMessage());

            } catch (ErroSistemaException ex) {
                barraStatus.setStatus(ex.getMessage());
            }
        }
    }

    public void TelaEmail(ExtracaoTableModel extracaoModel) {
        if (telaEmail == null) {
            telaEmail = new TelaFinalizaRegistro(extracaoModel);
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaEmail.getSize();
            telaEmail.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaEmail.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaEmail);
            telaEmail.setVisible(true);

        }
        jdpPainelPrincipal.moveToFront(telaEmail);
    }

    private void carregarParametros() {
        try {
            parametroControl = new ParametrosControl();
            Lparametros = new ArrayList<>();
            Lparametros = parametroControl.buscarParametros();
            for (ParametrosModel parametroModel : Lparametros) {
                if (parametroModel.getParametro().equalsIgnoreCase("EMAIL")) {
                    email = parametroModel.getValor();
                }
                if (parametroModel.getParametro().equalsIgnoreCase("EMAIL COPIA")) {
                    emailPcopia = parametroModel.getValor();
                }
                if (parametroModel.getParametro().equalsIgnoreCase("SENHA EMAIL")) {
                    senhaEmail = parametroModel.getValor();
                }
                if (parametroModel.getParametro().equalsIgnoreCase("USUARIO EMAIL")) {
                    userEmail = parametroModel.getValor();
                }
                if (parametroModel.getParametro().equalsIgnoreCase("DIRETORIO NEGATIVADOR")) {
                    diretorio = parametroModel.getValor();
                }
                if (parametroModel.getParametro().equalsIgnoreCase("ASSINATURA DE EMAIL")) {
                    assEmail = parametroModel.getValor();
                }
            }
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        }
    }

    public void setTelaExtorno(ExtracaoTableModel extracaoModel) {
        if (telaExtorno == null) {
            telaExtorno = new TelaExtornoContasReceber(extracaoModel);
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaExtorno.getSize();
            telaExtorno.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
            telaExtorno.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaExtorno);
            telaExtorno.setVisible(true);

            // centralizarTela(telaExtracao);
        }
        jdpPainelPrincipal.moveToFront(telaExtorno);
    }

    private void validaMenu() {
        int[] usuPermite = {117, 212, 200, 378, 356};//Usuarios com permissão menu Operações Negativador
        permissao = "N";
        for (int CodUsu : usuPermite) {
            if (usuario.getIdUsuario() == CodUsu) {
                permissao = "S";
                break;
            }
        }
        if (permissao.equalsIgnoreCase("S")) {
            jmCadastro.setEnabled(true);
            jmCadastro.setVisible(true);
            jmMovimentos.setEnabled(true);
            jmMovimentos.setVisible(true);
            jmSistema.setEnabled(true);
            jmSistema.setVisible(true);
        } else {
            jmCadastro.setEnabled(false);
            jmCadastro.setVisible(false);
            jmMovimentos.setEnabled(false);
            jmMovimentos.setVisible(false);
            jmSistema.setEnabled(false);
            jmSistema.setVisible(false);
        }
        if (usuario.getIdUsuario() == 117) {
            JmiDiversos.setEnabled(true);
            JmiDiversos.setVisible(true);
        } else {
            JmiDiversos.setEnabled(false);
            JmiDiversos.setVisible(false);
        }
    }
}
