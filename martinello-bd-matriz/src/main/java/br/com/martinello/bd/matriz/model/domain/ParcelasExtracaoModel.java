/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasExtracaoModel {
   
    //a

    public static String myNamespace = "tem";

//    private SOAPElement soapBodyElem;
 
    private String chaveDeAcesso;
    private String keyDeAcesso;
    private String insumoParaInclusao;
    private String dataDaInclusao;
    private String dataDaEmissao;
    private String dataDoVencimento;
    private String valorTitulo;
    private String idNaturezaDaInclusao;
    private String numeroContratoCliente;
    private String idMotivoDaInclusao;
    private String numeroDocumento;
    private String nomeDoDevedor;
    private String numeroRGDoDevedor;
    private String orgaoEmissor;
    private String dataEmissaoRG;
    private String nomeDoPai;
    private String nomeDaMae;
    private String idLogradouro;
    private String endereco;
    private String bairro;
    private String idCidade;
    private String estadoUF;
    private String cepCidade;
    private String complemento;
    private String sexoPessoa;
    private String dataNascimento;
    private String telefoneDdd;
    private String numeroTelefone;
    private String enderecoEmail;
    private String tipoDePessoa;
    private String idEstadoCivil;
    private String numeroDoEndereco;
    private String incluirCoobrigado;
    private String numeroDoDocumentoCoobrigado;
    private String dataNascimentoCoobrigado;
    private String nomeDoCoobrigado;
    private String idLogradouroCoobrigado;
    private String enderecoDoCoobrigado;
    private String numeroEnderecoDoCoobrigado;
    private String bairroDoCoobrigado;
    private String idCidadeDoCoobrigado;
    private String ufEstadoDoCoobrigado;
    private String cepCidadeDoCoobrigado;
    private String enderecoEmailDoCoobrigado;
    private String telefoneDddCoobrigado;
    private String numeroTelefoneDoCoobrigado;
  
   
    
    MessageFactory messageFactory;

    public ParcelasExtracaoModel() throws SOAPException {
       

    }
/**
     * @return the chaveDeAcesso
     */
    public String getChaveDeAcesso() {
        return chaveDeAcesso;
    }

    /**
     * @return the keyDeAcesso
     */
    public String getKeyDeAcesso() {
        return keyDeAcesso;
    }

    /**
     * @param keyDeAcesso the keyDeAcesso to set
     */
    public void setKeyDeAcesso(String keyDeAcesso) {
        this.keyDeAcesso = keyDeAcesso;
    }

    /**
     * @return the insumoParaInclusao
     */
    public String getInsumoParaInclusao() {
        return insumoParaInclusao;
    }

    /**
     * @param insumoParaInclusao the insumoParaInclusao to set
     */
    public void setInsumoParaInclusao(String insumoParaInclusao) {
        this.insumoParaInclusao = insumoParaInclusao;
    }

    /**
     * @return the dataDaInclusao
     */
    public String getDataDaInclusao() {
        return dataDaInclusao;
    }

    /**
     * @param dataDaInclusao the dataDaInclusao to set
     */
    public void setDataDaInclusao(String dataDaInclusao) {
        this.dataDaInclusao = dataDaInclusao;
    }

    /**
     * @return the dataDaEmissao
     */
    public String getDataDaEmissao() {
        return dataDaEmissao;
    }

    /**
     * @param dataDaEmissao the dataDaEmissao to set
     */
    public void setDataDaEmissao(String dataDaEmissao) {
        this.dataDaEmissao = dataDaEmissao;
    }

    /**
     * @return the dataDoVencimento
     */
    public String getDataDoVencimento() {
        return dataDoVencimento;
    }

    /**
     * @param dataDoVencimento the dataDoVencimento to set
     */
    public void setDataDoVencimento(String dataDoVencimento) {
        this.dataDoVencimento = dataDoVencimento;
    }

    /**
     * @return the valorTitulo
     */
    public String getValorTitulo() {
        return valorTitulo;
    }

    /**
     * @param valorTitulo the valorTitulo to set
     */
    public void setValorTitulo(String valorTitulo) {
        this.valorTitulo = valorTitulo;
    }

    /**
     * @return the idNaturezaDaInclusao
     */
    public String getIdNaturezaDaInclusao() {
        return idNaturezaDaInclusao;
    }

    /**
     * @param idNaturezaDaInclusao the idNaturezaDaInclusao to set
     */
    public void setIdNaturezaDaInclusao(String idNaturezaDaInclusao) {
        this.idNaturezaDaInclusao = idNaturezaDaInclusao;
    }

    /**
     * @return the numeroContratoCliente
     */
    public String getNumeroContratoCliente() {
        return numeroContratoCliente;
    }

    /**
     * @param numeroContratoCliente the numeroContratoCliente to set
     */
    public void setNumeroContratoCliente(String numeroContratoCliente) {
        this.numeroContratoCliente = numeroContratoCliente;
    }

    /**
     * @return the idMotivoDaInclusao
     */
    public String getIdMotivoDaInclusao() {
        return idMotivoDaInclusao;
    }

    /**
     * @param idMotivoDaInclusao the idMotivoDaInclusao to set
     */
    public void setIdMotivoDaInclusao(String idMotivoDaInclusao) {
        this.idMotivoDaInclusao = idMotivoDaInclusao;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the nomeDoDevedor
     */
    public String getNomeDoDevedor() {
        return nomeDoDevedor;
    }

    /**
     * @param nomeDoDevedor the nomeDoDevedor to set
     */
    public void setNomeDoDevedor(String nomeDoDevedor) {
        this.nomeDoDevedor = nomeDoDevedor;
    }

    /**
     * @return the numeroRGDoDevedor
     */
    public String getNumeroRGDoDevedor() {
        return numeroRGDoDevedor;
    }

    /**
     * @param numeroRGDoDevedor the numeroRGDoDevedor to set
     */
    public void setNumeroRGDoDevedor(String numeroRGDoDevedor) {
        this.numeroRGDoDevedor = numeroRGDoDevedor;
    }

    /**
     * @return the orgaoEmissor
     */
    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    /**
     * @param orgaoEmissor the orgaoEmissor to set
     */
    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    /**
     * @return the dataEmissaoRG
     */
    public String getDataEmissaoRG() {
        return dataEmissaoRG;
    }

    /**
     * @param dataEmissaoRG the dataEmissaoRG to set
     */
    public void setDataEmissaoRG(String dataEmissaoRG) {
        this.dataEmissaoRG = dataEmissaoRG;
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
     * @return the idLogradouro
     */
    public String getIdLogradouro() {
        return idLogradouro;
    }

    /**
     * @param idLogradouro the idLogradouro to set
     */
    public void setIdLogradouro(String idLogradouro) {
        this.idLogradouro = idLogradouro;
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
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the idCidade
     */
    public String getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(String idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the estadoUF
     */
    public String getEstadoUF() {
        return estadoUF;
    }

    /**
     * @param estadoUF the estadoUF to set
     */
    public void setEstadoUF(String estadoUF) {
        this.estadoUF = estadoUF;
    }

    /**
     * @return the cepCidade
     */
    public String getCepCidade() {
        return cepCidade;
    }

    /**
     * @param cepCidade the cepCidade to set
     */
    public void setCepCidade(String cepCidade) {
        this.cepCidade = cepCidade;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the sexoPessoa
     */
    public String getSexoPessoa() {
        return sexoPessoa;
    }

    /**
     * @param sexoPessoa the sexoPessoa to set
     */
    public void setSexoPessoa(String sexoPessoa) {
        this.sexoPessoa = sexoPessoa;
    }

    /**
     * @return the dataNascimento
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the telefoneDdd
     */
    public String getTelefoneDdd() {
        return telefoneDdd;
    }

    /**
     * @param telefoneDdd the telefoneDdd to set
     */
    public void setTelefoneDdd(String telefoneDdd) {
        this.telefoneDdd = telefoneDdd;
    }

    /**
     * @return the numeroTelefone
     */
    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    /**
     * @param numeroTelefone the numeroTelefone to set
     */
    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    /**
     * @return the enderecoEmail
     */
    public String getEnderecoEmail() {
        return enderecoEmail;
    }

    /**
     * @param enderecoEmail the enderecoEmail to set
     */
    public void setEnderecoEmail(String enderecoEmail) {
        this.enderecoEmail = enderecoEmail;
    }

    /**
     * @return the tipoDePessoa
     */
    public String getTipoDePessoa() {
        return tipoDePessoa;
    }

    /**
     * @param tipoDePessoa the tipoDePessoa to set
     */
    public void setTipoDePessoa(String tipoDePessoa) {
        this.tipoDePessoa = tipoDePessoa;
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
     * @return the numeroDoEndereco
     */
    public String getNumeroDoEndereco() {
        return numeroDoEndereco;
    }

    /**
     * @param numeroDoEndereco the numeroDoEndereco to set
     */
    public void setNumeroDoEndereco(String numeroDoEndereco) {
        this.numeroDoEndereco = numeroDoEndereco;
    }

    /**
     * @return the incluirCoobrigado
     */
    public String getIncluirCoobrigado() {
        return incluirCoobrigado;
    }

    /**
     * @param incluirCoobrigado the incluirCoobrigado to set
     */
    public void setIncluirCoobrigado(String incluirCoobrigado) {
        this.incluirCoobrigado = incluirCoobrigado;
    }

    /**
     * @return the numeroDoDocumentoCoobrigado
     */
    public String getNumeroDoDocumentoCoobrigado() {
        return numeroDoDocumentoCoobrigado;
    }

    /**
     * @param numeroDoDocumentoCoobrigado the numeroDoDocumentoCoobrigado to set
     */
    public void setNumeroDoDocumentoCoobrigado(String numeroDoDocumentoCoobrigado) {
        this.numeroDoDocumentoCoobrigado = numeroDoDocumentoCoobrigado;
    }

    /**
     * @return the dataNascimentoCoobrigado
     */
    public String getDataNascimentoCoobrigado() {
        return dataNascimentoCoobrigado;
    }

    /**
     * @param dataNascimentoCoobrigado the dataNascimentoCoobrigado to set
     */
    public void setDataNascimentoCoobrigado(String dataNascimentoCoobrigado) {
        this.dataNascimentoCoobrigado = dataNascimentoCoobrigado;
    }

    /**
     * @return the nomeDoCoobrigado
     */
    public String getNomeDoCoobrigado() {
        return nomeDoCoobrigado;
    }

    /**
     * @param nomeDoCoobrigado the nomeDoCoobrigado to set
     */
    public void setNomeDoCoobrigado(String nomeDoCoobrigado) {
        this.nomeDoCoobrigado = nomeDoCoobrigado;
    }

    /**
     * @return the idLogradouroCoobrigado
     */
    public String getIdLogradouroCoobrigado() {
        return idLogradouroCoobrigado;
    }

    /**
     * @param idLogradouroCoobrigado the idLogradouroCoobrigado to set
     */
    public void setIdLogradouroCoobrigado(String idLogradouroCoobrigado) {
        this.idLogradouroCoobrigado = idLogradouroCoobrigado;
    }

    /**
     * @return the enderecoDoCoobrigado
     */
    public String getEnderecoDoCoobrigado() {
        return enderecoDoCoobrigado;
    }

    /**
     * @param enderecoDoCoobrigado the enderecoDoCoobrigado to set
     */
    public void setEnderecoDoCoobrigado(String enderecoDoCoobrigado) {
        this.enderecoDoCoobrigado = enderecoDoCoobrigado;
    }

    /**
     * @return the numeroEnderecoDoCoobrigado
     */
    public String getNumeroEnderecoDoCoobrigado() {
        return numeroEnderecoDoCoobrigado;
    }

    /**
     * @param numeroEnderecoDoCoobrigado the numeroEnderecoDoCoobrigado to set
     */
    public void setNumeroEnderecoDoCoobrigado(String numeroEnderecoDoCoobrigado) {
        this.numeroEnderecoDoCoobrigado = numeroEnderecoDoCoobrigado;
    }

    /**
     * @return the bairroDoCoobrigado
     */
    public String getBairroDoCoobrigado() {
        return bairroDoCoobrigado;
    }

    /**
     * @param bairroDoCoobrigado the bairroDoCoobrigado to set
     */
    public void setBairroDoCoobrigado(String bairroDoCoobrigado) {
        this.bairroDoCoobrigado = bairroDoCoobrigado;
    }

    /**
     * @return the idCidadeDoCoobrigado
     */
    public String getIdCidadeDoCoobrigado() {
        return idCidadeDoCoobrigado;
    }

    /**
     * @param idCidadeDoCoobrigado the idCidadeDoCoobrigado to set
     */
    public void setIdCidadeDoCoobrigado(String idCidadeDoCoobrigado) {
        this.idCidadeDoCoobrigado = idCidadeDoCoobrigado;
    }

    /**
     * @return the ufEstadoDoCoobrigado
     */
    public String getUfEstadoDoCoobrigado() {
        return ufEstadoDoCoobrigado;
    }

    /**
     * @param ufEstadoDoCoobrigado the ufEstadoDoCoobrigado to set
     */
    public void setUfEstadoDoCoobrigado(String ufEstadoDoCoobrigado) {
        this.ufEstadoDoCoobrigado = ufEstadoDoCoobrigado;
    }

    /**
     * @return the cepCidadeDoCoobrigado
     */
    public String getCepCidadeDoCoobrigado() {
        return cepCidadeDoCoobrigado;
    }

    /**
     * @param cepCidadeDoCoobrigado the cepCidadeDoCoobrigado to set
     */
    public void setCepCidadeDoCoobrigado(String cepCidadeDoCoobrigado) {
        this.cepCidadeDoCoobrigado = cepCidadeDoCoobrigado;
    }

    /**
     * @return the enderecoEmailDoCoobrigado
     */
    public String getEnderecoEmailDoCoobrigado() {
        return enderecoEmailDoCoobrigado;
    }

    /**
     * @param enderecoEmailDoCoobrigado the enderecoEmailDoCoobrigado to set
     */
    public void setEnderecoEmailDoCoobrigado(String enderecoEmailDoCoobrigado) {
        this.enderecoEmailDoCoobrigado = enderecoEmailDoCoobrigado;
    }

    /**
     * @return the telefoneDddCoobrigado
     */
    public String getTelefoneDddCoobrigado() {
        return telefoneDddCoobrigado;
    }

    /**
     * @param telefoneDddCoobrigado the telefoneDddCoobrigado to set
     */
    public void setTelefoneDddCoobrigado(String telefoneDddCoobrigado) {
        this.telefoneDddCoobrigado = telefoneDddCoobrigado;
    }

    /**
     * @return the numeroTelefoneDoCoobrigado
     */
    public String getNumeroTelefoneDoCoobrigado() {
        return numeroTelefoneDoCoobrigado;
    }

    /**
     * @param numeroTelefoneDoCoobrigado the numeroTelefoneDoCoobrigado to set
     */
    public void setNumeroTelefoneDoCoobrigado(String numeroTelefoneDoCoobrigado) {
        this.numeroTelefoneDoCoobrigado = numeroTelefoneDoCoobrigado;
    }

    /**
     * @param chaveDeAcesso the chaveDeAcesso to set
     */
    public void setChaveDeAcesso(String chaveDeAcesso) {
        this.chaveDeAcesso = chaveDeAcesso;
    }
}
