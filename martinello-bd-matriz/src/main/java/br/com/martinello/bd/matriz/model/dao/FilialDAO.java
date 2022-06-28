/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.FilialModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class FilialDAO {

    private PreparedStatement psFiliais, psFilialInt, psInsertFilialInt, psUpdateFiliaisInt, psUpdateFiliaisExt, psVerificaMovCpf;
    private ResultSet rsFiliais, rsFilialInt, rsInsertFilialInt, rsUpdateFiliaisInt, rsUpdateFiliaisExt, rsVerificaMovCpf;
    private List<FilialModel> lfiliaisModel, lfiliaisCbBoxModel, lfiliaisIntegracao;
    private String sqlSelect, sqlSelecCombobox, sqlSelectIntegracao, sqlSelectFilialIntegrar, sqlSelectFilialIntegrada, sqlSelectFiliais,
            sqlInsertFiliais, sqlUpdateFiliaisInt, sqlVerificaMovCpf;
    private int resultado;
    private boolean resultInsert;

    public List<FilialModel> listarFiliais(FilialModel filtroFilialModel) throws ErroSistemaException {
        try {

            FilialModel filiaisModel;

            sqlSelect = "SELECT ID_FILIAL, NOME_FILIAL, CHAVE_FACMAT, KEY_FACMAT, COD_BVS, COD_FACMAT, COD_SPC, OPERADOR_SPC, SENHA_SPC, CNPJ, DATA_INICIO_OPER, EMAIL, STATUS_SPC, STATUS_FACMAT, USUARIO, DATA_ULT_ALT,"
                    + " DATA_INICIO_DBNOVO, ATIVO  FROM FILIAIS" + getWhere(filtroFilialModel) + " ORDER BY ID_FILIAL";
            System.out.println(sqlSelect);
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelect);
            lfiliaisModel = new ArrayList<>();
            rsFiliais = psFiliais.executeQuery();

            while (rsFiliais.next()) {
                filiaisModel = new FilialModel();
                filiaisModel.setPontoFilial(rsFiliais.getString("ID_FILIAL"));
                filiaisModel.setNomeLoja(rsFiliais.getString("NOME_FILIAL"));
                filiaisModel.setChaveFacmat(rsFiliais.getString("CHAVE_FACMAT"));
                filiaisModel.setKeyFacmat(rsFiliais.getString("KEY_FACMAT"));
                filiaisModel.setCodigoBvs(rsFiliais.getString("COD_BVS"));
                filiaisModel.setCodigoFacmat(rsFiliais.getString("COD_FACMAT"));
                filiaisModel.setCodigoSpc(rsFiliais.getString("COD_SPC"));
                filiaisModel.setOperadorSpc(rsFiliais.getString("OPERADOR_SPC"));
                filiaisModel.setSenhaSpc(rsFiliais.getString("SENHA_SPC"));
                filiaisModel.setCnpjLoja(rsFiliais.getString("CNPJ"));
                filiaisModel.setDatainicioOperacao(rsFiliais.getDate("DATA_INICIO_OPER"));
                filiaisModel.setDataOperacaoDbNovo(rsFiliais.getDate("DATA_INICIO_DBNOVO"));
                filiaisModel.setEmail(rsFiliais.getString("EMAIL"));
                filiaisModel.setStatus(rsFiliais.getString("Ativo"));
                filiaisModel.setStatusSpc(rsFiliais.getString("STATUS_SPC"));
                filiaisModel.setStatusFacmat(rsFiliais.getString("STATUS_FACMAT"));
                if (rsFiliais.getDate("DATA_ULT_ALT") != null) {
                    filiaisModel.setDataUltAlteracao(Utilitarios.converteData(rsFiliais.getDate("DATA_ULT_ALT")));
                }

                filiaisModel.setUsuario(rsFiliais.getString("USUARIO"));

                lfiliaisModel.add(filiaisModel);
            }
            resultado = psFiliais.executeUpdate();
            if (resultado == -1) {
                psFiliais.close();
                rsFiliais.close();
                return null;
            } else {
                psFiliais.close();
                rsFiliais.close();
                return lfiliaisModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        }
    }

    public List<FilialModel> listarFilial() throws ErroSistemaException {
        try {

            FilialModel filialModel;

            sqlSelecCombobox = "SELECT '0' CODPTO,\n"
                    + "       '0' ID_FILIAL,\n"
                    + "       'TODAS' NOME_FILIAL,\n"
                    + "       'SGL' ORIGEM_BD,\n"
                    + "       0 CODFIL\n"
                    + "  FROM DUAL\n"
                    + "UNION ALL\n"
                    + "SELECT FILIAL_SGL,\n"
                    + "       ID_FILIAL,       \n"
                    + "       NOME_FILIAL,\n"
                    + "       CASE\n"
                    + "         WHEN DATA_INICIO_DBNOVO = '31/12/1900' THEN\n"
                    + "          'SGL'\n"
                    + "         WHEN DATA_INICIO_DBNOVO > '31/12/1900' THEN\n"
                    + "          'NOVO_SGL'\n"
                    + "       END ORIGEM_BD,\n"
                    + "       CODFIL\n"
                    + "  FROM FILIAIS"
                    + "   ORDER BY ID_FILIAL ASC";
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelecCombobox);
            lfiliaisCbBoxModel = new ArrayList<>();
            rsFiliais = psFiliais.executeQuery();

            while (rsFiliais.next()) {
                filialModel = new FilialModel();
                filialModel.setPontoFilial(rsFiliais.getString("ID_FILIAL"));
                filialModel.setFilialSgl(rsFiliais.getString("CODPTO"));
                filialModel.setNomeLoja(rsFiliais.getString("NOME_FILIAL"));
                filialModel.setOrigemBD(rsFiliais.getString("ORIGEM_BD"));
                filialModel.setCodFil(rsFiliais.getString("CODFIL"));
                lfiliaisCbBoxModel.add(filialModel);
            }
            resultado = psFiliais.executeUpdate();
            if (resultado == -1) {
                psFiliais.close();
                rsFiliais.close();
                return null;
            } else {
                psFiliais.close();
                rsFiliais.close();
                return lfiliaisCbBoxModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        }
    }

    public int salvarFiliaisDAO(FilialModel listFiliais) throws ErroSistemaException {
        try {

            String sqlInsert = "INSERT INTO FILIAIS\n"
                    + "  (ID_FILIAL, FILIAL_SGL, NOME_FILIAL, CHAVE_FACMAT, KEY_FACMAT, COD_BVS, COD_FACMAT, COD_SPC, OPERADOR_SPC, SENHA_SPC, CNPJ, DATA_INICIO_OPER, DATA_INICIO_DBNOVO, ATIVO, STATUS_SPC, STATUS_FACMAT, USUARIO, DATA_ULT_ALT, EMAIL)\n"
                    + "VALUES\n"
                    + "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psFiliais = Conexao.getConnection().prepareStatement(sqlInsert);
            psFiliais.setString(1, listFiliais.getPontoFilial());
            psFiliais.setString(2, listFiliais.getFilialSgl());
            psFiliais.setString(3, listFiliais.getNomeLoja());
            psFiliais.setString(4, listFiliais.getChaveFacmat());
            psFiliais.setString(5, listFiliais.getKeyFacmat());
            psFiliais.setString(6, listFiliais.getCodigoBvs());
            psFiliais.setString(7, listFiliais.getCodigoFacmat());
            psFiliais.setString(8, listFiliais.getCodigoSpc());
            psFiliais.setString(9, listFiliais.getOperadorSpc());
            psFiliais.setString(10, listFiliais.getSenhaSpc());
            psFiliais.setString(11, listFiliais.getCnpjLoja());
            psFiliais.setString(12, Utilitarios.converteDataString(listFiliais.getDatainicioOperacao(), "dd/MM/yyyy"));
            psFiliais.setString(13, Utilitarios.converteDataString(listFiliais.getDataOperacaoDbNovo(), "dd/MM/yyyy"));
            psFiliais.setString(14, listFiliais.getStatus());
            psFiliais.setString(15, listFiliais.getStatusSpc());
            psFiliais.setString(16, listFiliais.getStatusFacmat());
            psFiliais.setString(17, listFiliais.getUsuario());
            psFiliais.setDate(18, Utilitarios.converteData(new Date()));
            psFiliais.setString(19, listFiliais.getEmail());

            resultado = psFiliais.executeUpdate();
            Conexao.getConnection().commit();

            if (resultado == -1) {
                psFiliais.close();
                return 0;
            } else {
                psFiliais.close();
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        } finally {
            try {
                psFiliais.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static java.sql.Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        java.sql.Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public boolean excluirFiliaisDAO(int codigo) throws SQLException {
        try {
            String sqlExcluir = ("DELETE FROM FILIAIS WHERE ID_FILIAL = '" + codigo + "'");

            psFiliais = Conexao.getConnection().prepareStatement(sqlExcluir);
            this.psFiliais.executeUpdate();
            Conexao.getConnection().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            psFiliais.close();
        }
    }

    public FilialModel retornarFiliaisDAO(String codigo) throws ErroSistemaException {
        FilialModel filiaisModel = new FilialModel();
        try {
            String sqlSelect = "SELECT ID_FILIAL, FILIAL_SGL, NOME_FILIAL, CHAVE_FACMAT, KEY_FACMAT, COD_BVS, COD_FACMAT, COD_SPC, OPERADOR_SPC, SENHA_SPC, CNPJ, DATA_INICIO_OPER, DATA_INICIO_DBNOVO, ATIVO, STATUS_SPC, STATUS_FACMAT, USUARIO, DATA_ULT_ALT, EMAIL  FROM FILIAIS WHERE ID_FILIAL = '" + codigo + "'";
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelect);

            rsFiliais = psFiliais.executeQuery(sqlSelect);

            while (rsFiliais.next()) {
                filiaisModel = new FilialModel();
                filiaisModel.setPontoFilial(rsFiliais.getString("ID_FILIAL"));
                filiaisModel.setNomeLoja(rsFiliais.getString("NOME_FILIAL"));
                filiaisModel.setChaveFacmat(rsFiliais.getString("CHAVE_FACMAT"));
                filiaisModel.setKeyFacmat(rsFiliais.getString("KEY_FACMAT"));
                filiaisModel.setCodigoBvs(rsFiliais.getString("COD_BVS"));
                filiaisModel.setCodigoFacmat(rsFiliais.getString("COD_FACMAT"));
                filiaisModel.setCodigoSpc(rsFiliais.getString("COD_SPC"));
                filiaisModel.setOperadorSpc(rsFiliais.getString("OPERADOR_SPC"));
                filiaisModel.setSenhaSpc(rsFiliais.getString("SENHA_SPC"));
                filiaisModel.setCnpjLoja(rsFiliais.getString("CNPJ"));
                filiaisModel.setDatainicioOperacao(rsFiliais.getDate("DATA_INICIO_OPER"));
                filiaisModel.setDataOperacaoDbNovo(rsFiliais.getDate("DATA_INICIO_DBNOVO"));
                filiaisModel.setStatus(rsFiliais.getString("Ativo"));
                filiaisModel.setStatusSpc(rsFiliais.getString("STATUS_SPC"));
                filiaisModel.setStatusFacmat(rsFiliais.getString("STATUS_FACMAT"));
                filiaisModel.setUsuario(rsFiliais.getString("USUARIO"));
                filiaisModel.setDataUltAlteracao(rsFiliais.getDate("DATA_ULT_ALT"));
                filiaisModel.setEmail(rsFiliais.getString("EMAIL"));
                filiaisModel.setFilialSgl(rsFiliais.getString("FILIAL_SGL"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                psFiliais.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return filiaisModel;
    }

    public boolean atualizarFiliaisDAO(FilialModel filialModel) throws SQLException {
        try {

            String sqlUpdate;
            sqlUpdate = ("UPDATE FILIAIS SET\n"
                    + " NOME_FILIAL = ?,"
                    + " CHAVE_FACMAT = ?,"
                    + "KEY_FACMAT = ?,"
                    + " COD_BVS = ?, "
                    + "COD_FACMAT = ?,"
                    + "COD_SPC = ?, "
                    + "OPERADOR_SPC = ?, "
                    + "SENHA_SPC = ?, "
                    + "CNPJ = ?, "
                    + "DATA_INICIO_DBNOVO = ?, "
                    + "ATIVO = ?,"
                    + "STATUS_SPC = ?,"
                    + "STATUS_FACMAT = ?,"
                    + "USUARIO = ?,"
                    + "DATA_ULT_ALT = ?,"
                    + "EMAIL = ? "
                    + "WHERE ID_FILIAL = ? ");
            psFiliais = Conexao.getConnection().prepareStatement(sqlUpdate);
            psFiliais.setString(1, filialModel.getNomeLoja());
            psFiliais.setString(2, filialModel.getChaveFacmat());
            psFiliais.setString(3, filialModel.getKeyFacmat());
            psFiliais.setString(4, filialModel.getCodigoBvs());
            psFiliais.setString(5, filialModel.getCodigoFacmat());
            psFiliais.setString(6, filialModel.getCodigoSpc());
            psFiliais.setString(7, filialModel.getOperadorSpc());
            psFiliais.setString(8, filialModel.getSenhaSpc());
            psFiliais.setString(9, filialModel.getCnpjLoja());
            psFiliais.setDate(10, new java.sql.Date(filialModel.getDataOperacaoDbNovo().getTime()));
            psFiliais.setString(11, filialModel.getStatus());
            psFiliais.setString(12, filialModel.getStatusSpc());
            psFiliais.setString(13, filialModel.getStatusFacmat());
            psFiliais.setString(14, filialModel.getUsuario());
            psFiliais.setDate(15, Utilitarios.converteData(new Date()));
            psFiliais.setString(16, filialModel.getEmail());
            psFiliais.setString(17, filialModel.getPontoFilial());

            psFiliais.executeUpdate();
            Conexao.getConnection().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            psFiliais.close();
        }
    }

    public int buscarFiliaisIntegradas(String usuario) throws ErroSistemaException {

        sqlSelectFilialIntegrar = "SELECT LPAD(PROCESSAMENTO.PTO, 4, 0) AS PTO,\n"
                + "       PROCESSAMENTO.DATA,\n"
                + "       PROCESSAMENTO.DATAPROCESSAMENTO\n"
                + "  FROM DB_INTEGRACAO.PROCESSAMENTO PROCESSAMENTO\n"
                + " WHERE (PROCESSAMENTO.DATA =\n"
                + "       (SELECT MAX(USU_DIAANT)\n"
                + "           FROM SAPIENS.USU_T000DAT\n"
                + "          WHERE USU_DIAUTI <= SYSDATE))";

        sqlSelectFiliais = "SELECT ID_FILIAL_EXTRACAO,\n"
                + "       FILIAL,\n"
                + "       FILIAIS.NOME_FILIAL,\n"
                + "       FILIAIS.CNPJ,\n"
                + "       FILIAIS.ATIVO,\n"
                + "       EXTRAIDO,\n"
                + "       FILIAL_EXTRACAO.USUARIO,\n"
                + "       PROCESSADO,\n"
                + "       DATA_PROCESSAMENTO,\n"
                + "       DATA_EXTRACAO\n"
                + "  FROM FILIAL_EXTRACAO\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = FILIAL_EXTRACAO.FILIAL\n"
                + " WHERE (DATA_EXTRACAO = TO_DATE(SYSDATE) AND\n"
                + "       FILIAL_EXTRACAO.FILIAL = ? AND FILIAL_EXTRACAO.DATA_PROCESSAMENTO = '31/12/1900')";

        sqlUpdateFiliaisInt = ("UPDATE FILIAL_EXTRACAO SET"
                + " PROCESSADO = ?,"
                + " FILIAL_EXTRACAO.USUARIO = ?,"
                + " DATA_PROCESSAMENTO = ?"
                + " WHERE ID_FILIAL_EXTRACAO = ?");

        try {
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelectFilialIntegrar);
            rsFiliais = psFiliais.executeQuery();
            psFilialInt = Conexao.getConnection().prepareStatement(sqlSelectFiliais);

            while (rsFiliais.next()) {
                psFilialInt.setString(1, rsFiliais.getString("PTO"));
                rsFilialInt = psFilialInt.executeQuery();
                if (rsFilialInt.next()) {

                    psUpdateFiliaisInt = Conexao.getConnection().prepareStatement(sqlUpdateFiliaisInt);
                    psUpdateFiliaisInt.setString(1, "S");
                    psUpdateFiliaisInt.setString(2, usuario);
                    psUpdateFiliaisInt.setDate(3, rsFiliais.getDate("DATA"));
                    psUpdateFiliaisInt.setInt(4, rsFilialInt.getInt("ID_FILIAL_EXTRACAO"));
                    psUpdateFiliaisInt.executeUpdate();
                }
                Conexao.getConnection().commit();
            }
            resultado = psFiliais.executeUpdate();
            if (resultado == -1) {
                psFiliais.close();
                psFilialInt.close();
                rsFiliais.close();
                rsFilialInt.close();
                return 0;
            } else {
                psFiliais.close();
                psFilialInt.close();
                rsFiliais.close();
                rsFilialInt.close();
                return 1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public int inserirFilialIntegracao() throws ErroSistemaException {

        sqlSelectFiliais = "SELECT ID_FILIAL_EXTRACAO,\n"
                + "       FILIAL,\n"
                + "       FILIAIS.NOME_FILIAL,\n"
                + "       FILIAIS.CNPJ,\n"
                + "       FILIAIS.ATIVO,\n"
                + "       EXTRAIDO,\n"
                + "       FILIAL_EXTRACAO.USUARIO,\n"
                + "       PROCESSADO,\n"
                + "       DATA_PROCESSAMENTO,\n"
                + "       DATA_EXTRACAO\n"
                + "  FROM FILIAL_EXTRACAO\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = FILIAL_EXTRACAO.FILIAL\n"
                + " WHERE (DATA_EXTRACAO = TO_DATE(SYSDATE) AND\n"
                + "       FILIAL_EXTRACAO.FILIAL = ?)";

        sqlSelect = "SELECT ID_FILIAL,"
                + " NOME_FILIAL,"
                + " CNPJ,"
                + " DATA_INICIO_OPER,"
                + " DATA_INICIO_DBNOVO,"
                + " ATIVO"
                + "  FROM FILIAIS";

        sqlInsertFiliais = "INSERT INTO FILIAL_EXTRACAO(ID_FILIAL_EXTRACAO, FILIAL, EXTRAIDO, PROCESSADO, DATA_PROCESSAMENTO, DATA_EXTRACAO) VALUES(ID_FIL_INT.nextval, ?, ?, ?, ?, ?)";
        try {
            psFilialInt = Conexao.getConnection().prepareStatement(sqlSelectFiliais);

            psFiliais = Conexao.getConnection().prepareStatement(sqlSelect);
            rsFiliais = psFiliais.executeQuery();
            while (rsFiliais.next()) {
                psFilialInt.setString(1, rsFiliais.getString("ID_FILIAL"));
                rsFilialInt = psFilialInt.executeQuery();
                if (rsFilialInt.next()) {

                } else {
                    psInsertFilialInt = Conexao.getConnection().prepareStatement(sqlInsertFiliais);
                    psInsertFilialInt.setString(1, rsFiliais.getString("ID_FILIAL"));
                    psInsertFilialInt.setString(2, "N");
                    psInsertFilialInt.setString(3, "N");
                    psInsertFilialInt.setString(4, "31/12/1900");
                    psInsertFilialInt.setString(5, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
                    psInsertFilialInt.executeUpdate();
                    psInsertFilialInt.close();
                }
                Conexao.getConnection().commit();
            }
            resultado = psFiliais.executeUpdate();

            if (resultado == -1) {
                psFiliais.close();
                psFilialInt.close();
                rsFiliais.close();
                rsFilialInt.close();

                return 0;
            } else {
                psFiliais.close();
                psFilialInt.close();
                rsFiliais.close();
                rsFilialInt.close();

                return 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public List<FilialModel> consultarFilialInt(FilialModel filtroFilialModel) throws ErroSistemaException {

        sqlSelectFiliais = "SELECT ID_FILIAL_EXTRACAO,\n"
                + "       FILIAL,\n"
                + "       FILIAIS.NOME_FILIAL,\n"
                + "       FILIAIS.CNPJ,\n"
                + "       FILIAIS.ATIVO,\n"
                + "       FILIAIS.DATA_INICIO_OPER,\n"
                + "       FILIAIS.DATA_INICIO_DBNOVO,\n"
                + "       EXTRAIDO,\n"
                + "       FILIAL_EXTRACAO.USUARIO,\n"
                + "       PROCESSADO,\n"
                + "       DATA_PROCESSAMENTO,\n"
                + "       DATA_EXTRACAO\n"
                + "  FROM FILIAL_EXTRACAO\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = FILIAL_EXTRACAO.FILIAL\n"
                + " WHERE (DATA_EXTRACAO = TO_DATE(SYSDATE))" + getAnd(filtroFilialModel);
        FilialModel filialIntegracaoModel;
        try {
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelectFiliais);
            rsFiliais = psFiliais.executeQuery();
            lfiliaisIntegracao = new ArrayList<>();
            while (rsFiliais.next()) {
                filialIntegracaoModel = new FilialModel();
                filialIntegracaoModel.setPontoFilial(rsFiliais.getString("FILIAL"));
                if (rsFiliais.getString("PROCESSADO").equalsIgnoreCase("S")) {
                    filialIntegracaoModel.setStatusIntegracao("INTEGRADO");
                } else {
                    filialIntegracaoModel.setStatusIntegracao("NAO INTEGRADO");
                }
                if (rsFiliais.getString("EXTRAIDO").equalsIgnoreCase("S")) {
                    filialIntegracaoModel.setStatusExtracao("EXTRAIDO");
                } else {
                    filialIntegracaoModel.setStatusExtracao("NAO EXTRAIDO");
                }
                filialIntegracaoModel.setStatus(rsFiliais.getString("ATIVO"));
                filialIntegracaoModel.setNomeLoja(rsFiliais.getString("NOME_FILIAL"));
                filialIntegracaoModel.setCnpjLoja(rsFiliais.getString("CNPJ"));
                filialIntegracaoModel.setDatainicioOperacao(rsFiliais.getDate("DATA_INICIO_OPER"));
                filialIntegracaoModel.setDataOperacaoDbNovo(rsFiliais.getDate("DATA_INICIO_DBNOVO"));
                lfiliaisIntegracao.add(filialIntegracaoModel);
            }

            resultInsert = psFiliais.execute();
            if (resultado == -1) {
                psFiliais.close();
                rsFiliais.close();
                return lfiliaisIntegracao;
            } else {
                psFiliais.close();
                rsFiliais.close();
                return lfiliaisIntegracao;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public String getAnd(FilialModel filtroFilialModel) {
        String where = "";

        where += ((filtroFilialModel.getStatusIntegracao().trim().length() >= 6) ? " AND FILIAL_EXTRACAO.PROCESSADO = '" + filtroFilialModel.getStatusIntegracao() + "'" : "");
        where += ((filtroFilialModel.getStatusExtracao().trim().length() >= 6) ? " AND FILIAL_EXTRACAO.EXTRAIDO = '" + filtroFilialModel.getStatusExtracao() + "'" : "");
        if (!where.isEmpty()) {
            where = " AND  0 = 0 " + where;
        }
        return where;
    }

    public String getWhere(FilialModel filtroFilialModel) {
        String where = "";
        where += ((filtroFilialModel.getNomeLoja() != null) ? " AND FILIAIS.NOME_FILIAL LIKE '" + filtroFilialModel.getNomeLoja() + "'" : "");
        where += ((filtroFilialModel.getPontoFilial() != null) ? " AND FILIAIS.ID_FILIAL = '" + filtroFilialModel.getPontoFilial() + "'" : "");
        where += ((filtroFilialModel.getCnpjLoja() != null) && !filtroFilialModel.getCnpjLoja().isEmpty() ? " AND FILIAIS.CNPJ = '" + filtroFilialModel.getCnpjLoja() + "'" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public FilialModel buscarFilialExtracao(String filial) throws ErroSistemaException {

        String sqlSelectFilial = "SELECT ID_FILIAL, FILIAL_SGL, NOME_FILIAL, CHAVE_FACMAT, KEY_FACMAT, COD_BVS, COD_FACMAT, COD_SPC, OPERADOR_SPC, SENHA_SPC, CNPJ, DATA_INICIO_OPER, DATA_INICIO_DBNOVO, ATIVO, STATUS_SPC, STATUS_FACMAT, USUARIO, DATA_ULT_ALT, EMAIL  FROM FILIAIS WHERE NOME_FILIAL = '" + filial + "'";
        FilialModel filialModel = new FilialModel();

        try {
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelectFilial);
            rsFiliais = psFiliais.executeQuery();

            if (rsFiliais.next()) {
                filialModel.setFilialSgl(rsFiliais.getString("FILIAL_SGL"));
                filialModel.setDataOperacaoDbNovo(rsFiliais.getDate("DATA_INICIO_DBNOVO"));
                filialModel.setPontoFilial(rsFiliais.getString("ID_FILIAL"));
            }
            psFiliais.close();
            rsFiliais.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return filialModel;

    }

    public FilialModel buscarFilialExtracaoManual(String filial) throws ErroSistemaException {

        String sqlSelectFilial = "SELECT ID_FILIAL,\n"
                + "       FILIAL_SGL,\n"
                + "       NOME_FILIAL,\n"
                + "       CHAVE_FACMAT,\n"
                + "       KEY_FACMAT,\n"
                + "       COD_BVS,\n"
                + "       COD_FACMAT,\n"
                + "       COD_SPC,\n"
                + "       OPERADOR_SPC,\n"
                + "       SENHA_SPC,\n"
                + "       CNPJ,\n"
                + "       DATA_INICIO_OPER,\n"
                + "       DATA_INICIO_DBNOVO,\n"
                + "       ATIVO,\n"
                + "       STATUS_SPC,\n"
                + "       STATUS_FACMAT,\n"
                + "       USUARIO,\n"
                + "       DATA_ULT_ALT,\n"
                + "       EMAIL\n"
                + "  FROM FILIAIS\n"
                + " WHERE ID_FILIAL = " + filial + "";
        FilialModel filialModel = new FilialModel();

        try {
            psFiliais = Conexao.getConnection().prepareStatement(sqlSelectFilial);
            rsFiliais = psFiliais.executeQuery();

            if (rsFiliais.next()) {
                filialModel.setFilialSgl(rsFiliais.getString("FILIAL_SGL"));
                filialModel.setDataOperacaoDbNovo(rsFiliais.getDate("DATA_INICIO_DBNOVO"));
                filialModel.setPontoFilial(rsFiliais.getString("ID_FILIAL"));
            }
            psFiliais.close();
            rsFiliais.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return filialModel;

    }

    public List<FilialModel> filialExt(List<FilialModel> lfilialExt) throws ErroSistemaException {
        String updateFilialExtaida = ("UPDATE FILIAL_EXTRACAO\n"
                + "   SET FILIAL_EXTRACAO.EXTRAIDO = 'S'\n"
                + " WHERE DATA_EXTRACAO = TO_DATE(SYSDATE)\n"
                + "   AND FILIAL = ?");
        for (FilialModel filialExt : lfilialExt) {
            try {
                psFiliais = Conexao.getConnection().prepareStatement(updateFilialExtaida);
                psFiliais.setString(1, filialExt.getPontoFilial());
                psFiliais.execute();
                Conexao.getConnection().commit();
                psFiliais.close();
            } catch (ErroSistemaException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }

        return null;

    }

    public boolean verificaMovCpf(String idFilial) throws ErroSistemaException {
        try {
            sqlVerificaMovCpf = "SELECT 1 FROM PARCELA WHERE ID_FILIAL = ?";
            psVerificaMovCpf = Conexao.getConnection().prepareStatement(sqlVerificaMovCpf);
            psVerificaMovCpf.setString(1, idFilial);
            rsVerificaMovCpf = psVerificaMovCpf.executeQuery();
            if (rsVerificaMovCpf.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }
}
