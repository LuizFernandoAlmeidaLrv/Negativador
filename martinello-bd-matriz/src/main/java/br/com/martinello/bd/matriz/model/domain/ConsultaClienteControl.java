/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import br.com.martinello.bd.matriz.model.dao.ConsultaClienteDAO;
import br.com.martinello.consulta.Domain.ConsultaClienteModel;
import br.com.martinello.consulta.Domain.PessoaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import br.com.martinello.util.filtro.Filtro;
import java.util.List;


/**
 *
 * @author luiz.almeida
 */
public class ConsultaClienteControl {

    private ConsultaClienteDAO consultaClienteDAO = new ConsultaClienteDAO();

    public ConsultaClienteControl() {

    }

    public ConsultaClienteModel ConsultarCliente(String scodFil, int codCli) throws ErroSistemaException {
      return consultaClienteDAO.ConsultaCliente(scodFil, codCli);
    }

    public List<PessoaModel> listCliente(List<Filtro> lFiltros) throws ErroSistemaException {
       return consultaClienteDAO.lListarCliente(lFiltros);
    }

   

}
