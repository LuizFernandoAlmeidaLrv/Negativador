/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ParcelaDAO;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.LogParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParcelaController {

    ParcelaDAO parcelaDAO = new ParcelaDAO();

    public List<ParcelaModel> extrairParcela(ParcelaModel parcelaModel) throws ErroSistemaException {
        return this.parcelaDAO.extrairParcela(parcelaModel);
    }

    public List<LogParcelaModel> extrairLogParcela(ParcelaModel parcelaModel) throws SQLException, ErroSistemaException {
        return this.parcelaDAO.buscaLogParcela(parcelaModel);
    }

    public List<LogParcelaModel> extrairLog(LogParcelaModel filtroLogExtracao) throws SQLException, ErroSistemaException {
        return this.parcelaDAO.buscaLog(filtroLogExtracao);
    }

    public int prescreveRegistroExtrator() throws ErroSistemaException {
        return parcelaDAO.prescreverRegistroExtrator();
    }

    public void prescreveRegistroImport() throws ErroSistemaException {
        parcelaDAO.prescreverRegistroImport();

    }

    public void prescreveRegistroImpcdl() throws ErroSistemaException {
        parcelaDAO.prescreverRegistroImpcdl();

    }

    public void ExtornoContasReceber(ExtracaoTableModel extracaoModel) throws ErroSistemaException {
        parcelaDAO.extornarContasReceber(extracaoModel);
    }

    public ExtracaoTableModel buscarRegistro(String idExtracao) throws ErroSistemaException {
        return this.parcelaDAO.buscarRegistro(idExtracao);
    }

    public void finalizarRegistro(ExtracaoTableModel regFinalizar, int idExtracao) throws ErroSistemaException {
       parcelaDAO.finalizarRegistro(regFinalizar, idExtracao);
    }

    public void ExtornoE301TCR(ExtracaoTableModel extracaoModel, String Origem) throws ErroSistemaException {
       parcelaDAO.extornarContasReceberE301TCR(extracaoModel, Origem);
    }
      
}
