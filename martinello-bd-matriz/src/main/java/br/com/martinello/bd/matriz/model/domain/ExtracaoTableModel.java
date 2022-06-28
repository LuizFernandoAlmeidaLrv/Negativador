/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author luiz.almeida
 */
public class ExtracaoTableModel {

    @Resolvable(colName = " ")
    private Icon gravarDataMr02;

    @Resolvable(colName = "Id Cliente")
    private String idCliente;

    @Resolvable(colName = "Cód. Cliente")
    private String codigoCliente;

    @Resolvable(colName = "Id Parcela")
    private String idParcela;

    @Resolvable(colName = "Nome")
    private String nome;

    @Resolvable(colName = "Nome Avalista")
    private String nomeAvalista;

    @Resolvable(colName = "Incluir Avalista")
    private String incluirAval;

    @Resolvable(colName = "Pessoa")
    private String tipoPessoa;

    @Resolvable(colName = "Cpf/Cnpj")
    private String cpfCnpj;

    @Resolvable(colName = "Numero RG")
    private String numeroRg;

    @Resolvable(colName = "Endereco")
    private String endereco;

    @Resolvable(colName = "Cidade")
    private String cidade;

    @Resolvable(colName = "CEP")
    private String cep;

    @Resolvable(colName = "Tipo Devedor")
    private String tipoDevedor;

    @Resolvable(colName = "Filial")
    private String idFilial;

    @Resolvable(colName = "Contrato")
    private String numeroDoc;

    @Resolvable(colName = "Data Lançamento")
    private Date dataLancamento;

    @Resolvable(colName = "Data Vencimento")
    private Date dataVencimento;

    @Resolvable(colName = "Valor")
    private double valor;

    @Resolvable(colName = "Data Pagamento")
    private Date dataPagamento;

    @Resolvable(colName = "Pago")
    private String pago;

    @Resolvable(colName = "DataNegativada")
    private Date dataNegativada;

    @Resolvable(colName = "Data da Baixa")
    private Date dataBaixa;

    @Resolvable(colName = "Data Alteração")
    private Date dataAlteracao;

    @Resolvable(colName = "Parcela")
    private String numeroParcela;

    @Resolvable(colName = "Data Extração")
    private Date dataExtracao;

    @Resolvable(colName = "Data Última Atualização")
    private Date dataUltimaAtualizacao;

    @Resolvable(colName = "Avalista")
    private String codAvalista;

    @Resolvable(colName = "Id. Extrator")
    private String idExtracao;

    @Resolvable(colName = "Tipo")
    private String tipoAcao;

    @Resolvable(colName = "Status")
    private String status;

    @Resolvable(colName = "Status Provedor")
    private String statusProvedor;

    @Resolvable(colName = "Data Inclusão Spc Cliente")
    private Date dataSpcInclusao;

    @Resolvable(colName = "Data Inclusão Spc Avalista")
    private Date dataSpcAvalistaInclusao;

    @Resolvable(colName = "Data Inclusão Facmat")
    private Date dataFacmatInclusao;

    @Resolvable(colName = "Data Baixa Spc Cliente")
    private Date dataSpcBaixa;

    @Resolvable(colName = "Data Baixa Spc Avalista")
    private Date dataSpcAvalistaBaixa;

    @Resolvable(colName = "Data Baixa Facmat")
    private Date dataFacmatBaixa;

    @Resolvable(colName = "Origem")
    private String origemRegistro;

    @Resolvable(colName = "Retorno")
    private String descricaoRetorno;

    @Resolvable(colName = "Nº")
    private int indice;

    @Resolvable(colName = "Data Retorno")
    private Date dataRetorno;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Status SPC Cliente")
    private String statusSpc;

    @Resolvable(colName = "Status SPC Aval.")
    private String statusSpcAval;

    @Resolvable(colName = "Status Facmat")
    private String statusFacmat;

    @Resolvable(colName = "CodFil")
    private int codfil;
    @Resolvable(colName = "Numero do Titulo")
    private String numtit;
    @Resolvable(colName = "Tipo do Titulo")
    private String codTpt;
     @Resolvable(colName = "Base de Dados")
    private String origemBd;

    private String dataEnvioInicio;
    private String dataEnvioFim;
    private String dataExtracaoInicio;
    private String dataExtracaoFim;

    public ExtracaoTableModel() {

    }

