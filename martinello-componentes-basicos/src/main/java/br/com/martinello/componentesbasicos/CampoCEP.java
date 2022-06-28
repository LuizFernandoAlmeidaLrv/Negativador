/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;

/**
 *
 * @author Sidnei
 */
public class CampoCEP extends CampoMascara {

    private static String maskaraBR = "*****-***";
    private static String caracteresValidos = "0123456789 ";

    public CampoCEP() {
        this(null, null, false, maskaraBR, null);
    }

    public CampoCEP(Class tabela, String campo) {
        this(tabela, campo, false, maskaraBR, null);
    }

    public CampoCEP(Class tabela, String campo, boolean obrigatorio, String mascara, Rotulo rotulo) {
        super(tabela, campo, obrigatorio, mascara, rotulo);
        setCaracteresValidos(caracteresValidos);
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (UtilitariosString.retiraMascara(getString()).length() == 8) {
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
        //System.out.println("CEP: " + Utilitarios.removeCaracteresEspeciais(getString()).trim() + " Tamanho: " + Utilitarios.removeCaracteresEspeciais(getString()).trim().length());
        if (Utilitarios.removeCaracteresEspeciais(getString()).trim().length() > 0) {
            if (Utilitarios.removeCaracteresEspeciais(getString()).trim().length() != 8) {
                setBorder(ConstantesGlobais.BORDA_ATENCAO);
                return false;
            }
        }
        setBorder(bordaOriginal);
        return true;
    }

    public Long getLong() {
        String cep = UtilitariosString.retiraMascara(getString());

        return cep.trim().length() > 0 ? new Long(cep) : 0;
    }

    public void setLong(Long cep) {
        setText(cep != null ? cep.toString() : "");
    }
}
