/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

/**
 *
 * @author luiz.almeida
 */
public class CidadeModel {
    
    private Integer idCidade;
    private String nomeCidade;
    private String UfEstado;
    private String codigoIbge;
    private String nomeEstado;
    private String nomePais;
    private String siglaPais;
    private String cep;
 

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
        return UfEstado;
    }

    public void setUfEstado(String UfEstado) {
        this.UfEstado = UfEstado;
    }

    public String getCodigoIbge() {
        return codigoIbge;
    }

    public void setCodigoIbge(String codigoIbge) {
        this.codigoIbge = codigoIbge;
    }

    public String getNomePais() {
        return nomePais;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
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
     * @return the nomepais
     */
    public String getNomepais() {
        return nomePais;
    }

    /**
     * @param nomePais the nomepais to set
     */
    public void setNomepais(String nomePais) {
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

    
}
