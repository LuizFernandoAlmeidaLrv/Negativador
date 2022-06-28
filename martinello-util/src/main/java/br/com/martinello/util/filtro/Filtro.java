/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util.filtro;

import java.util.Objects;

/**
 *
 * @author Sidnei
 */
public class Filtro {

    public static String DATE = "Date";
    public static String TIMESTAMP = "Timestamp";
    public static String STRING = "String";
    public static String LONG = "Long";
    public static String DOUBLE = "Double";
    public static String INTEGER = "Integer";
    public static String ARRAY = "Array";
    public static String CAIXA = "Caixa";
    public static String LOJA = "Loja";
    public static String USUARIO = "Usuario";

    public static String IGUAL = "Igual";
    public static String MAIOR = "Maior";
    public static String MENOR = "Menor";
    public static String PERIODO = "Periodo";
    public static String INICIANDO = "Iniciando";
    public static String CONTENDO = "Contendo";
    public static String ISNULL = "IsNull";
    public static String ISNOTNULL = "IsNotNull";

    private String campo;
    private String formato;
    private String tipo;
    private Object valor;

    public Filtro(String campo, String formato, String tipo, Object valor) {
        this.campo = campo;
        this.formato = formato;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.campo);
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
        final Filtro other = (Filtro) obj;
        if (!Objects.equals(this.campo, other.campo)) {
            return false;
        }
        return true;
    }

}
