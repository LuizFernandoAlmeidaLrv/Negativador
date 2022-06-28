/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ProcessamentoDAO;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ProcessamentoController {
    ProcessamentoDAO processamentoDAO = new ProcessamentoDAO();
    public ProcessamentoController(){
        processamentoDAO = new ProcessamentoDAO();
    }
//    public List<ProcessamentoModel> listarProcessosDAO(){
//       return this.processamentoDAO.listarProcessos();
//    }
    
    public boolean consultarProcesso(String provedor, String tipoRegistro) throws ErroSistemaException{
        return this.processamentoDAO.consultarProcesso(provedor, tipoRegistro);
    }
    public int insertProcessoDB(ProcessamentoModel processamentoModel) throws ErroSistemaException{
        return this.processamentoDAO.salvarProcesso(processamentoModel);
    }
    public int updateProcessoDB(ProcessamentoModel processamentoModel) throws ErroSistemaException{
        return this.processamentoDAO.updateProcessoDB(processamentoModel);
    }
     public boolean consultaExtracao()throws ErroSistemaException{
        return this.processamentoDAO.consultarProcessoExtracao();
    }
    public List<ProcessamentoModel> listProcessoDB() throws ErroSistemaException{
        return this.processamentoDAO.listarProcessos();
    }

    public void updateProcessoDBSpc(ProcessamentoModel processamentoModelSpc) throws ErroSistemaException {
        processamentoDAO.updateProcessoDBSpc(processamentoModelSpc);
    }
     public int insertProcessoPrescreverDB(ProcessamentoModel processamentoModel) throws ErroSistemaException{
        return this.processamentoDAO.salvarProcessoPrecrever(processamentoModel);
    }
     public boolean consultarProcessoPrecrever(String provedor, String tipoRegistro) throws ErroSistemaException{
        return this.processamentoDAO.consultarProcessoPrecrever(provedor, tipoRegistro);
    }

    public List<ProcessamentoModel> buscaProcessoDB(ProcessamentoModel consultaProcesso) throws ErroSistemaException {
         return this.processamentoDAO.buscarProcessos(consultaProcesso);
    }

    public void encerrarProcessos(UsuarioModel usuario) throws ErroSistemaException {
       processamentoDAO.encerrarProcessos(usuario);
    }
}
