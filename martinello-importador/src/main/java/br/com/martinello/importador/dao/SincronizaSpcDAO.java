/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;

import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.sql.Connection;
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
public class SincronizaSpcDAO {

    private String sqlSelectImpcdl, sqlSelectExtrator, sqlUpdateExtrator, sqlUpdateExtratorBvs, sqlSelectImport, sqlInsertLog, sqlSelectImportBvs, sqlSelectimpcdlBvs, sqlUpdateParcela, sqlUpdateImportProvedor;
    private ResultSet rsSelectImpcdl, rsSelectExtrator, rsSelectImport, rsSelectAvalista, rsVerificaAvalista, rsUpdateParcela;
    private PreparedStatement psSelectImpcdl, psSelectExtrator, psSelectImport, psUpdateExtrator, psSelectAvalista, psVerificaAvalista, psUpdateAvalista, psInsertLog, psUpdateParcela, psImportProvedor;
    private Connection connection;

    public SincronizaSpcDAO() {

        sqlSelectImpcdl = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "       EXTRATOR.ID_PARCELA,\n"
                + "       EXTRATOR.STATUS_SPC,"
                + "       IMPORT_PROVEDOR.ID_IMPORT,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.VENC,\n"
                + "       PARCELA.VALOR,\n"
                + "       IMPORT_PROVEDOR.CONTRATO      AS CONTRATO_IMPORT,\n"
                + "       IMPCDL.IMPCDLCONTRATODIGITADO AS CONTRATO_IMPCDL,\n"
                + "       IMPCDL.IMPCDLDATAINCLUSAO,\n"
                + "       PESSOA.NOME,\n"
                + "       PESSOA.CPF_RAZAO,\n"
                + "       FILIAIS.FILIAL_SGL\n"
                + "  FROM EXTRATOR\n"
                + " INNER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " INNER JOIN PESSOA\n"
                + "    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "  LEFT OUTER JOIN IMPCDL\n"
                + "    ON IMPCDL.IMPCDLCONTRATOREAL = PARCELA.NUMERO_DOC\n"
                + "   AND PESSOA.CPF_RAZAO = IMPCDL.IMPCDLCNPJCPF\n"
                + "   AND IMPCDL.IMPCDLPROVEDOR = 'C'\n"
                + "  LEFT OUTER JOIN IMPORT_PROVEDOR\n"
                + "    ON IMPORT_PROVEDOR.CONTRATO = IMPCDL.IMPCDLCONTRATODIGITADO\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = IMPORT_PROVEDOR.CPF\n"
                + "   AND IMPORT_PROVEDOR.PROVEDOR = 'C'\n"
                + " WHERE IMPCDL.IMPCDLCONTRATODIGITADO IS NOT NULL\n"
                + "   AND EXTRATOR.STATUS_SPC = 'P'\n"
                + "   AND IMPCDL.IMPCDLSITUACAO = 'A'\n"
                + "   AND IMPORT_PROVEDOR.CONTRATO IS NOT NULL\n"
                + " ORDER BY ID_EXTRATOR";

        sqlSelectImport = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "                       EXTRATOR.ID_PARCELA,\n"
                + "                       EXTRATOR.STATUS_SPC, "
                + "                       IMPORT_PROVEDOR.ID_IMPORT,\n"
                + "                       PARCELA.NUMERO_DOC,\n"
                + "                       PARCELA.VENC,\n"
                + "                       PARCELA.VALOR,"
                + "                       IMPORT_PROVEDOR.DATA_INCLUSAO_SPC,\n"
                + "                       IMPORT_PROVEDOR.CONTRATO      AS CONTRATO_IMPORT,\n"
                + "                       IMPCDL.IMPCDLCONTRATODIGITADO AS CONTRATO_IMPCDL,\n"
                + "                       IMPCDL.IMPCDLDATAINCLUSAO,\n"
                + "                       PESSOA.NOME,\n"
                + "                       PESSOA.CPF_RAZAO,\n"
                + "                       FILIAIS.FILIAL_SGL\n"
                + "                  FROM EXTRATOR\n"
                + "                 INNER JOIN PARCELA\n"
                + "                    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                 INNER JOIN PESSOA\n"
                + "                    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                 INNER JOIN FILIAIS\n"
                + "                    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "                  LEFT OUTER JOIN IMPCDL\n"
                + "                    ON IMPCDL.IMPCDLCONTRATOREAL = PARCELA.NUMERO_DOC\n"
                + "                    AND IMPCDL.IMPCDLFILIAL = FILIAIS.FILIAL_SGL\n"
                + "                   AND PESSOA.CPF_RAZAO = IMPCDL.IMPCDLCNPJCPF\n"
                + "                   AND IMPCDL.IMPCDLPROVEDOR = 'C'\n"
                + "                  LEFT OUTER JOIN IMPORT_PROVEDOR\n"
                + "                    ON IMPORT_PROVEDOR.CONTRATO = PARCELA.NUMERO_DOC\n"
                + "                   AND TO_NUMBER(PESSOA.CPF_RAZAO) = IMPORT_PROVEDOR.CPF\n"
                + "                   AND LPAD(TRIM(SUBSTR(ASSOCIADO, 2, 4)), 4, '0') = FILIAIS.ID_FILIAL\n"
                + "                   AND IMPORT_PROVEDOR.PROVEDOR = 'C'\n"
                + "                 WHERE EXTRATOR.STATUS_SPC = 'P' AND IMPORT_PROVEDOR.CONTRATO IS NOT NULL  ORDER BY ID_EXTRATOR";

//        sqlSelectImportBvs = "SELECT EXTRATOR.ID_EXTRATOR,"
//                + "       EXTRATOR.ID_PARCELA,\n"
//                + "       EXTRATOR.STATUS_FACMAT,\n"
//                + "       TO_DATE(PARCELA.venc) AS VENCIMENTO_SGL,\n"
//                + "       IMPORT_PROVEDOR.DATA_VENC AS VENC_PROVEDOR,\n"
//                + "       IMPORT_PROVEDOR.VALOR VALOR_PROVEDOR,\n"
//                + "       PARCELA.VALOR AS VALOR_SGL,\n"
//                + "       IMPORT_PROVEDOR.CPF,\n"
//                + "       IMPORT_PROVEDOR.DATA_INCLUSAO_BVS,\n"
//                + "       IMPORT_PROVEDOR.CODIGO_BVS,\n"
//                + "       IMPORT_PROVEDOR.CONTRATO,\n"
//                + "       IMPORT_PROVEDOR.ASSOCIADO,"
//                + "       IMPORT_PROVEDOR.ID_IMPORT\n"
//                + "  FROM PARCELA\n"
//                + " INNER JOIN EXTRATOR\n"
//                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
//                + " INNER JOIN FILIAIS\n"
//                + "    ON PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
//                + " INNER JOIN PESSOA\n"
//                + "    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
//                + " INNER JOIN IMPORT_PROVEDOR\n"
//                + "    ON PARCELA.NUMERO_DOC = IMPORT_PROVEDOR.CONTRATO\n"
//                + "   AND LPAD(TRIM(SUBSTR(ASSOCIADO, 2, 4)), 4, '0') = FILIAIS.ID_FILIAL\n"
//                + "   AND IMPORT_PROVEDOR.CPF = TO_NUMBER(PESSOA.CPF_RAZAO)\n"
//                + " WHERE PROVEDOR = 'B' AND IMPORT_PROVEDOR.CODIGO_BVS IS NOT NULL AND EXTRATOR.STATUS_FACMAT = 'P' ORDER BY EXTRATOR.ID_EXTRATOR";
        sqlSelectImportBvs = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "                      EXTRATOR.ID_PARCELA,\n"
                + "                      EXTRATOR.STATUS_FACMAT,\n"
                + "                      TO_DATE(PARCELA.venc) AS VENCIMENTO_SGL,\n"
                + "                      IMPORT_PROVEDOR.DATA_VENC AS VENC_PROVEDOR,\n"
                + "                      IMPORT_PROVEDOR.VALOR VALOR_PROVEDOR,\n"
                + "                      PARCELA.VALOR AS VALOR_SGL,\n"
                + "                      PARCELA.ID_REGISTRO_BVS,\n"
                + "                      IMPORT_PROVEDOR.CPF,\n"
                + "                      IMPORT_PROVEDOR.DATA_INCLUSAO_BVS,\n"
                + "                      IMPORT_PROVEDOR.CODIGO_BVS,\n"
                + "                      IMPORT_PROVEDOR.CONTRATO,\n"
                + "                      PARCELA.NUMERO_DOC,\n"
                + "                      IMPORT_PROVEDOR.ASSOCIADO,\n"
                + "                      IMPORT_PROVEDOR.ID_IMPORT\n"
                + "                 FROM PARCELA\n"
                + "                INNER JOIN EXTRATOR\n"
                + "                   ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                INNER JOIN FILIAIS\n"
                + "                   ON PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                INNER JOIN PESSOA\n"
                + "                   ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                INNER JOIN IMPORT_PROVEDOR\n"
                + "                   ON PARCELA.NUMERO_DOC =  LPAD(IMPORT_PROVEDOR.CONTRATO, 10, 0)\n"
                + "                  AND LPAD(TRIM(SUBSTR(ASSOCIADO, 2, 4)), 4, '0') = FILIAIS.ID_FILIAL\n"
                + "                  AND IMPORT_PROVEDOR.CPF = TO_NUMBER(PESSOA.CPF_RAZAO)\n"
                + "                  AND IMPORT_PROVEDOR.DATA_VENC = PARCELA.VENC\n"
                + "                WHERE PROVEDOR = 'B' AND IMPORT_PROVEDOR.CODIGO_BVS IS NOT NULL AND EXTRATOR.STATUS_FACMAT = 'H' \n"
                + "                AND PARCELA.ID_REGISTRO_BVS IS NULL ORDER BY EXTRATOR.ID_EXTRATOR";

        sqlSelectimpcdlBvs = "SELECT EXTRATOR.ID_EXTRATOR,\n"
                + "       EXTRATOR.ID_PARCELA,\n"
                + "       EXTRATOR.STATUS_SPC,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.VENC,\n"
                + "       PARCELA.VALOR,"
                + "       IMPORT_PROVEDOR.ID_IMPORT,\n"
                + "       IMPORT_PROVEDOR.CODIGO_BVS,\n"
                + "       IMPORT_PROVEDOR.DATA_INCLUSAO_BVS,\n"
                + "       IMPORT_PROVEDOR.CONTRATO          AS CONTRATO_IMPORT,\n"
                + "       IMPCDL.IMPCDLCONTRATODIGITADO     AS CONTRATO_IMPCDL,\n"
                + "       IMPCDL.IMPCDLDATAINCLUSAO,\n"
                + "       PESSOA.NOME,\n"
                + "       PESSOA.CPF_RAZAO,\n"
                + "       FILIAIS.FILIAL_SGL\n"
                + "  FROM EXTRATOR\n"
                + " INNER JOIN PARCELA\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " INNER JOIN PESSOA\n"
                + "    ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "  LEFT OUTER JOIN IMPCDL\n"
                + "    ON TRIM(IMPCDL.IMPCDLCONTRATOREAL) = PARCELA.NUMERO_DOC\n"
                + "   AND IMPCDL.IMPCDLFILIAL = FILIAIS.FILIAL_SGL\n"
                + "   AND PESSOA.CPF_RAZAO = IMPCDL.IMPCDLCNPJCPF\n"
                + "   AND IMPCDL.IMPCDLPROVEDOR = 'B'\n"
                + "  LEFT OUTER JOIN IMPORT_PROVEDOR\n"
                + "    ON IMPORT_PROVEDOR.CONTRATO = TRIM(IMPCDL.IMPCDLCONTRATODIGITADO)\n"
                + "   AND IMPORT_PROVEDOR.CPF = TO_NUMBER(PESSOA.CPF_RAZAO)\n"
                + "   AND LPAD(TRIM(SUBSTR(ASSOCIADO, 2, 4)), 4, '0') = FILIAIS.ID_FILIAL\n"
                + "   AND IMPORT_PROVEDOR.PROVEDOR = 'B'\n"
                + " WHERE EXTRATOR.STATUS_FACMAT = 'P'\n"
                + "   AND IMPORT_PROVEDOR.CONTRATO IS NOT NULL\n"
                + " ORDER BY ID_EXTRATOR";

        sqlInsertLog = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, ORIGEM, STATUS)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";

