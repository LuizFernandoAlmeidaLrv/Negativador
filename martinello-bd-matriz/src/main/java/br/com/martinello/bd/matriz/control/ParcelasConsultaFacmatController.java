/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
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
public class ParcelasConsultaFacmatController {

    public static String myNamespace;
    public static SOAPMessage soapResponse, soapMessage;
    private SOAPConnectionFactory soapConnectionFactory;
    private SOAPConnection soapConnection;
    private MessageFactory messageFactory;
    private SOAPPart soapPart;
    private SOAPEnvelope envelope;
    private SOAPBody soapBody;
    private SOAPElement soapBodyElem;
    private SOAPElement autenticacao;
    private SOAPElement chaveDeAcesso;
    private SOAPElement keyDeAcesso;
    private SOAPElement idDoRegistro;
    private Document docMessage;
    public NodeList nList;
    public String url, serverURI, serverURiRequest;
    public String baixaBvsIdRetorno, consultaBvsMenssage, baixaBvsCodigo;
    public volatile int retornoConsBvs, idExtrator;
    public boolean resultado;
    public ParcelasEnviarController parcelasEnviarController = new ParcelasEnviarController();
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();
   
    public ParcelasConsultaFacmatController() {

        //url = "http://www.btor.com.br/FacmatDev/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";;
        url = "https://www.facmat.com.br/CREDICONSULT_MARTINELO/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";
        myNamespace = "tem";
        serverURI = "http://tempuri.org/";
        serverURiRequest = "http://tempuri.org/Consultar";
    }

    public int callSoapWebService(ParcelasEnviarModel parcelasConsultarBvsModel) throws ErroSistemaException {
        try {
            URL endpoint = new URL(null, url,
                    new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    // Connection settings
                    connection.setConnectTimeout(60000); // 10 sec
                    connection.setReadTimeout(600000); // 1 min
                    return (connection);
                }
            });

            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            soapMessage = createSOAPRequest(parcelasConsultarBvsModel);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", serverURiRequest);
            soapMessage.saveChanges();
            System.out.println("SoapMessage.:");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
            soapResponse = soapConnection.call(soapMessage, endpoint);
            System.out.println("SoapResponse.:");
            soapResponse.writeTo(System.out);
            System.out.println("\n");

            docMessage = soapResponse.getSOAPBody().getOwnerDocument();
            nList = docMessage.getElementsByTagName("isEnviado");
            consultaBvsMenssage = (docMessage.getElementsByTagName("isEnviado").getLength() > 0 ? "" + docMessage.getElementsByTagName("isEnviado").item(0).getTextContent() : "0");
            if (consultaBvsMenssage.equalsIgnoreCase("true")) {
                docMessage = soapResponse.getSOAPBody().getOwnerDocument();
                nList = docMessage.getElementsByTagName("isSucesso");
                consultaBvsMenssage = (docMessage.getElementsByTagName("isSucesso").getLength() > 0 ? "" + docMessage.getElementsByTagName("isSucesso").item(0).getTextContent() : "0");
                if (consultaBvsMenssage.contains("true")) {
                    consultaBvsMenssage = "sucesso";
                    resultado = parcelasEnviarController.RetornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
                    retornoConsBvs = 1;
                } else {
                    nList = docMessage.getElementsByTagName("idRetorno");
                    consultaBvsMenssage = (docMessage.getElementsByTagName("idRetorno").getLength() != 0 ? "" + docMessage.getElementsByTagName("idRetorno").item(0).getTextContent() : "0");
                    /*Caso o retorno de sucesso seja false, mas o codigo de retorno seja sucesso o registro sera tratado como sucesso*/
                    if (consultaBvsMenssage.equals("32") || consultaBvsMenssage.equals("48") || consultaBvsMenssage.equals("41") || consultaBvsMenssage.equals("89") || consultaBvsMenssage.equals("100")
                            || consultaBvsMenssage.equals("34") ||consultaBvsMenssage.equals("29") ||consultaBvsMenssage.equals("5182") ||consultaBvsMenssage.equals("5164")) {
                        resultado = parcelasEnviarController.RetornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
                        retornoConsBvs = 1;
                    } else {
                        resultado = parcelasEnviarController.RetornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
                        retornoConsBvs = 0;
                    }

                }
            } else {
                nList = docMessage.getElementsByTagName("idRetorno");
                consultaBvsMenssage = (docMessage.getElementsByTagName("idRetorno").getLength() > 0 ? "" + docMessage.getElementsByTagName("idRetorno").item(0).getTextContent() : "0");
                if (consultaBvsMenssage.equals("144")) {
                    resultado = parcelasEnviarController.RetornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
                    retornoConsBvs = 0;
                } else if (consultaBvsMenssage.equals("37")) {
                    consultaBvsMenssage = "sucesso";
                    resultado = parcelasEnviarController.RetornoConsultaInclusao(parcelasConsultarBvsModel, consultaBvsMenssage);
                }
                consultaBvsMenssage = "Registro ainda não enviado para Bvs. Aguarde e refaça a consulta após alguns minutos.";
                parcelasEnviarController.insertLog(parcelasConsultarBvsModel, consultaBvsMenssage);
                retornoConsBvs = 0;
            }

