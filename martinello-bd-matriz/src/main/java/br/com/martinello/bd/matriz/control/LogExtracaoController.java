/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.LogExtracaoDAO;
import br.com.martinello.bd.matriz.model.domain.LogExtracaoModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class LogExtracaoController {
    
    LogExtracaoDAO logExtracaoDAO = new LogExtracaoDAO();
    
   public LogExtracaoController(){
       
   }
   
   public List<LogExtracaoModel> BuscarLogExtracao(LogExtracaoModel filtroLogExtracao) throws ErroSistemaException{
       return this.logExtracaoDAO.logExtracao(filtroLogExtracao);
   }
    
}
