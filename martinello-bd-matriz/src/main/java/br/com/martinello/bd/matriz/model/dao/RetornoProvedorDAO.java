/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.RetornoProvedorModel;
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
public class RetornoProvedorDAO {

    private String sqlSelectRetornoProvedor;
    private List<RetornoProvedorModel> lRetornoProvedorModel;
    private PreparedStatement psRetornoProvedor;
    private ResultSet rsRetornoProvedor;
    private int resultado;

    public RetornoProvedorDAO() {

    }

    public List<RetornoProvedorModel> buscarRetorno(RetornoProvedorModel retonoProvedor) throws ErroSistemaException {
        sqlSelectRetornoProvedor = "SELECT ID_RETORNO,\n"
                + "       CODIGO,\n"
                + "       DESCRICAO,\n"
                + "       COMENTARIO,\n"
                + "       CASE\n"
                + "         WHEN UPPER(TIPO) = 'S' THEN\n"
                + "          'SPC'\n"
                + "         WHEN UPPER(TIPO) = 'C' OR TIPO = 'B' THEN\n"
                + "          'FACMAT'\n"
                + "         WHEN UPPER(TIPO) = 'E' THEN\n"
                + "          'EXTRATOR'\n"
                + "       END TIPO,\n"
                + "       CASE\n"
                + "         WHEN UPPER(NOTIFICAR) = 'S' THEN\n"
                + "          'SIM'\n"
                + "         ELSE\n"
                + "          'NÃO'\n"
                + "       END NOTIFICAR\n"
                + "  FROM RETORNOS_INTEGRACAO" + getWhere(retonoProvedor);
        try {
            psRetornoProvedor = Conexao.getConnection().prepareStatement(sqlSelectRetornoProvedor);
            RetornoProvedorModel retornoProvedorModel;
            lRetornoProvedorModel = new ArrayList<>();
            rsRetornoProvedor = psRetornoProvedor.executeQuery();

            while (rsRetornoProvedor.next()) {

                retornoProvedorModel = new RetornoProvedorModel();
                retornoProvedorModel.setIdRetorno(rsRetornoProvedor.getInt("ID_RETORNO"));
                retornoProvedorModel.setCodigo(rsRetornoProvedor.getString("CODIGO"));
                retornoProvedorModel.setDescricao(rsRetornoProvedor.getString("DESCRICAO"));
                retornoProvedorModel.setComentario(rsRetornoProvedor.getString("COMENTARIO"));
                retornoProvedorModel.setTipo(rsRetornoProvedor.getString("TIPO"));
                retornoProvedorModel.setNotificar(rsRetornoProvedor.getString("NOTIFICAR"));

                lRetornoProvedorModel.add(retornoProvedorModel);
            }
            psRetornoProvedor.close();
            rsRetornoProvedor.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lRetornoProvedorModel;

    }

    public String getWhere(RetornoProvedorModel retonoProvedor) {
        String where = "";
        if (!retonoProvedor.getTipo().trim().equals("")) {
            if (retonoProvedor.getTipo().toString().equals("SPC")) {
                where += ((retonoProvedor.getTipo().toString().equals("SPC")) ? " AND TIPO = 'S'" : "");
            }
            if (retonoProvedor.getTipo().toString().equals("FACMAT")) {
                where += ((retonoProvedor.getTipo().toString().equals("FACMAT")) ? " AND TIPO IN ('B', 'C')" : "");
            }
            if (retonoProvedor.getTipo().toString().equals("EXTRATOR")) {
                where += ((retonoProvedor.getTipo().toString().equals("EXTRATOR")) ? " AND TIPO = 'E'" : "");
            }
        }
        if (!retonoProvedor.getCodigo().equals("")) {
            where += ((retonoProvedor.getCodigo() != null) ? " AND CODIGO = '" + retonoProvedor.getCodigo() + "'" : "");

        }
        if (retonoProvedor.getIdRetorno() > 0) {
            where += ((retonoProvedor.getIdRetorno() > 0) ? " AND ID_RETORNO = '" + retonoProvedor.getIdRetorno() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

}
