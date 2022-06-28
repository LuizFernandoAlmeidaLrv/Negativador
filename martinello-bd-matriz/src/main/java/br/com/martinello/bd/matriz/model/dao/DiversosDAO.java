/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author luiz.almeida
 */
public class DiversosDAO {

    private PreparedStatement psUpdateE140NFV, psBuscaRegistro;
    private ResultSet rsBuscaRegistro;
    private String sqlSelect, sqlUpdate;

    public String getSelectE140NFV() {
        sqlSelect = "SELECT E140NFV.CODEMP      E140NFVCODEMP,\n"
                + "       E140NFV.CODFIL      E140NFVCODFIL,\n"
                + "       E140NFV.CODSNF      E140NFVCODSNF,\n"
                + "       E140NFV.NUMNFV      E140NFVNUMNFV,\n"
                + "      usu.codigo_anterior,\n"
                + "       E140NFV.USU_CODVEN,\n"
                + "       E140NFV.DATEMI      E140NFVDATEMI,\n"
                + "       E140NFV.DATGER      E140NFVDATGER\n"
                + "  FROM SAPIENS.E140NFV,\n"
                + "       SAPIENS.E070FIL,\n"
                + "       SAPIENS.E140IPV,\n"
                + "       INTEGRACAOFL.NOTAS_FISCAIS NF,\n"
                + "       integracaofl.vendas        ven,\n"
                + "       integracaofl.usuarios      usu,\n"
                + "       SAPIENS.USU_V034FUN,\n"
                + "       SAPIENS.E075PRO\n"
                + " WHERE E070FIL.CODEMP = 1\n"
                + "   AND E140NFV.CODEMP = E070FIL.CODEMP\n"
                + "   AND nf.loja = ven.loja\n"
                + "   and nf.venda = ven.id_registro\n"
                + "   and ven.vendedor = usu.id_usuario\n"
                + "   and nf.codemp = e140nfv.codemp\n"
                + "   and nf.codfil = e140nfv.codfil\n"
                + "   and nf.codsnf = e140nfv.codsnf\n"
                + "   and nf.numnfs = e140nfv.numnfv\n"
                + "   AND USU_V034FUN.CODFIL = nf.CODFIL\n"
                + "   AND USU_V034FUN.NUMEMP = nf.CODEMP\n"
                + "   AND USU_V034FUN.USU_CODVEN = usu.codigo_anterior\n"
                + "   AND E140IPV.CODEMP = E140NFV.CODEMP\n"
                + "   AND E140IPV.CODFIL = E140NFV.CODFIL\n"
                + "   AND E140IPV.CODSNF = E140NFV.CODSNF\n"
                + "   AND E140IPV.NUMNFV = E140NFV.NUMNFV\n"
                + "   AND E075PRO.CODEMP = E140IPV.CODEMP\n"
                + "   AND E075PRO.CODPRO = E140IPV.CODPRO\n"
                + "   AND E075PRO.CODMAR = '000187'\n"
                + "   AND E070FIL.CODFIL > 1\n"
                + "   AND E070FIL.USU_DATIFL > '01/01/2020'\n"
                + "   AND DATEMI > E070FIL.USU_DATIFL\n"
                + "  and (E140NFV.USU_CODVEN = ' ' OR E140NFV.USU_CODVEN IS NULL)\n"
                + " GROUP BY E140NFV.CODEMP,\n"
                + "          E140NFV.CODFIL,\n"
                + "          E140NFV.CODSNF,\n"
                + "          E140NFV.NUMNFV,\n"
                + "        usu.codigo_anterior,\n"
                + "          E140NFV.USU_CODVEN,\n"
                + "          E140NFV.DATEMI,\n"
                + "          E140NFV.DATGER\n"
                + " ORDER BY E140NFV.CODFIL,  E140NFV.NUMNFV";

        return sqlSelect;

    }

    public String getUpdateE140NFV() {
        sqlUpdate = "UPDATE SAPIENS.E140NFV\n"
                + "    SET E140NFV.USU_CODVEN = ?\n"
                + "  WHERE E140NFV.CODEMP = ?\n"
                + "    AND E140NFV.CODFIL = ?\n"
                + "    AND E140NFV.CODSNF = ?\n"
                + "    AND E140NFV.NUMNFV = ?";
        return sqlUpdate;
    }

    public void AjustarE140NFV() throws ErroSistemaException {
        try (PreparedStatement psBuscaRegistro = Conexao.getConnection().prepareStatement(getSelectE140NFV())) {
            rsBuscaRegistro = psBuscaRegistro.executeQuery();
            psUpdateE140NFV = Conexao.getConnection().prepareStatement(getUpdateE140NFV());
            while (rsBuscaRegistro.next()) {
                psUpdateE140NFV.setString(1, rsBuscaRegistro.getString("codigo_anterior"));
                psUpdateE140NFV.setInt(2, rsBuscaRegistro.getInt("E140NFVCODEMP"));
                psUpdateE140NFV.setInt(3, rsBuscaRegistro.getInt("E140NFVCODFIL"));
                psUpdateE140NFV.setString(4, rsBuscaRegistro.getString("E140NFVCODSNF"));
                psUpdateE140NFV.setInt(5, rsBuscaRegistro.getInt("E140NFVNUMNFV"));
                psUpdateE140NFV.execute();
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }finally{
            try { 
                psUpdateE140NFV.close();
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

    }
    }
}
