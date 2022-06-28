/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Sidnei
 */
public class Rotulo extends JLabel {

    public Rotulo() {
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
        setHorizontalAlignment(RIGHT);
    }

    public Rotulo(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Rotulo(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Rotulo(String text) {
        super(text);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Rotulo(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public Rotulo(Icon image) {
        super(image);
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

}
