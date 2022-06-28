/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ConsultaModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author luiz.almeida
 */
public class ExtracaoDAO {

    public boolean retornoExt, valApp;
    private PreparedStatement psExtracao, psAvalista, psAvalistaUpdate, psParcela, psPessoa, psExtrator, psProcesso, psAtualizarRegistroExtrator, psVerificaInclusoes, psApp,
            psClienteUpdate, psInserirLogParcela, psVerificaParcela, psVerificaParcelaBaixa, psParcelaUpdate, psParcelasSemRetorno, psConsultaParcelaInclusao,
            psLogExtracao, psParcelasRetornada, psBuscarParcelasManual, psParcelaManual, psExtracaoManual, psVerificaParcelaOriginal, psConsultaTipoParcelaExtrator,
            psFinalizaInclusaoPendente, psUpdateContasReceber;
    private ResultSet rsExtracao, rsCliente, rsAvalista, rsParcelas, rsProExtrator, rsVerificaParcela, rsVerificaParcelaBaixa, rsParcelasSemRetorno,
            rsParcelasRetornada, rsBuscarParcelasManual, rsParcelaManual, rsVerificaParcelaOriginal, rsConsultaTipoParcelaExtrator, rsConsultaParcelaInclusao, rsVerificaInclusoes, rsApp;
    public List<ParcelaModel> lparcelasModel, lparcelasSalvarModel, lretornarParcelas, lretornarAvalista, lbuscarParcelasManual;
    public List<ExtracaoModel> lextracaoModel;
    public int idCliente, idAvalista, idParcela, idExtrator, resultado, opcao;
    public String sqlVerificaExtracao, sqlInsertCliente, sqlInsertAval, sqlInsertParcelas, sqlInsertExtrator, sqlInsertLog, sqlUpdateParcelasErro,
            sqlUpdateParcelas, sqlUpdatePessoas, sqlUpdateExtrator, sqlInsertLogExtracao, sqlSelectManual, sqlSelectFiliais, sqlSelectParcelaBaixaOrigem, sqlUpdateRetornoBaixa;

    public String logExtracao, statusLogExtracao, retornoTryCath;
    public int contErro = 0;
    public int contregistros = 0;
    private static String filial;
    private int avalista;
    private Calendar agora = Calendar.getInstance();
    private Format formato;

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
                + "                       COALESCE(PARCELA.ID_REGISTRO_BVS, ' ') ID_REGISTRO_BVS,\n"
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