        sqlUpdateExtrator = ("UPDATE EXTRATOR SET STATUS_SPC = ?, STATUS_SPC_AVAL = ?, DATA_SPC = ?, DATA_SPC_AVALISTA = ? WHERE ID_EXTRATOR = ?");

        sqlUpdateExtratorBvs = ("UPDATE EXTRATOR SET STATUS_FACMAT = ?, DATA_FACMAT = ? WHERE ID_EXTRATOR = ?");

        sqlUpdateParcela = ("UPDATE PARCELA SET ID_REGISTRO_BVS = ?, DATA_ULT_ATUALIZACAO = ? WHERE ID_PARCELA = ?");

        sqlUpdateImportProvedor = ("UPDATE IMPORT_PROVEDOR SET STATUS = 'A' WHERE ID_IMPORT = ?");

    }

    public boolean atualizaSpcImport() throws ErroSistemaException {
        System.out.println("Import import");
        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psUpdateExtrator = connection.prepareStatement(sqlUpdateExtrator);
            psSelectImport = Conexao.getConnection().prepareStatement(sqlSelectImport);
            psInsertLog = connection.prepareStatement(sqlInsertLog);
            psImportProvedor = connection.prepareStatement(sqlUpdateImportProvedor);
            rsSelectImport = psSelectImport.executeQuery();
            while (rsSelectImport.next()) {
                try {

                    psUpdateExtrator.setString(1, "H");
                    psUpdateExtrator.setString(2, "H");
                    psUpdateExtrator.setDate(3, rsSelectImport.getDate("DATA_INCLUSAO_SPC"));
                    psUpdateExtrator.setDate(4, rsSelectImport.getDate("DATA_INCLUSAO_SPC"));
                    psUpdateExtrator.setInt(5, rsSelectImport.getInt("ID_EXTRATOR"));
                    psUpdateExtrator.execute();

                    psInsertLog.setInt(1, rsSelectImport.getInt("ID_EXTRATOR"));
                    psInsertLog.setInt(2, 200);
                    psInsertLog.setDate(3, Utilitarios.converteData(new Date()));
                    psInsertLog.setString(4, "ARQUIVO IMPORTADO SPC");
                    psInsertLog.setString(5, "I");
                    psInsertLog.setString(6, "S");
                    psInsertLog.execute();

                    psImportProvedor.setInt(1, rsSelectImport.getInt("ID_IMPORT"));
                    psImportProvedor.execute();
                    connection.commit();
                } catch (SQLException ex) {

                    ex.printStackTrace();

                }
            }

            psUpdateExtrator.close();
            psSelectImport.close();
            psInsertLog.close();
            rsSelectImport.close();
            psImportProvedor.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();

            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        }

        return false;

    }

    public boolean atualizaSpcImpcdl() {
        System.out.println("Import impcdl 365 == 730");

        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psSelectImpcdl = Conexao.getConnection().prepareStatement(sqlSelectImpcdl);
            psUpdateExtrator = connection.prepareStatement(sqlUpdateExtrator);
            psInsertLog = connection.prepareStatement(sqlInsertLog);
            psImportProvedor = connection.prepareStatement(sqlUpdateImportProvedor);
            rsSelectImpcdl = psSelectImpcdl.executeQuery();
            int cont = 0;
            while (rsSelectImpcdl.next()) {
                try {
                    System.out.println("Num " + cont++);
                    psUpdateExtrator.setString(1, "H");
                    psUpdateExtrator.setString(2, "H");
                    psUpdateExtrator.setDate(3, rsSelectImpcdl.getDate("IMPCDLDATAINCLUSAO"));
                    psUpdateExtrator.setDate(4, rsSelectImpcdl.getDate("IMPCDLDATAINCLUSAO"));
                    psUpdateExtrator.setInt(5, rsSelectImpcdl.getInt("ID_EXTRATOR"));
                    psUpdateExtrator.execute();

                    psInsertLog.setInt(1, rsSelectImpcdl.getInt("ID_EXTRATOR"));
                    psInsertLog.setInt(2, 200);
                    psInsertLog.setDate(3, Utilitarios.converteData(new Date()));
                    psInsertLog.setString(4, "ARQUIVO IMPORTADO SPC");
                    psInsertLog.setString(5, "I");
                    psInsertLog.setString(6, "S");
                    psInsertLog.execute();

                    psImportProvedor.setInt(1, rsSelectImpcdl.getInt("ID_IMPORT"));
                    psImportProvedor.execute();

                    connection.commit();

                } catch (SQLException ex) {
                    System.out.println("Erro registro " + "Id_extrator: " + rsSelectImpcdl.getInt("ID_EXTRATOR") + "  Contrato: "
                            + rsSelectImpcdl.getString("NUMERO_DOC") + "  Cpf: " + rsSelectImpcdl.getString("CPF_RAZAO") + "  Nome: " + rsSelectImpcdl.getString("NOME")
                            + " Valor: " + rsSelectImpcdl.getString("VALOR"));
                    ex.printStackTrace();
                    try {
                        throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                    } catch (ErroSistemaException ex1) {
                        Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            psInsertLog.close();
            psSelectImpcdl.close();
            psUpdateExtrator.close();
            rsSelectImpcdl.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (ErroSistemaException ex1) {
                Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (ErroSistemaException ex1) {
                Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return false;

    }

    public boolean atualizaBvsImport() throws ErroSistemaException {
        System.out.println("Import import");
        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psUpdateExtrator = connection.prepareStatement(sqlUpdateExtratorBvs);
            psSelectImport = Conexao.getConnection().prepareStatement(sqlSelectImportBvs);
            psInsertLog = connection.prepareStatement(sqlInsertLog);
            psUpdateParcela = connection.prepareStatement(sqlUpdateParcela);
            psImportProvedor = connection.prepareStatement(sqlUpdateImportProvedor);
            rsSelectImport = psSelectImport.executeQuery();
            int cont = 0;
            while (rsSelectImport.next()) {
                try {
                    System.out.println("Num " + cont++);
                    psUpdateExtrator.setString(1, "S");
                    psUpdateExtrator.setDate(2, rsSelectImport.getDate("DATA_INCLUSAO_BVS"));
                    psUpdateExtrator.setInt(3, rsSelectImport.getInt("ID_EXTRATOR"));
                    psUpdateExtrator.execute();

                    psInsertLog.setInt(1, rsSelectImport.getInt("ID_EXTRATOR"));
                    psInsertLog.setInt(2, 200);
                    psInsertLog.setDate(3, Utilitarios.converteData(new Date()));
                    psInsertLog.setString(4, "ARQUIVO IMPORTADO FACMAT");
                    psInsertLog.setString(5, "I");
                    psInsertLog.setString(6, "S");
                    psInsertLog.execute();

                    psUpdateParcela.setString(1, rsSelectImport.getString("CODIGO_BVS"));
                    psUpdateParcela.setDate(2, Utilitarios.converteData(new Date()));
                    psUpdateParcela.setString(3, rsSelectImport.getString("ID_PARCELA"));
                    psUpdateParcela.execute();

                    psImportProvedor.setInt(1, rsSelectImport.getInt("ID_IMPORT"));
                    psImportProvedor.execute();
                    connection.commit();

                } catch (SQLException ex) {
                    connection.rollback();
                    ex.printStackTrace();

                }
            }
            psUpdateParcela.close();
            psUpdateExtrator.close();
            psSelectImport.close();
            psInsertLog.close();
            rsSelectImport.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();

            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        }

        return false;

    }

    public boolean atualizaBvsImpcdl() {
        System.out.println("Import impcdl 365 == 730");

        try {
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psSelectImpcdl = Conexao.getConnection().prepareStatement(sqlSelectimpcdlBvs);
            psUpdateExtrator = connection.prepareStatement(sqlUpdateExtratorBvs);
            psInsertLog = connection.prepareStatement(sqlInsertLog);
            psUpdateParcela = connection.prepareStatement(sqlUpdateParcela);
            rsSelectImpcdl = psSelectImpcdl.executeQuery();
            int cont = 0;
            while (rsSelectImpcdl.next()) {
                try {
                    System.out.println("Num " + cont++);
                    psUpdateExtrator.setString(1, "H");
                    psUpdateExtrator.setDate(2, rsSelectImpcdl.getDate("DATA_INCLUSAO_BVS"));
                    psUpdateExtrator.setInt(3, rsSelectImpcdl.getInt("ID_EXTRATOR"));
                    psUpdateExtrator.execute();

                    psInsertLog.setInt(1, rsSelectImpcdl.getInt("ID_EXTRATOR"));
                    psInsertLog.setInt(2, 201);
                    psInsertLog.setDate(3, Utilitarios.converteData(new Date()));
                    psInsertLog.setString(4, "ARQUIVO IMPORTADO FACMAT");
                    psInsertLog.setString(5, "I");
                    psInsertLog.setString(6, "S");
                    psInsertLog.execute();

                    psUpdateParcela.setString(1, rsSelectImpcdl.getString("CODIGO_BVS"));
                    psUpdateParcela.setDate(2, Utilitarios.converteData(new Date()));
                    psUpdateParcela.setString(3, rsSelectImpcdl.getString("ID_PARCELA"));
                    psUpdateParcela.execute();

                    connection.commit();

                } catch (SQLException ex) {
                    System.out.println("Erro registro " + "Id_extrator: " + rsSelectImpcdl.getInt("ID_EXTRATOR") + "  Contrato: "
                            + rsSelectImpcdl.getString("NUMERO_DOC") + "  Cpf: " + rsSelectImpcdl.getString("CPF_RAZAO") + "  Nome: " + rsSelectImpcdl.getString("NOME")
                            + " Valor: " + rsSelectImpcdl.getString("VALOR"));
                    ex.printStackTrace();
                    try {
                        throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                    } catch (ErroSistemaException ex1) {
                        Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
            psUpdateParcela.close();
            psInsertLog.close();
            psSelectImpcdl.close();
            psUpdateExtrator.close();
            rsSelectImpcdl.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (ErroSistemaException ex1) {
                Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (ErroSistemaException ex1) {
                Logger.getLogger(SincronizaSpcDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return false;

    }

}
