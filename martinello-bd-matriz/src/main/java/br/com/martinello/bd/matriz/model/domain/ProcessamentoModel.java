/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author luiz.almeida
 */
public class ProcessamentoModel {

    @Resolvable(colName = "Provedor")
    private String provedor;
    @Resolvable(colName = "Tipo")
    private String tipo;
    @Resolvable(colName = "Quantidade Enviado")
    private int itensEnviados;
    @Resolvable(colName = "Quant. Env. Sucesso")
    private int itensSucesso;
    @Resolvable(colName = "Quant. Erro")
    private int itensErro;
    @Resolvable(colName = "Total de Registros")
    private int itensTotal;
    @Resolvable(colName = "Inicio do Envio")
    private String dataInicio;
    @Resolvable(colName = "Fim do Envio")
    private String dataFim;
     @Resolvable(colName = "Inicio do Envio")
    private Date dataInicioEnvio;
      @Resolvable(colName = "Fim do Envio")
    private Date dataFimEnvio;
       @Resolvable(colName = "Usuário")
    private String user;
       @Resolvable(colName = "Indice")
    private int indice;

    public ProcessamentoModel() {
    }

    public ProcessamentoModel(String provedor, String tipo, int itensEnviados, int itensSucesso, int itensErro, int itensTotal, String dataInicio, String dataFim) {
        this.provedor = provedor;
        this.tipo = tipo;
        this.itensEnviados = itensEnviados;
        this.itensSucesso = itensSucesso;
        this.itensErro = itensErro;
        this.itensTotal = itensTotal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getItensEnviados() {
        return itensEnviados;
    }

    public void setItensEnviados(int itensEnviados) {
        this.itensEnviados = itensEnviados;
    }

    public int getItensSucesso() {
        return itensSucesso;
    }

    public void setItensSucesso(int itensSucesso) {
        this.itensSucesso = itensSucesso;
    }

    public int getItensErro() {
        return itensErro;
    }

    public void setItensErro(int itensErro) {
        this.itensErro = itensErro;
    }

    public int getItensTotal() {
        return itensTotal;
    }

    public void setItensTotal(int itensTotal) {
        this.itensTotal = itensTotal;
    }    public Date getDataInicioEnvio() {
        return dataInicioEnvio;
    }

    public void setDataInicioEnvio(Date dataInicioEnvio) {
        this.dataInicioEnvio = dataInicioEnvio;
    }

    public Date getDataFimEnvio() {
        return dataFimEnvio;
    }

    public void setDataFimEnvio(Date dataFimEnvio) {
        this.dataFimEnvio = dataFimEnvio;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }
    
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessamentoModel other = (ProcessamentoModel) obj;
        if (!Objects.equals(this.provedor, other.provedor)) {
            return false;
        }
        return true;
    }

}
