/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComponent;

public class PainelBotao extends JComponent {

    public PainelBotao(BotaoMensagem btnMsg) {
        this.setLayout(new FlowLayout(0, 0, 0));
        this.add(btnMsg);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(20, 20);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(20, 20);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }
}
