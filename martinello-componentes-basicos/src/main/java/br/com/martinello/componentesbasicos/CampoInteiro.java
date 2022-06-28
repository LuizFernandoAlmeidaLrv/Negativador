package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class CampoInteiro extends JTextField implements Campo, FocusListener {

    private Border bordaOriginal = null;
    private boolean obrigatorio;
    private String descricaoRotulo;
    private Rotulo rRotulo;
    private int valorPadrao = 0;
    protected int qtdMaxCaracteres = 999;

    public CampoInteiro() {
        this(null, null, null);
    }

    public CampoInteiro(Class tabela, String campo, String descricaoRotulo) {
        this(tabela, campo, descricaoRotulo, false, null);
    }

    public CampoInteiro(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio, Rotulo rRotulo) {
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;
        this.rRotulo = rRotulo;

        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                setComponenteRotulo(rRotulo);
            });
        }

        addFocusListener(this);

        bordaOriginal = this.getBorder();

        setFont(ConstantesGlobais.FONTE_11_NORMAL);

        Utilitarios.considerarEnterComoTab(this);

        setHorizontalAlignment(JTextField.RIGHT);
        setToolTipText(descricaoRotulo + " >= 0");
        limpar();

        setDocument(new FiltrarNumeros(qtdMaxCaracteres));
        setHorizontalAlignment(RIGHT);
    }

    @Override
    public void limpar() {
        setInteger(valorPadrao);
        setBorder(bordaOriginal);
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getLong() > 0) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(ConstantesGlobais.BORDA_ERRO);
                return true;
            }
        }
        return false;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().trim().equals("0")) {
            setText("");
        }
        this.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().trim().length() == 0) {
            setText("0");
        }
    }

    public Long getLong() {
        if (getText().trim().length() == 0) {
            return 0l;
        }
        return Long.parseLong(this.getText());
    }

    public void setLong(Long valor) {
        if (valor != null) {
            this.setText(valor.toString());
        } else {
            this.setText("0");
        }
    }

    public void setInteger(Integer valor) {
        if (valor == null) {
            this.setText("0");
        } else {
            this.setText(valor.toString());
        }
    }

    public Integer getInteger() {
        if (getText().trim().equals("")) {
            return 0;
        }
        return Integer.parseInt(this.getText());
    }

    public String getString() {
        return getText().trim();
    }

    public String getString(String tipo, String caracter, int quantidade) {
        return tipo.equals("L") ? Utilitarios.lpad(getText().trim(), caracter, quantidade) : Utilitarios.rpad(getText().trim(), caracter, quantidade);
    }

    public void setString(String valor) {
        setText(valor);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public String getDica() {
        return "";
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;

        atualizaDescricaoRotulo();
    }

    public void atualizaDescricaoRotulo() {
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

    public Border getBordaOriginal() {
        return bordaOriginal;
    }

    public void setBordaOriginal(Border bordaOriginal) {
        this.bordaOriginal = bordaOriginal;
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

        atualizaDescricaoRotulo();
    }

    public int getValorPadrao() {
        return valorPadrao;
    }

    public void setValorPadrao(int valorPadrao) {
        this.valorPadrao = valorPadrao;
    }

    public int getQtdMaxCaracteres() {
        return qtdMaxCaracteres;
    }

    public void setQtdMaxCaracteres(int qtdMaxCaracteres) {
        this.qtdMaxCaracteres = qtdMaxCaracteres;
        setDocument(new LimitaNroCaracteres(qtdMaxCaracteres));
    }

}
