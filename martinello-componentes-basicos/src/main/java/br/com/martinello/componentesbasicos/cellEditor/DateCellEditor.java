/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.cellEditor;

import br.com.martinello.componentesbasicos.CampoData;
import br.com.martinello.componentesbasicos.TabelaPadrao;
import java.awt.Component;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Sidnei
 */
public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {

    /**
     * classe para editar a celula com formato de data
     */
    private static final long serialVersionUID = 1L;
    private CampoData cell = null;
    private final TabelaPadrao tabelaPadrao;

//    private DateField getCell() {
//        if (cell == null) {
//            cell = new DateField();
//        }
//        return cell;
//    }
    public DateCellEditor(TabelaPadrao tabelaPadrao) {
        this.tabelaPadrao = tabelaPadrao;
    }

    private CampoData getCell() {
        if (cell == null) {
            cell = new CampoData();
            cell.setBorder(null);
        }
        return cell;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        getCell().setDate((Date) value);
        return getCell();
    }

    @Override
    public Object getCellEditorValue() {
        return getCell().getDate();
    }

//    @Override
//    public boolean stopCellEditing() {
//        if (getCell().getDate() == null || getCell().getDate().before(new Date())) {
//            fireEditingCanceled();
//            JOptionPane.showMessageDialog(null, "Vencimento inválido.");
//
//            /*Mantém em modo de edição a célula que está pendente de entrada de texto*/
//            boolean success = tabelaPadrao.editCellAt(tabelaPadrao.getSelectedRow(), tabelaPadrao.getSelectedColumn());
//            if (success) {
//                boolean toggle = false;
//                boolean extend = false;
//                tabelaPadrao.changeSelection(tabelaPadrao.getSelectedRow(), tabelaPadrao.getSelectedColumn(), toggle, extend);
//                getCell().requestFocus();
//            } else {
//                // célula nao é editada
//            }
//            return false;
//        }
//        return super.stopCellEditing();
//    }

}
