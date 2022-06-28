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
public class ValidacaoException extends Exception {

    public ValidacaoException(String message) {
        super(message);
    }

    public ValidacaoException(String message, Exception exception) {
        super(message, exception);
    }

    public ValidacaoException(Exception exception) {
        super(exception);
    }

}