            soapConnection.close();
            //  soapConnection.close();
            /* Gerar Status Extrator */
            idExtrator = parcelasConsultarBvsModel.getIdExtrator();
            retornoEnvio.atualizaStatus(idExtrator);

        } catch (SOAPException ex) {
            ex.printStackTrace();
            consultaBvsMenssage = "Erro ao Criar Envelope de Envio";
            parcelasEnviarController.insertLog(parcelasConsultarBvsModel, consultaBvsMenssage);
            retornoConsBvs = 0;
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            consultaBvsMenssage = "Erro ao Ler Envelope de Envio.";
            parcelasEnviarController.insertLog(parcelasConsultarBvsModel, consultaBvsMenssage);
            retornoConsBvs = 0;
            throw new ErroSistemaException(ex.getMessage(), ex);
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            consultaBvsMenssage = "Erro de Sistema. Erro não catalogado.";
            parcelasEnviarController.insertLog(parcelasConsultarBvsModel, consultaBvsMenssage);
            retornoConsBvs = 0;
            throw new ErroSistemaException(ex.getMessage(), ex);
        }

        return retornoConsBvs;
    }

    private SOAPMessage createSOAPRequest(ParcelasEnviarModel parcelasConsultarBvsModel) throws ErroSistemaException {
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();
            // SOAP Envelope
            envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(myNamespace, serverURI);
            soapBody = envelope.getBody();
            soapBodyElem = soapBody.addChildElement("Consultar", myNamespace);
            autenticacao = soapBodyElem.addChildElement("p_autenticacao", myNamespace);
            chaveDeAcesso = autenticacao.addChildElement("CHAVE", myNamespace);
            keyDeAcesso = autenticacao.addChildElement("KEY", myNamespace);
            idDoRegistro = soapBodyElem.addChildElement("p_idRegistro", myNamespace);

            /* CAMPO COM OS VALORES SETADOS */
//            chaveDeAcesso.setValue("643DA096-E504-41DA-A9AC-0347F0E0016C");
//            keyDeAcesso.setValue("38C2BA3B-D892-4B2B-8121-68B922AD8AAD");
            //chaveDeAcesso.setValue("A9FEA80F-3C71-440A-9F0E-E74D9034241F");
            // keyDeAcesso.setValue("FF025919-1B6F-47EF-9C4A-BA4E1A7DB0CA");
            chaveDeAcesso.setValue(parcelasConsultarBvsModel.getChaveFacmat());
            keyDeAcesso.setValue(parcelasConsultarBvsModel.getKeyFacmat());
            idDoRegistro.setValue(parcelasConsultarBvsModel.getIdRegistroFacmat()+"");
            return soapMessage;

        } catch (SOAPException ex) {
            ex.printStackTrace();
            consultaBvsMenssage = "Erro ao Criar Envelope de Envio";
            parcelasEnviarController.insertLog(parcelasConsultarBvsModel, consultaBvsMenssage);
            retornoConsBvs = 0;
            throw new ErroSistemaException(ex.getMessage(), ex);
        }

    }

}
