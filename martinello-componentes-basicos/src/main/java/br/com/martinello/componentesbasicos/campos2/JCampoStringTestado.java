/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos2;

/**
 *
 * @author Sidnei
 */
import br.com.martinello.componentesbasicos.campos.PainelBotao;
import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.LimitaNroCaracteres;
import br.com.martinello.componentesbasicos.Rotulo;
import br.com.martinello.componentesbasicos.campos.BotaoMensagem;
import br.com.martinello.componentesbasicos.novo.PainelDicas;
import br.com.martinello.util.Utilitarios;
import com.towel.el.annotation.Resolvable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author Sidnei
 */
//extends JComponent
public class JCampoStringTestado extends JComponent implements Campo, FocusListener, ActionListener {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;
    protected JFormattedTextField jFormattedTextField;
    private BotaoMensagem buttonMensagem = new BotaoMensagem();
    private static int CONST_CALC_TAMX_PAINELDICAS = 3;
    private static int TIPO_NORMAL = 0;
    private static int TIPO_ERRO = 3;
    private static int TIPO_AVISO = 2;
    private static int TIPO_ATENCAO = 1;
    private int tipoMensagem = TIPO_NORMAL;

    public JCampoStringTestado() {
        this(null, null, null, true);
        jFormattedTextField = new JFormattedTextField();
        jFormattedTextField.addFocusListener(this);

        bordaOriginal = jFormattedTextField.getBorder();
        Utilitarios.considerarEnterComoTab(jFormattedTextField);
    }

    public JCampoStringTestado(Class tabela, String campo, String rotulo) {
        this(tabela, campo, rotulo, false);
    }

    public JCampoStringTestado(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio) {
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;

        this.removeAll();
        this.setLayout(new BorderLayout());
        if (this.jFormattedTextField == null) {
            this.jFormattedTextField = new JFormattedTextField();
            this.jFormattedTextField.setColumns(10);

        }
        this.add((Component) this.jFormattedTextField, "Center");
        this.add((Component) new PainelBotao(buttonMensagem), "East");

        buttonMensagem.addActionListener(this);
        buttonMensagem.setVisible(false);

        jFormattedTextField.addFocusListener(this);

        bordaOriginal = jFormattedTextField.getBorder();
        bordaOriginal = javax.swing.BorderFactory.createLineBorder(Color.GRAY);
        jFormattedTextField.setBorder(bordaOriginal);
        Utilitarios.considerarEnterComoTab(jFormattedTextField);

        try {
            if (tabela != null) {
                if (tabela.getDeclaredField(campo).getType() != String.class && tabela.getDeclaredField(campo).getType() != int.class && tabela.getDeclaredField(campo).getType() != Date.class) {
                    JoinColumn joinColumn = tabela.getDeclaredField(campo).getAnnotation(JoinColumn.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !joinColumn.nullable();
                    dica = r.colName();
                    jFormattedTextField.setToolTipText(dica);
                } else {
                    Column coluna = tabela.getDeclaredField(campo).getAnnotation(Column.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !coluna.nullable();
                    dica = r.colName();
                    jFormattedTextField.setToolTipText(dica);

                    if (coluna.length() > 0) {
                        jFormattedTextField.setDocument(new LimitaNroCaracteres(coluna.length()));
                    }
                }
            }

        } catch (NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(jFormattedTextField, "Erro ao carregar configuração do campo " + campo, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void limpar() {
        jFormattedTextField.setText("");
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (jFormattedTextField.getText().trim().length() > 0) {
                buttonMensagem.setVisible(false);
                return false;
            } else {
                buttonMensagem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ico_erro.gif")));
                buttonMensagem.setVisible(true);
                tipoMensagem = TIPO_ERRO;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
        //setJlRotulo(jlRotulo);
    }

    public void setString(String valor) {
        jFormattedTextField.setText(valor);
    }

    public String getString() {
        return jFormattedTextField.getText().trim();
    }

    public void setInt(int valor) {
        jFormattedTextField.setText("" + valor);
    }

    public int getInt() {
        return Integer.parseInt(jFormattedTextField.getText().trim().length() == 0 ? "0" : jFormattedTextField.getText().trim());
    }

    public void setDate(Date data, String formato) {
        jFormattedTextField.setText(Utilitarios.converteDataString(data, formato));
    }

    public Date getDate(String formato) {
        return Utilitarios.stringParaData(getString(), formato);
    }

    @Override
    public void focusGained(FocusEvent e) {
        jFormattedTextField.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
        //eVazio();
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                if (obrigatorio) {
                    rRotulo.setText("<html><body>" + descricaoRotulo + "<FONT COLOR='red'><b>*</b></FONT>:</body></html>");
                } else {
                    rRotulo.setText("<html><body>" + descricaoRotulo + ":</body></html>");
                }
            });
        }
    }

    public void setText(String texto) {
        jFormattedTextField.setText(texto);
    }

    public String getText() {
        return jFormattedTextField.getText();
    }

    public void setEditable(boolean editable) {
        jFormattedTextField.setEditable(editable);
    }

    public void selectAll() {
        jFormattedTextField.selectAll();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int tamMax = "Campo Obrigatório Não Preenchido.".length() * CONST_CALC_TAMX_PAINELDICAS;
        int n = tamMax = tamMax > 90 ? tamMax : 90;

        Window parent = SwingUtilities.getWindowAncestor(buttonMensagem);

        //exibirPainelDicasModal((Window) parent, (int) 3, (String) "Título", (String) "Mensagem", (JComponent) jCampoString1, (int) 50);
        exibirPainelDicas((Window) SwingUtilities.getRoot(buttonMensagem), tipoMensagem, "", "Campo Obrigatório Não Preenchido.", buttonMensagem, tamMax);

    }

    public static void exibirPainelDicas(Window janela, int tipo, String titulo, String texto, Component ref, int tam) {
        PainelDicas pnlDicasTemp = new PainelDicas();
        JPanel novoGlassPane = new JPanel();
        novoGlassPane.setLayout(null);
        novoGlassPane.setBackground(Color.WHITE);
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
        pnlDicasTemp.mostrarPainelDicas(tipo, titulo, texto, p.x + 20, p.y + 8, tam);
    }

    private static Point obtemLocalizacaoPainelDicas(Component ref) {
        Point p = null;
        try {
            p = ref.getLocationOnScreen();
        } catch (IllegalComponentStateException e) {
        }
        return p;
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }

}
