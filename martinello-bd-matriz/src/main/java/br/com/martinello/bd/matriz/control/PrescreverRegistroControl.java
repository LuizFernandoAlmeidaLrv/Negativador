/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.util.excessoes.ErroSistemaException;

/**
 *
 * @author luiz.almeida
 */
public class PrescreverRegistroControl {

    public boolean resultado;
    private ProcessamentoController processamento = new ProcessamentoController();
    private ProcessamentoModel processamentoModel = new ProcessamentoModel();

    public PrescreverRegistroControl() {

    }

    public void Prescrever(String usuario) throws ErroSistemaException {

        ParcelaController parcelaController = new ParcelaController();

        resultado = processamento.consultarProcessoPrecrever("EXTRATOR", "PRESCREVER");
        if (resultado == false) {
            int cont;
            cont = parcelaController.prescreveRegistroExtrator();
            parcelaController.prescreveRegistroImport();
            parcelaController.prescreveRegistroImpcdl();
            processamentoModel = new ProcessamentoModel();
            processamentoModel.setProvedor("EXTRATOR");
            processamentoModel.setTipo("PRESCREVER");
            processamentoModel.setItensTotal(cont);
            processamentoModel.setUser(usuario);
            processamento.insertProcessoPrescreverDB(processamentoModel);
        }

    }

}
