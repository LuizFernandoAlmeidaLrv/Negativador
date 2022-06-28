/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.domain;

import br.com.martinello.importador.dao.ImpContasReceberDAO;
import br.com.martinello.importador.dao.ImpcdlDAO;
import br.com.martinello.importador.dao.ImportContasReceberCorrecao;
import br.com.martinello.importador.dao.SincronizaSpcDAO;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luiz.almeida
 */
public class Main {

    public static void main(String[] args) throws ErroSistemaException {
//        FacmatController facmatController = new FacmatController();
//        facmatController.FacmatController(); 
//          BuscaClienteDAO clienteinclusao = new BuscaClienteDAO();
//          clienteinclusao.listarClientes();
//          ListCidadeDAO listCidade = new ListCidadeDAO();
//          listCidade.ListCidade();
        //ParcelasDAO extrator = new ParcelasDAO();
        //extrator.listarParcelas();
        //  extrator.Extrator();
//          InclusaoFacmat inclusaoFacmat = new InclusaoFacmat();
//        inclusaoFacmat.InclusaoFacmat();
//       String tipo = "A";    
        // ParcelasDAO parcelasDAO = new ParcelasDAO();
        // parcelasDAO.realizarExtracao(numeroDoc, filial, cliente, tipo, retornoInclusaoManual, tipoAcao);
        // parcelasDAO.realizarExtracao(soapAction, soapAction, soapAction, tipo, soapEndpointUrl, soapAction);
        // ParcelasEnviarInclusaoSpcController inclusaoSpcController = new ParcelasEnviarInclusaoSpcController();
        // inclusaoSpcController.ParcelasEnviarInclusaoSpcController();
        //ParcelasEnviarBaixaSpcController baixaSpcController = new ParcelasEnviarBaixaSpcController();
        //  baixaSpcController.ParcelasEnviarBaixaSpcController();
        // ImpContasReceberDAO impContasReceber = new ImpContasReceberDAO();
        boolean resultado;
        //ImportContasReceberCorrecao correcao = new ImportContasReceberCorrecao();
        // correcao.realizarExtracao();
        //resultado = impContasReceber.realizarExtracao();
//ImpcdlDAO impcdl = new ImpcdlDAO();
//resultado = impcdl.realizarExtracao();

        SincronizaSpcDAO sincronizaSpc = new SincronizaSpcDAO();
//    // resultado = sincronizaSpc.atualizaSpcImpcdl();
//        resultado = sincronizaSpc.atualizaBvsImpcdl();
        resultado = sincronizaSpc.atualizaBvsImport();
    }
}
