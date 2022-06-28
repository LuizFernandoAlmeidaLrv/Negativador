/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.EnderecoModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class EnderecoDAO {

    private String sqlSelectEndereco, sqlSelectEnderecoAval;
    private PreparedStatement psEndereco;
    private ResultSet rsEndereco;
    private List<EnderecoModel> lenderecoModel, lenderecoAvalModel;
    private EnderecoModel enderecoModel;

    public List<EnderecoModel> listarEndereco(EnderecoModel enderecoModel, ParcelaModel parcelaModel) throws ErroSistemaException {
        sqlSelectEndereco = "SELECT PESSOA.NOME,\n"
                + "       PESSOA.ENDERECO,\n"
                + "       PESSOA.ID_LOGRADOURO,\n"
                + "       PESSOA.NUMERO,\n"
                + "       PESSOA.COMPLEMENTO,\n"
                + "       PESSOA.BAIRRO,\n"
                + "       PESSOA.CIDADE,\n"
                + "       PESSOA.UF_ESTADO,\n"
                + "       PESSOA.CODIGO_IBGE,\n"
                + "       PESSOA.CEP\n"
                + "  FROM PESSOA, PARCELA\n"
                + " WHERE PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "   AND PARCELA.ID_PARCELA = ?";

        try {
            lenderecoModel = new ArrayList<>();
            psEndereco = Conexao.getConnection().prepareStatement(sqlSelectEndereco);
            psEndereco.setString(1, parcelaModel.getIdParcela());
            rsEndereco = psEndereco.executeQuery();
            while (rsEndereco.next()) {
                enderecoModel = new EnderecoModel();
                enderecoModel.setCep(rsEndereco.getString("CEP"));
                enderecoModel.setCidade(rsEndereco.getString("CIDADE"));
                enderecoModel.setCodigoIbge(rsEndereco.getString("CODIGO_IBGE"));
                enderecoModel.setEndBairro(rsEndereco.getString("BAIRRO"));
                enderecoModel.setEndComplemento(rsEndereco.getString("COMPLEMENTO"));
                enderecoModel.setEndIdLogradouro(rsEndereco.getString("ID_LOGRADOURO"));
                enderecoModel.setEndNumero(rsEndereco.getString("NUMERO"));
                enderecoModel.setUfEstado(rsEndereco.getString("UF_ESTADO"));
                enderecoModel.setEndLogradouro(rsEndereco.getString("ENDERECO"));
                lenderecoModel.add(enderecoModel);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psEndereco.close();
                rsEndereco.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }

        return lenderecoModel;

    }

    public List<EnderecoModel> listarEnderecoAvalista(EnderecoModel enderecoModel, ParcelaModel parcelaModel) throws ErroSistemaException {
        EnderecoModel enderecoAvalModel;
        sqlSelectEnderecoAval = "SELECT PESSOA.NOME,\n"
                + "       PESSOA.ENDERECO,\n"
                + "       PESSOA.ID_LOGRADOURO,\n"
                + "       PESSOA.NUMERO,\n"
                + "       PESSOA.COMPLEMENTO,\n"
                + "       PESSOA.BAIRRO,\n"
                + "       PESSOA.CIDADE,\n"
                + "       PESSOA.UF_ESTADO,\n"
                + "       PESSOA.CODIGO_IBGE,\n"
                + "       PESSOA.CEP\n"
                + "  FROM PESSOA, PARCELA\n"
                + " WHERE PESSOA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + "   AND PARCELA.ID_PARCELA = ?";

        try {
            lenderecoAvalModel = new ArrayList<>();
            psEndereco = Conexao.getConnection().prepareStatement(sqlSelectEnderecoAval);
            psEndereco.setString(1, parcelaModel.getIdExtrator());
            rsEndereco = psEndereco.executeQuery();
            while (rsEndereco.next()) {
                enderecoAvalModel = new EnderecoModel();
                enderecoAvalModel.setCep(rsEndereco.getString("CEP"));
                enderecoAvalModel.setCidade(rsEndereco.getString("CIDADE"));
                enderecoAvalModel.setCodigoIbge(rsEndereco.getString("CODIGO_IBGE"));
                enderecoAvalModel.setEndBairro(rsEndereco.getString("BAIRRO"));
                enderecoAvalModel.setEndComplemento(rsEndereco.getString("COMPLEMENTO"));
                enderecoAvalModel.setEndIdLogradouro(rsEndereco.getString("ID_LOGRADOURO"));
                enderecoAvalModel.setEndNumero(rsEndereco.getString("NUMERO"));
                enderecoAvalModel.setUfEstado(rsEndereco.getString("UF_ESTADO"));
                enderecoAvalModel.setEndLogradouro(rsEndereco.getString("ENDERECO"));
                lenderecoAvalModel.add(enderecoAvalModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psEndereco.close();
                rsEndereco.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return lenderecoAvalModel;
    }

    public void AtualizarEndereco(EnderecoModel enderecoModel) {
        String sqlUpdate = "";
        sqlUpdate = "UPDATE PESSOA "
                + "             SET ENDERECO,\n"
                + "       ID_LOGRADOURO,\n"
                + "       NUMERO,\n"
                + "       COMPLEMENTO,\n"
                + "       BAIRRO,\n"
                + "       CIDADE,\n"
                + "       UF_ESTADO,\n"
                + "       CODIGO_IBGE,\n"
                + "       CEP\n";
    }
}
