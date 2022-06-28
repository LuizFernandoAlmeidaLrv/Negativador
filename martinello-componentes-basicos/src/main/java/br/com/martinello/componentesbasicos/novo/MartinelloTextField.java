/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class MartinelloTextField
        extends JTextField {

    public MartinelloTextField() {
    }

    public MartinelloTextField(int columns) {
        super(columns);
    }

    public MartinelloTextField(String text) {
        super(text);
    }

    public MartinelloTextField(String text, int columns) {
        super(text, columns);
    }

    public MartinelloTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

}
