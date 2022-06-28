/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.PessoaModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class PessoaDAO {

    private static PreparedStatement psListarInfoPessoal, psBuscaInfoAtuPessoa, psInsertInfoAtuPessoa;
    private static ResultSet rsListarInfoPessoal, rsBuscaInfoAtuPessoa, rsInsertInfoAtuPessoa;
    public String sqlSelectExtracao, sqlSelectExtracaoAvalista, sqlBuscaCliente, sqlUpdatePessoas;
    private int indice;
    public static List<PessoaModel> lpessoaModel;

    public List<PessoaModel> listarInfoPessoal(PessoaModel pessoaModel) {
        PessoaModel infoPessoaTableModel;
        try {
            sqlSelectExtracao = "SELECT PESSOA.ID_PESSOA,\n"
                    + "       PESSOA.NOME,\n"
                    + "       PESSOA.TIPO_PESSOA,\n"
                    + "       PESSOA.CPF_RAZAO,\n"
                    + "       PESSOA.CPF_ORIGEM,\n"
                    + "       PESSOA.NOME_PAI,\n"
                    + "       PESSOA.NOME_MAE,\n"
                    + "       PESSOA.NUM_RG,\n"
                    + "       PESSOA.DATE_EXPED,\n"
                    + "       PESSOA.ORGAO_EXPED,\n"
                    + "       PESSOA.EMAIL,\n"
                    + "       PESSOA.TELEFONE,\n"
                    + "       PESSOA.DATE_NASC,\n"
                    + "       PESSOA.EST_CIVIL,\n"
                    + "       PESSOA.ID_ESTCIVIL,\n"
                    + "       PESSOA.ENDERECO,\n"
                    + "       PESSOA.ID_LOGRADOURO,\n"
                    + "       PESSOA.NUMERO,\n"
                    + "       PESSOA.COMPLEMENTO,\n"
                    + "       PESSOA.BAIRRO,\n"
                    + "       PESSOA.CIDADE,\n"
                    + "       PESSOA.CODIGO_IBGE,\n"
                    + "       PESSOA.CEP,\n"
                    + "       PESSOA.UF_ESTADO,\n"
                    + "       PESSOA.TIPO_DEVEDOR,\n"
                    + "       PESSOA.SITUACAO,\n"
                    + "       PESSOA.ID_FILIAL,\n"
                    + "       PARCELA.ID_PARCELA,\n"
                    + "       PARCELA.ID_FILIAL,\n"
                    + "       PARCELA.CODIGO,\n"
                    + "       PARCELA.NUMERO_DOC,\n"
                    + "       PARCELA.DATALAN,\n"
                    + "       PARCELA.VENC,\n"
                    + "       PARCELA.VALOR,\n"
                    + "       PARCELA.DATAPAG,\n"
                    + "       PARCELA.CAPITPAG,\n"
                    + "       PARCELA.SIT,\n"
                    + "       PARCELA.DATA_NEGATIVADA,\n"
                    + "       PARCELA.DATA_BAIXA,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.TAXA,\n"
                    + "       PARCELA.JUROS,\n"
                    + "       PARCELA.VALOR_CALC,\n"
                    + "       PARCELA.VALOR_PAG,\n"
                    + "       PARCELA.JUROS_PAG,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.NUMPAR,\n"
                    + "       PARCELA.TIPOPAG,\n"
                    + "       PARCELA.DATA_EXTRACAO,\n"
                    + "       PARCELA.DATA_ULT_ATUALIZACAO,\n"
                    + "       PARCELA.ID_CLIENTE,"
                    + "       PARCELA.CLIENTE,"
                    + "       PARCELA.ID_AVALISTA,\n"
                    + "       EXTRATOR.ID_EXTRATOR,\n"
                    + "       EXTRATOR.TIPO,\n"
                    + "       EXTRATOR.STATUS,\n"
                    + "       EXTRATOR.DATA_SPC,\n"
                    + "       EXTRATOR.ID_PARCELA,\n"
                    + "       EXTRATOR.DATA_SPC_AVALISTA,\n"
                    + "       EXTRATOR.DATA_FACMAT,\n"
                    + "       EXTRATOR.ORIGEM\n"
                    + "  FROM PARCELA, PESSOA, EXTRATOR\n"
                    + " WHERE parcela.id_cliente = pessoa.id_pessoa\n"
                    + " and parcela.id_parcela = extrator.id_parcela\n"
                    + " and extrator.id_extrator = ?";
            System.out.println(sqlSelectExtracao);
            psListarInfoPessoal = Conexao.getConnection().prepareStatement(sqlSelectExtracao);
            lpessoaModel = new ArrayList<>();
            psListarInfoPessoal.setString(1, pessoaModel.getIdExtrator());
            rsListarInfoPessoal = psListarInfoPessoal.executeQuery();
            indice = 1;
            while (rsListarInfoPessoal.next()) {
                infoPessoaTableModel = new PessoaModel();
                infoPessoaTableModel.setIndice(indice++);
                infoPessoaTableModel.setIdExtrator(rsListarInfoPessoal.getString("ID_EXTRATOR"));
                infoPessoaTableModel.setPontoFilial(rsListarInfoPessoal.getString("ID_FILIAL"));
                infoPessoaTableModel.setIdPessoa(rsListarInfoPessoal.getInt("ID_PESSOA"));
                infoPessoaTableModel.setNomeRazaoSocial(rsListarInfoPessoal.getString("NOME"));
                infoPessoaTableModel.setCodigo(rsListarInfoPessoal.getString("CLIENTE"));
                infoPessoaTableModel.setTipoPessoa(rsListarInfoPessoal.getString("TIPO_PESSOA"));
                infoPessoaTableModel.setCpf(rsListarInfoPessoal.getString("CPF_RAZAO"));
                infoPessoaTableModel.setNumeroRG(rsListarInfoPessoal.getString("NUM_RG"));
                infoPessoaTableModel.setOrgaoEmissorRG(rsListarInfoPessoal.getString("ORGAO_EXPED"));
                infoPessoaTableModel.setDataEmissaoRG(rsListarInfoPessoal.getDate("DATE_EXPED"));
                infoPessoaTableModel.setSituacao(rsListarInfoPessoal.getString("SITUACAO"));
                infoPessoaTableModel.setNomeDoPai(rsListarInfoPessoal.getString("NOME_PAI"));
                infoPessoaTableModel.setNomeDaMae(rsListarInfoPessoal.getString("NOME_MAE"));
                infoPessoaTableModel.setEmail(rsListarInfoPessoal.getString("EMAIL"));
                infoPessoaTableModel.setEstadoCivil(rsListarInfoPessoal.getString("EST_CIVIL"));
                infoPessoaTableModel.setDataNascimento(rsListarInfoPessoal.getDate("DATE_NASC"));
                infoPessoaTableModel.setNumeroTel(rsListarInfoPessoal.getString("TELEFONE"));
                infoPessoaTableModel.setIdAvalista(rsListarInfoPessoal.getInt("ID_AVALISTA"));

                lpessoaModel.add(infoPessoaTableModel);
            }
            psListarInfoPessoal.close();
            rsListarInfoPessoal.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lpessoaModel;

    }

    public List<PessoaModel> listarInfoPessoalAval(PessoaModel pessoaModel) {
        PessoaModel inAvalTableModel;
        try {
            sqlSelectExtracaoAvalista = "SELECT PESSOA.ID_PESSOA,\n"
                    + "       PESSOA.NOME,\n"
                    + "       PESSOA.TIPO_PESSOA,\n"
                    + "       PESSOA.CPF_RAZAO,\n"
                    + "       PESSOA.CPF_ORIGEM,\n"
                    + "       PESSOA.NOME_PAI,\n"
                    + "       PESSOA.NOME_MAE,\n"
                    + "       PESSOA.NUM_RG,\n"
                    + "       PESSOA.DATE_EXPED,\n"
                    + "       PESSOA.ORGAO_EXPED,\n"
                    + "       PESSOA.EMAIL,\n"
                    + "       PESSOA.TELEFONE,\n"
                    + "       PESSOA.DATE_NASC,\n"
                    + "       PESSOA.EST_CIVIL,\n"
                    + "       PESSOA.ID_ESTCIVIL,\n"
                    + "       PESSOA.ENDERECO,\n"
                    + "       PESSOA.ID_LOGRADOURO,\n"
                    + "       PESSOA.NUMERO,\n"
                    + "       PESSOA.COMPLEMENTO,\n"
                    + "       PESSOA.BAIRRO,\n"
                    + "       PESSOA.CIDADE,\n"
                    + "       PESSOA.CODIGO_IBGE,\n"
                    + "       PESSOA.CEP,\n"
                    + "       PESSOA.UF_ESTADO,\n"
                    + "       PESSOA.TIPO_DEVEDOR,\n"
                    + "       PESSOA.SITUACAO,\n"
                    + "       PESSOA.ID_FILIAL,\n"
                    + "       PARCELA.ID_PARCELA,\n"
                    + "       PARCELA.ID_FILIAL,\n"
                    + "       PARCELA.CODIGO,\n"
                    + "       PARCELA.NUMERO_DOC,\n"
                    + "       PARCELA.DATALAN,\n"
                    + "       PARCELA.VENC,\n"
                    + "       PARCELA.VALOR,\n"
                    + "       PARCELA.DATAPAG,\n"
                    + "       PARCELA.CAPITPAG,\n"
                    + "       PARCELA.SIT,\n"
                    + "       PARCELA.DATA_NEGATIVADA,\n"
                    + "       PARCELA.DATA_BAIXA,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.TAXA,\n"
                    + "       PARCELA.JUROS,\n"
                    + "       PARCELA.VALOR_CALC,\n"
                    + "       PARCELA.VALOR_PAG,\n"
                    + "       PARCELA.JUROS_PAG,\n"
                    + "       PARCELA.DATAALT,\n"
                    + "       PARCELA.NUMPAR,\n"
                    + "       PARCELA.TIPOPAG,\n"
                    + "       PARCELA.DATA_EXTRACAO,\n"
                    + "       PARCELA.DATA_ULT_ATUALIZACAO,\n"
                    + "       PARCELA.ID_CLIENTE,"
                    + "       PARCELA.AVALISTA,"
                    + "       PARCELA.ID_AVALISTA,\n"
                    + "       EXTRATOR.ID_EXTRATOR,\n"
                    + "       EXTRATOR.TIPO,\n"
                    + "       EXTRATOR.STATUS,\n"
                    + "       EXTRATOR.DATA_SPC,\n"
                    + "       EXTRATOR.ID_PARCELA,\n"
                    + "       EXTRATOR.DATA_SPC_AVALISTA,\n"
                    + "       EXTRATOR.DATA_FACMAT,\n"
                    + "       EXTRATOR.ORIGEM\n"
                    + "  FROM PARCELA, PESSOA, EXTRATOR\n"
                    + " WHERE PARCELA.id_avalista = pessoa.id_pessoa\n"
                    + " and PARCELA.id_parcela = extrator.id_parcela\n"
                    + " and extrator.id_extrator = ?";
            System.out.println(sqlSelectExtracaoAvalista);
            psListarInfoPessoal = Conexao.getConnection().prepareStatement(sqlSelectExtracaoAvalista);

            lpessoaModel = null;
            lpessoaModel = new ArrayList<>();
            psListarInfoPessoal.setString(1, pessoaModel.getIdExtrator());
            rsListarInfoPessoal = psListarInfoPessoal.executeQuery();
            indice = 1;
            while (rsListarInfoPessoal.next()) {
                inAvalTableModel = new PessoaModel();
                inAvalTableModel.setIndice(indice++);
                inAvalTableModel.setIdExtrator(rsListarInfoPessoal.getString("ID_EXTRATOR"));
                inAvalTableModel.setPontoFilial(rsListarInfoPessoal.getString("ID_FILIAL"));
                inAvalTableModel.setIdPessoa(rsListarInfoPessoal.getInt("ID_CLIENTE"));
                inAvalTableModel.setNomeRazaoSocial(rsListarInfoPessoal.getString("NOME"));
                inAvalTableModel.setCodigo(rsListarInfoPessoal.getString("AVALISTA"));
                inAvalTableModel.setTipoPessoa(rsListarInfoPessoal.getString("TIPO_PESSOA"));
                inAvalTableModel.setCpf(rsListarInfoPessoal.getString("CPF_RAZAO"));
                inAvalTableModel.setNumeroRG(rsListarInfoPessoal.getString("NUM_RG"));
                inAvalTableModel.setOrgaoEmissorRG(rsListarInfoPessoal.getString("ORGAO_EXPED"));
                inAvalTableModel.setDataEmissaoRG(rsListarInfoPessoal.getDate("DATE_EXPED"));
                inAvalTableModel.setSituacao(rsListarInfoPessoal.getString("SITUACAO"));
                inAvalTableModel.setNomeDoPai(rsListarInfoPessoal.getString("NOME_PAI"));
                inAvalTableModel.setNomeDaMae(rsListarInfoPessoal.getString("NOME_MAE"));
                inAvalTableModel.setEmail(rsListarInfoPessoal.getString("EMAIL"));
                inAvalTableModel.setEstadoCivil(rsListarInfoPessoal.getString("EST_CIVIL"));
                inAvalTableModel.setDataNascimento(rsListarInfoPessoal.getDate("DATE_NASC"));
                inAvalTableModel.setNumeroTel(rsListarInfoPessoal.getString("TELEFONE"));
                inAvalTableModel.setIdAvalista(rsListarInfoPessoal.getInt("ID_AVALISTA"));

                lpessoaModel.add(inAvalTableModel);

            }
            psListarInfoPessoal.close();
            rsListarInfoPessoal.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return lpessoaModel;

    }

    public void atualizarPessoa(String codCli, String codFil, int idCliente) throws ErroSistemaException {
        Connection connection = Conexao.getConnection();
        try {
            connection.setAutoCommit(false);
       
        sqlBuscaCliente = "SELECT CLIENTE.PTO,\n"
                + "       CLIENTE.CLI,\n"
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
                + "       CLIENTE.CEP\n"
                + "  FROM DB_INTEGRACAO.MR01 CLIENTE WHERE PTO = '" + codFil + "' AND CLI = '" + codCli+"'";
        sqlUpdatePessoas = ("UPDATE PESSOA SET NOME = ?, TIPO_PESSOA = ?, CPF_RAZAO = ?, CPF_ORIGEM = ?, NOME_PAI = ?, NOME_MAE = ?,"
                + " NUM_RG = ?, DATE_EXPED = ?, ORGAO_EXPED = ?, EMAIL = ?, TELEFONE = ?, DATE_NASC = ?, EST_CIVIL = ?, ENDERECO = ?, ID_LOGRADOURO = ?,"
                + " NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, CODIGO_IBGE = ?, CEP = ?, UF_ESTADO = ? WHERE ID_PESSOA = ?");
        try {
            
            psBuscaInfoAtuPessoa = Conexao.getConnection().prepareStatement(sqlBuscaCliente);
            rsBuscaInfoAtuPessoa = psBuscaInfoAtuPessoa.executeQuery();
            psInsertInfoAtuPessoa = connection.prepareStatement(sqlUpdatePessoas);
            if (rsBuscaInfoAtuPessoa.next()) {

                psInsertInfoAtuPessoa.setString(1, rsBuscaInfoAtuPessoa.getString("RAZ"));
                psInsertInfoAtuPessoa.setString(2, rsBuscaInfoAtuPessoa.getString("TIPO"));
                psInsertInfoAtuPessoa.setString(3, rsBuscaInfoAtuPessoa.getString("CGC"));
                if (rsBuscaInfoAtuPessoa.getString("CGC").length() < 14) {
                    psInsertInfoAtuPessoa.setString(4, rsBuscaInfoAtuPessoa.getString("CGC").substring(8, 9));
                } else {
                    psInsertInfoAtuPessoa.setString(4, " ");
                }
                psInsertInfoAtuPessoa.setString(5, rsBuscaInfoAtuPessoa.getString("FILPAI"));
                psInsertInfoAtuPessoa.setString(6, rsBuscaInfoAtuPessoa.getString("FILMAE"));
                psInsertInfoAtuPessoa.setString(7, rsBuscaInfoAtuPessoa.getString("INS"));
                psInsertInfoAtuPessoa.setDate(8, rsBuscaInfoAtuPessoa.getDate("DTEXPED"));
                psInsertInfoAtuPessoa.setString(9, rsBuscaInfoAtuPessoa.getString("EXPEDIDOR"));
                psInsertInfoAtuPessoa.setString(10, rsBuscaInfoAtuPessoa.getString("EMAIL"));
                if (rsBuscaInfoAtuPessoa.getString("FON").length() > 0) {
                    psInsertInfoAtuPessoa.setString(11, rsBuscaInfoAtuPessoa.getString("FON"));
                } else {
                    psInsertInfoAtuPessoa.setString(11, " ");
                }

                psInsertInfoAtuPessoa.setDate(12, rsBuscaInfoAtuPessoa.getDate("DTNASC"));

                psInsertInfoAtuPessoa.setString(13, rsBuscaInfoAtuPessoa.getString("ESTCIVIL"));
                psInsertInfoAtuPessoa.setString(14, rsBuscaInfoAtuPessoa.getString("END"));
                psInsertInfoAtuPessoa.setString(15, "2");// Id_logradouro 2 Rua
                psInsertInfoAtuPessoa.setString(16, rsBuscaInfoAtuPessoa.getString("NUM"));
                psInsertInfoAtuPessoa.setString(17, rsBuscaInfoAtuPessoa.getString("COMPL"));
                psInsertInfoAtuPessoa.setString(18, rsBuscaInfoAtuPessoa.getString("BAI"));
                psInsertInfoAtuPessoa.setString(19, rsBuscaInfoAtuPessoa.getString("CID"));
                psInsertInfoAtuPessoa.setString(20, rsBuscaInfoAtuPessoa.getString("CODIBGE"));
                psInsertInfoAtuPessoa.setString(21, rsBuscaInfoAtuPessoa.getString("CEP"));
                psInsertInfoAtuPessoa.setString(22, rsBuscaInfoAtuPessoa.getString("EST"));
                psInsertInfoAtuPessoa.setInt(23, idCliente);
                psInsertInfoAtuPessoa.executeUpdate();

            }
             connection.commit();
        } catch (ErroSistemaException ex) {
            connection.rollback();
            throw new ErroSistemaException("Não foi possível atualizar as informações do Cliente");

        } catch (SQLException ex) {
            connection.rollback();
            throw new ErroSistemaException("Não foi possível atualizar as informações do Cliente");
        }
    
         } catch (SQLException ex) {
            throw new ErroSistemaException("Não foi possível atualizar as informações do Cliente");
        }
    }

}
