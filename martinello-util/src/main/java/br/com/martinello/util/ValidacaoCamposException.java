/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

/**
 *
 * @author Sidnei
 */
public class ValidacaoCamposException extends Exception {

    public ValidacaoCamposException(String message) {
        super(message);
    }

    public ValidacaoCamposException(String message, Exception exception) {
        super(message, exception);
    }

    public ValidacaoCamposException(Exception exception) {
        super(exception);
    }

}
