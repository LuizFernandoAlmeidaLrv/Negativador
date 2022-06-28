/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.util.ValidacaoCpfCnpj;

/**
 *
 * @author Sidnei
 */
public class CampoCpfCnpj extends CampoMascara {

    private static String maskaraCpf = "***.***.***-**";
    private static String maskaraCpnj = "**.***.***/****-**";
    private static String maskaraEstrangeiro = "*****************************";
    private static String caracteresValidos = "0123456789 ";
    private static String caracteresValidosEstrangeiro = "0123456789.-/abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
    private String tipo = "CPF";

    public CampoCpfCnpj() {
        this(null, null, false, "CPF", null);
    }

    public CampoCpfCnpj(Class tabela, String campo, String tipo) {
        this(tabela, campo, false, tipo, null);
    }

    public CampoCpfCnpj(Class tabela, String campo, boolean obrigatorio, String tipo, Rotulo rotulo) {
        super(tabela, campo, obrigatorio, tipo.equals("CPF") ? maskaraCpf : tipo.equals("CNPJ") ? maskaraCpnj : maskaraEstrangeiro, rotulo);
        setCaracteresValidos(tipo.equals("CPF") | tipo.equals("CNPJ") ? caracteresValidos : caracteresValidosEstrangeiro);
        this.tipo = tipo;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if ((tipo.equals("CPF") || tipo.equals("CNPJ")) && UtilitariosString.retiraMascara(getString()).length() == (tipo.equals("CPF") ? 11 : 14)) {
                setBorder(bordaOriginal);
                return false;
            } else if (tipo.equals("IDESTRANGEIRO") && UtilitariosString.retiraMascara(getString()).length() >= 7) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(ConstantesGlobais.BORDA_ERRO);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eValido() {
        if (UtilitariosString.retiraMascara(getString()).length() > 0) {
            if (tipo.equals("CPF") ? !ValidacaoCpfCnpj.isCPF(getString()) : tipo.equals("CNPJ") ? !ValidacaoCpfCnpj.isCNPJ(getString()) : false) {
                setBorder(ConstantesGlobais.BORDA_ATENCAO);
                return false;
            }
        }
        setBorder(bordaOriginal);
        return true;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        setMascara(tipo.equals("CPF") ? maskaraCpf : tipo.equals("CNPJ") ? maskaraCpnj : maskaraEstrangeiro);
        setCaracteresValidos(tipo.equals("CPF") | tipo.equals("CNPJ") ? caracteresValidos : caracteresValidosEstrangeiro);
    }

    @Override
    public String getString() {
        return super.getString().toUpperCase();
    }

}
