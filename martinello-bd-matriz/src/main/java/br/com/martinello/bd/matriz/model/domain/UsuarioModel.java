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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Sidnei
 */
public class UsuarioModel implements Serializable {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Resolvable(colName = "Id. Usuário")
    private long idUsuario;
  
    @Resolvable(colName = "Nome")
    private String nome;

    @Resolvable(colName = "Login")
    private String login;

    @Resolvable(colName = "Senha")
    private String senha;
    
    @Resolvable(colName = "Status")
    private String status;

    @Resolvable(colName = "Nível Liberação")
    private String nivelLiberacao;

    @Resolvable(colName = "Data/Hora Cad.", formatter = DataHoraFormatter.class)
    private Date dataHoraCadastro;

    @Resolvable(colName = "Data/Hora Atu.", formatter = DataHoraFormatter.class)
    private Date dataHoraAtualizacao;

    private List<UsuarioLogin> logins = new LinkedList<>();

    public UsuarioModel() {
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

   


    public String getNivelLiberacao() {
        return nivelLiberacao;
    }

    public void setNivelLiberacao(String nivelLiberacao) {
        this.nivelLiberacao = nivelLiberacao;
    }

    public Date getDataHoraCadastro() {
        return dataHoraCadastro;
    }

    public void setDataHoraCadastro(Date dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }

    public Date getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(Date dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }
    public List<UsuarioLogin> getLogins() {
        return logins;
    }

    public void setLogins(List<UsuarioLogin> logins) {
        this.logins = logins;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.idUsuario ^ (this.idUsuario >>> 32));
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
        final UsuarioModel other = (UsuarioModel) obj;
        return this.idUsuario == other.idUsuario;
    }

    @Override
    public String toString() {
        return nome;
    }

}
