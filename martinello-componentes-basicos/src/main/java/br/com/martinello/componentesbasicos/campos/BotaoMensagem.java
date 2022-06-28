/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotaoMensagem extends JButton {

    public BotaoMensagem() {
        this.setOpaque(false);
        this.setVisible(false);
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setSize(20, 20);
        this.setMaximumSize(this.getSize());
        this.setBorder(null);
        this.setText("");

        this.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ico_btn_msg_design.png")));
        this.setVisible(true);
    }

    @Override
    public String getText() {
        return "";
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