    public ExtracaoTableModel(Icon gravarDataMr02, String idCliente, String codigoCliente, String idParcela, String nome, String nomeAvalista, String tipoPessoa, String cpfCnpj, String numeroRg, String endereco, String cidade, String cep, String tipoDevedor, String idFilial, String numeroDoc, Date dataLancamento, Date dataVencimento, double valor, Date dataPagamento, String pago, Date dataNegativada, Date dataBaixa, Date dataAlteracao, String numeroParcela, Date dataExtracao, Date dataUltimaAtualizacao, String codAvalista, String idExtracao, String tipoAcao, String status, String statusProvedor, Date dataSpcInclusao, Date dataSpcAvalistaInclusao, Date dataFacmatInclusao, Date dataSpcBaixa, Date dataSpcAvalistaBaixa, Date dataFacmatBaixa, String origemRegistro, String descricaoRetorno, int indice, Date dataRetorno, String situacao, String statusSpc, String statusSpcAval, String statusFacmat, String dataEnvioInicio, String dataEnvioFim, String dataExtracaoInicio, String dataExtracaoFim) {
        this.gravarDataMr02 = gravarDataMr02;
        this.idCliente = idCliente;
        this.codigoCliente = codigoCliente;
        this.idParcela = idParcela;
        this.nome = nome;
        this.nomeAvalista = nomeAvalista;
        this.tipoPessoa = tipoPessoa;
        this.cpfCnpj = cpfCnpj;
        this.numeroRg = numeroRg;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.tipoDevedor = tipoDevedor;
        this.idFilial = idFilial;
        this.numeroDoc = numeroDoc;
        this.dataLancamento = dataLancamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.pago = pago;
        this.dataNegativada = dataNegativada;
        this.dataBaixa = dataBaixa;
        this.dataAlteracao = dataAlteracao;
        this.numeroParcela = numeroParcela;
        this.dataExtracao = dataExtracao;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.codAvalista = codAvalista;
        this.idExtracao = idExtracao;
        this.tipoAcao = tipoAcao;
        this.status = status;
        this.statusProvedor = statusProvedor;
        this.dataSpcInclusao = dataSpcInclusao;
        this.dataSpcAvalistaInclusao = dataSpcAvalistaInclusao;
        this.dataFacmatInclusao = dataFacmatInclusao;
        this.dataSpcBaixa = dataSpcBaixa;
        this.dataSpcAvalistaBaixa = dataSpcAvalistaBaixa;
        this.dataFacmatBaixa = dataFacmatBaixa;
        this.origemRegistro = origemRegistro;
        this.descricaoRetorno = descricaoRetorno;
        this.indice = indice;
        this.dataRetorno = dataRetorno;
        this.situacao = situacao;
        this.statusSpc = statusSpc;
        this.statusSpcAval = statusSpcAval;
        this.statusFacmat = statusFacmat;
        this.dataEnvioInicio = dataEnvioInicio;
        this.dataEnvioFim = dataEnvioFim;
        this.dataExtracaoInicio = dataExtracaoInicio;
        this.dataExtracaoFim = dataExtracaoFim;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(String idParcela) {
        this.idParcela = idParcela;
    }

    /**
     * @return the idCliente
     */
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNumeroRg() {
        return numeroRg;
    }

    public void setNumeroRg(String numeroRg) {
        this.numeroRg = numeroRg;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDevedor() {
        return tipoDevedor;
    }

    public void setTipoDevedor(String tipoDevedor) {
        this.tipoDevedor = tipoDevedor;
    }

    public String getIdFilial() {
        return idFilial;
    }

    public void setIdFilial(String idFilial) {
        this.idFilial = idFilial;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataNegativada() {
        return dataNegativada;
    }

    public void setDataNegativada(Date dataNegativada) {
        this.dataNegativada = dataNegativada;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
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

    public Date getDataExtracao() {
        return dataExtracao;
    }

    public void setDataExtracao(Date dataExtracao) {
        this.dataExtracao = dataExtracao;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public String getStatusSpc() {
        return statusSpc;
    }

    public void setStatusSpc(String statusSpc) {
        this.statusSpc = statusSpc;
    }

    public String getStatusSpcAval() {
        return statusSpcAval;
    }

    public void setStatusSpcAval(String statusSpcAval) {
        this.statusSpcAval = statusSpcAval;
    }

    public String getStatusFacmat() {
        return statusFacmat;
    }

    public void setStatusFacmat(String statusFacmat) {
        this.statusFacmat = statusFacmat;
    }

    /**
     * @param dataUltimaAtualizacao the dataUltimaAtualizacao to set
     */
    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    /**
     * @return the idExtracao
     */
    public String getIdExtracao() {
        return idExtracao;
    }

    /**
     * @param idExtracao the idExtracao to set
     */
    public void setIdExtracao(String idExtracao) {
        this.idExtracao = idExtracao;
    }

    /**
     * @return the tipoAcao
     */
    public String getTipoAcao() {
        return tipoAcao;
    }

    /**
     * @param tipoAcao the tipoAcao to set
     */
    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the origemRegistro
     */
    public String getOrigemRegistro() {
        return origemRegistro;
    }

    /**
     * @param origemRegistro the origemRegistro to set
     */
    public void setOrigemRegistro(String origemRegistro) {
        this.origemRegistro = origemRegistro;
    }

    /**
     * @return the descricaoRetorno
     */
    public String getDescricaoRetorno() {
        return descricaoRetorno;
    }

    /**
     * @param descricaoRetorno the descricaoRetorno to set
     */
    public void setDescricaoRetorno(String descricaoRetorno) {
        this.descricaoRetorno = descricaoRetorno;
    }

    public String getDataEnvioInicio() {
        return dataEnvioInicio;
    }

    public void setDataEnvioInicio(String dataEnvioInicio) {
        this.dataEnvioInicio = dataEnvioInicio;
    }

    public String getDataEnvioFim() {
        return dataEnvioFim;
    }

    public void setDataEnvioFim(String dataEnvioFim) {
        this.dataEnvioFim = dataEnvioFim;
    }

    public String getDataExtracaoInicio() {
        return dataExtracaoInicio;
    }

    public void setDataExtracaoInicio(String dataExtracaoInicio) {
        this.dataExtracaoInicio = dataExtracaoInicio;
    }

    public String getDataExtracaoFim() {
        return dataExtracaoFim;
    }

    public void setDataExtracaoFim(String dataExtracaoFim) {
        this.dataExtracaoFim = dataExtracaoFim;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Icon getGravarDataMr02() {
        return gravarDataMr02;
    }

    public void setGravarDataMr02(Icon gravarDataMr02) {
        this.gravarDataMr02 = gravarDataMr02;
    }

    public String getNomeAvalista() {
        return nomeAvalista;
    }

    public void setNomeAvalista(String nomeAvalista) {
        this.nomeAvalista = nomeAvalista;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Date getDataSpcInclusao() {
        return dataSpcInclusao;
    }

    public void setDataSpcInclusao(Date dataSpcInclusao) {
        this.dataSpcInclusao = dataSpcInclusao;
    }

    public Date getDataSpcAvalistaInclusao() {
        return dataSpcAvalistaInclusao;
    }

    public void setDataSpcAvalistaInclusao(Date dataSpcAvalistaInclusao) {
        this.dataSpcAvalistaInclusao = dataSpcAvalistaInclusao;
    }

    public Date getDataFacmatInclusao() {
        return dataFacmatInclusao;
    }

    public void setDataFacmatInclusao(Date dataFacmatInclusao) {
        this.dataFacmatInclusao = dataFacmatInclusao;
    }

    public Date getDataSpcBaixa() {
        return dataSpcBaixa;
    }

    public void setDataSpcBaixa(Date dataSpcBaixa) {
        this.dataSpcBaixa = dataSpcBaixa;
    }

    public Date getDataSpcAvalistaBaixa() {
        return dataSpcAvalistaBaixa;
    }

    public void setDataSpcAvalistaBaixa(Date dataSpcAvalistaBaixa) {
        this.dataSpcAvalistaBaixa = dataSpcAvalistaBaixa;
    }

    public Date getDataFacmatBaixa() {
        return dataFacmatBaixa;
    }

    public void setDataFacmatBaixa(Date dataFacmatBaixa) {
        this.dataFacmatBaixa = dataFacmatBaixa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getStatusProvedor() {
        return statusProvedor;
    }

    public void setStatusProvedor(String statusProvedor) {
        this.statusProvedor = statusProvedor;
    }

    public String getCodAvalista() {
        return codAvalista;
    }

    public void setCodAvalista(String codAvalista) {
        this.codAvalista = codAvalista;
    }

    public String getIncluirAval() {
        return incluirAval;
    }

    public void setIncluirAval(String incluirAval) {
        this.incluirAval = incluirAval;
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

    /**
     * @return the origemBd
     */
    public String getOrigemBd() {
        return origemBd;
    }

    /**
     * @param origemBd the origemBd to set
     */
    public void setOrigemBd(String origemBd) {
        this.origemBd = origemBd;
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

}
