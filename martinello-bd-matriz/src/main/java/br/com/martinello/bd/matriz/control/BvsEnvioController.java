/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ParcelasEnviarBvsFacmatDAO;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;

/**
 *
 * @author luiz.almeida
 */
public class BvsEnvioController {

    ParcelasEnviarBaixaBvsController parcelasEnviarBaixaBvsController;
    ParcelasEnviarInclusaoBvsController parcelasEnviarInclusaoBvsController;
    ParcelasConsultaFacmatController parcelasConsultaFacmatController;
     ParcelasEnviarBvsFacmatDAO parcelasBvsDAO;

    public BvsEnvioController() {
         parcelasBvsDAO = new ParcelasEnviarBvsFacmatDAO();

    }

    public int enviarBaixaBVS(ParcelasEnviarModel parcelasEnviarBvsModel) throws ErroSistemaException {
        parcelasEnviarBaixaBvsController = new ParcelasEnviarBaixaBvsController();
        return parcelasEnviarBaixaBvsController.callSoapWebService(parcelasEnviarBvsModel);
    }

    public int enviarInclusaoBVS(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel) throws ErroSistemaException {
        parcelasEnviarInclusaoBvsController = new ParcelasEnviarInclusaoBvsController();
        return parcelasEnviarInclusaoBvsController.callSoapWebService(parcelasEnviarInclusaoBvsModel);
    }
    
    public int consultarInclusaoBvs(ParcelasEnviarModel parcelasConsultarBvsModel) throws ErroSistemaException {
        parcelasConsultaFacmatController = new ParcelasConsultaFacmatController();
     return parcelasConsultaFacmatController.callSoapWebService(parcelasConsultarBvsModel);
    }

    public int naoEnviarInclusaoBVS(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel)  throws ErroSistemaException {
      return parcelasBvsDAO.naoEnviarInclusaoBVS(parcelasEnviarInclusaoBvsModel);
    }
}
