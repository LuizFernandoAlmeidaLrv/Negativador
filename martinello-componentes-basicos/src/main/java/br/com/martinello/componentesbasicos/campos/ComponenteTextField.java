/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  serpro.ppgd.negocio.Informacao
 */
package br.com.martinello.componentesbasicos.campos;

import br.com.martinello.componentesbasicos.Campo;
import javax.swing.JTextField;
import javax.swing.text.Document;

public class ComponenteTextField extends JTextField {

    protected Campo campo = null;

    public ComponenteTextField() {
    }

    public ComponenteTextField(int columns) {
        super(columns);
    }

    public ComponenteTextField(String text) {
        super(text);
    }

    public ComponenteTextField(String text, int columns) {
        super(text, columns);
    }

    public ComponenteTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

}
