/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

/**
 *
 * @author Sidnei
 */
import br.com.martinello.componentesbasicos.CampoString;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ComponenteString extends Componente {

    private JTextField componente;
    private int maxChars = 100;

    public ComponenteString() {
        super(new CampoString());
    }

    protected void informacaoModificada() {
//        ((ComponenteTextField) this.componente).setInformacao(this.campo);
//        if (this.campo instanceof Alfa && ((Alfa) this.campo).getMaximoCaracteres() != 1) {
//            this.setMaxChars(((Alfa) this.campo).getMaximoCaracteres());
//        }
        this.implementacaoPropertyChange(null);
    }

    public void setarCampo() {
        String s = this.componente.getText();
        if (s.length() > this.getMaxChars()) {
            s = s.substring(0, this.getMaxChars());
        }
        //this.getInformacao().setConteudo(s);
        this.chamaValidacao();
    }

    @Override
    public JComponent getComponenteEditor() {
        return this.componente;
    }

    public void implementacaoPropertyChange(PropertyChangeEvent evt) {
        //this.componente.setText(this.getInformacao().getConteudoFormatado());
        this.componente.setCaretPosition(0);
    }

    protected void readOnlyPropertyChange(boolean readOnly) {
        if (readOnly) {
            this.componente.setEditable(false);
        } else {
            this.componente.setEditable(true);
        }
    }

    protected void habilitadoPropertyChange(boolean habilitado) {
        this.getComponenteEditor().setEnabled(habilitado);
//        JLabel rotulo = this.getRotulo();
//        if (rotulo != null) {
//            this.getRotulo().setEnabled(habilitado);
//        }
    }

    @Override
    public JComponent getComponenteFoco() {
        return this.componente;
    }

    @Override
    protected void buildComponente() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        if (this.componente == null) {
            this.componente = new ComponenteTextField();
            this.componente.setColumns(10);
            this.componente.addFocusListener(new FocusAdapter() {

                @Override
                public void focusLost(FocusEvent e) {
                    if (e.getOppositeComponent() == null) {
                        return;
                    }
                    //JCampoString.this.setIdentificacaoFoco(false);
                    ComponenteString.this.setarCampo();
                }

                @Override
                public void focusGained(FocusEvent e) {
                    componente.selectAll();
                }
            });

            this.componente.addActionListener((ActionEvent e) -> {
                ComponenteString.this.setarCampo();
            });

            this.componente.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    int tamanhoTextoAtual;
                    boolean eventosEhInclusao;
                    char ch = e.getKeyChar();
                    boolean bl = eventosEhInclusao = ch != '\t' && ch != '\n' && ch != '\b' && ch != '' && ch != '%' && ch != '\'';
                    if (ch == '\'') {
                        eventosEhInclusao = true;
                    }
                    if ((tamanhoTextoAtual = ComponenteString.this.componente.getText().length()) >= ComponenteString.this.getMaxChars() && eventosEhInclusao && ComponenteString.this.componente.getSelectedText() == null) {
                        e.setKeyChar('\uffff');
                        e.consume();
                        UIManager.getLookAndFeel().provideErrorFeedback(ComponenteString.this.componente);
                    }
                }
            });
        }
        this.add((Component) this.componente, "Center");
        this.add((Component) new PainelBotao(this.getBotaoMensagem()), "East");
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }

    public int getMaxChars() {
        return this.maxChars;
    }

    public void setEstiloFonte(int estilo) {
        Font f = this.componente.getFont();
        f = f.deriveFont(estilo);
        this.componente.setFont(f);
    }

    @Override
    public void setIncrementoTamanhoFonte(int incremento) {
        this.incrementoTamanhoFonte = incremento;
        Font f = this.componente.getFont();
        if (this.tamanhoOriginal == -1.0f) {
            this.tamanhoOriginal = f.getSize2D();
        }
        f = f.deriveFont(this.tamanhoOriginal + (float) incremento);
        this.componente.setFont(f);
    }

    @Override
    public int getIncrementoTamanhoFonte() {
        return this.incrementoTamanhoFonte;
    }

    @Override
    public boolean eVazio() {
        return componente.getText().length() == 0;
    }

}
