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
public class LogExtracaoModel {

    @Resolvable(colName = "N°")
    private int indice;

    @Resolvable(colName = "Id Extracao")
    private String idExtracao;

    @Resolvable(colName = "Id Extrator")
    private String idExtrator;

    @Resolvable(colName = "Filial")
    private String filial;

    @Resolvable(colName = "Contrato")
    private String contrato;

    @Resolvable(colName = "Vencimento")
    private String vencimento;

    @Resolvable(colName = "Status")
    private String status;

    @Resolvable(colName = "Mensagem Status")
    private String menssagem;

    @Resolvable(colName = "Cliente")
    private String cliente;

    @Resolvable(colName = "Data Extração")
    private String dataExtracao;

    @Resolvable(colName = "CGCCPF")
    private String cgcCpf;

    private String dataExtracaoFim;

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getIdExtracao() {
        return idExtracao;
    }

    public void setIdExtracao(String idExtracao) {
        this.idExtracao = idExtracao;
    }

    public String getIdExtrator() {
        return idExtrator;
    }

    public void setIdExtrator(String idExtrator) {
        this.idExtrator = idExtrator;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getMenssagem() {
        return menssagem;
    }

    public void setMenssagem(String menssagem) {
        this.menssagem = menssagem;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDataExtracao() {
        return dataExtracao;
    }

    public void setDataExtracao(String dataExtracao) {
        this.dataExtracao = dataExtracao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    /**
     * @return the cgcCpf
     */
    public String getCgcCpf() {
        return cgcCpf;
    }

    /**
     * @param cgcCpf the cgcCpf to set
     */
    public void setCgcCpf(String cgcCpf) {
        this.cgcCpf = cgcCpf;
    }

    public String getDataExtracaoFim() {
        return dataExtracaoFim;
    }

    public void setDataExtracaoFim(String dataExtracaoFim) {
        this.dataExtracaoFim = dataExtracaoFim;
    }
}
