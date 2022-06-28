/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;

/**
 *
 * @author luiz.almeida
 */
public class ParametrosModel {
    @Resolvable(colName = "Parâmetro")
    private String parametro;

    @Resolvable(colName = "Valor")
    private String valor;

    @Resolvable(colName = "Ultima Atualização")
    private String ultimaAtualizacao;

    @Resolvable(colName = "Usuário Alteração")
    private String usuarioAlteracao;

    @Resolvable(colName = "Descrição do Parâmetro")
    private String descricao;

    public ParametrosModel() {

    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(String usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
