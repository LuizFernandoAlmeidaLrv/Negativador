/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import br.com.martinello.util.excessoes.ErroSistemaException;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author luiz.almeida
 */
public class ImportContasReceberCorrecao {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    public boolean retornoExt;
    private PreparedStatement psExtracao, psAvalista, psAvalistaUpdate, psParcela, psPessoa, psExtrator, psProcesso, psAtualizarRegistroExtrator, psCliente,
            psClienteUpdate, psInserirLogParcela, psVerificaParcela, psVerificaParcelaBaixa, psParcelaUpdate, psParcelaBaixaUpdate, psParcelasSemRetorno,
            psConsultaParcelaInclusao, psLogExtracao, psParcelasRetornada, psBuscarParcelasManual, psParcelaManual, psExtracaoManual, psVerificaParcelaOriginal,
            psConsultaTipoParcelaExtrator, psFinalizaInclusaoPendente, psSelectParcelasErro;
    private ResultSet rsExtracao, rsCliente, rsAvalista, rsParcelas, rsProExtrator, rsVerificaParcela, rsVerificaParcelaBaixa, rsParcelasSemRetorno,
            rsParcelasRetornada, rsBuscarParcelasManual, rsParcelaManual, rsVerificaParcelaOriginal, rsConsultaTipoParcelaExtrator, rsConsultaParcelaInclusao, rsSelectParcelasErro;
    public List<ParcelaModel> lparcelasModel, lparcelasSalvarModel, lretornarParcelas, lretornarAvalista, lbuscarParcelasManual;
    public List<ExtracaoModel> lextracaoModel;
    public int idCliente, idAvalista, idParcela, idExtrator, resultado, opcao;
    public String sqlVerificaExtracao, sqlInsertCliente, sqlInsertAval, sqlInsertParcelas, sqlInsertExtrator, sqlInsertLog, sqlUpdateParcelasErro,
            sqlUpdateParcelas, sqlUpdatePessoas, sqlUpdateExtrator, sqlInsertLogExtracao, sqlSelectManual, sqlSelectFiliais, sqlSelectParcelaBaixaOrigem, sqlUpdateRetornoBaixa;

    public String logExtracao, statusLogExtracao;
    public int contErro = 0;
    public int contregistros = 0;
    private static String filial;
    private int avalista;
   

    public String getSqlSelectParcelas() {

        String sqlSelectParcelas = "SELECT PESSOA.ID_PESSOA,\n"
                + "                       PESSOA.NOME,\n"
                + "                       PESSOA.TIPO_PESSOA,\n"
                + "                       PESSOA.CPF_RAZAO,\n"
                + "                       PESSOA.CPF_ORIGEM,\n"
                + "                       PESSOA.NOME_PAI,\n"
                + "                       PESSOA.NOME_MAE,\n"
                + "                       PESSOA.NUM_RG,\n"
                + "                       PESSOA.DATE_EXPED,\n"
                + "                       PESSOA.ORGAO_EXPED,\n"
                + "                       PESSOA.EMAIL,\n"
                + "                       PESSOA.TELEFONE,\n"
                + "                       PESSOA.DATE_NASC,\n"
                + "                       PESSOA.EST_CIVIL,\n"
                + "                       PESSOA.ID_ESTCIVIL,\n"
                + "                       PESSOA.ENDERECO,\n"
                + "                       PESSOA.ID_LOGRADOURO,\n"
                + "                       PESSOA.NUMERO,\n"
                + "                       PESSOA.COMPLEMENTO,\n"
                + "                       PESSOA.BAIRRO,\n"
                + "                       PESSOA.CIDADE,\n"
                + "                       PESSOA.CODIGO_IBGE,\n"
                + "                       PESSOA.CEP,\n"
                + "                       PESSOA.UF_ESTADO,\n"
                + "                       PESSOA.TIPO_DEVEDOR,\n"
                + "                       PESSOA.SITUACAO,\n"
                + "                       PESSOA.ID_FILIAL,\n"
                + "                       PARCELA.ID_PARCELA,\n"
                + "                       PARCELA.ID_FILIAL,\n"
                + "                       PARCELA.CODIGO,\n"
                + "                       PARCELA.NUMERO_DOC,\n"
                + "                       PARCELA.DATALAN,\n"
                + "                       PARCELA.VENC,\n"
                + "                       PARCELA.VALOR,\n"
                + "                       PARCELA.DATAPAG,\n"
                + "                       PARCELA.CAPITPAG,\n"
                + "                       PARCELA.SIT,\n"
                + "                       PARCELA.DATA_NEGATIVADA,\n"
                + "                       PARCELA.DATA_BAIXA,\n"
                + "                       PARCELA.TAXA,\n"
                + "                       PARCELA.JUROS,\n"
                + "                       PARCELA.VALOR_CALC,\n"
                + "                       PARCELA.VALOR_PAG,\n"
                + "                       PARCELA.JUROS_PAG,\n"
                + "                       PARCELA.DATAALT,\n"
                + "                       PARCELA.NUMPAR,\n"
                + "                       PARCELA.TIPOPAG,\n"
                + "                       PARCELA.DATA_EXTRACAO,\n"
                + "                       PARCELA.ID_CLIENTE,\n"
                + "                       PARCELA.ID_AVALISTA,\n"
                + "                       EXTRATOR.ID_EXTRATOR,\n"
                + "                       EXTRATOR.TIPO,\n"
                + "                       EXTRATOR.STATUS,\n"
                + "                       EXTRATOR.STATUS_SPC,\n"
                + "                       EXTRATOR.STATUS_SPC_AVAL,\n"
                + "                       EXTRATOR.STATUS_FACMAT,\n"
                + "                       EXTRATOR.DATA_SPC,\n"
                + "                       EXTRATOR.ID_PARCELA,\n"
                + "                       EXTRATOR.DATA_SPC_AVALISTA,\n"
                + "                       EXTRATOR.DATA_FACMAT,\n"
                + "                       EXTRATOR.ORIGEM,\n"
                + "                       EXTRATOR.RETORNO,\n"
                + "                       RETORNOS_INTEGRACAO.ID_RETORNO,\n"
                + "                       RETORNOS_INTEGRACAO.CODIGO,\n"
                + "                       RETORNOS_INTEGRACAO.DESCRICAO\n"
                + "                  FROM PARCELA\n"
                + "                INNER JOIN PESSOA ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                INNER JOIN EXTRATOR ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "                INNER JOIN RETORNOS_INTEGRACAO ON RETORNOS_INTEGRACAO.ID_RETORNO = EXTRATOR.RETORNO \n";

        return sqlSelectParcelas;
    }

