/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

/*
 * Decompiled with CFR 0_123.
 */
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PainelMensagensModal extends JDialog {

    public static final int OK = 1;
    public static final int CANCELAR = 2;
    private static int opcaoSelecionada = 2;
    private ConteudoPainelMensagens painelConteudo;

    public static int getOpcaoSelecionada() {
        return opcaoSelecionada;
    }

    public PainelMensagensModal(int tipo, String titulo, String texto, int x, int y, int tamMin) {
        super(GerenciadorGUI.tentaObterJanelaPrincipal());
        this.setPainelConteudo(new ConteudoPainelMensagens(tipo, titulo, texto, x, y, tamMin));
        this.adicionaListeners();
        this.getContentPane().add(this.getPainelConteudo());
        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
    }

    public PainelMensagensModal(Dialog parent, int tipo, String titulo, String texto, int x, int y, int tamMin) {
        super(parent);
        this.setPainelConteudo(new ConteudoPainelMensagens(tipo, titulo, texto, x, y, tamMin));
        this.adicionaListeners();
        this.getContentPane().add(this.getPainelConteudo());
        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
    }

    public PainelMensagensModal(Frame parent, int tipo, String titulo, String texto, int x, int y, int tamMin) {
        super(parent);
        this.setPainelConteudo(new ConteudoPainelMensagens(tipo, titulo, texto, x, y, tamMin));
        this.adicionaListeners();
        this.getContentPane().add(this.getPainelConteudo());
        this.posicionaDicas(this.getPainelConteudo().getAltura(), x, y, tamMin);
    }

    private void adicionaListeners() {
        this.getPainelConteudo().getBtnCancel().addActionListener((ActionEvent e) -> {
            PainelMensagensModal.access$0(2);
            PainelMensagensModal.this.esconderPainelDicas();
        });
        this.getPainelConteudo().getBtnFechar().addActionListener((ActionEvent e) -> {
            PainelMensagensModal.access$0(2);
            PainelMensagensModal.this.esconderPainelDicas();
        });
        this.getPainelConteudo().getBtnOk().addActionListener((ActionEvent e) -> {
            PainelMensagensModal.access$0(1);
            PainelMensagensModal.this.esconderPainelDicas();
        });
    }

    public void exibe() {
        opcaoSelecionada = 2;
        this.setModal(true);
        this.setResizable(false);
        this.setUndecorated(true);
        this.pack();
        SwingUtilities.invokeLater(() -> {
            PainelMensagensModal.this.getPainelConteudo().getBtnOk().requestFocus();
        });
        this.setVisible(true);
    }

    private void posicionaDicas(int alt, int x, int y, int tamMin) {
        JFrame janelaPrincipal = GerenciadorGUI.tentaObterJanelaPrincipal();
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

    public void esconderPainelDicas() {
        this.setVisible(false);
    }

    public void setPainelConteudo(ConteudoPainelMensagens painelConteudo) {
        this.painelConteudo = painelConteudo;
    }

    public ConteudoPainelMensagens getPainelConteudo() {
        return this.painelConteudo;
    }

    static /* synthetic */ void access$0(int n) {
        opcaoSelecionada = n;
    }

}
