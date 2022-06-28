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
public class ImpcdlModel {

    @Resolvable(colName = "Cpf/Cnpj")
    private String impcdlCpf;

    @Resolvable(colName = "Data Vencimento")
    private Date impcdlDataVencimento;
    
    private Date impcdlDataVencimentoFinal;

    @Resolvable(colName = "Data Inclusão")
    private Date impcdlDataInclusao;

    @Resolvable(colName = "Tipo Devedor")
    private String impcdlTipoDevedor;

    @Resolvable(colName = "Nome")
    private String impcdlConsumidor;

    @Resolvable(colName = "Data Compra")
    private Date impcdlDataCompra;

    @Resolvable(colName = "Valor")
    private double impcdlValor;

    @Resolvable(colName = "Data Baixa")
    private Date impcdlDataExclusao;

    @Resolvable(colName = "Bairro")
    private String impcdlBairro;

    @Resolvable(colName = "Cidade")
    private String impcdlCidade;

    @Resolvable(colName = "Estado")
    private String impcdlUf;

    @Resolvable(colName = "Cep")
    private String impcdlCep;

    @Resolvable(colName = "Filial")
    private String impcdlFilial;

    @Resolvable(colName = "Contrato Digitado")
    private String impcdlContratoDigitado;

    @Resolvable(colName = "Contrato Real")
    private String impcdlContratoReal;

    @Resolvable(colName = "Nº")
    private String indice;

    @Resolvable(colName = "id Registro")
    private String impcdlId;

    @Resolvable(colName = "Mãe")
    private String nomeMae;

    @Resolvable(colName = "Pai")
    private String nomePai;

    @Resolvable(colName = "Logradouro")
    private String logradouro;

    @Resolvable(colName = "Data Nascimento")
    private Date impcdlDataNascimento;

    @Resolvable(colName = "Provedor")
    private String provedor;

    @Resolvable(colName = "Status")
    private String status;
    
     @Resolvable(colName = "Encontrado")
    private String encontrado;

    @Resolvable(colName = "Data Encontrado")
    private Date dataEncontrado;

    public String getImpcdlCpf() {
        return impcdlCpf;
    }

    public void setImpcdlCpf(String impcdlCpf) {
        this.impcdlCpf = impcdlCpf;
    }

    public Date getImpcdlDataVencimento() {
        return impcdlDataVencimento;
    }

    public void setImpcdlDataVencimento(Date impcdlDataVencimento) {
        this.impcdlDataVencimento = impcdlDataVencimento;
    }

    public Date getImpcdlDataInclusao() {
        return impcdlDataInclusao;
    }

    public void setImpcdlDataInclusao(Date impcdlDataInclusao) {
        this.impcdlDataInclusao = impcdlDataInclusao;
    }

    public String getImpcdlTipoDevedor() {
        return impcdlTipoDevedor;
    }

    public void setImpcdlTipoDevedor(String impcdlTipoDevedor) {
        this.impcdlTipoDevedor = impcdlTipoDevedor;
    }

    public String getImpcdlConsumidor() {
        return impcdlConsumidor;
    }

    public void setImpcdlConsumidor(String impcdlConsumidor) {
        this.impcdlConsumidor = impcdlConsumidor;
    }

    public Date getImpcdlDataCompra() {
        return impcdlDataCompra;
    }

    public void setImpcdlDataCompra(Date impcdlDataCompra) {
        this.impcdlDataCompra = impcdlDataCompra;
    }

    public double getImpcdlValor() {
        return impcdlValor;
    }

    public void setImpcdlValor(double impcdlValor) {
        this.impcdlValor = impcdlValor;
    }

    public Date getImpcdlDataExclusao() {
        return impcdlDataExclusao;
    }

    public void setImpcdlDataExclusao(Date impcdlDataExclusao) {
        this.impcdlDataExclusao = impcdlDataExclusao;
    }

    public String getImpcdlBairro() {
        return impcdlBairro;
    }

    public void setImpcdlBairro(String impcdlBairro) {
        this.impcdlBairro = impcdlBairro;
    }

    public String getImpcdlCidade() {
        return impcdlCidade;
    }

    public void setImpcdlCidade(String impcdlCidade) {
        this.impcdlCidade = impcdlCidade;
    }

    public String getImpcdlUf() {
        return impcdlUf;
    }

    public void setImpcdlUf(String impcdlUf) {
        this.impcdlUf = impcdlUf;
    }

    public String getImpcdlCep() {
        return impcdlCep;
    }

    public void setImpcdlCep(String impcdlCep) {
        this.impcdlCep = impcdlCep;
    }

    public String getImpcdlFilial() {
        return impcdlFilial;
    }

    public void setImpcdlFilial(String impcdlFilial) {
        this.impcdlFilial = impcdlFilial;
    }

    public String getImpcdlContratoDigitado() {
        return impcdlContratoDigitado;
    }

    public void setImpcdlContratoDigitado(String impcdlContratoDigitado) {
        this.impcdlContratoDigitado = impcdlContratoDigitado;
    }

    public String getImpcdlContratoReal() {
        return impcdlContratoReal;
    }

    public void setImpcdlContratoReal(String impcdlContratoReal) {
        this.impcdlContratoReal = impcdlContratoReal;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getImpcdlId() {
        return impcdlId;
    }

    public void setImpcdlId(String impcdlId) {
        this.impcdlId = impcdlId;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Date getImpcdlDataNascimento() {
        return impcdlDataNascimento;
    }

    public void setImpcdlDataNascimento(Date impcdlDataNascimento) {
        this.impcdlDataNascimento = impcdlDataNascimento;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataEncontrado() {
        return dataEncontrado;
    }

    public void setDataEncontrado(Date dataEncontrado) {
        this.dataEncontrado = dataEncontrado;
    }

    public String getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(String encontrado) {
        this.encontrado = encontrado;
    }

    public Date getImpcdlDataVencimentoFinal() {
        return impcdlDataVencimentoFinal;
    }

    public void setImpcdlDataVencimentoFinal(Date impcdlDataVencimentoFinal) {
        this.impcdlDataVencimentoFinal = impcdlDataVencimentoFinal;
    }

}
