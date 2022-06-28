/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.util.anotacoes.FormatoColuna;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.JXFormattedTextField;

/**
 *
 * @author Sidnei
 */
public class CampoFiltroFK extends JXFormattedTextField {

    public CampoFiltroFK() {
    }

    public void atualizarFormato(FormatoColuna formatoCampo) {
        setText("");
        try {
            if (formatoCampo.equals(FormatoColuna.TEXT)) {
                setDocument(new DocumentoString());
                setHorizontalAlignment(JXFormattedTextField.LEFT);
                removeCaretListener(null);
                setText("");

            } else if (formatoCampo.equals(FormatoColuna.DATE)) {
                setDocument(new DocumentoString());
                setHorizontalAlignment(JXFormattedTextField.LEFT);
                removeCaretListener(null);
                setText("");

                MaskFormatter mf = new MaskFormatter("##/##/####");
                mf.install(this);

            } else if (formatoCampo.equals("Classe")) {
                setDocument(new DocumentoString());
                setHorizontalAlignment(JXFormattedTextField.LEFT);
                removeCaretListener(null);
                setText("");
            } else if (formatoCampo.equals(FormatoColuna.INTEGER)) {
                setDocument(new DocumentoNumero());
                setHorizontalAlignment(RIGHT);
//                jxftfFiltro.addCaretListener(new CaretListener() {
//
//                    @Override
//                    public void caretUpdate(CaretEvent e) {
//                        if (jxftfFiltro.getCaret().getMark() != jxftfFiltro.getText().length()) {
//                            jxftfFiltro.getCaret().setDot(jxftfFiltro.getText().length());
//                        }
//                    }
//                });
                setText("");

            } else if (formatoCampo.equals(FormatoColuna.REAL)) {
                setDocument(new DocumentoDecimal());
                setHorizontalAlignment(RIGHT);
//                jxftfFiltro.addCaretListener(new CaretListener() {
//
//                    @Override
//                    public void caretUpdate(CaretEvent e) {
//                        if (jxftfFiltro.getCaret().getMark() != jxftfFiltro.getText().length()) {
//                            jxftfFiltro.getCaret().setDot(jxftfFiltro.getText().length());
//                        }
//                    }
//                });
                setText("0,00");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DocumentoDecimal extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) {
            try {
                int multiplicador = 1;
                String valorAtual = getText(0, getLength()).trim().replace(".", "").replace(",", "");
                str = str.trim().replace(",", ".");
                if (str.indexOf("-") >= 0) { //indica que foi solicitado a mudança de sinal
                    multiplicador = -1;
                    str = str.replace("-", "");
                }
                if (valorAtual.indexOf("-") >= 0) { //indica que foi solicitado a mudança de sinal
                    multiplicador = multiplicador * (-1);
                    valorAtual = valorAtual.replace("-", "");
                }
                valorAtual = "000" + valorAtual + str;
                Double valor = Double.parseDouble(valorAtual);
                valor = valor * multiplicador;
                if (str.indexOf(".") == -1) //se não tem "." indica que o valor não tem os centavos
                {
                    valor = valor / 100;
                }
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                String valorFormatado = nf.format(valor).replace("R$ ", "");
                if (valorFormatado.trim().equals("-0,00")) {
                    valorFormatado = "0,00";
                }
                StringBuffer strBuf = new StringBuffer(valorFormatado);
                super.remove(0, getLength());
                super.insertString(0, strBuf.toString(), a);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível formatar/insert (MeuDocument-->CampoMonetario)");
            }
        }

        @Override
        public void remove(int offs, int len) {
            try {
                super.remove(offs, len);
                insertString(0, " ", null);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível formatar/remove (MeuDocument-->CampoMonetario)");
            }
        }
    }

    class DocumentoString extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) {
            try {
                super.insertString(offs, str, a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void remove(int offs, int len) {
            try {
                super.remove(offs, len);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível formatar/remove (MeuDocument-->CampoMonetario)");
            }
        }
    }

    class DocumentoNumero extends PlainDocument {

        public DocumentoNumero() {
            super();
            this.setDocumentFilter(filter);
        }

        DocumentFilter filter = new DocumentFilter() {

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
//                if (text.matches("[0-9]*+"))
//                    fb.insertString(offset, text, attr);
                fb.insertString(offset, text.replaceAll("[^0-9]", ""), attr);
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
//                if (text.matches("[0-9]*+"))
//                    fb.replace(offset, length, text, attrs);
                fb.replace(offset, length, text.replaceAll("[^0-9]", ""), attrs);
            }
        };
    }
}
