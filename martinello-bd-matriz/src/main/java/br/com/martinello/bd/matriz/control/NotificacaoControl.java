/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.NotificacaoDAO;
import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class NotificacaoControl {
    NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
    public NotificacaoControl(){
        
    }
    public List<FilialModel> ListFiliasComErro() throws ErroSistemaException{
        return notificacaoDAO.ListLojasComErro();
    }
     public List<ParcelasEnviarModel> ListRegistrosComErro(String filial) throws ErroSistemaException{
        return notificacaoDAO.ListRegistrosComErro(filial);
    }
}
