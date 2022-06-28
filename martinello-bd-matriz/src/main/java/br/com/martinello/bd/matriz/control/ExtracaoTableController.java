/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;


import br.com.martinello.bd.matriz.model.dao.ExtratorTableDAO;
import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ExtracaoTableController {
     private ExtratorTableDAO extracaoTableDAO = new ExtratorTableDAO();

    public List<ExtracaoTableModel> listarExtracaoTableController(ExtracaoTableModel filtroConsultaExtracao) throws ErroSistemaException {
        return this.extracaoTableDAO.listarExtracaoTable(filtroConsultaExtracao);
    }
    //public List<ExtracaoTableModel> listarExtracaoTableEnviomanual(String tipo) {
      //  return this.extracaoTableDAO.listarExtracaoTable(tipo);
   // }
    public List<ConsultaModel> listarMotBaixaBvs() throws ErroSistemaException {
    return this.extracaoTableDAO.listarMotivoBaixaBvs();
    }
    public List<ConsultaModel> listarMotBaixaSpc() throws ErroSistemaException {
    return this.extracaoTableDAO.listarMotivoBaixaSpc();
    }

    public List<ConsultaModel> listarMotInclusao() throws ErroSistemaException {
    return this.extracaoTableDAO.listarMotivoInclusaoBvs();
    }

    public List<ConsultaModel> listarNaturezaInclusao() throws ErroSistemaException {
       return this.extracaoTableDAO.listarNaturezaInclusao();
    }

    public List<ConsultaModel> listarNaturezaRegistro() throws ErroSistemaException {
    return this.extracaoTableDAO.listarNaturezaRegistro();    
    }

    
     
}
