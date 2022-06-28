/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.math.BigDecimal;

/**
 *
 * @author luiz.almeida
 */
public class ConsultaNegativacaoModel {

    @Resolvable(colName = "Nº")
    private int indice;
    @Resolvable(colName = "Id_Filial")
    private String id_Filial;
    @Resolvable(colName = "Filial")
    private String filial;
    @Resolvable(colName = "Quantidade")
    private int quantidade;
    @Resolvable(colName = "Qtd Clientes")
    private int qtdCliente;
    @Resolvable(colName = "Valor")
    private double valor;
    @Resolvable(colName = "Negativação/Cliente")
    private BigDecimal mediaClienteParcela;
    
     private String dataEnvioInicio;
    private String dataEnvioFim;
    private String dataExtracaoInicio;
    private String dataExtracaoFim;

    /**
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * @return the Id_Filial
     */
    public String getId_Filial() {
        return id_Filial;
    }

    /**
     * @param id_Filial the Id_Filial to set
     */
    public void setId_Filial(String Id_Filial) {
        this.id_Filial = Id_Filial;
    }

    /**
     * @return the Filial
     */
    public String getFilial() {
        return filial;
    }

    /**
     * @param filial the Filial to set
     */
    public void setFilial(String Filial) {
        this.filial = Filial;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
     * @return the dataEnvioInicio
     */
    public String getDataEnvioInicio() {
        return dataEnvioInicio;
    }

    /**
     * @param dataEnvioInicio the dataEnvioInicio to set
     */
    public void setDataEnvioInicio(String dataEnvioInicio) {
        this.dataEnvioInicio = dataEnvioInicio;
    }

    /**
     * @return the dataEnvioFim
     */
    public String getDataEnvioFim() {
        return dataEnvioFim;
    }

    /**
     * @param dataEnvioFim the dataEnvioFim to set
     */
    public void setDataEnvioFim(String dataEnvioFim) {
        this.dataEnvioFim = dataEnvioFim;
    }

    /**
     * @return the dataExtracaoInicio
     */
    public String getDataExtracaoInicio() {
        return dataExtracaoInicio;
    }

    /**
     * @param dataExtracaoInicio the dataExtracaoInicio to set
     */
    public void setDataExtracaoInicio(String dataExtracaoInicio) {
        this.dataExtracaoInicio = dataExtracaoInicio;
    }

    /**
     * @return the dataExtracaoFim
     */
    public String getDataExtracaoFim() {
        return dataExtracaoFim;
    }

    /**
     * @param dataExtracaoFim the dataExtracaoFim to set
     */
    public void setDataExtracaoFim(String dataExtracaoFim) {
        this.dataExtracaoFim = dataExtracaoFim;
    }

    /**
     * @return the qtdCliente
     */
    public int getQtdCliente() {
        return qtdCliente;
    }

    /**
     * @param qtdCliente the qtdCliente to set
     */
    public void setQtdCliente(int qtdCliente) {
        this.qtdCliente = qtdCliente;
    }

    /**
     * @return the mediaClienteParcela
     */
    public BigDecimal getMediaClienteParcela() {
        return mediaClienteParcela;
    }

    /**
     * @param mediaClienteParcela the mediaClienteParcela to set
     */
    public void setMediaClienteParcela(BigDecimal mediaClienteParcela) {
        this.mediaClienteParcela = mediaClienteParcela;
    }

}
