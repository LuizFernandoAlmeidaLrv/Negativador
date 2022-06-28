/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Sidnei
 */
public class CampoMascara extends CampoString {

    private MaskFormatter formatador;
    private String mascara;
    private boolean selecionaTextoOnFocusGained = true;
    private boolean sobrescreve = false;

    public CampoMascara() {
        this(null, null, null, false, "********", null);
    }

    public CampoMascara(Class tabela, String campo, String rotulo, String mascara) {
        this(tabela, campo, rotulo, false, mascara, null);
    }

    public CampoMascara(Class tabela, String campo, boolean obrigatorio, String mascara, Rotulo rotuloCampo) {
        this(tabela, campo, null, false, mascara, null);
    }

    public CampoMascara(Class tabela, String campo, String rotulo, boolean obrigatorio, String mascara, Rotulo rotuloCampo) {
        super(tabela, campo, rotulo, true, rotuloCampo);
        this.mascara = mascara;

        if (getMascara() == null) {
            this.setMascara("********");
            this.aplicaMascara();
        } else {
            this.setMascara(mascara);
        }

        int tamMask = this.getMascara().length();
        this.setHorizontalAlignment(0);
        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (isSelecionaTextoOnFocusGained()) {
                    SwingUtilities.invokeLater(() -> {
                        selectAll();
                    });
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (e.getOppositeComponent() == null) {
                    return;
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                trataEventoKeyTyped(e);
            }
        });

    }

    public MaskFormatter getFormatador() {
        return formatador;
    }

    public void setFormatador(MaskFormatter formatador) {
        this.formatador = formatador;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
        aplicaMascara();
    }

    public boolean isSelecionaTextoOnFocusGained() {
        return selecionaTextoOnFocusGained;
    }

    public void setSelecionaTextoOnFocusGained(boolean selecionaTextoOnFocusGained) {
        this.selecionaTextoOnFocusGained = selecionaTextoOnFocusGained;
    }

    public boolean isSobrescreve() {
        return sobrescreve;
    }

    public void setSobrescreve(boolean sobrescreve) {
        this.sobrescreve = sobrescreve;
    }

    private void aplicaMascara() {
        try {
            if (this.formatador == null) {
                this.formatador = new MaskFormatter(mascara);
                setFormatterFactory(new DefaultFormatterFactory(this.formatador));
            } else {
                this.formatador.setMask(mascara);
            }
            setValue(null);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        implementacaoPropertyChange(null);
    }

    public void setCaracteresValidos(String caracteresValidos) {
        this.formatador.setValidCharacters(caracteresValidos);
    }

    public void setCaracteresInvalidos(String charsInvalidos) {
        this.formatador.setInvalidCharacters(charsInvalidos);
    }

    public void implementacaoPropertyChange(PropertyChangeEvent evt) {
        if (this == null) {
            return;
        }

        //Tratamentos para parar de apitar quando entra nas telas...
        //String valorSemMascara = this.getText();
        String valorSemMascara = this.getText().replaceAll(".", "").replaceAll("-", "").replaceAll("/", "").trim();
        String valorComMascara = UtilitariosString.retornaComMascara((String) valorSemMascara, (String) this.getFormatador().getMask());
        if (valorSemMascara.trim().equals("")) {
            //this.setText(valorComMascara);
            this.setValue((Object) null);
        } else {
            //this.setText(valorComMascara);
        }
        this.setCaretPosition(0);
    }

    public void setarCampo() {
        String s = this.getText();
        String valorSemMascara = UtilitariosString.retiraMascara((String) s);
        int tamMask = this.getMascara().length();
        if (s.length() > tamMask) {
            s = s.substring(0, tamMask);
        }
        this.setText(valorSemMascara);
    }

    protected void trataEventoKeyTyped(KeyEvent e) {
        boolean manipulacaoCursor;
        boolean bl = manipulacaoCursor = e.getKeyChar() == '\t' || e.getKeyChar() == '\n' || e.getKeyChar() == '\b' || e.getKeyChar() == '' || e.getKeyChar() == '%' || e.getKeyChar() == '\'';
        if (this.getFormatador() != null && this.getFormatador().getValidCharacters() != null && this.getFormatador().getValidCharacters().indexOf(" ") == -1 && e.getKeyChar() == ' ') {
            e.setKeyChar('\uffff');
            e.consume();
            UIManager.getLookAndFeel().provideErrorFeedback(this);
            return;
        }
        if (manipulacaoCursor) {
            return;
        }
        char ch = e.getKeyChar();
        boolean eventosEhInclusao = ch != '\t' && ch != '\n' && ch != '\b' && ch != '' && ch != '%' && ch != '\'';
        int tamanhoTextoAtual = this.getText().trim().length();
        if (this.sobrescreve && this.getMascara() != null && this.getCaret().getDot() <= this.getMascara().length() && eventosEhInclusao && tamanhoTextoAtual >= this.getMascara().length() && this.getSelectedText() == null) {
            this.select(this.getCaret().getDot(), this.getCaret().getDot() + 1);
        }
        if (this.getMascara() != null && tamanhoTextoAtual >= this.getMascara().length() && eventosEhInclusao && this.getSelectedText() == null) {
            e.setKeyChar('\uffff');
            e.consume();
            UIManager.getLookAndFeel().provideErrorFeedback(this);
        }
    }

}
