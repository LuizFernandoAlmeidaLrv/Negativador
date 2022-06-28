package br.com.martinello.componentesbasicos;

import javax.swing.text.*;

public class FiltrarNumeros extends PlainDocument {

    private int iMaxLength;

    public FiltrarNumeros(int maxlen) {
        super();
        this.setDocumentFilter(filter);
        iMaxLength = maxlen;
    }
    DocumentFilter filter = new DocumentFilter() {

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {
//                if (text.matches("[0-9]*+"))
//                    fb.insertString(offset, text, attr);

            if (iMaxLength <= 0) {
                fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
                return;
            }
            int ilen = (getLength() + text.length());

            // se o comprimento final for menor...
            if (ilen <= iMaxLength) {
                fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
            }

        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
//                if (text.matches("[0-9]*+"))
//                    fb.replace(offset, length, text, attrs);
            fb.replace(offset, length, text.replaceAll("[^0-9]", ""), attrs);
        }
    };
}
