/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.util;

import com.towel.bean.Formatter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sidnei.vieira
 */
public class DataHoraFormatter implements Formatter {

    @Override
    public Object format(Object o) {
        Date data = (Date) o;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String string = dateFormat.format(data);

        return string;
    }

    @Override
    public Object parse(Object o) {
        return null;
    }

    @Override
    public String getName() {
        return "dataHora";
    }

}
