/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.DiversosDAO;
import br.com.martinello.util.excessoes.ErroSistemaException;

/**
 *
 * @author luiz.almeida
 */
public class DiversosControl {
    DiversosDAO diversosDAO = new DiversosDAO(); 

    public void AjustarE140NFV() throws ErroSistemaException {
       diversosDAO.AjustarE140NFV();
    }

    
}
