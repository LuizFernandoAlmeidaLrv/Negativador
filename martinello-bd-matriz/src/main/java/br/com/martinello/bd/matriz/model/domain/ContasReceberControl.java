/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.domain;

import br.com.martinello.bd.matriz.model.dao.ContasReceberDAO;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
import br.com.martinello.consulta.Domain.VendasModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */

public class ContasReceberControl {

    public ContasReceberDAO contasReceberDAO;

    public ContasReceberControl() {
        contasReceberDAO = new ContasReceberDAO();
    }

    public List<VendasModel> buscarVendas(String sCodFil, int codCli) throws ErroSistemaException {
       return contasReceberDAO.buscarVendas(sCodFil, codCli);
    }

    public List<ContasReceberModel> buscarContasReceber(String sCodFil, int codCli) throws ErroSistemaException {
       return contasReceberDAO.buscarContasReceber(sCodFil, codCli);
    }

    public List<ParcelaModel> buscarE301TCR(ConsultaModel filtroConsulta) throws ErroSistemaException {
        return contasReceberDAO.BuscarE301TCR(filtroConsulta);
    }

    public void ExtratoCliente(long cgcCpf, String login, String userSO) throws ErroSistemaException {
       contasReceberDAO.extratoCliente(cgcCpf, login, userSO);
    }

    public double buscarValorDevolucao(String idRegistro, String cliente) throws ErroSistemaException {
        return contasReceberDAO.buscarValorDevolucao(idRegistro, cliente);
    }

    public void AtualizarContrato(ParcelaModel parcela) throws ErroSistemaException {
          contasReceberDAO.AtualizarContratoE301TCR(parcela);
    }

       

}
