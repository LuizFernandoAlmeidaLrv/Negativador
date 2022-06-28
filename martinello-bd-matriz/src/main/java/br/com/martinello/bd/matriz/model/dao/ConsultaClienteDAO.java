/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.model.dao.Conexao;
import br.com.martinello.consulta.Domain.ConsultaClienteModel;
import br.com.martinello.consulta.Domain.ConsultaSpcBvsModel;
import br.com.martinello.consulta.Domain.DadosComerciaisModel;
import br.com.martinello.consulta.Domain.EnderecoModel;
import br.com.martinello.consulta.Domain.HistoricoCliente;
import br.com.martinello.consulta.Domain.ObservacaoModel;
import br.com.martinello.consulta.Domain.PessoaModel;
import br.com.martinello.consulta.Domain.ReferenciasModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import br.com.martinello.util.filtro.Filtro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ConsultaClienteDAO {

    private String sqlSelectCliente, sqlSelect;
    private PreparedStatement psCliente;
    private ResultSet rsCliente, rsSelect;
    private List<ConsultaClienteModel> lConsultaCliente;
    private List<EnderecoModel> lEndereco;
    private List<ReferenciasModel> lReferencias;
    private List<ConsultaSpcBvsModel> lConsultaSpcBvs;
    private List<HistoricoCliente> lHistoricoCliente;
    private List<ObservacaoModel> lObservacao;
    private List<DadosComerciaisModel> lDadosComerciais;
    public List<PessoaModel> lPessoa;
    private ConsultaClienteModel consultaCliente;
    private EnderecoModel endereco;
    private ReferenciasModel referencias;
    private ConsultaSpcBvsModel consultaSpcBvs;
    private HistoricoCliente historicoCliente;
    private ObservacaoModel observacao;
    private DadosComerciaisModel dadosComerciais;
    private PessoaModel cliente, clienteConjuge;
    private String idCliente;

    public ConsultaClienteDAO() {

    }

    public ConsultaClienteModel ConsultaCliente(String scodFil, int codCli) throws ErroSistemaException {
        try {
            consultaCliente = new ConsultaClienteModel();
            cliente = new PessoaModel();
            clienteConjuge = new PessoaModel();
            lEndereco = new LinkedList<>();
            lReferencias = new LinkedList<>();
            lConsultaSpcBvs = new LinkedList<>();
            lDadosComerciais = new LinkedList<>();
            lHistoricoCliente = new LinkedList<>();
            lObservacao = new LinkedList<>();

            sqlSelectCliente = "SELECT *FROM VCLIENTES WHERE CODCLI = " + codCli + " AND USU_CODFLO = " + scodFil;
            psCliente = Conexao.getConnection().prepareStatement(sqlSelectCliente);
            rsCliente = psCliente.executeQuery();
            if (rsCliente.next()) {
                cliente.setCpf(rsCliente.getString("NCGCCPF"));
                cliente.setNomeRazaoSocial(rsCliente.getString("RAZAO_SOCIAL"));
                cliente.setSexo(rsCliente.getString("SEXO"));
                cliente.setIdPessoa(rsCliente.getInt("CODCLI"));
                cliente.setDataNascimento(rsCliente.getDate("DATA_NASCIMENTO"));
                cliente.setEmail(rsCliente.getString("EMAIL"));
                cliente.setNomeDoPai(rsCliente.getString("NOME_PAI"));
                cliente.setNomeDaMae(rsCliente.getString("NOME_MAE"));
                cliente.setNaturalidade(rsCliente.getString("NATURALIDADE"));
                cliente.setSituacao(rsCliente.getString("SITUACAO"));
                cliente.setTipoPessoa(rsCliente.getString("TIPO"));
                cliente.setNumeroTel(rsCliente.getString("TELEFONE"));
                cliente.setNumeroCelular(rsCliente.getString("CELULAR"));
                cliente.setNegativacao(rsCliente.getString("NEGATIVADO"));
                clienteConjuge.setCpf(rsCliente.getString("CPF_CONJUGE"));
                clienteConjuge.setNomeRazaoSocial(rsCliente.getString("CONJUGE"));
                clienteConjuge.setDataNascimento(rsCliente.getDate("DATA_NASCIMENTO_CONJUGE"));
                clienteConjuge.setNomeDoPai(rsCliente.getString("NOME_PAI_CONJUGE"));
                clienteConjuge.setNomeDaMae(rsCliente.getString("NOME_MAE_CONJUGE"));
                consultaCliente.setCliente(cliente);
                consultaCliente.setClienteConjuge(clienteConjuge);
                consultaCliente.setDataResidencia(rsCliente.getDate("DATA_RESIDENCIA"));
                consultaCliente.setRenda(rsCliente.getDouble("RENDA"));
                consultaCliente.setRendaComplementar(rsCliente.getDouble("RENDA_COMPLEMENTAR"));
                consultaCliente.setDescRendaComplementar(rsCliente.getString("DESCRICAO_RENDA_COMPLEMENTAR"));
                consultaCliente.setNumeroDependentes(codCli);
                consultaCliente.setDespesasMoradia(rsCliente.getDouble("DESPESAS_MORADIA"));
                consultaCliente.setDespesasMensal(rsCliente.getDouble("DESPESAS_MENSAL"));
                consultaCliente.setValorPatrimnonio(rsCliente.getDouble("VALOR_PATRIMONIO"));
                idCliente = rsCliente.getString("IDCLIENTES");

            }
            lEndereco = buscarEndereco(scodFil, codCli);
            consultaCliente.setlEnderecos(lEndereco);
            lReferencias = buscarReferencias(scodFil, idCliente);
            consultaCliente.setlReferencias(lReferencias);
            lObservacao = buscarObservacao(scodFil, idCliente);
            consultaCliente.setlObservacoes(lObservacao);
            lConsultaSpcBvs = buscarConsultaSpc(scodFil, idCliente);
            consultaCliente.setlConsulta(lConsultaSpcBvs);
            lDadosComerciais = buscarDadosComerciais(scodFil, idCliente);
            consultaCliente.setlDadosComericiais(lDadosComerciais);

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return consultaCliente;
    }

    public List<PessoaModel> lListarCliente(List<Filtro> filtro) throws ErroSistemaException {
        try {
            sqlSelectCliente = "SELECT *FROM VCLIENTES" + getWhere(filtro);
            System.out.println("novoSelect:" + sqlSelectCliente);
            psCliente = Conexao.getConnection().prepareStatement(sqlSelectCliente);
            lPessoa = new LinkedList<>();
            rsCliente = psCliente.executeQuery();
            while(rsCliente.next()) {
                cliente = new PessoaModel();
                cliente.setCpf(rsCliente.getString("NCGCCPF"));
                cliente.setNomeRazaoSocial(rsCliente.getString("RAZAO_SOCIAL"));
                cliente.setSexo(rsCliente.getString("SEXO"));
                cliente.setIdPessoa(rsCliente.getInt("CODCLI"));
                cliente.setDataNascimento(rsCliente.getDate("DATA_NASCIMENTO"));
                cliente.setEmail(rsCliente.getString("EMAIL"));
                cliente.setNomeDoPai(rsCliente.getString("NOME_PAI"));
                cliente.setNomeDaMae(rsCliente.getString("NOME_MAE"));
                cliente.setNaturalidade(rsCliente.getString("NATURALIDADE"));
                cliente.setSituacao(rsCliente.getString("SITUACAO"));
                cliente.setTipoPessoa(rsCliente.getString("TIPO"));
                cliente.setNumeroTel(rsCliente.getString("TELEFONE"));
                cliente.setNumeroCelular(rsCliente.getString("CELULAR"));
                cliente.setNegativacao(rsCliente.getString("NEGATIVADO"));
                cliente.setPontoFilial(rsCliente.getString("LOJA"));
                cliente.setDataEmissaoRG(rsCliente.getDate("DATA_EXPEDICAO"));
                cliente.setOrgaoEmissorRG(rsCliente.getString("ORGAO_EXPEDITOR"));
                cliente.setSituacao(rsCliente.getString("SITUACAO"));
                
                lPessoa.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lPessoa;
    }

    private List<EnderecoModel> buscarEndereco(String scodFil, int codCli) throws ErroSistemaException {
        sqlSelect = "SELECT *FROM VCLIEND WHERE CODCLI = " + codCli + " AND USU_CODFLO = '" + scodFil + "' AND SISTEMA = 'SGLM'";
        lEndereco = new LinkedList<>();
        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                endereco = new EnderecoModel();
                endereco.setTipoDocumento(rsSelect.getString("TIPO_DOCUMENTO"));
                endereco.setDocumento(rsSelect.getString("DOCUMENTO"));
                endereco.setDescricao(rsSelect.getString("DESCRICAO"));
                endereco.setEndLogradouro(rsSelect.getString("ENDERECO"));
                endereco.setEndNumero(rsSelect.getString("NUMERO"));
                endereco.setEndBairro(rsSelect.getString("BAIRRO"));
                endereco.setCep(rsSelect.getString("CEP"));
                endereco.setCidade(rsSelect.getString("CIDADE_NOME"));
                endereco.setUfEstado(rsSelect.getString("ESTADO"));
                endereco.setCodigoIbge(rsSelect.getString("CIDADE_IBGE"));
                endereco.setEndComplemento(rsSelect.getString("COMPLEMENTO"));
                lEndereco.add(endereco);

            }

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lEndereco;
    }

    private List<ReferenciasModel> buscarReferencias(String scodFil, String idCliente) throws ErroSistemaException {
        sqlSelect = "SELECT *FROM VCLIREF WHERE IDCLIENTE = '" + idCliente + "' AND LOJA = " + scodFil;
        lReferencias = new LinkedList<>();
        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                referencias = new ReferenciasModel();
                referencias.setTipo(rsSelect.getString("TIPO_REFERENCIA"));
                referencias.setDataCadastro(rsSelect.getDate("DATA_CADASTRO"));
                referencias.setDataConsulta(rsSelect.getDate("DATA_CONSULTA"));
                referencias.setDataUltCompra(rsSelect.getDate("DATA_ULTIMA_COMPRA"));
                referencias.setReferencia(rsSelect.getString("REFERENCIA"));
                referencias.setResultado(rsSelect.getString("RESULTADO"));
                referencias.setDiasMedios(rsSelect.getString("DIAS_MEDIO_ATRASO"));
                referencias.setTipoPagamento(rsSelect.getString("TIPO_PAGAMENTO"));
                referencias.setValorAberto(rsSelect.getDouble("VALOR_ABERTO"));
                referencias.setValorEmAberto(rsSelect.getString("EXISTE_VALOR_VENCIDO_ABERTO"));
                referencias.setValorQuitado(rsSelect.getDouble("VALOR_QUITADO"));
                referencias.setValorUltCompra(rsSelect.getDouble("VALOR_ULTIMA_COMPRA"));
                lReferencias.add(referencias);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lReferencias;
    }

    private List<ObservacaoModel> buscarObservacao(String scodFil, String idCliente) throws ErroSistemaException {
        sqlSelect = "SELECT *FROM VCLIOBS WHERE IDCLIENTES = '" + idCliente + "' AND LOJA = " + scodFil + "  ORDER BY ID_CLIENTE_OBSERVACAO";
        lObservacao = new LinkedList<>();
        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                observacao = new ObservacaoModel();
                observacao.setClienteEndereco(rsSelect.getString("IDCLIENTES"));
                observacao.setTipo(rsSelect.getString("TIPO"));
                observacao.setDescricao(rsSelect.getString("DESCRICAO"));
                observacao.setDataGeracao(rsSelect.getDate("DATA_GERACAO"));
                observacao.setUsuarioGer(rsSelect.getString("USUARIO_GERACAO_NOME"));
                lObservacao.add(observacao);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lObservacao;
    }

    private List<ConsultaSpcBvsModel> buscarConsultaSpc(String scodFil, String idCliente) throws ErroSistemaException {
        sqlSelect = "SELECT *FROM VCLISPC WHERE IDCLIENTE = '" + idCliente + "' AND LOJA = " + scodFil + "ORDER BY DATA DESC";
        lConsultaSpcBvs = new LinkedList<>();
        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                consultaSpcBvs = new ConsultaSpcBvsModel();
                consultaSpcBvs.setTipo(rsSelect.getString("TIPO"));
                consultaSpcBvs.setResultado(rsSelect.getString("RESULTADO"));
                consultaSpcBvs.setData(rsSelect.getDate("DATA"));
                consultaSpcBvs.setUsuario(rsSelect.getString("USUARIO_GERACAO_NOME"));
                lConsultaSpcBvs.add(consultaSpcBvs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lConsultaSpcBvs;
    }

    private List<DadosComerciaisModel> buscarDadosComerciais(String scodFil, String idCliente) throws ErroSistemaException {
        sqlSelect = "SELECT *FROM VCLICOM WHERE IDCLIENTES  = '" + idCliente + "' AND LOJA = " + scodFil;
        lDadosComerciais = new LinkedList<>();
        try (PreparedStatement psSelect = Conexao.getConnection().prepareStatement(sqlSelect)) {
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                dadosComerciais = new DadosComerciaisModel();
                dadosComerciais.setCargo(rsSelect.getString("CARGO"));
                dadosComerciais.setContato(rsSelect.getString("CONTATO"));
                dadosComerciais.setDataAdmicao(rsSelect.getDate("DATA_ADMISSAO"));
                dadosComerciais.setDataAlteracao(rsSelect.getDate("DATA_ALTERACAO"));
                dadosComerciais.setDataGeracao(rsSelect.getDate("DATA_GERACAO"));
                dadosComerciais.setEmpresa(rsSelect.getString("EMPRESA"));
                dadosComerciais.setOcupacao(rsSelect.getString("OCUPACAO"));
                dadosComerciais.setTelefone(rsSelect.getString("TELEFONE"));
                dadosComerciais.setUsuarioAlteracao(rsSelect.getString("USUARIO_ALTERACAO"));
                dadosComerciais.setUsuarioGeracao(rsSelect.getString("USUARIO_GERACAO"));
                lDadosComerciais.add(dadosComerciais);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lDadosComerciais;
    }

    private String getWhere(List<Filtro> lfiltro) {
        String where = "";
        for (Filtro filtro : lfiltro) {
            if (filtro.getCampo().equalsIgnoreCase("ORIGEM")) {
                where += " AND SISTEMA = '" + filtro.getValor()+ "' ";
            }
            if (filtro.getCampo().equalsIgnoreCase("LOJA")) {
                where += " AND USU_CODFLO = '" + filtro.getValor() + "' ";
            }
            if (filtro.getCampo().equalsIgnoreCase("NOME")) {
                where += " AND UPPER(RAZAO_SOCIAL) LIKE '" + filtro.getValor() +"' ";
            }
            if (filtro.getCampo().equalsIgnoreCase("CGCCPF")) {
                where += " AND NCGCCPF = " + filtro.getValor();
            }
            if (filtro.getCampo().equalsIgnoreCase("CODCLI")) {
                         where += " AND CODCLI = " + filtro.getValor();
            }
            
            if (filtro.getCampo().equalsIgnoreCase("CODCLI_LOJA")) {
                         where += " AND IDCLIENTE_N = " + filtro.getValor();
            }
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }
}
