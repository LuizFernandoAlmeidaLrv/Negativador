/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.componentesbasicos.eventos.SetarValorEvento;
import br.com.martinello.util.Utilitarios;
import com.towel.el.annotation.Resolvable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import br.com.martinello.componentesbasicos.eventos.SetarValorListener;

/**
 *
 * @author Sidnei
 */
public class CampoListaSimples extends JComboBox implements Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;
    protected Collection<SetarValorListener> listenerSetarValor = new ArrayList<>();

    public CampoListaSimples() {
        Utilitarios.considerarEnterComoTab(this);
    }

    public CampoListaSimples(ComboBoxModel aModel) {
        super(aModel);
        Utilitarios.considerarEnterComoTab(this);
    }

    public CampoListaSimples(Class tabela, String campo) {
        bordaOriginal = this.getBorder();
        Utilitarios.considerarEnterComoTab(this);

        setFont(ConstantesGlobais.FONTE_11_NORMAL);

        try {
            if (tabela != null) {
                if (tabela.getDeclaredField(campo).getType() != String.class && tabela.getDeclaredField(campo).getType() != int.class) {
                    JoinColumn joinColumn = tabela.getDeclaredField(campo).getAnnotation(JoinColumn.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !joinColumn.nullable();
                    dica = r.colName();
                    setToolTipText(dica);
                } else {
                    Column coluna = tabela.getDeclaredField(campo).getAnnotation(Column.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !coluna.nullable();
                    dica = r.colName();
                    setToolTipText(dica);
                }
            }

        } catch (NoSuchFieldException | SecurityException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar configuração do campo " + campo, "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void limpar() {
        if (getModel().getSize() > 0) {
            setSelectedIndex(0);
        }
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getSelectedIndex() >= 0) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(ConstantesGlobais.BORDA_ERRO);
                return true;
            }
        }
        return false;
    }

    public Rotulo getRotuloCampo() {
        return rRotulo;
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

        atualizaDescricaoRotulo();

//        if (rRotulo != null) {
//            SwingUtilities.invokeLater(() -> {
//                if (obrigatorio) {
//                    rRotulo.setText("<html><body>" + descricaoRotulo + "<FONT COLOR='red'><b>*</b></FONT>:</body></html>");
//                } else {
//                    rRotulo.setText("<html><body>" + descricaoRotulo + ":</body></html>");
//                }
//            });
//        }
    }

    public void atualizaDescricaoRotulo() {
        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                if (obrigatorio) {
                    rRotulo.setText("<html><body>" + descricaoRotulo + "<FONT COLOR='red'><b>*</b></FONT>:</body></html>");
                } else {
                    rRotulo.setText("<html><body>" + descricaoRotulo + ":</body></html>");
                }
            });
        }
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    public Border getBordaOriginal() {
        return bordaOriginal;
    }

    public void setBordaOriginal(Border bordaOriginal) {
        this.bordaOriginal = bordaOriginal;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;

        atualizaDescricaoRotulo();
    }

    public String getString() {
        return getSelectedItem().toString();
    }

    public void setString(String valor) {
        setSelectedItem(valor);
        disparaValorSetado();
    }

    public int getInt() {
        return Integer.parseInt(getSelectedItem().toString());
    }

    public void setInt(int valor) {
        setSelectedItem(Integer.toString(valor));
        disparaValorSetado();
    }

    public synchronized void addSetarValorListener(SetarValorListener l) {
        if (!listenerSetarValor.contains(l)) {
            listenerSetarValor.add(l);
        }
    }

    public synchronized void removeSetarValorListener(SetarValorListener l) {
        listenerSetarValor.remove(l);
    }

    private void disparaValorSetado() {
        Collection<SetarValorListener> tl;

        synchronized (this) {
            tl = (Collection) (((ArrayList) listenerSetarValor).clone());
        }

        SetarValorEvento evento = new SetarValorEvento(this);

        for (SetarValorListener t : tl) {
            t.valorSetado(evento);
        }
    }
}
