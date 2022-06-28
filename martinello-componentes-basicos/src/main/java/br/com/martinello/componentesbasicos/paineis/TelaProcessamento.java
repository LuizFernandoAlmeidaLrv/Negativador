/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.paineis;

import javax.swing.JDialog;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Sidnei
 */
public class TelaProcessamento extends JDialog {

    protected JXBusyLabel jxblStatus = new JXBusyLabel();

    public TelaProcessamento(String texto) {
        super();
        setModal(true);
        jxblStatus.setText(texto);
        jxblStatus.setDelay(100);
        jxblStatus.setBusy(true);
        getContentPane().add(jxblStatus);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