    public String getSqlSelectExtracao() {
        String sqlSelectExtracao = "SELECT MR02.PTO,\n"
                + "       MR02.DOC,\n" 
                + "       MR02.ORIGEM,\n"
                + "       MR02.CODIGO,\n"
                + "       MR02.CLI,\n"
                + "       CLIENTE.RAZ,\n"
                + "       CLIENTE.CGC,\n"
                + "       CLIENTE.TIPO,\n"
                + "       CLIENTE.SIT AS SIT_CLIENTE,\n"
                + "       CLIENTE.INS,\n"
                + "       CLIENTE.EXPEDIDOR,\n"
                + "       CLIENTE.DTEXPED,\n"
                + "       CLIENTE.EMAIL,\n"
                + "       CLIENTE.FON,\n"
                + "       CLIENTE.DTNASC,\n"
                + "       CLIENTE.FILPAI,\n"
                + "       CLIENTE.FILMAE,\n"
                + "       CLIENTE.ESTCIVIL,\n"
                + "       CLIENTE.END,\n"
                + "       CLIENTE.NUM,\n"
                + "       CLIENTE.BAI,\n"
                + "       CLIENTE.COMPL,\n"
                + "       CLIENTE.NEG,\n"
                + "       CLIENTE.CID,\n"
                + "       CLIENTE.CODIBGE,\n"
                + "       CLIENTE.EST,\n"
                + "       CLIENTE.CEP,\n"
                + "       MR02.NUMPAR,\n"
                + "       MR02.DATALAN,\n"
                + "       MR02.VENC,\n"
                + "       MR02.VALOR,\n"
                + "       MR02.DATAPAG,\n"
                + "       MR02.DATAALT,\n"
                + "       MR02.DATA_MOV_RETORNAR,\n"
                + "       MR02.TAXA,\n"
                + "       MR02.JUROS,\n"
                + "       MR02.VALORCALC,\n"
                + "       MR02.VALORPAG,\n"
                + "       MR02.CAPITPAG,\n"
                + "       MR02.JUROSPAG,\n"
                + "       MR02.SIT AS SIT_MR02,\n"
                + "       MR02.TPGTO,\n"
                + "       MR02.NEGATIVADA,\n"
                + "       MR02.BAIXANEG,\n"
                + "       MR02.AVALISTA,\n"
                + "       MR02.RETORNADO,\n"
                + "       AVALISTA.RAZ AS NOMEAVALISTA,\n"
                + "       AVALISTA.CGC AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPO AS TIPO_AVALISTA,\n"
                + "       AVALISTA.INS AS RG_AVALISTA,\n"
                + "       AVALISTA.EXPEDIDOR AS EXPEDITOR_AVAL,\n"
                + "       AVALISTA.DTEXPED AS DTEXPED_AVAL,\n"
                + "       AVALISTA.EMAIL AS EMAIL_AVAL,\n"
                + "       AVALISTA.FON AS FON_AVAL,\n"
                + "       AVALISTA.DTNASC AS DTNASC_AVAL,\n"
                + "       AVALISTA.FILPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA.FILMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA.ESTCIVIL AS ESTCIVIL_AVAL,\n"
                + "       AVALISTA.END AS END_AVAL,\n"
                + "       AVALISTA.NUM AS NUM_AVAL,\n"
                + "       AVALISTA.BAI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.COMPL AS COMPL_AVAL,\n"
                + "       AVALISTA.CID AS CIDADE_AVAL,\n"
                + "       AVALISTA.CODIBGE AS CODIBGE_AVAL,\n"
                + "       AVALISTA.EST AS EST_AVAL,\n"
                + "       AVALISTA.CEP AS CEP_AVAL,\n"
                + "       AVALISTA.NEG AS NEG_AVAL,\n"
                + "       CASE\n"
                + "         WHEN UPPER(CLIENTE.ESTCIVIL) = 'S' THEN\n"
                + "          '1'\n"
                + "         WHEN UPPER(CLIENTE.ESTCIVIL) = 'C' THEN\n"
                + "          '2'\n"
                + "         WHEN UPPER(CLIENTE.ESTCIVIL) = 'D' THEN\n"
                + "          '3'\n"
                + "         WHEN UPPER(CLIENTE.ESTCIVIL) = 'V' THEN\n"
                + "          '4'\n"
                + "         ELSE\n"
                + "          '5'\n"
                + "       END ID_ESTCIVILCLI,\n"
                + "       CASE\n"
                + "         WHEN UPPER(AVALISTA.ESTCIVIL) = 'S' THEN\n"
                + "          '1'\n"
                + "         WHEN UPPER(AVALISTA.ESTCIVIL) = 'C' THEN\n"
                + "          '2'\n"
                + "         WHEN UPPER(AVALISTA.ESTCIVIL) = 'D' THEN\n"
                + "          '3'\n"
                + "         WHEN UPPER(AVALISTA.ESTCIVIL) = 'V' THEN\n"
                + "          '4'\n"
                + "         ELSE\n"
                + "          '5'\n"
                + "       END ID_ESTCIVILAVAL,\n"
                + "       CASE\n"
                + "         WHEN MR02.DATAPAG = '31/12/1900' THEN\n"
                + "          'I'\n"
                + "         WHEN MR02.DATAPAG <> '31/12/1900' THEN\n"
                + "          'B'\n"
                + "       END TIPO_ACAO\n"
                + "  FROM DB_INTEGRACAO.MR02\n"
                + " INNER JOIN DB_INTEGRACAO.MR01 CLIENTE\n"
                + "    ON CLIENTE.PTO = MR02.PTO\n"
                + "   AND CLIENTE.CLI = MR02.CLI\n"
                + "  LEFT OUTER JOIN DB_INTEGRACAO.MR01 AVALISTA\n"
                + "    ON AVALISTA.PTO = MR02.PTO\n"
                + "   AND TRIM(AVALISTA.CLI) = TRIM(MR02.AVALISTA)\n"
                + "  LEFT OUTER JOIN FILIAIS\n"
                + "    ON FILIAIS.FILIAL_SGL = MR02.PTO\n";
        return sqlSelectExtracao;
    }

