/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.consulta.Domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class ObservacaoModel {

    @Resolvable(colName = "Cliente Endereço")
    private String clienteEndereco;

    @Resolvable(colName = "Tipo")
    private String tipo;

    @Resolvable(colName = "Descrição")
    private String descricao;

    @Resolvable(colName = "Data Geração")
    private Date dataGeracao;

    @Resolvable(colName = "Usuário Geração")
    private String usuarioGer;

    public String getClienteEndereco() {
        return clienteEndereco;
    }

    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getUsuarioGer() {
        return usuarioGer;
    }

    public void setUsuarioGer(String usuarioGer) {
        this.usuarioGer = usuarioGer;
    }
    

}
