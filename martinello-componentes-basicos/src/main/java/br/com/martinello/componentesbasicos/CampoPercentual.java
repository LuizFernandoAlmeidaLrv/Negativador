package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CampoPercentual extends JTextField implements Campo, FocusListener {

    private static final long serialVersionUID = -7506506392528621022L;
    private static final NumberFormat MONETARY_FORMAT = new DecimalFormat("'%' #,##0.00");
    private NumberFormat numberFormat;
    private int limit = -1;
    private Border bordaOriginal = null;
    private boolean obrigatorio;
    private String descricaoRotulo;
    private Rotulo rRotulo;

    public CampoPercentual() {
        this(null, null, null, false, MONETARY_FORMAT);
    }

    public CampoPercentual(Class tabela, String campo, String rotulo) {
        this(tabela, campo, rotulo, false, MONETARY_FORMAT);
    }

    public CampoPercentual(Class tabela, String campo, String rotulo, boolean obrigatorio) {
        this(tabela, campo, rotulo, obrigatorio, MONETARY_FORMAT);
    }

    public CampoPercentual(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio, NumberFormat format) {
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;
        addFocusListener(this);
        bordaOriginal = this.getBorder();
        Utilitarios.considerarEnterComoTab(this);

        numberFormat = format;// alinhamento horizontal para o texto
        setHorizontalAlignment(RIGHT);// documento responsável pela formatação
        // do campo
        setDocument(new PlainDocument() {
            private static final long serialVersionUID = 1L;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                String text = new StringBuilder(CampoPercentual.this.getText().replaceAll("[^0-9]", "")).append(str.replaceAll("[^0-9]", "")).toString();
                super.remove(0, getLength());
                if (text.isEmpty()) {
                    text = "0";
                } else {
                    text = new BigInteger(text).toString();
                }
                super.insertString(0, numberFormat.format(new BigDecimal(getLimit() > 0 == text.length() > getLimit() ? text.substring(0, getLimit()) : text).divide(new BigDecimal(Math.pow(10, numberFormat.getMaximumFractionDigits())))), a);
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                if (len != getLength()) {
                    insertString(0, "", null);
                }
            }
        });// mantem o cursor no final
        // do campo
        addCaretListener(new CaretListener() {
            boolean update = false;

            @Override
            public void caretUpdate(CaretEvent e) {
                if (!update) {
                    update = true;
                    setCaretPosition(getText().length());
                    update = false;
                }
            }
        });// limpa o campo se
        // apertar DELETE
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    setText("");
                }
            }
        });// formato
        // inicial
        setText("0");
        setCaretPosition(getText().length());
    }

    /**
     * *
     * Define um valor BigDecimal ao campo**
     *
     * @param value
     */
    public void setValue(BigDecimal value) {
        super.setText(numberFormat.format(value));
    }

    /**
     * *
     * Recupera um valor BigDecimal do campo**
     *
     * @return
     */
    public final BigDecimal getValue() {
        return new BigDecimal(getText().replaceAll("[^0-9]", "")).divide(new BigDecimal(Math.pow(10, numberFormat.getMaximumFractionDigits())));
    }

    /**
     * *
     * Recupera o formatador atual do campo**
     *
     * @return
     */
    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    /**
     * *
     * Define o formatador do campo** @param numberFormat
     */
    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    /**
     * *
     * Preenche um StringBuilder com zeros** @param zeros*
     *
     * @return
     */
    private static final String makeZeros(int zeros) {
        if (zeros >= 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < zeros; i++) {
                builder.append('0');
            }
            return builder.toString();
        } else {
            throw new RuntimeException("Número de casas decimais inválida (" + zeros + ")");
        }
    }

    /**
     * *
     * Recupera o limite do campo.** @return
     */
    public int getLimit() {
        return limit;
    }

    /**
     * *
     * Define o limite do campo, limit < 0 para deixar livre (default) Ignora os
     * pontos e virgulas do formato, conta* somente com os números** @param
     * limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Double getDouble() {
        return new Double(getText().replace(".", "").replace(",", ".").replace("% ", ""));
    }

    public void setDouble(Double valor) {
        //this.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(numFormatter));
        if (valor != null) {
            setText(numberFormat.format(valor).replace(",", "").replace(".", "").replace("% ", ""));
        }
    }

    @Override
    public void limpar() {
        setText("0,00");
        setBorder(bordaOriginal);
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getDouble() > 0) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                return true;
            }
        }
        setBorder(bordaOriginal);
        return false;
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
    public void focusGained(FocusEvent e) {
        selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }

}
