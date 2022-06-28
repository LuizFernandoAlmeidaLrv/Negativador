/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;
import com.towel.el.annotation.Resolvable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParcelaModel {

    /**
     * @return the origemDB
     */
    public String getOrigemDB() {
        return origemDB;
    }

    /**
     * @param origemDB the origemDB to set
     */
    public void setOrigemDB(String origemDB) {
        this.origemDB = origemDB;
    }

    @Resolvable(colName = "Id Extrator")
    private String idExtrator;

    @Resolvable(colName = "Parcela")
    private String idParcela;
    
    @Resolvable(colName = "Sequência")
    private int sequencia;

    @Resolvable(colName = "Cliente")
    private String codCliente;

    @Resolvable(colName = "Filial")
    private String pontoFilial;

    @Resolvable(colName = "Contrato")
    private String numeroDoContrato;

    @Resolvable(colName = "Data Lançamento")
    private Date dataLancamento;

    @Resolvable(colName = "Data Vencimento")
    private Date dataVencimento;

    @Resolvable(colName = "Valor")
    private double valorParcela;
    
    @Resolvable(colName = "Valor Aberto")
    private double valorAberto;

    @Resolvable(colName = "Data Pagamento")
    private Date dataPagamento;

    @Resolvable(colName = "Capital Pago")
    private double capitalPago;

    @Resolvable(colName = "Situacao Cliente")
    private String situacaoDoCliente;

    @Resolvable(colName = "Data Negativação")
    private Date dataNegativacao;

    @Resolvable(colName = "Data Baixa")
    private Date dataBaixaNegativacao;

    @Resolvable(colName = "Cpf/Cnpj")
    private String cpfCnpj;

    @Resolvable(colName = "Nome")
    private String nomeDoDevedor;

    @Resolvable(colName = "Situação Parcela")
    private String situacaoParcela;

    @Resolvable(colName = "Data Alteração")
    private Date dataAlteracao;

    private boolean extrair;

    @Resolvable(colName = "Data Inclusão Spc")
    private Date dataEnvioSpc;

    @Resolvable(colName = "Data Inclusão Facmat")
    private Date dataEnvioFacmat;

    @Resolvable(colName = "Data Inclusão Avalista Spc")
    private Date dataEnvioAvalistaSpc;

    @Resolvable(colName = "Data Baixa Spc")
    private Date dataBaixaSpc;

    @Resolvable(colName = "Data Baixa Facmat")
    private Date dataBaixaFacmat;

    @Resolvable(colName = "Data Baixa Avalista Spc")
    private Date dataBaixaAvalistaSpc;

    @Resolvable(colName = "Data Extração")
    private Date dataExtracao;

    @Resolvable(colName = "Taxa de Juros")
    private BigDecimal taxaDeJuros;

    @Resolvable(colName = "Valor Juros")
    private double valorJuros;

    @Resolvable(colName = "Valor Calculado")
    private double valorCalc;

    @Resolvable(colName = "Valor Pago")
    private double valorPago;

    @Resolvable(colName = "Juros Pago")
    private double jurosPago;

    @Resolvable(colName = "N° Parcela")
    private String numeroParcela;

    @Resolvable(colName = "Tipo de Pagamento")
    private String tipoPagamento;

    @Resolvable(colName = "Data do Retorno")
    private Date dataDoRetorno;

    @Resolvable(colName = "Natureza Envio Spc")
    private String naturezaSpc;

    @Resolvable(colName = "Natureza Envio Bvs")
    private String naturezaBvs;

    @Resolvable(colName = "Motivo da Baixa Spc")
    private String motivoBaixaSpc;

    @Resolvable(colName = "Motivo da Baixa Bvs")
    private String motivoBaixaBvs;

    @Resolvable(colName = "Status Parcela")
    private String statusExtracao;

    @Resolvable(colName = "Tipo")
    private String tipo;

    @Resolvable(colName = "Data Ultima Atualização")
    private Date dataAtualizacao;

    @Resolvable(colName = "Id Registro BVS")
    private String idRegistroBvs;

    @Resolvable(colName = "Cod. Avalista")
    private String avalista;

    @Resolvable(colName = "Codigo Parcela")
    private String codigoClientParcela;
    
    @Resolvable(colName = "Tipo de Titulo")
    private String codTpt;
    
    @Resolvable(colName = "Origem DB")
    private String origemDB;
    @Resolvable(colName = "CodFil")
     private int codfil;
    

    private String motivoInclusaoBvs;
    private String naturezaRegistroBvs;
    private String naturezaInclusaoSpc;
    private String idMotivoBaixaBvs;
    private String idMotivoBaixaSpc;
    private String idMotivoInclusaoBvs;
    private String idNaturezaRegistroBvs;
    private String idNaturezaInclusaoSpc;
    private String mvtoRetornado;
    private String dataInicial;
    private String dataFinal;
    private int codemp;
  
    
    
    private List<Parcela_Movimentos> lMovimentos;

    public String getIdExtrator() {
        return idExtrator;
    }

    public void setIdExtrator(String idExtrator) {
        this.idExtrator = idExtrator;
    }

    public String getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(String idParcela) {
        this.idParcela = idParcela;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getPontoFilial() {
        return pontoFilial;
    }

    public void setPontoFilial(String pontoFilial) {
        this.pontoFilial = pontoFilial;
    }

    public String getNumeroDoContrato() {
        return numeroDoContrato;
    }

    public void setNumeroDoContrato(String numeroDoContrato) {
        this.numeroDoContrato = numeroDoContrato;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setStatusExtracao(String statusExtracao) {
        this.statusExtracao = statusExtracao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getCapitalPago() {
        return capitalPago;
    }

    public void setCapitalPago(double capitalPago) {
        this.capitalPago = capitalPago;
    }

    public String getSituacaoDoCliente() {
        return situacaoDoCliente;
    }

    public void setSituacaoDoCliente(String situacaoDoCliente) {
        this.situacaoDoCliente = situacaoDoCliente;
    }

    public Date getDataNegativacao() {
        return dataNegativacao;
    }

    public void setDataNegativacao(Date dataNegativacao) {
        this.dataNegativacao = dataNegativacao;
    }

    public Date getDataBaixaNegativacao() {
        return dataBaixaNegativacao;
    }

    public void setDataBaixaNegativacao(Date dataBaixaNegativacao) {
        this.dataBaixaNegativacao = dataBaixaNegativacao;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomeDoDevedor() {
        return nomeDoDevedor;
    }

    public void setNomeDoDevedor(String nomeDoDevedor) {
        this.nomeDoDevedor = nomeDoDevedor;
    }

    public String getSituacaoParcela() {
        return situacaoParcela;
    }

    public void setSituacaoParcela(String situacaoParcela) {
        this.situacaoParcela = situacaoParcela;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public boolean isExtrair() {
        return extrair;
    }

    public void setExtrair(boolean extrair) {
        this.extrair = extrair;
    }

    public Date getDataEnvioSpc() {
        return dataEnvioSpc;
    }

    public void setDataEnvioSpc(Date dataEnvioSpc) {
        this.dataEnvioSpc = dataEnvioSpc;
    }

    public Date getDataEnvioFacmat() {
        return dataEnvioFacmat;
    }

    public void setDataEnvioFacmat(Date dataEnvioFacmat) {
        this.dataEnvioFacmat = dataEnvioFacmat;
    }

    public Date getDataEnvioAvalistaSpc() {
        return dataEnvioAvalistaSpc;
    }

    public void setDataEnvioAvalistaSpc(Date dataEnvioAvalistaSpc) {
        this.dataEnvioAvalistaSpc = dataEnvioAvalistaSpc;
    }

    public Date getDataBaixaSpc() {
        return dataBaixaSpc;
    }

    public void setDataBaixaSpc(Date dataBaixaSpc) {
        this.dataBaixaSpc = dataBaixaSpc;
    }

    public Date getDataBaixaFacmat() {
        return dataBaixaFacmat;
    }

    public void setDataBaixaFacmat(Date dataBaixaFacmat) {
        this.dataBaixaFacmat = dataBaixaFacmat;
    }

    public Date getDataBaixaAvalistaSpc() {
        return dataBaixaAvalistaSpc;
    }

    public void setDataBaixaAvalistaSpc(Date dataBaixaAvalistaSpc) {
        this.dataBaixaAvalistaSpc = dataBaixaAvalistaSpc;
    }

    public Date getDataExtracao() {
        return dataExtracao;
    }

    public void setDataExtracao(Date dataExtracao) {
        this.dataExtracao = dataExtracao;
    }

    public BigDecimal getTaxaDeJuros() {
        return taxaDeJuros;
    }

    public void setTaxaDeJuros(BigDecimal taxaDeJuros) {
        this.taxaDeJuros = taxaDeJuros;
    }

    public double getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(double valorJuros) {
        this.valorJuros = valorJuros;
    }

    public double getValorCalc() {
        return valorCalc;
    }

    public void setValorCalc(double valorCalc) {
        this.valorCalc = valorCalc;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getJurosPago() {
        return jurosPago;
    }

    public void setJurosPago(double jurosPago) {
        this.jurosPago = jurosPago;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Date getDataDoRetorno() {
        return dataDoRetorno;
    }

    public void setDataDoRetorno(Date dataDoRetorno) {
        this.dataDoRetorno = dataDoRetorno;
    }

    public String getNaturezaSpc() {
        return naturezaSpc;
    }

    public void setNaturezaSpc(String naturezaSpc) {
        this.naturezaSpc = naturezaSpc;
    }

    public String getNaturezaBvs() {
        return naturezaBvs;
    }

    public void setNaturezaBvs(String naturezaBvs) {
        this.naturezaBvs = naturezaBvs;
    }

    public String getMotivoBaixaSpc() {
        return motivoBaixaSpc;
    }

    public void setMotivoBaixaSpc(String motivoBaixaSpc) {
        this.motivoBaixaSpc = motivoBaixaSpc;
    }

    public String getMotivoBaixaBvs() {
        return motivoBaixaBvs;
    }

    public void setMotivoBaixaBvs(String motivoBaixaBvs) {
        this.motivoBaixaBvs = motivoBaixaBvs;
    }

    public String getStatusExtracao() {
        return statusExtracao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getIdRegistroBvs() {
        return idRegistroBvs;
    }

    public void setIdRegistroBvs(String idRegistroBvs) {
        this.idRegistroBvs = idRegistroBvs;
    }

    public String getAvalista() {
        return avalista;
    }

    public void setAvalista(String avalista) {
        this.avalista = avalista;
    }

    public String getCodigoClientParcela() {
        return codigoClientParcela;
    }

    public void setCodigoClientParcela(String codigoClientParcela) {
        this.codigoClientParcela = codigoClientParcela;
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

    public String getMvtoRetornado() {
        return mvtoRetornado;
    }

    public void setMvtoRetornado(String mvtoRetornado) {
        this.mvtoRetornado = mvtoRetornado;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    /**
     * @return the valorAberto
     */
    public double getValorAberto() {
        return valorAberto;
    }

    /**
     * @param valorAberto the valorAberto to set
     */
    public void setValorAberto(double valorAberto) {
        this.valorAberto = valorAberto;
    }

    /**
     * @return the lMovimentos
     */
    public List<Parcela_Movimentos> getlMovimentos() {
        return lMovimentos;
    }

    /**
     * @param lMovimentos the lMovimentos to set
     */
    public void setlMovimentos(List<Parcela_Movimentos> lMovimentos) {
        this.lMovimentos = lMovimentos;
    }

    /**
     * @return the codTpt
     */
    public String getCodTpt() {
        return codTpt;
    }

    /**
     * @param codTpt the codTpt to set
     */
    public void setCodTpt(String codTpt) {
        this.codTpt = codTpt;
    }

    /**
     * @return the codemp
     */
    public int getCodemp() {
        return codemp;
    }

    /**
     * @param codemp the codemp to set
     */
    public void setCodemp(int codemp) {
        this.codemp = codemp;
    }

    /**
     * @return the codfil
     */
    public int getCodfil() {
        return codfil;
    }

    /**
     * @param codfil the codfil to set
     */
    public void setCodfil(int codfil) {
        this.codfil = codfil;
    }

}
