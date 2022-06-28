/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author sidnei.vieira
 */
public class Utilitarios {

    public Utilitarios() {
    }

    
    public static Date formataData(Date data) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNull = null;
        try {
            dataNull = (java.util.Date) formatter.parse("31/12/1900");
        } catch (ParseException e) {
            e.printStackTrace();
            //return data;
        }

        if ((data == null) || (data.before(dataNull))) {
            return dataNull;
        }
        return data;
    }
    
    

    public static java.sql.Date formataDataToDate(Date data1) {
       
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date datanull = null;
        java.sql.Date dataSql = null;
        try {
          //  data = (java.util.Date) formatter.parse("31/12/1900"); //return data;
          dataSql = new java.sql.Date(data1.getTime());
            datanull = (java.sql.Date) formatter.parse("31/12/1900");
        } catch (ParseException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataSql;
       
    }

    public static java.sql.Date stringParaData(String dataString) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = (java.util.Date) formatter.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
            //return data;
        }

        return converteData(data);
    }

    public static java.sql.Date stringParaData(String dataString, String formato) {
        DateFormat formatter = new SimpleDateFormat(formato);
        Date data = null;
        try {
            data = (java.util.Date) formatter.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
            //return data;
        }

        return converteData(data);
    }

    public static java.sql.Date getDataZero() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = (java.util.Date) formatter.parse("31/12/1900");
        } catch (ParseException e) {
            e.printStackTrace();
            //return data;
        }

        return converteData(data);
    }

    public static String dataParaString(Date data) {
        SimpleDateFormat formatoIng = new SimpleDateFormat("dd/MM/yyyy");
        return formatoIng.format(data);
    }

    public static String formataString(String texto) {
        if (texto == null) {
            return " ";
        }
        if (texto.trim().length() == 0) {
            return " ";
        }
        return texto.trim();
    }

    public static BigDecimal formataBigDecimal(BigDecimal valor) {
        if (valor == null) {
            return new BigDecimal(0);
        }
        return valor;
    }

    public static Integer formataInteger(Integer valor) {
        if (valor == null) {
            return 0;
        }
        return valor;
    }

    public static Long formataLong(Long valor) {
        if (valor == null) {
            return 0l;
        }
        return valor;
    }

    public static java.sql.Date converteData(java.util.Date data) {
        GregorianCalendar m_cal = new GregorianCalendar(TimeZone.getTimeZone("GMT-4"));
        m_cal.setTime(data);
        return new java.sql.Date(m_cal.getTime().getTime());
    }

//    public static String removeEnter(Object object) {
//        List<String> lista = new LinkedList<String>();
//        String texto = "";
//        if (object != null) {
//            texto = object.toString();
//            Integer posicao = 0;
//            if (texto.length() >= 2) {
//                for (int i = 0; i < texto.length(); i++) {
//                    if (texto.substring(i, i + 1).equals("\n")) {
//                        lista.add(texto.substring(posicao, i + 1));
//                        posicao = i + 1;
//                    }
//                }
//            }
//        }
//
//        texto = "";
//        for (String string : lista) {
//            string = string.replaceAll("\n", "QuebraLinha");
//            if (!string.trim().equals("QuebraLinha")) {
//                string = string.replaceAll("QuebraLinha", "\n");
//                texto = texto + "\n" + string.trim();
//            }
//            //System.out.println(string.trim().replaceAll("\n", "123") + " Linha Tamanho: " + string.length());
//        }
//        return texto;
//    }
    public static String removeEnter(Object object) {
        if (object != null) {
            return object.toString().trim();
        } else {
            return " ";
        }
    }

    public static String converteBooleanToString(Boolean valor) {
        if (valor) {
            return "true";
        } else {
            return "false";
        }
    }

    public static Date getDate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        System.out.println(sdf.format(gc.getTime()));

        gc.add(Calendar.HOUR, 30);
        System.out.println(sdf.format(gc.getTime()));

        return gc.getTime();
    }

    public static Integer getHora(String hora) {
        String valor = hora;
        return Integer.parseInt(valor.substring(0, 2));
    }

    public static Integer getMinuto(String minuto) {
        String valor = minuto;
        return Integer.parseInt(valor.substring(3, 5));
    }

    public static Integer getSegundo(String segundo) {
        String valor = segundo;
        return Integer.parseInt(valor.substring(6, 8));
    }

    public static String setHora(Integer horaBanco) {
        Integer hora;
        double minutos;
        double valorConversao = (Double.parseDouble(horaBanco.toString()) / 60d);
        hora = (int) (valorConversao);
        minutos = (valorConversao - (int) (valorConversao));
        minutos = converterDoubleDoisDecimais((minutos / 100) * 60);
        String sMinutos = Double.toString(minutos).substring(Double.toString(minutos).indexOf(".") + 1, Double.toString(minutos).length());
        String sHora = lpad(hora.toString(), "0", 2) + ":" + rpad(sMinutos, "0", 2);
        return sHora;
    }

    public static String horaAtualString() {
        return new SimpleDateFormat("HH:mm").format(new java.util.Date());
    }

    public static String horaAtualCompletaString() {
        return new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    }

    public static String dataHoraAtual() {
        return new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss.SSS").format(new java.util.Date());
    }
    
    public static String dataHoraAtualTexto() {
        return new SimpleDateFormat("dd-MM-yyyyHHmmss").format(new java.util.Date());
    }

    public static Integer horaAtualInteger() {
        return getHoraNumero(horaAtualString());
    }

    public static Integer getHoraNumero(String sHora) {
        String valor = sHora;
        Integer hora = Integer.parseInt(valor.substring(0, valor.indexOf(":")));
        Integer minutos = Integer.parseInt(valor.substring(valor.indexOf(":") + 1, valor.trim().length()));
        hora = (hora * 60) + minutos;
        return hora;
    }

    public static BigDecimal getHoraBigDecimal(String shora) {
        return new BigDecimal(getHoraNumero(shora));
    }

    public static double converterDoubleDoisDecimais(double precoDouble) {
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(precoDouble);
        String[] part = string.split("[,]");
        String string2 = part[0] + "." + part[1];
        double preco = Double.parseDouble(string2);
        return preco;
    }

    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

    public static String converteDataString(java.util.Date dtData, String mascara) {
        SimpleDateFormat out = new SimpleDateFormat(mascara);
        return dtData != null ? out.format(dtData) : "";
    }

    public static void considerarEnterComoTab(Component comp) {
        Set<AWTKeyStroke> keystrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newKeystrokes = new HashSet<>(keystrokes);
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeystrokes);