    public String getSqlWhereParcelas() {
        String sqlWhereParcelas = " WHERE PARCELA.ID_CLIENTE = PESSOA.ID_PESSOA\n"
                + "   AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "    AND PARCELA.ID_FILIAL = ?\n"
                + "  AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "  AND PARCELA.CLIENTE = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n"
                + "  AND EXTRATOR.STATUS = ?";
        return sqlWhereParcelas;
    }

    public String getSqlWhereAtomatico() {
        String sqlWhereAltomatico = "\n"
                + " WHERE ((EXISTS (SELECT 1\n"
                + "                  FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                 WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                   AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                   AND PARCELA.NUMERO_DOC = MR02.DOC\n"
                + "                   AND FILIAIS.FILIAL_SGL = MR02.PTO \n"
                + "                   AND EXTRATOR.STATUS IN ('EP','I')) OR NEGATIVADA = '31/12/1900') AND MR02.SIT = 'N' \n"
                + "   AND MR02.VENC BETWEEN SYSDATE - 45 AND SYSDATE - 30 \n"
                + "   AND CLIENTE.SIT NOT IN ('08', '09', '12', '13') \n"
                + "   AND MR02.VALOR >= '25,00')\n"
                + "    OR (MR02.SIT = 'P' AND MR02.DATAALT BETWEEN SYSDATE - 5 AND SYSDATE AND MR02.TPGTO IN ('R', 'N') AND MR02.BAIXANEG = '31/12/1900')\n"
                + "    OR (MR02.TPGTO <> 'T' AND MR02.DATAPAG BETWEEN SYSDATE - 5 AND SYSDATE AND MR02.NEGATIVADA <> '31/12/1900' AND MR02.BAIXANEG = '31/12/1900')";
        return sqlWhereAltomatico;
    }

