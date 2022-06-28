/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  serpro.ppgd.negocio.util.LogPPGD
 *  serpro.ppgd.negocio.util.UtilitariosString
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class ConteudoPainelDicas extends JPanel {

    private static final int DICAS_AVISO = 2;
    private static final int DICAS_ATENCAO = 1;
    private static final int DICAS_ERRO = 3;
    private static final int DICAS_ERRO_IMPEDITIVO = 5;
    private static final int DICAS_ERRO_OK_CANCELAR = 4;
    public static final int TAM_ICO_FECHARDICAS = 18;
    public static final int ALT_ICO_FECHARDICAS = 16;
    public static final int ALT_LB_TITULO = 24;
    public static final int TAM_MARGEM = 5;
    public final ImageIcon ICO_FECHARDICAS = new ImageIcon(getClass().getClassLoader().getResource("ico_fechardicas.gif"));
    public final ImageIcon ICO_FECHARDICASDESABILITADO = new ImageIcon(getClass().getClassLoader().getResource("ico_fechardicasdesabilitado.gif"));
    private Box boxDicas;
    private Box boxTitulo;
    private JEditorPane editDicas;
    private JLabel lbTitulo;
    private JButton btnFechar;
    private JPanel painelBotoes = new JPanel();
    private ButtonPPGD btnOk = new ButtonPPGD("Ok");
    private ButtonPPGD btnCancel = new ButtonPPGD("Cancelar");
    private int altura;

    public ConteudoPainelDicas(int tipo, String titulo, String texto, int x, int y, int tamMin) {
        this.buildPainel(tipo, titulo, texto, x, y, tamMin);
    }

    private void buildPainel(int tipo, String titulo, String texto, int x, int y, int tamMin) {
        this.aplicaBorda();
        String fonteHTML = this.aplicaFormatacao(tipo, titulo);
        this.setAltura(this.aplicaTexto(fonteHTML, texto, titulo, tamMin));
        if (5 == tipo) {
            this.aplicaBotaoOkSomente();
        } else if (4 == tipo) {
            this.aplicaBotoesOkCancelar();
        }
    }

    private void aplicaBorda() {
        this.setLayout(new BorderLayout());
        this.boxTitulo = new Box(0);
        this.lbTitulo = new JLabel();
        this.lbTitulo.setOpaque(true);
        this.boxTitulo.setBorder(BorderFactory.createEmptyBorder());
        this.boxTitulo.add(this.lbTitulo);
        this.setBtnFechar(new JButton(this.ICO_FECHARDICAS));
        this.getBtnFechar().setToolTipText("Fechar");
        this.getBtnFechar().setBackground(Color.white);
        int wFechar = this.ICO_FECHARDICAS.getIconWidth() + 4;
        int hFechar = this.ICO_FECHARDICAS.getIconHeight() + 4;
        this.getBtnFechar().setSize(wFechar, hFechar);
        this.getBtnFechar().setPreferredSize(new Dimension(wFechar, hFechar));
        this.getBtnFechar().setMinimumSize(new Dimension(wFechar, hFechar));
        this.getBtnFechar().setMaximumSize(new Dimension(wFechar, hFechar));
        this.getBtnFechar().addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                ConteudoPainelDicas.this.getBtnFechar().setIcon(ConteudoPainelDicas.this.ICO_FECHARDICASDESABILITADO);
            }

            public void mouseExited(MouseEvent e) {
                ConteudoPainelDicas.this.getBtnFechar().setIcon(ConteudoPainelDicas.this.ICO_FECHARDICAS);
            }
        });
        this.boxTitulo.add(this.getBtnFechar());
        this.editDicas = new JEditorPane("text/html", " ");
        this.editDicas.setEditable(false);
        this.editDicas.addHyperlinkListener(new ExecutaHiperlinkDicas());
        this.boxDicas = new Box(1);
        this.boxDicas.add(this.boxTitulo);
        this.add((Component) this.editDicas, "Center");
        this.add((Component) this.boxDicas, "North");
    }

    private void aplicaBotaoOkSomente() {
        JPanel painelBotoes = new JPanel();
        this.btnOk.setBackground(Color.white);
        painelBotoes.setBackground(Color.white);
        painelBotoes.add(this.btnOk);
        this.add((Component) painelBotoes, "South");
    }

    private void aplicaBotoesOkCancelar() {
        this.btnOk.setBackground(Color.white);
        this.btnCancel.setBackground(Color.white);
        this.painelBotoes.setBackground(Color.white);
        if (System.getProperty("os.name").startsWith("Mac")) {
            this.painelBotoes.add(this.btnCancel);
            this.painelBotoes.add(this.btnOk);
        } else {
            this.painelBotoes.add(this.btnOk);
            this.painelBotoes.add(this.btnCancel);
        }
        this.add((Component) this.painelBotoes, "South");
    }

    private int aplicaTexto(String fonteHTML, String texto, String titulo, int tamMin) {
        texto = expandeStringHTML((String) texto, (String) fonteHTML, (int) 0);
        this.editDicas.setText(texto);
        FontMetrics fm = this.getFontMetrics(this.editDicas.getFont());
        int tamTitulo = SwingUtilities.computeStringWidth(fm, titulo);
        tamMin = Math.max(tamMin, tamTitulo + 5) + 18;
        ParserCallbackLocal callback = new ParserCallbackLocal(fm, tamMin - 4, texto.length());
        HtmlParser p = new HtmlParser(texto, callback);
        int nrLinhas = callback.getNrLinhas();
        int ajuste = 0;
        if (nrLinhas < 3) {
            ajuste = 3;
        } else if (nrLinhas > 35) {
            tamTitulo *= 3;
            nrLinhas = 35;
        }
        tamMin = Math.max(tamMin, tamTitulo + 5) + 18;
        if (tamTitulo <= 0) {
            tamTitulo = 10;
        }
        int aumentoProporcionalLargura = 4000 / tamTitulo;
        int alt = (nrLinhas + 1) * (fm.getHeight() + ajuste) + 24 + this.painelBotoes.getPreferredSize().height + 10 + aumentoProporcionalLargura;
        setParametrosGUI((JComponent) this.lbTitulo, tamMin - 18, 16);
        setParametrosGUI((JComponent) this.editDicas, tamMin, alt - this.painelBotoes.getPreferredSize().height);
        return alt;
    }

    public static void setParametrosGUI(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
        c.setMinimumSize(c.getPreferredSize());
        c.setMaximumSize(c.getPreferredSize());
    }

    public static String expandeStringHTML(String texto, String tag, int nrMaxColuna) {
        String saida = "";
        if (tag == null) {
            tag = "";
        }
        if (texto != null && texto.length() > 0) {
            switch (texto.charAt(texto.length() - 1)) {
                case '!':
                case '.':
                case '?': {
                    break;
                }
                case '^': {
                    texto = texto.substring(0, texto.length() - 1);
                    break;
                }
                default: {
                    texto = texto + '.';
                }
            }
            if (nrMaxColuna > 0) {
                StringTokenizer sToken = new StringTokenizer(texto);
                String lin = "";
                while (sToken.hasMoreTokens()) {
                    String token = sToken.nextToken();
                    if (lin.length() + token.length() >= nrMaxColuna) {
                        saida = saida + lin + "<br>";
                        lin = "";
                    }
                    lin = lin + token + " ";
                }
                saida = saida + lin;
            } else {
                saida = texto;
            }
        } else {
            saida = "";
        }
        int pos = saida.indexOf("<html><body>");
        if (pos == -1) {
            saida = "<html><body>" + tag + saida;
            saida = saida + " </body></html>";
        } else {
            saida = "<html><body>" + tag + saida.substring(pos += "<html><body>".length());
        }
        return saida;
    }

    private String aplicaFormatacao(int tipo, String titulo) {
        Color c = null;
        Color corTitulo = null;
        String fonteHTML = null;
        switch (tipo) {
            case 2: {
                c = Color.YELLOW;
                corTitulo = Color.WHITE;
                break;
            }
            case 1: {
                c = Color.BLUE;
                corTitulo = Color.WHITE;
                break;
            }
            case 3: {
                c = Color.RED;
                corTitulo = Color.WHITE;
                break;
            }
            case 5: {
                c = Color.RED;
                corTitulo = Color.WHITE;
                break;
            }
            case 4: {
                c = Color.RED;
                corTitulo = Color.WHITE;
                break;
            }
            default: {
                c = ConstantesGlobaisGUI.COR_BORDA_PAINEL_DICAS_DEFAULT;
                corTitulo = ConstantesGlobaisGUI.COR_TITULO_PAINEL_DICAS_DEFAULT;
            }
        }
        fonteHTML = "<font size=\"3\">";
        this.lbTitulo.setText("<HTML><B>" + titulo + "</B></HTML>");
        this.lbTitulo.setBackground(c);
        this.boxTitulo.setBackground(c);
        this.setBackground(c);
        this.lbTitulo.setForeground(corTitulo);
        if (this.btnOk != null) {
            this.btnOk.setForeground(Color.BLACK);
        }
        if (this.btnCancel != null) {
            this.btnCancel.setForeground(Color.BLACK);
        }
        this.editDicas.setForeground(c);
        this.boxTitulo.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, c));
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, c));
        return fonteHTML;
    }

    public void setBtnOk(ButtonPPGD btnOk) {
        this.btnOk = btnOk;
    }

    public ButtonPPGD getBtnOk() {
        return this.btnOk;
    }

    public void setBtnCancel(ButtonPPGD btnCancel) {
        this.btnCancel = btnCancel;
    }

    public ButtonPPGD getBtnCancel() {
        return this.btnCancel;
    }

    public void setBtnFechar(JButton btnFechar) {
        this.btnFechar = btnFechar;
    }

    public JButton getBtnFechar() {
        return this.btnFechar;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAltura() {
        return this.altura;
    }

    class ParserCallbackLocal
            extends HTMLEditorKit.ParserCallback {

        private FontMetrics fm;
        private int tamMax;
        private int posFinal;
        private String htmlTexto;
        private int nrLinhasHtml;

        public ParserCallbackLocal(FontMetrics fm, int tamMax, int posFinal) {
            this.fm = fm;
            this.tamMax = tamMax;
            this.posFinal = posFinal;
            this.nrLinhasHtml = 0;
        }

        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
            if (t.breaksFlow() || pos >= this.posFinal) {
                this.nrLinhasHtml += this.calculaNrLinhas(this.htmlTexto);
                this.htmlTexto = null;
            }
        }

        public void handleEndTag(HTML.Tag t, int pos) {
            if (t.toString().equals("html")) {
                this.nrLinhasHtml += this.calculaNrLinhas(this.htmlTexto);
            }
        }

        public void handleText(char[] data, int pos) {
            this.htmlTexto = this.htmlTexto == null ? String.copyValueOf(data) : String.valueOf(this.htmlTexto) + String.copyValueOf(data);
        }

        public int getNrLinhas() {
            return this.nrLinhasHtml;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private int calculaNrLinhas(String texto) {
            String s = "";
            String lin = "";
            int count = 0;
            if (texto == null) {
                return 0;
            }
            StringTokenizer sToken = new StringTokenizer(texto);
            if (sToken.countTokens() == 0) {
                return 0;
            }
            if (sToken.countTokens() != 1) {
                return 1;
            } else {
                String token = sToken.nextToken();
                s = String.valueOf(lin) + token + " ";
                int tam = SwingUtilities.computeStringWidth(this.fm, s);
                if (tam >= this.tamMax) {
                    lin = "";
                    ++count;
                }
                lin = String.valueOf(lin) + token + " ";

                while (sToken.hasMoreTokens()) {

                }
            }

            if (s == "") {
                return count;
            }
            ++count;
            return count;
        }
    }

}
