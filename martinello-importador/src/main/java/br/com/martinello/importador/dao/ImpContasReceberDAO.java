/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.importador.dao;

import br.com.martinello.bd.matriz.model.dao.ExtracaoDAO;
import br.com.martinello.util.Utilitarios;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author luiz.almeida
 */
public class ImpContasReceberDAO {

    private String sqlSelecContasReceber, sqlInsertCliente, sqlInsertParcelas, sqlInsertLog, sqlInsertExtrator, sqlUpdateParcelasErro, sqlUpdateParcelas,
            sqlUpdatePessoas, sqlUpdateExtrator, sqlWhereVerificaParcelas, sqlSelectParcelas, sqlInsertLogExtracao;
    private PreparedStatement psExtracao, psAvalista, psAvalistaUpdate, psParcela, psPessoa, psExtrator, psProcesso, psProcessoUpdate, psCliente,
            psClienteUpdate, psLogExtrator, psVerificaParcela, psVerificaParcelaBaixa, psParcelaUpdate, psParcelaBaixaUpdate, psParcelasSemRetorno,
            psLogExtracao, psParcelasRetornada, psBuscarParcelasManual, psParcelaManual, psExtracaoManual, psVerificaParcelaOriginal;
    private ResultSet rsExtracao, rsCliente, rsAvalista, rsParcelas, rsProExtrator, rsVerificaParcela, rsVerificaParcelaBaixa, rsParcelasSemRetorno,
            rsParcelasRetornada, rsBuscarParcelasManual, rsParcelaManual, rsVerificaParcelaOriginal;
    public int idCliente, idAvalista, idParcela, idExtrator, resultado, opcao;
    public String logExtracao, statusLogExtracao;

