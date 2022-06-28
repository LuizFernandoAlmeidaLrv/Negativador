/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RecebimentoApp {

    

    @Resolvable(colName = "Id_Registro")
    private int idRecebimento;
    @Resolvable(colName = "Situação")
    private String sitRecebimento;
    @Resolvable(colName = "Bandeira")
    private String bandeira;
    @Resolvable(colName = "Autorização")
    private String codigoAutorizacao;
    @Resolvable(colName = "Documento")
    private String documento;
    @Resolvable(colName = "Cliente")
    private String nomeCliente;
    @Resolvable(colName = "Data_Geração")
    private String dataGerInicio;
    private String dataGerFim;
    @Resolvable(colName = "Data_Recebimento")
    private String dataRecInicio;
    private String dataRecFim;
    @Resolvable(colName = "Data Vencimento")
    private String dataVenInicio;
    private String dataVenFim;
    @Resolvable(colName = "Data_Conciliação")
    private String dataConInicio;
    private String dataConFim;
    @Resolvable(colName = "CPF/CNPJ")
    private String cgcCpf;
     @Resolvable(colName = "Forma PGTO")
    private String formaPagamento;
     @Resolvable(colName = "Valor")
    private double valor;
    
    private int codigoCliente;
    private List<ParcelaModel> lRecebimentoAppParcela;
    /**
     * @return the idRecebimento
     */
    public int getIdRecebimento() {
        return idRecebimento;
    }

    /**
     * @param idRecebimento the idRecebimento to set
     */
    public void setIdRecebimento(int idRecebimento) {
        this.idRecebimento = idRecebimento;
    }

    /**
     * @return the sitRecebimento
     */
    public String getSitRecebimento() {
        return sitRecebimento;
    }

    /**
     * @param sitRecebimento the sitRecebimento to set
     */
    public void setSitRecebimento(String sitRecebimento) {
        this.sitRecebimento = sitRecebimento;
    }

    /**
     * @return the bandeira
     */
    public String getBandeira() {
        return bandeira;
    }

    /**
     * @param bandeira the bandeira to set
     */
    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    /**
     * @return the codigoAutorizacao
     */
    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    /**
     * @param codigoAutorizacao the codigoAutorizacao to set
     */
    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the dataGerInicio
     */
    public String getDataGerInicio() {
        return dataGerInicio;
    }

    /**
     * @param dataGerInicio the dataGerInicio to set
     */
    public void setDataGerInicio(String dataGerInicio) {
        this.dataGerInicio = dataGerInicio;
    }

    /**
     * @return the dataGerFim
     */
    public String getDataGerFim() {
        return dataGerFim;
    }

    /**
     * @param dataGerFim the dataGerFim to set
     */
    public void setDataGerFim(String dataGerFim) {
        this.dataGerFim = dataGerFim;
    }

    /**
     * @return the dataRecInicio
     */
    public String getDataRecInicio() {
        return dataRecInicio;
    }

    /**
     * @param dataRecInicio the dataRecInicio to set
     */
    public void setDataRecInicio(String dataRecInicio) {
        this.dataRecInicio = dataRecInicio;
    }

    /**
     * @return the dataRecFim
     */
    public String getDataRecFim() {
        return dataRecFim;
    }

    /**
     * @param dataRecFim the dataRecFim to set
     */
    public void setDataRecFim(String dataRecFim) {
        this.dataRecFim = dataRecFim;
    }

    /**
     * @return the dataVenInicio
     */
    public String getDataVenInicio() {
        return dataVenInicio;
    }

    /**
     * @param dataVenInicio the dataVenInicio to set
     */
    public void setDataVenInicio(String dataVenInicio) {
        this.dataVenInicio = dataVenInicio;
    }

    /**
     * @return the dataVenFim
     */
    public String getDataVenFim() {
        return dataVenFim;
    }

    /**
     * @param dataVenFim the dataVenFim to set
     */
    public void setDataVenFim(String dataVenFim) {
        this.dataVenFim = dataVenFim;
    }

    /**
     * @return the dataConInicio
     */
    public String getDataConInicio() {
        return dataConInicio;
    }

    /**
     * @param dataConInicio the dataConInicio to set
     */
    public void setDataConInicio(String dataConInicio) {
        this.dataConInicio = dataConInicio;
    }

    /**
     * @return the dataConFim
     */
    public String getDataConFim() {
        return dataConFim;
    }

    /**
     * @param dataConFim the dataConFim to set
     */
    public void setDataConFim(String dataConFim) {
        this.dataConFim = dataConFim;
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

    /**
     * @return the codigoCliente
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }

    /**
     * @param codigoCliente the codigoCliente to set
     */
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    /**
     * @return the lRecebimentoAppParcela
     */
    public List<ParcelaModel> getlRecebimentoAppParcela() {
        return lRecebimentoAppParcela;
    }

    /**
     * @param lRecebimentoAppParcela the lRecebimentoAppParcela to set
     */
    public void setlRecebimentoAppParcela(List<ParcelaModel> lRecebimentoAppParcela) {
        this.lRecebimentoAppParcela = lRecebimentoAppParcela;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the formaPagamento
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

}
