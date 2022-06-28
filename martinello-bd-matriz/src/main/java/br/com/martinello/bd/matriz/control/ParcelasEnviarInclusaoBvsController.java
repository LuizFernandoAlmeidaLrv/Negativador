/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarInclusaoBvsController {

    private SOAPElement soapBodyElem;
    private SOAPElement autenticacao;
    private SOAPElement chaveDeAcesso;
    private SOAPElement keyDeAcesso;
    private SOAPElement insumoParaInclusao;
    private SOAPElement dataDaInclusao;
    private SOAPElement dataDaEmissao;
    private SOAPElement dataDoVencimento;
    private SOAPElement valorTitulo;
    private SOAPElement idNaturezaDaInclusao;
    private SOAPElement numeroContratoCliente;
    private SOAPElement idMotivoDaInclusao;
    private SOAPElement numeroDocumento;
    private SOAPElement nomeDoDevedor;
    private SOAPElement numeroRGDoDevedor;
    private SOAPElement orgaoEmissor;
    private SOAPElement dataEmissaoRG;
    private SOAPElement nomeDoPai;
    private SOAPElement nomeDaMae;
    private SOAPElement idLogradouro;
    private SOAPElement endereco;
    private SOAPElement bairro;
    private SOAPElement idCidade;
    private SOAPElement estadoUF;
    private SOAPElement cepCidade;
    private SOAPElement complemento;
    private SOAPElement sexoPessoa;
    private SOAPElement dataNascimento;
    private SOAPElement telefoneDdd;
    private SOAPElement numeroTelefone;
    private SOAPElement enderecoEmail;
    private SOAPElement tipoDePessoa;
    private SOAPElement idEstadoCivil;
    private SOAPElement numeroDoEndereco;
    private SOAPElement incluirCoobrigado;
    private SOAPElement numeroDoDocumentoCoobrigado;
    private SOAPElement dataNascimentoCoobrigado;
    private SOAPElement nomeDoCoobrigado;
    private SOAPElement idLogradouroCoobrigado;
    private SOAPElement enderecoDoCoobrigado;
    private SOAPElement numeroEnderecoDoCoobrigado;
    private SOAPElement bairroDoCoobrigado;
    private SOAPElement idCidadeDoCoobrigado;
    private SOAPElement ufEstadoDoCoobrigado;
    private SOAPElement cepCidadeDoCoobrigado;
    private SOAPElement enderecoEmailDoCoobrigado;
    private SOAPElement telefoneDddCoobrigado;
    private SOAPElement numeroTelefoneDoCoobrigado;
    private SOAPBody soapBody;
    private SOAPEnvelope envelope;
    private SOAPPart soapPart;
    private SOAPMessage soapMessage;
    private MessageFactory messageFactory;
    private String serverURI;
    private SOAPMessage soapResponse;
    private String soapEndpointUrl;
    private String serverURiRequest;
    private String myNamespace;
    private String retorno;
    private String incluirBvsFaultCode, incluirBvsIdInclusao, BvsMenssage;
    private boolean resultado;
    private String url;
    private NodeList nList;
    private Document doc;
    private ParcelasEnviarModel parcelasEnviarModel;
    private ParcelasEnviarController parcelasEnviarController = new ParcelasEnviarController();
    private List<ParcelasEnviarModel> linclusaoBvsModel = new ArrayList();
    private volatile int retornoIncBvs, idExtrator;
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();
    private AtualizarRegistroBvsControl atualizarRegistro = new AtualizarRegistroBvsControl();

    public ParcelasEnviarInclusaoBvsController() {
        // url = "http://www.btor.com.br/FacmatDev/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";
        url = "https://www.facmat.com.br/CREDICONSULT_MARTINELO/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";
        myNamespace = "tem";
        serverURI = "http://tempuri.org/";
        serverURiRequest = "http://tempuri.org/Incluir";
//        linclusaoBvsModel = parcelasEnviarController.listarBaixaBvs();
        // incluirBvsFaultCode = "";

    }

    public int callSoapWebService(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel) throws ErroSistemaException {
        try {
            if (parcelasEnviarInclusaoBvsModel.getIdRegistroFacmat() > 0 && parcelasEnviarInclusaoBvsModel.getStatusFacmat().equals("E")) {
                retornoIncBvs = atualizarRegistro.callSoapWebService(parcelasEnviarInclusaoBvsModel);
                retornoIncBvs = 1;
            } else {
                SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                SOAPConnection soapConnection = soapConnectionFactory.createConnection();

                // Send SOAP Message to SOAP Server
                soapMessage = createSOAPRequest(parcelasEnviarInclusaoBvsModel);
                MimeHeaders headers = soapMessage.getMimeHeaders();
                headers.addHeader("SOAPAction", serverURiRequest);
                soapMessage.saveChanges();
                System.out.println("Soap Message :");
                soapMessage.writeTo(System.out);
                System.out.println();
                soapResponse = soapConnection.call(soapMessage, url);
                System.out.println("Soap Response :");
                soapResponse.writeTo(System.out);

                /* Percorre o docXml SoapResponse e extrair o Result com o Id da Inclusão */
                doc = soapResponse.getSOAPBody().getOwnerDocument();
                nList = doc.getElementsByTagName("sucesso");
                BvsMenssage = (doc.getElementsByTagName("sucesso").getLength() > 0 ? doc.getElementsByTagName("sucesso").item(0).getTextContent() : "0");
                if (BvsMenssage.contains("true")) {
                    BvsMenssage = "true";
                    nList = doc.getElementsByTagName("idRegistro");
                    incluirBvsIdInclusao = (doc.getElementsByTagName("idRegistro").getLength() > 0 ? doc.getElementsByTagName("idRegistro").item(0).getTextContent() : "0");
                    resultado = parcelasEnviarController.RetornoInclusaoBvs(parcelasEnviarInclusaoBvsModel, BvsMenssage, incluirBvsIdInclusao);
                    retornoIncBvs = 1;
                } else if (BvsMenssage.contains("false")) {
                    nList = doc.getElementsByTagName("codigoErro");
                    BvsMenssage = (doc.getElementsByTagName("codigoErro").getLength() > 0 ? doc.getElementsByTagName("codigoErro").item(0).getTextContent() : "0");

                    nList = doc.getElementsByTagName("idRegistro");
                    incluirBvsIdInclusao = (doc.getElementsByTagName("idRegistro").getLength() > 0 ? doc.getElementsByTagName("idRegistro").item(0).getTextContent() : "0");
                    resultado = parcelasEnviarController.RetornoInclusaoBvs(parcelasEnviarInclusaoBvsModel, BvsMenssage, incluirBvsIdInclusao);
                    retornoIncBvs = 0;
                } else {
                    BvsMenssage = "Erro Nao Catalogado";
                    resultado = parcelasEnviarController.RetornoInclusaoBvs(parcelasEnviarInclusaoBvsModel, BvsMenssage, incluirBvsIdInclusao);
                    retornoIncBvs = 0;
                }

                soapConnection.close();
            }
            /* Gerar Status Extrator */
            idExtrator = parcelasEnviarInclusaoBvsModel.getIdExtrator();
            retornoEnvio.atualizaStatus(idExtrator);
        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoIncBvs = 0;
            BvsMenssage = "Falha ao formar o envelope de envio, ou serviço indisponível";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarInclusaoBvsModel, BvsMenssage);
        } catch (UnsupportedOperationException ex) {
            ex.printStackTrace();
            retornoIncBvs = 0;
            BvsMenssage = "Falha ao formar o envelope de envio";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarInclusaoBvsModel, BvsMenssage);
        } catch (IOException ex) {
            ex.printStackTrace();
            retornoIncBvs = 0;
            BvsMenssage = "Falha ao formar o envelope de envio";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarInclusaoBvsModel, BvsMenssage);

        }
        return retornoIncBvs;
    }

    private SOAPMessage createSOAPRequest(ParcelasEnviarModel parcelasEnviarInclusaoBvsModel) {
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();
            envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(myNamespace, serverURI);
            soapBody = envelope.getBody();
            soapBodyElem = soapBody.addChildElement("Incluir", myNamespace);
            autenticacao = soapBodyElem.addChildElement("p_autenticacao", myNamespace);
            chaveDeAcesso = autenticacao.addChildElement("CHAVE", myNamespace);
            keyDeAcesso = autenticacao.addChildElement("KEY", myNamespace);
            insumoParaInclusao = soapBodyElem.addChildElement("p_inclusao", myNamespace);
            dataDaInclusao = insumoParaInclusao.addChildElement("DataInclusao", myNamespace);
            dataDaEmissao = insumoParaInclusao.addChildElement("DataEmissao", myNamespace);
            dataDoVencimento = insumoParaInclusao.addChildElement("DataVencimento", myNamespace);
            valorTitulo = insumoParaInclusao.addChildElement("ValorTitulo", myNamespace);
            idNaturezaDaInclusao = insumoParaInclusao.addChildElement("IdNatureza", myNamespace);
            numeroContratoCliente = insumoParaInclusao.addChildElement("NumeroContratoCliente", myNamespace);
            idMotivoDaInclusao = insumoParaInclusao.addChildElement("IdMotivoInclusao", myNamespace);
            numeroDocumento = insumoParaInclusao.addChildElement("NumeroDocumento", myNamespace);
            nomeDoDevedor = insumoParaInclusao.addChildElement("NomeDevedor", myNamespace);
            numeroRGDoDevedor = insumoParaInclusao.addChildElement("NumeroRGDevedor", myNamespace);
            orgaoEmissor = insumoParaInclusao.addChildElement("OrgaoEmissor", myNamespace);
            dataEmissaoRG = insumoParaInclusao.addChildElement("DataEmissaoRG", myNamespace);
            nomeDoPai = insumoParaInclusao.addChildElement("NomePai", myNamespace);
            nomeDaMae = insumoParaInclusao.addChildElement("NomeMae", myNamespace);
            idLogradouro = insumoParaInclusao.addChildElement("IdLogradouro", myNamespace);
            endereco = insumoParaInclusao.addChildElement("Endereco", myNamespace);
            bairro = insumoParaInclusao.addChildElement("Bairro", myNamespace);
            idCidade = insumoParaInclusao.addChildElement("CodigoIBGECidade", myNamespace);
            estadoUF = insumoParaInclusao.addChildElement("UF", myNamespace);
            cepCidade = insumoParaInclusao.addChildElement("CEP", myNamespace);
            complemento = insumoParaInclusao.addChildElement("Complemento", myNamespace);
            sexoPessoa = insumoParaInclusao.addChildElement("Sexo", myNamespace);
            dataNascimento = insumoParaInclusao.addChildElement("DataNascimento", myNamespace);
            telefoneDdd = insumoParaInclusao.addChildElement("Ddd", myNamespace);
            numeroTelefone = insumoParaInclusao.addChildElement("Telefone", myNamespace);
            enderecoEmail = insumoParaInclusao.addChildElement("Email", myNamespace);
            tipoDePessoa = insumoParaInclusao.addChildElement("TipoPessoa", myNamespace);
            idEstadoCivil = insumoParaInclusao.addChildElement("IdEstadoCivil", myNamespace);
            numeroDoEndereco = insumoParaInclusao.addChildElement("NumeroEndereco", myNamespace);
            incluirCoobrigado = insumoParaInclusao.addChildElement("IsIncluirCoobrigado", myNamespace);
            numeroDoDocumentoCoobrigado = insumoParaInclusao.addChildElement("NumeroDocumentoCoobrigado", myNamespace);
            dataNascimentoCoobrigado = insumoParaInclusao.addChildElement("DataNascimentoCoobrigado", myNamespace);
            nomeDoCoobrigado = insumoParaInclusao.addChildElement("NomeCoobrigado", myNamespace);
            idLogradouroCoobrigado = insumoParaInclusao.addChildElement("IdLogradouroCoobrigado", myNamespace);
            enderecoDoCoobrigado = insumoParaInclusao.addChildElement("EnderecoCoobrigado", myNamespace);
            numeroEnderecoDoCoobrigado = insumoParaInclusao.addChildElement("NumeroEnderecoCoobrigado", myNamespace);
            bairroDoCoobrigado = insumoParaInclusao.addChildElement("BairroCoobrigado", myNamespace);
            idCidadeDoCoobrigado = insumoParaInclusao.addChildElement("IdCidadeCoobrigado", myNamespace);
            ufEstadoDoCoobrigado = insumoParaInclusao.addChildElement("UFCoobrigado", myNamespace);
            cepCidadeDoCoobrigado = insumoParaInclusao.addChildElement("CepCoobrigado", myNamespace);
            enderecoEmailDoCoobrigado = insumoParaInclusao.addChildElement("EmailCoobrigado", myNamespace);
            telefoneDddCoobrigado = insumoParaInclusao.addChildElement("DddCoobrigado", myNamespace);
            numeroTelefoneDoCoobrigado = insumoParaInclusao.addChildElement("TelefoneCoobrigado", myNamespace);

            /* AUTENTICACAO*/
            // chaveDeAcesso.setValue("A9FEA80F-3C71-440A-9F0E-E74D9034241F");
            // keyDeAcesso.setValue("FF025919-1B6F-47EF-9C4A-BA4E1A7DB0CA");
            // dataDaInclusao.setValue(Utilitarios.converteData(new Date()).toString());
            chaveDeAcesso.setValue(parcelasEnviarInclusaoBvsModel.getChaveFacmat());
            keyDeAcesso.setValue(parcelasEnviarInclusaoBvsModel.getKeyFacmat());
            String hora = Utilitarios.dataHoraAtual();
            dataDaInclusao.setValue(Utilitarios.converteData(new Date()) + "T" + hora.substring(11, 19));
            // dataDaInclusao.setValue("2019-06-07T00:02:00");
            dataDaEmissao.setValue(parcelasEnviarInclusaoBvsModel.getDataLancamento() + "T00:00:00");
            dataDoVencimento.setValue(parcelasEnviarInclusaoBvsModel.getDataVencimento() + "T00:00:00");
            valorTitulo.setValue(parcelasEnviarInclusaoBvsModel.getValorParcela().toString());
            idNaturezaDaInclusao.setValue(parcelasEnviarInclusaoBvsModel.getIdNaturezaRegistroBvs());
            numeroContratoCliente.setValue(parcelasEnviarInclusaoBvsModel.getNumeroDoContrato());
            idMotivoDaInclusao.setValue(parcelasEnviarInclusaoBvsModel.getIdMotivoInclusaoBvs());
            numeroDocumento.setValue(parcelasEnviarInclusaoBvsModel.getCpf());
            nomeDoDevedor.setValue(parcelasEnviarInclusaoBvsModel.getNomeRazaoSocial());
            if (parcelasEnviarInclusaoBvsModel.getTipoPessoa().equalsIgnoreCase("F")) {
                numeroRGDoDevedor.setValue(parcelasEnviarInclusaoBvsModel.getNumeroRG() + "");
                orgaoEmissor.setValue(parcelasEnviarInclusaoBvsModel.getOrgaoEmissorRG() + "");
                dataEmissaoRG.setValue(parcelasEnviarInclusaoBvsModel.getDataEmissaoRG() + "T00:00:00");
                nomeDoPai.setValue(parcelasEnviarInclusaoBvsModel.getNomeDoPai() + "");
                nomeDaMae.setValue(parcelasEnviarInclusaoBvsModel.getNomeDaMae());
                dataNascimento.setValue(parcelasEnviarInclusaoBvsModel.getDataNascimento() + "T00:00:00");
                idEstadoCivil.setValue(parcelasEnviarInclusaoBvsModel.getIdEstadoCivil());
                if (parcelasEnviarInclusaoBvsModel.getSexo().equals("")) {
                    insumoParaInclusao.removeChild(sexoPessoa);
                } else {
                    sexoPessoa.setValue(parcelasEnviarInclusaoBvsModel.getSexo());
                }

            } else {
                insumoParaInclusao.removeChild(idEstadoCivil);
                insumoParaInclusao.removeChild(sexoPessoa);
                insumoParaInclusao.removeChild(dataNascimento);
                insumoParaInclusao.removeChild(nomeDoPai);
                insumoParaInclusao.removeChild(nomeDaMae);
                insumoParaInclusao.removeChild(dataEmissaoRG);
                insumoParaInclusao.removeChild(orgaoEmissor);
                insumoParaInclusao.removeChild(numeroRGDoDevedor);
                // idEstadoCivil.setValue("");
            }
            idLogradouro.setValue(parcelasEnviarInclusaoBvsModel.getEndIdLogradouro());
            endereco.setValue(parcelasEnviarInclusaoBvsModel.getEndLogradouro());
            bairro.setValue(parcelasEnviarInclusaoBvsModel.getEndBairro());
            idCidade.setValue(parcelasEnviarInclusaoBvsModel.getCodigoIbge());
            estadoUF.setValue(parcelasEnviarInclusaoBvsModel.getUfEstado());
            cepCidade.setValue(parcelasEnviarInclusaoBvsModel.getCep());
            complemento.setValue(parcelasEnviarInclusaoBvsModel.getEndComplemento() + "");
            numeroDoEndereco.setValue(parcelasEnviarInclusaoBvsModel.getEndNumero() + "");

            telefoneDdd.setValue(parcelasEnviarInclusaoBvsModel.getDddNumeroTel() + "");
            numeroTelefone.setValue(parcelasEnviarInclusaoBvsModel.getNumeroTel() + "");
            enderecoEmail.setValue(parcelasEnviarInclusaoBvsModel.getEmail() + "");
            tipoDePessoa.setValue(parcelasEnviarInclusaoBvsModel.getTipoPessoa());
            if (parcelasEnviarInclusaoBvsModel.getAvalista() == null) {

                incluirCoobrigado.setValue("false");
                insumoParaInclusao.removeChild(numeroDoDocumentoCoobrigado);
                insumoParaInclusao.removeChild(nomeDoCoobrigado);
                insumoParaInclusao.removeChild(numeroTelefoneDoCoobrigado);
                insumoParaInclusao.removeChild(dataNascimentoCoobrigado);
                insumoParaInclusao.removeChild(idLogradouroCoobrigado);
                insumoParaInclusao.removeChild(enderecoDoCoobrigado);
                insumoParaInclusao.removeChild(numeroEnderecoDoCoobrigado);
                insumoParaInclusao.removeChild(bairroDoCoobrigado);
                insumoParaInclusao.removeChild(idCidadeDoCoobrigado);
                insumoParaInclusao.removeChild(cepCidadeDoCoobrigado);
                insumoParaInclusao.removeChild(enderecoEmailDoCoobrigado);
                insumoParaInclusao.removeChild(telefoneDddCoobrigado);
                insumoParaInclusao.removeChild(ufEstadoDoCoobrigado);

            } else {
                /* INFORMAÇÕES DO AVALISTA */
                incluirCoobrigado.setValue("true");
                numeroDoDocumentoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getCpfAval());
                dataNascimentoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getDataNascimentoAval() + "T00:00:00");
                nomeDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getNomeRazaoSocialAval());
                idLogradouroCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getEndIdLogradouroAval());
                enderecoDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getEndLogradouro());
                numeroEnderecoDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getEndNumeroAval() + "");
                bairroDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getEndBairroAval());
                idCidadeDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getCodigoIbgeAval());
                ufEstadoDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getUfEstadoAval());
                cepCidadeDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getCepAval());
                enderecoEmailDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getEmailAval() + "");
                telefoneDddCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getDddNumeroTelAval() + "");
                numeroTelefoneDoCoobrigado.setValue(parcelasEnviarInclusaoBvsModel.getNumeroTelAval() + "");
            }

        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoIncBvs = 0;
            BvsMenssage = "Falha ao formar o envelope de envio";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarInclusaoBvsModel, BvsMenssage);
        }
        return soapMessage;
    }
}
