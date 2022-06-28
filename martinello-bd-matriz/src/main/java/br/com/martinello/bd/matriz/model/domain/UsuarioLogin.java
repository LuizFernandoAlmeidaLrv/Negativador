/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import br.com.martinello.bd.matriz.util.DataHoraFormatter;
import com.towel.el.annotation.Resolvable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class UsuarioLogin implements Serializable {

    
   
    @Resolvable(colName = "Id. Login")
    private Long idLogin;

    @Resolvable(colName = "Usuário")
   
    private UsuarioModel usuario;

    @Resolvable(colName = "Data Login", formatter = DataHoraFormatter.class)
    private Date dataLogin;

    @Resolvable(colName = "Nome Estação")
    private String nomeEstacao;

    @Resolvable(colName = "S.O.")
    private String sistemaOperacional;

    @Resolvable(colName = "Nome Usuário S.O.")
    private String nomeUsuarioSO;

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Date getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(Date dataLogin) {
        this.dataLogin = dataLogin;
    }

    public String getNomeEstacao() {
        return nomeEstacao;
    }

    public void setNomeEstacao(String nomeEstacao) {
        this.nomeEstacao = nomeEstacao;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getNomeUsuarioSO() {
        return nomeUsuarioSO;
    }

    public void setNomeUsuarioSO(String nomeUsuarioSO) {
        this.nomeUsuarioSO = nomeUsuarioSO;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idLogin);
        hash = 23 * hash + Objects.hashCode(this.usuario);
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
        final UsuarioLogin other = (UsuarioLogin) obj;
        if (!Objects.equals(this.idLogin, other.idLogin)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }


}
