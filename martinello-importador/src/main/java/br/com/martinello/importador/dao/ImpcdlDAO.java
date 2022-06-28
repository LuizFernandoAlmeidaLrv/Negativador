/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luiz.almeida
 */
public class ImpcdlDAO {

    private String selectImpcdl, sqlInsertImpcdl, sqlSelect;
    private ResultSet rsSelectImpcdl, rsSelect;
    private PreparedStatement psSelectImpcdl, psInsertImpcdl, psSelect;

    public ImpcdlDAO() {
        selectImpcdl = "SELECT impcdlidentificacao,\n"
                + "       impcdlprovedor,\n"
                + "       impcdlcontrato,\n"
                + "       impcdlcnpjcpf,\n"
                + "       impcdldatavencimento,\n"
                + "       impcdldatainclusao,\n"
                + "       impcdltipodevedor,\n"
                + "       impcdlconsumidor,\n"
                + "       impcdlcodigoassociado,\n"
                + "       impcdlnomeassociado,\n"
                + "       impcdldatacompra,\n"
                + "       impcdlvalor,\n"
                + "       impcdlorigeminclusao,\n"
                + "       impcdldatadisponivel,\n"
                + "       impcdlorigemexclusao,\n"
                + "       impcdldataexclusao,\n"
                + "       impcdldatabaixaar,\n"
                + "       impcdltiponotificacao,\n"
                + "       impcdlmotivoexclusao,\n"
                + "       impcdllogradouro,\n"
                + "       impcdlcomplemento,\n"
                + "       impcdlnumero,\n"
                + "       impcdlbairro,\n"
                + "       impcdlcidade,\n"
                + "       impcdluf,\n"
                + "       impcdlcep,\n"
                + "       impcdlencontrado,\n"
                + "       impcdlcontratoreal,\n"
                + "       impcdlnomemae,\n"
                + "       impcdldatanascimento,\n"
                + "       impcdlcpfcnpjreal,\n"
                + "       impcdlstatus,\n"
                + "       impcdlcepreal,\n"
                + "       impcdldataimportacao,\n"
                + "       impcdldataencontrado,\n"
                + "       impcdldatabaixado,\n"
                + "       impcdlcontratorelacionado,\n"
                + "       impcdlfilial,\n"
                + "       impcdlcontratodigitado,\n"
                + "       impcdlsituacao,\n"
                + "       impcdlgravadata\n"
                + "  FROM extrator.importacdl\n"
                + " WHERE impcdlcontratodigitado <> impcdlcontratoreal\n"
                + "   AND impcdlsituacao = 'A'\n"
                + "   AND impcdldatavencimento >= CURRENT_DATE - 1860\n"
                + "   AND impcdlprovedor = 'B'\n"
                + " AND IMPCDLDATABAIXADO = '0001-01-01'";

        sqlInsertImpcdl = "INSERT INTO IMPCDL (impcdlidentificacao,\n"
                + "       impcdlprovedor,\n"
                + "       impcdlcontrato,\n"
                + "       impcdlcnpjcpf,\n"
                + "       impcdldatavencimento,\n"
                + "       impcdldatainclusao,\n"
                + "       impcdltipodevedor,\n"
                + "       impcdlconsumidor,\n"
                + "       impcdlcodigoassociado,\n"
                + "       impcdlnomeassociado,\n"
                + "       impcdldatacompra,\n"
                + "       impcdlvalor,\n"
                + "       impcdlorigeminclusao,\n"
                + "       impcdldatadisponivel,\n"
                + "       impcdlorigemexclusao,\n"
                + "       impcdldataexclusao,\n"
                + "       impcdldatabaixaar,\n"
                + "       impcdltiponotificacao,\n"
                + "       impcdlmotivoexclusao,\n"
                + "       impcdllogradouro,\n"
                + "       impcdlcomplemento,\n"
                + "       impcdlnumero,\n"
                + "       impcdlbairro,\n"
                + "       impcdlcidade,\n"
                + "       impcdluf,\n"
                + "       impcdlcep,\n"
                + "       impcdlencontrado,\n"
                + "       impcdlcontratoreal,\n"
                + "       impcdlnomemae,\n"
                + "       impcdldatanascimento,\n"
                + "       impcdlcpfcnpjreal,\n"
                + "       impcdlstatus,\n"
                + "       impcdlcepreal,\n"
                + "       impcdldataimportacao,\n"
                + "       impcdldataencontrado,\n"
                + "       impcdldatabaixado,\n"
                + "       impcdlcontratorelacionado,\n"
                + "       impcdlfilial,\n"
                + "       impcdlcontratodigitado,\n"
                + "       impcdlsituacao) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlSelect = "SELECT *FROM IMPCDL WHERE IMPCDLPROVEDOR = ? AND IMPCDLTIPODEVEDOR = ? AND IMPCDLFILIAL = ? AND IMPCDLCONTRATOREAL = ? AND IMPCDLCNPJCPF = ?";
    }

