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
public class DBException extends RuntimeException {

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Exception exception) {
        super(message, exception);
    }

    public DBException(Exception exception) {
        super(exception);
    }

}
