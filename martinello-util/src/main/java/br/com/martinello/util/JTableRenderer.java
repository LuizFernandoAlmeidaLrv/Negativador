/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

/**
 *
 * @author luiz.almeida
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Alex Passos
 */
public class JTableRenderer extends DefaultTableCellRenderer  {

    protected void setValue(Object value){

        if (value instanceof ImageIcon){

            if (value != null){
                ImageIcon d = (ImageIcon) value;
                setIcon(d);
            } else{
                setText("");
                setIcon(null);
            }
            
        } else {
            super.setValue(value);
        }
        
    }// fim da função
    
}
