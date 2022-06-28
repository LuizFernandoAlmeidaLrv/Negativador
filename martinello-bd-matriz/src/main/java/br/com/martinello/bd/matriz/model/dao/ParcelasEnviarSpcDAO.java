/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
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
public class ParcelasEnviarSpcDAO {

    private PreparedStatement psBaixaSpc, psInclusao, psLogRetorno, psretornoClienteSpc, psLogExtrator, psretornoAvalistaSpc, psRetornoExtrator, psLogExtracao;
    private ResultSet rsBaixaSpc, rsInclusaoSpc, rsRetornoExtrator;
    private String sqlInsertLogExtracao, sqlSelectParcelasEnviarSPC, sqlSelectRetornoExtrator, sqlSelectParcelasBaixaSPC;
    public List<ParcelasEnviarModel> linclusaoSpcModel, lbaixaSpcModel, lbuscarParcelasManual;
    public int resultado;

    public ParcelasEnviarSpcDAO() {
        sqlSelectRetornoExtrator = "SELECT ID_RETORNO, CODIGO FROM RETORNOS_INTEGRACAO WHERE CODIGO = ? AND TIPO = 'S'";

        sqlSelectParcelasEnviarSPC = "SELECT FILIAIS.ID_FILIAL,\n"
                + "                       PARCELA.ID_REGISTRO_BVS,\n"
                + "                       CASE\n"
                + "                         WHEN (EXTRATOR.TIPO) = 'I' THEN\n"
                + "                          'INCLUSAO'\n"
                + "                         WHEN (EXTRATOR.TIPO) = 'B' THEN\n"
                + "                          'BAIXA'\n"
                + "                       END TIPO_REGISTRO,\n"
                + "                       FILIAIS.OPERADOR_SPC,\n"
                + "                       FILIAIS.SENHA_SPC,\n"
                + "                       FILIAIS.COD_SPC,\n"
                + "                       CLIENTE.TIPO_PESSOA,\n"
                + "                       CLIENTE.CPF_RAZAO,\n"
                + "                       CLIENTE.CPF_ORIGEM,\n"
                + "                       CLIENTE.NOME,\n"
                + "                       CLIENTE.NUM_RG,\n"
                + "                       CLIENTE.ORGAO_EXPED,\n"
                + "                       CLIENTE.DATE_NASC,\n"
                + "                       CLIENTE.NOME_PAI,\n"
                + "                       CLIENTE.NOME_MAE,\n"
                + "                       CLIENTE.EMAIL,\n"
                + "                       NVL(CLIENTE.TELEFONE, 0)TELEFONE,\n"
                + "                       CLIENTE.EST_CIVIL,\n"
                + "                       CLIENTE.ID_ESTCIVIL,\n"
                + "                       CLIENTE.ENDERECO,\n"
                + "                       CLIENTE.CEP,\n"
                + "                       CLIENTE.ID_LOGRADOURO,\n"
                + "                       CLIENTE.BAIRRO,\n"
                + "                       CLIENTE.NUMERO,\n"
                + "                       CLIENTE.COMPLEMENTO,\n"
                + "                       CLIENTE.CIDADE,\n"
                + "                       CLIENTE.UF_ESTADO,\n"
                + "                       CLIENTE.CODIGO_IBGE,\n"
                + "                       CLIENTE.TIPO_DEVEDOR,\n"
                + "                       AVALISTA.TIPO_PESSOA AS TIPO_PESSOA_AVAL,\n"
                + "                       AVALISTA.CPF_RAZAO AS CPF_RAZAO_AVAL,\n"
                + "                       AVALISTA.CPF_ORIGEM AS CPF_ORIGEM_AVAL,\n"
                + "                       AVALISTA.NOME AS NOME_AVAL,\n"
                + "                       AVALISTA.NUM_RG AS NUM_RG_AVAL,\n"
                + "                       AVALISTA.ORGAO_EXPED AS ORGAO_EXPED_AVAL,\n"
                + "                       AVALISTA.DATE_NASC AS DATE_NASC_AVAL,\n"
                + "                       AVALISTA.NOME_PAI AS NOME_PAI_AVAL,\n"
                + "                       AVALISTA.NOME_MAE AS NOME_MAE_AVAL,\n"
                + "                       AVALISTA.EMAIL AS EMAIL_AVAL,\n"
                + "                       AVALISTA.TELEFONE AS TELEFONE_AVAL,\n"
                + "                       AVALISTA.EST_CIVIL AS EST_CIVIL_AVAL,\n"
                + "                       AVALISTA.ENDERECO AS ENDERECO_AVAL,\n"
                + "                       AVALISTA.CEP AS CEP_AVAL,\n"
                + "                       AVALISTA.BAIRRO AS BAIRRO_AVAL,\n"
                + "                       AVALISTA.NUMERO AS NUMERO_AVAL,\n"
                + "                       AVALISTA.COMPLEMENTO AS COMPLEMENTO_AVAL,\n"
                + "                       AVALISTA.CIDADE AS CIDADE_AVAL,\n"
                + "                       AVALISTA.UF_ESTADO AS UF_ESTADO_AVAL,\n"
                + "                       AVALISTA.CODIGO_IBGE AS CODIGO_IBGE_AVAL,\n"
                + "                       AVALISTA.TIPO_DEVEDOR AS TIPO_DEVEDOR_AVAL,\n"
                + "                       AVALISTA.ID_PESSOA AS ID_AVALISTA,\n"
                + "                       PARCELA.ID_PARCELA,\n"
                + "                       PARCELA.ID_FILIAL,\n"
                + "                       PARCELA.NUMERO_DOC,\n"
                + "                       PARCELA.DATALAN,\n"
                + "                       PARCELA.VENC,\n"
                + "                       PARCELA.VALOR,\n"
                + "                       PARCELA.DATAPAG,\n"
                + "                       PARCELA.DATA_NEGATIVADA,\n"
                + "                       PARCELA.DATA_BAIXA,\n"
                + "                       PARCELA.INCLUIR_AVAL, \n"
                + "                       EXTRATOR.ID_EXTRATOR,\n"
                + "                       EXTRATOR.ID_NATUREZA_INC_SPC,\n"
                + "                       NATUREZA_INCLUSAO.DESCRICAO,\n"
                + "                       EXTRATOR.STATUS,\n"
                + "                       EXTRATOR.STATUS_SPC\n"
                + "                  FROM PARCELA\n"
                + "              INNER JOIN PESSOA CLIENTE\n"
                + "                    ON CLIENTE.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + "                  JOIN FILIAIS\n"
                + "                    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + "                  JOIN EXTRATOR\n"
                + "                    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "                  LEFT OUTER JOIN PESSOA AVALISTA\n"
                + "                    ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA AND STATUS_SPC_AVAL IN ('P', 'E')\n"
                + "                  JOIN NATUREZA_INCLUSAO\n"
                + "                    ON EXTRATOR.ID_NATUREZA_INC_SPC =\n"
                + "                       NATUREZA_INCLUSAO.ID_NATUREZA_INCLUSAO\n"
                + "                   AND NATUREZA_INCLUSAO.PROVEDOR = 'SPC'\n"
                + "                 WHERE EXTRATOR.STATUS NOT IN ('F', 'FE', 'I') AND TIPO = 'I'\n"
                + "                   AND (EXTRATOR.STATUS_SPC IN ('P', 'E') OR (STATUS_SPC_AVAL IN ('P', 'E') AND PARCELA.ID_AVALISTA > 0))";

        sqlSelectParcelasBaixaSPC = "SELECT FILIAIS.ID_FILIAL,\n"
                + "       FILIAIS.OPERADOR_SPC,\n"
                + "       FILIAIS.SENHA_SPC,\n"
                + "       FILIAIS.COD_SPC,\n"
                + "       CLIENTE.TIPO_PESSOA,\n"
                + "       CLIENTE.CPF_RAZAO,\n"
                + "       CLIENTE.CPF_ORIGEM,\n"
                + "       PARCELA.CLIENTE,\n"
                + "       CLIENTE.NOME,\n"
                + "       AVALISTA.TIPO_PESSOA AS TIPO_PESSOA_AVAL,\n"
                + "       AVALISTA.CPF_RAZAO AS CPF_RAZAO_AVAL,\n"
                + "       AVALISTA.CPF_ORIGEM AS CPF_ORIGEM_AVAL,\n"
                + "       AVALISTA.NOME AS NOME_AVAL,\n"
                + "       PARCELA.ID_PARCELA,\n"
                + "       PARCELA.ID_FILIAL,\n"
                + "       CASE\n"
                + "         WHEN IMPCDL.IMPCDLCONTRATODIGITADO <> PARCELA.NUMERO_DOC THEN\n"
                + "          IMPCDL.IMPCDLCONTRATODIGITADO\n"
                + "         ELSE\n"
                + "          PARCELA.NUMERO_DOC\n"
                + "       END NUMERO_DOC,\n"
                + "       CASE\n"
                + "         WHEN IMPORT_PROVEDOR.DATA_VENC <> PARCELA.VENC AND\n"
                + "              IMPCDL.IMPCDLCONTRATODIGITADO IS NULL THEN\n"
                + "          IMPORT_PROVEDOR.DATA_VENC\n"
                + "         WHEN IMPCDL.IMPCDLCONTRATODIGITADO IS NOT NULL AND\n"
                + "              IMPCDL.IMPCDLDATAVENCIMENTO <> PARCELA.VENC  THEN\n"
                + "          IMPCDL.IMPCDLDATAVENCIMENTO\n"
                + "         ELSE\n"
                + "          PARCELA.VENC\n"
                + "       END VENC,\n"
                + "       PARCELA.VALOR,\n"
                + "       PARCELA.DATALAN,\n"
                + "       PARCELA.DATAPAG,\n"
                + "       PARCELA.ID_AVALISTA,\n"
                + "       PARCELA.DATA_BAIXA,\n"
                + "       MOTIVO_BAIXA.ID_MTV_BAIXA,\n"
                + "       MOTIVO_BAIXA.MOTIVO_BAIXA,\n"
                + "       EXTRATOR.ID_EXTRATOR,\n"
                + "       EXTRATOR.DATA_EXTRACAO,\n"
                + "       EXTRATOR.STATUS,\n"
                + "       IMPCDL.IMPCDLCONTRATODIGITADO AS CONTRATO_DIGITADO,\n"
                + "       IMPCDL.IMPCDLCONTRATOREAL AS CONTRATO_REAL\n"
                + "  FROM PARCELA\n"
                + " INNER JOIN PESSOA CLIENTE\n"
                + "    ON CLIENTE.ID_PESSOA = PARCELA.ID_CLIENTE\n"
                + " INNER JOIN FILIAIS\n"
                + "    ON FILIAIS.ID_FILIAL = PARCELA.ID_FILIAL\n"
                + " INNER JOIN EXTRATOR\n"
                + "    ON EXTRATOR.ID_PARCELA = PARCELA.ID_PARCELA\n"
                + "  LEFT JOIN PESSOA AVALISTA\n"
                + "    ON AVALISTA.ID_PESSOA = PARCELA.ID_AVALISTA\n"
                + " INNER JOIN MOTIVO_BAIXA\n"
                + "    ON MOTIVO_BAIXA.ID_MTV_BAIXA = EXTRATOR.ID_MOTIVO_EXCLUSAO_SPC\n"
                + "   AND MOTIVO_BAIXA.LOCAL_BAIXA = 'SPC'\n"
                + "  LEFT JOIN IMPCDL\n"
                + "    ON TRIM(IMPCDL.IMPCDLCONTRATOREAL) = PARCELA.NUMERO_DOC\n"
                + "   AND IMPCDL.IMPCDLFILIAL = FILIAIS.FILIAL_SGL\n"
                + "   AND IMPCDLPROVEDOR = 'C'\n"
                + "   AND TRIM(IMPCDLTIPODEVEDOR) = 'COMPRADOR'\n"
                + "  LEFT JOIN IMPORT_PROVEDOR\n"
                + "    ON TRIM(IMPORT_PROVEDOR.CONTRATO) = PARCELA.NUMERO_DOC\n"
                + "   AND TO_NUMBER(CLIENTE.CPF_RAZAO) = IMPORT_PROVEDOR.CPF\n"
                + "   AND LPAD(TRIM(SUBSTR(ASSOCIADO, 2, 4)), 4, '0') = FILIAIS.ID_FILIAL\n"
                + "   AND IMPORT_PROVEDOR.PROVEDOR = 'C'\n"
                + " WHERE EXTRATOR.STATUS NOT IN ('F', 'FE', 'I')\n"
                + "   AND TIPO = 'B'\n"
                + "   AND (EXTRATOR.STATUS_SPC IN ('P', 'E') OR\n"
                + "       (STATUS_SPC_AVAL IN ('P', 'E') AND PARCELA.ID_AVALISTA > 0))";

    }

