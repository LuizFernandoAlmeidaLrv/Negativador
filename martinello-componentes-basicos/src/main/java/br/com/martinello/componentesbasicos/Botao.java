/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author Sidnei
 */
public class Botao extends JButton {

    public Botao() {
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Botao(Icon icon) {
        super(icon);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Botao(String text) {
        super(text);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Botao(Action a) {
        super(a);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Botao(String text, Icon icon) {
        super(text, icon);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

}
