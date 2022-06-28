/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.PropriedadesBD;
import br.com.martinello.util.HttpTransport;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


/**
 *
 * @author Sidnei
 */
public class ConsultarUsuarioControl {

    //private String ipWSReceber = "192.168.0.3";
    private String urlWSReceber;
    private String soap_action;
    private String method_name = "AuthenticateJAAS";
    private String namespace = "http://services.senior.com.br";
    private SoapPrimitive retorno;
    private PropriedadesBD propriedadesBD;

    public Boolean ConsultarUsuarioControl(String nomeUsuario, String senhaUsuario) throws FileNotFoundException, IOException, XmlPullParserException {

        if (propriedadesBD == null) {
            propriedadesBD = new PropriedadesBD();
        }
        urlWSReceber = "http://" + propriedadesBD.getEnderecoWsReceberXML() + ":" + propriedadesBD.getPortaWsReceberXML() + "/g5-senior-services/sapiens_SyncMCWFUsers?wsdl";
        soap_action = "http://" + propriedadesBD.getEnderecoWsReceberXML() + ":" + propriedadesBD.getPortaWsReceberXML() + "/g5-senior-services/sapiens_SyncMCWFUsers";
        SoapObject request = new SoapObject(namespace, method_name);
        request.addProperty("user", propriedadesBD.getUsuarioWsReceberXML());
        request.addProperty("password", propriedadesBD.getSenhaWsReceberXML());
        request.addProperty("encryption", 0);
        SoapObject parametros = new SoapObject("", "parameters");

        parametros.addProperty("pmUserName", nomeUsuario);
        parametros.addProperty("pmUserPassword", senhaUsuario);
        //parametros.addProperty("SProAnt", codigoSGL);

        request.addSoapObject(parametros);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransport(urlWSReceber);

        httpTransport.call(soap_action, envelope);
        
        //System.out.println(request);

        SoapObject response = (SoapObject) envelope.bodyIn;

        //System.out.println(response);
        for (int c = 0; c < response.getPropertyCount(); c++) {
            SoapObject soapObject = (SoapObject) response.getProperty(c);
                    if (soapObject.getProperty("pmLogged").toString().equals("-1")) {
                return false;
            } else {
                return true;
            }

        }

        //return retorno.toString();
        //return listaXMLProcessar;
        return false;
    }
}