//        HashSet backup = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//        HashSet conj = (HashSet) backup.clone();
//        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
//        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
//        
//        HashSet backupCima = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//        HashSet conjCima = (HashSet) backupCima.clone();
//        conjCima.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, 0));
//        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conjCima);
        Set<AWTKeyStroke> forwardTraversalKeys = new HashSet<>(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardTraversalKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, KeyEvent.VK_UNDEFINED));

        Set<AWTKeyStroke> backwardTraversalKeys = new HashSet<>(comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardTraversalKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, KeyEvent.VK_UNDEFINED));

        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardTraversalKeys);
        comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardTraversalKeys);
    }

    public static LocalDate dateToLocalDate(Date data) {
        Instant instant = Instant.ofEpochMilli(data.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }

    public static String formatarString(String texto, String mascara) throws ParseException {
        MaskFormatter mf = new MaskFormatter(mascara);
        mf.setValueContainsLiteralCharacters(false);

        String textoRetorno = texto;

        if (textoRetorno == null) {
            textoRetorno = " ";
        }

        return textoRetorno.length() == 11 || textoRetorno.length() == 14 ? mf.valueToString(textoRetorno) : textoRetorno;
    }

    public static String formatarTelefone(String texto, String mascara) throws ParseException {
        MaskFormatter mf = new MaskFormatter(mascara);
        mf.setValueContainsLiteralCharacters(false);

        String textoRetorno = texto;

        if (textoRetorno == null) {
            textoRetorno = " ";
        }

        return mf.valueToString(textoRetorno);
    }

    public static double truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR).doubleValue();
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING).doubleValue();
        }
    }

    public static String doubleToString(double valor) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
        // Formato com sinal de menos -5.000,00
        DecimalFormat df1 = new DecimalFormat("#,##0.00", dfs);
        // Formato com parêntese (5.000,00)
        DecimalFormat df2 = new DecimalFormat("#,##0.00;(#,##0.00)", dfs);
//        d = -5000.00;
//        s = df1.format (d); 
//        System.out.println (s); // imprime -5.000,00
//        s = df2.format (d); 
//        System.out.println (s); // imprime (5.000,00)

        return df1.format(valor);
    }

    public static String doubleToString(double valor, int casasDemais) {
        return String.format("%." + casasDemais + "f", valor).replace(",", ".");
    }

    public static String removeAcentos(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }

    public static String getDataExtenso(Date data) {
        DateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
        return dfmt.format(data);
    }

    public static String space(int quantidadeEspacos) {
        return Utilitarios.lpad("", " ", quantidadeEspacos);
    }

    public static String replicate(String valor, int quantidadeEspacos) {
        return Utilitarios.lpad("", valor, quantidadeEspacos);
    }

}
