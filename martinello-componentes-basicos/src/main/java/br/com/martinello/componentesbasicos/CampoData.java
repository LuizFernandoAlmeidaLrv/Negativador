/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.util.Utilitarios;
import java.awt.Color;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Sidnei
 */
public class CampoData extends JXDatePicker implements Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;

    public CampoData() {
        this(null, null);
    }

    public CampoData(Class tabela, String campo) {
        setFont(ConstantesGlobais.FONTE_11_NORMAL);
        Utilitarios.considerarEnterComoTab(this);

        //setFormats(new SimpleDateFormat("dd/MM/yyyy")); //$NON-NLS-1$
        setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));
        getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);
    }

    public CampoData(String rotulo, boolean obrigatorio, Date selected) {
        super(selected);
        this.descricaoRotulo = rotulo;
        this.obrigatorio = obrigatorio;
    }

    public CampoData(String rotulo, boolean obrigatorio, Locale locale) {
        super(locale);
        this.descricaoRotulo = rotulo;
        this.obrigatorio = obrigatorio;
    }

    public CampoData(String rotulo, boolean obrigatorio, Date selection, Locale locale) {
        super(selection, locale);
        this.descricaoRotulo = rotulo;
        this.obrigatorio = obrigatorio;
    }

    @Override
    public void limpar() {
        setDate(null);
    }

    public void montarCampo() {

    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getDate() != null) {
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
        return "";
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;

        atualizaDescricaoRotulo();
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

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }

}
