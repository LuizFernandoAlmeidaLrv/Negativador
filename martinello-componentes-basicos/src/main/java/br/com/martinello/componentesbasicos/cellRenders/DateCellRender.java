/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.cellRenders;

import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Sidnei
 */
public class DateCellRender extends DefaultTableCellRenderer {

    /**
     * classe para mostrar a celula com formato de data
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String _valorFormatado;

        if (value != null) {
            _valorFormatado = (new SimpleDateFormat("dd/MM/yyyy").format((Date) value));
        } else {
            _valorFormatado = ("");
        }

        JLabel labelDataRenderer = (JLabel) componente;
        labelDataRenderer.setText(_valorFormatado);
        labelDataRenderer.setHorizontalAlignment(SwingConstants.CENTER);

//        Color tColorBackground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getBackground();
//        Color tColorForeground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getForeground();
//        labelDataRenderer.setOpaque(true);
//        labelDataRenderer.setBackground(tColorBackground);
//        labelDataRenderer.setForeground(tColorForeground);
        if (isSelected) {
            labelDataRenderer.setFont(new Font(labelDataRenderer.getFont().getName(), Font.BOLD, labelDataRenderer.getFont().getSize()));
//            labelDataRenderer.setBackground(table.getSelectionBackground());
//            labelDataRenderer.setOpaque(true);
//            labelDataRenderer.setForeground(table.getSelectionForeground());
        }
        if (hasFocus) {
            labelDataRenderer.setFont(new Font(labelDataRenderer.getFont().getName(), Font.PLAIN, labelDataRenderer.getFont().getSize()));
//            labelDataRenderer.setForeground(table.getSelectionBackground());
//            labelDataRenderer.setBackground(table.getSelectionForeground());
//            labelDataRenderer.setOpaque(true);
        }

        return labelDataRenderer;
    }

}
