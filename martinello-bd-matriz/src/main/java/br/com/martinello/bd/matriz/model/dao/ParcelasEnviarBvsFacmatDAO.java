/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.bd.matriz.model.domain.ProcessamentoModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarBvsFacmatDAO {

    private PreparedStatement psBaixaBvs, psInclusaoBvs, psretornoClienteBvs, psLogExtrator, psRetornoExtrator, psVerificaIdRegistrobvs, psConsultaInclusao;
    private ResultSet rsBaixaBvs, rsInclusaoBvs, rsRetornoExtrator, rsVerificaIdRegistrobvs, rsConsultaInclusao;
    public String sqlSelectParcelasEnviarBvs, sqlUpdateExtrator, sqlUpdateExtratorInclusaoErro, sqlSelectRetornoExtrator, sqlInsertLogRetorno, sqlSelectParcelasBaixaBvs,
            sqlUpdateExtratorBaixa, sqlUpdateExtratorBaixaErro, sqlUpdateParcelaInclusao, sqlSelectParcelas, updateRetornoConsulta, updateRetornoErro, sqlSelectRetornoExtratorConsulta;
    List<ParcelasEnviarModel> linclusaoBvsModel, lbaixaBvsModel, lconsultaBvs;
    private ProcessamentoModel processamentoModel = new ProcessamentoModel();
    private int resultadoInc, resultadoBaixa;

    public ParcelasEnviarBvsFacmatDAO() {

        sqlSelectParcelasEnviarBvs = "SELECT FILIAIS.ID_FILIAL,\n"
                + "       FILIAIS.COD_BVS,\n"
                + "       FILIAIS.COD_FACMAT,\n"
                + "       FILIAIS.CHAVE_FACMAT,\n"
                + "       FILIAIS.KEY_FACMAT,\n"
                + "       FILIAIS.STATUS_FACMAT,\n"
                + "       CLIENTE.TIPO_PESSOA,\n"
                + "       CLIENTE.CPF_RAZAO,\n"
                + "       CLIENTE.CPF_ORIGEM,\n"
                + "       CLIENTE.NOME,\n"
                + "       CLIENTE.NUM_RG,\n"
                + "       CLIENTE.ORGAO_EXPED,\n"
                + "       CLIENTE.DATE_EXPED,\n"
                + "       CLIENTE.DATE_NASC,\n"
                + "       CLIENTE.NOME_PAI,\n"
                + "       CLIENTE.NOME_MAE,\n"
                + "       CLIENTE.EMAIL,\n"
                + "       NVL(CLIENTE.TELEFONE, 0) TELEFONE,\n"
                + "       CLIENTE.EST_CIVIL,\n"
                + "       CLIENTE.ID_ESTCIVIL,\n"
                + "       CLIENTE.ENDERECO,\n"
                + "       CLIENTE.CEP,\n"
                + "       CLIENTE.ID_LOGRADOURO,\n"
                + "       CLIENTE.BAIRRO,\n"
                + "       CLIENTE.NUMERO,\n"
                + "       CLIENTE.COMPLEMENTO,\n"
                + "       CLIENTE.CIDADE,\n"
                + "       CLIENTE.UF_ESTADO,\n"
                + "       CLIENTE.CODIGO_IBGE,\n"
                + "       CLIENTE.TIPO_DEVEDOR,\n"
                + "       NVL((CLIENTE.SEXO), ' ') AS SEXO,\n"
                + "       AVALISTA.TIPO_PESSOA AS TIPO_PESSOA_AVAL,\n"
                + "       AVALISTA.CPF_RAZAO AS CPF_RAZAO_AVAL,\n"
                + "       AVALISTA.CPF_ORIGEM AS CPF_ORIGEM_AVAL,\n"
                + "       AVALISTA.NOME AS NOME_AVAL,\n"
                + "       AVALISTA.NUM_RG AS NUM_RG_AVAL,\n"
                + "       AVALISTA.ORGAO_EXPED AS ORGAO_EXPED_AVAL,\n"
                + "       AVALISTA.DATE_EXPED AS DATE_EXPED_AVAL,\n"
                + "       AVALISTA.DATE_NASC AS DATE_NASC_AVAL,\n"
                + "       AVALISTA.NOME_PAI AS NOME_PAI_AVAL,\n"
                + "       AVALISTA.NOME_MAE AS NOME_MAE_AVAL,\n"
                + "       AVALISTA.EMAIL AS EMAIL_AVAL,\n"
                + "       AVALISTA.TELEFONE AS TELEFONE_AVAL,\n"
                + "       AVALISTA.EST_CIVIL AS EST_CIVIL_AVAL,\n"
                + "       AVALISTA.ID_ESTCIVIL AS ID_ESTCIVIL_AVAL,\n"
                + "       AVALISTA.ENDERECO AS ENDERECO_AVAL,\n"
                + "       AVALISTA.ID_LOGRADOURO AS ID_LOGRADOURO_AVAL,\n"
                + "       AVALISTA.CEP AS CEP_AVAL,\n"
                + "       AVALISTA.BAIRRO AS BAIRRO_AVAL,\n"
                + "       AVALISTA.NUMERO AS NUMERO_AVAL,\n"
                + "       AVALISTA.COMPLEMENTO AS COMPLEMENTO_AVAL,\n"
                + "       AVALISTA.CIDADE AS CIDADE_AVAL,\n"
                + "       AVALISTA.UF_ESTADO AS UF_ESTADO_AVAL,\n"
                + "       AVALISTA.CODIGO_IBGE AS CODIGO_IBGE_AVAL,\n"
                + "       AVALISTA.TIPO_DEVEDOR AS TIPO_DEVEDOR_AVAL,\n"
                + "       PARCELA.ID_PARCELA,\n"
                + "       PARCELA.ID_FILIAL,\n"
                + "       PARCELA.NUMERO_DOC,\n"
                + "       PARCELA.DATALAN,\n"
                + "       PARCELA.VENC,\n"
                + "       PARCELA.VALOR,\n"
                + "       PARCELA.DATAPAG,\n"
                + "       PARCELA.DATA_NEGATIVADA,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.ID_REGISTRO_BVS,\n"
                + "       PARCELA.DATA_BAIXA,\n"
                + "       PARCELA.INCLUIR_AVAL,\n"
                + "       EXTRATOR.ID_EXTRATOR,\n"
                + "       EXTRATOR.STATUS,\n"
                + "       EXTRATOR.STATUS_FACMAT,\n"
                + "       EXTRATOR.TIPO,\n"
                + "       ID_NATUREZA_INC_BVS,\n"
                + "       ID_NATUREZA_INC_SPC,\n"
                + "       ID_MOTIVO_EXCLUSAO_SPC,\n"
                + "       ID_MOTIVO_EXCLUSAO_BVS,\n"
                + "       ID_MOTIVO_INC_BVS,\n"
                + "       BLOQUEAR_REGISTRO\n"
                + "  FROM PARCELA\n"
                + " RIGHT JOIN PESSOA CLIENTE\n"
                + "    ON CLIENTE.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "  JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "  JOIN EXTRATOR\n"
                + "    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "  LEFT OUTER JOIN PESSOA AVALISTA\n"
                + "    ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + " WHERE EXTRATOR.STATUS_FACMAT IN ('P', 'E')\n"
                + "   AND EXTRATOR.TIPO = 'I'\n"
                + "   AND EXTRATOR.DATA_FACMAT = '31/12/1900'\n"
                + "   AND EXTRATOR.STATUS NOT IN ('F','FE', 'I', 'PR')";

        sqlUpdateExtrator = ("UPDATE EXTRATOR SET STATUS_FACMAT = ? WHERE ID_EXTRATOR = ?");

        sqlSelectRetornoExtrator = "SELECT ID_RETORNO, CODIGO, DESCRICAO FROM RETORNOS_INTEGRACAO WHERE CODIGO = ? AND TIPO = 'B'";

        sqlSelectRetornoExtratorConsulta = "SELECT ID_RETORNO, CODIGO, DESCRICAO FROM RETORNOS_INTEGRACAO WHERE CODIGO = ? AND TIPO = 'C'";

        sqlInsertLogRetorno = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, ORIGEM, STATUS)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";

        sqlSelectParcelasBaixaBvs = "SELECT FILIAIS.ID_FILIAL,\n"
                + "                       FILIAIS.COD_BVS,\n"
                + "                       FILIAIS.COD_FACMAT,\n"
                + "                       FILIAIS.CHAVE_FACMAT,\n"
                + "                       FILIAIS.KEY_FACMAT,\n"
                + "                       CLIENTE.TIPO_PESSOA,\n"
                + "                       CLIENTE.CPF_RAZAO,\n"
                + "                       CLIENTE.CPF_ORIGEM,\n"
                + "                       CLIENTE.NOME,\n"
                + "                       NVL(CLIENTE.SEXO,' ') SEXO,\n"
                + "                       PARCELA.ID_PARCELA,\n"
                + "                       PARCELA.ID_FILIAL,\n"
                + "                       PARCELA.NUMERO_DOC,\n"
                + "                       PARCELA.DATALAN,\n"
                + "                       PARCELA.VENC,\n"
                + "                       PARCELA.ID_AVALISTA,\n"
                + "                       PARCELA.DATA_BAIXA,\n"
                + "                       PARCELA.ID_REGISTRO_BVS,\n"
                + "                       PARCELA.PRESCRITO,\n"
                + "                       EXTRATOR.ID_EXTRATOR,\n"
                + "                       EXTRATOR.STATUS,\n"
                + "                       EXTRATOR.TIPO,\n"
                + "                       EXTRATOR.ID_MOTIVO_EXCLUSAO_BVS,\n"
                + "                       MOTIVO_BAIXA.ID_MTV_BAIXA,\n"
                + "                       MOTIVO_BAIXA.MOTIVO_BAIXA\n"
                + "                  FROM PARCELA\n"
                + "                 RIGHT JOIN PESSOA CLIENTE\n"
                + "                    ON CLIENTE.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                  JOIN FILIAIS\n"
                + "                    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "                  JOIN EXTRATOR\n"
                + "                    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "                  LEFT OUTER JOIN PESSOA AVALISTA\n"
                + "                    ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + "                 INNER JOIN MOTIVO_BAIXA\n"
                + "                    ON MOTIVO_BAIXA.ID_MTV_BAIXA = EXTRATOR.ID_MOTIVO_EXCLUSAO_BVS\n"
                + "                    AND MOTIVO_BAIXA.LOCAL_BAIXA = 'BVS'\n"
                + "                 WHERE EXTRATOR.TIPO = 'B'\n"
                + "                   AND EXTRATOR.DATA_FACMAT = '31/12/1900' AND EXTRATOR.STATUS_FACMAT IN('P', 'E') AND EXTRATOR.STATUS NOT IN ('F','FE', 'I', 'PR')";

        sqlUpdateParcelaInclusao = ("UPDATE PARCELA SET ID_REGISTRO_BVS = ? DATA_ULT_ATUALIZACAO = ? WHERE ID_PARCELA = ?");

    }

    public List<ParcelasEnviarModel> BuscarBvsDAO() throws ErroSistemaException {
        try {
            linclusaoBvsModel = new ArrayList<>();
            psInclusaoBvs = Conexao.getConnection().prepareStatement(sqlSelectParcelasEnviarBvs);
            rsInclusaoBvs = psInclusaoBvs.executeQuery();
            ParcelasEnviarModel inclusaoBvsModel = new ParcelasEnviarModel();
            while (rsInclusaoBvs.next()) {
                inclusaoBvsModel = new ParcelasEnviarModel();
                inclusaoBvsModel.setTipoPessoa(rsInclusaoBvs.getString("TIPO_PESSOA"));
                inclusaoBvsModel.setCpf(rsInclusaoBvs.getString("CPF_RAZAO"));
                inclusaoBvsModel.setCpfOrigem(rsInclusaoBvs.getString("CPF_ORIGEM"));
                inclusaoBvsModel.setNomeRazaoSocial(rsInclusaoBvs.getString("NOME"));
                inclusaoBvsModel.setNumeroRG(rsInclusaoBvs.getString("NUM_RG"));
                inclusaoBvsModel.setOrgaoEmissorRG(rsInclusaoBvs.getString("ORGAO_EXPED"));
                inclusaoBvsModel.setDataEmissaoRG(rsInclusaoBvs.getDate("DATE_EXPED"));
                inclusaoBvsModel.setDataNascimento(rsInclusaoBvs.getDate("DATE_NASC"));
                inclusaoBvsModel.setNomeDoPai(rsInclusaoBvs.getString("NOME_PAI"));
                inclusaoBvsModel.setNomeDaMae(rsInclusaoBvs.getString("NOME_MAE"));
                inclusaoBvsModel.setEmail(rsInclusaoBvs.getString("EMAIL").trim());
                inclusaoBvsModel.setTipoParcela(rsInclusaoBvs.getString("TIPO").trim());
                if (rsInclusaoBvs.getString("sexo").trim().equals("")) {
                    inclusaoBvsModel.setSexo("");
                } else {
                    inclusaoBvsModel.setSexo(rsInclusaoBvs.getString("SEXO"));
                }
                if (rsInclusaoBvs.getString("TELEFONE").length() > 8) {
                    inclusaoBvsModel.setDddNumeroTel(rsInclusaoBvs.getString("TELEFONE").substring(0, 2));
                    inclusaoBvsModel.setNumeroTel(rsInclusaoBvs.getString("TELEFONE").substring(2));
                } else {
                    inclusaoBvsModel.setDddNumeroTel("");
                    inclusaoBvsModel.setNumeroTel("");
                }
                inclusaoBvsModel.setStatusFacmat(rsInclusaoBvs.getString("STATUS_FACMAT"));
                inclusaoBvsModel.setIdRegistroFacmat(rsInclusaoBvs.getInt("ID_REGISTRO_BVS"));
                inclusaoBvsModel.setEstadoCivil(rsInclusaoBvs.getString("EST_CIVIL"));
                inclusaoBvsModel.setIdEstadoCivil(rsInclusaoBvs.getString("ID_ESTCIVIL"));
                inclusaoBvsModel.setEndLogradouro(rsInclusaoBvs.getString("ENDERECO"));
                inclusaoBvsModel.setEndIdLogradouro(rsInclusaoBvs.getString("ID_LOGRADOURO"));
                inclusaoBvsModel.setEndBairro(rsInclusaoBvs.getString("BAIRRO"));
                inclusaoBvsModel.setEndNumero(rsInclusaoBvs.getString("NUMERO"));
                inclusaoBvsModel.setEndComplemento(rsInclusaoBvs.getString("COMPLEMENTO"));
                inclusaoBvsModel.setCep(rsInclusaoBvs.getString("CEP"));
                inclusaoBvsModel.setCodigoIbge(rsInclusaoBvs.getString("CODIGO_IBGE"));
                inclusaoBvsModel.setNomeCidade(rsInclusaoBvs.getString("CIDADE"));
                inclusaoBvsModel.setUfEstado(rsInclusaoBvs.getString("UF_ESTADO"));
                inclusaoBvsModel.setPontoFilial(rsInclusaoBvs.getString("ID_FILIAL"));
                inclusaoBvsModel.setChaveFacmat(rsInclusaoBvs.getString("CHAVE_FACMAT"));
                inclusaoBvsModel.setKeyFacmat(rsInclusaoBvs.getString("KEY_FACMAT"));
                inclusaoBvsModel.setCodigoBvs(rsInclusaoBvs.getString("COD_BVS"));
                inclusaoBvsModel.setCodigoFacmat(rsInclusaoBvs.getString("COD_FACMAT"));
                inclusaoBvsModel.setStatusFacmat(rsInclusaoBvs.getString("STATUS_FACMAT"));
                inclusaoBvsModel.setDataLancamento(rsInclusaoBvs.getDate("DATALAN"));
                inclusaoBvsModel.setIdMotivoInclusaoBvs(rsInclusaoBvs.getString("ID_MOTIVO_INC_BVS"));
                inclusaoBvsModel.setIdNaturezaRegistroBvs(rsInclusaoBvs.getString("ID_NATUREZA_INC_BVS"));
                inclusaoBvsModel.setDataVencimento(rsInclusaoBvs.getDate("VENC"));
                inclusaoBvsModel.setTipoDevedor(rsInclusaoBvs.getString("TIPO_DEVEDOR"));
                inclusaoBvsModel.setNumeroDoContrato(rsInclusaoBvs.getString("NUMERO_DOC"));
                inclusaoBvsModel.setValorParcela(rsInclusaoBvs.getBigDecimal("VALOR").setScale(2));
                inclusaoBvsModel.setIdParcela(rsInclusaoBvs.getInt("ID_PARCELA"));
                inclusaoBvsModel.setAvalista(null);

                inclusaoBvsModel.setIdExtrator(rsInclusaoBvs.getInt("ID_EXTRATOR"));
                if (rsInclusaoBvs.getString("ID_AVALISTA") != null && rsInclusaoBvs.getString("INCLUIR_AVAL").equals("S")) {
                    inclusaoBvsModel.setAvalista(rsInclusaoBvs.getString("ID_AVALISTA"));
                    inclusaoBvsModel.setTipoPessoaAval(rsInclusaoBvs.getString("TIPO_PESSOA_AVAL"));
                    inclusaoBvsModel.setCpfAval(rsInclusaoBvs.getString("CPF_RAZAO_AVAL"));
                    inclusaoBvsModel.setCpfAvalOrigem(rsInclusaoBvs.getString("CPF_ORIGEM_AVAL"));
                    inclusaoBvsModel.setNomeRazaoSocialAval(rsInclusaoBvs.getString("NOME_AVAL"));
                    inclusaoBvsModel.setNumeroRGAval(rsInclusaoBvs.getString("NUM_RG_AVAL"));
                    inclusaoBvsModel.setOrgaoEmissorRGAval(rsInclusaoBvs.getString("ORGAO_EXPED_AVAL"));
                    inclusaoBvsModel.setDataEmissaoRGAval(rsInclusaoBvs.getDate("DATE_EXPED_AVAL"));
                    inclusaoBvsModel.setDataNascimentoAval(rsInclusaoBvs.getDate("DATE_NASC_AVAL"));
                    inclusaoBvsModel.setNomeDoPaiAval(rsInclusaoBvs.getString("NOME_PAI_AVAL"));
                    inclusaoBvsModel.setNomeDaMaeAval(rsInclusaoBvs.getString("NOME_MAE_AVAL"));
                    inclusaoBvsModel.setEmailAval(rsInclusaoBvs.getString("EMAIL_AVAL").trim());
                    if (rsInclusaoBvs.getString("TELEFONE_AVAL").length() > 8) {
                        inclusaoBvsModel.setDddNumeroTelAval(rsInclusaoBvs.getString("TELEFONE_AVAL").substring(0, 2));
                    } else {
                        inclusaoBvsModel.setDddNumeroTel("");
                    }
                    inclusaoBvsModel.setNumeroTelAval(rsInclusaoBvs.getString("TELEFONE_AVAL"));
                    inclusaoBvsModel.setEstadoCivilAval(rsInclusaoBvs.getString("EST_CIVIL_AVAL"));
                    inclusaoBvsModel.setIdEstadoCivilAval(rsInclusaoBvs.getString("ID_ESTCIVIL_AVAL"));
                    inclusaoBvsModel.setDataLancamento(rsInclusaoBvs.getDate("DATALAN"));
                    inclusaoBvsModel.setTipoDevedorAval(rsInclusaoBvs.getString("TIPO_DEVEDOR_AVAL"));
                    inclusaoBvsModel.setNumeroDoContrato(rsInclusaoBvs.getString("NUMERO_DOC"));
                    inclusaoBvsModel.setEndLogradouroAval(rsInclusaoBvs.getString("ENDERECO_AVAL"));
                    inclusaoBvsModel.setEndIdLogradouroAval(rsInclusaoBvs.getString("ID_LOGRADOURO_AVAL"));
                    inclusaoBvsModel.setEndBairroAval(rsInclusaoBvs.getString("BAIRRO_AVAL"));
                    inclusaoBvsModel.setEndNumeroAval(rsInclusaoBvs.getString("NUMERO_AVAL"));
                    inclusaoBvsModel.setEndComplementoAval(rsInclusaoBvs.getString("COMPLEMENTO_AVAL"));
                    inclusaoBvsModel.setCepAval(rsInclusaoBvs.getString("CEP_AVAL"));
                    inclusaoBvsModel.setCodigoIbgeAval(rsInclusaoBvs.getString("CODIGO_IBGE"));
                    inclusaoBvsModel.setNomeCidadeAval(rsInclusaoBvs.getString("CIDADE_AVAL"));
                    inclusaoBvsModel.setUfEstadoAval(rsInclusaoBvs.getString("UF_ESTADO_AVAL"));
                }
                linclusaoBvsModel.add(inclusaoBvsModel);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return linclusaoBvsModel;

    }

    public void finalizarBaixasNaoIncluidasFacmat() {

    }

    public List<ParcelasEnviarModel> BuscarBaixaBvsDAO() throws ErroSistemaException {
        try {
            lbaixaBvsModel = new ArrayList<>();
            psBaixaBvs = Conexao.getConnection().prepareStatement(sqlSelectParcelasBaixaBvs);
            rsBaixaBvs = psBaixaBvs.executeQuery();
            ParcelasEnviarModel baixaBvsModel = new ParcelasEnviarModel();
            while (rsBaixaBvs.next()) {
                baixaBvsModel = new ParcelasEnviarModel();
                baixaBvsModel.setChaveFacmat(rsBaixaBvs.getString("CHAVE_FACMAT"));
                baixaBvsModel.setKeyFacmat(rsBaixaBvs.getString("KEY_FACMAT"));
                baixaBvsModel.setCodigoBvs(rsBaixaBvs.getString("COD_BVS"));
                baixaBvsModel.setCodigoFacmat(rsBaixaBvs.getString("COD_FACMAT"));
                baixaBvsModel.setIdExtrator(rsBaixaBvs.getInt("ID_EXTRATOR"));
                baixaBvsModel.setIdParcela(rsBaixaBvs.getInt("ID_PARCELA"));
                baixaBvsModel.setIdRegistroFacmat(rsBaixaBvs.getInt("ID_REGISTRO_BVS"));
                baixaBvsModel.setIdMotivoBaixaBvs(rsBaixaBvs.getString("ID_MOTIVO_EXCLUSAO_BVS"));
                baixaBvsModel.setTipoParcela(rsBaixaBvs.getString("TIPO").trim());
                System.out.println("motivo baixa" + rsBaixaBvs.getString("ID_MOTIVO_EXCLUSAO_BVS"));
                lbaixaBvsModel.add(baixaBvsModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lbaixaBvsModel;
    }

    public boolean retornoBvsInclusaoDAO(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage, String incluirBvsIdRetorno) throws ErroSistemaException {
        sqlUpdateParcelaInclusao = ("UPDATE PARCELA SET ID_REGISTRO_BVS = ?, DATA_ULT_ATUALIZACAO = ? WHERE ID_PARCELA = ?");
        sqlSelectParcelas = "SELECT ID_REGISTRO_BVS FROM PARCELA WHERE ID_PARCELA = ? AND TRIM(PARCELA.ID_REGISTRO_BVS) IS NULL";
        try {
            /* Sucesso no envio para o Facmat */
            if (bvsMenssage.contains("true")) {
                psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateParcelaInclusao);
                psretornoClienteBvs.setString(1, incluirBvsIdRetorno);
                psretornoClienteBvs.setDate(2, Utilitarios.converteData(new Date()));
                psretornoClienteBvs.setInt(3, parcelasEnviarBvsModel.getIdParcela());
                psretornoClienteBvs.execute();
                psretornoClienteBvs.close();

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                psRetornoExtrator.setString(1, "A");// AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                psRetornoExtrator.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                psRetornoExtrator.execute();

//             
                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                psLogExtrator.setInt(2, 143);
                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                psLogExtrator.setString(4, "CLIENTE ENVIADO PARA FACMAT AGUARDANDO");
                psLogExtrator.setString(5, "I");
                psLogExtrator.setString(6, "S");
                psLogExtrator.execute();

            } else if (bvsMenssage.equals("28")) {
                /* Mensagem 28, significa que o registro já existe e é devolvido o ID */
                psVerificaIdRegistrobvs = Conexao.getConnection().prepareStatement(sqlSelectParcelas);
                psVerificaIdRegistrobvs.setInt(1, parcelasEnviarBvsModel.getIdParcela());
                rsVerificaIdRegistrobvs = psVerificaIdRegistrobvs.executeQuery();
                /* Parcela não possui o ID, gravo na extração o ID */
                if (rsVerificaIdRegistrobvs.next()) {
                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateParcelaInclusao);
                    psretornoClienteBvs.setString(1, incluirBvsIdRetorno);
                    psretornoClienteBvs.setDate(2, Utilitarios.converteData(new Date()));
                    psretornoClienteBvs.setInt(3, parcelasEnviarBvsModel.getIdParcela());
                    psretornoClienteBvs.execute();
                    psretornoClienteBvs.close();
                }

                /* Mudo o Status para Aguardando. */
                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                psRetornoExtrator.setString(1, "A");// AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                psRetornoExtrator.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                psRetornoExtrator.execute();

                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                psLogExtrator.setInt(2, 143);
                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                psLogExtrator.setString(4, "CLIENTE ENVIADO PARA FACMAT AGUARDANDO");
                psLogExtrator.setString(5, "I");
                psLogExtrator.setString(6, "S");
                psLogExtrator.execute();

                psVerificaIdRegistrobvs.close();
                rsVerificaIdRegistrobvs.close();
            } else {
                /* Verifica na tabela de retorno os cód. correspondente devolvido da Facmat. */
                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                psRetornoExtrator.setString(1, bvsMenssage);
                rsRetornoExtrator = psRetornoExtrator.executeQuery();
                if (rsRetornoExtrator.next()) {
                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                    psretornoClienteBvs.setString(1, "E"); // AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                    psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                    psretornoClienteBvs.execute();
                    psretornoClienteBvs.close();

                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                    psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                    psLogExtrator.setInt(2, rsRetornoExtrator.getInt("ID_RETORNO"));
                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                    psLogExtrator.setString(4, bvsMenssage);
                    psLogExtrator.setString(5, "I");
                    psLogExtrator.setString(6, "E");
                    psLogExtrator.execute();

                } else {
                    /* Se não encontrou um cód. correspondente então joga no genérico */
                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                    psretornoClienteBvs.setString(1, "E"); // AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                    psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                    psretornoClienteBvs.execute();
                    psretornoClienteBvs.close();

                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                    psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                    psLogExtrator.setInt(2, 199);
                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                    psLogExtrator.setString(4, bvsMenssage);
                    psLogExtrator.setString(5, "I");
                    psLogExtrator.setString(6, "E");
                    psLogExtrator.execute();

                }

                rsRetornoExtrator.close();
            }
            psRetornoExtrator.close();
            psLogExtrator.close();

            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;

    }

    public boolean retornoBvsAtualizacaoInclusaoDAO(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage, String incluirBvsIdRetorno) throws ErroSistemaException {
        sqlUpdateParcelaInclusao = ("UPDATE PARCELA SET ID_REGISTRO_BVS = ?, DATA_ULT_ATUALIZACAO = ? WHERE ID_PARCELA = ?");

        try {
            /* Sucesso no envio para o Facmat */
            if (bvsMenssage.contains("true")) {
                psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateParcelaInclusao);
                psretornoClienteBvs.setString(1, incluirBvsIdRetorno);
                psretornoClienteBvs.setDate(2, Utilitarios.converteData(new Date()));
                psretornoClienteBvs.setInt(3, parcelasEnviarBvsModel.getIdParcela());
                psretornoClienteBvs.execute();
                psretornoClienteBvs.close();

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                psRetornoExtrator.setString(1, "A");// AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                psRetornoExtrator.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                psRetornoExtrator.execute();

//             
                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                psLogExtrator.setInt(2, 143);
                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                psLogExtrator.setString(4, "CLIENTE ATUALIZADO FACMAT AGUARDANDO");
                psLogExtrator.setString(5, "I");
                psLogExtrator.setString(6, "S");
                psLogExtrator.execute();

            }
//else if (bvsMenssage.equals("28")) {
//                /* Mensagem 28, significa que o registro já existe e é devolvido o ID */
//                psVerificaIdRegistrobvs = Conexao.getConnection().prepareStatement(sqlSelectParcelas);
//                psVerificaIdRegistrobvs.setInt(1, parcelasEnviarBvsModel.getIdParcela());
//                rsVerificaIdRegistrobvs = psVerificaIdRegistrobvs.executeQuery();
//                /* Parcela não possui o ID, gravo na extração o ID */
//                if (rsVerificaIdRegistrobvs.next()) {
//                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateParcelaInclusao);
//                    psretornoClienteBvs.setString(1, incluirBvsIdRetorno);
//                    psretornoClienteBvs.setDate(2, Utilitarios.converteData(new Date()));
//                    psretornoClienteBvs.setInt(3, parcelasEnviarBvsModel.getIdParcela());
//                    psretornoClienteBvs.execute();
//                    psretornoClienteBvs.close();
//                }
//
//                /* Mudo o Status para Aguardando. */
//                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
//                psRetornoExtrator.setString(1, "A");// AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
//                psRetornoExtrator.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
//                psRetornoExtrator.execute();
//
//                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
//                psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
//                psLogExtrator.setInt(2, 143);
//                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
//                psLogExtrator.setString(4, "CLIENTE ENVIADO PARA FACMAT AGUARDANDO");
//                psLogExtrator.setString(5, "I");
//                psLogExtrator.setString(6, "S");
//                psLogExtrator.execute();
//
//                psVerificaIdRegistrobvs.close();
//                rsVerificaIdRegistrobvs.close();
//            } else {
//                /* Verifica na tabela de retorno os cód. correspondente devolvido da Facmat. */
//                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
//                psRetornoExtrator.setString(1, bvsMenssage);
//                rsRetornoExtrator = psRetornoExtrator.executeQuery();
//                if (rsRetornoExtrator.next()) {
//                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
//                    psretornoClienteBvs.setString(1, "E"); // AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
//                    psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
//                    psretornoClienteBvs.execute();
//                    psretornoClienteBvs.close();
//
//                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
//                    psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
//                    psLogExtrator.setInt(2, rsRetornoExtrator.getInt("ID_RETORNO"));
//                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
//                    psLogExtrator.setString(4, bvsMenssage);
//                    psLogExtrator.setString(5, "I");
//                    psLogExtrator.setString(6, "E");
//                    psLogExtrator.execute();
//
//                } else {
//                    /* Se não encontrou um cód. correspondente então joga no genérico */
//                    psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
//                    psretornoClienteBvs.setString(1, "E"); // AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
//                    psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
//                    psretornoClienteBvs.execute();
//                    psretornoClienteBvs.close();
//
//                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
//                    psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
//                    psLogExtrator.setInt(2, 199);
//                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
//                    psLogExtrator.setString(4, bvsMenssage);
//                    psLogExtrator.setString(5, "I");
//                    psLogExtrator.setString(6, "E");
//                    psLogExtrator.execute();
//
//                }
//
//                rsRetornoExtrator.close();
//            }
            psRetornoExtrator.close();
            psLogExtrator.close();

            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;

    }

    public boolean retornoBaixaBvsDAO(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage) throws ErroSistemaException {
        try {
            if (bvsMenssage.equalsIgnoreCase("true")) {
                psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                psretornoClienteBvs.setString(1, "A");
                psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                psretornoClienteBvs.execute();
                psretornoClienteBvs.close();

                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                psLogExtrator.setInt(2, 143);
                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                psLogExtrator.setString(4, "CLIENTE ENVIADO PARA FACMAT");
                psLogExtrator.setString(5, "B");
                psLogExtrator.setString(6, "S");
                psLogExtrator.execute();
                psLogExtrator.close();
            } else {
                if (bvsMenssage.equals("36") || bvsMenssage.equals("37")) {
                    updateRetornoConsulta = "UPDATE EXTRATOR SET DATA_FACMAT = ?, STATUS_FACMAT = ? WHERE ID_EXTRATOR = ?";
                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                    if (bvsMenssage.equals("37")) {
                        psretornoClienteBvs = Conexao.getConnection().prepareStatement(updateRetornoConsulta);
                        psretornoClienteBvs.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
                        psretornoClienteBvs.setString(2, "S");
                        psretornoClienteBvs.setInt(3, parcelasEnviarBvsModel.getIdExtrator());
                        psretornoClienteBvs.execute();
                        psretornoClienteBvs.close();
                        psLogExtrator.setInt(2, 202);
                        psLogExtrator.setString(4, "CLIENTE JÁ BAIXADO ANTERIORMENTE, VERIFIQUE!");
                    }
                    if (bvsMenssage.equals("36")) {
                        psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                        psretornoClienteBvs.setString(1, "S");
                        psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                        psretornoClienteBvs.execute();
                        psretornoClienteBvs.close();
                        psLogExtrator.setInt(2, 203);
                        psLogExtrator.setString(4, "CLIENTE SEM ID_REGISTRO FACMAT, VERIFIQUE!");
                    }

                    psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                    psLogExtrator.setString(5, "B");
                    psLogExtrator.setString(6, "S");
                    psLogExtrator.execute();
                    psLogExtrator.close();
                } else {

                    psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                    psRetornoExtrator.setString(1, bvsMenssage.toUpperCase());
                    rsRetornoExtrator = psRetornoExtrator.executeQuery();

                    if (rsRetornoExtrator.next()) {
                        psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                        psretornoClienteBvs.setString(1, "E");
                        psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                        psretornoClienteBvs.executeUpdate();
                        psretornoClienteBvs.close();

                        psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                        psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                        psLogExtrator.setInt(2, rsRetornoExtrator.getInt("ID_RETORNO"));
                        psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                        psLogExtrator.setString(4, rsRetornoExtrator.getString("DESCRICAO"));
                        psLogExtrator.setString(5, "B");
                        psLogExtrator.setString(6, "E");
                        psLogExtrator.executeUpdate();
                        psLogExtrator.close();

                    } else {
                        psretornoClienteBvs = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                        psretornoClienteBvs.setString(1, "E"); // AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                        psretornoClienteBvs.setInt(2, parcelasEnviarBvsModel.getIdExtrator());
                        psretornoClienteBvs.executeUpdate();
                        psretornoClienteBvs.close();

                        psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                        psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
                        psLogExtrator.setInt(2, 199);
                        psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                        psLogExtrator.setString(4, bvsMenssage);
                        psLogExtrator.setString(5, "B");
                        psLogExtrator.setString(6, "E");
                        psLogExtrator.executeUpdate();
                        psLogExtrator.close();
                    }
                    psRetornoExtrator.close();
                    rsRetornoExtrator.close();
                }
            }
            Conexao.getConnection().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;
    }

    public List<ParcelasEnviarModel> parcelasConsultarInclusao() throws ErroSistemaException {
        lconsultaBvs = new ArrayList<>();
        String selectConsultaInclusao = "SELECT PARCELA.ID_PARCELA,\n"
                + "                       PARCELA.ID_REGISTRO_BVS,\n"
                + "                       PARCELA.NUMERO_DOC,\n"
                + "                       PARCELA.ID_CLIENTE,\n"
                + "                       PARCELA.ID_FILIAL,\n"
                + "                       EXTRATOR.ID_EXTRATOR,\n"
                + "                       EXTRATOR.DATA_FACMAT,\n"
                + "                       EXTRATOR.STATUS_FACMAT,"
                + "                       EXTRATOR.TIPO,\n"
                + "                       FILIAIS.CHAVE_FACMAT,\n"
                + "                       FILIAIS.KEY_FACMAT\n"
                + "                  FROM PARCELA\n"
                + "                 INNER JOIN EXTRATOR\n"
                + "                    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "                    INNER JOIN FILIAIS\n"
                + "                    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "                 WHERE EXTRATOR.STATUS_FACMAT = 'A'\n"
                + "                   AND PARCELA.ID_REGISTRO_BVS IS NOT NULL";
        try {
            psConsultaInclusao = Conexao.getConnection().prepareStatement(selectConsultaInclusao);
            rsConsultaInclusao = psConsultaInclusao.executeQuery();
            while (rsConsultaInclusao.next()) {
                ParcelasEnviarModel parcelaConsulta = new ParcelasEnviarModel();
                parcelaConsulta.setIdExtrator(rsConsultaInclusao.getInt("ID_EXTRATOR"));
                parcelaConsulta.setIdRegistroFacmat(rsConsultaInclusao.getInt("ID_REGISTRO_BVS"));
                parcelaConsulta.setPontoFilial(rsConsultaInclusao.getString("ID_FILIAL"));
                parcelaConsulta.setNumeroDoContrato(rsConsultaInclusao.getString("NUMERO_DOC"));
                parcelaConsulta.setCodCliente(rsConsultaInclusao.getString("ID_CLIENTE"));
                parcelaConsulta.setChaveFacmat(rsConsultaInclusao.getString("CHAVE_FACMAT"));
                parcelaConsulta.setKeyFacmat(rsConsultaInclusao.getString("KEY_FACMAT"));
                parcelaConsulta.setTipoParcela(rsConsultaInclusao.getString("TIPO"));
                lconsultaBvs.add(parcelaConsulta);
            }
            int resultado;
            resultado = psConsultaInclusao.executeUpdate();
            if (resultado == -1) {
                psConsultaInclusao.close();
                //  rsConsultaInclusao.close();

            } else {
                psConsultaInclusao.close();
                rsConsultaInclusao.close();

            }
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return lconsultaBvs;

    }

    public boolean retornoConsultaInclusao(ParcelasEnviarModel parcelasConsultarBvsModel, String consultaBvsMenssage) throws ErroSistemaException {

        updateRetornoConsulta = "UPDATE EXTRATOR SET DATA_FACMAT = ?, STATUS_FACMAT = ? WHERE ID_EXTRATOR = ?";
        updateRetornoErro = ("UPDATE EXTRATOR SET STATUS_FACMAT = ? WHERE ID_EXTRATOR = ?");
        try {
            if (consultaBvsMenssage.equalsIgnoreCase("sucesso")) {

                psRetornoExtrator = Conexao.getConnection().prepareStatement(updateRetornoConsulta);
                psRetornoExtrator.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
                psRetornoExtrator.setString(2, "S");
                psRetornoExtrator.setInt(3, parcelasConsultarBvsModel.getIdExtrator());
                psRetornoExtrator.execute();
                psRetornoExtrator.close();

                psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                psLogExtrator.setInt(1, parcelasConsultarBvsModel.getIdExtrator());
                psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("I")) {
                    psLogExtrator.setInt(2, 304);
                    psLogExtrator.setString(4, "INCLUSAO EFETUADA COM SUCESSO");
                    psLogExtrator.setString(5, "I");
                    psLogExtrator.setString(6, "S");
                } else {
                    psLogExtrator.setInt(2, 278);
                    psLogExtrator.setString(4, "EXCLUSAO EFETUADA COM SUCESSO");
                    psLogExtrator.setString(5, "I");
                    psLogExtrator.setString(6, "S");
                }

                psLogExtrator.execute();
                psLogExtrator.close();

            } else {

                if (consultaBvsMenssage.equals("37")) {
                    psRetornoExtrator = Conexao.getConnection().prepareStatement(updateRetornoConsulta);
                    psRetornoExtrator.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
                    psRetornoExtrator.setString(2, "S");
                    psRetornoExtrator.setInt(3, parcelasConsultarBvsModel.getIdExtrator());
                    psRetornoExtrator.execute();
                    psRetornoExtrator.close();

                    psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                    psLogExtrator.setInt(1, parcelasConsultarBvsModel.getIdExtrator());
                    psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));

                    psLogExtrator.setInt(2, 278);
                    psLogExtrator.setString(4, "EXCLUSAO EFETUADA COM SUCESSO");
                    psLogExtrator.setString(5, "I");
                    psLogExtrator.setString(6, "S");

                    psLogExtrator.execute();
                    psLogExtrator.close();
                } else {
                    if (consultaBvsMenssage.equals("32") || consultaBvsMenssage.equals("48") || consultaBvsMenssage.equals("41") || consultaBvsMenssage.equals("89")
                            || consultaBvsMenssage.equals("100") || consultaBvsMenssage.equals("34") || consultaBvsMenssage.equals("29") || consultaBvsMenssage.equals("5182")
                            || consultaBvsMenssage.equals("5164")) {
                        psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtratorConsulta);
                        psRetornoExtrator.setString(1, consultaBvsMenssage.toUpperCase());
                        rsRetornoExtrator = psRetornoExtrator.executeQuery();
                        if (rsRetornoExtrator.next()) {
                            psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                            psRetornoExtrator.setString(1, "S");
                            psRetornoExtrator.setInt(2, parcelasConsultarBvsModel.getIdExtrator());
                            psRetornoExtrator.executeUpdate();
                            psRetornoExtrator.close();

                            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                            psLogExtrator.setInt(1, parcelasConsultarBvsModel.getIdExtrator());
                            psLogExtrator.setInt(2, rsRetornoExtrator.getInt("ID_RETORNO"));
                            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                            if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("I")) {
                                psLogExtrator.setString(4, "INCLUSAO EFETUADA COM SUCESSO");
                                psLogExtrator.setString(5, "I");
                            } else if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("B")) {
                                psLogExtrator.setString(4, "EXCLUSAO EFETUADA COM SUCESSO");
                                psLogExtrator.setString(5, "B");
                            }
                            psLogExtrator.setString(6, "S");
                            psLogExtrator.execute();
                            psLogExtrator.close();
                        }
                    } else {

                        psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtratorConsulta);
                        psRetornoExtrator.setString(1, consultaBvsMenssage.toUpperCase());
                        rsRetornoExtrator = psRetornoExtrator.executeQuery();
                        if (rsRetornoExtrator.next()) {
                            psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                            if (consultaBvsMenssage.equals("266") || consultaBvsMenssage.equals("281")) { // * CONTRATO NAO INFORMADO
                                psRetornoExtrator.setString(1, "A");
                            } else {
                                psRetornoExtrator.setString(1, "E");
                            }
                            psRetornoExtrator.setInt(2, parcelasConsultarBvsModel.getIdExtrator());
                            psRetornoExtrator.executeUpdate();
                            psRetornoExtrator.close();

                            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                            psLogExtrator.setInt(1, parcelasConsultarBvsModel.getIdExtrator());
                            psLogExtrator.setInt(2, rsRetornoExtrator.getInt("ID_RETORNO"));
                            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                            psLogExtrator.setString(4, rsRetornoExtrator.getString("DESCRICAO"));
                            if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("I")) {
                                psLogExtrator.setString(5, "I");
                            } else if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("B")) {
                                psLogExtrator.setString(5, "B");
                            }
                            psLogExtrator.setString(6, "E");
                            psLogExtrator.execute();
                            psLogExtrator.close();
                        } else {
                            psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
                            psRetornoExtrator.setString(1, "E");
                            psRetornoExtrator.setInt(2, parcelasConsultarBvsModel.getIdExtrator());
                            psRetornoExtrator.executeUpdate();
                            psRetornoExtrator.close();

                            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
                            psLogExtrator.setInt(1, parcelasConsultarBvsModel.getIdExtrator());
                            psLogExtrator.setInt(2, 199);
                            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
                            psLogExtrator.setString(4, consultaBvsMenssage);
                            if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("I")) {
                                psLogExtrator.setString(5, "I");
                            } else if (parcelasConsultarBvsModel.getTipoParcela().equalsIgnoreCase("B")) {
                                psLogExtrator.setString(5, "B");
                            }
                            psLogExtrator.setString(6, "E");
                            psLogExtrator.execute();
                            psLogExtrator.close();
                        }
                        rsRetornoExtrator.close();
                    }
                }

            }
            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;
    }

    public void insertLogErro(ParcelasEnviarModel parcelasEnviarBvsModel, String bvsMenssage) {
        try {
            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);

            psLogExtrator.setInt(1, parcelasEnviarBvsModel.getIdExtrator());
            if (bvsMenssage.equalsIgnoreCase("Registro ainda não enviado para Bvs. Aguarde e refaça a consulta após alguns minutos.")) {
                psLogExtrator.setInt(2, 349);
            } else {
                psLogExtrator.setInt(2, 199);
            }
            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
            psLogExtrator.setString(4, bvsMenssage);
            if (parcelasEnviarBvsModel.getTipoParcela().equalsIgnoreCase("I")) {
                psLogExtrator.setString(5, "I");
            } else if (parcelasEnviarBvsModel.getTipoParcela().equalsIgnoreCase("B")) {
                psLogExtrator.setString(5, "B");
            }
            psLogExtrator.setString(6, "E");
            psLogExtrator.executeUpdate();
            Conexao.getConnection().commit();
            psLogExtrator.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int naoEnviarInclusaoBVS(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel) throws ErroSistemaException {
        sqlUpdateParcelaInclusao = ("UPDATE PARCELA SET DATA_ULT_ATUALIZACAO = ? WHERE ID_PARCELA = ?");
        try (PreparedStatement psParcelaBvs = Conexao.getConnection().prepareStatement(sqlUpdateParcelaInclusao)) {
            psParcelaBvs.setDate(1, Utilitarios.converteData(new Date()));
            psParcelaBvs.setInt(2, parcelasEnviarInclusaoBvsModel.getIdParcela());
            psParcelaBvs.execute();

            try (PreparedStatement psExtrator = Conexao.getConnection().prepareStatement(sqlUpdateExtrator)) {
                psExtrator.setString(1, "S");// AGUARDANDO, REGISTRO ENVIADO A FACMAT AGUARDANDO RETORNO BVS
                psExtrator.setInt(2, parcelasEnviarInclusaoBvsModel.getIdExtrator());
                psExtrator.execute();
            }
            try (PreparedStatement psLog = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno)) {
                psLog.setInt(1, parcelasEnviarInclusaoBvsModel.getIdExtrator());
                psLog.setInt(2, 362);
                psLog.setDate(3, Utilitarios.converteData(new Date()));
                psLog.setString(4, "CLIENTE NÃO ENVIADO PARA A FACMAT, ENVIO SUSPENSO!");
                psLog.setString(5, "I");
                psLog.setString(6, "S");
                psLog.execute();
            }
            Conexao.getConnection().commit();
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return 1;
    }

}
