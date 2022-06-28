package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
//import com.sun.xml.internal.ws.resources.SoapMessages;
import java.util.ArrayList;
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
//import sun.misc.BASE64Encoder;
import java.io.IOException;
import java.util.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarBaixaSpcController {

    public String incluirSpcResponseAvalista, baixarSpcResponse, baixarSpcFaultCode;
    public static String myNamespace, myNamespaceBranco, myNamespaceURI, autenticacaoString;
    public static String soapEndpointUrl;
    public static String soapAction = "";
    public String user = "399092";
    public String password = "27102018";
    private SOAPMessage soapResponse;
    private SOAPMessage soapMessage;
    private SOAPPart soapPart;
    private SOAPEnvelope envelope;
    private SOAPBody soapBody;
    private SOAPElement soapBodyElem;
    private SOAPElement insumoExcluirSpc;
    private SOAPElement tipoPessoa;
    private SOAPElement dadosPessoaFisica;
    private SOAPElement cpf;
    private SOAPElement nome;
    private SOAPElement dadosPessoaJuridica;
    private SOAPElement cnpj;
    private SOAPElement razaoSocial;
    private SOAPElement nomeComercial;
    private SOAPElement codigoAssociado;
    private SOAPElement dataCompra;
    private SOAPElement dataVencimento;
    private SOAPElement numeroContrato;
    private SOAPElement motivoExclusao;
    private SOAPElement idMotivoExclusao;
    private SOAPElement descricaoMotivoExclusao;
    public NodeList nList;
    public ParcelasEnviarController spcController = new ParcelasEnviarController();
    public List<ParcelasEnviarModel> lbaixaSpcModel = new ArrayList();
    public boolean resultado;
    public ParcelasEnviarModel parcelasEnviarSpcModel;
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();
    private volatile int retornoBxSpc, idExtrator;
    // public ParcelasEnviarSpcDAO insumoSpsDAO = new ParcelasEnviarSpcDAO();

    public ParcelasEnviarBaixaSpcController() {
        soapAction = "";
        myNamespace = "web";
        myNamespaceBranco = "";
        myNamespaceURI = "http://webservice.spc.insumo.spcjava.spcbrasil.org/";
        soapEndpointUrl = "https://servicos.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";
        //soapEndpointUrl = "https://treina.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService";
        //processamentoBaixaSpcModel = new ProcessamentoModel();
        // callSoapWebService(soapEndpointUrl, soapAction, parcelasEnviarSpcModel, "C", processamentoBaixaSpcModel);
    }

    public int callSoapWebService(ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            baixarSpcResponse = "0";

            soapMessage = createSOAPRequest(soapAction, parcelasEnviarSpcModel, tipo);
            autenticacaoString = parcelasEnviarSpcModel.getOperadorSpc() + ":" + parcelasEnviarSpcModel.getSenhaSpc();

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);
            headers.addHeader("Content-Type", "text/xml");
            headers.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(autenticacaoString.getBytes()));
         
            soapMessage.saveChanges();

            // Print the request message, just for debugging purposes /
            System.out.println("Request SOAP Message:");
            soapMessage.writeTo(System.out);
            System.out.println("\n");

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();
            Document doc = soapResponse.getSOAPBody().getOwnerDocument();
            // Document doc = soapConnection.call(soapMessage, soapEndpointUrl).getSOAPBody().getOwnerDocument();
            nList = doc.getElementsByTagName("sucesso");
            baixarSpcResponse = (doc.getElementsByTagName("sucesso").getLength() > 0 ? doc.getElementsByTagName("sucesso").item(0).getTextContent() : "0");
            //nList = doc.getElementsByTagName("sucesso");
            if (baixarSpcResponse.contains("Registro removido com sucesso!")) {
                if (tipo.equalsIgnoreCase("C")) {
                    retornoBxSpc = 1;
                    resultado = spcController.AtualizarParcelaSpcClienteB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                } else if (tipo.equalsIgnoreCase("A")) {
                    resultado = spcController.AtualizarParcelaSpcAvalistaB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                }
            } else {
                // doc = soapConnection.call(soapMessage, soapEndpointUrl).getSOAPBody().getOwnerDocument();

                nList = doc.getElementsByTagName("faultstring");
                baixarSpcResponse = (doc.getElementsByTagName("faultstring").getLength() > 0 ? doc.getElementsByTagName("faultstring").item(0).getTextContent() : "0");
                nList = doc.getElementsByTagName("faultcode");
                baixarSpcFaultCode = (doc.getElementsByTagName("faultcode").getLength() > 0 ? doc.getElementsByTagName("faultcode").item(0).getTextContent() : "0");
                if (baixarSpcResponse.contains("Registro não encontrado para a exclusão")) {
                    if (tipo.equalsIgnoreCase("C")) {
                        retornoBxSpc = 0;
                        resultado = spcController.AtualizarParcelaSpcClienteB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                    } else if (tipo.equalsIgnoreCase("A")) {
                        resultado = spcController.AtualizarParcelaSpcAvalistaB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                    }
                } else {
                    if (tipo.equalsIgnoreCase("C")) {
                        retornoBxSpc = 0;
                        resultado = spcController.AtualizarParcelaSpcClienteB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                    } else if (tipo.equalsIgnoreCase("A")) {
                        resultado = spcController.AtualizarParcelaSpcAvalistaB(parcelasEnviarSpcModel, baixarSpcResponse, baixarSpcFaultCode);
                    }
                }
            }
            soapConnection.close();
            /* Gerar Status Extrator */
            idExtrator = parcelasEnviarSpcModel.getIdExtrator();
            retornoEnvio.atualizaStatus(idExtrator);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            retornoBxSpc = 0;
            baixarSpcResponse = "Erro Campo Obrigatório não informado";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, baixarSpcFaultCode, baixarSpcResponse);
        } catch (SOAPException sx) {
            sx.printStackTrace();
            retornoBxSpc = 0;
            baixarSpcResponse = "Erro Criar Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, baixarSpcFaultCode, baixarSpcResponse);
        } catch (IOException ex) {
            ex.printStackTrace();
            retornoBxSpc = 0;
            baixarSpcResponse = "Erro ao Ler Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, baixarSpcFaultCode, baixarSpcResponse);
        }
        return retornoBxSpc;
    }

    public SOAPMessage createSOAPRequest(String soapAction, ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
        try {
            MessageFactory messageFactory;
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();
            envelope = soapPart.getEnvelope();
            soapBody = envelope.getBody();
            // SOAP Envelope
            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
            soapBodyElem = soapBody.addChildElement("excluirSpc", myNamespace);
            insumoExcluirSpc = soapBodyElem.addChildElement("excluir");
            tipoPessoa = insumoExcluirSpc.addChildElement("tipo-pessoa");
            dadosPessoaFisica = insumoExcluirSpc.addChildElement("dados-pessoa-fisica");
            cpf = dadosPessoaFisica.addChildElement("cpf");
            nome = dadosPessoaFisica.addChildElement("nome");

            /* DADOS REFERENTE A PESSOA FISICA */
            dadosPessoaJuridica = insumoExcluirSpc.addChildElement("dados-pessoa-juridica");
            cnpj = dadosPessoaJuridica.addChildElement("cnpj");
            razaoSocial = dadosPessoaJuridica.addChildElement("razao-social");
            nomeComercial = dadosPessoaJuridica.addChildElement("nome-comercial");

            /* DADOS REFERENTES AOS ASSOCIADOS */
            codigoAssociado = insumoExcluirSpc.addChildElement("codigo-associado");

            /* INFORMAÇÕES REFERENTES A COMPRA */
            dataCompra = insumoExcluirSpc.addChildElement("data-compra");
            dataVencimento = insumoExcluirSpc.addChildElement("data-vencimento");
            numeroContrato = insumoExcluirSpc.addChildElement("numero-contrato");

            /* NATUREZA DA INCLUSÃO E MOTIVO DA INCLUSÃO */
            motivoExclusao = insumoExcluirSpc.addChildElement("motivo-exclusao");
            idMotivoExclusao = motivoExclusao.addChildElement("id");
            descricaoMotivoExclusao = motivoExclusao.addChildElement("descricao-motivo");

            /* INSUMO EXCLUSAO SPC */
            if (tipo.equalsIgnoreCase("C")) {
                if (parcelasEnviarSpcModel.getTipoPessoa().equalsIgnoreCase("F")) {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                    /* TAG PESSOA FISICA */
                    cpf.setAttribute("regiao-origem", parcelasEnviarSpcModel.getCpfOrigem());//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", parcelasEnviarSpcModel.getCpf());//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                    cnpj.setAttribute("numero", "");
                    razaoSocial.setValue("");
                    nomeComercial.setValue("");
                } else {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                    cpf.setAttribute("regiao-origem", "");//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", "");//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue("");
                    cnpj.setAttribute("numero", parcelasEnviarSpcModel.getCpf());
                    razaoSocial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                    nomeComercial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                }
            } else if (tipo.equalsIgnoreCase("A")) {
                if (parcelasEnviarSpcModel.getTipoPessoaAval().equalsIgnoreCase("F")) {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoaAval());
                    /* TAG PESSOA FISICA */
                    cpf.setAttribute("regiao-origem", parcelasEnviarSpcModel.getCpfAvalOrigem());//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", parcelasEnviarSpcModel.getCpfAval());//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                    cnpj.setAttribute("numero", "");
                    razaoSocial.setValue("");
                    nomeComercial.setValue("");
                } else {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoaAval());
                    cpf.setAttribute("regiao-origem", "");//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", "");//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue("");
                    cnpj.setAttribute("numero", parcelasEnviarSpcModel.getCpfAval());
                    razaoSocial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                    nomeComercial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                }

            }
            /* TAG INFORMAÇÕES DO ASSOCIADO */
            codigoAssociado.setValue(parcelasEnviarSpcModel.getCodigoAssociado());

            /* TAG INFORMAÇÕES REFERENTES A COMPRA */
            dataCompra.setValue(parcelasEnviarSpcModel.getDataLancamento() + "T00:00:00");
            dataVencimento.setValue(parcelasEnviarSpcModel.getDataVencimento() + "T00:00:00");
            numeroContrato.setValue(parcelasEnviarSpcModel.getNumeroDoContrato());

            /* NATUREZA DA INCLUSÃO */
            idMotivoExclusao.setValue(parcelasEnviarSpcModel.getIdMotivoBaixaSpc());
            descricaoMotivoExclusao.setValue(parcelasEnviarSpcModel.getMotivoBaixaSpc());

        } catch (SOAPException ex) {
            retornoBxSpc = 0;
            baixarSpcResponse = "Erro Criar Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, baixarSpcFaultCode, baixarSpcResponse);
        }
        return soapMessage;
    }

}
