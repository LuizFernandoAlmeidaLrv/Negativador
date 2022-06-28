/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

import br.com.martinello.componentesbasicos.ConstantesGlobais;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.IllegalComponentStateException;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author Sidnei
 */
public abstract class GerenciadorGUI {

    private float alinhamento;
    private int eixo;
    private byte posicaoRotulo;
    public static final PainelMensagens painelDicas = new PainelMensagens();
    private static JPanel glassPane = null;

    static {
        glassPane = new JPanel();
        glassPane.setLayout(null);
        glassPane.setBackground(ConstantesGlobais.COR_BRANCO);
        glassPane.setOpaque(false);
        glassPane.add(painelDicas);
        JFrame framePrincipal = tentaObterJanelaPrincipal();
        if (framePrincipal != null) {
            framePrincipal.getRootPane().setGlassPane(glassPane);
            framePrincipal.getRootPane().getGlassPane().setVisible(true);
        }
    }

    public static JFrame tentaObterJanelaPrincipal() {
        Frame[] frames = Frame.getFrames();
        JFrame principal = null;
        int i = 0;
        while (i < frames.length) {
            if (frames[i] instanceof JFrame && frames[i].getName().equals("TELA_PRINCIPAL")) {
                principal = (JFrame) frames[i];
            }
            ++i;
        }
        if (principal == null) {
        }
        return principal;
    }

    public static void exibirPainelMensagens(int tipo, String titulo, String texto, Component ref, int tam) {
        Point p = obtemLocalizacaoPainelDicas(ref);
        if (p == null) {
            return;
        }
        SwingUtilities.convertPointFromScreen(p, glassPane);
        painelDicas.mostrarPainelMensagens(tipo, titulo, texto, p.x + 20, p.y + 8, tam);
    }

    public static void exibirPainelMensagens(Window janela, int tipo, String titulo, String texto, Component ref, int tam) {
        PainelMensagens pnlDicasTemp = new PainelMensagens();
        JPanel novoGlassPane = new JPanel();
        novoGlassPane.setLayout(null);
        novoGlassPane.setBackground(ConstantesGlobais.COR_BRANCO);
        novoGlassPane.setOpaque(false);
        novoGlassPane.add(pnlDicasTemp);
        Point p = obtemLocalizacaoPainelDicas(ref);
        if (p == null) {
            return;
        }
        if (janela instanceof JDialog) {
            ((JDialog) janela).getRootPane().setGlassPane(novoGlassPane);
            ((JDialog) janela).getRootPane().getGlassPane().setVisible(true);
            SwingUtilities.convertPointFromScreen(p, ((JDialog) janela).getGlassPane());
        } else {
            ((JFrame) janela).getRootPane().setGlassPane(novoGlassPane);
            ((JFrame) janela).getRootPane().getGlassPane().setVisible(true);
            SwingUtilities.convertPointFromScreen(p, ((JFrame) janela).getGlassPane());
        }
        pnlDicasTemp.mostrarPainelMensagens(tipo, titulo, texto, p.x + 20, p.y + 8, tam);
    }

    private static Point obtemLocalizacaoPainelDicas(Component ref) {
        Point p = null;
        try {
            p = ref.getLocationOnScreen();
        } catch (IllegalComponentStateException e) {
            //LogPPGD.erro((String)"O painel de dicas n\u00e3o pode ser exibido porq o botao ainda n\u00e3o est\u00e1 visivel!!");
        }
        return p;
    }

    public static JPanel getGlassPane() {
        return glassPane;
    }

    public static void exibirPainelMensagensModal(Window parent, int tipo, String titulo, String texto, Component ref, int tam) {
        Point p = obtemLocalizacaoPainelDicas(ref);
        if (p == null) {
            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
            Point centralizado = new Point((tela.width - ref.getWidth()) / 2, (tela.height - ref.getHeight()) / 2);
            new PainelMensagensModal(tipo, titulo, texto, centralizado.x + 20, centralizado.y + 8, tam).exibe();
        } else {
            p.x += ref.getWidth();
            if (parent instanceof Dialog) {
                new PainelMensagensModal((Dialog) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam).exibe();
            } else if (parent instanceof Frame) {
                new PainelMensagensModal((Frame) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam).exibe();
            }
        }
    }

