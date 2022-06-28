package br.com.martinello.componentesbasicos.cellRenders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class StringCellRender extends DefaultTableCellRenderer {

    NumberFormat currencyFormat;

    public StringCellRender() {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        String _formattedValue = value != null ? value.toString() : "";

        JLabel labelStringRenderer = (JLabel) componente;
        labelStringRenderer.setText(_formattedValue);
        labelStringRenderer.setHorizontalAlignment(JLabel.LEFT);

//        Color tColorBackground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getBackground();
//        Color tColorForeground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getForeground();
//        labelStringRenderer.setOpaque(true);
//        labelStringRenderer.setBackground(tColorBackground);
//        labelStringRenderer.setForeground(tColorForeground);
        if (isSelected) {
            labelStringRenderer.setFont(new Font(labelStringRenderer.getFont().getName(), Font.BOLD, labelStringRenderer.getFont().getSize()));
//            labelDecimalRenderer.setBackground(table.getSelectionBackground());
//            labelDecimalRenderer.setOpaque(true);
//            labelDecimalRenderer.setForeground(table.getSelectionForeground());
        }
        if (hasFocus) {
            labelStringRenderer.setFont(new Font(labelStringRenderer.getFont().getName(), Font.PLAIN, labelStringRenderer.getFont().getSize()));
//            labelDecimalRenderer.setForeground(table.getSelectionBackground());
//            labelDecimalRenderer.setBackground(table.getSelectionForeground());
//            labelDecimalRenderer.setOpaque(true);
        }
        return labelStringRenderer;
    }
}
