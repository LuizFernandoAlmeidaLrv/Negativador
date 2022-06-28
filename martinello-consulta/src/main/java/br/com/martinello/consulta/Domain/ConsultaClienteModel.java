/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.consulta.Domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ConsultaClienteModel {

    private PessoaModel cliente;
    private PessoaModel clienteConjuge;
    private String tipoRresidencia;
    private String descRendaComplementar;
    private Date dataResidencia;
    private Double renda;
    private Double rendaComplementar;
    private Double despesasMoradia;
    private Double despesasMensal;
    private int numeroDependentes;
    private Double valorPatrimnonio;
    private String avalista;
    private List<EnderecoModel> lEnderecos;
    private List<ReferenciasModel> lReferencias;
    private List<ConsultaSpcBvsModel> lConsulta;
    private List<DadosComerciaisModel> lDadosComericiais;
    private List<HistoricoCliente> lHistoricoClientes;
    private List<ObservacaoModel> lObservacoes;

    public PessoaModel getCliente() {
        return cliente;
    }

    public void setCliente(PessoaModel cliente) {
        this.cliente = cliente;
    }

    public Date getDataResidencia() {
        return dataResidencia;
    }

    public void setDataResidencia(Date dataResidencia) {
        this.dataResidencia = dataResidencia;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public Double getRendaComplementar() {
        return rendaComplementar;
    }

    public void setRendaComplementar(Double rendaComplementar) {
        this.rendaComplementar = rendaComplementar;
    }

    public Double getDespesasMoradia() {
        return despesasMoradia;
    }

    public void setDespesasMoradia(Double despesasMoradia) {
        this.despesasMoradia = despesasMoradia;
    }

    public Double getDespesasMensal() {
        return despesasMensal;
    }

    public void setDespesasMensal(Double despesasMensal) {
        this.despesasMensal = despesasMensal;
    }

    public int getNumeroDependentes() {
        return numeroDependentes;
    }

    public void setNumeroDependentes(int numeroDependentes) {
        this.numeroDependentes = numeroDependentes;
    }

    public Double getValorPatrimnonio() {
        return valorPatrimnonio;
    }

    public void setValorPatrimnonio(Double valorPatrimnonio) {
        this.valorPatrimnonio = valorPatrimnonio;
    }

    public String getAvalista() {
        return avalista;
    }

    public void setAvalista(String avalista) {
        this.avalista = avalista;
    }

    public List<EnderecoModel> getlEnderecos() {
        return lEnderecos;
    }

    public void setlEnderecos(List<EnderecoModel> lEnderecos) {
        this.lEnderecos = lEnderecos;
    }

    public List<ReferenciasModel> getlReferencias() {
        return lReferencias;
    }

    public void setlReferencias(List<ReferenciasModel> lReferencias) {
        this.lReferencias = lReferencias;
    }

    public List<ConsultaSpcBvsModel> getlConsulta() {
        return lConsulta;
    }

    public void setlConsulta(List<ConsultaSpcBvsModel> lConsulta) {
        this.lConsulta = lConsulta;
    }

    public List<DadosComerciaisModel> getlDadosComericiais() {
        return lDadosComericiais;
    }

    public void setlDadosComericiais(List<DadosComerciaisModel> lDadosComericiais) {
        this.lDadosComericiais = lDadosComericiais;
    }

    public List<HistoricoCliente> getlHistoricoClientes() {
        return lHistoricoClientes;
    }

    public void setlHistoricoClientes(List<HistoricoCliente> lHistoricoClientes) {
        this.lHistoricoClientes = lHistoricoClientes;
    }

    public List<ObservacaoModel> getlObservacoes() {
        return lObservacoes;
    }

    public void setlObservacoes(List<ObservacaoModel> lObservacoes) {
        this.lObservacoes = lObservacoes;
    }

    public PessoaModel getClienteConjuge() {
        return clienteConjuge;
    }

    public void setClienteConjuge(PessoaModel clienteConjuge) {
        this.clienteConjuge = clienteConjuge;
    }

    public String getTipoRresidencia() {
        return tipoRresidencia;
    }

    public void setTipoRresidencia(String tipoRresidencia) {
        this.tipoRresidencia = tipoRresidencia;
    }

    /**
     * @return the descRendaComplementar
     */
    public String getDescRendaComplementar() {
        return descRendaComplementar;
    }

    /**
     * @param descRendaComplementar the descRendaComplementar to set
     */
    public void setDescRendaComplementar(String descRendaComplementar) {
        this.descRendaComplementar = descRendaComplementar;
    }
}
