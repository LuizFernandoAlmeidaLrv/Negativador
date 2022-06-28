/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author sidnei.vieira
 */
public class HttpTransport extends HttpTransportSE {

    private static final String CATEGORIA = "HTTP";

    public HttpTransport(String s) {
        //super(s);
        //Time Out de 50 segundos
        super(s, 50000);
    }

    @Override
    public void call(String soapAction, SoapEnvelope soapenvelope) throws IOException, XmlPullParserException {
        // Aapenas para logar o xml elemento envelope do SOAP
        byte bytes[] = createRequestData(soapenvelope);
        String envelope = new String(bytes);
        //Log.i(CATEGORIA, "Envelope: " + envelope);
        super.call(soapAction, soapenvelope);

    }
}