    public String getSqlWhereAutomaticoUnica() {
//        String sqlWhereAutomaticoUnica = " WHERE (PARCELA.SIT = 'N' AND PARCELA.VENC BETWEEN SYSDATE - 45 AND\n"
//                + "       SYSDATE - 30 AND PARCELA.NEGATIVADA = '31/12/1900' AND\n"
//                + "       CLIENTE.SIT NOT IN ('08', '09', '12', '13') AND\n"
//                + "       PARCELA.VALOR >= '25,00' AND TRIM(PARCELA.ORIGEM) IS NULL AND PARCELA.PTO = ?)\n"
//                + "    OR (PARCELA.SIT = 'N' AND PARCELA.VENC BETWEEN SYSDATE - 45 AND\n"
//                + "       SYSDATE - 30 AND PARCELA.NEGATIVADA = '31/12/1900' AND\n"
//                + "       CLIENTE.SIT NOT IN ('08', '09', '12', '13') AND\n"
//                + "       PARCELA.VALOR >= '25,00'AND PARCELA_ORIGEM.NEGATIVADA = '31/12/1900' AND PARCELA.PTO = ?)\n"
//                + "    OR (PARCELA.TPGTO <> 'T' AND PARCELA.DATAPAG BETWEEN SYSDATE - 5 AND\n"
//                + "       SYSDATE AND PARCELA.NEGATIVADA <> '31/12/1900' AND\n"
//                + "       PARCELA.BAIXANEG = '31/12/1900' AND PARCELA.PTO = ?)\n"
//                + "    OR (PARCELA.SIT = 'P' AND PARCELA.DATAALT BETWEEN SYSDATE - 5 AND\n"
//                + "       SYSDATE AND PARCELA.TPGTO IN ('R', 'N') AND\n"
//                + "       PARCELA.BAIXANEG = '31/12/1900' AND\n"
//                + "       PARCELA_ORIGEM.NEGATIVADA <> '31/12/1900' AND\n"
//                + "       PARCELA_ORIGEM.VENC >= SYSDATE - 1825 AND\n"
//                + "       PARCELA_ORIGEM.BAIXANEG = '31/12/1900' AND PARCELA.PTO = ?)";

        String sqlWhereAutomaticoUnica = "  WHERE (((EXISTS (SELECT 1\n"
                + "                                  FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                                 WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                                   AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                                   AND PARCELA.NUMERO_DOC = MR02.DOC\n"
                + "                                   AND FILIAIS.FILIAL_SGL = MR02.PTO \n"
                + "                                   AND EXTRATOR.STATUS IN ('EP','I')) OR NEGATIVADA = '31/12/1900') AND MR02.SIT = 'N' \n"
                + "                   AND MR02.VENC BETWEEN SYSDATE - 45 AND SYSDATE - 30 \n"
                + "                   AND CLIENTE.SIT NOT IN ('08', '09', '12', '13') \n"
                + "                   AND MR02.VALOR >= '25,00')\n"
                + "                    OR (MR02.SIT = 'P' AND MR02.DATAALT BETWEEN SYSDATE - 5 AND SYSDATE AND MR02.TPGTO IN ('R', 'N') AND MR02.BAIXANEG = '31/12/1900')\n"
                + "                    OR (MR02.TPGTO <> 'T' AND MR02.DATAPAG BETWEEN SYSDATE - 5 AND SYSDATE AND MR02.NEGATIVADA <> '31/12/1900' AND MR02.BAIXANEG = '31/12/1900'))\n"
                + "                    AND MR02.PTO = ?";
        return sqlWhereAutomaticoUnica;
    }

    public String getSqlWhereVerificaParcelas() {
        String sqlWhereVerificaParcelas = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND PARCELA.CLIENTE = ?\n";
        return sqlWhereVerificaParcelas;
    }

