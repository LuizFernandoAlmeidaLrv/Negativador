/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.RetornoProvedorDAO;
import br.com.martinello.bd.matriz.model.domain.RetornoProvedorModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RetornoProvedorControl {
    private RetornoProvedorDAO retornoProvedorDao = new RetornoProvedorDAO();
    public List<RetornoProvedorModel> buscaProcessoDB(RetornoProvedorModel retonoProvedor) throws ErroSistemaException {
       return retornoProvedorDao.buscarRetorno(retonoProvedor);
    }
    
}
