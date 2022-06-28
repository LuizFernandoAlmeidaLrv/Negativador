/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Sidnei
 */
public class CampoValor extends CampoString {

    private static Valor valor = new Valor("0,00");
    private static String maskara = "###.###.##0,00";
    protected boolean aceitaNumerosNegativos = false;

    public CampoValor() {
        this(null, null, null, false, null);
    }

    public CampoValor(Class tabela, String campo, String rotulo) {
        this(tabela, campo, rotulo, false, null);
    }

    public CampoValor(Class tabela, String campo, String rotulo, boolean obrigatorio, Rotulo rotuloCampo) {
        super(tabela, campo, rotulo, obrigatorio, rotuloCampo);

        this.setInformacao(valor);
        this.buildComponente();
        this.setText("0,00");
    }

    public Valor getInformacao() {
        return valor;
    }

    public void setInformacao(Valor valor) {
        this.valor = valor;
    }

    public boolean isAceitaNumerosNegativos() {
        return this.aceitaNumerosNegativos;
    }

    public void setAceitaNumerosNegativos(boolean aceitaNumerosNegativos) {
        this.aceitaNumerosNegativos = aceitaNumerosNegativos;
    }

    protected void informacaoModificada() {
        setInformacao(this.getInformacao());
        this.implementacaoPropertyChange(null);
    }

    protected void buildComponente() {
        setHorizontalAlignment(11);
        //setFont(new Font("Verdana", 0, 9));
        //setFont(ConstantesGlobais.FONTE_9_NORMAL);
        setHorizontalAlignment(4);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    selectAll();
                });
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (e.getOppositeComponent() == null) {
                    return;
                }

                getInformacao().setConteudo(getText());
                setText(getInformacao().getConteudoFormatado());
                //JEditValor.this.chamaValidacao();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ev) {
                trataEventoKeyTyped(ev);
            }
        });

    }

    protected void trataEventoKeyTyped(KeyEvent e) {
        char ch;
        boolean naoEhDigitoVirgulaPontoOuSinalNegativo;
        boolean manipulacaoCursor;
        int tamanhoMaximo;

        boolean bl = manipulacaoCursor = (e.getKeyChar() == '\t' || e.getKeyChar() == '\n' || e.getKeyChar() == '\b' || e.getKeyChar() == '' || e.getKeyChar() == '%' || e.getKeyChar() == '\'') && !String.valueOf(e.getKeyChar()).equals("%") && !String.valueOf(e.getKeyChar()).equals("'");
        if (manipulacaoCursor) {
            return;
        }

        String text = getText();
        int tamanhoTextoAtual = UtilitariosString.retiraMascara((String) text).length();
        boolean fracaoGrande = false;

        Valor info = (Valor) this.getInformacao();

        int pos = text.indexOf(44);
        if (pos != -1) {
            tamanhoMaximo = info.getMaximoDigitosParteInteira() + info.getCasasDecimais();
            String s = text.substring(pos + 1);
            if (s.length() == info.getCasasDecimais() && getCaretPosition() > pos && getSelectedText() == null) {
                fracaoGrande = true;
            }
        } else {
            tamanhoMaximo = info.getMaximoDigitosParteInteira();
        }

        if (e.getKeyChar() == ',') {
            tamanhoMaximo = info.getMaximoDigitosParteInteira() + info.getCasasDecimais();
        }

        boolean eventosEhInclusao = (ch = e.getKeyChar()) != '\t' && ch != '\n' && ch != '\b' && ch != '' && ch != '%' && ch != '\'';
        boolean jaExisteUmSinalNeg = false;
        if (getText().trim().indexOf("-") != -1) {
            jaExisteUmSinalNeg = true;
        }

        boolean jaExisteVirgula = false;
        if (getText().trim().indexOf(",") != -1) {
            jaExisteVirgula = true;
        }

        boolean bl2 = naoEhDigitoVirgulaPontoOuSinalNegativo = !Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '-' && e.getKeyChar() != '.' && e.getKeyChar() != ',' && e.getKeyChar() != ' ';
        if (tamanhoTextoAtual >= tamanhoMaximo && eventosEhInclusao && getSelectedText() == null || e.getKeyChar() == '-' && !this.aceitaNumerosNegativos || Character.isLetter(e.getKeyChar()) || fracaoGrande || e.getKeyChar() == '-' && jaExisteUmSinalNeg || jaExisteUmSinalNeg && tamanhoTextoAtual + 1 >= tamanhoMaximo || e.getKeyChar() == '\'' || e.getKeyChar() == '\"' || String.valueOf(e.getKeyChar()).equals("\u00b4") || String.valueOf(e.getKeyChar()).equals("`") || String.valueOf(e.getKeyChar()).equals("^") || String.valueOf(e.getKeyChar()).equals("@") || String.valueOf(e.getKeyChar()).equals("~") || String.valueOf(e.getKeyChar()).equals("'") || String.valueOf(e.getKeyChar()).equals("%") || e.getKeyChar() == ',' && jaExisteVirgula || e.getKeyChar() == ',' && info.getCasasDecimais() == 0 || naoEhDigitoVirgulaPontoOuSinalNegativo) {
            e.setKeyChar('\uffff');
            e.consume();
            UIManager.getLookAndFeel().provideErrorFeedback(this);
        }
    }

    public void implementacaoPropertyChange(PropertyChangeEvent evt) {
        setText(this.getInformacao().getConteudoFormatado());
    }

    public JComponent getComponenteEditor() {
        return this;
    }

    protected void readOnlyPropertyChange(boolean readOnly) {
        if (readOnly) {
            setEditable(false);
        } else {
            setEditable(true);
        }
    }

    protected void habilitadoPropertyChange(boolean habilitado) {
        this.getComponenteEditor().setEnabled(habilitado);
    }

    public JComponent getComponenteFoco() {
        return this;
    }

    public void setEstiloFonte(int estilo) {
        Font f = getFont();
        f = f.deriveFont(estilo);
        setFont(f);
    }

//    public void setIncrementoTamanhoFonte(int incremento) {
//        this.incrementoTamanhoFonte = incremento;
//        Font f = this.componente.getFont();
//        if (this.tamanhoOriginal == -1.0f) {
//            this.tamanhoOriginal = f.getSize2D();
//        }
//        f = f.deriveFont(this.tamanhoOriginal + (float) incremento);
//        this.componente.setFont(f);
//    }
//
//    public int getIncrementoTamanhoFonte() {
//        return this.incrementoTamanhoFonte;
//    }
    public void setAceitaFoco(boolean aAceita) {
        this.getComponenteEditor().setFocusable(aAceita);
    }

    public boolean isAceitaFoco() {
        return this.getComponenteEditor().isFocusable();
    }

    public Double getDouble() {
        getInformacao().setConteudo(getText());
        return getInformacao().getConteudo() < 0 ? new Double(getInformacao().getParteInteira() + "." + getInformacao().getParteDecimal()) * -1 : new Double(getInformacao().getParteInteira() + "." + getInformacao().getParteDecimal());
    }

    public void setDouble(Double valor) {
        getInformacao().setConteudo(valor);
        setText(this.getInformacao().getConteudoFormatado());
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getDouble() > 0d) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(ConstantesGlobais.BORDA_ERRO);
                return true;
            }
        }
        return false;
    }

}