    public String getSqlWhereConsultaParcelaExtrator() {
        String sqlWhereVerificaParcelas = " WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND PARCELA.CLIENTE = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n";
        return sqlWhereVerificaParcelas;
    }

    public String getSqlWhereParcelaSemRetorno() {
        String sqlWhereParcelaSemRetorno = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND PARCELA.CLIENTE = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n"
                + "   AND EXTRATOR.STATUS = ?\n"
                + "   AND EXTRATOR.DATA_RETORNO = '31/12/1900'";

        return sqlWhereParcelaSemRetorno;
    }

    public String getSqlWhereParcelaRetornada() {
        String sqlWhereParcelaRetornada = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND PARCELA.CLIENTE = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n"
                + "   AND EXTRATOR.STATUS = ?\n"
                + "   AND EXTRATOR.DATA_RETORNO <> '31/12/1900'";
        return sqlWhereParcelaRetornada;
    }

    public String getSqlWhereManualExtracao() {
        String sqlWhereManualExtracao = " WHERE MR02.PTO = ?\n"
                + "   AND MR02.CLI = ?\n"
                + "   AND MR02.DOC = ?"
                + " AND MR02.CODIGO = ?";

        return sqlWhereManualExtracao;
    }

    public String getSqlSelectParcelasErro() {
        String sqlSelectParcelasErro = "SELECT * \n"
                + "  FROM PARCELA, PESSOA, FILIAIS, EXTRATOR\n"
                + " WHERE PARCELA.ID_CLIENTE = PESSOA.ID_PESSOA\n"
                + "   AND PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "   AND EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "   AND NOT EXISTS (SELECT 1\n"
                + "          FROM DB_INTEGRACAO.MR02,DB_INTEGRACAO.MR01\n"
                + "         WHERE MR02.PTO = MR01.PTO \n"
                + "           AND MR02.CLI = MR01.CLI           \n"
                + "           AND MR02.PTO = FILIAIS.FILIAL_SGL\n"
                + "           AND MR02.DOC = PARCELA.NUMERO_DOC\n"
                + "           AND PESSOA.CPF_RAZAO = MR01.CGC) \n"
                + "   AND (EXTRATOR.TIPO = 'I')";

        return sqlSelectParcelasErro;
    }

    public String getOrderByParcelaExtrator() {

        return " ORDER BY ID_EXTRATOR DESC ";
    }

