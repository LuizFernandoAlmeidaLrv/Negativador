/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ImpcdlDAO;
import br.com.martinello.bd.matriz.model.domain.ImpcdlModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ImpcdlController {

    ImpcdlDAO impcdlDAO = new ImpcdlDAO();

    public ImpcdlController() {

    }

    public List<ImpcdlModel> buscaImpcdl(ImpcdlModel impcdlModel) throws ErroSistemaException {
        return impcdlDAO.BuscaImpcdl(impcdlModel);
    }

    public void alterarRelacionado(ImpcdlModel impcdl) throws ErroSistemaException {
       impcdlDAO.alterarRelacionado(impcdl);
    }

}
