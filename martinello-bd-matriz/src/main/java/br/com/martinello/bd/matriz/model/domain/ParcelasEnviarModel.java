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
public class ParcelasEnviarModel {

    public String getStatusSpc() {
        return statusSpc;
    }

    public void setStatusSpc(String statusSpc) {
        this.statusSpc = statusSpc;
    }

    public String getTipoParcela() {
        return tipoParcela;
    }

   
   
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
    private String cep;
    private String nomeCidadeAval;
    private String ufEstadoAval;
    private String codigoIbgeAval;
    private String cepAval;
    private int idParcela;
    private int idExtrator;
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
    private String pontoFilial;
    private Integer idCliente;
    private String codCliente;
    private String nomeRazaoSocial;
    private String negativacao;
    private String situacaoCliente;
    private String nomeDoPai;
    private String nomeDaMae;
    private String numeroRG;
    private String orgaoEmissorRG;
    private Date dataEmissaoRG;
    private String email;
    private String estadoCivil;
    private Date dataNascimento;
    private String tipoPessoa;
    private String cpf;
    private String cpfOrigem;
    private String cpfAvalOrigem;
    private String numeroTel;
    private String dddNumeroTel;
    private String idEstadoCivil;
    private String nomeRazaoSocialAval;
    private String situacaoClienteAval;
    private String nomeDoPaiAval;
    private String nomeDaMaeAval;
    private String numeroRGAval;
    private String orgaoEmissorRGAval;
    private Date dataEmissaoRGAval;
    private String emailAval;
    private String estadoCivilAval;
    private Date dataNascimentoAval;
    private String tipoPessoaAval;
    private String cpfAval;
    private String numeroTelAval;
    private String dddNumeroTelAval;
    private String idEstadoCivilAval;
    private String keyFacmat;
    private String chaveFacmat;
    private String codigoBvs;
    private String codigoFacmat;
    private String codigoSpc;
    private String codigoAssociado;
    private String tipoDevedor;
    private String tipoDevedorAval;
    private String operadorSpc;
    private String senhaSpc;
    private int idRegistroFacmat;
    private String cpfCnpj;
    private String dataInicial;
    private String dataFinal;
    private String motivoBaixaBvs;
    private String motivoBaixaSpc;
    private String motivoInclusaoBvs;
    private String naturezaRegistroBvs;
    private String naturezaInclusaoSpc;
    private String idMotivoBaixaBvs;
    private String idMotivoBaixaSpc;
    private String idMotivoInclusaoBvs;
    private String idNaturezaRegistroBvs;
    private String idNaturezaInclusaoSpc;
    private String sexo;
    private String tipoParcela;
    private String statusSpc;
    private String statusFacmat;
    
     public void setTipoParcela(String tipoParcela) {
        this.tipoParcela = tipoParcela;
    }

    
     public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
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

    public String getMotivoBaixaBvs() {
        return motivoBaixaBvs;
    }