    public List<ParcelasEnviarModel> BuscarSpcDAO() throws ErroSistemaException {
        try {

            linclusaoSpcModel = new ArrayList<>();
            psInclusao = Conexao.getConnection().prepareStatement(sqlSelectParcelasEnviarSPC);
            rsInclusaoSpc = psInclusao.executeQuery();
            ParcelasEnviarModel inclusaoSpcModel = new ParcelasEnviarModel();
            while (rsInclusaoSpc.next()) {
                try {
                    inclusaoSpcModel = new ParcelasEnviarModel();
                    inclusaoSpcModel.setStatusSpc(rsInclusaoSpc.getString("STATUS_SPC"));
                    inclusaoSpcModel.setTipoPessoa(rsInclusaoSpc.getString("TIPO_PESSOA"));
                    inclusaoSpcModel.setCpf(rsInclusaoSpc.getString("CPF_RAZAO"));
                    inclusaoSpcModel.setCpfOrigem(rsInclusaoSpc.getString("CPF_ORIGEM"));
                    inclusaoSpcModel.setNomeRazaoSocial(rsInclusaoSpc.getString("NOME"));
                    inclusaoSpcModel.setNumeroRG(rsInclusaoSpc.getString("NUM_RG"));
                    inclusaoSpcModel.setOrgaoEmissorRG(rsInclusaoSpc.getString("ORGAO_EXPED"));
                    inclusaoSpcModel.setDataNascimento(rsInclusaoSpc.getDate("DATE_NASC"));
                    inclusaoSpcModel.setNomeDoPai(rsInclusaoSpc.getString("NOME_PAI"));
                    inclusaoSpcModel.setNomeDaMae(rsInclusaoSpc.getString("NOME_MAE"));
                    inclusaoSpcModel.setEmail(rsInclusaoSpc.getString("EMAIL"));
                    if (rsInclusaoSpc.getString("TELEFONE").length() > 8) {
                        inclusaoSpcModel.setDddNumeroTel(rsInclusaoSpc.getString("TELEFONE").replaceAll("[^0-9]", "").trim().substring(0, 2));
                    } else {
                        inclusaoSpcModel.setDddNumeroTel("");
                    }
                    inclusaoSpcModel.setNumeroTel(rsInclusaoSpc.getString("TELEFONE").replaceAll("[^0-9]", "").trim());
                    inclusaoSpcModel.setEstadoCivil(rsInclusaoSpc.getString("EST_CIVIL"));
                    inclusaoSpcModel.setEndLogradouro(rsInclusaoSpc.getString("ENDERECO"));
                    inclusaoSpcModel.setEndBairro(rsInclusaoSpc.getString("BAIRRO"));
                    inclusaoSpcModel.setEndNumero(rsInclusaoSpc.getString("NUMERO"));
                    inclusaoSpcModel.setEndComplemento(rsInclusaoSpc.getString("COMPLEMENTO"));
                    inclusaoSpcModel.setCep(rsInclusaoSpc.getString("CEP"));
                    inclusaoSpcModel.setNomeCidade(rsInclusaoSpc.getString("CIDADE"));
                    inclusaoSpcModel.setUfEstado(rsInclusaoSpc.getString("UF_ESTADO"));
                    inclusaoSpcModel.setPontoFilial(rsInclusaoSpc.getString("ID_FILIAL"));
                    inclusaoSpcModel.setOperadorSpc(rsInclusaoSpc.getString("OPERADOR_SPC"));
                    inclusaoSpcModel.setSenhaSpc(rsInclusaoSpc.getString("SENHA_SPC"));
                    inclusaoSpcModel.setCodigoAssociado(rsInclusaoSpc.getString("COD_SPC"));
                    inclusaoSpcModel.setIdNaturezaInclusaoSpc(rsInclusaoSpc.getString("ID_NATUREZA_INC_SPC"));
                    inclusaoSpcModel.setNaturezaInclusaoSpc(rsInclusaoSpc.getString("DESCRICAO"));
                    inclusaoSpcModel.setDataLancamento(rsInclusaoSpc.getDate("DATALAN"));
                    inclusaoSpcModel.setDataVencimento(rsInclusaoSpc.getDate("VENC"));
                    inclusaoSpcModel.setTipoDevedor(rsInclusaoSpc.getString("TIPO_DEVEDOR"));
                    inclusaoSpcModel.setNumeroDoContrato(rsInclusaoSpc.getString("NUMERO_DOC"));
                    inclusaoSpcModel.setValorParcela(rsInclusaoSpc.getBigDecimal("VALOR").setScale(2));
                    inclusaoSpcModel.setAvalista("0");
                    inclusaoSpcModel.setIdExtrator(rsInclusaoSpc.getInt("ID_EXTRATOR"));
                    /**
                     * Verifica se possui avalista e se o avalista pode ser
                     * Incluido.
                     */
                    if (rsInclusaoSpc.getString("ID_AVALISTA") != null && rsInclusaoSpc.getString("INCLUIR_AVAL").equals("S")) {
                        inclusaoSpcModel.setAvalista(rsInclusaoSpc.getString("ID_AVALISTA"));
                        inclusaoSpcModel.setTipoPessoaAval(rsInclusaoSpc.getString("TIPO_PESSOA_AVAL"));
                        inclusaoSpcModel.setCpfAval(rsInclusaoSpc.getString("CPF_RAZAO_AVAL"));
                        inclusaoSpcModel.setCpfAvalOrigem(rsInclusaoSpc.getString("CPF_ORIGEM_AVAL"));
                        inclusaoSpcModel.setNomeRazaoSocialAval(rsInclusaoSpc.getString("NOME_AVAL"));
                        inclusaoSpcModel.setNumeroRGAval(rsInclusaoSpc.getString("NUM_RG_AVAL"));
                        inclusaoSpcModel.setOrgaoEmissorRGAval(rsInclusaoSpc.getString("ORGAO_EXPED_AVAL"));
                        inclusaoSpcModel.setDataNascimentoAval(rsInclusaoSpc.getDate("DATE_NASC_AVAL"));
                        inclusaoSpcModel.setNomeDoPaiAval(rsInclusaoSpc.getString("NOME_PAI_AVAL"));
                        inclusaoSpcModel.setNomeDaMaeAval(rsInclusaoSpc.getString("NOME_MAE_AVAL"));
                        inclusaoSpcModel.setEmailAval(rsInclusaoSpc.getString("EMAIL_AVAL"));
                        if (rsInclusaoSpc.getString("TELEFONE_AVAL").length() > 8) {
                            inclusaoSpcModel.setDddNumeroTelAval(rsInclusaoSpc.getString("TELEFONE_AVAL").substring(0, 2));
                        } else {
                            inclusaoSpcModel.setDddNumeroTel("");
                        }
                        inclusaoSpcModel.setNumeroTelAval(rsInclusaoSpc.getString("TELEFONE_AVAL"));
                        inclusaoSpcModel.setEstadoCivilAval(rsInclusaoSpc.getString("EST_CIVIL_AVAL"));
                        inclusaoSpcModel.setCodigoAssociado(rsInclusaoSpc.getString("COD_SPC"));
                        inclusaoSpcModel.setDataLancamento(rsInclusaoSpc.getDate("DATALAN"));
                        inclusaoSpcModel.setTipoDevedorAval(rsInclusaoSpc.getString("TIPO_DEVEDOR_AVAL"));
                        inclusaoSpcModel.setNumeroDoContrato(rsInclusaoSpc.getString("NUMERO_DOC"));
                        inclusaoSpcModel.setEndLogradouroAval(rsInclusaoSpc.getString("ENDERECO_AVAL"));
                        inclusaoSpcModel.setEndBairroAval(rsInclusaoSpc.getString("BAIRRO_AVAL"));
                        inclusaoSpcModel.setEndNumeroAval(rsInclusaoSpc.getString("NUMERO_AVAL"));
                        inclusaoSpcModel.setEndComplementoAval(rsInclusaoSpc.getString("COMPLEMENTO_AVAL"));
                        inclusaoSpcModel.setCepAval(rsInclusaoSpc.getString("CEP_AVAL"));
                        inclusaoSpcModel.setNomeCidadeAval(rsInclusaoSpc.getString("CIDADE_AVAL"));
                        inclusaoSpcModel.setUfEstadoAval(rsInclusaoSpc.getString("UF_ESTADO_AVAL"));

                    }

                    linclusaoSpcModel.add(inclusaoSpcModel);
                } catch (SQLException e) {
                    throw new ErroSistemaException(e.getMessage(), e.getCause());
                } catch (NullPointerException nexc) {
                    throw new ErroSistemaException(nexc.getMessage(), nexc.getCause());
                }
            }
            psInclusao.close();
            rsInclusaoSpc.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return linclusaoSpcModel;

    }

    public List<ParcelasEnviarModel> BuscarSpcBaixaDAO() throws ErroSistemaException {
        try {
            lbaixaSpcModel = new ArrayList<>();
            psBaixaSpc = Conexao.getConnection().prepareStatement(sqlSelectParcelasBaixaSPC);
            rsBaixaSpc = psBaixaSpc.executeQuery();
            ParcelasEnviarModel baixaSpcModel = new ParcelasEnviarModel();
            while (rsBaixaSpc.next()) {
                baixaSpcModel = new ParcelasEnviarModel();
                baixaSpcModel.setPontoFilial(rsBaixaSpc.getString("ID_FILIAL"));
                baixaSpcModel.setTipoPessoa(rsBaixaSpc.getString("TIPO_PESSOA"));
                baixaSpcModel.setCpf(rsBaixaSpc.getString("CPF_RAZAO"));
                baixaSpcModel.setCpfOrigem(rsBaixaSpc.getString("CPF_ORIGEM"));
                baixaSpcModel.setNomeRazaoSocial(rsBaixaSpc.getString("NOME"));
                baixaSpcModel.setTipoPessoaAval(rsBaixaSpc.getString("TIPO_PESSOA_AVAL"));
                baixaSpcModel.setCpfAval(rsBaixaSpc.getString("CPF_RAZAO_AVAL"));
                baixaSpcModel.setCpfAvalOrigem(rsBaixaSpc.getString("CPF_ORIGEM_AVAL"));
                baixaSpcModel.setNomeRazaoSocialAval(rsBaixaSpc.getString("NOME_AVAL"));
                baixaSpcModel.setOperadorSpc(rsBaixaSpc.getString("OPERADOR_SPC"));
                baixaSpcModel.setSenhaSpc(rsBaixaSpc.getString("SENHA_SPC"));
                baixaSpcModel.setCodigoAssociado(rsBaixaSpc.getString("COD_SPC"));
                baixaSpcModel.setDataLancamento(rsBaixaSpc.getDate("DATALAN"));
                baixaSpcModel.setDataVencimento(rsBaixaSpc.getDate("VENC"));
                baixaSpcModel.setNumeroDoContrato(rsBaixaSpc.getString("NUMERO_DOC"));
                baixaSpcModel.setIdMotivoBaixaSpc(rsBaixaSpc.getString("ID_MTV_BAIXA"));
                baixaSpcModel.setMotivoBaixaSpc(rsBaixaSpc.getString("MOTIVO_BAIXA"));
                if (rsBaixaSpc.getString("ID_AVALISTA") != null) {
                    baixaSpcModel.setAvalista(rsBaixaSpc.getString("ID_AVALISTA"));
                } else {
                    baixaSpcModel.setAvalista("0");
                }
                baixaSpcModel.setIdExtrator(rsBaixaSpc.getInt("ID_EXTRATOR"));

                lbaixaSpcModel.add(baixaSpcModel);

            }
            psBaixaSpc.close();
            rsBaixaSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lbaixaSpcModel;

    }

    public void retornoInclusaoSpcClienteDAO(ParcelasEnviarModel parcelasEnviarSpcModel, String incluirSpcFaultcode, String incluirSpcResponse) throws ErroSistemaException {
        try {
            if (incluirSpcResponse.contains("Dados inseridos com sucesso!")) {
                /* Registro Inserido com Sucesso */

                gravarRetornoClienteSucessoExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 142, "Cliente Incluído no SPC com Sucesso. ", "S", "I");
            } else if (incluirSpcFaultcode != null && incluirSpcFaultcode.equalsIgnoreCase("IE_SPC001.E09")) {
                /* Registro já existe. */

                gravarRetornoClienteSucessoExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 142, "Cliente Incluído no SPC com Sucesso. ", "S", "I");
            } else if (incluirSpcFaultcode != null) {
                /* Se não existe, será verificado o código retornado, é realizado um select e neste select busco o codigo relacionado na minha base. */

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                psRetornoExtrator.setString(1, incluirSpcFaultcode);
                rsRetornoExtrator = psRetornoExtrator.executeQuery();
                if (rsRetornoExtrator.next()) {
                    gravarRetornoClienteErroExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), rsRetornoExtrator.getInt("ID_RETORNO"), incluirSpcResponse, "E", "I");
                } else {
                    /* Não encontrou um códiugo relacionado */
                    gravarRetornoClienteErroExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Erro não catalogado: " + incluirSpcResponse, "E", "I");
                }
                psRetornoExtrator.close();
                rsRetornoExtrator.close();

            } else {
                gravarRetornoClienteErroExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Erro não catalogado: " + incluirSpcResponse, "E", "I");
            }
            Conexao.getConnection().commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public boolean retornoInclusaoSpcAvalistaDAO(ParcelasEnviarModel parcelasEnviarSpcModel, String incluirSpcFaultcode, String incluirSpcResponse) throws ErroSistemaException {
        try {
            /* Os dados foram inseridos com sucesso. */
            if (incluirSpcResponse.contains("Dados inseridos com sucesso!")) {
                gravarRetornoAvalistaSucessoExtratorSpc("S", parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 352, "AVALISTA ENVIADO PARA O SPC", "S", "I");
            } else if (incluirSpcFaultcode != null && incluirSpcFaultcode.equalsIgnoreCase("IE_SPC001.E09")) {
                /* Registro já incluído anteriormente. Gravar como sucesso. */

                gravarRetornoAvalistaSucessoExtratorSpc("S", parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 352, "AVALISTA ENVIADO PARA O SPC", "S", "I");
            } else if (incluirSpcFaultcode != null) {
                /* Verificar se o código enviado, está na tabela de retorno, para realizar o de-para. */

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                psRetornoExtrator.setString(1, incluirSpcFaultcode);
                rsRetornoExtrator = psRetornoExtrator.executeQuery();

                /* Se encontrar o código de retorno correspondente. Grava o cód. interno da tabela */
                if (rsRetornoExtrator.next()) {
                    gravarRetornoAvalistaErroExtratorSpc("E", parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), rsRetornoExtrator.getInt("ID_RETORNO"), incluirSpcResponse, "E", "I");
                } else {
                    /* Gravar o erro não localizado com 142 - Erro não catalogado. */
                    gravarRetornoAvalistaErroExtratorSpc("E", parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, incluirSpcResponse, "E", "I");
                }
                psRetornoExtrator.close();
                rsRetornoExtrator.close();
            } else {
                /* Gravar o erro não localizado com 142 - Erro não catalogado. */
                gravarRetornoAvalistaErroExtratorSpc("E", parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, incluirSpcResponse, "E", "I");
            }
            Conexao.getConnection().commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;
    }

