package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class CampoFormatavel extends JFormattedTextField implements FocusListener, Campo {

    public String dica;
    public String descricaoRotulo;
    public boolean obrigatorio;
    public int tamanho;
    public Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;

    public CampoFormatavel(Class tabela, String campo, String descricaoRotulo) {
        this(tabela, campo, descricaoRotulo, false, null);
    }

    public CampoFormatavel(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio, Rotulo rRotulo) {
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
        Utilitarios.considerarEnterComoTab(this);
    }

    @Override
    public void limpar() {
        setText("");
        setBorder(bordaOriginal);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getText().trim().length() > 0) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
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
    public void focusGained(FocusEvent e) {
        //setBackground(new Color(100,149,237));
        this.selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
//        if (eObrigatorio())
//           setBackground(Color.yellow);
//        else
//           setBackground(Color.white);
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    public String getString() {
        return this.getText().trim();
    }

    public void setString(String texto) {
        this.setText(texto);
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

    public Long getLong() {
        return Long.parseLong(this.getText());
    }

    public void setLong(Long valor) {
        this.setText(valor.toString());
    }

    public void setInteger(Integer valor) {
        if (valor == null) {
            this.setText("0");
        } else {
            this.setText(valor.toString());
        }
    }

    public boolean isNull() {
        if (getString().trim().isEmpty()) {
            return true;
        }
        return false;
    }

//    public Integer getInteger() {
//        if (getText().trim().equals("")) {
//            return 0;
//        }
//        return Integer.parseInt(this.getText());
//    }
    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
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
}
