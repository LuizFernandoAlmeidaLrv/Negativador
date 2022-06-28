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
public class Parcela_Movimentos {

    @Resolvable(colName = "Id Contas Receber")
    private String idContasReceber;
    
    @Resolvable(colName = "Id C.R Movimento")
    private String idContasReceberMov;

    @Resolvable(colName = "Cartão Crédito")
    private String cartao;

    @Resolvable(colName = "Cheque")
    private String cheque;

    @Resolvable(colName = "Voucher")
    private String voucher;    
   
    @Resolvable(colName = "Data")
    private Date dataMov;
    
     @Resolvable(colName = "Tipo")
    private String tipoMovimento;

    @Resolvable(colName = "Valor Desconto")
    private double valorDesconto;
    
    @Resolvable(colName = "Valor Juros")
    private double valorJuros;
    
    @Resolvable(colName = "Valor Calculado")
    private double valorCalculado;
    
    @Resolvable(colName = "Valor Pago")
    private double valorPago;

    @Resolvable(colName = "Valor Capital Pago")
    private double capitalPago;
    
    @Resolvable(colName = "Valor Juros Pago")
    private double valorJurosPago;

    @Resolvable(colName = "Situação")
    private String situacao;  
    
    @Resolvable(colName = "Observação")
    private String observacao;  
    
   
    
    
    /**
     * @return the idContasReceber
     */
    public String getIdContasReceber() {
        return idContasReceber;
    }

    /**
     * @param idContasReceber the idContasReceber to set
     */
    public void setIdContasReceber(String idContasReceber) {
        this.idContasReceber = idContasReceber;
    }

    /**
     * @return the idContasReceberMov
     */
    public String getIdContasReceberMov() {
        return idContasReceberMov;
    }

    /**
     * @param idContasReceberMov the idContasReceberMov to set
     */
    public void setIdContasReceberMov(String idContasReceberMov) {
        this.idContasReceberMov = idContasReceberMov;
    }

    /**
     * @return the cartao
     */
    public String getCartao() {
        return cartao;
    }

    /**
     * @param cartao the cartao to set
     */
    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    /**
     * @return the cheque
     */
    public String getCheque() {
        return cheque;
    }

    /**
     * @param cheque the cheque to set
     */
    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    /**
     * @return the voucher
     */
    public String getVoucher() {
        return voucher;
    }

    /**
     * @param voucher the voucher to set
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    /**
     * @return the dataMov
     */
    public Date getDataMov() {
        return dataMov;
    }

    /**
     * @param dataMov the dataMov to set
     */
    public void setDataMov(Date dataMov) {
        this.dataMov = dataMov;
    }

    /**
     * @return the tipoMovimento
     */
    public String getTipoMovimento() {
        return tipoMovimento;
    }

    /**
     * @param tipoMovimento the tipoMovimento to set
     */
    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    /**
     * @return the valorDesconto
     */
    public double getValorDesconto() {
        return valorDesconto;
    }

    /**
     * @param valorDesconto the valorDesconto to set
     */
    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    /**
     * @return the valorJuros
     */
    public double getValorJuros() {
        return valorJuros;
    }

    /**
     * @param valorJuros the valorJuros to set
     */
    public void setValorJuros(double valorJuros) {
        this.valorJuros = valorJuros;
    }

    /**
     * @return the valorCalculado
     */
    public double getValorCalculado() {
        return valorCalculado;
    }

    /**
     * @param valorCalculado the valorCalculado to set
     */
    public void setValorCalculado(double valorCalculado) {
        this.valorCalculado = valorCalculado;
    }

    /**
     * @return the valorPago
     */
    public double getValorPago() {
        return valorPago;
    }

    /**
     * @param valorPago the valorPago to set
     */
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    /**
     * @return the capitalPago
     */
    public double getCapitalPago() {
        return capitalPago;
    }

    /**
     * @param capitalPago the capitalPago to set
     */
    public void setCapitalPago(double capitalPago) {
        this.capitalPago = capitalPago;
    }

    /**
     * @return the valorJurosPago
     */
    public double getValorJurosPago() {
        return valorJurosPago;
    }

    /**
     * @param valorJurosPago the valorJurosPago to set
     */
    public void setValorJurosPago(double valorJurosPago) {
        this.valorJurosPago = valorJurosPago;
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
}
