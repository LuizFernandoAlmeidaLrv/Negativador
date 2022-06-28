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
public class ReferenciasModel {

    @Resolvable(colName = "Tipo")
    private String tipo;

    @Resolvable(colName = "Data Consulta")
    private Date dataConsulta;

    @Resolvable(colName = "Referência")
    private String referencia;

    @Resolvable(colName = "Resultado")
    private String resultado;

    @Resolvable(colName = "Data Cadastro")
    private Date dataCadastro;

    @Resolvable(colName = "Data Última Compra")
    private Date dataUltCompra;

    @Resolvable(colName = "Dias Médios")
    private String diasMedios;

    @Resolvable(colName = "Valor Última Compra")
    private Double valorUltCompra;

    @Resolvable(colName = "Valor Aberto")
    private Double valorAberto;

    @Resolvable(colName = "Valor Quitado")
    private Double valorQuitado;

    @Resolvable(colName = "Existe Valor em Aberto")
    private String valorEmAberto;

    @Resolvable(colName = "Tipo Pagamento")
    private String tipoPagamento;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataUltCompra() {
        return dataUltCompra;
    }

    public void setDataUltCompra(Date dataUltCompra) {
        this.dataUltCompra = dataUltCompra;
    }

    public String getDiasMedios() {
        return diasMedios;
    }

    public void setDiasMedios(String diasMedios) {
        this.diasMedios = diasMedios;
    }

    public Double getValorUltCompra() {
        return valorUltCompra;
    }

    public void setValorUltCompra(Double valorUltCompra) {
        this.valorUltCompra = valorUltCompra;
    }

    public Double getValorAberto() {
        return valorAberto;
    }

    public void setValorAberto(Double valorAberto) {
        this.valorAberto = valorAberto;
    }

    public Double getValorQuitado() {
        return valorQuitado;
    }

    public void setValorQuitado(Double valorQuitado) {
        this.valorQuitado = valorQuitado;
    }

    public String getValorEmAberto() {
        return valorEmAberto;
    }

    public void setValorEmAberto(String valorEmAberto) {
        this.valorEmAberto = valorEmAberto;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    

}
