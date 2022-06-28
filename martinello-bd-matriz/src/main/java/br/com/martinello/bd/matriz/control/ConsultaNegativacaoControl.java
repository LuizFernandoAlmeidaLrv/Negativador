/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ConsultaNegativacaoDAO;
import br.com.martinello.bd.matriz.model.domain.ConsultaNegativacaoModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ConsultaNegativacaoControl {

    private ConsultaNegativacaoDAO consultaNegativacaoDAO = new ConsultaNegativacaoDAO();

    public List<ConsultaNegativacaoModel> listarNegativacaoSintetica(ExtracaoTableModel filtroConsultaExtracao) throws ErroSistemaException {
        return this.consultaNegativacaoDAO.listarNegativacaoSintetica(filtroConsultaExtracao);
    }

    public List<ExtracaoTableModel> consultaNegativacaoControl(ExtracaoTableModel filtroConsultaExtracao) throws ErroSistemaException {
        return this.consultaNegativacaoDAO.consultaNegativacao(filtroConsultaExtracao);
    }
}
