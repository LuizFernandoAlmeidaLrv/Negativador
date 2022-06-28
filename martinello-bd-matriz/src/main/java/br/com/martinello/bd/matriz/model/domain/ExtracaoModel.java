/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class ExtracaoModel {

    private String endereco;
    private String endLogradouro;
    private String endIdLogradouro;
    private String endNumero;
    private String endComplemento;
    private String endBairro;
    private String enderecoAval;
    private String endLogradouroAval;
    private String endIdLogradouroAval;
    private String endNumeroAval;
    private String endComplementoAval;
    private String endBairroAval;

    private Integer idCidade;
    private String nomeCidade;
    private String ufEstado;
    private String codigoIbge;
    private String nomeEstado;
    private String nomePais;
    private String siglaPais;
    private String cep;
    private String nomeCidadeAval;
    private String ufEstadoAval;
    private String codigoIbgeAval;
    private String cepAval;

    private int idParcela;
    private String numeroDoContrato;
    private String codigoClientParcela;
    private Date dataLancamento;
    private Date dataVencimento;
    private BigDecimal valorParcela;
    private Date dataPagamento;
    private BigDecimal capitalPago;
    private String situacaoDoCliente;
    private Date dataNegativacao;
    private Date dataNegativacaoSpc;
    private Date dataNegativacaoBvs;
    private Date dataBaixaNegativacao;
    private Date dataBaixaNegativacaoSpc;
    private Date dataBaixaNegativacaoBvs;
    private Date dataExtracao;
    private String taxaDeJuros;
    private BigDecimal valorJuros;
    private BigDecimal valorCalc;
    private BigDecimal valorPago;
    private BigDecimal jurosPago;
    private Date dataAlteracao;
    private String numeroParcela;
    private String tipoPagamento;
    private Date dataMovimentoRetornar;
    private String mvtoRetornado;
    private String avalista;

    private int codfil;
    private String numtit;
    private String pontoFilial;
    private Integer idCliente;
    private String codCliente;
    private String nomeRazaoSocial;
    private String negativacao;
    private String situacaoCliente;
    private String numeroDocumentoCGC;
    private String nomeDoPai;
    private String nomeDaMae;
    private char sexo;
    private String numeroRG;
    private String orgaoEmissorRG;
    private Date dataEmissaoRG;
    private String email;
    private String estadoCivil;
    private Date dataNascimento;
    private String tipoPessoa;
    private String cpf;
    private String numeroTel;
    private String idEstadoCivil;
    private String nomeRazaoSocialAval;
    private String situacaoClienteAval;
    private String numeroDocumentoCGCAval;
    private String nomeDoPaiAval;
    private String nomeDaMaeAval;
    private char sexoAval;
    private String numeroRGAval;
    private String orgaoEmissorRGAval;
    private Date dataEmissaoRGAval;
    private String emailAval;
    private String estadoCivilAval;
    private Date dataNascimentoAval;
    private String tipoPessoaAval;
    private String cpfAval;
    private String numeroTelAval;
    private String idEstadoCivilAval;
    private String origemRegistro;
    private String tipoParcela;
    private String retornoExtracao;
    private String bdOrigem;
    private String insertAvalista;
    private String observacao;

    private String motivoBaixaSpc;
    private String motivoInclusaoBvs;
    private String motivoInclusaoSpc;
    private String naturezaRegistroBvs;
    private String naturezaInclusaoSpc;
    private String idMotivoBaixaBvs;
    private String idMotivoBaixaSpc;
    private String idMotivoInclusaoBvs;
    private String idNaturezaRegistroBvs;
    private String idNaturezaInclusaoSpc;

    public String getBdOrigem() {
        return bdOrigem;
    }

    public void setBdOrigem(String bdOrigem) {
        this.bdOrigem = bdOrigem;
    }

    public String getInsertAvalista() {
        return insertAvalista;
    }

    public void setInsertAvalista(String insertAvalista) {
        this.insertAvalista = insertAvalista;
    }

    public String getMotivoInclusaoSpc() {
        return motivoInclusaoSpc;
    }

    public void setMotivoInclusaoSpc(String motivoInclusaoSpc) {
        this.motivoInclusaoSpc = motivoInclusaoSpc;
    }

    public String getMotivoBaixaSpc() {
        return motivoBaixaSpc;
    }

    public void setMotivoBaixaSpc(String motivoBaixaSpc) {
        this.motivoBaixaSpc = motivoBaixaSpc;
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

    public String getRetornoExtracao() {
        return retornoExtracao;
    }

    public void setRetornoExtracao(String retornoExtracao) {
        this.retornoExtracao = retornoExtracao;
    }

    public String getTipoParcela() {
        return tipoParcela;
    }

    public void setTipoParcela(String tipoParcela) {
        this.tipoParcela = tipoParcela;
    }

    public String getOrigemRegistro() {
        return origemRegistro;
    }

    public void setOrigemRegistro(String origemRegistro) {
        this.origemRegistro = origemRegistro;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the endLogradouro
     */
    public String getEndLogradouro() {
        return endLogradouro;
    }

    /**
     * @param endLogradouro the endLogradouro to set
     */
    public void setEndLogradouro(String endLogradouro) {
        this.endLogradouro = endLogradouro;
    }

    /**
     * @return the endIdLogradouro
     */
    public String getEndIdLogradouro() {
        return endIdLogradouro;
    }

    /**
     * @param endIdLogradouro the endIdLogradouro to set
     */
    public void setEndIdLogradouro(String endIdLogradouro) {
        this.endIdLogradouro = endIdLogradouro;
    }

    /**
     * @return the endNumero
     */
    public String getEndNumero() {
        return endNumero;
    }

    /**
     * @param endNumero the endNumero to set
     */
    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    /**
     * @return the endComplemento
     */
    public String getEndComplemento() {
        return endComplemento;
    }

    /**
     * @param endComplemento the endComplemento to set
     */
    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    /**
     * @return the endBairro
     */
    public String getEndBairro() {
        return endBairro;
    }

    /**
     * @param endBairro the endBairro to set
     */
    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    /**
     * @return the enderecoAval
     */
    public String getEnderecoAval() {
        return enderecoAval;
    }

    /**
     * @param enderecoAval the enderecoAval to set
     */
    public void setEnderecoAval(String enderecoAval) {
        this.enderecoAval = enderecoAval;
    }

    /**
     * @return the endLogradouroAval
     */
    public String getEndLogradouroAval() {
        return endLogradouroAval;
    }

    /**
     * @param endLogradouroAval the endLogradouroAval to set
     */
    public void setEndLogradouroAval(String endLogradouroAval) {
        this.endLogradouroAval = endLogradouroAval;
    }

    /**
     * @return the endIdLogradouroAval
     */
    public String getEndIdLogradouroAval() {
        return endIdLogradouroAval;
    }

    /**
     * @param endIdLogradouroAval the endIdLogradouroAval to set
     */
    public void setEndIdLogradouroAval(String endIdLogradouroAval) {
        this.endIdLogradouroAval = endIdLogradouroAval;
    }

    /**
     * @return the endNumeroAval
     */
    public String getEndNumeroAval() {
        return endNumeroAval;
    }

    /**
     * @param endNumeroAval the endNumeroAval to set
     */
    public void setEndNumeroAval(String endNumeroAval) {
        this.endNumeroAval = endNumeroAval;
    }

    /**
     * @return the endComplementoAval
     */
    public String getEndComplementoAval() {
        return endComplementoAval;
    }

    /**
     * @param endComplementoAval the endComplementoAval to set
     */
    public void setEndComplementoAval(String endComplementoAval) {
        this.endComplementoAval = endComplementoAval;
    }

    /**
     * @return the endBairroAval
     */
    public String getEndBairroAval() {
        return endBairroAval;
    }

    /**
     * @param endBairroAval the endBairroAval to set
     */
    public void setEndBairroAval(String endBairroAval) {
        this.endBairroAval = endBairroAval;
    }

    /**
     * @return the idCidade
     */
    public Integer getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the nomeCidade
     */
    public String getNomeCidade() {
        return nomeCidade;
    }

    /**
     * @param nomeCidade the nomeCidade to set
     */
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    /**
     * @return the ufEstado
     */
    public String getUfEstado() {
        return ufEstado;
    }

    /**
     * @param ufEstado the ufEstado to set
     */
    public void setUfEstado(String ufEstado) {
        this.ufEstado = ufEstado;
    }

    /**
     * @return the codigoIbge
     */
    public String getCodigoIbge() {
        return codigoIbge;
    }

    /**
     * @param codigoIbge the codigoIbge to set
     */
    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    /**
     * @return the nomeEstado
     */
    public String getNomeEstado() {
        return nomeEstado;
    }

    /**
     * @param nomeEstado the nomeEstado to set
     */
    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    /**
     * @return the nomePais
     */
    public String getNomePais() {
        return nomePais;
    }

    /**
     * @param nomePais the nomePais to set
     */
    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }

    /**
     * @return the siglaPais
     */
    public String getSiglaPais() {
        return siglaPais;
    }

    /**
     * @param siglaPais the siglaPais to set
     */
    public void setSiglaPais(String siglaPais) {
        this.siglaPais = siglaPais;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the nomeCidadeAval
     */
    public String getNomeCidadeAval() {
        return nomeCidadeAval;
    }

    /**
     * @param nomeCidadeAval the nomeCidadeAval to set
     */
    public void setNomeCidadeAval(String nomeCidadeAval) {
        this.nomeCidadeAval = nomeCidadeAval;
    }

    /**
     * @return the ufEstadoAval
     */
    public String getUfEstadoAval() {
        return ufEstadoAval;
    }

    /**
     * @param ufEstadoAval the ufEstadoAval to set
     */
    public void setUfEstadoAval(String ufEstadoAval) {
        this.ufEstadoAval = ufEstadoAval;
    }

    /**
     * @return the codigoIbgeAval
     */
    public String getCodigoIbgeAval() {
        return codigoIbgeAval;
    }

    /**
     * @param codigoIbgeAval the codigoIbgeAval to set
     */
    public void setCodigoIbgeAval(String codigoIbgeAval) {
        this.codigoIbgeAval = codigoIbgeAval;
    }

    /**
     * @return the cepAval
     */
    public String getCepAval() {
        return cepAval;
    }

    /**
     * @param cepAval the cepAval to set
     */
    public void setCepAval(String cepAval) {
        this.cepAval = cepAval;
    }

    /**
     * @return the idParcela
     */
    public int getIdParcela() {
        return idParcela;
    }

    /**
     * @param idParcela the idParcela to set
     */
    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    /**
     * @return the numeroDoContrato
     */
    public String getNumeroDoContrato() {
        return numeroDoContrato;
    }

    /**
     * @param numeroDoContrato the numeroDoContrato to set
     */
    public void setNumeroDoContrato(String numeroDoContrato) {
        this.numeroDoContrato = numeroDoContrato;
    }

    /**
     * @return the codigoClientParcela
     */
    public String getCodigoClientParcela() {
        return codigoClientParcela;
    }

    /**
     * @param codigoClientParcela the codigoClientParcela to set
     */
    public void setCodigoClientParcela(String codigoClientParcela) {
        this.codigoClientParcela = codigoClientParcela;
    }

    /**
     * @return the dataLancamento
     */
    public Date getDataLancamento() {
        return dataLancamento;
    }

    /**
     * @param dataLancamento the dataLancamento to set
     */
    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    /**
     * @return the dataVencimento
     */
    public Date getDataVencimento() {
        return dataVencimento;
    }

    /**
     * @param dataVencimento the dataVencimento to set
     */
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * @return the valorParcela
     */
    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    /**
     * @param valorParcela the valorParcela to set
     */
    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    /**
     * @return the dataPagamento
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    /**
     * @param dataPagamento the dataPagamento to set
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    /**
     * @return the capitalPago
     */
    public BigDecimal getCapitalPago() {
        return capitalPago;
    }

    /**
     * @param capitalPago the capitalPago to set
     */
    public void setCapitalPago(BigDecimal capitalPago) {
        this.capitalPago = capitalPago;
    }

    /**
     * @return the situacaoDoCliente
     */
    public String getSituacaoDoCliente() {
        return situacaoDoCliente;
    }

    /**
     * @param situacaoDoCliente the situacaoDoCliente to set
     */
    public void setSituacaoDoCliente(String situacaoDoCliente) {
        this.situacaoDoCliente = situacaoDoCliente;
    }

    /**
     * @return the dataNegativacao
     */
    public Date getDataNegativacao() {
        return dataNegativacao;
    }

    /**
     * @param dataNegativacao the dataNegativacao to set
     */
    public void setDataNegativacao(Date dataNegativacao) {
        this.dataNegativacao = dataNegativacao;
    }

    /**
     * @return the dataNegativacaoSpc
     */
    public Date getDataNegativacaoSpc() {
        return dataNegativacaoSpc;
    }

    /**
     * @param dataNegativacaoSpc the dataNegativacaoSpc to set
     */
    public void setDataNegativacaoSpc(Date dataNegativacaoSpc) {
        this.dataNegativacaoSpc = dataNegativacaoSpc;
    }

    /**
     * @return the dataNegativacaoBvs
     */
    public Date getDataNegativacaoBvs() {
        return dataNegativacaoBvs;
    }

    /**
     * @param dataNegativacaoBvs the dataNegativacaoBvs to set
     */
    public void setDataNegativacaoBvs(Date dataNegativacaoBvs) {
        this.dataNegativacaoBvs = dataNegativacaoBvs;
    }

    /**
     * @return the dataBaixaNegativacao
     */
    public Date getDataBaixaNegativacao() {
        return dataBaixaNegativacao;
    }

    /**
     * @param dataBaixaNegativacao the dataBaixaNegativacao to set
     */
    public void setDataBaixaNegativacao(Date dataBaixaNegativacao) {
        this.dataBaixaNegativacao = dataBaixaNegativacao;
    }

    /**
     * @return the dataBaixaNegativacaoSpc
     */
    public Date getDataBaixaNegativacaoSpc() {
        return dataBaixaNegativacaoSpc;
    }

    /**
     * @param dataBaixaNegativacaoSpc the dataBaixaNegativacaoSpc to set
     */
    public void setDataBaixaNegativacaoSpc(Date dataBaixaNegativacaoSpc) {
        this.dataBaixaNegativacaoSpc = dataBaixaNegativacaoSpc;
    }

    /**
     * @return the dataBaixaNegativacaoBvs
     */
    public Date getDataBaixaNegativacaoBvs() {
        return dataBaixaNegativacaoBvs;
    }

    /**
     * @param dataBaixaNegativacaoBvs the dataBaixaNegativacaoBvs to set
     */
    public void setDataBaixaNegativacaoBvs(Date dataBaixaNegativacaoBvs) {
        this.dataBaixaNegativacaoBvs = dataBaixaNegativacaoBvs;
    }

    /**
     * @return the dataExtracao
     */
    public Date getDataExtracao() {
        return dataExtracao;
    }

    /**
     * @param dataExtracao the dataExtracao to set
     */
    public void setDataExtracao(Date dataExtracao) {
        this.dataExtracao = dataExtracao;
    }

    /**
     * @return the taxaDeJuros
     */
    public String getTaxaDeJuros() {
        return taxaDeJuros;
    }

    /**
     * @param taxaDeJuros the taxaDeJuros to set
     */
    public void setTaxaDeJuros(String taxaDeJuros) {
        this.taxaDeJuros = taxaDeJuros;
    }

    /**
     * @return the valorJuros
     */
    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    /**
     * @param valorJuros the valorJuros to set
     */
    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    /**
     * @return the valorCalc
     */
    public BigDecimal getValorCalc() {
        return valorCalc;
    }

    /**
     * @param valorCalc the valorCalc to set
     */
    public void setValorCalc(BigDecimal valorCalc) {
        this.valorCalc = valorCalc;
    }

    /**
     * @return the valorPago
     */
    public BigDecimal getValorPago() {
        return valorPago;
    }

    /**
     * @param valorPago the valorPago to set
     */
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    /**
     * @return the jurosPago
     */
    public BigDecimal getJurosPago() {
        return jurosPago;
    }

    /**
     * @param jurosPago the jurosPago to set
     */
    public void setJurosPago(BigDecimal jurosPago) {
        this.jurosPago = jurosPago;
    }

    /**
     * @return the dataAlteracao
     */
    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    /**
     * @param dataAlteracao the dataAlteracao to set
     */
    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    /**
     * @return the numeroParcela
     */
    public String getNumeroParcela() {
        return numeroParcela;
    }

    /**
     * @param numeroParcela the numeroParcela to set
     */
    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    /**
     * @return the tipoPagamento
     */
    public String getTipoPagamento() {
        return tipoPagamento;
    }

    /**
     * @param tipoPagamento the tipoPagamento to set
     */
    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    /**
     * @return the dataMovimentoRetornar
     */
    public Date getDataMovimentoRetornar() {
        return dataMovimentoRetornar;
    }

    /**
     * @param dataMovimentoRetornar the dataMovimentoRetornar to set
     */
    public void setDataMovimentoRetornar(Date dataMovimentoRetornar) {
        this.dataMovimentoRetornar = dataMovimentoRetornar;
    }

    /**
     * @return the mvtoRetornado
     */
    public String getMvtoRetornado() {
        return mvtoRetornado;
    }

    /**
     * @param mvtoRetornado the mvtoRetornado to set
     */
    public void setMvtoRetornado(String mvtoRetornado) {
        this.mvtoRetornado = mvtoRetornado;
    }

    /**
     * @return the avalista
     */
    public String getAvalista() {
        return avalista;
    }

    /**
     * @param avalista the avalista to set
     */
    public void setAvalista(String avalista) {
        this.avalista = avalista;
    }

    /**
     * @return the pontoFilial
     */
    public String getPontoFilial() {
        return pontoFilial;
    }

    /**
     * @param pontoFilial the pontoFilial to set
     */
    public void setPontoFilial(String pontoFilial) {
        this.pontoFilial = pontoFilial;
    }

    /**
     * @return the codCliente
     */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * @param codCliente the codCliente to set
     */
    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    /**
     * @return the nomeRazaoSocial
     */
    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    /**
     * @param nomeRazaoSocial the nomeRazaoSocial to set
     */
    public void setNomeRazaoSocial(String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
    }

    /**
     * @return the negativacao
     */
    public String getNegativacao() {
        return negativacao;
    }

    /**
     * @param negativacao the negativacao to set
     */
    public void setNegativacao(String negativacao) {
        this.negativacao = negativacao;
    }

    /**
     * @return the situacaoCliente
     */
    public String getSituacaoCliente() {
        return situacaoCliente;
    }

    /**
     * @param situacaoCliente the situacaoCliente to set
     */
    public void setSituacaoCliente(String situacaoCliente) {
        this.situacaoCliente = situacaoCliente;
    }

    /**
     * @return the numeroDocumentoCGC
     */
    public String getNumeroDocumentoCGC() {
        return numeroDocumentoCGC;
    }

    /**
     * @param numeroDocumentoCGC the numeroDocumentoCGC to set
     */
    public void setNumeroDocumentoCGC(String numeroDocumentoCGC) {
        this.numeroDocumentoCGC = numeroDocumentoCGC;
    }

    /**
     * @return the nomeDoPai
     */
    public String getNomeDoPai() {
        return nomeDoPai;
    }

    /**
     * @param nomeDoPai the nomeDoPai to set
     */
    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    /**
     * @return the nomeDaMae
     */
    public String getNomeDaMae() {
        return nomeDaMae;
    }

    /**
     * @param nomeDaMae the nomeDaMae to set
     */
    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    /**
     * @return the sexo
     */
    public char getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the numeroRG
     */
    public String getNumeroRG() {
        return numeroRG;
    }

    /**
     * @param numeroRG the numeroRG to set
     */
    public void setNumeroRG(String numeroRG) {
        this.numeroRG = numeroRG;
    }

    /**
     * @return the orgaoEmissorRG
     */
    public String getOrgaoEmissorRG() {
        return orgaoEmissorRG;
    }

    /**
     * @param orgaoEmissorRG the orgaoEmissorRG to set
     */
    public void setOrgaoEmissorRG(String orgaoEmissorRG) {
        this.orgaoEmissorRG = orgaoEmissorRG;
    }

    /**
     * @return the dataEmissaoRG
     */
    public Date getDataEmissaoRG() {
        return dataEmissaoRG;
    }

    /**
     * @param dataEmissaoRG the dataEmissaoRG to set
     */
    public void setDataEmissaoRG(Date dataEmissaoRG) {
        this.dataEmissaoRG = dataEmissaoRG;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the estadoCivil
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the tipoPessoa
     */
    public String getTipoPessoa() {
        return tipoPessoa;
    }

    /**
     * @param tipoPessoa the tipoPessoa to set
     */
    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the numeroTel
     */
    public String getNumeroTel() {
        return numeroTel;
    }

    /**
     * @param numeroTel the numeroTel to set
     */
    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    /**
     * @return the idEstadoCivil
     */
    public String getIdEstadoCivil() {
        return idEstadoCivil;
    }

    /**
     * @param idEstadoCivil the idEstadoCivil to set
     */
    public void setIdEstadoCivil(String idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    /**
     * @return the nomeRazaoSocialAval
     */
    public String getNomeRazaoSocialAval() {
        return nomeRazaoSocialAval;
    }

    /**
     * @param nomeRazaoSocialAval the nomeRazaoSocialAval to set
     */
    public void setNomeRazaoSocialAval(String nomeRazaoSocialAval) {
        this.nomeRazaoSocialAval = nomeRazaoSocialAval;
    }

    /**
     * @return the situacaoClienteAval
     */
    public String getSituacaoClienteAval() {
        return situacaoClienteAval;
    }

    /**
     * @param situacaoClienteAval the situacaoClienteAval to set
     */
    public void setSituacaoClienteAval(String situacaoClienteAval) {
        this.situacaoClienteAval = situacaoClienteAval;
    }

    /**
     * @return the numeroDocumentoCGCAval
     */
    public String getNumeroDocumentoCGCAval() {
        return numeroDocumentoCGCAval;
    }

    /**
     * @param numeroDocumentoCGCAval the numeroDocumentoCGCAval to set
     */
    public void setNumeroDocumentoCGCAval(String numeroDocumentoCGCAval) {
        this.numeroDocumentoCGCAval = numeroDocumentoCGCAval;
    }

    /**
     * @return the nomeDoPaiAval
     */
    public String getNomeDoPaiAval() {
        return nomeDoPaiAval;
    }

    /**
     * @param nomeDoPaiAval the nomeDoPaiAval to set
     */
    public void setNomeDoPaiAval(String nomeDoPaiAval) {
        this.nomeDoPaiAval = nomeDoPaiAval;
    }

    /**
     * @return the nomeDaMaeAval
     */
    public String getNomeDaMaeAval() {
        return nomeDaMaeAval;
    }

    /**
     * @param nomeDaMaeAval the nomeDaMaeAval to set
     */
    public void setNomeDaMaeAval(String nomeDaMaeAval) {
        this.nomeDaMaeAval = nomeDaMaeAval;
    }

    /**
     * @return the sexoAval
     */
    public char getSexoAval() {
        return sexoAval;
    }

    /**
     * @param sexoAval the sexoAval to set
     */
    public void setSexoAval(char sexoAval) {
        this.sexoAval = sexoAval;
    }

    /**
     * @return the numeroRGAval
     */
    public String getNumeroRGAval() {
        return numeroRGAval;
    }

    /**
     * @param numeroRGAval the numeroRGAval to set
     */
    public void setNumeroRGAval(String numeroRGAval) {
        this.numeroRGAval = numeroRGAval;
    }

    /**
     * @return the orgaoEmissorRGAval
     */
    public String getOrgaoEmissorRGAval() {
        return orgaoEmissorRGAval;
    }

    /**
     * @param orgaoEmissorRGAval the orgaoEmissorRGAval to set
     */
    public void setOrgaoEmissorRGAval(String orgaoEmissorRGAval) {
        this.orgaoEmissorRGAval = orgaoEmissorRGAval;
    }

    /**
     * @return the dataEmissaoRGAval
     */
    public Date getDataEmissaoRGAval() {
        return dataEmissaoRGAval;
    }

    /**
     * @param dataEmissaoRGAval the dataEmissaoRGAval to set
     */
    public void setDataEmissaoRGAval(Date dataEmissaoRGAval) {
        this.dataEmissaoRGAval = dataEmissaoRGAval;
    }

    /**
     * @return the emailAval
     */
    public String getEmailAval() {
        return emailAval;
    }

    /**
     * @param emailAval the emailAval to set
     */
    public void setEmailAval(String emailAval) {
        this.emailAval = emailAval;
    }

    /**
     * @return the estadoCivilAval
     */
    public String getEstadoCivilAval() {
        return estadoCivilAval;
    }

    /**
     * @param estadoCivilAval the estadoCivilAval to set
     */
    public void setEstadoCivilAval(String estadoCivilAval) {
        this.estadoCivilAval = estadoCivilAval;
    }

    /**
     * @return the dataNascimentoAval
     */
    public Date getDataNascimentoAval() {
        return dataNascimentoAval;
    }

    /**
     * @param dataNascimentoAval the dataNascimentoAval to set
     */
    public void setDataNascimentoAval(Date dataNascimentoAval) {
        this.dataNascimentoAval = dataNascimentoAval;
    }

    /**
     * @return the tipoPessoaAval
     */
    public String getTipoPessoaAval() {
        return tipoPessoaAval;
    }

    /**
     * @param tipoPessoaAval the tipoPessoaAval to set
     */
    public void setTipoPessoaAval(String tipoPessoaAval) {
        this.tipoPessoaAval = tipoPessoaAval;
    }

    /**
     * @return the cpfAval
     */
    public String getCpfAval() {
        return cpfAval;
    }

    /**
     * @param cpfAval the cpfAval to set
     */
    public void setCpfAval(String cpfAval) {
        this.cpfAval = cpfAval;
    }

    /**
     * @return the numeroTelAval
     */
    public String getNumeroTelAval() {
        return numeroTelAval;
    }

    /**
     * @param numeroTelAval the numeroTelAval to set
     */
    public void setNumeroTelAval(String numeroTelAval) {
        this.numeroTelAval = numeroTelAval;
    }

    /**
     * @return the idEstadoCivilAval
     */
    public String getIdEstadoCivilAval() {
        return idEstadoCivilAval;
    }

    /**
     * @param idEstadoCivilAval the idEstadoCivilAval to set
     */
    public void setIdEstadoCivilAval(String idEstadoCivilAval) {
        this.idEstadoCivilAval = idEstadoCivilAval;
    }

    /**
     * @return the idCliente
     */
    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the idCliente
     */
    public Integer getIdCliente() {
        return idCliente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    /**
     * @return the numtit
     */
    public String getNumtit() {
        return numtit;
    }

    /**
     * @param numtit the numtit to set
     */
    public void setNumtit(String numtit) {
        this.numtit = numtit;
    }

}
