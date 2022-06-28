/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util.filtro;

import java.util.Date;

/**
 *
 * @author Sidnei
 */
public class Periodo {

    private Date dataInicial, dataFinal;
    private Long lNumeroInicial, lNumeroFinal;
    private Integer iNumeroInicial, iNumeroFinal;

    public Periodo(Date dataInicial, Date dataFinal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public Periodo(Long lNumeroInicial, Long lNumeroFinal) {
        this.lNumeroInicial = lNumeroInicial;
        this.lNumeroFinal = lNumeroFinal;
    }

    public Periodo(Integer iNumeroInicial, Integer iNumeroFinal) {
        this.iNumeroInicial = iNumeroInicial;
        this.iNumeroFinal = iNumeroFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Long getlNumeroInicial() {
        return lNumeroInicial;
    }

    public void setlNumeroInicial(Long lNumeroInicial) {
        this.lNumeroInicial = lNumeroInicial;
    }

    public Long getlNumeroFinal() {
        return lNumeroFinal;
    }

    public void setlNumeroFinal(Long lNumeroFinal) {
        this.lNumeroFinal = lNumeroFinal;
    }

    public Integer getiNumeroInicial() {
        return iNumeroInicial;
    }

    public void setiNumeroInicial(Integer iNumeroInicial) {
        this.iNumeroInicial = iNumeroInicial;
    }

    public Integer getiNumeroFinal() {
        return iNumeroFinal;
    }

    public void setiNumeroFinal(Integer iNumeroFinal) {
        this.iNumeroFinal = iNumeroFinal;
    }

}
