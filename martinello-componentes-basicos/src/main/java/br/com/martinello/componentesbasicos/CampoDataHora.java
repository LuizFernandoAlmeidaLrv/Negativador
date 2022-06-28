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
public class CampoDataHora extends JXDatePicker implements Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected Boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;

    public CampoDataHora() {
        this(null, null, null, null, false);
    }

    public CampoDataHora(Class tabela, String campo, String descricaoRotulo) {
        this(tabela, campo, descricaoRotulo, null, false);

    }

    public CampoDataHora(Class tabela, String campo, String descricaoRotulo, Rotulo rRotulo, boolean obrigatorio) {
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;
        this.rRotulo = rRotulo;

        setFont(ConstantesGlobais.FONTE_11_NORMAL);
        Utilitarios.considerarEnterComoTab(this);
        //setFormats(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")); //$NON-NLS-1$

        setFormats(DateFormat.getDateInstance(DateFormat.MEDIUM));
        getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);

        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                setComponenteRotulo(rRotulo);
            });
        }
    }

    public CampoDataHora(String descricaoRotulo, boolean obrigatorio, Date selected) {
        super(selected);
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;
    }

    public CampoDataHora(String descricaoRotulo, boolean obrigatorio, Locale locale) {
        super(locale);
        this.descricaoRotulo = descricaoRotulo;
        this.obrigatorio = obrigatorio;
    }

    public CampoDataHora(String descricaoRotulo, boolean obrigatorio, Date selection, Locale locale) {
        super(selection, locale);
        this.descricaoRotulo = descricaoRotulo;
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
        return obrigatorio && getDate() != null ? true ? !obrigatorio : true : false;
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
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

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

}