    public ImpContasReceberDAO() {

        sqlSelecContasReceber = "SELECT PARCELA.PTO,\n"
                + "                       PARCELA.DOC,\n"
                + "                       PARCELA_ORIGEM.DOC,\n"
                + "                       PARCELA.ORIGEM,\n"
                + "                       PARCELA.CODIGO,\n"
                + "                       PARCELA.CLI,\n"
                + "                       CLIENTE.RAZ,\n"
                + "                       CLIENTE.CGC,\n"
                + "                       CLIENTE.TIPO,\n"
                + "                       CLIENTE.SIT,\n"
                + "                       CLIENTE.INS,\n"
                + "                       CLIENTE.EXPEDIDOR,\n"
                + "                       CLIENTE.DTEXPED,\n"
                + "                       CLIENTE.EMAIL,\n"
                + "                       CLIENTE.FON,\n"
                + "                       CLIENTE.DTNASC,\n"
                + "                       CLIENTE.FILPAI,\n"
                + "                       CLIENTE.FILMAE,\n"
                + "                       CLIENTE.ESTCIVIL,\n"
                + "                       CLIENTE.END,\n"
                + "                       CLIENTE.NUM,\n"
                + "                       CLIENTE.BAI,\n"
                + "                       CLIENTE.COMPL,\n"
                + "                       CLIENTE.NEG,\n"
                + "                       CLIENTE.CID,\n"
                + "                       CLIENTE.CODIBGE,\n"
                + "                       CLIENTE.EST,\n"
                + "                       CLIENTE.CEP,\n"
                + "                       PARCELA.NUMPAR,\n"
                + "                       PARCELA.DATALAN,\n"
                + "                       PARCELA.VENC,\n"
                + "                       PARCELA.VALOR,\n"
                + "                       PARCELA.DATAPAG,\n"
                + "                       PARCELA.DATAALT,\n"
                + "                       PARCELA.DATA_MOV_RETORNAR,\n"
                + "                       PARCELA.TAXA,\n"
                + "                       PARCELA.JUROS,\n"
                + "                       PARCELA.VALORCALC,\n"
                + "                       PARCELA.VALORPAG,\n"
                + "                       PARCELA.CAPITPAG,\n"
                + "                       PARCELA.JUROSPAG,\n"
                + "                       PARCELA.SIT,\n"
                + "                       PARCELA.TPGTO,\n"
                + "                       PARCELA.NEGATIVADA,\n"
                + "                       PARCELA.BAIXANEG,\n"
                + "                       PARCELA.AVALISTA,\n"
                + "                       PARCELA.RETORNADO,\n"
                + "                       AVALISTA.RAZ AS NOMEAVALISTA,\n"
                + "                       AVALISTA.CGC AS CPF_AVALISTA,\n"
                + "                       AVALISTA.TIPO AS TIPO_AVALISTA,\n"
                + "                       AVALISTA.INS AS RG_AVALISTA,\n"
                + "                       AVALISTA.EXPEDIDOR AS EXPEDITOR_AVAL,\n"
                + "                       AVALISTA.DTEXPED AS DTEXPED_AVAL,\n"
                + "                       AVALISTA.EMAIL AS EMAIL_AVAL,\n"
                + "                       AVALISTA.FON AS FON_AVAL,\n"
                + "                       AVALISTA.DTNASC AS DTNASC_AVAL,\n"
                + "                       AVALISTA.FILPAI AS FILPAI_AVAL,\n"
                + "                       AVALISTA.FILMAE AS FILMAE_AVAL,\n"
                + "                       AVALISTA.ESTCIVIL AS ESTCIVIL_AVAL,\n"
                + "                       AVALISTA.END AS END_AVAL,\n"
                + "                       AVALISTA.NUM AS NUM_AVAL,\n"
                + "                       AVALISTA.BAI AS BAIRRO_AVAL,\n"
                + "                       AVALISTA.COMPL AS COMPL_AVAL,\n"
                + "                       AVALISTA.CID AS CIDADE_AVAL,\n"
                + "                       AVALISTA.CODIBGE AS CODIBGE_AVAL,\n"
                + "                       AVALISTA.EST AS EST_AVAL,\n"
                + "                       AVALISTA.CEP AS CEP_AVAL,\n"
                + "                       AVALISTA.NEG AS NEG_AVAL,\n"
                + "                       CASE\n"
                + "                         WHEN UPPER(CLIENTE.ESTCIVIL) = 'S' THEN\n"
                + "                          '1'\n"
                + "                         WHEN UPPER(CLIENTE.ESTCIVIL) = 'C' THEN\n"
                + "                          '2'\n"
                + "                         WHEN UPPER(CLIENTE.ESTCIVIL) = 'D' THEN\n"
                + "                          '3'\n"
                + "                         WHEN UPPER(CLIENTE.ESTCIVIL) = 'V' THEN\n"
                + "                          '4'\n"
                + "                         ELSE\n"
                + "                          '5'\n"
                + "                       END ID_ESTCIVILCLI,\n"
                + "                       CASE\n"
                + "                         WHEN UPPER(AVALISTA.ESTCIVIL) = 'S' THEN\n"
                + "                          '1'\n"
                + "                         WHEN UPPER(AVALISTA.ESTCIVIL) = 'C' THEN\n"
                + "                          '2'\n"
                + "                         WHEN UPPER(AVALISTA.ESTCIVIL) = 'D' THEN\n"
                + "                          '3'\n"
                + "                         WHEN UPPER(AVALISTA.ESTCIVIL) = 'V' THEN\n"
                + "                          '4'\n"
                + "                         ELSE\n"
                + "                          '5'\n"
                + "                       END ID_ESTCIVILAVAL,\n"
                + "                       CASE\n"
                + "                         WHEN PARCELA.DATAPAG = '31/12/1900' AND\n"
                + "                              PARCELA.NEGATIVADA = '31/12/1900' THEN\n"
                + "                          'I'\n"
                + "                         ELSE\n"
                + "                          'B'\n"
                + "                       END TIPO_ACAO\n"
                + "                  FROM DB_INTEGRACAO.MR02 PARCELA\n"
                + "                 INNER JOIN DB_INTEGRACAO.MR01 CLIENTE\n"
                + "                    ON CLIENTE.PTO = PARCELA.PTO\n"
                + "                   AND CLIENTE.CLI = PARCELA.CLI\n"
                + "                  LEFT OUTER JOIN DB_INTEGRACAO.MR01 AVALISTA\n"
                + "                    ON AVALISTA.PTO = PARCELA.PTO\n"
                + "                   AND TRIM(AVALISTA.CLI) = TRIM(PARCELA.AVALISTA)\n"
                + "                  LEFT OUTER JOIN DB_INTEGRACAO.MR02 PARCELA_ORIGEM\n"
                + "                    ON PARCELA_ORIGEM.CODIGO = PARCELA.ORIGEM\n"
                + "                   AND PARCELA_ORIGEM.PTO = PARCELA.PTO\n"
                + "                   LEFT OUTER JOIN FILIAIS ON FILIAIS.FILIAL_SGL = PARCELA.PTO\n"
                + "                WHERE  NOT EXISTS (SELECT 1\n"
                + "          FROM PARCELA PARC,FILIAIS\n"
                + "         WHERE FILIAIS.ID_FILIAL = PARC.ID_FILIAL \n"
                + "           AND PARCELA.PTO = FILIAIS.FILIAL_SGL \n"
                + "           AND PARCELA.CODIGO = PARC.CODIGO)\n"
                + "   AND PARCELA.VENC > SYSDATE - 1855\n"
                + "   AND PARCELA.NEGATIVADA <> '31/12/1900'\n"
                + "   AND PARCELA.BAIXANEG = '31/12/1900'"
                + "AND PARCELA.PTO >= '41'";

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

        sqlUpdateParcelasErro = ("UPDATE PARCELA SET ID_FILIAL = ?, DATALAN = ?, VENC = ?, VALOR = ?, DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?,"
                + " JUROS_PAG = ?, DATA_NEGATIVADA = ?, DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, NUMPAR = ?, TIPOPAG = ?"
                + " WHERE ID_PARCELA = ?");

        sqlUpdateParcelas = ("UPDATE PARCELA SET DATAPAG = ?, CAPITPAG = ?, SIT = ?, TAXA = ?, JUROS =?, VALOR_CALC = ?, VALOR_PAG = ?, JUROS_PAG = ?, DATA_NEGATIVADA = ?,"
                + " DATA_BAIXA = ?, DATA_ULT_ATUALIZACAO = ?, DATAALT = ?, TIPOPAG = ?"
                + " WHERE ID_PARCELA = ?");

        sqlUpdatePessoas = ("UPDATE PESSOA SET NOME = ?, TIPO_PESSOA = ?, CPF_RAZAO = ?, CPF_ORIGEM = ?, NOME_PAI = ?, NOME_MAE = ?,"
                + " NUM_RG = ?, DATE_EXPED = ?, ORGAO_EXPED = ?, EMAIL = ?, TELEFONE = ?, DATE_NASC = ?, EST_CIVIL = ?, ID_ESTCIVIL = ?, ENDERECO = ?, ID_LOGRADOURO = ?,"
                + " NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, CODIGO_IBGE = ?, CEP = ?, UF_ESTADO = ?, TIPO_DEVEDOR = ?, SITUACAO = ?, ID_FILIAL = ? WHERE ID_PESSOA = ?");

        sqlUpdateExtrator = ("UPDATE EXTRATOR SET TIPO = ?, STATUS = ?, ORIGEM = ?, RETORNO = ? WHERE ID_EXTRATOR = ?");

        sqlWhereVerificaParcelas = "WHERE PARCELA.ID_FILIAL = ?\n"
                + "   AND PARCELA.CODIGO = ?\n"
                + "   AND PARCELA.NUMERO_DOC = ?\n"
                + "   AND PARCELA.CLIENTE = ?\n";

        sqlSelectParcelas = "SELECT PESSOA.ID_PESSOA,\n"
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

        sqlInsertLogExtracao = "INSERT INTO LOG_EXTRACAO (ID_LOG_EXTRACAO, ID_EXTRATOR, NUM_DOC, CLIENTE, PONTO, MENSAGEM, DATA_EXTRACAO, STATUS, ORIGEM)"
                + "VALUES (ID_LOG_EXTRACAO.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

    }

