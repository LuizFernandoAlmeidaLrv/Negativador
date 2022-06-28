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
public class DadosComerciaisModel {
    @Resolvable(colName = "Empresa")
    private String empresa;
    
    @Resolvable(colName = "Ocupação")
    private String ocupacao;
    
    @Resolvable(colName = "Cargo")
    private String cargo;
    
    @Resolvable(colName = "Data Adimissão")
    private Date dataAdmicao;
    
    @Resolvable(colName = "Telefone")
    private String telefone;
    
    @Resolvable(colName = "Contato")
    private String contato;
    
    @Resolvable(colName = "Data Geração")
    private Date dataGeracao;
    
    @Resolvable(colName = "Usuário Geração")
    private String usuarioGeracao;
    
    @Resolvable(colName = "Data Alteração")
    private Date DataAlteracao;
    
    @Resolvable(colName = "Usuário Alteração")
    private String usuarioAlteracao;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getDataAdmicao() {
        return dataAdmicao;
    }

    public void setDataAdmicao(Date dataAdmicao) {
        this.dataAdmicao = dataAdmicao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getUsuarioGeracao() {
        return usuarioGeracao;
    }

    public void setUsuarioGeracao(String usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
    }

    public Date getDataAlteracao() {
        return DataAlteracao;
    }

    public void setDataAlteracao(Date DataAlteracao) {
        this.DataAlteracao = DataAlteracao;
    }

    public String getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(String usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }
    
    
    
}
