/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class ConsultaModel {

    private String pontoFilial;
    private int id;
    private String cpfCnpj;
    private String numeroDoContrato;
    private String dataInicial;
    private String dataFinal;
    private String dataIniciaPag;
    private String dataFinalPag;
    private String dataInicialNeg;
    private String dataFinalNeg;
    private String dataInicialBai;
    private String dataFinalBai;
    private String codCliente;
    private String nome;
    private String motivoBaixaBvs;
    private String motivoBaixaSpc;
    private String motivoInclusaoBvs;
    private String naturezaRegistroBvs;
    private String naturezaInclusaoSpc;
    private String idMotivoBaixaBvs;
    private String idMotivoBaixaSpc;
    private String idMotivoInclusaoBvs;
    private String idNaturezaRegistroBvs;
    private String idNaturezaInclusaoSpc;
    private String dbSapiens;
    private String origem_BD;
    private String situacao;
     private String listMovimento;

//    public ConsultaModel(String pontoFilial, String cpfCnpj, String numeroDoContrato, String dataInicial, String dataFinal, String codCliente){
//        
//        this.codCliente = codCliente;
//        this.cpfCnpj = cpfCnpj;
//        this.dataInicial = dataInicial;
//        this.dataFinal = dataFinal;
//        this.numeroDoContrato = numeroDoContrato;
//        this.pontoFilial = pontoFilial;
//        
//    }
//    public ConsultaModel() {
//       
//    }
    /**
     * @return the dataInicialNeg
     */
    public String getDataInicialNeg() {
        return dataInicialNeg;
    }

    /**
     * @param dataInicialNeg the dataInicialNeg to set
     */
    public void setDataInicialNeg(String dataInicialNeg) {
        this.dataInicialNeg = dataInicialNeg;
    }

    /**
     * @return the dataFinalNeg
     */
    public String getDataFinalNeg() {
        return dataFinalNeg;
    }

    /**
     * @param dataFinalNeg the dataFinalNeg to set
     */
    public void setDataFinalNeg(String dataFinalNeg) {
        this.dataFinalNeg = dataFinalNeg;
    }

    /**
     * @return the dataInicialBai
     */
    public String getDataInicialBai() {
        return dataInicialBai;
    }

    /**
     * @param dataInicialBai the dataInicialBai to set
     */
    public void setDataInicialBai(String dataInicialBai) {
        this.dataInicialBai = dataInicialBai;
    }

    /**
     * @return the dataFinalBai
     */
    public String getDataFinalBai() {
        return dataFinalBai;
    }

    /**
     * @param dataFinalBai the dataFinalBai to set
     */
    public void setDataFinalBai(String dataFinalBai) {
        this.dataFinalBai = dataFinalBai;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDataIniciaPag() {
        return dataIniciaPag;
    }

    public void setDataIniciaPag(String dataIniciaPag) {
        this.dataIniciaPag = dataIniciaPag;
    }

    public String getDataFinalPag() {
        return dataFinalPag;
    }

    public void setDataFinalPag(String dataFinalPag) {
        this.dataFinalPag = dataFinalPag;
    }

    public String getPontoFilial() {
        return pontoFilial;
    }

    public void setPontoFilial(String pontoFilial) {
        this.pontoFilial = pontoFilial;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNumeroDoContrato() {
        return numeroDoContrato;
    }

    public void setNumeroDoContrato(String numeroDoContrato) {
        this.numeroDoContrato = numeroDoContrato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getMotivoBaixaBvs() {
        return motivoBaixaBvs;
    }

    public void setMotivoBaixaBvs(String motivoBaixaBvs) {
        this.motivoBaixaBvs = motivoBaixaBvs;
    }

    public String getMotivoBaixaSpc() {
        return motivoBaixaSpc;
    }

    public String getDbSapiens() {
        return dbSapiens;
    }

    public void setDbSapiens(String dbSapiens) {
        this.dbSapiens = dbSapiens;
    }

    public void setMotivoBaixaSpc(String motivoBaixaSpc) {
        this.motivoBaixaSpc = motivoBaixaSpc;
    }

    public String getMotivoInclusaoBvs() {
        return motivoInclusaoBvs;
    }

    public void setMotivoInclusaoBvs(String motivoInclusaoBvs) {
        this.motivoInclusaoBvs = motivoInclusaoBvs;
    }

    public String getNaturezaRegistroBvs() {
        return naturezaRegistroBvs;
    }

    public void setNaturezaRegistroBvs(String naturezaRegistroBvs) {
        this.naturezaRegistroBvs = naturezaRegistroBvs;
    }

    public String getNaturezaInclusaoSpc() {
        return naturezaInclusaoSpc;
    }

    public void setNaturezaInclusaoSpc(String naturezaInclusaoSpc) {
        this.naturezaInclusaoSpc = naturezaInclusaoSpc;
    }

    public String getIdMotivoBaixaBvs() {
        return idMotivoBaixaBvs;
    }

    public void setIdMotivoBaixaBvs(String idMotivoBaixaBvs) {
        this.idMotivoBaixaBvs = idMotivoBaixaBvs;
    }

    public String getIdMotivoBaixaSpc() {
        return idMotivoBaixaSpc;
    }

    public void setIdMotivoBaixaSpc(String idMotivoBaixaSpc) {
        this.idMotivoBaixaSpc = idMotivoBaixaSpc;
    }

    public String getIdMotivoInclusaoBvs() {
        return idMotivoInclusaoBvs;
    }

    public void setIdMotivoInclusaoBvs(String idMotivoInclusaoBvs) {
        this.idMotivoInclusaoBvs = idMotivoInclusaoBvs;
    }

    public String getIdNaturezaRegistroBvs() {
        return idNaturezaRegistroBvs;
    }

    public void setIdNaturezaRegistroBvs(String idNaturezaRegistroBvs) {
        this.idNaturezaRegistroBvs = idNaturezaRegistroBvs;
    }

    public String getIdNaturezaInclusaoSpc() {
        return idNaturezaInclusaoSpc;
    }

    public void setIdNaturezaInclusaoSpc(String idNaturezaInclusaoSpc) {
        this.idNaturezaInclusaoSpc = idNaturezaInclusaoSpc;
    }

    public String getOrigem_BD() {
        return origem_BD;
    }

    public void setOrigem_BD(String origem_BD) {
        this.origem_BD = origem_BD;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the listMovimento
     */
    public String getListMovimento() {
        return listMovimento;
    }

    /**
     * @param listMovimento the listMovimento to set
     */
    public void setListMovimento(String listMovimento) {
        this.listMovimento = listMovimento;
    }

}
