/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class LogParcelaModel {

    public Date getDataLogFim() {
        return dataLogFim;
    }

    public void setDataLogFim(Date dataLogFim) {
        this.dataLogFim = dataLogFim;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }


    @Resolvable(colName = "Id LogExtrator")
    private int idLogExtrator;

    @Resolvable(colName = "Id Extrator")
    private int idExtrator;

    @Resolvable(colName = "Codigo Retorno")
    private int cogigoRetorno;

    @Resolvable(colName = "Data Log")
    private Date dataLog;

  
    private Date dataLogFim;
    
    @Resolvable(colName = "Descrição Retorno")
    private String observacao;

    @Resolvable(colName = "Id Parcela")
    private int idParcela;

    @Resolvable(colName = "Status Spc")
    private String statusSpc;

    @Resolvable(colName = "Status Facmat")
    private String statusFacmat;

    @Resolvable(colName = "Data Retorno")
    private Date dataRetorno;

    @Resolvable(colName = "Descrição Log")
    private String descricaoLog;

    @Resolvable(colName = "Provedor")
    private String provedor;

    @Resolvable(colName = "Nº")
    private int indice;
    
     @Resolvable(colName = "Status")
    private String status;
     
      @Resolvable(colName = "Atividade Origem")
    private String origem;
      
       @Resolvable(colName = "Nome")
    private String nome;
       
        @Resolvable(colName = "Filial")
    private String filial;
        
       @Resolvable(colName = "Cliente")
    private String cliente;
       
         
       @Resolvable(colName = "Contrato")
    private String contrato;
       
        @Resolvable(colName = "Tipo")
    private String tipo;

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

    public int getIdLogExtrator() {
        return idLogExtrator;
    }

    public void setIdLogExtrator(int idLogExtrator) {
        this.idLogExtrator = idLogExtrator;
    }

    public int getIdExtrator() {
        return idExtrator;
    }

    public void setIdExtrator(int idExtrator) {
        this.idExtrator = idExtrator;
    }

    public int getCogigoRetorno() {
        return cogigoRetorno;
    }

    public void setCogigoRetorno(int cogigoRetorno) {
        this.cogigoRetorno = cogigoRetorno;
    }

    public Date getDataLog() {
        return dataLog;
    }

    public void setDataLog(Date dataLog) {
        this.dataLog = dataLog;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    public String getStatusSpc() {
        return statusSpc;
    }

    public void setStatusSpc(String statusSpc) {
        this.statusSpc = statusSpc;
    }

    public String getStatusFacmat() {
        return statusFacmat;
    }

    public void setStatusFacmat(String statusFacmat) {
        this.statusFacmat = statusFacmat;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getDescricaoLog() {
        return descricaoLog;
    }

    public void setDescricaoLog(String descricaoLog) {
        this.descricaoLog = descricaoLog;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String tipoOrigem) {
        this.provedor = tipoOrigem;
    }
        public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
