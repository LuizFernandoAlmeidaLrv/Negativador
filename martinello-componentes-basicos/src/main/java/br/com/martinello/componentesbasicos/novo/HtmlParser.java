/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

import java.io.IOException;
import java.io.StringReader;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class HtmlParser {

    private HTMLEditorKit.ParserCallback callback;
    private String texto;

    public HtmlParser(String html, HTMLEditorKit.ParserCallback callback) {
        StringReader r = new StringReader(html);
        HTMLEditorKit.Parser parse = new HTMLParse().getParser();
        this.callback = callback == null ? new ParserCallbackLocal() : callback;
        try {
            parse.parse(r, this.callback, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTexto() {
        String saida = this.texto;
        this.texto = "";
        return saida;
    }

    static /* synthetic */ void access$1(HtmlParser htmlParser, String string) {
        htmlParser.texto = string;
    }

    class ParserCallbackLocal
            extends HTMLEditorKit.ParserCallback {

        ParserCallbackLocal() {
        }

        public void handleComment(char[] data, int pos) {
        }

        public void handleError(String errorMsg, int pos) {
        }

        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        }

        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        }

        public void handleEndTag(HTML.Tag t, int pos) {
        }

        public void handleText(char[] data, int pos) {
            if (HtmlParser.this.texto == null) {
                HtmlParser.access$1(HtmlParser.this, String.copyValueOf(data));
            } else {
                HtmlParser htmlParser = HtmlParser.this;
                HtmlParser.access$1(htmlParser, String.valueOf(htmlParser.texto) + " " + String.copyValueOf(data));
            }
        }
    }

    class HTMLParse
            extends HTMLEditorKit {

        HTMLParse() {
        }

        public HTMLEditorKit.Parser getParser() {
            return super.getParser();
        }
    }

}
