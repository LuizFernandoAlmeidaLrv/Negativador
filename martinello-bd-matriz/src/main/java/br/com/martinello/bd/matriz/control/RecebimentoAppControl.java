/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.RecebimentoAppDAO;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.RecebimentoApp;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RecebimentoAppControl {
     private RecebimentoAppDAO recebimentoAppDAO = new RecebimentoAppDAO();

    public List<RecebimentoApp> buscarRecebimento(RecebimentoApp recebimentoApp) throws ErroSistemaException {
        return this.recebimentoAppDAO.buscarRecebimento(recebimentoApp);
    }

    public void gerarPendenciaRecApp(ParcelaModel parcelaApp) throws ErroSistemaException {
        recebimentoAppDAO.gerarPendenciaRecApp(parcelaApp);
    }
}