    public void gravarRetornoAvalistaErroExtratorSpc(String tipo, int idExtrator) throws ErroSistemaException {
        /**
         * Cód. 198 é erro não catalogado.
         */
        String sqlUpdateExtratorAvalistaErro = ("UPDATE EXTRATOR SET STATUS_SPC_AVAL = ? WHERE ID_EXTRATOR = ? ");

        try {
            psretornoAvalistaSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorAvalistaErro);
            psretornoAvalistaSpc.setString(1, tipo); // E = Erro.
            psretornoAvalistaSpc.setInt(2, idExtrator);
            psretornoAvalistaSpc.execute();
            psretornoAvalistaSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void gravarRetornoClienteSucessoExtratorSpc(int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtrator = ("UPDATE EXTRATOR SET DATA_SPC = ?, STATUS_SPC = ?, STATUS_SPC_AVAL = ? WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtrator);
            psretornoClienteSpc.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
            psretornoClienteSpc.setString(2, "S");
            psretornoClienteSpc.setString(3, "S");
            psretornoClienteSpc.setInt(4, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void gravarRetornoClienteErroExtratorSpc(int idExtrator) throws ErroSistemaException {
        /**
         * Cód. 198 é erro não catalogado.
         */
        String sqlUpdateExtratorInclusaoErro = ("UPDATE EXTRATOR SET  STATUS_SPC = ? WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorInclusaoErro);
            psretornoClienteSpc.setString(1, "E");
            psretornoClienteSpc.setInt(2, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void gravarRetornoAvalistaSucessoExtratorSpc(String tipo, int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtratorAvalista = ("UPDATE EXTRATOR SET DATA_SPC_AVALISTA = ?, STATUS_SPC_AVAL = ? WHERE ID_EXTRATOR = ? ");

        try {
            psretornoAvalistaSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorAvalista);
            psretornoAvalistaSpc.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
            psretornoAvalistaSpc.setString(2, tipo);
            psretornoAvalistaSpc.setInt(3, idExtrator);
            psretornoAvalistaSpc.execute();
            psretornoAvalistaSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void gravarRetornoBaixaSucessoExtratorSpc(int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtratorBaixa = ("UPDATE EXTRATOR\n"
                + "   SET DATA_SPC          = ?,\n"
                + "       STATUS_SPC        = ?\n"
                + " WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorBaixa);
            psretornoClienteSpc.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
            psretornoClienteSpc.setString(2, "S");
            psretornoClienteSpc.setInt(3, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void gravarRetornoBaixaSucessoExtratorSpcAval(int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtratorBaixa = ("UPDATE EXTRATOR\n"
                + "        SET DATA_SPC_AVALISTA = ?,\n"
                + "       STATUS_SPC_AVAL   = ?\n"
                + " WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorBaixa);
            psretornoClienteSpc.setString(1, Utilitarios.converteDataString(new Date(), "dd/MM/yyyy"));
            psretornoClienteSpc.setString(2, "S");
            psretornoClienteSpc.setInt(3, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void gravarRetornoBaixaErroExtratorSpc(int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtratorBaixaErro = ("UPDATE EXTRATOR SET STATUS_SPC = ? WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorBaixaErro);
            psretornoClienteSpc.setString(1, "E");
            psretornoClienteSpc.setInt(2, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void gravarRetornoBaixaErroExtratorSpcAval(int idExtrator) throws ErroSistemaException {
        String sqlUpdateExtratorBaixaErro = ("UPDATE EXTRATOR SET STATUS_SPC_AVAL = ? WHERE ID_EXTRATOR = ?");

        try {
            psretornoClienteSpc = Conexao.getConnection().prepareStatement(sqlUpdateExtratorBaixaErro);
            psretornoClienteSpc.setString(1, "E");
            psretornoClienteSpc.setInt(2, idExtrator);
            psretornoClienteSpc.execute();
            psretornoClienteSpc.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Status Msg: E - Erro, S - Sucesso. Origem: I - Inclusão, B - Baixa.
     */
    public void gravarLogExtrator(int idExtrator, int codigo, String msgLog, String statusMsg, String origem) throws ErroSistemaException {
        String sqlInsertLogRetorno = "INSERT INTO LOG_EXTRATOR (ID_LOG, ID_EXTRATOR, CODIGO_RETORNO, DATA_EXECUCAO, OBSERVACAO, STATUS, ORIGEM)"
                + "VALUES (ID_LOG_EXTRATOR.nextval, ?, ?, ?, ?, ?, ?)";
        try {
            psLogExtrator = Conexao.getConnection().prepareStatement(sqlInsertLogRetorno);
            psLogExtrator.setInt(1, idExtrator);
            psLogExtrator.setInt(2, codigo);
            psLogExtrator.setDate(3, Utilitarios.converteData(new Date()));
            psLogExtrator.setString(4, msgLog);
            psLogExtrator.setString(5, statusMsg);
            psLogExtrator.setString(6, origem);
            psLogExtrator.execute();
            psLogExtrator.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public boolean retornoBaixaSpcClienteDAO(ParcelasEnviarModel parcelasEnviarSpcModel, String baixarSpcResponse, String baixarSpcFaultCode) throws ErroSistemaException {

        try {
            if (baixarSpcResponse.contains("Registro removido com sucesso!")) {
                /* Registo foi excluido do SPC com sucesso */

                gravarRetornoBaixaSucessoExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 351, "Baixa realizada com sucesso no SPC.", "S", "B");
            } else if (baixarSpcFaultCode != null && baixarSpcFaultCode.equalsIgnoreCase("IE_SPC005.E10")) {
                /* Registro não localizado no SPC, logo entendemos que já foi baixado */

                gravarRetornoBaixaSucessoExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 194, "Baixa realizada com sucesso, cliente não encontrado no SPC.", "S", "B");
            } else if (baixarSpcFaultCode != null) {
                /* Verifica o código do Erro retornoado, e verifica na base para localizar o codigo correspondente. */

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                psRetornoExtrator.setString(1, baixarSpcFaultCode);
                rsRetornoExtrator = psRetornoExtrator.executeQuery();

                if (rsRetornoExtrator.next()) {
                    gravarRetornoBaixaErroExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), rsRetornoExtrator.getInt("ID_RETORNO"), baixarSpcResponse, "E", "B");
                } else {
                    gravarRetornoBaixaErroExtratorSpc(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Erro não catalogado." + baixarSpcResponse, "E", "B");
                }

                rsRetornoExtrator.close();
                psRetornoExtrator.close();
            } else {
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Erro não catalogado." + baixarSpcResponse, "E", "B");
            }
            Conexao.getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;
    }

    public boolean retornoBaixaSpcAvalistaDAO(ParcelasEnviarModel parcelasEnviarSpcModel, String baixarSpcResponse, String baixarSpcFaultCode) throws ErroSistemaException {

        try {
            if (baixarSpcResponse.contains("Registro removido com sucesso!")) {
                /* Registo foi excluido do SPC com sucesso */

                gravarRetornoBaixaSucessoExtratorSpcAval(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 353, "Baixa Avalista realizada com sucesso no SPC.", "S", "B");
            } else if (baixarSpcFaultCode != null && baixarSpcFaultCode.equalsIgnoreCase("IE_SPC005.E10")) {
                /* Registro não localizado no SPC, logo entendemos que já foi baixado */

                gravarRetornoBaixaSucessoExtratorSpcAval(parcelasEnviarSpcModel.getIdExtrator());
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 194, "Baixa Avalista realizada com sucesso. Avalista não encontrado no SPC.", "S", "B");
            } else if (baixarSpcFaultCode != null) {
                /* Verifica o código do Erro retornoado, e verifica na base para localizar o codigo correspondente. */

                psRetornoExtrator = Conexao.getConnection().prepareStatement(sqlSelectRetornoExtrator);
                psRetornoExtrator.setString(1, baixarSpcFaultCode);
                rsRetornoExtrator = psRetornoExtrator.executeQuery();

                if (rsRetornoExtrator.next()) {
                    gravarRetornoBaixaErroExtratorSpcAval(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), rsRetornoExtrator.getInt("ID_RETORNO"), baixarSpcResponse, "E", "B");
                } else {
                    gravarRetornoBaixaErroExtratorSpcAval(parcelasEnviarSpcModel.getIdExtrator());
                    gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Avalista erro não catalogado." + baixarSpcResponse, "E", "B");
                }

                rsRetornoExtrator.close();
                psRetornoExtrator.close();
            } else {
                gravarLogExtrator(parcelasEnviarSpcModel.getIdExtrator(), 198, "Avalista erro não catalogado." + baixarSpcResponse, "E", "B");
            }
            Conexao.getConnection().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return false;
    }
}