    public ImportContasReceberCorrecao() {

        sqlUpdateParcelasErro = ("UPDATE PARCELA SET ID_CLIENTE = ?, ID_AVALISTA = ? WHERE ID_PARCELA = ?");

        sqlUpdateParcelas = ("UPDATE PARCELA SET DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?, JUROS_PAG = ?, DATA_NEGATIVADA = ?,"
                + " DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, TIPOPAG = ?"
                + " WHERE ID_PARCELA = ?");

        sqlUpdatePessoas = ("UPDATE PESSOA SET NOME = ?, TIPO_PESSOA = ?, CPF_RAZAO = ?, CPF_ORIGEM = ?, NOME_PAI = ?, NOME_MAE = ?,"
                + " NUM_RG = ?, DATE_EXPED = ?, ORGAO_EXPED = ?, EMAIL = ?, TELEFONE = ?, DATE_NASC = ?, EST_CIVIL = ?, ID_ESTCIVIL = ?, ENDERECO = ?, ID_LOGRADOURO = ?,"
                + " NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, CODIGO_IBGE = ?, CEP = ?, UF_ESTADO = ?, TIPO_DEVEDOR = ?, SITUACAO = ?, ID_FILIAL = ? WHERE ID_PESSOA = ?");

        sqlUpdateExtrator = ("UPDATE EXTRATOR SET STATUS = ? WHERE ID_EXTRATOR = ?");

        sqlVerificaExtracao = "SELECT *FROM EXTRATOR, PARCELAS WHERE EXTRATOR.ID_PARCELAS = PARCELAS.ID_PARCELA  AND CODIGO = '12345'  AND TIPO = 'I' AND STATUS <> 'F'";

        sqlInsertLogExtracao = "INSERT INTO LOG_EXTRACAO (ID_LOG_EXTRACAO, ID_EXTRATOR, NUM_DOC, CLIENTE, PONTO, MENSAGEM, DATA_EXTRACAO, STATUS, ORIGEM)"
                + "VALUES (ID_LOG_EXTRACAO.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertCliente = "INSERT INTO PESSOA (ID_PESSOA, NOME, TIPO_PESSOA, CPF_RAZAO, CPF_ORIGEM, NOME_PAI, NOME_MAE,"
                + " NUM_RG, DATE_EXPED, ORGAO_EXPED, EMAIL, TELEFONE, DATE_NASC, EST_CIVIL, ID_ESTCIVIL, ENDERECO, ID_LOGRADOURO,"
                + " NUMERO, COMPLEMENTO, BAIRRO, CIDADE, CODIGO_IBGE, CEP, UF_ESTADO, TIPO_DEVEDOR, SITUACAO, ID_FILIAL, SEXO)"
                + " VALUES (ID_LOG_PESSOA.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertParcelas = "INSERT INTO PARCELA (ID_PARCELA, ID_FILIAL, CODIGO, NUMERO_DOC, DATALAN, VENC, VALOR, DATAPAG,"
                + " CAPITPAG, SIT, DATA_NEGATIVADA, DATA_BAIXA, TAXA, JUROS, VALOR_CALC, VALOR_PAG, JUROS_PAG, DATAALT,"
                + " NUMPAR, TIPOPAG, DATA_EXTRACAO, ID_CLIENTE, ID_AVALISTA, CLIENTE, AVALISTA, PRESCRITO, INCLUIR_AVAL, ORIGEM_PROVEDOR)"
                + " VALUES (ID_LOG_PARCELA.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertLog = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, ORIGEM, STATUS)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";

        sqlInsertExtrator = "INSERT INTO EXTRATOR(ID_EXTRATOR, TIPO, STATUS, DATA_SPC, ID_PARCELA, DATA_SPC_AVALISTA, DATA_FACMAT, ORIGEM, RETORNO, DATA_RETORNO, DATA_EXTRACAO,"
                + " ORIGEM_DB, ID_NATUREZA_INC_BVS, ID_NATUREZA_INC_SPC, ID_MOTIVO_EXCLUSAO_SPC, ID_MOTIVO_EXCLUSAO_BVS, ID_MOTIVO_INC_BVS, BLOQUEAR_REGISTRO, STATUS_SPC, STATUS_FACMAT, STATUS_SPC_AVAL)"
                + " VALUES (ID_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    }

    public void realizarExtracao() throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            // select da extração, busca os dados.
            /**
             * Instancia todos os PrepareStatement fora do laço While
             *
             */
            psProcesso = connection.prepareStatement((sqlInsertExtrator), new String[]{"ID_EXTRATOR"});
            psInserirLogParcela = connection.prepareStatement(sqlInsertLog);
            psParcela = connection.prepareStatement((sqlInsertParcelas), new String[]{"ID_PARCELA"});
            psPessoa = connection.prepareStatement((sqlInsertCliente), new String[]{"ID_PESSOA"});
            psAvalista = connection.prepareStatement((sqlInsertCliente), new String[]{"ID_PESSOA"});
            psAvalistaUpdate = connection.prepareStatement(sqlUpdatePessoas);
            psClienteUpdate = connection.prepareStatement(sqlUpdatePessoas);
            psAtualizarRegistroExtrator = connection.prepareStatement(sqlUpdateExtrator);
            psParcelaUpdate = connection.prepareStatement(sqlUpdateParcelasErro);
            psParcelaBaixaUpdate = connection.prepareStatement(sqlUpdateParcelas);
            psVerificaParcelaBaixa = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelas());
            psParcelasSemRetorno = connection.prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelaSemRetorno());
            psLogExtracao = connection.prepareStatement(sqlInsertLogExtracao);
            psParcelasRetornada = connection.prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelaRetornada());

            /**
             * Percorre os dados da extração, atualiza se existir, insere se for novo.
             */
            psSelectParcelasErro = Conexao.getConnection().prepareStatement(getSqlSelectParcelasErro());
            rsSelectParcelasErro = psSelectParcelasErro.executeQuery();
            while (rsSelectParcelasErro.next()) {
                psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + getSqlWhereManualExtracao());
                psExtracao.setString(1, rsSelectParcelasErro.getString("FILIAL_SGL"));
                psExtracao.setString(2, rsSelectParcelasErro.getString("CLIENTE"));
                psExtracao.setString(3, rsSelectParcelasErro.getString("NUMERO_DOC"));
                psExtracao.setString(4, rsSelectParcelasErro.getString("CODIGO"));

