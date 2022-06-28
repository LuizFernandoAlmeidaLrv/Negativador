/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.EnderecoDAO;
import br.com.martinello.bd.matriz.model.domain.EnderecoModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class EnderecoController {
    EnderecoDAO enderecoDAO = new EnderecoDAO();

    public List<EnderecoModel> extrairEndereco(ParcelaModel parcelaModel, EnderecoModel enderecoModel) throws ErroSistemaException {
        return this.enderecoDAO.listarEndereco(enderecoModel, parcelaModel);
    }
     public List<EnderecoModel> extrairEnderecoAvalista(ParcelaModel parcelaModel, EnderecoModel enderecoModel) throws ErroSistemaException {
        return this.enderecoDAO.listarEnderecoAvalista(enderecoModel, parcelaModel);
    }

    public void AtualizarEndereco(EnderecoModel enderecoModel) {
       enderecoDAO.AtualizarEndereco(enderecoModel);
    }
}
