/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.consulta.Domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ContasReceberModel {

    @Resolvable(colName = "Venda")
    private String venda;
    
     @Resolvable(colName = "NumTit")
    private String titulo;

    @Resolvable(colName = "Tipo")
    private String tipo;

    @Resolvable(colName = "Data Lançamento")
    private Date dataLancamento;

    @Resolvable(colName = "Cliente")
    private String cliente;

    @Resolvable(colName = "Cliente Endereço")
    private String clienteEndereco;

    @Resolvable(colName = "núm. Parc.")
    private String numeroParcela;
    
    @Resolvable(colName = "Contrato")
    private String docSgl;

    @Resolvable(colName = "Vencimento")
    private Date dataVencimento;
    
     @Resolvable(colName = "Previsão PGTO")
    private Date previsaoPagamento;

    @Resolvable(colName = "Data Pagamento")
    private Date dataPagamento;

    @Resolvable(colName = "Valor")
    private Double valor;

    @Resolvable(colName = "Valor Aberto")
    private Double valorAberto;

    @Resolvable(colName = "Situação")
    private String situcao;
    
       @Resolvable(colName = "Observação")
    private String observacao;

    @Resolvable(colName = "Avalista")
    private String avalista;
    
    @Resolvable(colName = "Pgto")
     private String tipoBaixa;
     
     private int dias;
     private List<ContasReceber_Movimentos> lContasReceber_mov;

    public String getVenda() {
        return venda;
    }

    public void setVenda(String venda) {
        this.venda = venda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getClienteEndereco() {
        return clienteEndereco;
    }

    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * @return the previsaoPagamento
     */
    public Date getPrevisaoPagamento() {
        return previsaoPagamento;
    }

    /**
     * @param previsaoPagamento the previsaoPagamento to set
     */
    public void setPrevisaoPagamento(Date previsaoPagamento) {
        this.previsaoPagamento = previsaoPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorAberto() {
        return valorAberto;
    }

    public void setValorAberto(Double valorAberto) {
        this.valorAberto = valorAberto;
    }

    public String getSitucao() {
        return situcao;
    }

    public void setSitucao(String situcao) {
        this.situcao = situcao;
    }

    public String getAvalista() {
        return avalista;
    }

    public void setAvalista(String avalista) {
        this.avalista = avalista;
    }

    public String getTipoBaixa() {
        return tipoBaixa;
    }

    public void setTipoBaixa(String tipoBaixa) {
        this.tipoBaixa = tipoBaixa;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * @return the docSgl
     */
    public String getDocSgl() {
        return docSgl;
    }

    /**
     * @param docSgl the docSgl to set
     */
    public void setDocSgl(String docSgl) {
        this.docSgl = docSgl;
    }

    /**
     * @return the lContasReceber_mov
     */
    public List<ContasReceber_Movimentos> getlContasReceber_mov() {
        return lContasReceber_mov;
    }

    /**
     * @param lContasReceber_mov the lContasReceber_mov to set
     */
    public void setlContasReceber_mov(List<ContasReceber_Movimentos> lContasReceber_mov) {
        this.lContasReceber_mov = lContasReceber_mov;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
