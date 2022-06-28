package br.com.martinello.componentesbasicos.cellRenders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DecimalCellRender extends DefaultTableCellRenderer {

    NumberFormat currencyFormat;

    public DecimalCellRender(NumberFormat cf) {
        currencyFormat = cf;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        String _formattedValue;
        Double _value = (Double) value;

        if (value == null) {
            _formattedValue = "Not Set";
        } else {
            _formattedValue = currencyFormat.format(_value);
        }
        JLabel labelDecimalRenderer = (JLabel) componente;
        labelDecimalRenderer.setText(_formattedValue);
        labelDecimalRenderer.setHorizontalAlignment(JLabel.RIGHT);

        Color tColorBackground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getBackground();
        Color tColorForeground = table.getCellRenderer(row, 0).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, 0).getForeground();
        labelDecimalRenderer.setOpaque(true);
        labelDecimalRenderer.setBackground(tColorBackground);
        labelDecimalRenderer.setForeground(tColorForeground);

        if (isSelected) {
            labelDecimalRenderer.setFont(new Font(labelDecimalRenderer.getFont().getName(), Font.BOLD, labelDecimalRenderer.getFont().getSize()));
//            labelDecimalRenderer.setBackground(table.getSelectionBackground());
//            labelDecimalRenderer.setOpaque(true);
//            labelDecimalRenderer.setForeground(table.getSelectionForeground());
        }
        if (hasFocus) {
            labelDecimalRenderer.setFont(new Font(labelDecimalRenderer.getFont().getName(), Font.PLAIN, labelDecimalRenderer.getFont().getSize()));
//            labelDecimalRenderer.setForeground(table.getSelectionBackground());
//            labelDecimalRenderer.setBackground(table.getSelectionForeground());
//            labelDecimalRenderer.setOpaque(true);
        }
        return labelDecimalRenderer;
    }
}
