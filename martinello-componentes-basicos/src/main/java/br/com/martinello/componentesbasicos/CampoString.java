/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.componentesbasicos.eventos.SetarValorEvento;
import br.com.martinello.util.Utilitarios;
import com.towel.el.annotation.Resolvable;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import br.com.martinello.componentesbasicos.eventos.SetarValorListener;

/**
 *
 * @author Sidnei
 */
public class CampoString extends JFormattedTextField implements Campo, FocusListener {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;
    protected int qtdMaxCaracteres = 999;
    protected boolean somenteMaiusculas = false;
    protected Collection<SetarValorListener> listenerSetarValor = new ArrayList<>();

    public CampoString() {
        this(null, null, null, false, null);
    }

    public CampoString(Class tabela, String campo) {
        this(tabela, campo, null, false, null);
    }

    public CampoString(Class tabela, String campo, String descricaoRotulo) {
        this(tabela, campo, descricaoRotulo, false, null);
    }

    public CampoString(Class tabela, String campo, Rotulo rRotulo) {
        this(tabela, campo, null, false, rRotulo);
    }

    public CampoString(Class tabela, String campo, String descricaoRotulo, Rotulo rRotulo) {
        this(tabela, campo, descricaoRotulo, false, rRotulo);
    }

    public CampoString(Class tabela, String campo, String descricaoRotulo, boolean obrigatorio, Rotulo rRotulo) {
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;

        setFont(ConstantesGlobais.FONTE_11_NORMAL);

        addFocusListener(this);

        bordaOriginal = this.getBorder();

        Utilitarios.considerarEnterComoTab(this);

        setDocument(new LimitaNroCaracteres(qtdMaxCaracteres, somenteMaiusculas));

        try {
            if (tabela != null) {
                if (tabela.getDeclaredField(campo).getType() != String.class && tabela.getDeclaredField(campo).getType() != int.class && tabela.getDeclaredField(campo).getType() != Date.class) {
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

                    if (coluna.length() > 0) {
                        setDocument(new LimitaNroCaracteres(coluna.length()));
                    }
                }
            }

        } catch (NoSuchFieldException | SecurityException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar configuração do campo " + campo, "Erro", JOptionPane.ERROR_MESSAGE);
        }

        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                setComponenteRotulo(rRotulo);
            });
        }
    }

    @Override
    public void limpar() {
        setText("");
        setBorder(bordaOriginal);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getText().trim().length() > 0) {
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

    public void setString(String valor) {
        setText(valor);
        disparaValorSetado();
    }

    public String getString() {
        return getText().trim();
    }

    public void setInt(int valor) {
        setText("" + valor);
        disparaValorSetado();
    }

    public int getInt() {
        return Integer.parseInt(getText().trim().length() == 0 ? "0" : getText().trim());
    }

    public void setDate(Date data, String formato) {
        setText(Utilitarios.converteDataString(data, formato));
        disparaValorSetado();
    }

    public Date getDate(String formato) {
        return Utilitarios.stringParaData(getString(), formato);
    }

    @Override
    public void focusGained(FocusEvent e) {
        selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {
        boolean eVazio = eVazio();
        if (!eVazio) {
            eValido();
        }
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

    public int getQtdMaxCaracteres() {
        return qtdMaxCaracteres;
    }

    public void setQtdMaxCaracteres(int qtdMaxCaracteres) {
        this.qtdMaxCaracteres = qtdMaxCaracteres;
        setDocument(new LimitaNroCaracteres(qtdMaxCaracteres, somenteMaiusculas));
    }

    public boolean isSomenteMaiusculas() {
        return somenteMaiusculas;
    }

    public void setSomenteMaiusculas(boolean somenteMaiusculas) {
        this.somenteMaiusculas = somenteMaiusculas;
        setDocument(new LimitaNroCaracteres(qtdMaxCaracteres, somenteMaiusculas));
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
