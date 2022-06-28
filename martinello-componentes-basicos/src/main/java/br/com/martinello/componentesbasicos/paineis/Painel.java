/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.paineis;

import br.com.martinello.componentesbasicos.ConstantesGlobais;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Sidnei
 */
public class Painel extends JPanel {

    public Painel(LayoutManager layout) {
        super(layout);
    }

    public Painel() {
    }

    public Painel(String titulo) {
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, titulo, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));
    }

}
