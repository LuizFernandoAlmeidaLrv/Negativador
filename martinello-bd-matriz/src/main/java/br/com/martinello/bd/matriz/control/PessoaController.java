/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.PessoaDAO;
import br.com.martinello.bd.matriz.model.domain.PessoaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class PessoaController {

   private PessoaDAO pessoaDAO = new PessoaDAO();
    public List<PessoaModel> extrairInfoPessoa(PessoaModel pessoaModel) {
       return this.pessoaDAO.listarInfoPessoal(pessoaModel);
    }
     public List<PessoaModel> extrairInfoAvalista(PessoaModel pessoaModel) {
       return this.pessoaDAO.listarInfoPessoalAval(pessoaModel);
    }

    public void AtualizaPessoa(String codCli, String codFil, int idCliente) throws ErroSistemaException {
         this.pessoaDAO.atualizarPessoa(codCli, codFil, idCliente);
    }
    
}