    public Boolean realizarExtracao() {

        // select da extração, busca os dados.
        /**
         * Instancia todos os PrepareStatement fora do laço While
         *
         */
        try {
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);

            psExtracao = connection.prepareStatement(sqlSelecContasReceber);
            psProcesso = connection.prepareStatement((sqlInsertExtrator), new String[]{"ID_EXTRATOR"});
            psLogExtrator = connection.prepareStatement(sqlInsertLog);
            psParcela = connection.prepareStatement((sqlInsertParcelas), new String[]{"ID_PARCELA"});
            psPessoa = connection.prepareStatement((sqlInsertCliente), new String[]{"ID_PESSOA"});
            psAvalista = connection.prepareStatement((sqlInsertCliente), new String[]{"ID_PESSOA"});
            psAvalistaUpdate = connection.prepareStatement(sqlUpdatePessoas);
            psClienteUpdate = connection.prepareStatement(sqlUpdatePessoas);
            psProcessoUpdate = connection.prepareStatement(sqlUpdateExtrator);
            psParcelaUpdate = connection.prepareStatement(sqlUpdateParcelasErro);
            psParcelaBaixaUpdate = connection.prepareStatement(sqlUpdateParcelas);
            //  psVerificaParcelaBaixa = Conexao.getConnection().prepareStatement(sqlSelectParcelas + sqlWhereParcelas);
            //  psParcelasSemRetorno = connection.prepareStatement(sqlSelectParcelas + sqlWhereParcelaSemRetorno);
            psLogExtracao = connection.prepareStatement(sqlInsertLogExtracao);
            // psParcelasRetornada = connection.prepareStatement(sqlSelectParcelas + sqlWhereParcelaRetornada);

            /**
             * Percorre os dados da extração um a um fazendo os inserts e updades
             */
            //  String teste = sqlSelectExtracao + sqlWhereAutomaticoUnica;
            rsExtracao = psExtracao.executeQuery();
//            rsExtracao.last(); // vai para o final do rs
//            System.out.println("Tamanho = " + rsExtracao.getRow()); //imprime a Row (int)
//            rsExtracao.beforeFirst(); // retorna para o inicio do rs
            int count = 0;
            while (rsExtracao.next()) {
                System.out.println("Numero: " + count++);
                idAvalista = 0;
                try {

                    psVerificaParcela = Conexao.getConnection().prepareStatement(sqlSelectParcelas + sqlWhereVerificaParcelas);
                    psVerificaParcela.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
                    psVerificaParcela.setString(2, rsExtracao.getString("CODIGO"));
                    psVerificaParcela.setString(3, rsExtracao.getString("DOC"));
                    psVerificaParcela.setString(4, rsExtracao.getString("CLI"));
                    rsVerificaParcela = psVerificaParcela.executeQuery();
                    // Se trazer resultado sera feito o Update das imformações
                    //tratar update manual
                    if (rsVerificaParcela.next()) {
                        psVerificaParcela.close();
                        rsVerificaParcela.close();

                        /**
                         * Alimenta o select que verifica se a parcela já existe com o status
                         * Finalizado e data de retorno = '31/12/1900' Caso exista sera gravado um
                         * log para que o operador verifique o retorno e a parcela não sera
                         * inserida.
                         */
                        psParcelasSemRetorno.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
                        psParcelasSemRetorno.setString(2, rsExtracao.getString("CODIGO"));
                        psParcelasSemRetorno.setString(3, rsExtracao.getString("DOC"));
                        psParcelasSemRetorno.setString(4, rsExtracao.getString("CLI"));

                        psParcelasSemRetorno.setString(5, "I");

                        psParcelasSemRetorno.setString(6, "F");
                        rsParcelasSemRetorno = psParcelasSemRetorno.executeQuery();
                        //Caso corra o rsParcelasSemRetorno é porque a parcela já existe na base de dados e sua data retorno esta = '31/12/1900'
                        if (rsParcelasSemRetorno.next()) {
                            logExtracao = "O documento já existe com o Status finalizado, verifique o retorno!";
                            statusLogExtracao = "E";
                            idExtrator = rsParcelasSemRetorno.getInt("ID_EXTRATOR");
                            insertLogExtracao(logExtracao, statusLogExtracao);
                            rsParcelasSemRetorno.close();
                        } else {

                        }

                    }
                    psVerificaParcela.close();
                    rsVerificaParcela.close();
                    insertRegistroInclusao();
                    connection.commit();

                } catch (SQLException e) {
                    connection.rollback();
                    logExtracao = ("Erro.:" + e.getMessage());
                    statusLogExtracao = "E";
                    insertLogExtracao(logExtracao, statusLogExtracao);
                    connection.commit();
                    resultado = psLogExtracao.executeUpdate();
                    if (resultado == -1) {

                    } else {

                    }
                } catch (NullPointerException nexc) {
                    nexc.printStackTrace();
                    connection.rollback();
                    logExtracao = "O Documento possui campo obrigatório nullo - Nenhum Registro salvo Entre em contato com Luiz dep:TI";
                    statusLogExtracao = "E";
                    insertLogExtracao(logExtracao, statusLogExtracao);
                    connection.commit();

                }
            }

        } catch (SQLException ex) {
            System.out.println(ex);

            return false;
        } catch (IOException ex) {
            Logger.getLogger(ImpContasReceberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                psProcesso.close();
                psLogExtrator.close();
                psParcela.close();
                psPessoa.close();
                psAvalista.close();
                psAvalistaUpdate.close();
                psClienteUpdate.close();
                psProcessoUpdate.close();
                psParcelaUpdate.close();
                psParcelaBaixaUpdate.close();
                //   psVerificaParcelaBaixa.close();
                //  psParcelasSemRetorno.close();
                psLogExtracao.close();
                // psParcelasRetornada.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;

    }

    public boolean insertRegistroInclusao() {

        try {
            /**
             * Caso a Parcela ainda não exista no banco de Dados sera feito o inset de todas as
             * informações.
             */
            psPessoa.setString(1, rsExtracao.getString("RAZ"));
            psPessoa.setString(2, rsExtracao.getString("TIPO"));
            psPessoa.setString(3, rsExtracao.getString("CGC"));
            if (rsExtracao.getString("CGC").length() < 14) {
                psPessoa.setString(4, rsExtracao.getString("CGC").substring(8, 9));
            } else {
                psPessoa.setString(4, " ");
            }
            psPessoa.setString(5, rsExtracao.getString("FILMAE"));
            psPessoa.setString(6, rsExtracao.getString("FILPAI"));
            psPessoa.setString(7, rsExtracao.getString("INS"));
            psPessoa.setDate(8, rsExtracao.getDate("DTEXPED"));
            psPessoa.setString(9, rsExtracao.getString("EXPEDIDOR"));
            psPessoa.setString(10, rsExtracao.getString("EMAIL"));
            if (rsExtracao.getString("FON").length() > 0) {
                psPessoa.setString(11, rsExtracao.getString("FON"));
            } else {
                psPessoa.setString(11, " ");
            }
            Date dataFinal = new Date();
            Calendar calendarData = Calendar.getInstance();
            calendarData.setTime(dataFinal);
            int numeroDiasParaSubtrair = 5843;
            // achar data de início
            calendarData.add(Calendar.DATE, numeroDiasParaSubtrair);
            Date dataInicial = calendarData.getTime();
            if (rsExtracao.getDate("DTNASC").before(dataInicial)) {
                psPessoa.setDate(12, rsExtracao.getDate("DTNASC"));
            } else {
                psPessoa.setDate(12, rsExtracao.getDate("DTNASC"));
                psProcesso.setString(2, "ERRO");
                psProcesso.setInt(8, 92);
                psLogExtrator.setInt(2, 92);
                psLogExtrator.setString(4, "Cliente menor de 16 anos");

            }
            psPessoa.setString(13, rsExtracao.getString("ESTCIVIL"));
            psPessoa.setString(14, rsExtracao.getString("ID_ESTCIVILCLI"));
            psPessoa.setString(15, rsExtracao.getString("END"));
            psPessoa.setString(16, "2");// Id_logradouro 2 Rua
            psPessoa.setString(17, rsExtracao.getString("NUM"));
            psPessoa.setString(18, rsExtracao.getString("COMPL"));
            psPessoa.setString(19, rsExtracao.getString("BAI"));
            psPessoa.setString(20, rsExtracao.getString("CID"));
            if (rsExtracao.getString("CODIBGE").length() > 0) {
                psPessoa.setString(21, rsExtracao.getString("CODIBGE"));
                if (rsExtracao.getDate("DTNASC").after(dataInicial)) {

                } else {
                    psProcesso.setString(2, "P");
                    psProcesso.setInt(8, 160);
                    psLogExtrator.setInt(2, 160);
                    psLogExtrator.setString(4, "Operação bem sucedida");
                    psLogExtrator.setString(6, "S");
                }

            } else {
                psPessoa.setString(21, "0");
                psProcesso.setString(2, "I");
                psProcesso.setInt(8, 159);
                psLogExtrator.setInt(2, 159);
                psLogExtrator.setString(4, "Codigo Ibge Invalido");
                psLogExtrator.setString(6, "S");
            }
            psPessoa.setString(22, rsExtracao.getString("CEP"));
            psPessoa.setString(23, rsExtracao.getString("EST"));
            psPessoa.setString(24, "C");
            psPessoa.setString(25, rsExtracao.getString("NEG"));
            psPessoa.setString(26, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));

            psPessoa.setString(27, "M");

            psPessoa.executeUpdate();
            rsCliente = psPessoa.getGeneratedKeys();
            if (rsCliente.next()) {
                idCliente = rsCliente.getInt(1);
                rsCliente.close();
            }
            /**
             * Insere as informações referentes ao avalista, Se a parcela não possuir um avalista,
             * pula para inserção da parcela.
             */
            String AvalistaTeste = rsExtracao.getString("AVALISTA");
            System.out.println("Avalista" + AvalistaTeste);
            if (rsExtracao.getString("AVALISTA").trim().length() > 0) {
                psAvalista.setString(1, rsExtracao.getString("NOMEAVALISTA"));
                psAvalista.setString(2, rsExtracao.getString("TIPO_AVALISTA"));
                psAvalista.setString(3, rsExtracao.getString("CPF_AVALISTA"));
                if (rsExtracao.getString("CPF_AVALISTA").length() < 13) {
                    psAvalista.setString(4, rsExtracao.getString("CPF_AVALISTA").substring(8, 9));
                } else {
                    psAvalista.setString(4, " ");
                }
                psAvalista.setString(5, rsExtracao.getString("FILMAE_AVAL"));
                psAvalista.setString(6, rsExtracao.getString("FILPAI_AVAL"));
                psAvalista.setString(7, rsExtracao.getString("RG_AVALISTA"));
                psAvalista.setDate(8, rsExtracao.getDate("DTEXPED_AVAL"));
                psAvalista.setString(9, rsExtracao.getString("EXPEDITOR_AVAL"));
                psAvalista.setString(10, rsExtracao.getString("EMAIL_AVAL"));
                if (rsExtracao.getString("FON_AVAL").length() > 0) {
                    psAvalista.setString(11, rsExtracao.getString("FON_AVAL"));
                } else {
                    psAvalista.setString(11, " ");
                }
                psAvalista.setDate(12, rsExtracao.getDate("DTNASC_AVAL"));
                psAvalista.setString(13, rsExtracao.getString("ESTCIVIL_AVAL"));
                psAvalista.setString(14, rsExtracao.getString("ID_ESTCIVILAVAL"));
                psAvalista.setString(15, rsExtracao.getString("END_AVAL"));
                psAvalista.setString(16, "2");// Id_logradouro 2 Rua
                psAvalista.setString(17, rsExtracao.getString("NUM_AVAL"));
                psAvalista.setString(18, rsExtracao.getString("COMPL_AVAL"));
                psAvalista.setString(19, rsExtracao.getString("BAIRRO_AVAL"));
                psAvalista.setString(20, rsExtracao.getString("CIDADE_AVAL"));
                if (rsExtracao.getString("CODIBGE_AVAL").trim().length() > 0) {
                    psAvalista.setString(21, rsExtracao.getString("CODIBGE_AVAL"));
                } else {
                    psAvalista.setString(21, "0");
                }
                psAvalista.setString(22, rsExtracao.getString("CEP_AVAL"));
                psAvalista.setString(23, rsExtracao.getString("EST_AVAL"));
                psAvalista.setString(24, "A");
                psAvalista.setString(25, rsExtracao.getString("NEG_AVAL"));
                psAvalista.setString(26, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));

                psAvalista.setString(27, "M");

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
            psParcela.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
            psParcela.setString(2, rsExtracao.getString("CODIGO"));
            psParcela.setString(3, rsExtracao.getString("DOC"));
            psParcela.setDate(4, rsExtracao.getDate("DATALAN"));
            psParcela.setDate(5, rsExtracao.getDate("VENC"));
            psParcela.setBigDecimal(6, rsExtracao.getBigDecimal("VALOR"));
            psParcela.setDate(7, rsExtracao.getDate("DATAPAG"));
            psParcela.setBigDecimal(8, rsExtracao.getBigDecimal("CAPITPAG"));
            psParcela.setString(9, rsExtracao.getString("SIT"));
            psParcela.setDate(10, rsExtracao.getDate("NEGATIVADA"));
            psParcela.setDate(11, rsExtracao.getDate("BAIXANEG"));
            psParcela.setBigDecimal(12, rsExtracao.getBigDecimal("TAXA"));
            psParcela.setBigDecimal(13, rsExtracao.getBigDecimal("JUROS"));
            psParcela.setBigDecimal(14, rsExtracao.getBigDecimal("VALORCALC"));
            psParcela.setBigDecimal(15, rsExtracao.getBigDecimal("VALORPAG"));
            psParcela.setBigDecimal(16, rsExtracao.getBigDecimal("JUROSPAG"));
            psParcela.setDate(17, rsExtracao.getDate("DATAALT"));
            psParcela.setString(18, rsExtracao.getString("NUMPAR"));
            psParcela.setString(19, rsExtracao.getString("TPGTO"));
            psParcela.setDate(20, Utilitarios.converteData(new Date()));
            psParcela.setInt(21, idCliente);
            if (idAvalista == 0) {
                psParcela.setNull(22, Types.INTEGER);
            } else {
                psParcela.setInt(22, idAvalista);
            }
            psParcela.setString(23, rsExtracao.getString("CLI"));
            psParcela.setString(24, rsExtracao.getString("AVALISTA"));
            psParcela.setString(25, "N");//Prescrever?

            psParcela.setString(26, "S");//Enviar Acvalista

            psParcela.setString(27, "P");// Provedor
            psParcela.execute();
            rsParcelas = psParcela.getGeneratedKeys();
            if (rsParcelas.next()) {
                idParcela = rsParcelas.getInt(1);
                rsParcelas.close();
            }
            /**
             * Insere o registro na tabela de controle de processamento.
             */

            psProcesso.setString(1, "I");
            psProcesso.setDate(3, Utilitarios.getDataZero());
            psProcesso.setInt(4, idParcela);
            psProcesso.setDate(5, Utilitarios.getDataZero());
            psProcesso.setDate(6, Utilitarios.getDataZero());
            psProcesso.setString(7, "I");
            psProcesso.setDate(9, Utilitarios.getDataZero());
            psProcesso.setDate(10, Utilitarios.converteData(new Date()));
            psProcesso.setString(11, "SGL");

            psProcesso.setString(12, "15");//ID_NATUREZA_INC_BVS
            psProcesso.setString(13, "101");//ID_NATUREZA_INC_SPC
            psProcesso.setString(14, "1");//ID_MOTIVO_EXCLUSAO_SPC
            psProcesso.setString(15, "01");//ID_MOTIVO_EXCLUSAO_BVS
            psProcesso.setString(16, "1");//ID_MOTIVO_INC_BVS

            psProcesso.setString(17, "N");//Bloquear Registro?
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
            psLogExtrator.setInt(1, idExtrator);
            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
            psLogExtrator.setString(5, "E");
            psLogExtrator.execute();
            logExtracao = "Extraido com sucesso!";
            statusLogExtracao = "S";
            insertLogExtracao(logExtracao, statusLogExtracao);

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateRegistro() {
        try {

            psClienteUpdate.setString(1, rsExtracao.getString("RAZ"));
            psClienteUpdate.setString(2, rsExtracao.getString("TIPO"));
            psClienteUpdate.setString(3, rsExtracao.getString("CGC"));
            if (rsExtracao.getString("CGC").length() < 14) {
                psClienteUpdate.setString(4, rsExtracao.getString("CGC").substring(8, 9));
            } else {
                psClienteUpdate.setString(4, " ");
            }
            psClienteUpdate.setString(5, rsExtracao.getString("FILMAE"));
            psClienteUpdate.setString(6, rsExtracao.getString("FILPAI"));
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
                psProcessoUpdate.setString(2, "P");
                psProcessoUpdate.setInt(4, 160);
                psLogExtrator.setInt(2, 160);
                psLogExtrator.setString(4, "Operação bem sucedida");
            } else {
                psClienteUpdate.setString(21, "0");
                psProcessoUpdate.setString(2, "E");
                psProcessoUpdate.setInt(4, 159);
                psLogExtrator.setInt(2, 159);
                psLogExtrator.setString(4, "Codigo Ibge Invalido");
            }
            psClienteUpdate.setString(22, rsExtracao.getString("CEP"));
            psClienteUpdate.setString(23, rsExtracao.getString("EST"));
            psClienteUpdate.setString(24, "C");
            psClienteUpdate.setString(25, rsExtracao.getString("NEG"));
            psClienteUpdate.setString(26, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
            psClienteUpdate.setInt(27, rsVerificaParcela.getInt("ID_CLIENTE"));
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
                psAvalistaUpdate.setString(5, rsExtracao.getString("FILMAE_AVAL"));
                psAvalistaUpdate.setString(6, rsExtracao.getString("FILPAI_AVAL"));
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
                psAvalistaUpdate.setInt(27, rsVerificaParcela.getInt("ID_AVALISTA"));
                psAvalistaUpdate.execute();

            }
            /**
             * ******UPDATE PARCELA*****
             */
            psParcelaUpdate.setString(1, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
            psParcelaUpdate.setDate(2, rsExtracao.getDate("DATALAN"));
            psParcelaUpdate.setDate(3, rsExtracao.getDate("VENC"));
            psParcelaUpdate.setBigDecimal(4, rsExtracao.getBigDecimal("VALOR"));
            psParcelaUpdate.setDate(5, rsExtracao.getDate("DATAPAG"));
            psParcelaUpdate.setBigDecimal(6, rsExtracao.getBigDecimal("CAPITPAG"));
            psParcelaUpdate.setString(7, rsExtracao.getString("SIT_PARCELA"));
            psParcelaUpdate.setBigDecimal(8, rsExtracao.getBigDecimal("TAXA"));
            psParcelaUpdate.setBigDecimal(9, rsExtracao.getBigDecimal("JUROS"));
            psParcelaUpdate.setBigDecimal(10, rsExtracao.getBigDecimal("VALORCALC"));
            psParcelaUpdate.setBigDecimal(11, rsExtracao.getBigDecimal("VALORPAG"));
            psParcelaUpdate.setBigDecimal(12, rsExtracao.getBigDecimal("JUROSPAG"));
            psParcelaUpdate.setDate(13, rsExtracao.getDate("NEGATIVADA"));
            psParcelaUpdate.setDate(14, rsExtracao.getDate("BAIXANEG"));
            psParcelaUpdate.setDate(15, Utilitarios.converteData(new Date()));
            psParcelaUpdate.setDate(16, rsExtracao.getDate("DATAALT"));
            psParcelaUpdate.setString(17, rsExtracao.getString("NUMPAR"));
            psParcelaUpdate.setString(18, rsExtracao.getString("TPGTO"));
            psParcelaUpdate.setInt(19, rsVerificaParcela.getInt("ID_PARCELA"));
            psParcelaUpdate.execute();

            /**
             * Insere o registro na tabela de controle de processamento
             */
            psProcessoUpdate.setString(1, "I");

            psProcessoUpdate.setString(3, "I");
            psProcessoUpdate.setInt(5, idExtrator);
            psProcessoUpdate.execute();
            /**
             * Insere o log da extracao
             */
            psLogExtrator.setInt(1, idExtrator);
            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
            psLogExtrator.execute();

            rsVerificaParcela.close();

        } catch (SQLException ex) {

        }
        return false;

    }

    public String insertLogExtracao(String logExtracao, String statusLogExtracao) {
        try {

            psLogExtracao.setInt(1, idExtrator);
            psLogExtracao.setString(2, rsExtracao.getString("DOC"));
            psLogExtracao.setString(3, rsExtracao.getString("CLI"));
            psLogExtracao.setString(4, StringUtils.leftPad(rsExtracao.getString("PTO"), 4, "0"));
            psLogExtracao.setString(5, logExtracao);
            psLogExtracao.setDate(6, Utilitarios.converteData(new Date()));
            psLogExtracao.setString(7, statusLogExtracao);
            psLogExtracao.setString(8, "EXTRACAO");//Origem Log
            psLogExtracao.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ExtracaoDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }
}
