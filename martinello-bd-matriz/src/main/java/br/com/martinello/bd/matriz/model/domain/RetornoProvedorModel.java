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
public class RetornoProvedorModel {

    @Resolvable(colName = "idRetorno")
    private int idRetorno;

    @Resolvable(colName = "Código")
    private String codigo;

    @Resolvable(colName = "Descrição")
    private String descricao;

    @Resolvable(colName = "Comentário")
    private String comentario;

    @Resolvable(colName = "Provedor")
    private String tipo;

    @Resolvable(colName = "Notificar")
    private String notificar;

    public int getIdRetorno() {
        return idRetorno;
    }

    public void setIdRetorno(int idRetorno) {
        this.idRetorno = idRetorno;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNotificar() {
        return notificar;
    }

    public void setNotificar(String notificar) {
        this.notificar = notificar;
    }

}
