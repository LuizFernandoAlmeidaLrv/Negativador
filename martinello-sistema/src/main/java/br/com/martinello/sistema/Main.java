/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema;


import br.com.martinello.bd.matriz.control.ParametrosControl;
import br.com.martinello.sistema.view.TelaLogin;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Sidnei
 */
public class Main {

    
public String retorno;
    public static void main(String[] args) throws ErroSistemaException {        
     validaVersao("21/02/2022");
        if (isRunning(Main.class.getName())) {
            System.out.println("Aplicação está em execução");
          //  System.exit(0);
        }
        JWindow jwInicializandoSistema = new JWindow();
        JXBusyLabel jxblInicializandoSistema = new JXBusyLabel();
 
        jxblInicializandoSistema.setText("Inicializando banco de dados...");
        jxblInicializandoSistema.setBusy(true);
        jwInicializandoSistema.add(jxblInicializandoSistema);
        jwInicializandoSistema.setSize(300, 150);
        jwInicializandoSistema.setLocationRelativeTo(null);
        jwInicializandoSistema.setVisible(true);
        try {
            Properties props = new Properties();
            props.put("logoString", "NEGATIVADOR");         
            GraphiteLookAndFeel.setCurrentTheme(props);
            
            // select the Look and Feel
           // UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
           UIManager.setLookAndFeel(new FlatIntelliJLaf());
            //AcrylLookAndFeel.setCurrentTheme(props);
            //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            throw new ErroSistemaException("Não foi possível carregar o LookAndFeel", e);
        }

        java.awt.EventQueue.invokeLater(() -> {
            jwInicializandoSistema.setVisible(false);
        });
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }

    public static boolean isRunning(String applicationName) {
        try {
            Process process = Runtime.getRuntime().exec("jps -l");
            InputStream ips = process.getInputStream();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(ips));
            String line = null;
            List<String> javaProcesses = new ArrayList<String>();
            while ((line = bfr.readLine()) != null) {
                String[] splited = line.split(" ");
                javaProcesses.add(splited.length > 1 ? splited[1] : "");
            }
            Iterator<String> it = javaProcesses.iterator();
            Integer count = 0;
            while (it.hasNext()) {
                if (it.next().equals(applicationName)) {
                    count++;
                }
            }
            return count >= 2;
        } catch (IOException e) {
            return false;
        }
    }

    private static void validaVersao(String versao) throws ErroSistemaException{        
        try {
            ParametrosControl parametro = new ParametrosControl();
            parametro.ValidaVersao(versao);
           
        } catch (ErroSistemaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }  
          
    }

}