    public static void exibirPainelMensagensModal(Window parent, int tipo, String titulo, String texto, JComponent ref, int tam, ActionListener preActionOk) {
        Point p = GerenciadorGUI.obtemLocalizacaoPainelDicas(ref);
        if (p == null) {
            return;
        }
        if (p == null) {
            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
            Point centralizado = new Point((tela.width - ref.getWidth()) / 2, (tela.height - ref.getHeight()) / 2);
            PainelMensagensModal painel = new PainelMensagensModal(tipo, titulo, texto, centralizado.x + 20, centralizado.y + 8, tam);
            GerenciadorGUI.preparaModalOk(preActionOk, painel);
            painel.exibe();
        } else {
            p.x += ref.getWidth();
            if (parent instanceof Dialog) {
                PainelMensagensModal painel = new PainelMensagensModal((Dialog) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam);
                GerenciadorGUI.preparaModalOk(preActionOk, painel);
                painel.exibe();
            } else if (parent instanceof Frame) {
                PainelMensagensModal painel = new PainelMensagensModal((Frame) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam);
                GerenciadorGUI.preparaModalOk(preActionOk, painel);
                painel.exibe();
            } else {
                //LogPPGD.erro((String) "O parent passado para o PainelDicas n\u00e3o \u00e9 nem Dialog nem Frame!");
            }
        }
    }

    private static void preparaModalOk(ActionListener preActionOk, PainelMensagensModal painel) {
        painel.getPainelConteudo().getBtnOk().addActionListener(preActionOk);
        painel.getPainelConteudo().getBtnFechar().addActionListener(preActionOk);
//        painel.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                ActionListener.this.actionPerformed(null);
//            }
//        });
    }

    public static void exibirPainelMensagensModal(Window parent, int tipo, String titulo, String texto, Component ref, int tam, ActionListener preActionOk, ActionListener preActionCancel) {
        Point p = GerenciadorGUI.obtemLocalizacaoPainelDicas(ref);
        if (p == null) {
            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
            Point centralizado = new Point((tela.width - ref.getWidth()) / 2, (tela.height - ref.getHeight()) / 2);
            PainelMensagensModal painel = new PainelMensagensModal(tipo, titulo, texto, centralizado.x + 20, centralizado.y + 8, tam);
            GerenciadorGUI.preparaModalOk(preActionOk, painel);
            painel.exibe();
        } else {
            p.x += ref.getWidth();
            if (parent instanceof Dialog) {
                PainelMensagensModal painel = new PainelMensagensModal((Dialog) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam);
                GerenciadorGUI.preparaModalOkCancel(preActionOk, preActionCancel, painel);
                painel.exibe();
            } else if (parent instanceof Frame) {
                PainelMensagensModal painel = new PainelMensagensModal((Frame) parent, tipo, titulo, texto, p.x + 20, p.y + 8, tam);
                GerenciadorGUI.preparaModalOkCancel(preActionOk, preActionCancel, painel);
                painel.exibe();
            } else {
                //LogPPGD.erro((String) "O parent passado para o PainelDicas n\u00e3o \u00e9 nem Dialog nem Frame!");
            }
        }
    }

    private static void preparaModalOkCancel(ActionListener preActionOk, ActionListener preActionCancel, PainelMensagensModal painel) {
        painel.getPainelConteudo().getBtnOk().addActionListener(preActionOk);
        painel.getPainelConteudo().getBtnFechar().addActionListener(preActionCancel);
        painel.getPainelConteudo().getBtnCancel().addActionListener(preActionCancel);

//        painel.addWindowListener(new WindowAdapter() {
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//                ActionListener.this.actionPerformed(null);
//            }
//        });
    }

    public static void esconderPainelDicas() {
        painelDicas.esconderPainelDicas();
    }

    public static void mudaCursor(Component ref, int pTipoCursor) {
        try {
            ((JFrame) SwingUtilities.getRoot(ref)).setCursor(Cursor.getPredefinedCursor(pTipoCursor));
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static void mudaCursorNoComponente(Component pComp, int pTipoCursor) {
        try {
            pComp.setCursor(Cursor.getPredefinedCursor(pTipoCursor));
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public static void setParametrosGUI(JComponent c, LayoutManager l) {
        GerenciadorGUI.setParametrosGUI(c, l, null);
    }

    public static void setParametrosGUI(JComponent c, LayoutManager l, Border b) {
        if (l != null) {
            c.setLayout(l);
        }
        if (b != null) {
            c.setBorder(b);
        }
    }

    public static void setParametrosGUI(JComponent c, Color bk) {
        GerenciadorGUI.setParametrosGUI(c, null, null, bk);
    }

    public static void setParametrosGUI(JComponent c, Font f, Color fr, Color bk) {
        c.setOpaque(true);
        if (f != null) {
            c.setFont(f);
        }
        if (bk != null) {
            c.setBackground(bk);
        }
        if (fr != null) {
            c.setForeground(fr);
        }
    }

    public static void setParametrosGUI(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
        c.setMinimumSize(c.getPreferredSize());
        c.setMaximumSize(c.getPreferredSize());
    }

    public static void setParametrosGUIPrefSize(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
    }
}
