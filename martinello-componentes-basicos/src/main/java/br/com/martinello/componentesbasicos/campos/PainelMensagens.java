/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

/*
 * Decompiled with CFR 0_123.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PainelMensagens extends JPanel {

    private static final int DICAS_AVISO = 2;
    private static final int DICAS_ATENCAO = 1;
    private static final int DICAS_ERRO = 3;
    private static final int DICAS_ERRO_IMPEDITIVO = 5;
    private final int TAM_ICO_FECHARDICAS = 18;
    private final int ALT_ICO_FECHARDICAS = 16;
    private final int ALT_LB_TITULO = 36;
    private final int TAM_MARGEM = 4;
    public final ImageIcon ICO_FECHARDICAS = new ImageIcon(getClass().getClassLoader().getResource("ico_fechardicas.gif"));
    public final ImageIcon ICO_FECHARDICASDESABILITADO = new ImageIcon(getClass().getClassLoader().getResource("ico_fechardicasdesabilitado.gif"));
    private Box boxDicas;
    private Box boxTitulo;
    private JTextArea editDicas;
    private JLabel lbTitulo;
    private JLabel lbIcone;
    private JScrollPane scroll;

    public PainelMensagens() {
        this.setVisible(false);
        this.setLayout(new BorderLayout());
        this.boxTitulo = new Box(0);
        this.lbTitulo = new JLabel();
        this.lbTitulo.setOpaque(true);
        this.boxTitulo.add(this.lbTitulo);
        this.lbIcone = new JLabel(this.ICO_FECHARDICAS);
        this.lbIcone.addMouseListener(new EsconderDicas());
        this.boxTitulo.add(this.lbIcone);
        this.editDicas = new JTextArea();
        this.editDicas.setLineWrap(true);
        this.editDicas.setEditable(false);
        this.boxDicas = new Box(1);
        this.boxDicas.add(this.boxTitulo);
        this.add((Component) this.editDicas, "Center");
        this.add((Component) this.boxDicas, "North");
        this.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                PainelMensagens.this.esconderPainelDicas();
            }
        });
    }

    public void mostrarPainelMensagens(int tipoMensagem, String tituloMensagem, String mensagem, int x, int y, int tamMin) {
        Color corJanela = null;
        Color corTitulo = null;
        switch (tipoMensagem) {
            case 2: {
                corJanela = Color.YELLOW;
                corTitulo = Color.WHITE;
                break;
            }
            case 1: {
                corJanela = Color.BLUE;
                corTitulo = Color.WHITE;
                break;
            }
            case 3: {
                corJanela = Color.RED;
                corTitulo = Color.WHITE;
                break;
            }
            default: {
                corJanela = Color.BLACK;
                corTitulo = Color.WHITE;
            }
        }
        this.lbTitulo.setText("<HTML><B>" + tituloMensagem + "</B></HTML>");
        this.lbTitulo.setBackground(corJanela);
        this.lbTitulo.setForeground(corTitulo);
        this.boxTitulo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, corJanela));
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, corJanela));
        this.editDicas = new TextAreaCustomizada(mensagem, tamMin *= 2);
        this.editDicas.setLineWrap(true);
        this.editDicas.setWrapStyleWord(true);
        int altura = (int) this.editDicas.getPreferredSize().getHeight() + 36 + 8;
        Window janelaPai = SwingUtilities.getWindowAncestor(this);
        if (altura > janelaPai.getHeight()) {
            altura = janelaPai.getHeight() - 50;
        }
        setParametrosGUI((JComponent) this.lbTitulo, tamMin - 18, 16);
        setParametrosGUI((JComponent) this.editDicas, tamMin, altura - 36);
        Rectangle r = this.getRootPane().getBounds();
        Insets i = this.getRootPane().getInsets();
        if ((double) (x + tamMin) > r.getWidth()) {
            x = (int) r.getWidth() - tamMin - i.right - i.left - 8;
        }
        if ((double) (y + altura) > r.getHeight()) {
            y = (int) r.getHeight() - altura - i.top - i.bottom - 8;
        }
        this.scroll = new JScrollPane(this.editDicas);
        this.add(this.scroll);
        this.setBounds(x, y, tamMin, altura);
        this.setVisible(true);
    }

    public void esconderPainelDicas() {
        this.setVisible(false);
    }

    public static void setParametrosGUI(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
        c.setMinimumSize(c.getPreferredSize());
        c.setMaximumSize(c.getPreferredSize());
    }

    public static void setParametrosGUIPrefSize(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
    }

    class EsconderDicas extends MouseAdapter {

        EsconderDicas() {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            PainelMensagens.this.lbIcone.setIcon(PainelMensagens.this.ICO_FECHARDICASDESABILITADO);
            mudaCursorNoComponente((Component) e.getSource(), 12);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            PainelMensagens.this.lbIcone.setIcon(PainelMensagens.this.ICO_FECHARDICAS);
            mudaCursorNoComponente((Component) e.getSource(), 0);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            PainelMensagens.this.esconderPainelDicas();
        }
    }

    public static void mudaCursorNoComponente(Component pComp, int pTipoCursor) {
        try {
            pComp.setCursor(Cursor.getPredefinedCursor(pTipoCursor));
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public class TextAreaCustomizada
            extends JTextArea {

        protected int largura;

        public TextAreaCustomizada(String text, int largura) {
            super(text);
            this.largura = 0;
            this.largura = largura;
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension preferred = super.getPreferredSize();
            int newHeight = this.calcularAltura(this.getText(), this.largura);
            Insets insets = this.getInsets();
            preferred.setSize(this.largura, newHeight += insets.top + insets.bottom);
            return preferred;
        }

        protected int calcularAltura(String texto, double largura) {
            FontMetrics fm = this.getFontMetrics(this.getFont());
            String[] palavras = texto.split(" ");
            String frase = "";
            int qtdeLinhas = 1;
            String[] arrstring = palavras;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String palavra = arrstring[n2];
                if ((double) fm.stringWidth(frase = String.valueOf(frase) + palavra) == largura) {
                    ++qtdeLinhas;
                    frase = "";
                } else if ((double) fm.stringWidth(frase) > largura && !frase.contains(" ")) {
                    ++qtdeLinhas;
                    frase = "";
                } else if ((double) fm.stringWidth(frase) > largura) {
                    ++qtdeLinhas;
                    frase = palavra;
                }
                if (frase.length() > 0) {
                    frase = String.valueOf(frase) + " ";
                }
                ++n2;
            }
            int defaultHeight = fm.getHeight();
            int altura = qtdeLinhas * defaultHeight;
            return altura;
        }
    }

}