    public boolean realizarExtracao() {

        try {
            psSelectImpcdl = ConexaoPGSQL.getConnection().prepareStatement(selectImpcdl);
            psSelect = Conexao.getConnection().prepareStatement(sqlSelect);
            rsSelectImpcdl = psSelectImpcdl.executeQuery();
            psInsertImpcdl = Conexao.getConnection().prepareStatement(sqlInsertImpcdl);
            while (rsSelectImpcdl.next()) {
               // psSelect.setString(1, rsSelectImpcdl.getString("impcdlprovedor"));
              //  psSelect.setString(2, rsSelectImpcdl.getString("impcdltipodevedor"));
              //  psSelect.setString(3, rsSelectImpcdl.getString("impcdlfilial"));
              //  psSelect.setString(4, rsSelectImpcdl.getString("impcdlcontratoreal"));
              //  psSelect.setString(5, rsSelectImpcdl.getString("impcdlcnpjcpf"));
               // rsSelect = psSelect.executeQuery();
               // if (rsSelect.next()) {
                 //   System.out.println("registro já existe " + "FILIAL:"+ rsSelectImpcdl.getString("impcdlfilial") +" PROVEDOR"+ rsSelectImpcdl.getString("impcdlprovedor") + " CONTRATO" + rsSelectImpcdl.getString("impcdlcontrato") + " CPF" + rsSelectImpcdl.getString("impcdlcnpjcpf")
                 //           + " " + rsSelectImpcdl.getString("impcdltipodevedor") + " NOME" + rsSelectImpcdl.getString("impcdlconsumidor").trim() + " CONTRATO REAL" + rsSelectImpcdl.getString("impcdlcontratoreal") + " CONTRATO DIGITADO" + rsSelectImpcdl.getString("impcdlcontratodigitado"));
                 //   rsSelect.close();
              //  } else {
                    psInsertImpcdl.setString(1, rsSelectImpcdl.getString("impcdlidentificacao"));
                    psInsertImpcdl.setString(2, rsSelectImpcdl.getString("impcdlprovedor"));
                    psInsertImpcdl.setString(3, rsSelectImpcdl.getString("impcdlcontrato"));
                    psInsertImpcdl.setString(4, rsSelectImpcdl.getString("impcdlcnpjcpf"));
                    psInsertImpcdl.setDate(5, rsSelectImpcdl.getDate("impcdldatavencimento"));
                    psInsertImpcdl.setDate(6, rsSelectImpcdl.getDate("impcdldatainclusao"));
                    psInsertImpcdl.setString(7, rsSelectImpcdl.getString("impcdltipodevedor"));
                    psInsertImpcdl.setString(8, rsSelectImpcdl.getString("impcdlconsumidor"));
                    psInsertImpcdl.setString(9, rsSelectImpcdl.getString("impcdlcodigoassociado"));
                    psInsertImpcdl.setString(10, rsSelectImpcdl.getString("impcdlnomeassociado"));
                    psInsertImpcdl.setDate(11, rsSelectImpcdl.getDate("impcdldatacompra"));
                    psInsertImpcdl.setBigDecimal(12, rsSelectImpcdl.getBigDecimal("impcdlvalor"));
                    psInsertImpcdl.setString(13, rsSelectImpcdl.getString("impcdlorigeminclusao"));
                    psInsertImpcdl.setDate(14, rsSelectImpcdl.getDate("impcdldatadisponivel"));
                    psInsertImpcdl.setString(15, rsSelectImpcdl.getString("impcdlorigemexclusao"));
                    psInsertImpcdl.setDate(16, rsSelectImpcdl.getDate("impcdldataexclusao"));
                    psInsertImpcdl.setDate(17, rsSelectImpcdl.getDate("impcdldatabaixaar"));
                    psInsertImpcdl.setString(18, rsSelectImpcdl.getString("impcdltiponotificacao"));
                    psInsertImpcdl.setString(19, rsSelectImpcdl.getString("impcdlmotivoexclusao"));
                    psInsertImpcdl.setString(20, rsSelectImpcdl.getString("impcdllogradouro"));
                    psInsertImpcdl.setString(21, rsSelectImpcdl.getString("impcdlcomplemento"));
                    psInsertImpcdl.setString(22, rsSelectImpcdl.getString("impcdlnumero"));
                    psInsertImpcdl.setString(23, rsSelectImpcdl.getString("impcdlbairro"));
                    psInsertImpcdl.setString(24, rsSelectImpcdl.getString("impcdlcidade"));
                    psInsertImpcdl.setString(25, rsSelectImpcdl.getString("impcdluf"));
                    psInsertImpcdl.setString(26, rsSelectImpcdl.getString("impcdlcep"));
                    psInsertImpcdl.setString(27, rsSelectImpcdl.getString("impcdlencontrado"));
                    psInsertImpcdl.setString(28, rsSelectImpcdl.getString("impcdlcontratoreal"));
                    psInsertImpcdl.setString(29, rsSelectImpcdl.getString("impcdlnomemae"));
                    psInsertImpcdl.setDate(30, rsSelectImpcdl.getDate("impcdldatanascimento"));
                    psInsertImpcdl.setString(31, rsSelectImpcdl.getString("impcdlcpfcnpjreal"));
                    psInsertImpcdl.setString(32, rsSelectImpcdl.getString("impcdlstatus"));
                    psInsertImpcdl.setString(33, rsSelectImpcdl.getString("impcdlcepreal"));
                    psInsertImpcdl.setDate(34, rsSelectImpcdl.getDate("impcdldataimportacao"));
                    psInsertImpcdl.setDate(35, rsSelectImpcdl.getDate("impcdldataencontrado"));
                    psInsertImpcdl.setDate(36, rsSelectImpcdl.getDate("impcdldatabaixado"));
                    psInsertImpcdl.setString(37, rsSelectImpcdl.getString("impcdlcontratorelacionado"));
                    psInsertImpcdl.setString(38, rsSelectImpcdl.getString("impcdlfilial"));
                    psInsertImpcdl.setString(39, rsSelectImpcdl.getString("impcdlcontratodigitado"));
                    psInsertImpcdl.setString(40, rsSelectImpcdl.getString("impcdlsituacao"));
                    //   psInsertImpcdl.setBoolean(41, rsSelectImpcdl.getBoolean("impcdlgravadata"));

                    psInsertImpcdl.execute();

              //  }

            }
            psSelect.close();
            psInsertImpcdl.close();
            rsSelectImpcdl.close();
            psSelectImpcdl.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ImpcdlDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ImpcdlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }
}
