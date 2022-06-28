package br.com.martinello.componentesbasicos.formatters;

import com.towel.bean.Formatter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoedaFormatter implements Formatter {

    private NumberFormat numeroFormato = NumberFormat.getNumberInstance();
    private DecimalFormat formato = (DecimalFormat) numeroFormato;

    @Override
    public String format(Object obj) {
        formato.applyPattern("#,##0.00");
        return formato.format((Double) obj).toString();
    }

    @Override
    public String getName() {
        return "moeda";
    }

    @Override
    public Object parse(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
