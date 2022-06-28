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
public class VendasModel {

    @Resolvable(colName = "Id. Venda")
    private int idVenda;
    @Resolvable(colName = "Id. Registro")
    private String idRegistro;
    @Resolvable(colName = "Operação")
    private String operacao;
    @Resolvable(colName = "Tipo")
    private String tipoVenda;
    @Resolvable(colName = "Data")
    private Date dataVenda;
    @Resolvable(colName = "Cliente")
    private String cliente;
    @Resolvable(colName = "Cliente Endereço")
    private String clienteEndereco;
    @Resolvable(colName = "Nome Cliente")
    private String nomeCliente;
    @Resolvable(colName = "Vendedor")
    private String vendedor;
    @Resolvable(colName = "Valor A Vista")
    private double vlrAvista;
    @Resolvable(colName = "Valor A Prazo")
    private double vlrAprazo;
    @Resolvable(colName = "Situacao")
    private String situacao;
     @Resolvable(colName = "Devolvida")
    private String devolvida;
   
    private List<VendasItensModel> lVendasItens;
    private List<ParcelasModel> lParcela;
    private List<ContasReceberModel> lContasReceber;

    /**
     * @return the idVenda
     */
    public int getIdVenda() {
        return idVenda;
    }

    /**
     * @param idVenda the idVenda to set
     */
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    /**
     * @return the operacao
     */
    public String getOperacao() {
        return operacao;
    }

    /**
     * @param operacao the operacao to set
     */
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    /**
     * @return the tipoVenda
     */
    public String getTipoVenda() {
        return tipoVenda;
    }

    /**
     * @param tipoVenda the tipoVenda to set
     */
    public void setTipoVenda(String tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    /**
     * @return the dataVenda
     */
    public Date getDataVenda() {
        return dataVenda;
    }

    /**
     * @param dataVenda the dataVenda to set
     */
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the clienteEndereco
     */
    public String getClienteEndereco() {
        return clienteEndereco;
    }

    /**
     * @param clienteEndereco the clienteEndereco to set
     */
    public void setClienteEndereco(String clienteEndereco) {
        this.clienteEndereco = clienteEndereco;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the vendedor
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the vlrAvista
     */
    public double getVlrAvista() {
        return vlrAvista;
    }

    /**
     * @param vlrAvista the vlrAvista to set
     */
    public void setVlrAvista(double vlrAvista) {
        this.vlrAvista = vlrAvista;
    }

    /**
     * @return the vlrAprazo
     */
    public double getVlrAprazo() {
        return vlrAprazo;
    }

    /**
     * @param vlrAprazo the vlrAprazo to set
     */
    public void setVlrAprazo(double vlrAprazo) {
        this.vlrAprazo = vlrAprazo;
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
     * @return the lVendasItens
     */
    public List<VendasItensModel> getlVendasItens() {
        return lVendasItens;
    }

    /**
     * @param lVendasItens the lVendasItens to set
     */
    public void setlVendasItens(List<VendasItensModel> lVendasItens) {
        this.lVendasItens = lVendasItens;
    }

    public String getDevolvida() {
        return devolvida;
    }

    public void setDevolvida(String devolvida) {
        this.devolvida = devolvida;
    }

    public List<ParcelasModel> getlParcela() {
        return lParcela;
    }

    public void setlParcela(List<ParcelasModel> lParcela) {
        this.lParcela = lParcela;
    }

    /**
     * @return the lContasReceber
     */
    public List<ContasReceberModel> getlContasReceber() {
        return lContasReceber;
    }

    /**
     * @param lContasReceber the lContasReceber to set
     */
    public void setlContasReceber(List<ContasReceberModel> lContasReceber) {
        this.lContasReceber = lContasReceber;
    }

    /**
     * @return the idRegistro
     */
    public String getIdRegistro() {
        return idRegistro;
    }

    /**
     * @param idRegistro the idRegistro to set
     */
    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }
}
