/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.ParcelasEnviarSpcDAO;
import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.Base64;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
//import sun.misc.BASE64Encoder;

/**
 *
 * @author luiz.almeida
 */
public class ParcelasEnviarInclusaoSpcController {

    private String incluirSpcFaultcode, incluirSpcResponse;
    private String myNamespace, myNamespaceBranco, myNamespaceURI, autenticacaoString;
    private String soapEndpointUrl;
    private String soapAction = "";
    private SOAPElement soapBodyElem;
    private SOAPElement insumoSpc;
    private SOAPElement tipoPessoa;
    private SOAPElement dadosPessoaFisica;
    private SOAPElement cpf;
    private SOAPElement nome;
    private SOAPElement numeroRg;
    private SOAPElement siglaEstadoRg;
    private SOAPElement dataNascimento;
    private SOAPElement nomePai;
    private SOAPElement nomeMae;
    private SOAPElement email;
    private SOAPElement telefone;
    private SOAPElement estadoCivil;
    private SOAPElement dadosPessoaJuridica;
    private SOAPElement cnpj;
    private SOAPElement razaoSocial;
    private SOAPElement nomeComercial;
    private SOAPElement emailJuridico;
    private SOAPElement codigoAssociado;
    private SOAPElement dataCompra;
    private SOAPElement dataVencimento;
    private SOAPElement codigoTipoDevedor;
    private SOAPElement numeroContrato;
    private SOAPElement valorDebito;
    private SOAPElement naturezaInclusao;
    private SOAPElement idNaturezaDaInclusao;
    private SOAPElement nomeNaturezaDaInclusao;
    private SOAPElement motivoExclusao;
    private SOAPElement idMotivoExclusao;
    private SOAPElement descricaoMotivoExclusao;
    private SOAPElement enderecoPessoa;
    private SOAPElement cep;
    private SOAPElement logradouro;
    private SOAPElement bairro;
    private SOAPElement numeroEnderecoPessoa;
    private SOAPElement complementoEnderecoPessoa;
    private SOAPElement cidadeEnderecoPessoa;
    private SOAPElement estadoEnderecoPessoa;
    private SOAPElement contaContrato;
    private SOAPElement devedorComprador;
    private SOAPElement documentoDevedorComprador;
    private SOAPElement tipoPessoaDevedorComprador;
    private SOAPElement notificarViaEmail;
    private SOAPEnvelope envelope;
    private SOAPBody soapBody;
    private MessageFactory messageFactory, messageFactoryAvalista;
    private SOAPMessage soapMessage, soapMessageAvalista;
    private SOAPMessage soapResponse, soapResponseAvalista;
    private SOAPPart soapPart, soapPartAvalista;
    private String ddd, dddAval;
    private Element eElement;
    private NodeList nList;
    private volatile int retornoIncSpc, idExtrator;

    private SOAPConnection soapConnection;

    public ParcelasEnviarController spcController = new ParcelasEnviarController();
    public List<ParcelasEnviarModel> linclusaoSpcModel = new ArrayList();
    public boolean resultado;
    public ParcelasEnviarModel parcelasEnviarSpcModel;
    private ParcelasEnviarSpcDAO insumoSpsDAO = new ParcelasEnviarSpcDAO();
    private RetornoEnvioController retornoEnvio = new RetornoEnvioController();

    public ParcelasEnviarInclusaoSpcController() {
        parcelasEnviarSpcModel = new ParcelasEnviarModel();
        myNamespace = "web";
        myNamespaceURI = "http://webservice.spc.insumo.spcjava.spcbrasil.org/";
        /* soapEndpointUrl = "https://treina.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";  Homologação */
        soapEndpointUrl = "https://servicos.spc.org.br/spc/remoting/ws/insumo/spc/spcWebService?wsdl";

    }

