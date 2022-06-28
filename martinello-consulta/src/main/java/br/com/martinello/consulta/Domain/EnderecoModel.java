package br.com.martinello.consulta.Domain;

import com.towel.el.annotation.Resolvable;
import java.util.List;

public class EnderecoModel {

    @Resolvable(colName = "Logradouro")
    private String endLogradouro;

    @Resolvable(colName = "Id Logradouro")
    private String endIdLogradouro;

    @Resolvable(colName = "N°")
    private String endNumero;

    @Resolvable(colName = "Complemento")
    private String endComplemento;

    @Resolvable(colName = "Bairro")
    private String endBairro;

    @Resolvable(colName = "Cidade")
    private String cidade;

    @Resolvable(colName = "UF")
    private String ufEstado;

    @Resolvable(colName = "Código IBGE")
    private String codigoIbge;

    @Resolvable(colName = "CEP")
    private String cep;
    
    @Resolvable(colName = "Documento")
    private String documento;
    
    @Resolvable(colName = "Tipo")
    private String tipoDocumento;
    
     @Resolvable(colName = "Descrição")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;

    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

}
