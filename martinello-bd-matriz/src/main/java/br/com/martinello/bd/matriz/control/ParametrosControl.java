/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ParametrosDAO;
import br.com.martinello.bd.matriz.model.domain.ParametrosModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParametrosControl {
    
    private ParametrosDAO parametroDAO = new ParametrosDAO();
    public ParametrosControl(){
        
    }

    public List<ParametrosModel> buscarParametros() throws ErroSistemaException {
        return parametroDAO.ListParametros();
    }

    public void updateParametro(ParametrosModel parametro) throws ErroSistemaException {
       parametroDAO.updateParametro(parametro);
    }

    public void insertParametro(ParametrosModel parametro) throws ErroSistemaException {
       parametroDAO.insertParametro(parametro);
    }

    public void ValidaVersao(String versao) throws ErroSistemaException {
        parametroDAO.validaVensao(versao);
    }
}
