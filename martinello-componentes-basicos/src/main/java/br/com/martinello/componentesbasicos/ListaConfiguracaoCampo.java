/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.util.anotacoes.FormatoColuna;

/**
 *
 * @author Sidnei
 */
public class ListaConfiguracaoCampo {

    private String nome;
    private String descricao;
    private FormatoColuna tipo;

    public ListaConfiguracaoCampo() {
    }

    public ListaConfiguracaoCampo(String nome, String descricao, FormatoColuna tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FormatoColuna getTipo() {
        return tipo;
    }

    public void setTipo(FormatoColuna tipo) {
        this.tipo = tipo;
    }

}
