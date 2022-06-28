/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import java.awt.event.KeyEvent;
import javax.swing.UIManager;

/**
 *
 * @author Sidnei
 */
public class CampoPorcentagem extends CampoValor {

    private static String maskara = "##0,00";
    private int TAM_MAX = maskara.length() - 1;

    public CampoPorcentagem() {
        super();
    }

    @Override
    protected void trataEventoKeyTyped(KeyEvent e) {
        int tamanhoMaximo;
        char ch;
        String text = getText();
        int tamanhoTextoAtual = UtilitariosString.retiraMascara((String) text).length();
        boolean fracaoGrande = false;
        int pos = text.indexOf(44);
        if (pos != -1) {
            tamanhoMaximo = this.TAM_MAX;
            String s = text.substring(pos + 1);
            if (s.length() > 1 && getCaretPosition() > pos && getSelectedText() == null) {
                fracaoGrande = true;
            }
        } else {
            tamanhoMaximo = this.TAM_MAX - ConstantesGlobais.TAMANHO_VALOR_PARTE_DECIMAL;
        }
        if (e.getKeyChar() == ',') {
            tamanhoMaximo = this.TAM_MAX;
        }
        boolean eventosEhInclusao = (ch = e.getKeyChar()) != '\t' && ch != '\n' && ch != '\b' && ch != '' && ch != '%' && ch != '\'';
        boolean jaExisteUmSinalNeg = false;
        if (getText().trim().indexOf("-") != -1) {
            jaExisteUmSinalNeg = true;
        }
        if (tamanhoTextoAtual >= tamanhoMaximo && eventosEhInclusao && getSelectedText() == null || e.getKeyChar() == '-' && !this.aceitaNumerosNegativos || Character.isLetter(e.getKeyChar()) || fracaoGrande || e.getKeyChar() == '-' && jaExisteUmSinalNeg) {
            e.setKeyChar('\uffff');
            e.consume();
            UIManager.getLookAndFeel().provideErrorFeedback(this);
        }
    }
}
