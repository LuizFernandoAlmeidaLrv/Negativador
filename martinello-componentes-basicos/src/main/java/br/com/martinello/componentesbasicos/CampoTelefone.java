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
public class CampoTelefone extends CampoMascara {

    private static String maskaraBR = "(**)*****-****";
    private static String caracteresValidos = "0123456789 ";

    public CampoTelefone() {
        this(null, null, null, false, maskaraBR, null);
    }

    public CampoTelefone(Class tabela, String campo, String descricaoRotulo, String mascara) {
        this(tabela, campo, descricaoRotulo, false, mascara, null);
    }

    public CampoTelefone(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio, String mascara, Rotulo rotulo) {
        super(tabela, campo, descricaoRotulo, obrigatorio, mascara, rotulo);
        setCaracteresValidos(caracteresValidos);
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (UtilitariosString.retiraMascara(getString()).length() >= 10) {
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
            for (int i = 0; i < 9; i++) {
                String telefone = Utilitarios.rpad(UtilitariosString.retiraMascara(getString()), "" + i, 11);

                if (telefone.equals(Utilitarios.rpad("" + i, "" + i, 11))) {
                    setBorder(ConstantesGlobais.BORDA_ATENCAO);
                    return false;
                }
            }
        }
        setBorder(bordaOriginal);
        return true;
    }

}