    public void setMotivoBaixaBvs(String motivoBaixaBvs) {
        this.motivoBaixaBvs = motivoBaixaBvs;
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

    public int getIdRegistroFacmat() {
        return idRegistroFacmat;
    }

    public void setIdRegistroFacmat(int idRegistroFacmat) {
        this.idRegistroFacmat = idRegistroFacmat;
    }


    public String getEndLogradouro() {
        return endLogradouro;
    }

    public void setEndLogradouro(String endLogradouro) {
        this.endLogradouro = endLogradouro;
    }

    public String getEndIdLogradouro() {
        return endIdLogradouro;
    }

    public void setEndIdLogradouro(String endIdLogradouro) {
        this.endIdLogradouro = endIdLogradouro;
    }

    public String getEndNumero() {
        return endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndComplemento() {
        return endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndBairro() {
        return endBairro;
    }

    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    public String getEnderecoAval() {
        return enderecoAval;
    }

    public void setEnderecoAval(String enderecoAval) {
        this.enderecoAval = enderecoAval;
    }

    public String getEndLogradouroAval() {
        return endLogradouroAval;
    }

    public void setEndLogradouroAval(String endLogradouroAval) {
        this.endLogradouroAval = endLogradouroAval;
    }

    public String getEndIdLogradouroAval() {
        return endIdLogradouroAval;
    }

    public void setEndIdLogradouroAval(String endIdLogradouroAval) {
        this.endIdLogradouroAval = endIdLogradouroAval;
    }

    public String getEndNumeroAval() {
        return endNumeroAval;
    }

    public void setEndNumeroAval(String endNumeroAval) {
        this.endNumeroAval = endNumeroAval;
    }

    public String getEndComplementoAval() {
        return endComplementoAval;
    }

    public void setEndComplementoAval(String endComplementoAval) {
        this.endComplementoAval = endComplementoAval;
    }

    public String getEndBairroAval() {
        return endBairroAval;
    }

    public void setEndBairroAval(String endBairroAval) {
        this.endBairroAval = endBairroAval;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getUfEstado() {
        return ufEstado;
    }

    public void setUfEstado(String ufEstado) {
        this.ufEstado = ufEstado;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNomeCidadeAval() {
        return nomeCidadeAval;
    }

    public void setNomeCidadeAval(String nomeCidadeAval) {
        this.nomeCidadeAval = nomeCidadeAval;
    }

    public String getUfEstadoAval() {
        return ufEstadoAval;
    }

    public void setUfEstadoAval(String ufEstadoAval) {
        this.ufEstadoAval = ufEstadoAval;
    }

    public String getCodigoIbgeAval() {
        return codigoIbgeAval;
    }

    public void setCodigoIbgeAval(String codigoIbgeAval) {
        this.codigoIbgeAval = codigoIbgeAval;
    }

    public String getCepAval() {
        return cepAval;
    }

    public void setCepAval(String cepAval) {
        this.cepAval = cepAval;
    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    public String getNumeroDoContrato() {
        return numeroDoContrato;
    }

    public void setNumeroDoContrato(String numeroDoContrato) {
        this.numeroDoContrato = numeroDoContrato;
    }

    public String getCodigoClientParcela() {
        return codigoClientParcela;
    }

    public void setCodigoClientParcela(String codigoClientParcela) {
        this.codigoClientParcela = codigoClientParcela;
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

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getCapitalPago() {
        return capitalPago;
    }

    public void setCapitalPago(BigDecimal capitalPago) {
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

    public Date getDataNegativacaoSpc() {
        return dataNegativacaoSpc;
    }

    public void setDataNegativacaoSpc(Date dataNegativacaoSpc) {
        this.dataNegativacaoSpc = dataNegativacaoSpc;
    }

    public Date getDataNegativacaoBvs() {
        return dataNegativacaoBvs;
    }

    public void setDataNegativacaoBvs(Date dataNegativacaoBvs) {
        this.dataNegativacaoBvs = dataNegativacaoBvs;
    }

    public Date getDataBaixaNegativacao() {
        return dataBaixaNegativacao;
    }

    public void setDataBaixaNegativacao(Date dataBaixaNegativacao) {
        this.dataBaixaNegativacao = dataBaixaNegativacao;
    }

    public Date getDataBaixaNegativacaoSpc() {
        return dataBaixaNegativacaoSpc;
    }

    public void setDataBaixaNegativacaoSpc(Date dataBaixaNegativacaoSpc) {
        this.dataBaixaNegativacaoSpc = dataBaixaNegativacaoSpc;
    }

    public Date getDataBaixaNegativacaoBvs() {
        return dataBaixaNegativacaoBvs;
    }

    public void setDataBaixaNegativacaoBvs(Date dataBaixaNegativacaoBvs) {
        this.dataBaixaNegativacaoBvs = dataBaixaNegativacaoBvs;
    }

    public Date getDataExtracao() {
        return dataExtracao;
    }

    public void setDataExtracao(Date dataExtracao) {
        this.dataExtracao = dataExtracao;
    }

    public String getTaxaDeJuros() {
        return taxaDeJuros;
    }

    public void setTaxaDeJuros(String taxaDeJuros) {
        this.taxaDeJuros = taxaDeJuros;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorCalc() {
        return valorCalc;
    }

    public void setValorCalc(BigDecimal valorCalc) {
        this.valorCalc = valorCalc;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public BigDecimal getJurosPago() {
        return jurosPago;
    }

    public void setJurosPago(BigDecimal jurosPago) {
        this.jurosPago = jurosPago;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
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

    public Date getDataMovimentoRetornar() {
        return dataMovimentoRetornar;
    }

    public void setDataMovimentoRetornar(Date dataMovimentoRetornar) {
        this.dataMovimentoRetornar = dataMovimentoRetornar;
    }

    public String getMvtoRetornado() {
        return mvtoRetornado;
    }

    public void setMvtoRetornado(String mvtoRetornado) {
        this.mvtoRetornado = mvtoRetornado;
    }

    public String getAvalista() {
        return avalista;
    }

    public void setAvalista(String avalista) {
        this.avalista = avalista;
    }

    public String getPontoFilial() {
        return pontoFilial;
    }

    public void setPontoFilial(String pontoFilial) {
        this.pontoFilial = pontoFilial;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public void setNomeRazaoSocial(String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
    }

    public String getNegativacao() {
        return negativacao;
    }

    public void setNegativacao(String negativacao) {
        this.negativacao = negativacao;
    }

    public String getSituacaoCliente() {
        return situacaoCliente;
    }

    public void setSituacaoCliente(String situacaoCliente) {
        this.situacaoCliente = situacaoCliente;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNumeroRG() {
        return numeroRG;
    }

    public void setNumeroRG(String numeroRG) {
        this.numeroRG = numeroRG;
    }

    public String getOrgaoEmissorRG() {
        return orgaoEmissorRG;
    }

    public void setOrgaoEmissorRG(String orgaoEmissorRG) {
        this.orgaoEmissorRG = orgaoEmissorRG;
    }

    public Date getDataEmissaoRG() {
        return dataEmissaoRG;
    }

    public void setDataEmissaoRG(Date dataEmissaoRG) {
        this.dataEmissaoRG = dataEmissaoRG;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpfOrigem() {
        return cpfOrigem;
    }

    public void setCpfOrigem(String cpfOrigem) {
        this.cpfOrigem = cpfOrigem;
    }

    public String getCpfAvalOrigem() {
        return cpfAvalOrigem;
    }

    public void setCpfAvalOrigem(String cpfAvalOrigem) {
        this.cpfAvalOrigem = cpfAvalOrigem;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getDddNumeroTel() {
        return dddNumeroTel;
    }

    public void setDddNumeroTel(String dddNumeroTel) {
        this.dddNumeroTel = dddNumeroTel;
    }

    public String getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(String idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public String getNomeRazaoSocialAval() {
        return nomeRazaoSocialAval;
    }

    public void setNomeRazaoSocialAval(String nomeRazaoSocialAval) {
        this.nomeRazaoSocialAval = nomeRazaoSocialAval;
    }

    public String getSituacaoClienteAval() {
        return situacaoClienteAval;
    }

    public void setSituacaoClienteAval(String situacaoClienteAval) {
        this.situacaoClienteAval = situacaoClienteAval;
    }

    public String getNomeDoPaiAval() {
        return nomeDoPaiAval;
    }

    public void setNomeDoPaiAval(String nomeDoPaiAval) {
        this.nomeDoPaiAval = nomeDoPaiAval;
    }

    public String getNomeDaMaeAval() {
        return nomeDaMaeAval;
    }

    public void setNomeDaMaeAval(String nomeDaMaeAval) {
        this.nomeDaMaeAval = nomeDaMaeAval;
    }

    public String getNumeroRGAval() {
        return numeroRGAval;
    }

    public void setNumeroRGAval(String numeroRGAval) {
        this.numeroRGAval = numeroRGAval;
    }

    public String getOrgaoEmissorRGAval() {
        return orgaoEmissorRGAval;
    }

    public void setOrgaoEmissorRGAval(String orgaoEmissorRGAval) {
        this.orgaoEmissorRGAval = orgaoEmissorRGAval;
    }

    public Date getDataEmissaoRGAval() {
        return dataEmissaoRGAval;
    }

    public void setDataEmissaoRGAval(Date dataEmissaoRGAval) {
        this.dataEmissaoRGAval = dataEmissaoRGAval;
    }

    public String getEmailAval() {
        return emailAval;
    }

    public void setEmailAval(String emailAval) {
        this.emailAval = emailAval;
    }

    public String getEstadoCivilAval() {
        return estadoCivilAval;
    }

    public void setEstadoCivilAval(String estadoCivilAval) {
        this.estadoCivilAval = estadoCivilAval;
    }

    public Date getDataNascimentoAval() {
        return dataNascimentoAval;
    }

    public void setDataNascimentoAval(Date dataNascimentoAval) {
        this.dataNascimentoAval = dataNascimentoAval;
    }

    public String getTipoPessoaAval() {
        return tipoPessoaAval;
    }

    public void setTipoPessoaAval(String tipoPessoaAval) {
        this.tipoPessoaAval = tipoPessoaAval;
    }

    public String getCpfAval() {
        return cpfAval;
    }

    public void setCpfAval(String cpfAval) {
        this.cpfAval = cpfAval;
    }

    public String getNumeroTelAval() {
        return numeroTelAval;
    }

    public void setNumeroTelAval(String numeroTelAval) {
        this.numeroTelAval = numeroTelAval;
    }

    public String getDddNumeroTelAval() {
        return dddNumeroTelAval;
    }

    public void setDddNumeroTelAval(String dddNumeroTelAval) {
        this.dddNumeroTelAval = dddNumeroTelAval;
    }

    public String getIdEstadoCivilAval() {
        return idEstadoCivilAval;
    }

    public void setIdEstadoCivilAval(String idEstadoCivilAval) {
        this.idEstadoCivilAval = idEstadoCivilAval;
    }

    public String getKeyFacmat() {
        return keyFacmat;
    }

    public void setKeyFacmat(String keyFacmat) {
        this.keyFacmat = keyFacmat;
    }

    public String getChaveFacmat() {
        return chaveFacmat;
    }

    public void setChaveFacmat(String chaveFacmat) {
        this.chaveFacmat = chaveFacmat;
    }

    public String getCodigoBvs() {
        return codigoBvs;
    }

    public void setCodigoBvs(String codigoBvs) {
        this.codigoBvs = codigoBvs;
    }

    public String getCodigoFacmat() {
        return codigoFacmat;
    }

    public void setCodigoFacmat(String codigoFacmat) {
        this.codigoFacmat = codigoFacmat;
    }

    public String getCodigoSpc() {
        return codigoSpc;
    }

    public void setCodigoSpc(String codigoSpc) {
        this.codigoSpc = codigoSpc;
    }

    public String getCodigoAssociado() {
        return codigoAssociado;
    }

    public void setCodigoAssociado(String codigoAssociado) {
        this.codigoAssociado = codigoAssociado;
    }

    public String getTipoDevedor() {
        return tipoDevedor;
    }

    public void setTipoDevedor(String tipoDevedor) {
        this.tipoDevedor = tipoDevedor;
    }

    public String getTipoDevedorAval() {
        return tipoDevedorAval;
    }

    public void setTipoDevedorAval(String tipoDevedorAval) {
        this.tipoDevedorAval = tipoDevedorAval;
    }

    public String getOperadorSpc() {
        return operadorSpc;
    }

    public void setOperadorSpc(String operadorSpc) {
        this.operadorSpc = operadorSpc;
    }

    public String getSenhaSpc() {
        return senhaSpc;
    }

    public void setSenhaSpc(String senhaSpc) {
        this.senhaSpc = senhaSpc;
    }

    public int getIdExtrator() {
        return idExtrator;
    }

    public void setIdExtrator(int idExtrator) {
        this.idExtrator = idExtrator;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getStatusFacmat() {
        return statusFacmat;
    }

    public void setStatusFacmat(String statusFacmat) {
        this.statusFacmat = statusFacmat;
    }

}
