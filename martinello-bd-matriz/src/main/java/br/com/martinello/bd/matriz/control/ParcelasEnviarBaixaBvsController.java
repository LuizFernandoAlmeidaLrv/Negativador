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

/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarBaixaBvsController {

    private String myNamespace;
    private String serverURI;
    private String serverURiRequest;
    private SOAPElement soapBodyElem;
    private SOAPElement autenticacao;
    private SOAPElement chaveDeAcesso;
    private SOAPElement keyDeAcesso;
    private SOAPElement insumoExclusao;
    private SOAPElement idDaInclusao;
    private SOAPElement idMotivoDaBaixa;
    private SOAPBody soapBody;
    private SOAPEnvelope envelope;
    private SOAPPart soapPart;
    private SOAPMessage soapMessage;
    private MessageFactory messageFactory;
    private SOAPMessage soapResponse;
    private String url;
    private String baixaBvsResponse, baixaBvsFaultCode;
    private boolean resultado;
    private NodeList nList;
    private Document docMessage, docIdRegistro;
    private ParcelasEnviarModel parcelasEnviarBvsModel;
    private ParcelasEnviarController parcelasEnviarController = new ParcelasEnviarController();
    //public ParcelasEnviarController parcelasEnviarController = new ParcelasEnviarController();
    private List<ParcelasEnviarModel> lbaixaBvsModel = new ArrayList();
    private String baixaBvsIdRetorno, bvsMenssage, baixaBvsCodigo, consultaBvsMenssage;
    private SOAPConnectionFactory soapConnectionFactory;
    private SOAPConnection soapConnection;
    private volatile int retornoBxBvs, idExtrator;
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();

    public ParcelasEnviarBaixaBvsController() {
        myNamespace = "tem";
        serverURI = "http://tempuri.org/";
        serverURiRequest = "http://tempuri.org/Excluir";
        // url = "http://www.btor.com.br/FacmatDev/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";
        url = "https://www.facmat.com.br/CREDICONSULT_MARTINELO/WEBSERVICE/ws_REGISTRO_INADIMPLENCIA.asmx";
        // callSoapWebService(parcelasEnviarBvsModel, processamentoBaixaBvsModel);
    }

    public int callSoapWebService(ParcelasEnviarModel parcelasEnviarBvsModel) throws ErroSistemaException {

        try {
            if (parcelasEnviarBvsModel.getIdRegistroFacmat() == 0) {
                bvsMenssage = "36";
                resultado = parcelasEnviarController.AtualizarParcelaBaixaBvs(parcelasEnviarBvsModel, bvsMenssage);
                retornoBxBvs = 0;
            } else {

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
                soapMessage = createSOAPRequest(parcelasEnviarBvsModel);
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
                /* Percorre o docXml SoapResponse e extrair o Result com o Id da Inclusão */
                docMessage = soapResponse.getSOAPBody().getOwnerDocument();

                nList = docMessage.getElementsByTagName("sucesso");
                baixaBvsResponse = (docMessage.getElementsByTagName("sucesso").getLength() > 0 ? "" + docMessage.getElementsByTagName("sucesso").item(0).getTextContent() : "0");
                if (baixaBvsResponse.contains("true")) {
                    bvsMenssage = "true";
                    retornoBxBvs = 1;
                    resultado = parcelasEnviarController.AtualizarParcelaBaixaBvs(parcelasEnviarBvsModel, bvsMenssage);
                } else {
                    nList = docMessage.getElementsByTagName("codigoErro");
                    bvsMenssage = (docMessage.getElementsByTagName("codigoErro").getLength() > 0 ? "" + docMessage.getElementsByTagName("codigoErro").item(0).getTextContent() : "0");
                    if (bvsMenssage.equalsIgnoreCase("37")) {
                        resultado = parcelasEnviarController.AtualizarParcelaBaixaBvs(parcelasEnviarBvsModel, bvsMenssage);
                        retornoBxBvs = 1;
                    } else {
                        retornoBxBvs = 0;
                        resultado = parcelasEnviarController.AtualizarParcelaBaixaBvs(parcelasEnviarBvsModel, bvsMenssage);
                       
                    }

                }

                soapConnection.close();
            }
            /* Gerar Status Extrator */

            idExtrator = parcelasEnviarBvsModel.getIdExtrator();
            retornoEnvio.atualizaStatus(idExtrator);
            parcelasEnviarBvsModel.setTipoParcela("B");
        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoBxBvs = 0;
            bvsMenssage = "Falha ao formar o envelope de envio";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarBvsModel, bvsMenssage);
        } catch (IOException ex) {
            ex.printStackTrace();
            retornoBxBvs = 0;
            bvsMenssage = "Falha IOException";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarBvsModel, bvsMenssage);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            retornoBxBvs = 0;
            bvsMenssage = "Falha campo nulo";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarBvsModel, bvsMenssage);
        }
        return retornoBxBvs;
    }

    private SOAPMessage createSOAPRequest(ParcelasEnviarModel parcelasEnviarBvsModel) {
        try {

            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();

            // SOAP Envelope
            envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(myNamespace, serverURI);
            //envelope.addNamespaceDeclaration("tem", serverURI);
            soapBody = envelope.getBody();
            soapBodyElem = soapBody.addChildElement("Excluir", myNamespace);
            autenticacao = soapBodyElem.addChildElement("p_autenticacao", myNamespace);
            chaveDeAcesso = autenticacao.addChildElement("CHAVE", myNamespace);
            keyDeAcesso = autenticacao.addChildElement("KEY", myNamespace);
            insumoExclusao = soapBodyElem.addChildElement("p_exclusao", myNamespace);
            idDaInclusao = insumoExclusao.addChildElement("IdRegistro", myNamespace);
            idMotivoDaBaixa = insumoExclusao.addChildElement("IdMotivoBaixa", myNamespace);

            /* CAMPO COM OS VALORES SETADOS */
//            chaveDeAcesso.setValue("A9FEA80F-3C71-440A-9F0E-E74D9034241F");
//            keyDeAcesso.setValue("FF025919-1B6F-47EF-9C4A-BA4E1A7DB0CA");
            chaveDeAcesso.setValue(parcelasEnviarBvsModel.getChaveFacmat());
            keyDeAcesso.setValue(parcelasEnviarBvsModel.getKeyFacmat());
            idDaInclusao.setValue(parcelasEnviarBvsModel.getIdRegistroFacmat() + "");
            idMotivoDaBaixa.setValue(parcelasEnviarBvsModel.getIdMotivoBaixaBvs());

        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoBxBvs = 0;
            bvsMenssage = "Falha ao formar o envelope de envio";
            parcelasEnviarController.AtualizarParcelaBaixaBvsErro(parcelasEnviarBvsModel, bvsMenssage);
        }

        return soapMessage;
    }

}
