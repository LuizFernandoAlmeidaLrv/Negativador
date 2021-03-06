/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.consulta;

import br.com.martinello.componentesbasicos.paineis.Painel;
import br.com.martinello.util.ValidacaoCpfCnpj;
import br.com.martinello.util.filtro.Filtro;

/**
 *
 * @author Sidnei
 */
public class CampoCpfCnpjConsulta extends Painel {

    private String nomeCampo;

    /**
     * Creates new form CampoStringConsulta
     */
    public CampoCpfCnpjConsulta() {
        initComponents();
        cccCpfCnj.setTipo("CPF");
        cccCpfCnj.setObrigatorio(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clsTipoFiltro = new br.com.martinello.componentesbasicos.CampoListaSimples();
        cccCpfCnj = new br.com.martinello.componentesbasicos.CampoCpfCnpj();

        setLayout(new java.awt.BorderLayout());

        clsTipoFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CPF", "CNPJ", "ID. Estrangeiro" }));
        clsTipoFiltro.setMaximumSize(new java.awt.Dimension(72, 20));
        clsTipoFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clsTipoFiltroItemStateChanged(evt);
            }
        });
        add(clsTipoFiltro, java.awt.BorderLayout.LINE_START);

        cccCpfCnj.setText("");
        add(cccCpfCnj, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void clsTipoFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clsTipoFiltroItemStateChanged
        cccCpfCnj.setTipo(clsTipoFiltro.getSelectedItem().toString());
    }//GEN-LAST:event_clsTipoFiltroItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.CampoCpfCnpj cccCpfCnj;
    private br.com.martinello.componentesbasicos.CampoListaSimples clsTipoFiltro;
    // End of variables declaration//GEN-END:variables

    public String getFiltroOld() {
        String filtroDescricao = null;
        if (ValidacaoCpfCnpj.removeMascara(cccCpfCnj.getText().trim()).length() > 0) {
            return cccCpfCnj.getText().trim().toUpperCase();
        }

        return filtroDescricao;
    }

    public void setaTipoFiltro(Object valor) {
        clsTipoFiltro.setSelectedItem(valor);
    }

    public void setaValorFiltro(String valor) {
        cccCpfCnj.setString(valor);
    }

    public String getValorFiltro() {
        return cccCpfCnj.getString();
    }

    public Filtro getFiltro(String campo) {
        return new Filtro(campo, Filtro.STRING, Filtro.IGUAL, cccCpfCnj.getText().trim().toUpperCase().length() >= 10 ? cccCpfCnj.getText().trim().toUpperCase() : null);
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

}
