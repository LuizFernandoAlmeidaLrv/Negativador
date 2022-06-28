/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util.excessoes;

/**
 *
 * @author Sidnei
 */
public class ConsistenciaException extends Exception {

    public ConsistenciaException(String message) {
        super(message);
    }

    public ConsistenciaException(String message, Exception exception) {
        super(message, exception);
    }

    public ConsistenciaException(Exception exception) {
        super(exception);
    }
}
