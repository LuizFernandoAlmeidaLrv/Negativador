/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ExtracaoDAO;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasExtracaoController {

    ExtracaoDAO parcelasDAO = new ExtracaoDAO();
    ExtracaoDAO novaExtracao = new ExtracaoDAO();

    public List<ParcelaModel> buscarExtracaoManual(ConsultaModel filtroConsulta) throws ErroSistemaException {
        return this.novaExtracao.BuscarParcelaManual(filtroConsulta);
    }

   public void realizarExtracao(List<ExtracaoModel> lExtracaoModel, String retornoExtracao) throws ErroSistemaException {
        novaExtracao.realizarExtracao(lExtracaoModel, retornoExtracao);
    }

}