    public int callSoapWebService(ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
        try {
            if (tipo.equals("C") && parcelasEnviarSpcModel.getStatusSpc().equals("S")) {
                retornoIncSpc = 1;
            } else {

                URL endpoint = new URL(null, soapEndpointUrl,
                        new URLStreamHandler() {
                    @Override
                    protected URLConnection openConnection(URL url) throws IOException {
                        URL target = new URL(url.toString());
                        URLConnection connection = target.openConnection();
                        // Connection settings
                        connection.setConnectTimeout(10000); // 10 sec
                        connection.setReadTimeout(60000); // 1 min
                        return (connection);
                    }
                });
                soapConnection = SOAPConnectionFactory.newInstance().createConnection();

                incluirSpcResponse = "0";
                // Send SOAP Message to SOAP Server
                soapMessage = createSOAPRequest(soapAction, parcelasEnviarSpcModel, tipo);

                autenticacaoString = (parcelasEnviarSpcModel.getOperadorSpc().trim() + ":" + parcelasEnviarSpcModel.getSenhaSpc().trim());
                MimeHeaders headers = soapMessage.getMimeHeaders();
                headers.addHeader("SOAPAction", soapAction);
                headers.addHeader("Content-Type", "text/xml");
                headers.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(autenticacaoString.getBytes()));
                System.out.println("Authorization" + "Basic " + Base64.getEncoder().encodeToString(autenticacaoString.getBytes()));
                soapMessage.saveChanges();
                System.out.println("Request SOAP Message:");
                soapMessage.writeTo(System.out);
                System.out.println("\n");

                soapResponse = soapConnection.call(soapMessage, soapEndpointUrl);

                // Print the SOAP Response
                System.out.println("Response SOAP Message:");
                soapResponse.writeTo(System.out);
                //Leitura do SoapResponse
                Document doc = soapResponse.getSOAPBody().getOwnerDocument();
                nList = doc.getElementsByTagName("sucesso");
                incluirSpcResponse = (doc.getElementsByTagName("sucesso").getLength() > 0 ? doc.getElementsByTagName("sucesso").item(0).getTextContent() : "0");
                if (incluirSpcResponse.contains("Dados inseridos com sucesso!")) {
                    if (tipo.equalsIgnoreCase("C")) {
                        spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                        retornoIncSpc = 1;
                    } else if (tipo.equalsIgnoreCase("A")) {
                        spcController.AtualizarParcelaSpcAvalista(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                    }
                } else {
                    nList = doc.getElementsByTagName("faultstring");
                    incluirSpcResponse = (doc.getElementsByTagName("faultstring").getLength() > 0 ? doc.getElementsByTagName("faultstring").item(0).getTextContent() : "0");

                    nList = doc.getElementsByTagName("faultcode");
                    incluirSpcFaultcode = (doc.getElementsByTagName("faultcode").getLength() > 0 ? doc.getElementsByTagName("faultcode").item(0).getTextContent() : "0");

                    if (incluirSpcResponse.contains("Registro de SPC já existe")) {
                        if (tipo.equalsIgnoreCase("C")) {
                            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                            retornoIncSpc = 0;
                        } else if (tipo.equalsIgnoreCase("A")) {
                            spcController.AtualizarParcelaSpcAvalista(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                        }
                    } else {
                        if (tipo.equalsIgnoreCase("C")) {
                            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                            retornoIncSpc = 0;
                        } else if (tipo.equalsIgnoreCase("A")) {
                            spcController.AtualizarParcelaSpcAvalista(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
                        }
                    }
                }
                soapConnection.close();

                /* Gerar Status Extrator */
                idExtrator = parcelasEnviarSpcModel.getIdExtrator();
                retornoEnvio.atualizaStatus(idExtrator);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            retornoIncSpc = 0;
            incluirSpcResponse = "Erro ao Ler Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoIncSpc = 0;
            incluirSpcResponse = "Erro ao Criar Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
        
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            retornoIncSpc = 0;
            incluirSpcResponse = "Erro NullPoint.";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
        }

        return retornoIncSpc;

    }

    public SOAPMessage createSOAPRequest(String soapAction, ParcelasEnviarModel parcelasEnviarSpcModel, String tipo) throws ErroSistemaException {
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();
            envelope = soapPart.getEnvelope();
            soapBody = envelope.getBody();
            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
            soapBodyElem = soapBody.addChildElement("incluirSpc", myNamespace);
            insumoSpc = soapBodyElem.addChildElement("insumoSpc");
            tipoPessoa = insumoSpc.addChildElement("tipo-pessoa");

            /* DADOS REFERENTE A PESSOA FISICA */
            dadosPessoaFisica = insumoSpc.addChildElement("dados-pessoa-fisica");
            cpf = dadosPessoaFisica.addChildElement("cpf");
            nome = dadosPessoaFisica.addChildElement("nome");
            numeroRg = dadosPessoaFisica.addChildElement("numero-rg");
            siglaEstadoRg = dadosPessoaFisica.addChildElement("sigla-estado-rg");
            dataNascimento = dadosPessoaFisica.addChildElement("data-nascimento");
            nomePai = dadosPessoaFisica.addChildElement("nome-pai");
            nomeMae = dadosPessoaFisica.addChildElement("nome-mae");
            email = dadosPessoaFisica.addChildElement("email");
            telefone = dadosPessoaFisica.addChildElement("telefone");
            estadoCivil = dadosPessoaFisica.addChildElement("estado-civil");

            /* DADOS REFERENTE A PESSOA JURIDICA */
            dadosPessoaJuridica = insumoSpc.addChildElement("dados-pessoa-juridica");
            cnpj = dadosPessoaJuridica.addChildElement("cnpj");
            razaoSocial = dadosPessoaJuridica.addChildElement("razao-social");
            nomeComercial = dadosPessoaJuridica.addChildElement("nome-comercial");
            emailJuridico = dadosPessoaJuridica.addChildElement("email");

            /* DADOS REFERENTES AOS ASSOCIADOS */
            codigoAssociado = insumoSpc.addChildElement("codigo-associado");

            /* INFORMAÇÕES REFERENTES A COMPRA */
            dataCompra = insumoSpc.addChildElement("data-compra");
            dataVencimento = insumoSpc.addChildElement("data-vencimento");
            codigoTipoDevedor = insumoSpc.addChildElement("codigo-tipo-devedor");
            numeroContrato = insumoSpc.addChildElement("numero-contrato");
            valorDebito = insumoSpc.addChildElement("valor-debito");

            /* NATUREZA DA INCLUSÃO E MOTIVO DA INCLUSÃO */
            naturezaInclusao = insumoSpc.addChildElement("natureza-inclusao");
            idNaturezaDaInclusao = naturezaInclusao.addChildElement("id");
            nomeNaturezaDaInclusao = naturezaInclusao.addChildElement("nome");

            /* ENDEREÇO */
            enderecoPessoa = insumoSpc.addChildElement("endereco-pessoa");
            cep = enderecoPessoa.addChildElement("cep");
            logradouro = enderecoPessoa.addChildElement("logradouro");
            bairro = enderecoPessoa.addChildElement("bairro");
            numeroEnderecoPessoa = enderecoPessoa.addChildElement("numero");
            complementoEnderecoPessoa = enderecoPessoa.addChildElement("complemento");
            cidadeEnderecoPessoa = enderecoPessoa.addChildElement("cidade");
            estadoEnderecoPessoa = cidadeEnderecoPessoa.addChildElement("estado");
            contaContrato = insumoSpc.addChildElement("conta-contrato");
            //  devedorComprador = insumoSpc.addChildElement("devedor-comprador");
            //  documentoDevedorComprador = devedorComprador.addChildElement("documento");
            //   tipoPessoaDevedorComprador = devedorComprador.addChildElement("tipo-pessoa");
            notificarViaEmail = insumoSpc.addChildElement("notificar-via-email");
            if (tipo.equalsIgnoreCase("C")) {
                if (parcelasEnviarSpcModel.getTipoPessoa().equalsIgnoreCase("F")) {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                    /* TAG PESSOA FISICA */
                    cpf.setAttribute("regiao-origem", parcelasEnviarSpcModel.getCpfOrigem());//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", parcelasEnviarSpcModel.getCpf());//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                    numeroRg.setValue(parcelasEnviarSpcModel.getNumeroRG());
                    siglaEstadoRg.setValue(parcelasEnviarSpcModel.getOrgaoEmissorRG());
                    dataNascimento.setValue(parcelasEnviarSpcModel.getDataNascimento() + "T00:00:00");
                    nomePai.setValue(parcelasEnviarSpcModel.getNomeDoPai());
                    nomeMae.setValue(parcelasEnviarSpcModel.getNomeDaMae());
                    email.setValue(parcelasEnviarSpcModel.getEmail());
                    if (parcelasEnviarSpcModel.getNumeroTel().length() >= 11) {
                        telefone.setAttribute("numero-ddd", parcelasEnviarSpcModel.getDddNumeroTel());
                        telefone.setAttribute("numero", parcelasEnviarSpcModel.getNumeroTel().substring(2));
                    } else {
                        dadosPessoaFisica.removeChild(telefone);
                    }
                    if (parcelasEnviarSpcModel.getEstadoCivil().equalsIgnoreCase("O")) {
                        estadoCivil.setValue("");
                    } else {
                        estadoCivil.setValue(parcelasEnviarSpcModel.getEstadoCivil());
                    }

                    cnpj.setAttribute("numero", "");
                    razaoSocial.setValue("");
                    nomeComercial.setValue("");
                    emailJuridico.setValue("");

                } else // juridica
                {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                    insumoSpc.removeChild(dadosPessoaFisica);
                    cnpj.setAttribute("numero", parcelasEnviarSpcModel.getCpf());
                    razaoSocial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                    nomeComercial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocial());
                    emailJuridico.setValue(parcelasEnviarSpcModel.getEmail());
                }

                /* TAG INFORMAÇÕES DO ASSOCIADO */
                codigoAssociado.setValue(parcelasEnviarSpcModel.getCodigoAssociado());
                /* TAG INFORMAÇÕES REFERENTES A COMPRA */
                dataCompra.setValue(parcelasEnviarSpcModel.getDataLancamento() + "T00:00:00");
                dataVencimento.setValue(parcelasEnviarSpcModel.getDataVencimento() + "T00:00:00");
                codigoTipoDevedor.setValue(parcelasEnviarSpcModel.getTipoDevedor());
                numeroContrato.setValue(parcelasEnviarSpcModel.getNumeroDoContrato());
                valorDebito.setValue(parcelasEnviarSpcModel.getValorParcela().toString());
                /* NATUREZA DA INCLUSÃO */
                idNaturezaDaInclusao.setValue(parcelasEnviarSpcModel.getIdNaturezaInclusaoSpc());
                nomeNaturezaDaInclusao.setValue(parcelasEnviarSpcModel.getNaturezaInclusaoSpc());
                //   idMotivoExclusao.setValue("");
                //   descricaoMotivoExclusao.setValue("");
                /* ENDEREÇO */
                cep.setValue(parcelasEnviarSpcModel.getCep());
                logradouro.setValue(parcelasEnviarSpcModel.getEndLogradouro());
                bairro.setValue(parcelasEnviarSpcModel.getEndBairro());
                numeroEnderecoPessoa.setValue(parcelasEnviarSpcModel.getEndNumero() + "");
                complementoEnderecoPessoa.setValue(parcelasEnviarSpcModel.getEndComplemento());
                cidadeEnderecoPessoa.setAttribute("nome", parcelasEnviarSpcModel.getNomeCidade());
                estadoEnderecoPessoa.setAttribute("sigla-uf", parcelasEnviarSpcModel.getUfEstado());
                //  documentoDevedorComprador.setValue(parcelasEnviarSpcModel.getCpf());
                // tipoPessoaDevedorComprador.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                notificarViaEmail.setValue("S");
            }
            if (tipo.equalsIgnoreCase("A")) {
                if (parcelasEnviarSpcModel.getTipoPessoaAval().equalsIgnoreCase("F")) {
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoaAval());
                    /* TAG PESSOA FISICA */
                    cpf.setAttribute("regiao-origem", parcelasEnviarSpcModel.getCpfAvalOrigem());//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", parcelasEnviarSpcModel.getCpfAval());//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                    numeroRg.setValue(parcelasEnviarSpcModel.getNumeroRGAval());
                    siglaEstadoRg.setValue(parcelasEnviarSpcModel.getOrgaoEmissorRGAval());
                    dataNascimento.setValue(parcelasEnviarSpcModel.getDataNascimentoAval() + "T00:00:00");
                    nomePai.setValue(parcelasEnviarSpcModel.getNomeDoPaiAval());
                    nomeMae.setValue(parcelasEnviarSpcModel.getNomeDaMaeAval());
                    email.setValue(parcelasEnviarSpcModel.getEmailAval());
                    if (parcelasEnviarSpcModel.getNumeroTelAval().length() >= 8) {
                        telefone.setAttribute("numero", parcelasEnviarSpcModel.getNumeroTelAval().substring(2));
                        telefone.setAttribute("numero-ddd", parcelasEnviarSpcModel.getDddNumeroTelAval());
                    } else {
                        dadosPessoaFisica.removeChild(telefone);
                    }
                    if (parcelasEnviarSpcModel.getEstadoCivilAval().equalsIgnoreCase("O")) {
                        estadoCivil.setValue("");
                    } else {
                        estadoCivil.setValue(parcelasEnviarSpcModel.getEstadoCivilAval());
                    }

                    cnpj.setAttribute("numero", "");
                    razaoSocial.setValue("");
                    nomeComercial.setValue("");
                    emailJuridico.setValue("");
                } else {   // juridica
                    tipoPessoa.setValue(parcelasEnviarSpcModel.getTipoPessoaAval());

//                    dadosPessoaFisica.removeChild(cpf);
//                    dadosPessoaFisica.removeChild(nome);
//                    dadosPessoaFisica.removeChild(numeroRg);
//                    dadosPessoaFisica.removeChild(siglaEstadoRg);
//                    dadosPessoaFisica.removeChild(dataNascimento);
//                    dadosPessoaFisica.removeChild(nomePai);
//                    dadosPessoaFisica.removeChild(nomeMae);
//                    dadosPessoaFisica.removeChild(email);
//                    dadosPessoaFisica.removeChild(estadoCivil);
                    dadosPessoaFisica.removeChild(telefone);


                    /* TAG PESSOA FISICA */
                    cpf.setAttribute("regiao-origem", "");//ORIGEM DO CPF CONSINTE DO 3º NUMERO DA DIREITA PARA A ESQUERDA CONTIDO NO CPF
                    cpf.setAttribute("numero", "");//NUMERO COMPLETO DO CPF(APENAS NUMEROS)
                    nome.setValue("");
                    numeroRg.setValue("");
                    siglaEstadoRg.setValue("");
                    dataNascimento.setValue("");
                    nomePai.setValue("");
                    nomeMae.setValue("");
                    email.setValue("");
                    estadoCivil.setValue("");

                    cnpj.setAttribute("numero", parcelasEnviarSpcModel.getCpfAval());
                    razaoSocial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                    nomeComercial.setValue(parcelasEnviarSpcModel.getNomeRazaoSocialAval());
                    emailJuridico.setValue(parcelasEnviarSpcModel.getEmailAval());
                }

                /* TAG INFORMAÇÕES DO ASSOCIADO */
                codigoAssociado.setValue(parcelasEnviarSpcModel.getCodigoAssociado());

                /* TAG INFORMAÇÕES REFERENTES A COMPRA */
                dataCompra.setValue(parcelasEnviarSpcModel.getDataLancamento() + "T00:00:00");
                dataVencimento.setValue(parcelasEnviarSpcModel.getDataVencimento() + "T00:00:00");
                codigoTipoDevedor.setValue(parcelasEnviarSpcModel.getTipoDevedorAval());
                numeroContrato.setValue(parcelasEnviarSpcModel.getNumeroDoContrato());
                valorDebito.setValue(parcelasEnviarSpcModel.getValorParcela().toString());

                /* NATUREZA DA INCLUSÃO */
                idNaturezaDaInclusao.setValue(parcelasEnviarSpcModel.getIdNaturezaInclusaoSpc());
                nomeNaturezaDaInclusao.setValue(parcelasEnviarSpcModel.getNaturezaInclusaoSpc());

                /* ENDEREÇO */
                cep.setValue(parcelasEnviarSpcModel.getCepAval());
                logradouro.setValue(parcelasEnviarSpcModel.getEndLogradouroAval());
                bairro.setValue(parcelasEnviarSpcModel.getEndBairroAval());
                numeroEnderecoPessoa.setValue(parcelasEnviarSpcModel.getEndNumeroAval());
                complementoEnderecoPessoa.setValue(parcelasEnviarSpcModel.getEndComplementoAval());
                cidadeEnderecoPessoa.setAttribute("nome", parcelasEnviarSpcModel.getNomeCidadeAval());
                estadoEnderecoPessoa.setAttribute("sigla-uf", parcelasEnviarSpcModel.getUfEstadoAval());
                // documentoDevedorComprador.setValue(parcelasEnviarSpcModel.getCpf());
                // tipoPessoaDevedorComprador.setValue(parcelasEnviarSpcModel.getTipoPessoa());
                notificarViaEmail.setValue("S");
            }

        } catch (SOAPException ex) {
            ex.printStackTrace();
            retornoIncSpc = 0;
            incluirSpcResponse = "Erro ao Criar Envelope de Envio";
            spcController.AtualizarParcelaSpcCliente(parcelasEnviarSpcModel, incluirSpcFaultcode, incluirSpcResponse);
        }
        return soapMessage;
    }
}
