/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema;

import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.util.Utilitarios;

/**
 *
 * @author Sidnei
 */
import br.com.martinello.componentesbasicos.ConstantesGlobais;
import java.util.Date;

public class BarraStatus extends javax.swing.JPanel {

   
    protected UsuarioModel usuarioModel;
    protected FilialModel filial;
    private Date dataExtrator;
    private String status;

    public BarraStatus() {
        initComponents();
    }

    public BarraStatus(UsuarioModel usuario, String eletromóveis_martinello, String status, Date dataExtrator) {
       filial = new FilialModel();
        filial.setNomeLoja("Eletromóveis_Martinello");
        initComponents();
        setUsuario(usuario);
        setFilial(filial);
        setDataExtrator(dataExtrator);
        setStatus(status);

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rRotuloLoja = new br.com.martinello.componentesbasicos.Rotulo();
        rLoja = new br.com.martinello.componentesbasicos.Rotulo();
        rRotuloUsuario = new br.com.martinello.componentesbasicos.Rotulo();
        rUsuario = new br.com.martinello.componentesbasicos.Rotulo();
        rRotuloData = new br.com.martinello.componentesbasicos.Rotulo();
        rData = new br.com.martinello.componentesbasicos.Rotulo();
        rRotuloStatus = new br.com.martinello.componentesbasicos.Rotulo();
        rStatus = new br.com.martinello.componentesbasicos.Rotulo();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rRotuloLoja.setText("LOJA:");
        rRotuloLoja.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rLoja.setForeground(new java.awt.Color(255, 0, 0));
        rLoja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rLoja.setText("jLabel2");
        rLoja.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rRotuloUsuario.setText("USUÁRIO:");
        rRotuloUsuario.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rUsuario.setForeground(new java.awt.Color(255, 0, 0));
        rUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rUsuario.setText("jLabel4");
        rUsuario.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rRotuloData.setText("DATA ");
        rRotuloData.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rData.setForeground(new java.awt.Color(255, 0, 0));
        rData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rData.setText("jLabel4");
        rData.setFont(ConstantesGlobais.FONTE_11_BOLD);

        rRotuloStatus.setText("STATUS:");
        rRotuloStatus.setFont(ConstantesGlobais.FONTE_11_BOLD);
        
        rStatus.setForeground(new java.awt.Color(255, 0, 0));
        rStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rStatus.setFont(ConstantesGlobais.FONTE_11_BOLD);
        rStatus.setText("jLabel3");
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rRotuloLoja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rRotuloUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rRotuloStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(rRotuloData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rData, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rRotuloData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rRotuloLoja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rLoja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rRotuloUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rRotuloStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(rStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Rotulo rData;
    private br.com.martinello.componentesbasicos.Rotulo rLoja;
    private br.com.martinello.componentesbasicos.Rotulo rRotuloData;
    private br.com.martinello.componentesbasicos.Rotulo rRotuloLoja;
    private br.com.martinello.componentesbasicos.Rotulo rRotuloStatus;
    private br.com.martinello.componentesbasicos.Rotulo rRotuloUsuario;
    private br.com.martinello.componentesbasicos.Rotulo rStatus;
    private br.com.martinello.componentesbasicos.Rotulo rUsuario;
    // End of variables declaration//GEN-END:variables

    public UsuarioModel getUsuario() {
        return usuarioModel;
    }

    public void setUsuario(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;

        rUsuario.setText(usuarioModel != null ? usuarioModel.getNome() : "");
    }

    public FilialModel getFilial() {
        return filial;
    }

    public void setFilial(FilialModel filial) {
        this.filial = filial;

        rLoja.setText(filial != null ? filial.getNomeLoja() : "");
    }

    public Date getDataExtrator() {
        return dataExtrator;
    }

    public void setDataExtrator(Date dataExtrator) {
        this.dataExtrator = dataExtrator;
        rData.setText(Utilitarios.dataParaString(dataExtrator));

    }
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        rStatus.setText(status);
    }


}
