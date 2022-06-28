/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.consulta.Domain;

import com.towel.el.annotation.Resolvable;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class VendasItensModel {

    @Resolvable(colName = "Tipo Entrega")
    private String tipoEntrega;
    @Resolvable(colName = "Produto")
    private String produto;
    @Resolvable(colName = "Estoque Depósito")
    private String estoque;
    @Resolvable(colName = "Calcula Juros")
    private String calcJuros;
    @Resolvable(colName = "Serial")
    private String serial;
    @Resolvable(colName = "Número de Série")
    private String numeroSerie;
    @Resolvable(colName = "Observação")
    private String observacao;
    @Resolvable(colName = "Local Item")
    private String local;
    @Resolvable(colName = "Local Item Loja")
    private String localLoja;
    @Resolvable(colName = "Valor A Vista")
    private double vlrAvista;
    @Resolvable(colName = "Valor Total A Vista")
    private double vlrTotAvista;
    @Resolvable(colName = "Valor Total A Prazo")
    private double vlrTotAprazo;
    @Resolvable(colName = "Quantidade")
    private int quantidade;

    

    /**
     * @return the tipoEntrega
     */
    public String getTipoEntrega() {
        return tipoEntrega;
    }

    /**
     * @param tipoEntrega the tipoEntrega to set
     */
    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    /**
     * @return the produto
     */
    public String getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(String produto) {
        this.produto = produto;
    }

    /**
     * @return the estoque
     */
    public String getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    /**
     * @return the calcJuros
     */
    public String getCalcJuros() {
        return calcJuros;
    }

    /**
     * @param calcJuros the calcJuros to set
     */
    public void setCalcJuros(String calcJuros) {
        this.calcJuros = calcJuros;
    }

    /**
     * @return the serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * @return the numeroSerie
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * @param numeroSerie the numeroSerie to set
     */
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the localLoja
     */
    public String getLocalLoja() {
        return localLoja;
    }

    /**
     * @param localLoja the localLoja to set
     */
    public void setLocalLoja(String localLoja) {
        this.localLoja = localLoja;
    }

    /**
     * @return the vlrAvista
     */
    public double getVlrAvista() {
        return vlrAvista;
    }

    /**
     * @param vlrAvista the vlrAvista to set
     */
    public void setVlrAvista(double vlrAvista) {
        this.vlrAvista = vlrAvista;
    }

    /**
     * @return the vlrTotAvista
     */
    public double getVlrTotAvista() {
        return vlrTotAvista;
    }

    /**
     * @param vlrTotAvista the vlrTotAvista to set
     */
    public void setVlrTotAvista(double vlrTotAvista) {
        this.vlrTotAvista = vlrTotAvista;
    }

    /**
     * @return the vlrTotAprazo
     */
    public double getVlrTotAprazo() {
        return vlrTotAprazo;
    }

    /**
     * @param vlrTotAprazo the vlrTotAprazo to set
     */
    public void setVlrTotAprazo(double vlrTotAprazo) {
        this.vlrTotAprazo = vlrTotAprazo;
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

   
}
