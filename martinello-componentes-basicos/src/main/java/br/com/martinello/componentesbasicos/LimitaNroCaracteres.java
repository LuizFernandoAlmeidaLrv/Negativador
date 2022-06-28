package br.com.martinello.componentesbasicos;

import javax.swing.text.*;

public class LimitaNroCaracteres extends PlainDocument {

    private int iMaxLength;
    private boolean somenteMaisculas = false;

    public LimitaNroCaracteres(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    public LimitaNroCaracteres(int maxlen, boolean somenteMaisculas) {
        super();
        iMaxLength = maxlen;
        this.somenteMaisculas = somenteMaisculas;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        //if (s == null) return;
        // aceitara qualquer no. de caracteres
        if (iMaxLength <= 0) {
            super.insertString(offset, somenteMaisculas ? str.toUpperCase() : str, attr);
            return;
        }
        int ilen = (getLength() + str.length());

        // se o comprimento final for menor...
        if (ilen <= iMaxLength) {
            super.insertString(offset, somenteMaisculas ? str.toUpperCase() : str, attr);   // ...aceita str
        }
    }
}
