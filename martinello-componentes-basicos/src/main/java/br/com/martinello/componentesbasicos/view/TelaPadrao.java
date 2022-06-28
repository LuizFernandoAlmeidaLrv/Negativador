/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.view;

import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.paineis.Painel;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author Sidnei
 */
public class TelaPadrao extends JInternalFrame implements InternalFrameListener {

    protected final int INCLUINDO = 1;
    protected final int ALTERANDO = 2;
    protected final int EXCLUINDO = 3;
    protected final int PESQUISANDO = 4;
    protected final int INATIVANDO = 5;
    protected final int VISUALIZANDO = 6;
    protected final int INDEFINIDO = 0;
    protected int statusTela = INDEFINIDO;

    public TelaPadrao() {
        addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public TelaPadrao(String title) {
        super(title);
        this.addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public TelaPadrao(String title, boolean resizable) {
        super(title, resizable);
        this.addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public TelaPadrao(String title, boolean resizable, boolean closable) {
        super(title, resizable, closable);
        this.addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public TelaPadrao(String title, boolean resizable, boolean closable, boolean maximizable) {
        super(title, resizable, closable, maximizable);
        this.addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public TelaPadrao(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        this.addInternalFrameListener(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

    /**
     * Limpar lista de campos
     *
     * @param campos
     */
    public void limparListaCampos(List<Campo> campos) {
        campos.forEach((campo) -> {
            campo.limpar();
        });
    }

    public void habilitarCampos(List<Campo> campos, boolean habilitar) {
        campos.forEach((campo) -> {
            campo.setEnabled(habilitar);
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

    protected void fecharTela() {
        if (statusTela != INDEFINIDO) {
            int fechaTela = JOptionPane.showConfirmDialog(rootPane, "Confirma fechamento da tela " + getTitle() + "?", "Fechamento", JOptionPane.YES_NO_OPTION);
            if (fechaTela == 0) {
                dispose();
            }
        } else {
            dispose();
        }
    }

    public void habilitaComponentesCadastro(boolean habilita) {
    }

    public void habilitaCadastro(boolean habilitada) {
    }

    public void habilitaBotoesTabelas(Painel painel, boolean b) {
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        fecharTela();
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void setStatusTela(int statusTela) {
        this.statusTela = statusTela;
    }

    
}
