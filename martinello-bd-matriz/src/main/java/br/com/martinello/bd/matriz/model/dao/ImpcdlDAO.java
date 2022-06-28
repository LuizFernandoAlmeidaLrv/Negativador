/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ImpcdlModel;
import br.com.martinello.util.Utilitarios;
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
public class ImpcdlDAO {

    private String selectImpcdl;
    private PreparedStatement psSelectImpcdl, psUpdateImpcdl;
    private ResultSet rsSelectImpcdl;
    private List<ImpcdlModel> LImpcdl = new ArrayList<>();

    public ImpcdlDAO() {

    }

    public List<ImpcdlModel> BuscaImpcdl(ImpcdlModel impcdlModel) throws ErroSistemaException {
        selectImpcdl = " SELECT IMPCDLCONTRATO,\n"
                + "                      IMPCDLCNPJCPF,\n"
                + "                      IMPCDLDATAVENCIMENTO,\n"
                + "                      IMPCDLDATAINCLUSAO,\n"
                + "                      IMPCDLTIPODEVEDOR,\n"
                + "                      IMPCDLCONSUMIDOR,\n"
                + "                      IMPCDLDATACOMPRA,\n"
                + "                      IMPCDLVALOR,\n"
                + "                      IMPCDLORIGEMINCLUSAO,\n"
                + "                      IMPCDLDATADISPONIVEL,\n"
                + "                      IMPCDLORIGEMEXCLUSAO,\n"
                + "                      IMPCDLDATAEXCLUSAO,\n"
                + "                      IMPCDLDATABAIXAAR,\n"
                + "                      IMPCDLTIPONOTIFICACAO,\n"
                + "                      IMPCDLMOTIVOEXCLUSAO,\n"
                + "                      IMPCDLLOGRADOURO,\n"
                + "                      IMPCDLDATAENCONTRADO,\n"
                + "                      IMPCDLENCONTRADO,\n"
                + "                      IMPCDLPROVEDOR,\n"
                + "                      IMPCDLSTATUS,\n"
                + "                      IMPCDLCOMPLEMENTO,\n"
                + "                      IMPCDLNUMERO,\n"
                + "                      IMPCDLBAIRRO,\n"
                + "                      IMPCDLCIDADE,\n"
                + "                      IMPCDLUF,\n"
                + "                      IMPCDLCEP,\n"
                + "                      IMPCDLNOMEMAE,\n"
                + "                      IMPCDLDATANASCIMENTO,\n"
                + "                      IMPCDLFILIAL,\n"
                + "                      IMPCDLCONTRATODIGITADO,\n"
                + "                      IMPCDLCONTRATOREAL,\n"
                + "                      IMPCDLIDENTIFICACAO\n"
                + "                 FROM IMPCDL" + getWhere(impcdlModel);
        try {
            LImpcdl = new ArrayList<>();
            ImpcdlModel impcdl = new ImpcdlModel();
            psSelectImpcdl = Conexao.getConnection().prepareStatement(selectImpcdl);
            rsSelectImpcdl = psSelectImpcdl.executeQuery();
            while (rsSelectImpcdl.next()) {
                impcdl = new ImpcdlModel();
                impcdl.setImpcdlId(rsSelectImpcdl.getString("IMPCDLIDENTIFICACAO"));
                impcdl.setImpcdlContratoDigitado(rsSelectImpcdl.getString("IMPCDLCONTRATODIGITADO"));
                impcdl.setImpcdlContratoReal(rsSelectImpcdl.getString("IMPCDLCONTRATOREAL"));
                impcdl.setImpcdlCpf(rsSelectImpcdl.getString("IMPCDLCNPJCPF"));
                impcdl.setImpcdlDataVencimento(rsSelectImpcdl.getDate("IMPCDLDATAVENCIMENTO"));
                impcdl.setImpcdlDataInclusao(rsSelectImpcdl.getDate("IMPCDLDATAINCLUSAO"));
                impcdl.setImpcdlTipoDevedor(rsSelectImpcdl.getString("IMPCDLTIPODEVEDOR"));
                impcdl.setImpcdlConsumidor(rsSelectImpcdl.getString("IMPCDLCONSUMIDOR").trim());
                impcdl.setImpcdlDataCompra(rsSelectImpcdl.getDate("IMPCDLDATACOMPRA"));
                impcdl.setImpcdlValor(rsSelectImpcdl.getDouble("IMPCDLVALOR"));
                impcdl.setImpcdlDataExclusao(rsSelectImpcdl.getDate("IMPCDLDATAEXCLUSAO"));
                impcdl.setImpcdlBairro(rsSelectImpcdl.getString("IMPCDLBAIRRO").trim());
                impcdl.setImpcdlCidade(rsSelectImpcdl.getString("IMPCDLCIDADE").trim());
                impcdl.setImpcdlCep(rsSelectImpcdl.getString("IMPCDLCEP"));
                impcdl.setImpcdlUf(rsSelectImpcdl.getString("IMPCDLUF"));
                impcdl.setImpcdlFilial(rsSelectImpcdl.getString("IMPCDLFILIAL"));
                impcdl.setNomeMae(rsSelectImpcdl.getString("IMPCDLNOMEMAE").trim());
                impcdl.setLogradouro(rsSelectImpcdl.getString("IMPCDLLOGRADOURO").trim());
                impcdl.setImpcdlDataNascimento(rsSelectImpcdl.getDate("IMPCDLDATANASCIMENTO"));
                impcdl.setDataEncontrado(rsSelectImpcdl.getDate("IMPCDLDATAENCONTRADO"));
                if (rsSelectImpcdl.getString("IMPCDLPROVEDOR").trim().equalsIgnoreCase("C")) {
                    impcdl.setProvedor("SPC");
                } else {
                    impcdl.setProvedor("BVS");
                }
                if (rsSelectImpcdl.getString("IMPCDLSTATUS").trim().equalsIgnoreCase("A")) {
                    impcdl.setStatus("AGUARDANDO");
                }
                if (rsSelectImpcdl.getString("IMPCDLSTATUS").trim().equalsIgnoreCase("B")) {
                    impcdl.setStatus("BAIXADO");
                }
                if (rsSelectImpcdl.getString("IMPCDLSTATUS").trim().equalsIgnoreCase("I")) {
                    impcdl.setStatus("IMPORTADO");
                }
                if (rsSelectImpcdl.getString("IMPCDLSTATUS").trim().equalsIgnoreCase("P")) {
                    impcdl.setStatus("PRESCRITO");
                }
                impcdl.setEncontrado(rsSelectImpcdl.getString("IMPCDLENCONTRADO"));

                LImpcdl.add(impcdl);

            }

            return LImpcdl;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public String getWhere(ImpcdlModel impcdlModel) {
        String where = "";

        if (impcdlModel != null) {
            if (impcdlModel.getImpcdlFilial() != null) {
                where += ((impcdlModel.getImpcdlFilial() != null) ? " AND IMPCDL.IMPCDLFILIAL = '" + impcdlModel.getImpcdlFilial() + "'" : "");
            }
            if (impcdlModel.getImpcdlCpf() != null) {
                where += ((impcdlModel.getImpcdlCpf() != null) ? " AND IMPCDL.IMPCDLCNPJCPF = '" + impcdlModel.getImpcdlCpf() + "'" : "");
            }
            if (impcdlModel.getImpcdlContratoReal() != ("**********")) {
                where += ((impcdlModel.getImpcdlContratoReal() != null) ? " AND IMPCDL.IMPCDLCONTRATOREAL = '" + impcdlModel.getImpcdlContratoReal() + "'" : "");
            }
            if (impcdlModel.getStatus() != null) {
                where += ((impcdlModel.getStatus() != null) ? " AND IMPCDL.IMPCDLSTATUS = '" + impcdlModel.getStatus() + "'" : "");
            }
            if (impcdlModel.getImpcdlConsumidor() != null) {
                where += ((impcdlModel.getImpcdlConsumidor() != null) ? " AND IMPCDL.IMPCDLCONSUMIDOR LIKE '" + impcdlModel.getImpcdlConsumidor() + "'" : "");
            }
            if (impcdlModel.getEncontrado() != null) {
                where += ((impcdlModel.getEncontrado() != null) ? " AND IMPCDL.IMPCDLENCONTRADO = '" + impcdlModel.getEncontrado() + "'" : "");
            }
            if (impcdlModel.getImpcdlDataVencimento() != null) {
                where += ((impcdlModel.getImpcdlDataVencimento() != null) ? " AND IMPCDL.IMPCDLDATAVENCIMENTO BETWEEN '"
                        + Utilitarios.converteDataString(impcdlModel.getImpcdlDataVencimento(), "dd/MM/yyyy") + "' AND '" + Utilitarios.converteDataString(impcdlModel.getImpcdlDataVencimentoFinal(), "dd/MM/yyyy") + "'" : "");
            }
            if (impcdlModel.getDataEncontrado() != null) {
                where += ((impcdlModel.getDataEncontrado() != null) ? " AND IMPCDL.IMPCDLDATAENCONTRADO = '" + Utilitarios.converteDataString(impcdlModel.getDataEncontrado(), "dd/MM/yyyy") + "'" : "");
            }

        }
        return where.trim().length() > 0 ? " WHERE 0 = 0 " + where : "";

    }

    public void alterarRelacionado(ImpcdlModel impcdl) throws ErroSistemaException {
        String updateImpcdl = ("UPDATE IMPCDL SET IMPCDLCONTRATOREAL = ?, IMPCDLCONTRATODIGITADO = ? WHERE IMPCDLIDENTIFICACAO = ?");

        try {
            psUpdateImpcdl = Conexao.getConnection().prepareStatement(updateImpcdl);
            psUpdateImpcdl.setString(1, impcdl.getImpcdlContratoReal());
            psUpdateImpcdl.setString(2, impcdl.getImpcdlContratoDigitado());
            psUpdateImpcdl.setString(3, impcdl.getImpcdlId());
            psUpdateImpcdl.execute();
            Conexao.getConnection().commit();
            psUpdateImpcdl.close();
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
                psUpdateImpcdl.close();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }
}
