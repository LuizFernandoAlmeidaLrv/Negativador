/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import com.towel.el.annotation.Resolvable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class FilialModel {
    
     @Resolvable(colName = "Nome")
    private String nome;

    @Resolvable(colName = "Filial")
    private String filial;

    @Resolvable(colName = "Email")
    private String email;

    @Resolvable(colName = "ChaveFacmat")
    private String chaveFacmat;

    @Resolvable(colName = "KeyFacmat")
    private String keyFacmat;

    @Resolvable(colName = "Cod. BVS")
    private String codigoBvs;

    @Resolvable(colName = "Cod. Facmat")
    private String codigoFacmat;

    @Resolvable(colName = "Cod. SPC")
    private String codigoSpc;

    @Resolvable(colName = "Operador SPC")
    private String operadorSpc;

    @Resolvable(colName = "Senha SPC")
    private String senhaSpc;

    @Resolvable(colName = "CNPJ")
    private String cnpjLoja;

    @Resolvable(colName = "Inicio Operacao")
    private Date datainicioOperacao;

    @Resolvable(colName = "Inicio Db Novo")
    private Date dataOperacaoDbNovo;

    @Resolvable(colName = "Status")
    private String status;

    @Resolvable(colName = "Status Integração")
    private String statusIntegracao;

    @Resolvable(colName = "Status Extração")
    private String statusExtracao;
    
    @Resolvable(colName = "Status Spc")
    private String statusSpc;

    @Resolvable(colName = "Status Facmat")
    private String statusFacmat;

    @Resolvable(colName = "Usuário")
    private String usuario;

    @Resolvable(colName = "Data Ultima Alteração")
    private Date dataUltAlteracao;
    
     @Resolvable(colName = "Filial Sgl")
    private String filialSgl;
     
      @Resolvable(colName = "CodFil")
    private String codFil;
     
    @Resolvable(colName = "Origem_BD")
    private String origemBD;

    public String getFilialSgl() {
        return filialSgl;
    }

    public void setFilialSgl(String filialSgl) {
        this.filialSgl = filialSgl;
    }

    public String getStatusSpc() {
        return statusSpc;
    }

    public void setStatusSpc(String statusSpc) {
        this.statusSpc = statusSpc;
    }

    public String getStatusFacmat() {
        return statusFacmat;
    }

    public void setStatusFacmat(String statusFacmat) {
        this.statusFacmat = statusFacmat;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getDataUltAlteracao() {
        return dataUltAlteracao;
    }

    public void setDataUltAlteracao(Date dataUltAlteracao) {
        this.dataUltAlteracao = dataUltAlteracao;
    }

   

    public FilialModel(String nomeLoja, String pontoFilial, String chaveFacmat, String keyFacmat, String codigoBvs, String codigoFacmat, String codigoSpc, String operadorSpc, String senhaSpc, String cnpjLoja, Date datainicioOperacao, Date dataOperacaoDbNovo, String status) {
        this.nome = nomeLoja;
        this.filial = pontoFilial;
        this.chaveFacmat = chaveFacmat;
        this.keyFacmat = keyFacmat;
        this.codigoBvs = codigoBvs;
        this.codigoFacmat = codigoFacmat;
        this.codigoSpc = codigoSpc;
        this.operadorSpc = operadorSpc;
        this.senhaSpc = senhaSpc;
        this.cnpjLoja = cnpjLoja;
        this.datainicioOperacao = datainicioOperacao;
        this.dataOperacaoDbNovo = dataOperacaoDbNovo;
        this.status = status;
    }

    public FilialModel(String nomeLoja, String pontoFilial) {
        this.nome = nomeLoja;
        this.filial = pontoFilial;
    }

    public FilialModel() {
    }

    public String getNomeLoja() {
        return nome;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nome = nomeLoja;
    }

    public String getPontoFilial() {
        return filial;
    }

    public void setPontoFilial(String pontoFilial) {
        this.filial = pontoFilial;
    }

    public String getChaveFacmat() {
        return chaveFacmat;
    }

    public void setChaveFacmat(String chaveFacmat) {
        this.chaveFacmat = chaveFacmat;
    }

    public String getKeyFacmat() {
        return keyFacmat;
    }

    public void setKeyFacmat(String keyFacmat) {
        this.keyFacmat = keyFacmat;
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

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public Date getDatainicioOperacao() {
        return datainicioOperacao;
    }

    public void setDatainicioOperacao(Date datainicioOperacao) {
        this.datainicioOperacao = datainicioOperacao;
    }

    public Date getDataOperacaoDbNovo() {
        return dataOperacaoDbNovo;
    }

    public void setDataOperacaoDbNovo(Date dataOperacaoDbNovo) {
        this.dataOperacaoDbNovo = dataOperacaoDbNovo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(String statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public String getStatusExtracao() {
        return statusExtracao;
    }

    public void setStatusExtracao(String statusExtracao) {
        this.statusExtracao = statusExtracao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getNomeLoja(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula, retorna null -
     * para facilitar em casos onde formulários podem ter campos de datas vazios.
     *
     * @param data String no formato dd/MM/yyyy a ser formatada
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula
     * @throws Exception Caso a String esteja no formato errado
     */
    /**
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula, retorna null -
     * para facilitar em casos onde formulários podem ter campos de datas vazios.
     *
     * @param data String no formato dd/MM/yyyy a ser formatada
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula
     * @throws Exception Caso a String esteja no formato errado
     */
    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getOrigemBD() {
        return origemBD;
    }

    public void setOrigemBD(String origemBD) {
        this.origemBD = origemBD;
    }

    /**
     * @return the codFil
     */
    public String getCodFil() {
        return codFil;
    }

    /**
     * @param codFil the codFil to set
     */
    public void setCodFil(String codFil) {
        this.codFil = codFil;
    }
}
