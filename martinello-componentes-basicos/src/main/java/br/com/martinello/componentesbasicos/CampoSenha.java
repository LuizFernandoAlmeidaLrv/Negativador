package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class CampoSenha extends JPasswordField implements FocusListener, Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected int tamanho;
    protected Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;

    public CampoSenha() {
        addFocusListener(this);

        bordaOriginal = this.getBorder();
        Utilitarios.considerarEnterComoTab(this);
    }

    public CampoSenha(Class tabela, String campo) {
        Utilitarios.considerarEnterComoTab(this);

//       if (eObrigatorio())
//           setBackground(Color.yellow);
        addFocusListener(this);
        bordaOriginal = this.getBorder();
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

    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    public String getString() {
        return this.getText().trim();
    }

    public void setString(String texto) {
        this.setText(texto);
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

    public JLabel getJlRotulo() {
        return rRotulo;
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

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }
}
