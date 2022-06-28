/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.RetornoEnvioDAO;
import br.com.martinello.util.excessoes.ErroSistemaException;

/**
 *
 * @author luiz.almeida
 */
public class RetornoEnvioController {

    RetornoEnvioDAO retornoEnvio = new RetornoEnvioDAO();
   

    public RetornoEnvioController() {

    }

    public int atualizaStatus(int idExtrator) throws ErroSistemaException {
        return this.retornoEnvio.sinclonizaRetorno(idExtrator);
    }

    public void retornoContasReceber() throws ErroSistemaException {
        retornoEnvio.retornoContasReceber();
    }
    
    public void forcarPendencia() throws ErroSistemaException {
        retornoEnvio.ForcarPendencia();
    }

    public int SinclonizaStatus() throws ErroSistemaException {
        return this.retornoEnvio.buscaASinclonizar();
    }

    
}
