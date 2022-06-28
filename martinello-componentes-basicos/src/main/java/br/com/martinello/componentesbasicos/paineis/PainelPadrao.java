/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.paineis;

import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.ConstantesGlobais;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Sidnei
 */
public class PainelPadrao extends JPanel {

    public static final int INCLUINDO = 1;
    public static final int ALTERANDO = 2;
    public static final int EXCLUINDO = 3;
    public static final int PESQUISANDO = 4;
    public static final int INATIVANDO = 5;
    public static final int VISUALIZANDO = 5;
    public static final int INDEFINIDO = 0;
    protected int statusTela = INDEFINIDO;

    public PainelPadrao(LayoutManager layout) {
        super(layout);
    }

    public PainelPadrao() {
    }

    public PainelPadrao(String titulo) {
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, titulo, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));
    }

    protected void habilitaPainel(boolean habilita, JPanel painel) {
        for (Component c : painel.getComponents()) {
            c.setEnabled(habilita);
            if (c instanceof JPanel) {
                habilitaPainel(habilita, (JPanel) c);
            }
//            if (c instanceof PainelAba) {
//                PainelAba painelAba = (PainelAba) c;
//
//                for (Component component : painelAba.getComponents()) {
//                    if (c instanceof JPanel) {
//                        habilitaPainel(habilita, (JPanel) component);
//                    }
//                }
//            }
        }
    }

    public void limparListaCampos(List<Campo> campos) {
        campos.forEach((campo) -> {
            campo.limpar();
        });
    }

    public int[] getConverteLinhaIndiceParaModelo(int[] linhasSelecionadas, JTable jTable) {
        int[] arrayLinhasSelecionadas = new int[linhasSelecionadas.length];

        for (int i = 0; i < linhasSelecionadas.length; i++) {
            arrayLinhasSelecionadas[i] = jTable.convertRowIndexToModel(jTable.getSelectedRow());
        }

        return arrayLinhasSelecionadas;
    }

    /**
     * Validar lista de campos
     *
     * @param campos
     * @return
     */
    protected boolean validarListaCampos(List<Campo> campos) {
        boolean resultado = true;

        for (Campo campo : campos) {
            if (campo.eVazio()) {
                resultado = false;
            }
        }

        return resultado;
    }

    protected boolean validarValoresListaCampos(List<Campo> campos) {
        boolean resultado = true;

        for (Campo campo : campos) {
            if (!campo.eValido()) {
                resultado = false;
            }
        }

        return resultado;
    }

    public void habilitaComponentesCadastro(boolean habilita) {
    }

    public void habilitaCadastro(boolean habilitada) {
    }

    public void habilitaBotoesTabelas(Painel painel, boolean b) {
    }

    public void habilitarCampos(List<Campo> campos, boolean habilitar) {
        campos.forEach((campo) -> {
            campo.setEnabled(habilitar);
        });
    }

}
