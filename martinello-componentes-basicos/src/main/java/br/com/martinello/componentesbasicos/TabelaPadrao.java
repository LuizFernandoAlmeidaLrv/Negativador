/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import br.com.martinello.componentesbasicos.cellRenders.DateCellRender;
import br.com.martinello.componentesbasicos.cellRenders.DecimalCellRender;
import br.com.martinello.componentesbasicos.cellRenders.LocalDateCellRender;
import br.com.martinello.componentesbasicos.cellRenders.StringCellRender;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author Sidnei
 */
public class TabelaPadrao extends JXTable {

    public TabelaPadrao() {
        setColumnControlVisible(true);

        Locale lReais = new Locale("pt", "BR");
        //NumberFormat _format = NumberFormat.getCurrencyInstance(lReais);
        NumberFormat _format = NumberFormat.getCurrencyInstance();
        DecimalCellRender _renderer = new DecimalCellRender(_format);
        setDefaultRenderer(Double.class, _renderer);

        setDefaultRenderer(java.util.Date.class, new DateCellRender());
        setDefaultRenderer(LocalDate.class, new LocalDateCellRender());
        setDefaultRenderer(String.class, new StringCellRender());

        setHighlighters(HighlighterFactory.createAlternateStriping());

        setFont(ConstantesGlobais.FONTE_10_NORMAL);
        getTableHeader().setFont(ConstantesGlobais.FONTE_10_BOLD);

        setHorizontalScrollEnabled(true);
        getTableHeader().setReorderingAllowed(false);
    }

    public TabelaPadrao(TableModel dm) {
        super(dm);
        setColumnControlVisible(true);

        Locale lReais = new Locale("pt", "BR");
        //NumberFormat _format = NumberFormat.getCurrencyInstance(lReais);
        NumberFormat _format = NumberFormat.getCurrencyInstance();
        DecimalCellRender _renderer = new DecimalCellRender(_format);
        setDefaultRenderer(Double.class, _renderer);

        setDefaultRenderer(Date.class, new DateCellRender());
        setDefaultRenderer(LocalDate.class, new LocalDateCellRender());
        setDefaultRenderer(String.class, new StringCellRender());

        setHighlighters(HighlighterFactory.createAlternateStriping());

        setFont(ConstantesGlobais.FONTE_10_NORMAL);
        getTableHeader().setFont(ConstantesGlobais.FONTE_10_BOLD);

        setHorizontalScrollEnabled(true);
        getTableHeader().setReorderingAllowed(false);
    }

    public TabelaPadrao(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(int numRows, int numColumns) {
        super(numRows, numColumns);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        setColumnControlVisible(true);
    }

    public int[] getLinhasSelecionadas() {
        //Seleciona os índices da tabela que estão selecionados
        int linhasSelecionadas[] = getSelectedRows();
        for (int i = 0; i < linhasSelecionadas.length; i++) {
            linhasSelecionadas[i] = convertRowIndexToModel(linhasSelecionadas[i]);
        }
        // a seleção será de acordo com o modelo da JTableModel - então
        // de acordo com os índices selecionados após execução do RowSorter
        return linhasSelecionadas;
    }

    /**
     * <p>
     * Converte o índice selecionado da tabela para o índice do modelo da
     * tabela.</p>
     * <p>
     * É uma versão sobrecarregada do método que realiza a conversão somente
     * para um ítem</p>
     *
     * @return Retorna um inteiro com o índice selecionado
     */
    public int getLinhaSelecionada() {
        int indices[] = getLinhasSelecionadas();
        if (indices != null && indices.length > 0) {
            return indices[0];
        } else {
            return -1;
        }
    }
}