                rsExtracao = psExtracao.executeQuery();
                if (rsExtracao.next()) {
                    idAvalista = 0;
                    idCliente = 0;
                    idExtrator = 0;
                    System.out.println(rsSelectParcelasErro.getString("FILIAL_SGL"));
                    System.out.println(rsSelectParcelasErro.getString("CLIENTE"));
                    System.out.println(rsSelectParcelasErro.getString("NUMERO_DOC"));
                    System.out.println(rsSelectParcelasErro.getString("CODIGO"));

                    try {
                        if (rsExtracao.getString("AVALISTA").trim().length() > 0) {
                            idAvalista = rsSelectParcelasErro.getInt("ID_AVALISTA");
                        }
                        idCliente = rsSelectParcelasErro.getInt("ID_CLIENTE");

                        updateRegistro(rsSelectParcelasErro.getInt("ID_PARCELA"), idCliente, idAvalista);

                        connection.commit();

                    } catch (SQLException e) {
                        connection.rollback();
                        resultado = psLogExtracao.executeUpdate();
                        if (resultado == -1) {
                            retornoExt = false;
                        } else {
                            retornoExt = true;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        connection.rollback();
                        retornoExt = false;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                psProcesso.close();
                psInserirLogParcela.close();
                psParcela.close();
                psPessoa.close();
                psAvalista.close();
                psAvalistaUpdate.close();
                psClienteUpdate.close();
                psAtualizarRegistroExtrator.close();
                psParcelaUpdate.close();
                psParcelaBaixaUpdate.close();
                psVerificaParcelaBaixa.close();
                psParcelasSemRetorno.close();
                psLogExtracao.close();
                psParcelasRetornada.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex);
            }
        }
    }

    public void updateRegistro(int idParcelaBanco, int idClienteUpdate, int idAvalistaUpdate) throws ErroSistemaException {
        try {

            psClienteUpdate.setString(1, rsExtracao.getString("RAZ"));
            psClienteUpdate.setString(2, rsExtracao.getString("TIPO"));
            psClienteUpdate.setString(3, rsExtracao.getString("CGC"));
            if (rsExtracao.getString("CGC").length() < 14) {
                psClienteUpdate.setString(4, rsExtracao.getString("CGC").substring(8, 9));
            } else {
                psClienteUpdate.setString(4, " ");
            }
            psClienteUpdate.setString(5, rsExtracao.getString("FILPAI"));
            psClienteUpdate.setString(6, rsExtracao.getString("FILMAE"));
            psClienteUpdate.setString(7, rsExtracao.getString("INS"));
            psClienteUpdate.setDate(8, rsExtracao.getDate("DTEXPED"));
            psClienteUpdate.setString(9, rsExtracao.getString("EXPEDIDOR"));
            psClienteUpdate.setString(10, rsExtracao.getString("EMAIL"));
            if (rsExtracao.getString("FON").length() > 0) {
                psClienteUpdate.setString(11, rsExtracao.getString("FON"));
            } else {
                psClienteUpdate.setString(11, " ");
            }
            psClienteUpdate.setDate(12, rsExtracao.getDate("DTNASC"));
            psClienteUpdate.setString(13, rsExtracao.getString("ESTCIVIL"));
            psClienteUpdate.setString(14, rsExtracao.getString("ID_ESTCIVILCLI"));
            psClienteUpdate.setString(15, rsExtracao.getString("END"));
            psClienteUpdate.setString(16, "2");// Id_logradouro 2 Rua
            psClienteUpdate.setString(17, rsExtracao.getString("NUM"));
            psClienteUpdate.setString(18, rsExtracao.getString("COMPL"));
            psClienteUpdate.setString(19, rsExtracao.getString("BAI"));
            psClienteUpdate.setString(20, rsExtracao.getString("CID"));
            if (rsExtracao.getString("CODIBGE").length() > 0) {
                psClienteUpdate.setString(21, rsExtracao.getString("CODIBGE"));
            } else {
                psClienteUpdate.setString(21, "0");
            }
            psClienteUpdate.setString(22, rsExtracao.getString("CEP"));
            psClienteUpdate.setString(23, rsExtracao.getString("EST"));
            psClienteUpdate.setString(24, "C");
            psClienteUpdate.setString(25, rsExtracao.getString("NEG"));
            psClienteUpdate.setString(26, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
            psClienteUpdate.setInt(27, idClienteUpdate);
            psClienteUpdate.executeUpdate();

            /**
             * Atualiza as informações referentes ao avalista, Se a parcela não possuir um avalista
             * pula para update da parcela.
             */
            if (rsExtracao.getString("AVALISTA").trim().length() > 0) {
                psAvalistaUpdate.setString(1, rsExtracao.getString("NOMEAVALISTA"));
                psAvalistaUpdate.setString(2, rsExtracao.getString("TIPO_AVALISTA"));
                psAvalistaUpdate.setString(3, rsExtracao.getString("CPF_AVALISTA"));
                if (rsExtracao.getString("CPF_AVALISTA").length() < 13) {
                    psAvalistaUpdate.setString(4, rsExtracao.getString("CPF_AVALISTA").substring(8, 9));
                } else {
                    psAvalistaUpdate.setString(4, " ");
                }
                psAvalistaUpdate.setString(5, rsExtracao.getString("FILPAI_AVAL"));
                psAvalistaUpdate.setString(6, rsExtracao.getString("FILMAE_AVAL"));
                psAvalistaUpdate.setString(7, rsExtracao.getString("RG_AVALISTA"));
                psAvalistaUpdate.setDate(8, rsExtracao.getDate("DTEXPED_AVAL"));
                psAvalistaUpdate.setString(9, rsExtracao.getString("EXPEDITOR_AVAL"));
                psAvalistaUpdate.setString(10, rsExtracao.getString("EMAIL_AVAL"));
                if (rsExtracao.getString("FON_AVAL").length() > 0) {
                    psAvalistaUpdate.setString(11, rsExtracao.getString("FON_AVAL"));
                } else {
                    psAvalistaUpdate.setString(11, " ");
                }
                psAvalistaUpdate.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                psAvalistaUpdate.setString(13, rsExtracao.getString("ESTCIVIL_AVAL"));
                psAvalistaUpdate.setString(14, rsExtracao.getString("ID_ESTCIVILAVAL"));
                psAvalistaUpdate.setString(15, rsExtracao.getString("END_AVAL"));
                psAvalistaUpdate.setString(16, "2");// Id_logradouro 2 Rua
                psAvalistaUpdate.setString(17, rsExtracao.getString("NUM_AVAL"));
                psAvalistaUpdate.setString(18, rsExtracao.getString("COMPL_AVAL"));
                psAvalistaUpdate.setString(19, rsExtracao.getString("BAIRRO_AVAL"));
                psAvalistaUpdate.setString(20, rsExtracao.getString("CIDADE_AVAL"));
                if (rsExtracao.getString("CODIBGE_AVAL").trim().length() > 0) {
                    psAvalistaUpdate.setString(21, rsExtracao.getString("CODIBGE_AVAL"));
                } else {
                    psAvalistaUpdate.setString(21, "0");
                }
                psAvalistaUpdate.setString(22, rsExtracao.getString("CEP_AVAL"));
                psAvalistaUpdate.setString(23, rsExtracao.getString("EST_AVAL"));
                psAvalistaUpdate.setString(24, "A");
                psAvalistaUpdate.setString(25, rsExtracao.getString("NEG_AVAL"));
                psAvalistaUpdate.setString(26, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
                //  psAvalistaUpdate.setInt(27, idParcelaBanco);
                psAvalistaUpdate.setInt(27, idAvalistaUpdate);
                psAvalistaUpdate.execute();

            }
            /**
             * ******UPDATE PARCELA*****
             */
//            psParcelaUpdate.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
//            psParcelaUpdate.setDate(2, rsExtracao.getDate("DATALAN"));
//            psParcelaUpdate.setDate(3, rsExtracao.getDate("VENC"));
//            psParcelaUpdate.setBigDecimal(4, rsExtracao.getBigDecimal("VALOR"));
//            psParcelaUpdate.setDate(5, rsExtracao.getDate("DATAPAG"));
//            psParcelaUpdate.setBigDecimal(6, rsExtracao.getBigDecimal("CAPITPAG"));
//            psParcelaUpdate.setString(7, rsExtracao.getString("SIT_MR02"));
//            psParcelaUpdate.setBigDecimal(8, rsExtracao.getBigDecimal("TAXA"));
//            psParcelaUpdate.setBigDecimal(9, rsExtracao.getBigDecimal("JUROS"));
//            psParcelaUpdate.setBigDecimal(10, rsExtracao.getBigDecimal("VALORCALC"));
//            psParcelaUpdate.setBigDecimal(11, rsExtracao.getBigDecimal("VALORPAG"));
//            psParcelaUpdate.setBigDecimal(12, rsExtracao.getBigDecimal("JUROSPAG"));
//            psParcelaUpdate.setDate(13, rsExtracao.getDate("NEGATIVADA"));
//            psParcelaUpdate.setDate(14, rsExtracao.getDate("BAIXANEG"));
//            psParcelaUpdate.setDate(15, Utilitarios.converteData(new Date()));
//            psParcelaUpdate.setDate(16, rsExtracao.getDate("DATAALT"));
//            psParcelaUpdate.setString(17, rsExtracao.getString("NUMPAR"));
//            psParcelaUpdate.setString(18, rsExtracao.getString("TPGTO"));
//            psParcelaUpdate.setInt(19, idParcelaBanco);
//            psParcelaUpdate.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }
}
