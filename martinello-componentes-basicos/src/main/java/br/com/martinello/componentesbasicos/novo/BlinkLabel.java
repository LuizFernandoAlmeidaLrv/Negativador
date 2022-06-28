/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

import java.awt.Color;
import javax.swing.JLabel;

public class BlinkLabel extends Thread {

    private JLabel label;
    private long delay;
    private Color cOriginal;
    private Color cBlink;
    private boolean blink;
    private int repeticao;
    private boolean flOk;

    public BlinkLabel() {
    }

    public BlinkLabel(JLabel nLabel, long nDelay, Color nBlink, int nRepeticao) {
        this.cOriginal = nLabel.getForeground();
        this.cBlink = nBlink;
        this.label = nLabel;
        this.delay = nDelay;
        this.repeticao = nRepeticao;
        this.flOk = true;
        this.blink = false;
    }

    public BlinkLabel(JLabel nLabel) {
        this(nLabel, 350, Color.WHITE, 10);
    }

    @Override
    public void run() {
        int count = this.repeticao;
        boolean opaco = this.label.isOpaque();
        while (this.flOk) {
            boolean bl = this.blink = !this.blink;
            if (this.blink) {
                this.label.setForeground(this.cOriginal);
            } else {
                this.label.setForeground(this.cBlink);
            }
            if (this.repeticao > 0 && --count <= 0) {
                this.parar();
            }
            try {
                Thread.sleep(this.delay);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        this.label.setOpaque(opaco);
        this.label.setForeground(this.cOriginal);
    }

    public void parar() {
        this.flOk = false;
    }
}
