/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author luiz.almeida
 */
public class FixedLengthDocument extends PlainDocument {

    private final int iMaxLength;

    public FixedLengthDocument(final int maxlen) {
        super();
        this.iMaxLength = maxlen;
    }

    @Override
    public void insertString(final int offset, final String str, final AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if (this.iMaxLength <= 0) // aceitara qualquer no. de caracteres  
        {
            super.insertString(offset, str, attr);
            return;
        }

        final int ilen = this.getLength() + str.length();
        if (ilen <= this.iMaxLength) {
            super.insertString(offset, str, attr); // ...aceita str  
        } else {
            if (this.getLength() == this.iMaxLength) {
                return; // nada a fazer
            }
            final String newStr = str.substring(0, this.iMaxLength - this.getLength());

            super.insertString(offset, newStr, attr);
        }
    }
}
