/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

/*
 * Decompiled with CFR 0_123.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class PainelDicasModal
        extends JDialog {

    public static final int OK = 1;
    public static final int CANCELAR = 2;
    private static int opcaoSelecionada = 2;
    private ConteudoPainelDicas painelConteudo;

    public static int getOpcaoSelecionada() {
        return opcaoSelecionada;
    }

//    public PainelDicasModal(int tipo, String titulo, String texto, int x, int y, int tamMin) {
//        super(UtilitariosGUI.tentaObterJanelaPrincipal());
//        this.setPainelConteudo(new ConteudoPainelDicas(tipo, titulo, texto, x, y, tamMin));
//        this.adicionaListeners();
//        this.getContentPane().add(this.getPainelConteudo());
//        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
//    }
    public PainelDicasModal(Dialog parent, int tipo, String titulo, String texto, int x, int y, int tamMin) {
        super(parent);
        this.setPainelConteudo(new ConteudoPainelDicas(tipo, titulo, texto, x, y, tamMin));
        this.adicionaListeners();
        this.getContentPane().add(this.getPainelConteudo());
        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
    }

    public PainelDicasModal(Frame parent, int tipo, String titulo, String texto, int x, int y, int tamMin) {
        super(parent);
        this.setPainelConteudo(new ConteudoPainelDicas(tipo, titulo, texto, x, y, tamMin));
        this.adicionaListeners();
        this.getContentPane().add(this.getPainelConteudo());
        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
    }

    private void adicionaListeners() {
        this.getPainelConteudo().getBtnCancel().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PainelDicasModal.access$0(2);
                PainelDicasModal.this.esconderPainelDicas();
            }
        });
        this.getPainelConteudo().getBtnFechar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PainelDicasModal.access$0(2);
                PainelDicasModal.this.esconderPainelDicas();
            }
        });
        this.getPainelConteudo().getBtnOk().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PainelDicasModal.access$0(1);
                PainelDicasModal.this.esconderPainelDicas();
            }
        });
    }

    public void exibe() {
        opcaoSelecionada = 2;
        this.setModal(true);
        this.setResizable(false);
        this.setUndecorated(true);
        this.pack();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                PainelDicasModal.this.getPainelConteudo().getBtnOk().requestFocus();
            }
        });
        this.setVisible(true);
    }

    private void posicionaDicas(int alt, int x, int y, int tamMin) {
        JFrame janelaPrincipal = tentaObterJanelaPrincipal();
        Rectangle r = janelaPrincipal.getRootPane().getBounds();
        Insets i = janelaPrincipal.getRootPane().getInsets();
        if ((double) (x + tamMin) > r.getWidth()) {
            x = (int) r.getWidth() - tamMin - i.right - i.left - 50;
        }
        if ((double) (y + alt) > r.getHeight() - 60.0) {
            y = (int) r.getHeight() - alt - i.top - i.bottom - 75;
        }
        this.setLocation(x, y);
    }

    public static JFrame tentaObterJanelaPrincipal() {
        Frame[] frames = Frame.getFrames();
        JFrame principal = null;
        int i = 0;
        while (i < frames.length) {
            System.out.println(frames[i].getName());
            if (frames[i] instanceof JFrame && frames[i].getName().equals("NewJFrame")) {
                principal = (JFrame) frames[i];
            }
            ++i;
        }
        if (principal == null) {
        }
        return principal;
    }

    public void esconderPainelDicas() {
        this.setVisible(false);
    }

    public void setPainelConteudo(ConteudoPainelDicas painelConteudo) {
        this.painelConteudo = painelConteudo;
    }

    public ConteudoPainelDicas getPainelConteudo() {
        return this.painelConteudo;
    }

    static /* synthetic */ void access$0(int n) {
        opcaoSelecionada = n;
    }

}
