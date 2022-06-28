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
public class ParcelasModel {

    @Resolvable(colName = "Sequência")
    private int sequencia;
    @Resolvable(colName = "Forma Pagamento")
    private String formaPgto;
    @Resolvable(colName = "Tipo")
    private String tipoParcela;
    @Resolvable(colName = "Vencimento")
    private Date vencimento;
    @Resolvable(colName = "Valor")
    private double valor;

    /**
     * @return the sequencia
     */
    public int getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * @return the formaPgto
     */
    public String getFormaPgto() {
        return formaPgto;
    }

    /**
     * @param formaPgto the formaPgto to set
     */
    public void setFormaPgto(String formaPgto) {
        this.formaPgto = formaPgto;
    }

    /**
     * @return the tipoParcela
     */
    public String getTipoParcela() {
        return tipoParcela;
    }

    /**
     * @param tipoParcela the tipoParcela to set
     */
    public void setTipoParcela(String tipoParcela) {
        this.tipoParcela = tipoParcela;
    }

    /**
     * @return the vencimento
     */
    public Date getVencimento() {
        return vencimento;
    }

    /**
     * @param vencimento the vencimento to set
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
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
}
