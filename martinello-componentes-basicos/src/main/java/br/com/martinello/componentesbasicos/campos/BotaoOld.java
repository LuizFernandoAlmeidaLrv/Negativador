/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

/*
 * Decompiled with CFR 0_123.
 */
import br.com.martinello.componentesbasicos.novo.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class BotaoOld extends JButton
        implements MouseListener {

    private String label;
    private Icon iconDefault;
    private Icon iconSelecionado;
    private Icon iconPressionado;
    private Icon iconDesabilitado;
    private Icon iconToggled;
    private boolean sublinhar;
    private boolean toggled = false;
    private boolean estiloLink = false;

    public BotaoOld(String label) {
        this(label, null, null, null, null, null);
    }

    public BotaoOld(String label, Icon iconDefault) {
        this(label, iconDefault, null, null, null, null);
    }

    public BotaoOld(String label, Icon iconDefault, Icon iconSelecionado, Icon iconPressionado) {
        this(label, iconDefault, iconSelecionado, iconPressionado, null, null);
    }

    public BotaoOld(String label, Icon iconDefault, Icon iconSelecionado, Icon iconPressionado, Icon iconDesabilitado) {
        this(label, iconDefault, iconSelecionado, iconPressionado, iconDesabilitado, null);
    }

    public BotaoOld(String label, Icon iconDefault, Icon iconSelecionado, Icon iconPressionado, Icon iconDesabilitado, Icon iconToggled) {
        this.iconDefault = iconDefault;
        this.iconSelecionado = iconSelecionado;
        this.iconPressionado = iconPressionado;
        this.iconToggled = iconToggled;
        this.setDisabledIcon(iconDesabilitado);
        this.setDisabledSelectedIcon(iconDesabilitado);
        this.setSublinhar(false);
        this.setContentAreaFilled(true);
        this.setAlignmentX(0.5f);
        this.setHorizontalAlignment(0);
        this.setIcon(iconDefault);
        if (iconSelecionado != null && iconToggled == null) {
            this.setRolloverIcon(iconSelecionado);
        }
        if (iconPressionado != null) {
            this.setPressedIcon(iconPressionado);
        }
        this.setText(label);
        this.addMouseListener(this);
    }

    public void setSublinhar(boolean flag) {
        this.sublinhar = flag;
    }

    public void setToggled(boolean flag) {
        this.toggled = flag;
        if (this.toggled) {
            this.setIcon(this.iconToggled);
        } else {
            this.setIcon(this.iconDefault);
        }
        this.repaint();
    }

    public boolean getToggled() {
        return this.toggled;
    }

    public void setText(String text) {
        this.label = text;
        super.setText("<html>" + this.label + "</html>");
    }

    public void setaDimensoes() {
        FontMetrics fm = this.getFontMetrics(this.getFont());
        setParametrosGUI((JComponent) this, 10 + SwingUtilities.computeStringWidth(fm, this.label) + this.iconDefault.getIconWidth() + 10, this.iconDefault.getIconHeight() + 10);
    }

    public static void setParametrosGUI(JComponent c, int tam, int alt) {
        c.setPreferredSize(new Dimension(tam, alt));
        c.setMinimumSize(c.getPreferredSize());
        c.setMaximumSize(c.getPreferredSize());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.sublinhar && this.isEnabled()) {
            mudaCursorNoComponente(this, 12);
            super.setText("<html><u>" + this.label + "</u></html>");
        }
        if (this.sublinhar) {
            this.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (this.sublinhar) {
            mudaCursorNoComponente(this, 0);
            super.setText("<html>" + this.label + "</html>");
        }
        if (this.sublinhar) {
            this.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            this.setForeground(Color.BLACK);
        } else {
            this.setForeground(Color.GRAY);
        }
        super.setEnabled(enabled);
    }

    public void setEstiloLink(boolean estiloLink) {
        this.estiloLink = estiloLink;
        if (estiloLink) {
            this.setSublinhar(true);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
        } else {
            this.setSublinhar(false);
            this.setContentAreaFilled(true);
            this.setBorderPainted(true);
        }
    }

    public boolean isEstiloLink() {
        return this.estiloLink;
    }

    public static void mudaCursorNoComponente(Component pComp, int pTipoCursor) {
        try {
            pComp.setCursor(Cursor.getPredefinedCursor(pTipoCursor));
        } catch (Exception exception) {
            // empty catch block
        }
    }
}
