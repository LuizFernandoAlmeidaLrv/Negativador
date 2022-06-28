/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.FilialDAO;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class FilialController {

    private FilialDAO filiaisDAO = new FilialDAO();

    public List<FilialModel> listarFiliaisController(FilialModel filtroFilialModel) throws ErroSistemaException {
        return this.filiaisDAO.listarFiliais(filtroFilialModel);
    }

    public List<FilialModel> listarFilialController() throws ErroSistemaException {
        return this.filiaisDAO.listarFilial();
    }

    public int salvarFiliaisControler(FilialModel listFiliaisModel) throws ErroSistemaException, SQLException {
        return this.filiaisDAO.salvarFiliaisDAO(listFiliaisModel);
    }

    public boolean excluirFiliaisController(int codigo) throws ErroSistemaException, SQLException {
        return this.filiaisDAO.excluirFiliaisDAO(codigo);
    }

    public FilialModel retornarFiliaisController(String codigo) throws ErroSistemaException, SQLException {
        return this.filiaisDAO.retornarFiliaisDAO(codigo);
    }

    public boolean atualizarFiliaisController(FilialModel filiaisModel) throws SQLException {
        return this.filiaisDAO.atualizarFiliaisDAO(filiaisModel);
    }

    public List<FilialModel> listarFilialProcessada(FilialModel filtroFilialModel) throws ErroSistemaException {
        return this.filiaisDAO.consultarFilialInt(filtroFilialModel);
    }

    public int buscarFilialProcessada(String usuario) throws ErroSistemaException {
        return this.filiaisDAO.buscarFiliaisIntegradas(usuario);
    }

    public int insertFiliaisExtracao() throws ErroSistemaException {
        return this.filiaisDAO.inserirFilialIntegracao();
    }

    public FilialModel listarFilialExtracao(String filial) throws ErroSistemaException {
        return this.filiaisDAO.buscarFilialExtracao(filial);
    }

    public FilialModel listarFilialExtracaoManual(String filial) throws ErroSistemaException {
        return this.filiaisDAO.buscarFilialExtracaoManual(filial);
    }

    public List<FilialModel> atualizarExtracao(List<FilialModel> lfilialExt) throws ErroSistemaException {
        return this.filiaisDAO.filialExt(lfilialExt);
    }

    public boolean verificaMovCpf() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean verificaMovCpf(String idFilial) throws ErroSistemaException {
        return filiaisDAO.verificaMovCpf(idFilial);
    }

}
