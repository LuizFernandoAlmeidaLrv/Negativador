/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 *
 * @author Sidnei
 */
public class MenuItem extends JMenuItem {

    private String texto, nome, metodo;

    public MenuItem(String texto, Icon icon, String nome, String metodo) {
        super(texto, icon);
        this.texto = texto;
        this.nome = nome;
        this.metodo = metodo;

        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public MenuItem(String texto, int mnemonic, String nome, String metodo) {
        super(texto, mnemonic);
        this.texto = texto;
        this.nome = nome;
        this.metodo = metodo;

        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public MenuItem(String texto, String nome, String metodo) {
        super(texto);
        this.texto = texto;
        this.nome = nome;
        this.metodo = metodo;

        setFont(ConstantesGlobais.FONTE_11_NORMAL);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

}