    public String getSqlSelectExtracaoFloIErro() {
        String sqlSelectExtracaoFloIErro = "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.ID_FILIAL AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       NVL(CLIENTE_INF.CODSEX, ' ') AS SEXO,\n"
                + "       CLI.SITUACAO AS SIT_CLIENTE,\n"
                + "       CLIENTE_INF.NUMRGE AS NUMERO_RG,\n"
                + "       CLIENTE_INF.ORGRGE AS EXPEDIDOR,\n"
                + "       CLIENTE_INF.DATRGE AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       CLIENTE_INF.DATNAS AS DATA_NASCIMENTO,\n"
                + "       CLIENTE_INF.NOMPAI AS FIL_PAI,\n"
                + "       CLIENTE_INF.NOMMAE AS FIL_MAE,\n"
                + "       CLIENTE_INF.ESTCIV AS ID_ESTCIVILCLI,\n"
                + "       CLIENTE_INF.ESTCIV AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       E008CEP.CODIBG AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       E301TCR.ULTPGT AS DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       0 AS VALORCALC,\n"
                + "       0 AS VALORPAG,\n"
                + "       'N' AS SITUACAO_TITULO,\n"
                + "       '' AS TIPO_PGTO,\n"
                + "       E301TCR.USU_DATNEG AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       NVL(AVALISTA.CODCLI, 0) AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGCCPF AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPCLI AS TIPO_AVALISTA,\n"
                + "       AVALISTA_INF.CODSEX AS SEXO_AVAL,\n"
                + "       AVA.SITUACAO AS SIT_AVALISTA,\n"
                + "       AVALISTA_INF.NUMRGE AS RG_AVALISTA,\n"
                + "       AVALISTA_INF.ORGRGE AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA_INF.DATRGE AS DTEXPED_AVAL,\n"
                + "       AVALISTA.INTNET AS EMAIL_AVAL,\n"
                + "       AVALISTA.FONCLI AS FONE_AVAL,\n"
                + "       AVALISTA_INF.DATNAS AS DTNASC_AVAL,\n"
                + "       AVALISTA_INF.NOMPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA_INF.NOMMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ENDCLI AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NENCLI AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAICLI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.CPLEND AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDCLI AS CIDADE_AVAL,\n"
                + "       E008CEP_AVAL.CODIBG AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.SIGUFS AS UF_AVAL,\n"
                + "       AVALISTA.CEPCLI AS CEP_AVAL,\n"
                + "       AVALISTA.USU_NEGCLI AS NEGATIVADO_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS ID_ESTCIVILAVAL,\n"
                + "       EXTRATOR.TIPO AS TIPO_ACAO\n"
                + "  FROM PARCELA\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + " INNER JOIN SAPIENS.E301TCR\n"
                + "    ON (PARCELA.NUMTIT = E301TCR.NUMTIT OR\n"
                + "                LPAD(PARCELA.NUMERO_DOC, 12, '0') =\n"
                + "                LPAD(E301TCR.USU_CODCTR, 12, '0'))\n"
                + "   AND FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = E301TCR.CODCLI\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS CLIEND\n"
                + "    ON CLIEND.CODCLI = CLIENTE.CODCLI\n"
                + "   AND CLIEND.LOJA = FILIAL.ID_FILIAL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS AVAEND\n"
                + "    ON AVAEND.CODCLI = E301TCR.USU_CODAVA\n"
                + "   AND AVAEND.LOJA = FILIAL.ID_FILIAL\n"
                + "   AND E301TCR.USU_CODAVA > 0\n"
                + "   AND E301TCR.USU_CODAVA IS NOT NULL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES CLI\n"
                + "    ON CLI.ID_REGISTRO = CLIEND.CLIENTE\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES AVA\n"
                + "    ON AVA.ID_REGISTRO = AVAEND.CLIENTE\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = E301TCR.USU_CODAVA\n"
                + "   AND (E301TCR.USU_NEGAVA <> 'N' OR E301TCR.USU_NEGAVA IS NULL)\n"
                + "   AND AVA.SITUACAO NOT IN ('08', '09', '12', '13')\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS CLIENTE_INF\n"
                + "    ON CLIENTE_INF.CODCLI = CLIENTE.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS AVALISTA_INF\n"
                + "    ON AVALISTA_INF.CODCLI = AVALISTA.CODCLI\n"
                + "  LEFT JOIN SAPIENS.E008CEP\n"
                + "    ON E008CEP.CEPINI = CLIENTE.CEPINI\n"
                + "   AND E008CEP.CEPFIM >= CLIENTE.CEPCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP E008CEP_AVAL\n"
                + "    ON E008CEP_AVAL.CEPINI = AVALISTA.CEPINI\n"
                + "   AND E008CEP_AVAL.CEPFIM >= AVALISTA.CEPCLI\n"
                + "  LEFT OUTER JOIN EXTRATOR\n"
                + "    ON PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + " WHERE (E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "           FROM SAPIENS.E301MCR\n"
                + "          WHERE E301MCR.NUMTIT = E301TCR.NUMTIT \n"
                + "            AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "            AND E301MCR.CODFIL = E301TCR.CODFIL))\n"
                + "   AND EXTRATOR.STATUS IN ('I', 'EP')\n"
                + "    AND E301TCR.SITTIT NOT IN ('LS', 'LQ') ";
        return sqlSelectExtracaoFloIErro;
    }

    public String getSelectBaixaInd() {
        String selectBaixaInd = "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.ID_FILIAL AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       CLIENTE_INF.CODSEX AS SEXO,\n"
                + "       '' AS SIT_CLIENTE,\n"
                + "       CLIENTE_INF.NUMRGE AS NUMERO_RG,\n"
                + "       CLIENTE_INF.ORGRGE AS EXPEDIDOR,\n"
                + "       CLIENTE_INF.DATRGE AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       CLIENTE_INF.DATNAS AS DATA_NASCIMENTO,\n"
                + "       CLIENTE_INF.NOMPAI AS FIL_PAI,\n"
                + "       CLIENTE_INF.NOMMAE AS FIL_MAE,\n"
                + "       CLIENTE_INF.ESTCIV AS ID_ESTCIVILCLI,\n"
                + "       CLIENTE_INF.ESTCIV AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       E008CEP.CODIBG AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.TPTRLC = '012' THEN\n"
                + "          E301MCR.DATPGT\n"
                + "         ELSE\n"
                + "          E301TCR.ULTPGT\n"
                + "       END DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       VLRLIQ AS VALORCALC,\n"
                + "       VLRLIQ AS VALORPAG,\n"
                + "       'S' AS SITUACAO_TITULO,\n"
                + "       CASE\n"
                + "         WHEN E301TCR.SITTIT = 'LQ' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'Q'\n"
                + "         WHEN E301TCR.SITTIT = 'AB' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'P'\n"
                + "         WHEN E301TCR.SITTIT = 'LS' AND E301MCR.TPTRLC = '012' THEN\n"
                + "          'N'\n"
                + "       END TIPO_PGTO,\n"
                + "       NVL(E301TCR.USU_DATNEG, '31/12/1900') AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       NVL(AVALISTA.CODCLI, 0) AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGCCPF AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPCLI AS TIPO_AVALISTA,\n"
                + "       AVALISTA_INF.CODSEX AS SEXO_AVAL,\n"
                + "       ' ' AS SIT_AVALISTA,\n"
                + "       AVALISTA_INF.NUMRGE AS RG_AVALISTA,\n"
                + "       AVALISTA_INF.ORGRGE AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA_INF.DATRGE AS DTEXPED_AVAL,\n"
                + "       AVALISTA.INTNET AS EMAIL_AVAL,\n"
                + "       AVALISTA.FONCLI AS FONE_AVAL,\n"
                + "       AVALISTA_INF.DATNAS AS DTNASC_AVAL,\n"
                + "       AVALISTA_INF.NOMPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA_INF.NOMMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ENDCLI AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NENCLI AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAICLI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.CPLEND AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDCLI AS CIDADE_AVAL,\n"
                + "       E008CEP_AVAL.CODIBG AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.SIGUFS AS UF_AVAL,\n"
                + "       AVALISTA.CEPCLI AS CEP_AVAL,\n"
                + "       AVALISTA.USU_NEGCLI AS NEGATIVADO_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS ID_ESTCIVILAVAL,\n"
                + "       'B' AS TIPO_ACAO\n"
                + "  FROM SAPIENS.E301TCR\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = E301TCR.CODCLI\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = E301TCR.USU_CODAVA\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS CLIENTE_INF\n"
                + "    ON CLIENTE_INF.CODCLI = CLIENTE.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS AVALISTA_INF\n"
                + "    ON AVALISTA_INF.CODCLI = AVALISTA.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP\n"
                + "    ON E008CEP.CEPINI = CLIENTE.CEPINI\n"
                + "   AND E008CEP.CEPFIM >= CLIENTE.CEPCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP E008CEP_AVAL\n"
                + "    ON E008CEP_AVAL.CEPINI = AVALISTA.CEPINI\n"
                + "   AND E008CEP_AVAL.CEPFIM >= AVALISTA.CEPCLI\n"
                + " WHERE E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "          FROM SAPIENS.E301MCR\n"
                + "         WHERE E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "           AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "           AND E301MCR.CODFIL = E301TCR.CODFIL)\n"
                + "   and (EXISTS (SELECT 1\n"
                + "                  FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                 WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                   AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') =\n"
                + "                       LPAD(E301TCR.USU_CODCTR, 12, '0')\n"
                + "                   AND FILIAIS.FILIAL_SGL = FILIAL.FILIAL_SGL \n"
                + "                   AND E301TCR.USU_DATNEG > '31/12/1900'              \n"
                + "                   AND ((E301TCR.ULTPGT BETWEEN SYSDATE - 180 AND SYSDATE) OR\n"
                + "                       (E301MCR.DATPGT BETWEEN SYSDATE - 180 AND SYSDATE AND\n"
                + "                       E301MCR.TPTRLC = '012'))\n"
                + "                   AND (E301TCR.USU_DATBAI = '31/12/1900' OR\n"
                + "                       E301TCR.USU_DATBAI IS NULL)))\n"
                + "   AND FILIAL.DATA_INICIO_DBNOVO > '31/12/1900'\n"
                + "   AND (E301TCR.ULTPGT >= USU_DATNEG OR (USU_DATNEG IS NULL) OR\n"
                + "       E301MCR.DATPGT >= USU_DATNEG)";
        return selectBaixaInd;
    }

    public String getSqlSelectExtracao() {
        String sqlSelectExtracao = "SELECT MR02.PTO AS PTO,\n"
                + "       FILIAIS.CODFIL AS FILIAL,\n"
                + "       FILIAIS.ID_FILIAL AS FILIAL_FLO,\n"
                + "       MR02.DOC AS DOC,\n"
                + "       MR02.CODIGO AS CHAVE_SGL,\n"
                + "       MR02.CLI AS CLIENTE,\n"
                + "       CLIENTE.RAZ AS NOME_CLIENTE,\n"
                + "       TO_NUMBER(CLIENTE.CGC) AS CGCCPF,\n"
                + "       CLIENTE.TIPO AS TIPO_PESSOA,\n"
                + "       CLIENTE.SIT  AS SIT_CLIENTE,\n"
                + "       CLIENTE.INS AS NUMERO_RG,\n"
                + "       CLIENTE.EXPEDIDOR AS EXPEDIDOR,\n"
                + "       CLIENTE.DTEXPED AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.EMAIL AS EMAIL,\n"
                + "       CLIENTE.FON AS TELEFONE,\n"
                + "       CLIENTE.DTNASC AS DATA_NASCIMENTO,\n"
                + "       CLIENTE.FILPAI AS FIL_PAI,\n"
                + "       CLIENTE.FILMAE AS FIL_MAE,\n"
                + "       CLIENTE.ESTCIVIL AS EST_CIVIL,\n"
                + "       CLIENTE.END AS ENDERECO,\n"
                + "       CLIENTE.NUM AS NUMERO_END,\n"
                + "       CLIENTE.BAI AS BAIRRO,\n"
                + "       CLIENTE.COMPL AS COMPLEMENTO,\n"
                + "       CLIENTE.NEG AS NEGATIVADO,\n"
                + "       CLIENTE.CID AS CIDADE,\n"
                + "       CLIENTE.CODIBGE AS CODI_IBGE,\n"
                + "       CLIENTE.EST AS UF,\n"
                + "       CLIENTE.CEP AS CEP,\n"
                + "       MR02.NUMPAR AS PARCELA,\n"
                + "       MR02.DATALAN AS DATA_LANCAMENTO,\n"
                + "       MR02.VENC AS VENCIMENTO,\n"
                + "       MR02.VALOR AS VALOR,\n"
                + "       MR02.DATAPAG AS DATA_PAGAMENTO,\n"
                + "       MR02.DATAALT AS DATA_ALTERACAO,\n"
                + "       MR02.DATA_MOV_RETORNAR AS DATA_MOV_RETORNAR,\n"
                + "       MR02.TAXA AS TAXA,\n"
                + "       MR02.JUROS AS JUROS,\n"
                + "       MR02.VALORCALC AS VALORCALC,\n"
                + "       MR02.VALORPAG AS VALORPAG,\n"
                + "       MR02.CAPITPAG AS CAPITPAG,\n"
                + "       MR02.JUROSPAG AS JUROSPAG,\n"
                + "       MR02.SIT AS SITUACAO_TITULO,\n"
                + "       MR02.TPGTO AS TIPO_PGTO,\n"
                + "       MR02.NEGATIVADA AS DATA_NEGATIVACAO,\n"
                + "       MR02.BAIXANEG AS DATA_BAIXA,\n"
                + "       MR02.AVALISTA AS AVALISTA,\n"
                + "       MR02.RETORNADO AS RETORNADO,\n"
                + "       AVALISTA.RAZ AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGC AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPO AS TIPO_AVALISTA,\n"
                + "       AVALISTA.SIT AS SIT_AVALISTA,\n"
                + "       AVALISTA.INS AS RG_AVALISTA,\n"
                + "       AVALISTA.EXPEDIDOR AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA.DTEXPED AS DTEXPED_AVAL,\n"
                + "       AVALISTA.EMAIL AS EMAIL_AVAL,\n"
                + "       AVALISTA.FON AS FONE_AVAL,\n"
                + "       AVALISTA.DTNASC AS DTNASC_AVAL,\n"
                + "       AVALISTA.FILPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA.FILMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA.ESTCIVIL AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.END AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NUM AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.COMPL AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CID AS CIDADE_AVAL,\n"
                + "       AVALISTA.CODIBGE AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.EST AS UF_AVAL,\n"
                + "       AVALISTA.CEP AS CEP_AVAL,\n"
                + "       AVALISTA.NEG AS NEGATIVADO_AVAL,\n"
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

    public String getSqlExtracaoFloBaixa() {
        String sqlExtracaoFloBaixa = ""
                + "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.ID_FILIAL AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       CLIENTE_INF.CODSEX AS SEXO,\n"
                + "       '' AS SIT_CLIENTE,\n"
                + "       CLIENTE_INF.NUMRGE AS NUMERO_RG,\n"
                + "       CLIENTE_INF.ORGRGE AS EXPEDIDOR,\n"
                + "       CLIENTE_INF.DATRGE AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       CLIENTE_INF.DATNAS AS DATA_NASCIMENTO,\n"
                + "       CLIENTE_INF.NOMPAI AS FIL_PAI,\n"
                + "       CLIENTE_INF.NOMMAE AS FIL_MAE,\n"
                + "       CLIENTE_INF.ESTCIV AS ID_ESTCIVILCLI,\n"
                + "       CLIENTE_INF.ESTCIV AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       E008CEP.CODIBG AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.TPTRLC = '012' THEN\n"
                + "          E301MCR.DATPGT\n"
                + "         ELSE\n"
                + "          E301TCR.ULTPGT\n"
                + "       END DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       VLRLIQ AS VALORCALC,\n"
                + "       VLRLIQ AS VALORPAG,\n"
                + "       'S' AS SITUACAO_TITULO,\n"
                + "       CASE\n"
                + "         WHEN E301TCR.SITTIT = 'LQ' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'Q'\n"
                + "         WHEN E301TCR.SITTIT = 'AB' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'P'\n"
                + "         WHEN E301TCR.SITTIT = 'LS' AND E301MCR.TPTRLC = '012' THEN\n"
                + "          'N'\n"
                + "       END TIPO_PGTO,\n"
                + "       NVL(E301TCR.USU_DATNEG, '31/12/1900') AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       NVL(AVALISTA.CODCLI, 0) AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGCCPF AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPCLI AS TIPO_AVALISTA,\n"
                + "       AVALISTA_INF.CODSEX AS SEXO_AVAL,\n"
                + "       ' ' AS SIT_AVALISTA,\n"
                + "       AVALISTA_INF.NUMRGE AS RG_AVALISTA,\n"
                + "       AVALISTA_INF.ORGRGE AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA_INF.DATRGE AS DTEXPED_AVAL,\n"
                + "       AVALISTA.INTNET AS EMAIL_AVAL,\n"
                + "       AVALISTA.FONCLI AS FONE_AVAL,\n"
                + "       AVALISTA_INF.DATNAS AS DTNASC_AVAL,\n"
                + "       AVALISTA_INF.NOMPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA_INF.NOMMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ENDCLI AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NENCLI AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAICLI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.CPLEND AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDCLI AS CIDADE_AVAL,\n"
                + "       E008CEP_AVAL.CODIBG AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.SIGUFS AS UF_AVAL,\n"
                + "       AVALISTA.CEPCLI AS CEP_AVAL,\n"
                + "       AVALISTA.USU_NEGCLI AS NEGATIVADO_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS ID_ESTCIVILAVAL,\n"
                + "       'B' AS TIPO_ACAO\n"
                + "  FROM SAPIENS.E301TCR\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = E301TCR.CODCLI\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = E301TCR.USU_CODAVA\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS CLIENTE_INF\n"
                + "    ON CLIENTE_INF.CODCLI = CLIENTE.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS AVALISTA_INF\n"
                + "    ON AVALISTA_INF.CODCLI = AVALISTA.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP\n"
                + "    ON E008CEP.CEPINI = CLIENTE.CEPINI\n"
                + "   AND E008CEP.CEPFIM >= CLIENTE.CEPCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP E008CEP_AVAL\n"
                + "    ON E008CEP_AVAL.CEPINI = AVALISTA.CEPINI\n"
                + "   AND E008CEP_AVAL.CEPFIM >= AVALISTA.CEPCLI\n";
        String sqlExtracaoFloBaixa2 = ""
                + "SELECT FILIAL.USU_CODPTO AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.USU_CODFLO AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       ' ' AS SEXO,\n"
                + "       ' ' AS SIT_CLIENTE,\n"
                + "       ' ' AS NUMERO_RG,\n"
                + "       ' ' AS EXPEDIDOR,\n"
                + "       TO_DATE('31/12/1900', 'DD/MM/YYYY') AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       TO_DATE('31/12/1900', 'DD/MM/YYYY') AS DATA_NASCIMENTO,\n"
                + "       '' AS FIL_PAI,\n"
                + "       '' AS FIL_MAE,\n"
                + "       0 AS ID_ESTCIVILCLI,\n"
                + "       0 AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       0 AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       E301TCR.ULTPGT AS DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       0 AS VALORCALC,\n"
                + "       0 AS VALORPAG,\n"
                + "       'N' AS SITUACAO_TITULO,\n"
                + "       '' AS TIPO_PGTO,\n"
                + "       E301TCR.USU_DATNEG AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       E301TCR.USU_CODAVA AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       '' AS NOME_AVALISTA,\n"
                + "       0 AS CPF_AVALISTA,\n"
                + "       '' AS TIPO_AVALISTA,\n"
                + "       ' ' AS SEXO_AVAL,\n"
                + "       ' ' AS SIT_AVALISTA,\n"
                + "       '' AS RG_AVALISTA,\n"
                + "       '' AS EXPEDITOR_AVAL,\n"
                + "       TO_DATE('31/12/1900', 'DD/MM/YYYY') AS DTEXPED_AVAL,\n"
                + "       '' AS EMAIL_AVAL,\n"
                + "       '' AS FONE_AVAL,\n"
                + "       TO_DATE('31/12/1900', 'DD/MM/YYYY') AS DTNASC_AVAL,\n"
                + "       '' AS FILPAI_AVAL,\n"
                + "       '' AS FILMAE_AVAL,\n"
                + "       0 AS EST_CIVIL_AVAL,\n"
                + "       '' AS ENDERECO_AVAL,\n"
                + "       '' AS NUMERO_END_AVAL,\n"
                + "       '' AS BAIRRO_AVAL,\n"
                + "       '' AS COMPLEMENTO_AVAL,\n"
                + "       '' AS CIDADE_AVAL,\n"
                + "       0 AS CODI_IBGE_AVAL,\n"
                + "       '' AS UF_AVAL,\n"
                + "       0 AS CEP_AVAL,\n"
                + "       '' AS NEGATIVADO_AVAL,\n"
                + "       0 AS EST_CIVIL_AVAL,\n"
                + "       0 AS ID_ESTCIVILAVAL,\n"
                + "       'B' AS TIPO_ACAO\n"
                + "  FROM PARCELA,\n"
                + "       SAPIENS.E301TCR,\n"
                + "       SAPIENS.E301MCR,\n"
                + "       SAPIENS.E070FIL FILIAL,\n"
                + "       SAPIENS.E085CLI CLIENTE,\n"
                + "       PESSOA\n"
                + " WHERE PARCELA.ID_FILIAL = FILIAL.USU_CODFLO\n"
                + "   AND PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "   AND E301TCR.CODEMP = FILIAL.CODEMP\n"
                + "   AND E301TCR.CODFIL = FILIAL.CODFIL\n"
                + "   AND E301TCR.CODCLI = CLIENTE.CODCLI\n"
                + "   AND CLIENTE.CGCCPF = TO_NUMBER(PESSOA.CPF_RAZAO)\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "   AND E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') =\n"
                + "       LPAD(E301TCR.USU_CODCTR, 12, '0')\n"
                + "   AND ((E301TCR.ULTPGT > SYSDATE - 30) OR\n"
                + "       (E301MCR.DATPGT > SYSDATE - 30 AND E301MCR.TPTRLC = '012'))\n"
                + "   AND (E301TCR.SITTIT NOT IN ('AB') OR\n"
                + "       (E301TCR.SITTIT = 'AB' AND E301TCR.ULTPGT >= E301TCR.USU_DATNEG))  \n"
                + "   AND E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "          FROM SAPIENS.E301MCR\n"
                + "         WHERE E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "           AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "           AND E301MCR.CODFIL = E301TCR.CODFIL)\n"
                + "          AND (USU_DATBAI = '31/12/1900' OR USU_DATBAI IS NULL OR (USU_DATBAI < USU_DATNEG))\n"
                + "   AND FILIAL.USU_DATIFL > '01/01/2020'";
        return sqlExtracaoFloBaixa2;
    }
     public String getSqlExtracaoFloBaixaManual() {
        String sqlExtracaoFloBaixa = ""
                + "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.ID_FILIAL AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       CLIENTE_INF.CODSEX AS SEXO,\n"
                + "       '' AS SIT_CLIENTE,\n"
                + "       CLIENTE_INF.NUMRGE AS NUMERO_RG,\n"
                + "       CLIENTE_INF.ORGRGE AS EXPEDIDOR,\n"
                + "       CLIENTE_INF.DATRGE AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       CLIENTE_INF.DATNAS AS DATA_NASCIMENTO,\n"
                + "       CLIENTE_INF.NOMPAI AS FIL_PAI,\n"
                + "       CLIENTE_INF.NOMMAE AS FIL_MAE,\n"
                + "       CLIENTE_INF.ESTCIV AS ID_ESTCIVILCLI,\n"
                + "       CLIENTE_INF.ESTCIV AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       E008CEP.CODIBG AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.TPTRLC = '012' THEN\n"
                + "          E301MCR.DATPGT\n"
                + "         ELSE\n"
                + "          E301TCR.ULTPGT\n"
                + "       END DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       VLRLIQ AS VALORCALC,\n"
                + "       VLRLIQ AS VALORPAG,\n"
                + "       'S' AS SITUACAO_TITULO,\n"
                + "       CASE\n"
                + "         WHEN E301TCR.SITTIT = 'LQ' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'Q'\n"
                + "         WHEN E301TCR.SITTIT = 'AB' AND E301TCR.SITANT = 'AB' THEN\n"
                + "          'P'\n"
                + "         WHEN E301TCR.SITTIT = 'LS' AND E301MCR.TPTRLC = '012' THEN\n"
                + "          'N'\n"
                + "       END TIPO_PGTO,\n"
                + "       NVL(E301TCR.USU_DATNEG, '31/12/1900') AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       NVL(AVALISTA.CODCLI, 0) AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGCCPF AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPCLI AS TIPO_AVALISTA,\n"
                + "       AVALISTA_INF.CODSEX AS SEXO_AVAL,\n"
                + "       ' ' AS SIT_AVALISTA,\n"
                + "       AVALISTA_INF.NUMRGE AS RG_AVALISTA,\n"
                + "       AVALISTA_INF.ORGRGE AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA_INF.DATRGE AS DTEXPED_AVAL,\n"
                + "       AVALISTA.INTNET AS EMAIL_AVAL,\n"
                + "       AVALISTA.FONCLI AS FONE_AVAL,\n"
                + "       AVALISTA_INF.DATNAS AS DTNASC_AVAL,\n"
                + "       AVALISTA_INF.NOMPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA_INF.NOMMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ENDCLI AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NENCLI AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAICLI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.CPLEND AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDCLI AS CIDADE_AVAL,\n"
                + "       E008CEP_AVAL.CODIBG AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.SIGUFS AS UF_AVAL,\n"
                + "       AVALISTA.CEPCLI AS CEP_AVAL,\n"
                + "       AVALISTA.USU_NEGCLI AS NEGATIVADO_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS ID_ESTCIVILAVAL,\n"
                + "       'B' AS TIPO_ACAO\n"
                + "  FROM SAPIENS.E301TCR\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = E301TCR.CODCLI\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = E301TCR.USU_CODAVA\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS CLIENTE_INF\n"
                + "    ON CLIENTE_INF.CODCLI = CLIENTE.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS AVALISTA_INF\n"
                + "    ON AVALISTA_INF.CODCLI = AVALISTA.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP\n"
                + "    ON E008CEP.CEPINI = CLIENTE.CEPINI\n"
                + "   AND E008CEP.CEPFIM >= CLIENTE.CEPCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP E008CEP_AVAL\n"
                + "    ON E008CEP_AVAL.CEPINI = AVALISTA.CEPINI\n"
                + "   AND E008CEP_AVAL.CEPFIM >= AVALISTA.CEPCLI\n";

        return sqlExtracaoFloBaixa;
    }

    public String getSqlExtracaoFloInclusao() {
        String sqlExtracaoFloInclusao = ""
                + "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       FILIAL.CODFIL AS FILIAL,\n"
                + "       FILIAL.ID_FILIAL AS FILIAL_FLO,\n"
                + "       E301TCR.SITTIT,\n"
                + "       E301TCR.NUMTIT AS TITULO,\n"
                + "       E301TCR.USU_CODCTR AS DOC,\n"
                + "       E301TCR.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       E301TCR.USU_IDETCR AS USU_IDETCR,\n"
                + "       E301TCR.CODTPT AS CODTPT,\n"
                + "       E301TCR.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.TIPCLI AS TIPO_PESSOA,\n"
                + "       NVL(CLIENTE_INF.CODSEX, ' ') AS SEXO,\n"
                + "       CLI.SITUACAO AS SIT_CLIENTE,\n"
                + "       CLIENTE_INF.NUMRGE AS NUMERO_RG,\n"
                + "       CLIENTE_INF.ORGRGE AS EXPEDIDOR,\n"
                + "       CLIENTE_INF.DATRGE AS DATA_EXPEDICAO,\n"
                + "       CLIENTE.INTNET AS EMAIL,\n"
                + "       CLIENTE.FONCLI AS TELEFONE,\n"
                + "       CLIENTE_INF.DATNAS AS DATA_NASCIMENTO,\n"
                + "       CLIENTE_INF.NOMPAI AS FIL_PAI,\n"
                + "       CLIENTE_INF.NOMMAE AS FIL_MAE,\n"
                + "       CLIENTE_INF.ESTCIV AS ID_ESTCIVILCLI,\n"
                + "       CLIENTE_INF.ESTCIV AS EST_CIVIL,\n"
                + "       CLIENTE.ENDCLI AS ENDERECO,\n"
                + "       CLIENTE.NENCLI AS NUMERO_END,\n"
                + "       CLIENTE.BAICLI AS BAIRRO,\n"
                + "       CLIENTE.CPLEND AS COMPLEMENTO,\n"
                + "       CLIENTE.USU_NEGCLI AS NEGATIVADO,\n"
                + "       CLIENTE.CIDCLI AS CIDADE,\n"
                + "       E008CEP.CODIBG AS CODI_IBGE,\n"
                + "       CLIENTE.SIGUFS AS UF,\n"
                + "       CLIENTE.CEPCLI AS CEP,\n"
                + "       E301TCR.USU_NUMPAR AS PARCELA,\n"
                + "       E301TCR.DATEMI AS DATA_LANCAMENTO,\n"
                + "       E301TCR.VCTORI AS VENCIMENTO,\n"
                + "       E301TCR.VLRABE AS VALOR,\n"
                + "       E301TCR.VLRORI AS VALOR_ORIGINAL,\n"
                + "       E301TCR.ULTPGT AS DATA_PAGAMENTO,\n"
                + "       E301TCR.USU_DATALT AS DATA_ALTERACAO,\n"
                + "       TO_DATE(SYSDATE) AS DATA_MOV_RETORNAR,\n"
                + "       0 AS TAXA,\n"
                + "       0 AS JUROS,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRLIQ\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END VLRLIQ,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITPAG,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRJRS\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END JUROSPAG,\n"
                + "       0 AS VALORCALC,\n"
                + "       0 AS VALORPAG,\n"
                + "       'N' AS SITUACAO_TITULO,\n"
                + "       '' AS TIPO_PGTO,\n"
                + "       E301TCR.USU_DATNEG AS DATA_NEGATIVACAO,\n"
                + "       E301TCR.USU_DATBAI AS DATA_BAIXA,\n"
                + "       NVL(AVALISTA.CODCLI, 0) AS AVALISTA,\n"
                + "       'S' AS RETORNADO,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA,\n"
                + "       AVALISTA.CGCCPF AS CPF_AVALISTA,\n"
                + "       AVALISTA.TIPCLI AS TIPO_AVALISTA,\n"
                + "       AVALISTA_INF.CODSEX AS SEXO_AVAL,\n"
                + "       AVA.SITUACAO AS SIT_AVALISTA,\n"
                + "       AVALISTA_INF.NUMRGE AS RG_AVALISTA,\n"
                + "       AVALISTA_INF.ORGRGE AS EXPEDIDOR_AVAL,\n"
                + "       AVALISTA_INF.DATRGE AS DTEXPED_AVAL,\n"
                + "       AVALISTA.INTNET AS EMAIL_AVAL,\n"
                + "       AVALISTA.FONCLI AS FONE_AVAL,\n"
                + "       AVALISTA_INF.DATNAS AS DTNASC_AVAL,\n"
                + "       AVALISTA_INF.NOMPAI AS FILPAI_AVAL,\n"
                + "       AVALISTA_INF.NOMMAE AS FILMAE_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ENDCLI AS ENDERECO_AVAL,\n"
                + "       AVALISTA.NENCLI AS NUMERO_END_AVAL,\n"
                + "       AVALISTA.BAICLI AS BAIRRO_AVAL,\n"
                + "       AVALISTA.CPLEND AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDCLI AS CIDADE_AVAL,\n"
                + "       E008CEP_AVAL.CODIBG AS CODI_IBGE_AVAL,\n"
                + "       AVALISTA.SIGUFS AS UF_AVAL,\n"
                + "       AVALISTA.CEPCLI AS CEP_AVAL,\n"
                + "       AVALISTA.USU_NEGCLI AS NEGATIVADO_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA_INF.ESTCIV AS ID_ESTCIVILAVAL,\n"
                + "       'I' AS TIPO_ACAO\n"
                + "  FROM SAPIENS.E301TCR\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "   AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "   AND E301MCR.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = E301TCR.CODCLI\n"
                + "  LEFT OUTER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = E301TCR.CODFIL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS CLIEND\n"
                + "    ON CLIEND.CODCLI = CLIENTE.CODCLI\n"
                + "   AND CLIEND.LOJA = FILIAL.ID_FILIAL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES_ENDERECOS AVAEND\n"
                + "    ON AVAEND.CODCLI = E301TCR.USU_CODAVA\n"
                + "   AND AVAEND.LOJA = FILIAL.ID_FILIAL\n"
                + "   AND E301TCR.USU_CODAVA > 0\n"
                + "   AND E301TCR.USU_CODAVA IS NOT NULL\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES CLI\n"
                + "    ON CLI.ID_REGISTRO = CLIEND.CLIENTE\n"
                + "  LEFT OUTER JOIN INTEGRACAOFL.CLIENTES AVA\n"
                + "    ON AVA.ID_REGISTRO = AVAEND.CLIENTE\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = E301TCR.USU_CODAVA\n"
                + "   AND (E301TCR.USU_NEGAVA <> 'N' OR E301TCR.USU_NEGAVA IS NULL)\n"
                + "   AND AVA.SITUACAO NOT IN ('08', '09', '12', '13')\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS CLIENTE_INF\n"
                + "    ON CLIENTE_INF.CODCLI = CLIENTE.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085FIS AVALISTA_INF\n"
                + "    ON AVALISTA_INF.CODCLI = AVALISTA.CODCLI\n"
                + "  LEFT JOIN SAPIENS.E008CEP\n"
                + "    ON E008CEP.CEPINI = CLIENTE.CEPINI\n"
                + "   AND E008CEP.CEPFIM >= CLIENTE.CEPCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E008CEP E008CEP_AVAL\n"
                + "    ON E008CEP_AVAL.CEPINI = AVALISTA.CEPINI\n"
                + "   AND E008CEP_AVAL.CEPFIM >= AVALISTA.CEPCLI \n";
        return sqlExtracaoFloInclusao;
    }

    public String getSqlWhereAutomaticoFloI() {
        String sqlWhereAutomaticoFloI = ""
                + "WHERE (E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "           FROM SAPIENS.E301MCR\n"
                + "          WHERE E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "            AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "            AND E301MCR.CODFIL = E301TCR.CODFIL) AND\n"
                + "       FILIAL.DATA_INICIO_DBNOVO > '31/12/1900' AND E301TCR.SITTIT = 'AB' AND\n"
                + "       CLI.SITUACAO NOT IN ('08', '09', '12', '13')       \n"
                + "       AND E301TCR.VLRABE >= '25,00' AND E301TCR.USU_CODCTR IS NOT NULL AND\n"
                + "       (USU_DATBAI IS NULL OR USU_DATBAI = '31/12/1900') AND\n"
                + "       (E301TCR.VCTORI BETWEEN SYSDATE - 50 AND SYSDATE - 30 AND\n"
                + "       (E301TCR.USU_DATNEG = '31/12/1900' OR (E301TCR.USU_DATNEG IS NULL))))\n"
                + "   AND FILIAL.ATIVO = 'A'\n"
                + " AND (E301TCR.ULTPGT = '31/12/1900' OR (E301TCR.ULTPGT > '31/12/1900' AND E301TCR.ULTPGT < E301TCR.VCTORI + 29))";
        return sqlWhereAutomaticoFloI;
    }

    public String getSqlWhereAutomaticoFloB() {
        String sqlWhereAutomaticoFloB = ""
                + "WHERE ((((E301TCR.ULTPGT BETWEEN SYSDATE - 6 AND SYSDATE) OR\n"
                + "       (E301MCR.DATPGT BETWEEN SYSDATE - 6 AND SYSDATE AND\n"
                + "       E301MCR.TPTRLC = '012')) AND\n"
                + "       (USU_DATNEG <> '31/12/1900' AND USU_DATNEG IS NOT NULL AND\n"
                + "       (USU_DATBAI = '31/12/1900' OR USU_DATBAI IS NULL) OR\n"
                + "       (USU_DATNEG <> '31/12/1900' AND USU_DATNEG IS NOT NULL AND\n"
                + "       USU_DATBAI < USU_DATNEG)) ) AND\n"
                + "       E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "           FROM SAPIENS.E301MCR\n"
                + "          WHERE E301MCR.NUMTIT = E301TCR.NUMTIT\n"
                + "            AND E301MCR.CODEMP = E301TCR.CODEMP\n"
                + "            AND E301MCR.CODFIL = E301TCR.CODFIL))\n"
                + "   AND FILIAL.DATA_INICIO_DBNOVO > '31/12/1900'\n";
        /*OR\n"
                + "       (EXISTS (SELECT 1\n"
                + "                    FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                   WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                     AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                     AND LPAD(PARCELA.NUMERO_DOC, 12, '0') =\n"
                + "                         LPAD(E301TCR.USU_CODCTR, 12, '0')\n"
                + "                     AND FILIAIS.FILIAL_SGL = FILIAL.FILIAL_SGL\n"
                + "                     AND (E301TCR.USU_DATNEG = '31/12/1900' OR\n"
                + "                         E301TCR.USU_DATNEG IS NULL)\n"
                + "                     AND ((E301TCR.ULTPGT BETWEEN SYSDATE - 6 AND SYSDATE) OR\n"
                + "                         (E301MCR.DATPGT BETWEEN SYSDATE - 6 AND SYSDATE AND\n"
                + "                         E301MCR.TPTRLC = '012'))\n"
                + "                     AND (E301TCR.USU_DATBAI = '31/12/1900' OR\n"
                + "                         E301TCR.USU_DATBAI IS NULL)))*/
        return sqlWhereAutomaticoFloB;
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

    public String getSqlWhereAutomaticoI() {

        String sqlWhereAutomaticoI = " WHERE ((EXISTS (SELECT 1\n"
                + "                   FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                  WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                    AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                    AND PARCELA.NUMERO_DOC = MR02.DOC\n"
                + "                    AND FILIAIS.FILIAL_SGL = MR02.PTO\n"
                + "                    AND EXTRATOR.STATUS IN ('EP', 'I')))\n"
                + "   OR (\n"
                + "       NEGATIVADA = '31/12/1900'\n"
                + "   AND MR02.SIT = 'N'\n"
                + "   AND MR02.VENC BETWEEN SYSDATE - 45 AND SYSDATE - 30\n"
                + "   AND CLIENTE.SIT NOT IN ('08', '09', '12', '13')\n"
                + "   AND MR02.VALOR >= '25,00'))  \n"
                + "   AND FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' \n"
                + "   AND FILIAIS.ATIVO = 'A'\n";

        return sqlWhereAutomaticoI;
    }

    public String getSqlWhereAutomaticoB() {

        String sqlWhereAutomaticoB = "   WHERE (MR02.SIT = 'P' AND MR02.DATAALT BETWEEN SYSDATE - 7 AND SYSDATE AND\n"
                + "       MR02.TPGTO IN ('R', 'N') AND MR02.BAIXANEG = '31/12/1900' OR\n"
                + "       (EXISTS (SELECT 1\n"
                + "                   FROM PARCELA, FILIAIS, EXTRATOR\n"
                + "                  WHERE PARCELA.ID_FILIAL = FILIAIS.ID_FILIAL\n"
                + "                    AND PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "                    AND PARCELA.NUMERO_DOC = MR02.DOC\n"
                + "                    AND FILIAIS.FILIAL_SGL = MR02.PTO\n"
                + "                    AND EXTRATOR.STATUS = 'P'\n"
                + "                    AND NEGATIVADA = '31/12/1900'\n"
                + "                    AND MR02.TPGTO <> 'T'\n"
                + "                    AND MR02.SIT || MR02.TPGTO <> 'PQ'\n"
                + "                    AND MR02.DATAPAG BETWEEN SYSDATE - 5 AND SYSDATE\n"
                + "                    AND MR02.BAIXANEG = '31/12/1900')) OR\n"
                + "       (MR02.TPGTO <> 'T' AND MR02.SIT || MR02.TPGTO <> 'PQ' AND\n"
                + "       MR02.DATAPAG BETWEEN SYSDATE - 5 AND SYSDATE AND\n"
                + "       MR02.NEGATIVADA <> '31/12/1900' AND MR02.BAIXANEG = '31/12/1900')) \n"
                + "        AND FILIAIS.DATA_INICIO_DBNOVO = '31/12/1900' ";

        return sqlWhereAutomaticoB;
    }

    public String getSqlWhereFilial() {
        String sqlWhereFilial = " AND MR02.PTO = ? \n";
        return sqlWhereFilial;
    }

    public String getSqlWhereVerificaParcelas() {
        String sqlWhereVerificaParcelas = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') = ?\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?\n";
        return sqlWhereVerificaParcelas;
    }

    public String getSqlWhereConsultaParcelaExtrator() {
        String sqlWhereVerificaParcelas = " WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') = ?\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n";
        return sqlWhereVerificaParcelas;
    }

    public String getSqlWhereParcelaSemRetorno() {
        String sqlWhereParcelaSemRetorno = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n"
                + "   AND EXTRATOR.STATUS = ?\n"
                + "   AND EXTRATOR.DATA_RETORNO = '31/12/1900'";

        return sqlWhereParcelaSemRetorno;
    }

    public String getSqlWhereParcelaRetornada() {
        String sqlWhereParcelaRetornada = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?\n"
                + "   AND EXTRATOR.TIPO = ?\n"
                + "   AND EXTRATOR.STATUS = ?\n"
                + "   AND EXTRATOR.DATA_RETORNO <> '31/12/1900'";
        return sqlWhereParcelaRetornada;
    }

    public String getSqlWhereManualExtracao() {
        String sqlWhereManualExtracao = " WHERE MR02.PTO = ?\n"
                + "   AND MR02.DOC = ?"
                + " AND MR02.CODIGO = ?";

        return sqlWhereManualExtracao;
    }

    public String getSqlWhereManualExtracaoE301TCR() {
        String sqlWhereManualExtracaoE301TCR = " WHERE E301TCR.CODFIL = ?\n"
                + "   AND E301TCR.CODCLI = ?\n"
                + "   AND E301TCR.USU_CODCTR = ?\n"
                + "   AND E301TCR.USU_NUMDOC = ?";

        return sqlWhereManualExtracaoE301TCR;
    }

    public String getOrderByParcelaExtrator() {

        return " ORDER BY ID_EXTRATOR DESC ";
    }

    public String getSqlWhereVerificaInclusaoManual() {
        String sqlWhereVerificaInclusaoManual = ""
                + " WHERE EXTRATOR.TIPO IN ('I', 'B')\n"
                + "   AND EXTRATOR.STATUS NOT IN ('F', 'FE')\n"
                + "   AND PARCELA.ID_FILIAL = ?\n"
                + "   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') = ?\n"
                + "   AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?\n";
        return sqlWhereVerificaInclusaoManual;

    }

    public String getSqlVerificaInclusaoManualPasso2() {
        String sqlVerificaInclusaoManualPasso2 = "SELECT *\n"
                + "  FROM PARCELA, EXTRATOR\n"
                + " WHERE PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "   AND EXTRATOR.ID_EXTRATOR =\n"
                + "       (SELECT MAX(ID_EXTRATOR)\n"
                + "          FROM PARCELA \n "
                + "         INNER JOIN PESSOA \n"
                + "            ON PESSOA.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "         INNER JOIN EXTRATOR \n"
                + "            ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "         WHERE PARCELA.ID_PARCELA = EXTRATOR.ID_PARCELA\n"
                + "           AND PARCELA.ID_FILIAL = ?\n"
                + "   AND LPAD(PARCELA.NUMERO_DOC, 12, '0') = ?\n"
                + "           AND TO_NUMBER(PESSOA.CPF_RAZAO) = ?)";
        return sqlVerificaInclusaoManualPasso2;

    }

    public String getSqlVerificaRecebimento() {
        String sqlRecebimento = "SELECT DATA_RECEBIMENTO\n"
                + "          FROM INTEGRACAO_RECEBIMENTO.RECEBIMENTOS_PARCELAS RP,\n"
                + "               INTEGRACAO_RECEBIMENTO.RECEBIMENTOS          R\n"
                + "         WHERE R.SITUACAO_RECEBIMENTO = 'R'\n"
                + "           AND R.ID_RECEBIMENTO = RP.RECEBIMENTO\n"
                + "           AND RP.ID_CARNE = ?\n"
                + "           AND RP.ID_FILIAL = ? \n";
        return sqlRecebimento;
    }

    public ExtracaoDAO() {
        retornoTryCath = "";
        sqlUpdateParcelasErro = ("UPDATE PARCELA SET ID_FILIAL = ?, DATALAN = ?, VENC = ?, VALOR = ?, DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?,"
                + " JUROS_PAG = ?, DATA_NEGATIVADA = ?, DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, NUMPAR = ?, TIPOPAG = ?, NUMTIT = ?, USU_IDETCR = ?, CODTPT = ?"
                + " WHERE ID_PARCELA = ?");

        sqlUpdateParcelas = ("UPDATE PARCELA SET DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?, JUROS_PAG = ?, DATA_NEGATIVADA = ?,"
                + " DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, TIPOPAG = ?, OBSERVACAO = OBSERVACAO || ?, NUMTIT = ?, USU_IDETCR = ?, CODTPT = ?"
                + " WHERE ID_PARCELA = ?");

        sqlUpdatePessoas = ("UPDATE PESSOA SET NOME = ?, TIPO_PESSOA = ?, CPF_RAZAO = ?, CPF_ORIGEM = ?, NOME_PAI = ?, NOME_MAE = ?,"
                + " NUM_RG = ?, DATE_EXPED = ?, ORGAO_EXPED = ?, EMAIL = ?, TELEFONE = ?, DATE_NASC = ?, EST_CIVIL = ?, ID_ESTCIVIL = ?, ENDERECO = ?, ID_LOGRADOURO = ?,"
                + " NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, CODIGO_IBGE = ?, CEP = ?, UF_ESTADO = ?, TIPO_DEVEDOR = ?, SITUACAO = ?, ID_FILIAL = ? WHERE ID_PESSOA = ?");

        sqlUpdateExtrator = ("UPDATE EXTRATOR SET STATUS = ? WHERE ID_EXTRATOR = ?");

        sqlVerificaExtracao = "SELECT *FROM EXTRATOR, PARCELAS WHERE EXTRATOR.ID_PARCELAS = PARCELAS.ID_PARCELA  AND CODIGO = '12345'  AND TIPO = 'I' AND STATUS <> 'F'";

        sqlInsertLogExtracao = "INSERT INTO LOG_EXTRACAO (ID_LOG_EXTRACAO, ID_EXTRATOR, NUM_DOC, VENCIMENTO, CLIENTE, PONTO, MENSAGEM, DATA_EXTRACAO, STATUS, ORIGEM, CGCCPF)"
                + "VALUES (ID_LOG_EXTRACAO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertCliente = "INSERT INTO PESSOA (ID_PESSOA, NOME, TIPO_PESSOA, CPF_RAZAO, CPF_ORIGEM, NOME_PAI, NOME_MAE,"
                + " NUM_RG, DATE_EXPED, ORGAO_EXPED, EMAIL, TELEFONE, DATE_NASC, EST_CIVIL, ID_ESTCIVIL, ENDERECO, ID_LOGRADOURO,"
                + " NUMERO, COMPLEMENTO, BAIRRO, CIDADE, CODIGO_IBGE, CEP, UF_ESTADO, TIPO_DEVEDOR, SITUACAO, ID_FILIAL, SEXO)"
                + " VALUES (ID_LOG_PESSOA.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertParcelas = "INSERT INTO PARCELA (ID_PARCELA, ID_FILIAL, CODIGO, NUMERO_DOC, DATALAN, VENC, VALOR, DATAPAG,"
                + " CAPITPAG, SIT, DATA_NEGATIVADA, DATA_BAIXA, TAXA, JUROS, VALOR_CALC, VALOR_PAG, JUROS_PAG, DATAALT,"
                + " NUMPAR, TIPOPAG, DATA_EXTRACAO, ID_CLIENTE, ID_AVALISTA, CLIENTE, AVALISTA, PRESCRITO, INCLUIR_AVAL, ORIGEM_PROVEDOR, OBSERVACAO, NUMTIT, USU_IDETCR, CODTPT)"
                + " VALUES (ID_LOG_PARCELA.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        sqlInsertLog = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, ORIGEM, STATUS)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";

        sqlInsertExtrator = "INSERT INTO EXTRATOR(ID_EXTRATOR, TIPO, STATUS, DATA_SPC, ID_PARCELA, DATA_SPC_AVALISTA, DATA_FACMAT, ORIGEM, RETORNO, DATA_RETORNO, DATA_EXTRACAO,"
                + " ORIGEM_DB, ID_NATUREZA_INC_BVS, ID_NATUREZA_INC_SPC, ID_MOTIVO_EXCLUSAO_SPC, ID_MOTIVO_EXCLUSAO_BVS, ID_MOTIVO_INC_BVS, BLOQUEAR_REGISTRO, STATUS_SPC, STATUS_FACMAT, STATUS_SPC_AVAL)"
                + " VALUES (ID_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    }

    public void realizarExtracao(List<ExtracaoModel> lExtracaoModel, String retornoExtracao) throws ErroSistemaException {
        try {
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            contregistros = 0;
            for (ExtracaoModel extracaoModel : lExtracaoModel) {
                if (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getBdOrigem().equals("SGL")) {
                    psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + getSqlWhereManualExtracao());
                    psExtracao.setString(1, extracaoModel.getPontoFilial());
                    psExtracao.setString(2, extracaoModel.getNumeroDoContrato());
                    psExtracao.setString(3, extracaoModel.getCodigoClientParcela());
                    System.out.println(" cliente " + getSqlSelectExtracao() + getSqlWhereManualExtracao());

                } else if (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getBdOrigem().equals("NOVO_SGL")) {
                    if (extracaoModel.getTipoParcela().equalsIgnoreCase("B")) {
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlExtracaoFloBaixaManual()+ getSqlWhereManualExtracaoE301TCR());
                    } else {
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlExtracaoFloInclusao() + getSqlWhereManualExtracaoE301TCR());
                    }
                    psExtracao.setInt(1, extracaoModel.getCodfil());
                    psExtracao.setString(2, extracaoModel.getCodCliente());
                    psExtracao.setString(3, extracaoModel.getNumeroDoContrato());
                    psExtracao.setString(4, extracaoModel.getCodigoClientParcela());
                } else if (extracaoModel.getOrigemRegistro().equals("A") && extracaoModel.getBdOrigem().equals("SGL")) {
                    if (extracaoModel.getPontoFilial().equals("")) {
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + getSqlWhereAutomaticoI()
                                + "Union All \n" + getSqlSelectExtracao() + getSqlWhereAutomaticoB() + " \n order by filial desc");
                        //  psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + getSqlWhereAutomaticoI());
                    } else {
                        filial = extracaoModel.getPontoFilial();
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlSelectExtracao() + getSqlWhereAutomaticoI()
                                + "AND MR02.PTO = " + filial + " \n Union All \n" + getSqlSelectExtracao() + getSqlWhereAutomaticoB() + "AND MR02.PTO = " + filial + " \n");
                    }

                } else if (extracaoModel.getOrigemRegistro().equals("A") && extracaoModel.getBdOrigem().equals("NOVO_SGL")) {
                    if (extracaoModel.getPontoFilial().equals("")) {
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlExtracaoFloInclusao() + getSqlWhereAutomaticoFloI()
                                + "UNION ALL \n" + getSqlExtracaoFloBaixa()  + "UNION ALL \n" + getSqlSelectExtracaoFloIErro());
                        //   psExtracao = Conexao.getConnection().prepareStatement(getSqlExtracaoFloInclusao() + getSqlWhereAutomaticoFloI() + "UNION ALL \n" + getSqlSelectExtracaoFloIErro());
                    } else {
                        filial = extracaoModel.getPontoFilial();
                        psExtracao = Conexao.getConnection().prepareStatement(getSqlExtracaoFloInclusao() + getSqlWhereAutomaticoFloI()
                                + "AND FILIAL.FILIAL_SGL = " + filial + " \n UNION ALL \n" + getSqlExtracaoFloBaixa()
                               + "AND FILIAL.USU_CODPTO = " + filial + "\n UNION ALL \n" + getSqlSelectExtracaoFloIErro() + "AND FILIAL.FILIAL_SGL = " + filial + " \n");
                    }

//                               
                }
                System.out.println("MR02: " + getSqlSelectExtracao() + getSqlWhereAutomaticoI()
                                + "Union All \n" + getSqlSelectExtracao() + getSqlWhereAutomaticoB() + " \n order by filial desc");

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
                psVerificaParcelaBaixa = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelas());
                psParcelasSemRetorno = connection.prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelaSemRetorno());
                psLogExtracao = connection.prepareStatement(sqlInsertLogExtracao);
                psParcelasRetornada = connection.prepareStatement(getSqlSelectParcelas() + getSqlWhereParcelaRetornada());

                /**
                 * Percorre os dados da extração, atualiza se existir, insere se
                 * for novo.
                 */
                rsExtracao = psExtracao.executeQuery();
                while (rsExtracao.next()) {
                    idAvalista = 0;
                    idCliente = 0;
                    idExtrator = 0;
                    contregistros++;

                    try {
                        /**
                         * Verifica se a parcela já existe no extrator. Caso não
                         * exista e o tipo de registro seja Inclusão será feito
                         * o insert do registro. Caso o registro exista sera
                         * feito verificações para tomada de decisão.
                         */

                        psVerificaParcela = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereVerificaParcelas() + getOrderByParcelaExtrator());
                        psVerificaParcela.setString(1, rsExtracao.getString("FILIAL_FLO"));
                        psVerificaParcela.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                        psVerificaParcela.setString(3, rsExtracao.getString("CGCCPF"));
                        System.out.println("Filial =" + rsExtracao.getString("FILIAL_FLO") + "  Doc = " + Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12) + "  cpf = " + rsExtracao.getString("CGCCPF"));
                        rsVerificaParcela = psVerificaParcela.executeQuery();

                        /* Verifiar se a Parcela Existe no BD pegando apenas a ultima linha. */
                        if (rsVerificaParcela.next()) {

                            /* Verifica se é inclusão pelo automatico ou inclusão pelo manual. O manual pode não vir com tipo ação correto. */
                            if ((extracaoModel.getOrigemRegistro().equals("A") && rsExtracao.getString("TIPO_ACAO").equals("I"))
                                    || (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getTipoParcela().equals("I"))) {

                                psConsultaTipoParcelaExtrator = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereConsultaParcelaExtrator() + getOrderByParcelaExtrator());
                                psConsultaTipoParcelaExtrator.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                psConsultaTipoParcelaExtrator.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                psConsultaTipoParcelaExtrator.setString(3, rsExtracao.getString("CGCCPF"));
                                psConsultaTipoParcelaExtrator.setString(4, "I");

                                rsConsultaTipoParcelaExtrator = psConsultaTipoParcelaExtrator.executeQuery();

                                if (rsConsultaTipoParcelaExtrator.next()) {
                                    if (extracaoModel.getOrigemRegistro().equals("M")) {
                                        /**
                                         * Se a origem for manual valida se
                                         * existe algum registro com pendencia.
                                         * Não é possivel fazer o insert se a
                                         * parcela possuir processos não
                                         * finalizados.
                                         */
                                        psVerificaInclusoes = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereVerificaInclusaoManual());
                                        psVerificaInclusoes.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                        psVerificaInclusoes.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                        psVerificaInclusoes.setString(3, rsExtracao.getString("CGCCPF"));
                                        rsVerificaInclusoes = psVerificaInclusoes.executeQuery();
                                        if (rsVerificaInclusoes.next()) {
                                            throw new ErroSistemaException("Não foi possível fazer nova inclusão\n pois existe registro anterior com status diferente de finalizado.\n Verifique os registros anteriores antes de nova inclusão.");
                                        } else {
                                            psVerificaInclusoes.close();
                                            rsVerificaInclusoes.close();
                                            /**
                                             * Valida se o último processo
                                             * inserido da parcela é de baixa e
                                             * valida novamente se seu status é
                                             * finalizado. Caso seja verdadeiro
                                             * faz o insert da nova inclusão.
                                             * caso contrario lança uma exeção.
                                             */
                                            psVerificaInclusoes = Conexao.getConnection().prepareStatement(getSqlVerificaInclusaoManualPasso2());
                                            psVerificaInclusoes.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                            psVerificaInclusoes.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                            psVerificaInclusoes.setString(3, rsExtracao.getString("CGCCPF"));
                                            rsVerificaInclusoes = psVerificaInclusoes.executeQuery();

                                            if (rsVerificaInclusoes.next()) {
                                                if ((rsVerificaInclusoes.getString("TIPO").equals("B") && (rsVerificaInclusoes.getString("STATUS").equals("F"))
                                                        || rsVerificaInclusoes.getString("STATUS").equals("FE"))) {
                                                    insertRegistroInclusao(extracaoModel);
                                                } else {
                                                    throw new ErroSistemaException("Não foi possível fazer nova inclusão\n pois já existe um registro anterior de inclusao ativo, sem ter sido feito uma baixa.\n Para realizar novas inclusões de um mesmo registro é necessario \n que o seu ciclo anterior tenha terminado com uma inclusão e uma baixa.\n Verifique os registros anteriores antes de nova inclusão.");
                                                }
                                            }
                                        }
                                        psVerificaInclusoes.close();
                                        rsVerificaInclusoes.close();
                                    } else {

                                        if (rsConsultaTipoParcelaExtrator.getString("STATUS").equals("P") || rsConsultaTipoParcelaExtrator.getString("STATUS").equals("EP") || rsConsultaTipoParcelaExtrator.getString("STATUS").equals("I")) {
                                            updateRegistro(extracaoModel, retornoExtracao, rsVerificaParcela.getInt("ID_PARCELA"), rsConsultaTipoParcelaExtrator.getInt("ID_EXTRATOR"), rsVerificaParcela.getInt("ID_CLIENTE"), rsVerificaParcela.getInt("ID_AVALISTA"));
                                        }

                                        /* Verificar se a parcela está PR */
                                        if (rsConsultaTipoParcelaExtrator.getString("STATUS").equals("PR")) {
                                            logExtracao = "Inclusão de Parcela com Status Processado, aguardando retorno. Não será extraida.";
                                            insertLogExtracao(rsConsultaTipoParcelaExtrator.getInt("ID_EXTRATOR"), logExtracao, "E");
                                        }

                                        /* Verificar se a parcela está processada Parcial, significa que foi enviado apenas para um provedor e ainda não possuí Erro. */
                                        if (rsConsultaTipoParcelaExtrator.getString("STATUS").equals("PP")) {
                                            logExtracao = "Inclusão de Parcela com Status Processado Parcial, aguardando processamento em todos os provedores. Não será extraida.";
                                            insertLogExtracao(rsConsultaTipoParcelaExtrator.getInt("ID_EXTRATOR"), logExtracao, "E");
                                        }

                                        /* Se finalizado sem envio. Não enviar novamente. */
                                        if (rsConsultaTipoParcelaExtrator.getString("STATUS").equals("FE")) {
                                            logExtracao = "Inclusão de Parcela com Status Finalizado sem Envio, isso ocorre pois a parcela foi paga antes de negativar nos provedores. Não será extraida.";
                                            insertLogExtracao(rsConsultaTipoParcelaExtrator.getInt("ID_EXTRATOR"), logExtracao, "E");
                                        }

                                        /* Verificar se a parcela já possuí uma inclusão do tipo inclusão já finalizada, se sim, gera log e não incluí.*/
                                        if (rsConsultaTipoParcelaExtrator.getString("STATUS").equals("F")) {
                                            logExtracao = "Inclusão de Parcela com Status Finalizado, uma nova extração só é permitida de forma manual. Não será extraida.";
                                            insertLogExtracao(rsConsultaTipoParcelaExtrator.getInt("ID_EXTRATOR"), logExtracao, "E");
                                        }
                                    }
                                }

                                rsConsultaTipoParcelaExtrator.close();
                                psConsultaTipoParcelaExtrator.close();

                            } else if ((extracaoModel.getOrigemRegistro().equals("A") && rsExtracao.getString("TIPO_ACAO").equals("B"))
                                    || (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getTipoParcela().equals("B"))) {
                                /* Registro de Baixa.*/
 /*  psConsultaTipoParcelaExtrator = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereConsultaParcelaExtrator() + getOrderByParcelaExtrator());
                                psConsultaTipoParcelaExtrator.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                psConsultaTipoParcelaExtrator.setString(2, rsExtracao.getString("DOC"));
                                psConsultaTipoParcelaExtrator.setString(3, rsExtracao.getString("CGCCPF"));
                                psConsultaTipoParcelaExtrator.setString(4, "B");

                                rsConsultaTipoParcelaExtrator = psConsultaTipoParcelaExtrator.executeQuery();
                                System.out.println("query" + getSqlSelectParcelas() + getSqlWhereConsultaParcelaExtrator() + getOrderByParcelaExtrator());
                                System.out.println(rsExtracao.getString("DOC"));
                                System.out.println(rsExtracao.getString("CGCCPF"));
                                System.out.println(rsExtracao.getString("FILIAL_FLO"));

                                if (rsConsultaTipoParcelaExtrator.next() && (extracaoModel.getOrigemRegistro().equals("A") && rsExtracao.getString("TIPO_ACAO").equals("B"))) {*/
                                if (rsVerificaParcela.getString("TIPO").equalsIgnoreCase("B")) {
                                    /* Verificar se está com erro de processamento no envio ou pendênte ou inválida atualiza a parcela. */
                                    if (rsVerificaParcela.getString("STATUS").equals("P") || rsVerificaParcela.getString("STATUS").equals("EP") || rsVerificaParcela.getString("STATUS").equals("I")) {
                                        logExtracao = "Baixa da Parcela, com situação Pendente/Erro/Inválida. Não será extraida.";
                                        insertLogExtracao(rsVerificaParcela.getInt("ID_EXTRATOR"), logExtracao, "E");
                                    }
                                    /* Verificar se a parcela está PR */
                                    if (rsVerificaParcela.getString("STATUS").equals("PR")) {
                                        logExtracao = "Baixa da Parcela, com situação Pendente/Erro/Inválida. Não será extraida.";
                                        insertLogExtracao(rsVerificaParcela.getInt("ID_EXTRATOR"), logExtracao, "E");
                                    }

                                    /* Verificar se a parcela está processada Parcial, significa que foi enviado apenas para um provedor e ainda não possuí Erro. */
                                    if (rsVerificaParcela.getString("STATUS").equals("PP")) {
                                        logExtracao = "Baixa da Parcela com Status Processado Parcial, aguardando processamento em todos os provedores. Não será extraida.";
                                        insertLogExtracao(rsVerificaParcela.getInt("ID_EXTRATOR"), logExtracao, "E");
                                    }

                                    /* Se finalizado sem envio. Apenas gera no Log e não faz nada. */
                                    if (rsVerificaParcela.getString("STATUS").equals("FE")) {
                                        logExtracao = "Baixa de Parcela com Status Finalizado sem Envio, isso ocorre pois a parcela foi paga antes de negativar nos provedores. Não será extraida.";
                                        insertLogExtracao(rsVerificaParcela.getInt("ID_EXTRATOR"), logExtracao, "E");
                                    }

                                    /* Verificar se a parcela já possuí uma baixa do tipo baixa já finalizada, se sim, gera log e não incluí.*/
                                    if (rsVerificaParcela.getString("STATUS").equals("F")) {
                                        logExtracao = "Baixa de Parcela com Status Finalizado, uma nova baixa só é permitida de forma manual. Não será extraida.";
                                        insertLogExtracao(rsVerificaParcela.getInt("ID_EXTRATOR"), logExtracao, "E");
                                    }
                                } else {

                                    /**
                                     * Se a inclusão não tiver sido realizada
                                     * nos provedores(Em nenhum), finaliza a
                                     * inclusão. - Gravar no Log. - Realizar
                                     * update na linha da extração da
                                     * inclusão.(Finalizado sem envio, FE) - Não
                                     * realiza a inclusão da baixa.
                                     */

                                    /* Baixa não Existe, logo deverá inserir. */
                                    psConsultaParcelaInclusao = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereConsultaParcelaExtrator() + getOrderByParcelaExtrator());
                                    psConsultaParcelaInclusao.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                    psConsultaParcelaInclusao.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                    psConsultaParcelaInclusao.setString(3, rsExtracao.getString("CGCCPF"));
                                    psConsultaParcelaInclusao.setString(4, "I");

                                    rsConsultaParcelaInclusao = psConsultaParcelaInclusao.executeQuery();

                                    /* Verifica se Existe uma Inclusão. */
                                    if (rsConsultaParcelaInclusao.next()) {
                                        if (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getTipoParcela().equals("B")) {
                                            /**
                                             * Se a origem for manual valida se
                                             * existe algum registro com
                                             * pendencia. Não é possivel fazer o
                                             * insert se a parcela possuir
                                             * processos não finalizados.
                                             */
                                            psVerificaInclusoes = Conexao.getConnection().prepareStatement(getSqlSelectParcelas() + getSqlWhereVerificaInclusaoManual());
                                            psVerificaInclusoes.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                            psVerificaInclusoes.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                            psVerificaInclusoes.setString(3, rsExtracao.getString("CGCCPF"));
                                            rsVerificaInclusoes = psVerificaInclusoes.executeQuery();
                                            if (rsVerificaInclusoes.next()) {
                                                throw new ErroSistemaException("Não foi possível fazer nova inclusão de registro de baixa \n poís existe registro anteriore com status diferente de finalizado.\n Verifique os registros anteriores antes de nova inclusão do registro de baixa.");
                                            } else {
                                                psVerificaInclusoes.close();
                                                rsVerificaInclusoes.close();
                                                /**
                                                 * Valida se o último processo
                                                 * inserido da parcela é de
                                                 * baixa e valida novamente se
                                                 * seu status é finalizado. Caso
                                                 * seja verdadeiro faz o insert
                                                 * da nova inclusão. caso
                                                 * contrario lança uma exeção.
                                                 */
                                                psVerificaInclusoes = Conexao.getConnection().prepareStatement(getSqlVerificaInclusaoManualPasso2());
                                                psVerificaInclusoes.setString(1, rsExtracao.getString("FILIAL_FLO"));
                                                psVerificaInclusoes.setString(2, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
                                                psVerificaInclusoes.setString(3, rsExtracao.getString("CGCCPF"));
                                                rsVerificaInclusoes = psVerificaInclusoes.executeQuery();

                                                if (rsVerificaInclusoes.next()) {
                                                    if ((rsVerificaInclusoes.getString("TIPO").equals("I")) && (rsVerificaInclusoes.getString("STATUS").equals("F") || rsVerificaInclusoes.getString("STATUS").equals("FE"))) {
                                                        insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "P", "P", "P", "P", connection);
                                                    } else {
                                                        throw new ErroSistemaException("Não foi possível fazer nova inclusão de registro de baixa\n poís já existe um registro anterior de baixa ativo ou não existe um registro de inclusão.\n Para inserir um registro de baixa é necessario que haja um registro de inclusão com status F ou FE.\n Verifique o registro anterior antes de inserir nova baixa.");
                                                    }
                                                }
                                            }
                                            psVerificaInclusoes.close();
                                            rsVerificaInclusoes.close();

                                        } else {

//                                    if ((rsConsultaParcelaInclusao.getString("TIPO").equals("I") && rsConsultaParcelaInclusao.getString("STATUS").equals("F"))
//                                            || rsConsultaParcelaInclusao.getString("STATUS").equals("FE")) {
                                            avalista = rsConsultaParcelaInclusao.getInt("ID_AVALISTA");
                                            String status = rsConsultaParcelaInclusao.getString("STATUS");
                                            String statusSpc = rsConsultaParcelaInclusao.getString("STATUS_SPC");
                                            String statusSpcAval = rsConsultaParcelaInclusao.getString("STATUS_SPC_AVAL");
                                            String statusFacmat = rsConsultaParcelaInclusao.getString("STATUS_FACMAT");

                                            /* Se a inclusção é finalizada, então a baixa deverá ser processada por completa. */
                                            if (rsConsultaParcelaInclusao.getString("STATUS").equalsIgnoreCase("F")) {
                                                insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "P", "P", "P", "P", connection);
                                            } else {

                                                String statusSpcAux = "P", statusSpcAvaAux = "P", statusFacmatAux = "P";

                                                /* Verifica se possuí avalista. */
                                                if (avalista == 0) {

                                                    /* Se todos os registros da inclusão for de ERRO ou Pendente, vamos finalizar o registro com Finalizado Erro FE.  */
                                                    if ((statusSpc.equals("E") && statusFacmat.equals("E"))
                                                            || (statusSpc.equals("P") && statusFacmat.equals("P"))
                                                            || statusSpc.equals("FE") && statusFacmat.equals("FE")) {
                                                        finalizaInclusaoPendente(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                        if (rsConsultaParcelaInclusao.getString("ID_REGISTRO_BVS").trim().isEmpty()) {
                                                            insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "FE", "S", "S", "S", connection);
                                                        } else {
                                                            insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "FE", "S", "S", "P", connection);
                                                        }
                                                    } else if ((statusFacmat.equals("E") || statusSpc.equals("E") || statusFacmat.equals("FE") || statusSpc.equals("FE"))) {

                                                        /* Apenas um dos provedores foi enviado. */
                                                        if (statusFacmat.equals("E") || statusFacmat.equals("FE")) {
                                                            if (rsConsultaParcelaInclusao.getString("ID_REGISTRO_BVS").trim().isEmpty()) {
                                                                statusFacmatAux = "S";
                                                            } else {
                                                                statusFacmatAux = "P";
                                                            }
                                                            finalizaInclusaoPendenteFacmat(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                        }
                                                        if (statusSpc.equals("E") || statusSpc.equals("FE")) {
                                                            statusSpcAux = "S";
                                                            finalizaInclusaoPendenteSpc(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                        }
                                                        insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "P", statusSpcAux, statusSpcAvaAux, statusFacmatAux, connection);
                                                    }
                                                } else if (avalista != 0) {

                                                    /* Se todos os registros da inclusão for de ERRO ou Pendente, vamos finalizar o registro com Finalizado Erro FE.  */
                                                    if ((statusSpc.equals("E") && statusSpcAval.equals("E") && statusFacmat.equals("E"))
                                                            || (statusSpc.equals("P") && statusFacmat.equals("P") && statusSpcAval.equals("P"))
                                                            || (statusFacmat.equals("FE") && statusSpc.equals("FE") && statusSpcAval.equals("FE"))) {
                                                        finalizaInclusaoPendente(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                        if (rsConsultaParcelaInclusao.getString("ID_REGISTRO_BVS").trim().isEmpty()) {
                                                            insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "FE", "S", "S", "S", connection);
                                                        } else {
                                                            insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "P", "S", "S", "P", connection);
                                                        }

                                                    } else if ((statusFacmat.equals("E") || statusSpc.equals("E") || statusSpcAval.equals("E")
                                                            || statusFacmat.equals("FE") || statusSpc.equals("FE") || statusSpcAval.equals("FE"))) {
                                                        /* Apenas um dos provedores foi enviado. */
                                                        if (statusFacmat.equals("E") || statusFacmat.equals("FE")) {
                                                            if (rsConsultaParcelaInclusao.getString("ID_REGISTRO_BVS").trim().isEmpty()) {
                                                                statusFacmatAux = "S";
                                                            } else {
                                                                statusFacmatAux = "P";
                                                            }
                                                            if (statusFacmat.equals("E")) {
                                                                finalizaInclusaoPendenteFacmat(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                            }
                                                        }
                                                        if (statusSpc.equals("E") || statusSpc.equals("FE")) {
                                                            statusSpcAux = "S";
                                                            if (statusSpc.equals("E")) {
                                                                finalizaInclusaoPendenteSpc(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                            }
                                                        }
                                                        if (statusSpcAval.equals("E") || statusSpcAval.equals("FE")) {
                                                            statusSpcAvaAux = "S";
                                                            if (statusSpcAval.equals("E")) {
                                                                finalizaInclusaoPendenteSpcAval(rsConsultaParcelaInclusao.getInt("ID_EXTRATOR"));
                                                            }
                                                        }
                                                        insertBaixa(extracaoModel, rsConsultaParcelaInclusao.getInt("ID_PARCELA"), "P", statusSpcAux, statusSpcAvaAux, statusFacmatAux, connection);
                                                    }
                                                }
                                            }
                                        }
                                        rsConsultaParcelaInclusao.close();
                                        psConsultaParcelaInclusao.close();
                                    }
                                }
                                //rsConsultaTipoParcelaExtrator.close();
                                //psConsultaTipoParcelaExtrator.close();
                            }

                        } else if (rsExtracao.getString("TIPO_ACAO").equals("I") && extracaoModel.getOrigemRegistro().equals("A")) {
                            valApp = verificaRecApp(extracaoModel);
                            if (valApp == false) {
                                insertRegistroInclusao(extracaoModel);
                            }
                        } else if (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getTipoParcela().equals("I")) {
                            /* Inserção do Registro Manual */
                            insertRegistroInclusao(extracaoModel);
                        } else if (rsExtracao.getString("TIPO_ACAO").equals("B") && extracaoModel.getOrigemRegistro().equals("A")) {
                            Date dataFinal = new Date();
                            Calendar calendarData = Calendar.getInstance();
                            calendarData.setTime(dataFinal);
                            int numeroDiasParaSubtrair = 1826;
                            // achar data de início
                            calendarData.add(Calendar.DATE, numeroDiasParaSubtrair);
                            Date dataInicial = calendarData.getTime();
                            if (rsExtracao.getDate("VENCIMENTO").before(dataInicial) && rsExtracao.getDate("DATA_NEGATIVACAO").after(Utilitarios.getDataZero())) {
                                baixarPrescritoPago(extracaoModel);
                                logExtracao = "Não existe uma parcela extraída para este registro, o documento possui data de venc maior que cinco anos. Foi gerado retorno ao contas receber.";
                                statusLogExtracao = "S";
                                idExtrator = 0;
                                insertLogExtracao(0, logExtracao, statusLogExtracao);
                            } else {
                                baixarPrescritoPago(extracaoModel);
                                logExtracao = "Não existe uma parcela extraída para este registro, o documento não existe nos Provedores Verifique";
                                statusLogExtracao = "E";
                                idExtrator = 0;
                                insertLogExtracao(0, logExtracao, statusLogExtracao);
                            }
                        } else if (extracaoModel.getOrigemRegistro().equals("M") && extracaoModel.getTipoParcela().equals("B")) {
                            logExtracao = "Não existe uma parcela extraída para este registro, o documento não existe nos Provedores Verifique";
                            statusLogExtracao = "E";
                            idExtrator = 0;
                            insertLogExtracao(0, logExtracao, statusLogExtracao);
                            throw new ErroSistemaException(logExtracao);
                        }

                        rsVerificaParcela.close();
                        psVerificaParcela.close();
                        connection.commit();
                    } catch (SQLException e) {
                        connection.rollback();
                        insertLogExtracao(0, "Erro.:" + e.getMessage(), "E");
                        connection.commit();
                        resultado = psLogExtracao.executeUpdate();
                        if (resultado == -1) {
                            retornoExt = false;
                        } else {
                            retornoExt = true;
                        }
                        retornoTryCath = retornoTryCath + " \n " + e.getMessage()
                                + "FILIAL: " + rsExtracao.getString("FILIAL_FLO") + "CONTRATO:" + rsExtracao.getString("DOC") + "CLIENTE:" + rsExtracao.getString("CLIENTE");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        connection.rollback();
                        insertLogExtracao(0, "Erro.:" + e.getMessage(), "E");
                        connection.commit();
                        retornoTryCath = retornoTryCath + " \n " + e.getMessage()
                                + "FILIAL: " + rsExtracao.getString("FILIAL_FLO") + "CONTRATO:" + rsExtracao.getString("DOC") + "CLIENTE:" + rsExtracao.getString("CLIENTE");
                        //  retornoExt = false;
                    } catch (ErroSistemaException e) {
                        connection.rollback();
                        insertLogExtracao(0, "Erro.:" + e.getMessage(), "E");
                        retornoTryCath = retornoTryCath + " \n " + e.getMessage()
                                + "FILIAL: " + rsExtracao.getString("FILIAL_FLO") + "CONTRATO:" + rsExtracao.getString("DOC") + "CLIENTE:" + rsExtracao.getString("CLIENTE");
                    }
                }
            }
            updateProcesso(contregistros);
            if (!retornoTryCath.isEmpty()) {
                throw new ErroSistemaException(retornoTryCath);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
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

    public void insertRegistroInclusao(ExtracaoModel extracaoModel) throws ErroSistemaException {

        try {
            /**
             * Caso a Parcela ainda não exista no banco de Dados sera feito o
             * insert do LOG,PESSOA,PARCELA,EXTRAÇÃO
             */
            String statusExtrator = "P";
            int idRetornoExtrator = 160;
            String descricaoLogParcela = "Extração bem Sucedida.";
            String statusLogParcela = "S";
            validaNascimento();
            psPessoa.setString(1, rsExtracao.getString("NOME_CLIENTE"));
            psPessoa.setString(2, rsExtracao.getString("TIPO_PESSOA"));
            psPessoa.setString(3, rsExtracao.getString("CGCCPF"));
            if (rsExtracao.getString("CGCCPF").length() < 14 & rsExtracao.getString("TIPO_PESSOA").equalsIgnoreCase("F")) {
                String cpf = StringUtils.leftPad(rsExtracao.getString("CGCCPF"), 9, "0");
                psPessoa.setString(4, cpf.substring(8, 9));
            } else {
                psPessoa.setString(4, " ");
            }
            if (rsExtracao.getString("TIPO_PESSOA").equalsIgnoreCase("F")) {
                psPessoa.setString(5, rsExtracao.getString("FIL_PAI"));
                psPessoa.setString(6, rsExtracao.getString("FIL_MAE"));
                psPessoa.setString(7, rsExtracao.getString("NUMERO_RG"));
                psPessoa.setDate(8, rsExtracao.getDate("DATA_EXPEDICAO"));
                psPessoa.setString(9, rsExtracao.getString("EXPEDIDOR"));
                if (rsExtracao.getDate("DATA_NASCIMENTO").before(agora.getTime())) {
                    psPessoa.setDate(12, rsExtracao.getDate("DATA_NASCIMENTO"));
                } else {
                    psPessoa.setDate(12, rsExtracao.getDate("DATA_NASCIMENTO"));
                    statusExtrator = "I";
                    idRetornoExtrator = 92;
                    descricaoLogParcela = "Clinte menor de 18 anos";
                    statusLogParcela = "E";
                }
                psPessoa.setString(13, rsExtracao.getString("EST_CIVIL"));
                psPessoa.setString(14, rsExtracao.getString("ID_ESTCIVILCLI"));
            } else {
                psPessoa.setString(5, " ");
                psPessoa.setString(6, " ");
                psPessoa.setString(7, rsExtracao.getString("NUMERO_RG"));
                psPessoa.setDate(8, Utilitarios.getDataZero());
                psPessoa.setString(9, " ");
                psPessoa.setDate(12, Utilitarios.getDataZero());
                psPessoa.setString(13, " ");
                psPessoa.setString(14, "0");
            }
            psPessoa.setString(10, rsExtracao.getString("EMAIL"));
            if (rsExtracao.getString("TELEFONE").length() > 0) {
                psPessoa.setString(11, rsExtracao.getString("TELEFONE").replaceAll("[^0-9]", ""));
            } else {
                psPessoa.setString(11, " ");
            }
            psPessoa.setString(15, rsExtracao.getString("ENDERECO"));
            psPessoa.setString(16, "2");// Id_logradouro 2 Rua
            psPessoa.setString(17, rsExtracao.getString("NUMERO_END"));
            psPessoa.setString(18, rsExtracao.getString("COMPLEMENTO"));
            psPessoa.setString(19, rsExtracao.getString("BAIRRO"));
            psPessoa.setString(20, rsExtracao.getString("CIDADE"));
            if (rsExtracao.getString("CODI_IBGE").trim().length() > 0) {
                psPessoa.setString(21, rsExtracao.getString("CODI_IBGE"));
            } else {
                psPessoa.setString(21, "0");
                statusExtrator = "I";
                idRetornoExtrator = 159;
                descricaoLogParcela = "Codigo Ibge Invalido";
                statusLogParcela = "E";
            }
            if (rsExtracao.getString("CEP").trim().length() > 0) {
                if (rsExtracao.getString("CEP").trim().length() > 8) {
                    psPessoa.setString(22, "0");
                    statusExtrator = "I";
                    idRetornoExtrator = 22;
                    descricaoLogParcela = "CEP Cliente Invalido";
                    statusLogParcela = "E";
                } else {
                    psPessoa.setString(22, rsExtracao.getString("CEP").trim());
                }
            } else {
                psPessoa.setString(22, "0");
                statusExtrator = "I";
                idRetornoExtrator = 22;
                descricaoLogParcela = "CEP Cliente Invalido";
                statusLogParcela = "E";
            }

            psPessoa.setString(23, rsExtracao.getString("UF"));
            psPessoa.setString(24, "C");
            psPessoa.setString(25, rsExtracao.getString("NEGATIVADO"));
            psPessoa.setString(26, rsExtracao.getString("FILIAL_FLO"));
            if (extracaoModel.getBdOrigem().equals("NOVO_SGL")) {
                if (rsExtracao.getString("SEXO").length() > 0) {
                    psPessoa.setString(27, rsExtracao.getString("SEXO"));
                } else {
                    psPessoa.setString(27, "");
                }
            } else {
                psPessoa.setString(27, "");
            }
            psPessoa.executeUpdate();
            rsCliente = psPessoa.getGeneratedKeys();
            if (rsCliente.next()) {
                idCliente = rsCliente.getInt(1);
                rsCliente.close();
            }
            /**
             * Insere as informações referentes ao avalista, Se a parcela não
             * possuir um avalista, pula para inserção da parcela.
             */

            if ((extracaoModel.getBdOrigem().equalsIgnoreCase("SGL") && rsExtracao.getString("AVALISTA").trim().length() > 0)
                    || (extracaoModel.getBdOrigem().equalsIgnoreCase("NOVO_SGL") && rsExtracao.getInt("AVALISTA") > 0)) {
                psAvalista.setString(1, rsExtracao.getString("NOME_AVALISTA"));
                psAvalista.setString(2, rsExtracao.getString("TIPO_AVALISTA"));
                psAvalista.setString(3, rsExtracao.getString("CPF_AVALISTA"));
                if (rsExtracao.getString("CPF_AVALISTA").length() < 13 & rsExtracao.getString("TIPO_AVALISTA").equalsIgnoreCase("F")) {
                    String cpfAval = StringUtils.leftPad(rsExtracao.getString("CPF_AVALISTA"), 9, "0");
                    psAvalista.setString(4, cpfAval.substring(8, 9));
                } else {
                    psAvalista.setString(4, " ");
                }
                psAvalista.setString(10, rsExtracao.getString("EMAIL_AVAL"));
                if (rsExtracao.getString("FONE_AVAL").length() > 0) {
                    psAvalista.setString(11, rsExtracao.getString("FONE_AVAL").replaceAll("[^0-9]", ""));
                } else {
                    psAvalista.setString(11, " ");
                }
                if (rsExtracao.getString("TIPO_AVALISTA").equalsIgnoreCase("F")) {
                    psAvalista.setString(5, rsExtracao.getString("FILPAI_AVAL"));
                    psAvalista.setString(6, rsExtracao.getString("FILMAE_AVAL"));
                    psAvalista.setString(7, rsExtracao.getString("RG_AVALISTA"));
                    psAvalista.setDate(8, rsExtracao.getDate("DTEXPED_AVAL"));
                    psAvalista.setString(9, rsExtracao.getString("EXPEDIDOR_AVAL"));
                    if (rsExtracao.getDate("DTNASC_AVAL").before(agora.getTime())) {
                        psAvalista.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                    } else {
                        psAvalista.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                        statusExtrator = "I";
                        idRetornoExtrator = 151;
                        descricaoLogParcela = "Avalista menor de 18 anos";
                        statusLogParcela = "E";
                    }
                    psAvalista.setString(13, rsExtracao.getString("EST_CIVIL_AVAL"));
                    psAvalista.setString(14, rsExtracao.getString("ID_ESTCIVILAVAL"));
                } else {
                    psAvalista.setString(5, " ");
                    psAvalista.setString(6, " ");
                    psAvalista.setString(7, rsExtracao.getString("RG_AVALISTA"));
                    psAvalista.setDate(8, Utilitarios.getDataZero());
                    psAvalista.setString(9, " ");
                    psAvalista.setDate(12, Utilitarios.getDataZero());
                    psAvalista.setString(13, " ");
                    psAvalista.setString(14, "0");
                }
                psAvalista.setString(15, rsExtracao.getString("ENDERECO_AVAL"));
                psAvalista.setString(16, "2");// Id_logradouro 2 Rua
                psAvalista.setString(17, rsExtracao.getString("NUMERO_END_AVAL"));
                psAvalista.setString(18, rsExtracao.getString("COMPLEMENTO_AVAL"));
                psAvalista.setString(19, rsExtracao.getString("BAIRRO_AVAL"));
                psAvalista.setString(20, rsExtracao.getString("CIDADE_AVAL"));

                if (rsExtracao.getString("CODI_IBGE_AVAL").trim().length() > 0) {
                    psAvalista.setString(21, rsExtracao.getString("CODI_IBGE_AVAL"));
                } else {

                    psAvalista.setString(21, "0");
                    statusExtrator = "I";
                    idRetornoExtrator = 159;
                    descricaoLogParcela = "Codigo Ibge Avalista Invalido";
                    statusLogParcela = "E";
                }

                if (rsExtracao.getString("CEP_AVAL").trim().length() > 0) {
                    if (rsExtracao.getString("CEP_AVAL").trim().length() > 8) {
                        psAvalista.setString(22, "0");
                        statusExtrator = "I";
                        idRetornoExtrator = 22;
                        descricaoLogParcela = "CEP Avalista Invalido";
                        statusLogParcela = "E";
                    } else {
                        psAvalista.setString(22, rsExtracao.getString("CEP_AVAL").trim());
                    }
                } else {
                    psAvalista.setString(22, "0");
                    statusExtrator = "I";
                    idRetornoExtrator = 153;
                    descricaoLogParcela = "CEP Avalista Invalido";
                    statusLogParcela = "E";
                }
                psAvalista.setString(23, rsExtracao.getString("UF_AVAL"));
                psAvalista.setString(24, "A");
                psAvalista.setString(25, rsExtracao.getString("NEGATIVADO_AVAL"));
                psAvalista.setString(26, rsExtracao.getString("FILIAL_FLO"));
                if (extracaoModel.getBdOrigem().equals("NOVO_SGL")) {
                    if (rsExtracao.getString("SEXO_AVAL").length() > 0) {
                        psAvalista.setString(27, rsExtracao.getString("SEXO_AVAL"));
                    } else {
                        psAvalista.setString(27, "");
                    }
                } else {
                    psAvalista.setString(27, "");
                }

                psAvalista.execute();
                rsAvalista = psAvalista.getGeneratedKeys();
                if (rsAvalista.next()) {
                    idAvalista = (rsAvalista.getInt(1));
                    rsAvalista.close();
                }
            }
            /**
             * Insert da parcela.
             */
            psParcela.setString(1, rsExtracao.getString("FILIAL_FLO"));
            psParcela.setString(2, rsExtracao.getString("CHAVE_SGL"));
            psParcela.setString(3, rsExtracao.getString("DOC"));
            psParcela.setDate(4, rsExtracao.getDate("DATA_LANCAMENTO"));
            psParcela.setDate(5, rsExtracao.getDate("VENCIMENTO"));
            psParcela.setBigDecimal(6, rsExtracao.getBigDecimal("VALOR"));
            psParcela.setDate(7, rsExtracao.getDate("DATA_PAGAMENTO"));
            psParcela.setBigDecimal(8, rsExtracao.getBigDecimal("CAPITPAG"));
            psParcela.setString(9, rsExtracao.getString("SITUACAO_TITULO"));
            psParcela.setDate(10, rsExtracao.getDate("DATA_NEGATIVACAO"));
            psParcela.setDate(11, rsExtracao.getDate("DATA_BAIXA"));
            psParcela.setBigDecimal(12, rsExtracao.getBigDecimal("TAXA"));
            psParcela.setBigDecimal(13, rsExtracao.getBigDecimal("JUROS"));
            psParcela.setBigDecimal(14, rsExtracao.getBigDecimal("VALORCALC"));
            psParcela.setBigDecimal(15, rsExtracao.getBigDecimal("VALORPAG"));
            psParcela.setBigDecimal(16, rsExtracao.getBigDecimal("JUROSPAG"));
            psParcela.setDate(17, rsExtracao.getDate("DATA_ALTERACAO"));
            psParcela.setString(18, rsExtracao.getString("PARCELA"));
            psParcela.setString(19, rsExtracao.getString("TIPO_PGTO"));
            psParcela.setDate(20, Utilitarios.converteData(new Date()));
            psParcela.setInt(21, idCliente);
            if (idAvalista == 0) {
                psParcela.setNull(22, Types.INTEGER);
            } else {
                psParcela.setInt(22, idAvalista);
            }
            psParcela.setString(23, rsExtracao.getString("CLIENTE"));
            psParcela.setString(24, rsExtracao.getString("AVALISTA"));
            psParcela.setString(25, "N");//Prescrever?
            if (extracaoModel.getOrigemRegistro().equals("A")) {
                if (idAvalista == 0) {
                    psParcela.setString(26, "N");
                } else {
                    psParcela.setString(26, "S");
                }

            } else {
                psParcela.setString(26, extracaoModel.getInsertAvalista());
            }
            psParcela.setString(27, "C");//
            psParcela.setString(28, extracaoModel.getObservacao());
            if (extracaoModel.getBdOrigem().equalsIgnoreCase("SGL")) {
                psParcela.setString(29, "");
                psParcela.setString(30, "");
                psParcela.setString(31, "");
            } else {
                psParcela.setString(29, rsExtracao.getString("TITULO"));
                psParcela.setString(30, rsExtracao.getString("USU_IDETCR"));
                psParcela.setString(31, rsExtracao.getString("CODTPT"));
            }

            psParcela.execute();
            rsParcelas = psParcela.getGeneratedKeys();
            if (rsParcelas.next()) {
                idParcela = rsParcelas.getInt(1);
                rsParcelas.close();
            }
            /**
             * Insere o registro na tabela de controle de processamento.
             */
            if (extracaoModel.getOrigemRegistro().equals("M")) {
                psProcesso.setString(1, extracaoModel.getTipoParcela());
            } else {
                psProcesso.setString(1, rsExtracao.getString("TIPO_ACAO"));
            }
            psProcesso.setString(2, statusExtrator);
            psProcesso.setDate(3, Utilitarios.getDataZero());
            psProcesso.setInt(4, idParcela);
            psProcesso.setDate(5, Utilitarios.getDataZero());
            psProcesso.setDate(6, Utilitarios.getDataZero());
            psProcesso.setString(7, extracaoModel.getOrigemRegistro());
            psProcesso.setInt(8, idRetornoExtrator);
            psProcesso.setDate(9, Utilitarios.getDataZero());
            psProcesso.setDate(10, Utilitarios.converteData(new Date()));
            psProcesso.setString(11, extracaoModel.getBdOrigem());
            if (extracaoModel.getOrigemRegistro().equals("A")) {
                psProcesso.setString(12, "15");//ID_NATUREZA_INC_BVS
                psProcesso.setString(13, "101");//ID_NATUREZA_INC_SPC
                psProcesso.setString(14, "1");//ID_MOTIVO_EXCLUSAO_SPC
                psProcesso.setString(15, "01");//ID_MOTIVO_EXCLUSAO_BVS
                psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
            } else {
                if (extracaoModel.getTipoParcela().equals("B")) {
                    psProcesso.setString(12, "15");//ID_NATUREZA_INC_BVS
                    psProcesso.setString(13, "101");//ID_NATUREZA_INC_SPC
                    psProcesso.setString(14, extracaoModel.getIdMotivoBaixaSpc());//ID_MOTIVO_EXCLUSAO_SPC
                    psProcesso.setString(15, extracaoModel.getIdMotivoBaixaBvs());//ID_MOTIVO_EXCLUSAO_BVS
                    psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
                } else {
                    psProcesso.setString(12, extracaoModel.getIdNaturezaRegistroBvs());//ID_NATUREZA_INC_BVS
                    psProcesso.setString(13, extracaoModel.getIdNaturezaInclusaoSpc());//ID_NATUREZA_INC_SPC
                    psProcesso.setString(14, "1");//ID_MOTIVO_EXCLUSAO_SPC
                    psProcesso.setString(15, "01");//ID_MOTIVO_EXCLUSAO_BVS
                    psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
                }

            }
            psProcesso.setString(17, "N");/* Bloquear Registro? */
            psProcesso.setString(18, "P");
            psProcesso.setString(19, "P");
            psProcesso.setString(20, "P");

            psProcesso.execute();
            rsProExtrator = psProcesso.getGeneratedKeys();
            if (rsProExtrator.next()) {
                idExtrator = rsProExtrator.getInt(1);
                rsProExtrator.close();
            }
            /**
             * Insere o log da extracao, já tratando o codigo IBGE inválido.
             */
            psInserirLogParcela.setInt(1, idExtrator);
            psInserirLogParcela.setInt(2, idRetornoExtrator);
            psInserirLogParcela.setDate(3, Utilitarios.converteData(new Date()));
            psInserirLogParcela.setString(4, descricaoLogParcela);
            psInserirLogParcela.setString(5, "E");
            psInserirLogParcela.setString(6, statusLogParcela);
            psInserirLogParcela.execute();
            logExtracao = "Inclusão extraida com sucesso.";
            statusLogExtracao = "S";
            insertLogExtracao(idExtrator, logExtracao, statusLogExtracao);
        } catch (SQLException | StringIndexOutOfBoundsException | NumberFormatException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void updateRegistro(ExtracaoModel extracaoModel, String retornoExtracao, int idParcelaBanco, int idExtrator, int idClienteUpdate, int idAvalistaUpdate) throws ErroSistemaException {
        try {
            String statusExtrator = "P";
            int idRetornoExtrator = 160;
            String descricaoLogParcela = "Extração bem Sucedida.";
            String statusLogParcela = "S";
            validaNascimento();
            if (extracaoModel.getOrigemRegistro().equals("M")) {
                opcao = JOptionPane.showConfirmDialog(null, "Parcela já existe tem certeza que deseja atualizar as informações:" + "Id_Extração Anterior.: " + rsVerificaParcela.getString("ID_EXTRATOR"), "Atenção", JOptionPane.YES_NO_OPTION);
            }
            if (opcao == JOptionPane.OK_OPTION | extracaoModel.getOrigemRegistro().equals("A")) {
                psClienteUpdate.setString(1, rsExtracao.getString("NOME_CLIENTE"));
                psClienteUpdate.setString(2, rsExtracao.getString("TIPO_PESSOA"));
                psClienteUpdate.setString(3, rsExtracao.getString("CGCCPF"));
                if (rsExtracao.getString("CGCCPF").length() < 14 & rsExtracao.getString("TIPO_PESSOA").equalsIgnoreCase("F")) {
                    String cpf = StringUtils.leftPad(rsExtracao.getString("CGCCPF"), 9, "0");
                    psClienteUpdate.setString(4, cpf.substring(8, 9));
                } else {
                    psClienteUpdate.setString(4, " ");
                }
                if (rsExtracao.getString("TIPO_PESSOA").equalsIgnoreCase("F")) {
                    psClienteUpdate.setString(5, rsExtracao.getString("FIL_PAI"));
                    psClienteUpdate.setString(6, rsExtracao.getString("FIL_MAE"));
                    psClienteUpdate.setString(7, rsExtracao.getString("NUMERO_RG"));
                    psClienteUpdate.setDate(8, rsExtracao.getDate("DATA_EXPEDICAO"));
                    psClienteUpdate.setString(9, rsExtracao.getString("EXPEDIDOR"));
                    if (rsExtracao.getDate("DATA_NASCIMENTO").before(agora.getTime())) {
                        psClienteUpdate.setDate(12, rsExtracao.getDate("DATA_NASCIMENTO"));
                    } else {
                        psClienteUpdate.setDate(12, rsExtracao.getDate("DATA_NASCIMENTO"));
                        statusExtrator = "I";
                        idRetornoExtrator = 92;
                        descricaoLogParcela = "Clinte menor de 18 anos";
                        statusLogParcela = "E";
                    }
                    psClienteUpdate.setString(13, rsExtracao.getString("EST_CIVIL"));
                    psClienteUpdate.setString(14, rsExtracao.getString("ID_ESTCIVILCLI"));
                } else {
                    psClienteUpdate.setString(5, " ");
                    psClienteUpdate.setString(6, " ");
                    psClienteUpdate.setString(7, rsExtracao.getString("NUMERO_RG"));
                    psClienteUpdate.setDate(8, Utilitarios.getDataZero());
                    psClienteUpdate.setString(9, " ");
                    psClienteUpdate.setDate(12, Utilitarios.getDataZero());
                    psClienteUpdate.setString(13, " ");
                    psClienteUpdate.setString(14, "0");
                }
                psClienteUpdate.setString(10, rsExtracao.getString("EMAIL"));
                if (rsExtracao.getString("TELEFONE").length() > 0) {
                    psClienteUpdate.setString(11, rsExtracao.getString("TELEFONE").replaceAll("[^0-9]", ""));
                } else {
                    psClienteUpdate.setString(11, " ");
                }
                psClienteUpdate.setString(15, rsExtracao.getString("ENDERECO"));
                psClienteUpdate.setString(16, "2");// Id_logradouro 2 Rua
                psClienteUpdate.setString(17, rsExtracao.getString("NUMERO_END"));
                psClienteUpdate.setString(18, rsExtracao.getString("COMPLEMENTO"));
                psClienteUpdate.setString(19, rsExtracao.getString("BAIRRO"));
                psClienteUpdate.setString(20, rsExtracao.getString("CIDADE"));
                if (rsExtracao.getString("CODI_IBGE").trim().length() > 0) {
                    psClienteUpdate.setString(21, rsExtracao.getString("CODI_IBGE"));
                } else {
                    psClienteUpdate.setString(21, "0");
                    statusExtrator = "I";
                    idRetornoExtrator = 159;
                    descricaoLogParcela = "Codigo Ibge Invalido";
                    statusLogParcela = "E";
                }
                if (rsExtracao.getString("CEP").trim().length() > 0) {
                    psClienteUpdate.setString(22, rsExtracao.getString("CEP"));
                } else {
                    psClienteUpdate.setString(22, "0");
                    statusExtrator = "I";
                    idRetornoExtrator = 22;
                    descricaoLogParcela = "CEP Cliente Invalido";
                    statusLogParcela = "E";
                }
                psClienteUpdate.setString(23, rsExtracao.getString("UF"));
                psClienteUpdate.setString(24, "C");
                psClienteUpdate.setString(25, rsExtracao.getString("NEGATIVADO"));
                psClienteUpdate.setString(26, rsExtracao.getString("FILIAL_FLO"));
                psClienteUpdate.setInt(27, idClienteUpdate);
                psClienteUpdate.executeUpdate();

                /**
                 * Atualiza as informações referentes ao avalista, Se a parcela
                 * não possuir um avalista pula para update da parcela.
                 */
                if ((extracaoModel.getBdOrigem().equalsIgnoreCase("SGL") && rsExtracao.getString("AVALISTA").trim().length() > 0)
                        || (extracaoModel.getBdOrigem().equalsIgnoreCase("NOVO_SGL") && rsExtracao.getInt("AVALISTA") > 0)) {
                    psAvalistaUpdate.setString(1, rsExtracao.getString("NOME_AVALISTA"));
                    psAvalistaUpdate.setString(2, rsExtracao.getString("TIPO_AVALISTA"));
                    psAvalistaUpdate.setString(3, rsExtracao.getString("CPF_AVALISTA"));
                    if (rsExtracao.getString("CPF_AVALISTA").length() < 13 & rsExtracao.getString("TIPO_AVALISTA").equalsIgnoreCase("F")) {
                        String cpfAval = StringUtils.leftPad(rsExtracao.getString("CPF_AVALISTA"), 9, "0");
                        psAvalistaUpdate.setString(4, cpfAval.substring(8, 9));
                    } else {
                        psAvalistaUpdate.setString(4, " ");
                    }
                    if (rsExtracao.getString("TIPO_AVALISTA").equalsIgnoreCase("F")) {
                        psAvalistaUpdate.setString(5, rsExtracao.getString("FILPAI_AVAL"));
                        psAvalistaUpdate.setString(6, rsExtracao.getString("FILMAE_AVAL"));
                        psAvalistaUpdate.setString(7, rsExtracao.getString("RG_AVALISTA"));
                        psAvalistaUpdate.setDate(8, rsExtracao.getDate("DTEXPED_AVAL"));
                        psAvalistaUpdate.setString(9, rsExtracao.getString("EXPEDIDOR_AVAL"));
                        if (rsExtracao.getDate("DTNASC_AVAL").before(agora.getTime())) {
                            psAvalistaUpdate.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                        } else {
                            psAvalistaUpdate.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                            statusExtrator = "I";
                            idRetornoExtrator = 151;
                            descricaoLogParcela = "Avalista menor de 18 anos";
                            statusLogParcela = "E";
                        }
                        psAvalistaUpdate.setString(13, rsExtracao.getString("EST_CIVIL_AVAL"));
                        psAvalistaUpdate.setString(14, rsExtracao.getString("ID_ESTCIVILAVAL"));
                    } else {
                        psAvalistaUpdate.setString(5, " ");
                        psAvalistaUpdate.setString(6, " ");
                        psAvalistaUpdate.setString(7, rsExtracao.getString("RG_AVALISTA"));
                        psAvalistaUpdate.setDate(8, Utilitarios.getDataZero());
                        psAvalistaUpdate.setString(9, " ");
                        psAvalistaUpdate.setDate(12, Utilitarios.getDataZero());
                        psAvalistaUpdate.setString(13, " ");
                        psAvalistaUpdate.setString(14, "0");
                    }
                    psAvalistaUpdate.setString(10, rsExtracao.getString("EMAIL_AVAL"));
                    if (rsExtracao.getString("FONE_AVAL").length() > 0) {
                        psAvalistaUpdate.setString(11, rsExtracao.getString("FONE_AVAL").replaceAll("[^0-9]", ""));
                    } else {
                        psAvalistaUpdate.setString(11, " ");
                    }
                    psAvalistaUpdate.setString(15, rsExtracao.getString("ENDERECO_AVAL"));
                    psAvalistaUpdate.setString(16, "2");// Id_logradouro 2 Rua
                    psAvalistaUpdate.setString(17, rsExtracao.getString("NUMERO_END_AVAL"));
                    psAvalistaUpdate.setString(18, rsExtracao.getString("COMPLEMENTO_AVAL"));
                    psAvalistaUpdate.setString(19, rsExtracao.getString("BAIRRO_AVAL"));
                    psAvalistaUpdate.setString(20, rsExtracao.getString("CIDADE_AVAL"));
                    if (rsExtracao.getString("CODI_IBGE_AVAL").trim().length() > 0) {
                        psAvalistaUpdate.setString(21, rsExtracao.getString("CODI_IBGE_AVAL"));
                    } else {
                        psAvalistaUpdate.setString(21, "0");
                        statusExtrator = "I";
                        idRetornoExtrator = 159;
                        descricaoLogParcela = "Codigo Ibge Avalista Invalido";
                        statusLogParcela = "E";
                    }
                    if (rsExtracao.getString("CEP_AVAL").trim().length() > 0) {
                        psAvalistaUpdate.setString(22, rsExtracao.getString("CEP_AVAL"));
                    } else {
                        psAvalistaUpdate.setString(22, "0");
                        statusExtrator = "I";
                        idRetornoExtrator = 153;
                        descricaoLogParcela = "CEP Avalista Invalido";
                        statusLogParcela = "E";
                    }
                    psAvalistaUpdate.setString(23, rsExtracao.getString("UF_AVAL"));
                    psAvalistaUpdate.setString(24, "A");
                    psAvalistaUpdate.setString(25, rsExtracao.getString("NEGATIVADO_AVAL"));
                    psAvalistaUpdate.setString(26, rsExtracao.getString("FILIAL_FLO"));
                    psAvalistaUpdate.setInt(27, idAvalistaUpdate);
                    psAvalistaUpdate.execute();

                }
                /**
                 * ******UPDATE PARCELA*****
                 */
                psParcelaUpdate.setString(1, rsExtracao.getString("FILIAL_FLO"));
                psParcelaUpdate.setDate(2, rsExtracao.getDate("DATA_LANCAMENTO"));
                psParcelaUpdate.setDate(3, rsExtracao.getDate("VENCIMENTO"));
                psParcelaUpdate.setBigDecimal(4, rsExtracao.getBigDecimal("VALOR"));
                psParcelaUpdate.setDate(5, rsExtracao.getDate("DATA_PAGAMENTO"));
                psParcelaUpdate.setBigDecimal(6, rsExtracao.getBigDecimal("CAPITPAG"));
                psParcelaUpdate.setString(7, rsExtracao.getString("SITUACAO_TITULO"));
                psParcelaUpdate.setBigDecimal(8, rsExtracao.getBigDecimal("TAXA"));
                psParcelaUpdate.setBigDecimal(9, rsExtracao.getBigDecimal("JUROS"));
                psParcelaUpdate.setBigDecimal(10, rsExtracao.getBigDecimal("VALORCALC"));
                psParcelaUpdate.setBigDecimal(11, rsExtracao.getBigDecimal("VALORPAG"));
                psParcelaUpdate.setBigDecimal(12, rsExtracao.getBigDecimal("JUROSPAG"));
                psParcelaUpdate.setDate(13, rsExtracao.getDate("DATA_NEGATIVACAO"));
                psParcelaUpdate.setDate(14, rsExtracao.getDate("DATA_BAIXA"));
                psParcelaUpdate.setDate(15, Utilitarios.converteData(new Date()));
                psParcelaUpdate.setDate(16, rsExtracao.getDate("DATA_ALTERACAO"));
                psParcelaUpdate.setString(17, rsExtracao.getString("PARCELA"));
                psParcelaUpdate.setString(18, rsExtracao.getString("TIPO_PGTO"));
                if (extracaoModel.getBdOrigem().equalsIgnoreCase("SGL")) {
                    psParcelaUpdate.setString(19, "");
                    psParcelaUpdate.setString(20, "");
                    psParcelaUpdate.setString(21, "");
                } else {
                    psParcelaUpdate.setString(19, rsExtracao.getString("TITULO"));
                    psParcelaUpdate.setString(20, rsExtracao.getString("USU_IDETCR"));
                    psParcelaUpdate.setString(21, rsExtracao.getString("CODTPT"));
                }

                psParcelaUpdate.setInt(22, idParcelaBanco);
                psParcelaUpdate.execute();

                psAtualizarRegistroExtrator.setString(1, statusExtrator);
                psAtualizarRegistroExtrator.setInt(2, idExtrator);
                psAtualizarRegistroExtrator.execute();

                /* Insere o Log da Parcela */
                psInserirLogParcela.setInt(1, idExtrator);
                psInserirLogParcela.setInt(2, idRetornoExtrator);
                psInserirLogParcela.setDate(3, Utilitarios.converteData(new Date()));
                psInserirLogParcela.setString(4, descricaoLogParcela);
                psInserirLogParcela.setString(5, "E");
                psInserirLogParcela.setString(6, statusLogParcela);
                psInserirLogParcela.execute();


                /* Gerar o Log da Extração. Item a item. */
                logExtracao = "Parcela extraida com sucesso.Dados da Parcela atualizados com sucesso.";
                statusLogExtracao = "S";
                insertLogExtracao(idExtrator, logExtracao, statusLogExtracao);

                if (extracaoModel.getOrigemRegistro().equals("M")) {
                    retornoExtracao = "Parcela Atualizada";
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void insertBaixa(ExtracaoModel extracaoModel, int idParcelaBanco, String statusExtrator, String statusSpc, String statusSpcAval, String statusFacmat, Connection connection) throws ErroSistemaException {
        try (PreparedStatement psParcelaBaixaUpdate = connection.prepareStatement(sqlUpdateParcelas)) {
            psParcelaBaixaUpdate.setDate(1, rsExtracao.getDate("DATA_PAGAMENTO"));
            /* i*/
            psParcelaBaixaUpdate.setBigDecimal(2, rsExtracao.getBigDecimal("CAPITPAG"));
            psParcelaBaixaUpdate.setString(3, rsExtracao.getString("SITUACAO_TITULO"));
            psParcelaBaixaUpdate.setBigDecimal(4, rsExtracao.getBigDecimal("TAXA"));
            psParcelaBaixaUpdate.setBigDecimal(5, rsExtracao.getBigDecimal("JUROS"));
            psParcelaBaixaUpdate.setBigDecimal(6, rsExtracao.getBigDecimal("VALORCALC"));
            psParcelaBaixaUpdate.setBigDecimal(7, rsExtracao.getBigDecimal("VALORPAG"));
            psParcelaBaixaUpdate.setBigDecimal(8, rsExtracao.getBigDecimal("JUROSPAG"));
            psParcelaBaixaUpdate.setDate(9, rsExtracao.getDate("DATA_NEGATIVACAO"));
            psParcelaBaixaUpdate.setDate(10, rsExtracao.getDate("DATA_BAIXA"));
            psParcelaBaixaUpdate.setDate(11, Utilitarios.converteData(new Date()));
            psParcelaBaixaUpdate.setDate(12, rsExtracao.getDate("DATA_ALTERACAO"));
            psParcelaBaixaUpdate.setString(13, rsExtracao.getString("TIPO_PGTO"));
            psParcelaBaixaUpdate.setString(14, " | " + extracaoModel.getObservacao());
            if (extracaoModel.getBdOrigem().equalsIgnoreCase("SGL")) {
                psParcelaBaixaUpdate.setString(15, "");
                psParcelaBaixaUpdate.setString(16, "");
                psParcelaBaixaUpdate.setString(17, "");
            } else {
                psParcelaBaixaUpdate.setString(15, rsExtracao.getString("TITULO"));
                psParcelaBaixaUpdate.setString(16, rsExtracao.getString("USU_IDETCR"));
                psParcelaBaixaUpdate.setString(17, rsExtracao.getString("CODTPT"));
            }
            psParcelaBaixaUpdate.setInt(18, idParcelaBanco);
            psParcelaBaixaUpdate.execute();

            /**
             * Insere o registro na tabela de controle de processamento
             */
            if (extracaoModel.getOrigemRegistro().equals("M")) {
                psProcesso.setString(1, extracaoModel.getTipoParcela());
            } else {
                psProcesso.setString(1, rsExtracao.getString("TIPO_ACAO"));
            }

            psProcesso.setDate(3, Utilitarios.getDataZero());
            psProcesso.setInt(4, idParcelaBanco);
            psProcesso.setDate(5, Utilitarios.getDataZero());
            psProcesso.setDate(6, Utilitarios.getDataZero());
            psProcesso.setString(7, extracaoModel.getOrigemRegistro());
            psProcesso.setInt(8, 160);
            psProcesso.setDate(9, Utilitarios.getDataZero());
            psProcesso.setDate(10, Utilitarios.converteData(new Date()));
            psProcesso.setString(11, extracaoModel.getBdOrigem());
            if (extracaoModel.getOrigemRegistro().equals("A")) {
                psProcesso.setString(12, "15");//ID_NATUREZA_INC_BVS
                psProcesso.setString(13, "101");//ID_NATUREZA_INC_SPC
                psProcesso.setString(14, "1");//ID_MOTIVO_EXCLUSAO_SPC
                psProcesso.setString(15, "01");//ID_MOTIVO_EXCLUSAO_BVS
                psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
            } else {
                if (extracaoModel.getTipoParcela().equals("B")) {
                    psProcesso.setString(12, "15");//ID_NATUREZA_INC_BVS
                    psProcesso.setString(13, "101");//ID_NATUREZA_INC_SPC
                    psProcesso.setString(14, extracaoModel.getIdMotivoBaixaSpc());//ID_MOTIVO_EXCLUSAO_SPC
                    psProcesso.setString(15, extracaoModel.getIdMotivoBaixaBvs());//ID_MOTIVO_EXCLUSAO_BVS
                    psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
                } else {
                    psProcesso.setString(12, extracaoModel.getIdNaturezaRegistroBvs());//ID_NATUREZA_INC_BVS
                    psProcesso.setString(13, extracaoModel.getIdNaturezaInclusaoSpc());//ID_NATUREZA_INC_SPC
                    psProcesso.setString(14, "1");//ID_MOTIVO_EXCLUSAO_SPC
                    psProcesso.setString(15, "01");//ID_MOTIVO_EXCLUSAO_BVS
                    psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS
                }

            }
            psProcesso.setString(17, "N");//Bloquear Registro?

            psProcesso.setString(2, statusExtrator);
            psProcesso.setString(18, statusSpc);
            psProcesso.setString(19, statusFacmat);
            psProcesso.setString(20, statusSpcAval);
            psProcesso.execute();

            rsProExtrator = psProcesso.getGeneratedKeys();
            if (rsProExtrator.next()) {
                idExtrator = rsProExtrator.getInt(1);
                rsProExtrator.close();
            }
            /**
             * Insere o log da extracao
             */
            psInserirLogParcela.setInt(1, idExtrator);
            if (statusExtrator.equals("FE")) {
                psInserirLogParcela.setInt(2, 350);
                psInserirLogParcela.setDate(3, Utilitarios.converteData(new Date()));
                psInserirLogParcela.setString(4, "Baixa extraida e finalizada aut. Devido ao registro de inclusao não ter sido transmitido.");
                psInserirLogParcela.setString(5, "E");
                psInserirLogParcela.setString(6, "S");
                psInserirLogParcela.execute();
                //rsVerificaParcelaBaixa.close();
                logExtracao = "Baixa extraida e finalizada aut. Devido ao registro de inclusao não ter sido transmitido.";
                statusLogExtracao = "S";
            } else {
                psInserirLogParcela.setInt(2, 160);
                psInserirLogParcela.setDate(3, Utilitarios.converteData(new Date()));
                psInserirLogParcela.setString(4, "Baixa extraida com sucesso.");
                psInserirLogParcela.setString(5, "E");
                psInserirLogParcela.setString(6, "S");
                psInserirLogParcela.execute();
                //rsVerificaParcelaBaixa.close();
                logExtracao = "Baixa extraida com sucesso.";
                statusLogExtracao = "S";
            }

            insertLogExtracao(idExtrator, logExtracao, statusLogExtracao);

            /* Se o status da baixa da parcela for FE, então gerar no log da Parcela e do Extrator mensagens para isso. */
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public List<ParcelaModel> BuscarParcelaManual(ConsultaModel filtroConsulta) throws ErroSistemaException {

        lbuscarParcelasManual = new ArrayList<>();

        String sqlSelectMR02 = "SELECT PARCELA.PTO,\n"
                + "       PARCELA.DOC        AS NUMERO_CONTRATO,\n"
                + "       PARCELA.CODIGO     AS CHAVE_SGL,\n"
                + "       PARCELA.CLI        AS CLIENTE,\n"
                + "       CLIENTE.RAZ        AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGC        AS CGCCPF,\n"
                + "       CLIENTE.SIT        AS SITUACAO_CLIENTE,\n"
                + "       PARCELA.DATALAN    AS DATA_LANCAMENTO,\n"
                + "       PARCELA.VENC       AS DATA_VENCIMENTO,\n"
                + "       PARCELA.VALOR      AS VALOR,\n"
                + "       PARCELA.DATAPAG    AS DATA_PAGAMENTO,\n"
                + "       PARCELA.CAPITPAG   AS CAPITAL_PAGO,\n"
                + "       PARCELA.SIT        AS SITUACAO_PARCELA,\n"
                + "       PARCELA.NEGATIVADA AS DATA_NEGATIVACAO,\n"
                + "       PARCELA.BAIXANEG   AS DATA_BAIXA,\n"
                + "       PARCELA.AVALISTA   AS AVALISTA,\n"
                + "       AVALISTA.RAZ       AS NOME_AVALISTA\n"
                + "  FROM DB_INTEGRACAO.MR02 PARCELA\n"
                + " INNER JOIN DB_INTEGRACAO.MR01 CLIENTE\n"
                + "    ON CLIENTE.PTO = PARCELA.PTO\n"
                + "   AND CLIENTE.CLI = PARCELA.CLI\n"
                + "  LEFT OUTER JOIN DB_INTEGRACAO.MR01 AVALISTA\n"
                + "    ON AVALISTA.PTO = PARCELA.PTO\n"
                + "   AND TRIM(AVALISTA.CLI) = TRIM(PARCELA.AVALISTA)\n";

        String sqlSelectE301 = "SELECT FILIAL.FILIAL_SGL AS PTO,\n"
                + "       PARCELA.USU_CODCTR AS NUMERO_CONTRATO,\n"
                + "       PARCELA.NUMTIT AS ID_PARCELA,\n"
                + "       PARCELA.USU_NUMDOC AS CHAVE_SGL,\n"
                + "       PARCELA.CODCLI AS CLIENTE,\n"
                + "       CLIENTE.NOMCLI AS NOME_CLIENTE,\n"
                + "       CLIENTE.CGCCPF AS CGCCPF,\n"
                + "       CLIENTE.USU_SITCLI AS SITUACAO_CLIENTE,\n"
                + "       PARCELA.DATEMI AS DATA_LANCAMENTO,\n"
                + "       PARCELA.VCTORI AS DATA_VENCIMENTO,\n"
                + "       CASE\n"
                + "         WHEN PARCELA.VLRABE < PARCELA.VLRORI AND PARCELA.VLRABE > 0 THEN\n"
                + "          PARCELA.VLRABE\n"
                + "         WHEN PARCELA.VLRABE = PARCELA.VLRORI OR PARCELA.VLRABE = 0 THEN\n"
                + "          PARCELA.VLRORI\n"
                + "       END VALOR,\n"
                + "       PARCELA.ULTPGT AS DATA_PAGAMENTO,\n"
                + "       CASE\n"
                + "         WHEN E301MCR.SEQMOV > 1 THEN\n"
                + "          E301MCR.VLRMOV\n"
                + "         WHEN E301MCR.SEQMOV = 1 THEN\n"
                + "          0\n"
                + "       END CAPITAL_PAGO,\n"
                + "       PARCELA.SITTIT AS SITUACAO_PARCELA,\n"
                + "       PARCELA.USU_DATNEG AS DATA_NEGATIVACAO,\n"
                + "       PARCELA.USU_DATBAI AS DATA_BAIXA,\n"
                + "       PARCELA.USU_CODAVA AS AVALISTA,\n"
                + "       AVALISTA.NOMCLI AS NOME_AVALISTA\n"
                + "  FROM SAPIENS.E301TCR PARCELA\n"
                + "  LEFT OUTER JOIN SAPIENS.E301MCR\n"
                + "    ON E301MCR.NUMTIT = PARCELA.NUMTIT\n"
                + "   AND E301MCR.CODEMP = PARCELA.CODEMP\n"
                + "   AND E301MCR.CODFIL = PARCELA.CODFIL\n"
                + " INNER JOIN SAPIENS.E085CLI CLIENTE\n"
                + "    ON CLIENTE.CODCLI = PARCELA.CODCLI\n"
                + "  LEFT OUTER JOIN SAPIENS.E085CLI AVALISTA\n"
                + "    ON AVALISTA.CODCLI = PARCELA.USU_CODAVA\n"
                + " INNER JOIN FILIAIS FILIAL\n"
                + "    ON FILIAL.CODFIL = PARCELA.CODFIL\n";

        String sqlSelectE301CMR = "AND E301MCR.SEQMOV =\n"
                + "       (SELECT MAX(SEQMOV)\n"
                + "           FROM SAPIENS.E301MCR\n"
                + "          WHERE E301MCR.NUMTIT = PARCELA.NUMTIT\n"
                + "            AND E301MCR.CODEMP = PARCELA.CODEMP\n"
                + "            AND E301MCR.CODFIL = PARCELA.CODFIL)";

        if (filtroConsulta.getOrigem_BD().equals("SGL")) {
            sqlSelectManual = sqlSelectMR02 + getWhere(filtroConsulta);
        } else if (filtroConsulta.getOrigem_BD().equals("NOVO_SGL")) {
            sqlSelectManual = sqlSelectE301 + getWhereE301(filtroConsulta) + sqlSelectE301CMR;
        }

        try {
            System.out.println("slect" + sqlSelectManual);
            psBuscarParcelasManual = Conexao.getConnection().prepareStatement(sqlSelectManual);

            rsBuscarParcelasManual = psBuscarParcelasManual.executeQuery();
            while (rsBuscarParcelasManual.next()) {
                ParcelaModel parcelasModel = new ParcelaModel();
                parcelasModel.setPontoFilial(StringUtils.leftPad(rsBuscarParcelasManual.getString("PTO"), 4, "0"));
                parcelasModel.setCodCliente(rsBuscarParcelasManual.getString("CLIENTE"));
                parcelasModel.setNumeroDoContrato(rsBuscarParcelasManual.getString("NUMERO_CONTRATO"));
                parcelasModel.setNomeDoDevedor(rsBuscarParcelasManual.getString("NOME_CLIENTE"));
                parcelasModel.setCpfCnpj(rsBuscarParcelasManual.getString("CGCCPF"));
                parcelasModel.setDataLancamento(rsBuscarParcelasManual.getDate("DATA_LANCAMENTO"));
                parcelasModel.setDataVencimento(rsBuscarParcelasManual.getDate("DATA_VENCIMENTO"));
                parcelasModel.setDataAlteracao(rsBuscarParcelasManual.getDate("DATA_PAGAMENTO"));
                parcelasModel.setDataNegativacao(rsBuscarParcelasManual.getDate("DATA_NEGATIVACAO"));
                parcelasModel.setDataBaixaNegativacao(rsBuscarParcelasManual.getDate("DATA_BAIXA"));
                parcelasModel.setValorParcela(rsBuscarParcelasManual.getDouble("VALOR"));
                parcelasModel.setCapitalPago(rsBuscarParcelasManual.getDouble("CAPITAL_PAGO"));
                parcelasModel.setDataPagamento(rsBuscarParcelasManual.getDate("DATA_PAGAMENTO"));
                parcelasModel.setSituacaoParcela(rsBuscarParcelasManual.getString("SITUACAO_PARCELA"));
                parcelasModel.setSituacaoDoCliente(rsBuscarParcelasManual.getString("SITUACAO_CLIENTE"));
                parcelasModel.setCodigoClientParcela(rsBuscarParcelasManual.getString("CHAVE_SGL"));
                parcelasModel.setAvalista(rsBuscarParcelasManual.getString("NOME_AVALISTA"));
                if (filtroConsulta.getOrigem_BD().equalsIgnoreCase("NOVO_SGL")) {
                    parcelasModel.setIdParcela(rsBuscarParcelasManual.getString("ID_PARCELA"));
                }

                lbuscarParcelasManual.add(parcelasModel);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
        return lbuscarParcelasManual;
    }

    public void insertLogExtracao(int idExtrator, String logExtracao, String statusLogExtracao) throws ErroSistemaException {
        try {
            sqlInsertLogExtracao = "INSERT INTO LOG_EXTRACAO (ID_LOG_EXTRACAO, ID_EXTRATOR, NUM_DOC, VENCIMENTO, CLIENTE, PONTO, MENSAGEM, DATA_EXTRACAO, STATUS, ORIGEM, CGCCPF)"
                    + "VALUES (ID_LOG_EXTRACAO.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            psLogExtracao.setInt(1, idExtrator);
            psLogExtracao.setString(2, rsExtracao.getString("DOC"));
            psLogExtracao.setDate(3, rsExtracao.getDate("VENCIMENTO"));
            psLogExtracao.setString(4, rsExtracao.getString("CLIENTE"));
            psLogExtracao.setString(5, rsExtracao.getString("FILIAL_FLO"));
            psLogExtracao.setString(6, logExtracao);
            psLogExtracao.setDate(7, Utilitarios.converteData(new Date()));
            psLogExtracao.setString(8, statusLogExtracao);
            psLogExtracao.setString(9, "EXTRACAO");//Origem Log
            psLogExtracao.setLong(10, Long.parseLong(rsExtracao.getString("CGCCPF")));
            psLogExtracao.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public String getWhere(ConsultaModel filtroConsulta) {
        String where = "";

        if (filtroConsulta.getPontoFilial() != null) {
            where += ((filtroConsulta.getPontoFilial() != null) ? " AND PARCELA.PTO = '" + filtroConsulta.getPontoFilial() + "'" : "");
        }
        if (filtroConsulta.getDataInicial() != null && filtroConsulta.getDataFinal() != null) {
            where += ((filtroConsulta.getDataInicial() != null) ? " AND PARCELA.VENC  BETWEEN '" + filtroConsulta.getDataInicial() + "'" : "");

            where += ((filtroConsulta.getDataFinal() != null) ? " AND '" + filtroConsulta.getDataFinal() + "'" : "");
        }
        if (filtroConsulta.getDataIniciaPag() != null && filtroConsulta.getDataFinalPag() != null) {
            where += ((filtroConsulta.getDataIniciaPag() != null) ? " AND PARCELA.DATAPAG BETWEEN '" + filtroConsulta.getDataIniciaPag() + "'" : "");
            where += ((filtroConsulta.getDataFinalPag() != null) ? " AND '" + filtroConsulta.getDataFinalPag() + "'" : "");
        }
        if (filtroConsulta.getNumeroDoContrato() != null) {
            where += ((filtroConsulta.getNumeroDoContrato() != null) ? " AND PARCELA.DOC = '" + filtroConsulta.getNumeroDoContrato() + "'" : "");
        }
        if (filtroConsulta.getCodCliente() != null) {
            where += ((filtroConsulta.getCodCliente() != null) ? " AND PARCELA.CLI = '" + filtroConsulta.getCodCliente() + "'" : "");
        }
        if (filtroConsulta.getCpfCnpj() != null) {
            where += ((filtroConsulta.getCpfCnpj() != null) ? " AND CLIENTE.CGC = '" + filtroConsulta.getCpfCnpj() + "'" : "");
        }
        if (filtroConsulta.getNome() != null) {
            where += ((filtroConsulta.getNome() != null) ? " AND CLIENTE.RAZ LIKE '" + filtroConsulta.getNome() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public String getWhereE301(ConsultaModel filtroConsulta) {
        String where = "";

        if (filtroConsulta.getPontoFilial() != null) {
            where += ((filtroConsulta.getPontoFilial() != null) ? " AND FILIAL.FILIAL_SGL = '" + filtroConsulta.getPontoFilial() + "'" : "");
        }
        if (filtroConsulta.getDataInicial() != null && filtroConsulta.getDataFinal() != null) {
            where += ((filtroConsulta.getDataInicial() != null) ? " AND PARCELA.VCTORI  BETWEEN '" + filtroConsulta.getDataInicial() + "'" : "");
            where += ((filtroConsulta.getDataFinal() != null) ? " AND '" + filtroConsulta.getDataFinal() + "'" : "");
        }
        if (filtroConsulta.getDataIniciaPag() != null && filtroConsulta.getDataFinalPag() != null) {
            where += ((filtroConsulta.getDataIniciaPag() != null) ? " AND (PARCELA.ULTPGT BETWEEN '" + filtroConsulta.getDataIniciaPag() + "'" : "");
            where += ((filtroConsulta.getDataFinalPag() != null) ? " AND '" + filtroConsulta.getDataFinalPag() + "'"
                    + "OR(E301MCR.DATPGT BETWEEN '" + filtroConsulta.getDataIniciaPag() + "' AND '" + filtroConsulta.getDataFinalPag() + "' AND E301MCR.TPTRLC = '012'))" : "");
        }
        if (filtroConsulta.getDataInicialNeg() != null && filtroConsulta.getDataFinalNeg() != null) {
            where += ((filtroConsulta.getDataInicialNeg() != null) ? " AND PARCELA.USU_DATNEG BETWEEN '" + filtroConsulta.getDataInicialNeg() + "'" : "");
            where += ((filtroConsulta.getDataFinalNeg() != null) ? " AND '" + filtroConsulta.getDataFinalNeg() + "'" : "");
        }
        if (filtroConsulta.getDataInicialBai() != null && filtroConsulta.getDataFinalBai() != null) {
            where += ((filtroConsulta.getDataInicialBai() != null) ? " AND PARCELA.USU_DATBAI BETWEEN '" + filtroConsulta.getDataInicialBai() + "'" : "");
            where += ((filtroConsulta.getDataFinalBai() != null) ? " AND '" + filtroConsulta.getDataFinalBai() + "'" : "");
        }
        if (filtroConsulta.getNumeroDoContrato() != null) {
            where += ((filtroConsulta.getNumeroDoContrato() != null) ? " AND PARCELA.USU_CODCTR = '" + filtroConsulta.getNumeroDoContrato() + "'" : "");
        }
        if (filtroConsulta.getSituacao() != null) {
            where += ((filtroConsulta.getSituacao() != null) ? " AND PARCELA.SITTIT = '" + filtroConsulta.getSituacao() + "'" : "");
        }
        if (filtroConsulta.getCodCliente() != null) {
            where += ((filtroConsulta.getCodCliente() != null) ? " AND PARCELA.CODCLI = '" + filtroConsulta.getCodCliente() + "'" : "");
        }
        if (filtroConsulta.getCpfCnpj() != null) {
            long cpf = Long.parseLong(filtroConsulta.getCpfCnpj());
            where += " AND CLIENTE.CGCCPF = " + cpf;
        }
        if (filtroConsulta.getNome() != null) {
            where += ((filtroConsulta.getNome() != null) ? " AND CLIENTE.NOMCLI LIKE '" + filtroConsulta.getNome() + "'" : "");
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public void finalizaInclusaoPendente(int idExtrator) throws ErroSistemaException {
        String updateStatusInclusaoPendente = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC = 'FE', STATUS_SPC_AVAL = 'FE', STATUS_FACMAT = 'FE' WHERE ID_EXTRATOR = " + idExtrator + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendente);
            psFinalizaInclusaoPendente.execute();
            logExtracao = "Registro de inclusão finalizado aut. Devido ao registro de inclusao não ter sido transmitido.";
            insertLogExtracao(idExtrator, logExtracao, "S");

            psInserirLogParcela.setInt(1, idExtrator);
            psInserirLogParcela.setInt(2, 350);
            psInserirLogParcela.setDate(3, Utilitarios.converteData(new Date()));
            psInserirLogParcela.setString(4, logExtracao);
            psInserirLogParcela.setString(5, "E");
            psInserirLogParcela.setString(6, "S");
            psInserirLogParcela.execute();
            psFinalizaInclusaoPendente.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteFacmat(int idExtrator) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_FACMAT = 'FE' WHERE ID_EXTRATOR = " + idExtrator + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();
            psFinalizaInclusaoPendente.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteSpc(int idExtrator) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC = 'FE' WHERE ID_EXTRATOR = " + idExtrator + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();
            psFinalizaInclusaoPendente.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void finalizaInclusaoPendenteSpcAval(int idExtrator) throws ErroSistemaException {
        String updateStatusInclusaoPendenteFacmat = ("UPDATE EXTRATOR SET STATUS = 'FE', STATUS_SPC_AVAL = 'FE' WHERE ID_EXTRATOR = " + idExtrator + "");
        try {
            psFinalizaInclusaoPendente = Conexao.getConnection().prepareStatement(updateStatusInclusaoPendenteFacmat);
            psFinalizaInclusaoPendente.execute();
            psFinalizaInclusaoPendente.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    /*
    Gerar Retorno de Data de baixa ao contas Receber quando não for encontrado 
    um registro de Inclusão para a baixa 
    e quando o registro possuir data de vencimento 
    superior a 5 anos atrás.
     */
    public void baixarPrescritoPago(ExtracaoModel extracaoModel) throws ErroSistemaException {
        String sqlUpdatePrescritoPago = "";
        if (extracaoModel.getBdOrigem().equalsIgnoreCase("NOVO_SGL")) {
            sqlUpdatePrescritoPago = "UPDATE SAPIENS.E301TCR SET USU_DATBAI = ? \n"
                    + "                WHERE CODEMP = 1 \n"
                    + "                  AND CODFIL = ?\n"
                    + "                  AND USU_CODCTR = ? \n"
                    + "                  AND CODCLI = ?";
        } else {
            sqlUpdatePrescritoPago = ("UPDATE DB_INTEGRACAO.MR02\n"
                    + "                   SET DATA_MOV_RETORNAR = TO_DATE(SYSDATE), RETORNADO = 'N', BAIXANEG = ?\n"
                    + "                 WHERE PTO = ?\n"
                    + "                   AND DOC = ?\n"
                    + "                   AND CLI = ?");
        }
        try {
            psUpdateContasReceber = Conexao.getConnection().prepareStatement(sqlUpdatePrescritoPago);

            psUpdateContasReceber.setString(1, Utilitarios.converteDataString(new Date(), "dd-MM-yyyy"));
            if (extracaoModel.getBdOrigem().equalsIgnoreCase("NOVO_SGL")) {
                psUpdateContasReceber.setInt(2, rsExtracao.getInt("FILIAL"));
                psUpdateContasReceber.setString(3, Utilitarios.lpad(rsExtracao.getString("DOC"), "0", 12));
            } else {
                psUpdateContasReceber.setString(2, rsExtracao.getString("PTO"));
                psUpdateContasReceber.setString(3, rsExtracao.getString("DOC"));
            }
            psUpdateContasReceber.setString(4, rsExtracao.getString("CLIENTE"));
            psUpdateContasReceber.execute();
            psUpdateContasReceber.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex);
        }
    }

    public void updateProcesso(int quantidade) throws ErroSistemaException {
        ProcessamentoModel processamentoModel = new ProcessamentoModel();
        processamentoModel.setProvedor("EXTRATOR");
        processamentoModel.setTipo("EXTRACAO");
        processamentoModel.setItensTotal(quantidade);
        ProcessamentoDAO processamento = new ProcessamentoDAO();
        processamento.updateProcessoDBQuantidade(processamentoModel);
    }

    private void validaNascimento() {
        agora = Calendar.getInstance();
        // formata e exibe a data e hora atual
        formato = new SimpleDateFormat("dd/MM/yyyy");
        // vamos subtrair 5 minutos a esta data
        agora.add(Calendar.DATE, -6574);
        // formata e exibe o resultado
        formato = new SimpleDateFormat("dd/MM/yyyy");
    }

    private boolean verificaRecApp(ExtracaoModel extracaoModel) throws ErroSistemaException {

        try (PreparedStatement psApp = Conexao.getConnection().prepareStatement(getSqlVerificaRecebimento())) {
            psApp.setString(1, rsExtracao.getString("CHAVE_SGL"));
            psApp.setInt(2, rsExtracao.getInt("FILIAL"));
            rsApp = psApp.executeQuery();
            valApp = false;
            if (rsApp.next()) {
                logExtracao = "Recebimento App Pendente de Integração. Parcela possui pagamento via App, mas ainda não foi baixado na loja! Não será extraida.";
                insertLogExtracao(0, logExtracao, "A");
                valApp = true;
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex);
        } finally {
            try {
                rsApp.close();
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex);
            }
        }
        return valApp;
    }
}
