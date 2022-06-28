/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;

/**
 *
 * @author luiz.almeida
 */
public class SpcEnvioController {

    ParcelasEnviarBaixaSpcController parcelasEnviarBaixaSpcController;
    ParcelasEnviarInclusaoSpcController parcelasEnviarInclusaoSpcController;

    public SpcEnvioController() {

    }

    public int enviarBaixaSPC(ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
        parcelasEnviarBaixaSpcController = new ParcelasEnviarBaixaSpcController();
        return this.parcelasEnviarBaixaSpcController.callSoapWebService(parcelasEnviarSpcModel, tipo);
    }

    public int enviarInclusaoSPC(ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
       parcelasEnviarInclusaoSpcController = new ParcelasEnviarInclusaoSpcController();
       return this.parcelasEnviarInclusaoSpcController.callSoapWebService(parcelasEnviarSpcModel, tipo);
      
    }
}
