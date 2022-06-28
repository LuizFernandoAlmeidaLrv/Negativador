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
public class PessoaModel extends EnderecoModel {

    public String getIdExtrator() {
        return idExtrator;
    }

    public void setIdExtrator(String idExtrator) {
        this.idExtrator = idExtrator;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Resolvable(colName = "N°")
    private int indice;
    
    @Resolvable(colName = "Id Extrator")
    private String idExtrator;
    
    @Resolvable(colName = "Filial")
    private String pontoFilial;

    @Resolvable(colName = "Codigo Cliente")
    private String codigo;

    @Resolvable(colName = "Nome Completo")
    private String nomeRazaoSocial;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Nome do Pai")
    private String nomeDoPai;

    @Resolvable(colName = "Nome da Mãe")
    private String nomeDaMae;

    @Resolvable(colName = "Numero RG")
    private String numeroRG;

    @Resolvable(colName = "Orgão Emissor RG")
    private String orgaoEmissorRG;

    @Resolvable(colName = "Data Emissão RG")
    private Date dataEmissaoRG;

    @Resolvable(colName = "E-mail")
    private String email;

    @Resolvable(colName = "Estado Civil")
    private String estadoCivil;

    @Resolvable(colName = "Data  de Nascimento")
    private Date dataNascimento;

    @Resolvable(colName = "Tipo Pessoa")
    private String tipoPessoa;

    @Resolvable(colName = "CPF/CNPJ")
    private String cpf;

    @Resolvable(colName = "Telefone")
    private String numeroTel;

    @Resolvable(colName = "Id Cliente")
    private int idPessoa;

    @Resolvable(colName = "Id Avalista")
    private int idAvalista;

    private String negativacao;

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
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
   

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return the idPessoa
     */
    public int getIdPessoa() {
        return idPessoa;
    }

    /**
     * @param idPessoa the idPessoa to set
     */
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    /**
     * @return the idAvalista
     */
    public int getIdAvalista() {
        return idAvalista;
    }

    /**
     * @param idAvalista the idAvalista to set
     */
    public void setIdAvalista(int idAvalista) {
        this.idAvalista = idAvalista;
    }

}
