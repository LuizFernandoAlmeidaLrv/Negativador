/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ParcelasEnviarBvsFacmatDAO;
import br.com.martinello.bd.matriz.model.dao.ParcelasEnviarSpcDAO;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarController {

    private ParcelasEnviarSpcDAO parcelasSpsDAO = new ParcelasEnviarSpcDAO();

    public List<ParcelasEnviarModel> listarInclusaoSpc() throws ErroSistemaException {
        return this.parcelasSpsDAO.BuscarSpcDAO();
    }

    public void AtualizarParcelaSpcCliente(ParcelasEnviarModel parcelasEnviarSpcModel, String incluirSpcFaultcode, String incluirSpcResponse) throws ErroSistemaException {
        parcelasSpsDAO.retornoInclusaoSpcClienteDAO(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
    }

    public void AtualizarParcelaSpcAvalista(ParcelasEnviarModel parcelasEnviarSpcModel, String incluirSpcFaultcode, String incluirSpcResponse) throws ErroSistemaException {
        parcelasSpsDAO.retornoInclusaoSpcAvalistaDAO(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
    }

    //baixa
    public List<ParcelasEnviarModel> listarBaixaSpc() throws ErroSistemaException {
        return this.parcelasSpsDAO.BuscarSpcBaixaDAO();
    }

    public boolean AtualizarParcelaSpcClienteB(ParcelasEnviarModel parcelasEnviarSpcModel, String baixarSpcResponse, String baixarSpcFaultCode) throws ErroSistemaException {
        return this.parcelasSpsDAO.retornoBaixaSpcClienteDAO(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
    }

    public boolean AtualizarParcelaSpcAvalistaB(ParcelasEnviarModel parcelasEnviarSpcModel, String baixarSpcResponse, String baixarSpcFaultCode) throws ErroSistemaException {
        return this.parcelasSpsDAO.retornoBaixaSpcAvalistaDAO(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
    }

    /**
     * Controller Facmat 
     */
    private ParcelasEnviarBvsFacmatDAO parcelasBvsDAO = new ParcelasEnviarBvsFacmatDAO();

    public List<ParcelasEnviarModel> listarInclusaoBvs() throws ErroSistemaException {
        return this.parcelasBvsDAO.BuscarBvsDAO();
    }

    public List<ParcelasEnviarModel> listarBaixaBvs() throws ErroSistemaException {
        return this.parcelasBvsDAO.BuscarBaixaBvsDAO();
    }

    public boolean RetornoInclusaoBvs(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel, String bvsMenssage, String incluirBvsIdRetorno) throws ErroSistemaException {
        return this.parcelasBvsDAO.retornoBvsInclusaoDAO(parcelasEnviarInclusaoBvsModel, bvsMenssage, incluirBvsIdRetorno);
    }

    public boolean RetornoAtualizaInclusaoBvs(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel, String bvsMenssage, String incluirBvsIdRetorno) throws ErroSistemaException {
        return this.parcelasBvsDAO.retornoBvsAtualizacaoInclusaoDAO(parcelasEnviarInclusaoBvsModel, bvsMenssage, incluirBvsIdRetorno);
    }

    public List<ParcelasEnviarModel> consultarInclusao() throws ErroSistemaException {
        return this.parcelasBvsDAO.parcelasConsultarInclusao();
    }

    public boolean RetornoConsultaInclusao(ParcelasEnviarModel parcelasConsultarBvsModel, String consultaBvsMenssage) throws ErroSistemaException {
        return this.parcelasBvsDAO.retornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
    }

    public void insertLog(ParcelasEnviarModel parcelasConsultarBvsModel, String consultaBvsMenssage) throws ErroSistemaException {
        parcelasBvsDAO.insertLogErro(parcelasConsultarBvsModel, consultaBvsMenssage);
    }

    public boolean AtualizarParcelaBaixaBvs(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage) throws ErroSistemaException {
        return this.parcelasBvsDAO.retornoBaixaBvsDAO(parcelasEnviarBvsModel, bvsMenssage);
    }

    public void AtualizarParcelaBaixaBvsErro(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage) {
        this.parcelasBvsDAO.insertLogErro(parcelasEnviarBvsModel, bvsMenssage);
    }
}
