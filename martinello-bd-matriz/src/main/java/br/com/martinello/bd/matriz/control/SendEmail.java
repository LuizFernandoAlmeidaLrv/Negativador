/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.domain.ParcelasEnviarModel;
import br.com.martinello.util.Utilitarios;
import java.io.File;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author luiz.almeida
 */
public class SendEmail {

    private InternetAddress internetaddress = new InternetAddress();

    public void SendEmail(List<ParcelasEnviarModel> ListRegistrosErro, String emailLojas, String userEmail, String senhaEmail, String emailPcopia, String email, String assEmail, String diretorio) {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Hotmail
         */
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");      
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // return new PasswordAuthentication(userEmail, senhaEmail);
                return new PasswordAuthentication(userEmail, senhaEmail);
            }
        });

        /**
         * Ativa Debug para sessão
         */
        //session.setDebug(true);
        //Store store = new POP3SSLStore(session, urlName);
        Message message = new MimeMessage(session);

        try {

            message.setFrom(new InternetAddress(email)); //Remetente
            message.setRecipients(Message.RecipientType.TO,
                    internetaddress.parse(emailLojas)); //Destinatário(s)
            message.setRecipients(Message.RecipientType.BCC,
                    internetaddress.parse(emailPcopia)); //Destinatário(s)
            /*
            message.setFrom(new InternetAddress("informatica9@martinello.com.br")); //Remetente
            message.setRecipients(Message.RecipientType.TO,
                    internetaddress.parse("informatica9@martinello.com.br")); //Destinatário(s)
            message.setRecipients(Message.RecipientType.BCC,
                    internetaddress.parse("informatica9@martinello.com.br")); //Destinatário(s)*/
            message.setSubject("ERRO - NEGATIVAÇÃO AUTOMÁTICA");//Assunto
            // message.setText("Você tem uma nova mensagem.");
            String msg = "<div id=\"outer\">\n"
                    + "        <div style=\"background-color:#B0C4DE;text-align:center;height:150px;margin-top:5px\">\n"
                    + "		 <p style=\"font-size:15px; color:Black; font-family:Verdana;\">Segue clientes para atualização dos dados cadastrais no sistema de frente de loja..</p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\">Se o caso do seu cliente não for</font><b> CEP</b>\n"
                    + "          <font color=\"#000000\">verificar se a&nbsp;</font> <b>DATA DE NASCIMENTO ou NOME DA MÃE</b></font></u><font color=\"#ff0000\">&nbsp;\n"
                    + "          </font><font color=\"#ff0000\"><font color=\"#000000\">está incorreto. <br>\n"
                    + "          </font></font></font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><font color=\"#ff0000\">- Verificar também o cadastro do AVALISTA.</font><br>\n"
                    + "          </font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><font color=\"#ff0000\"><font color=\"#000000\">-<font color=\"#3333ff\"><i>\n"
                    + "          </i><u>Se já tiver algum contrato pago, me avisar</u><u>.</u></font></font></font></font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><u><i><br>\n"
                    + "          </i></u></font></p>\n"
                    + "        </div>\n"
                    + "    </div>\n";

            String corpoMensagem = "<tr>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Filial</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Cod.Cliente</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Documento</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Nome</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Contrato</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Data Vencimento</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Cod.Avalista</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Nome Avalista</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Valor</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Status Erro</b></td>"
                    + "             </tr>\n";
            for (ParcelasEnviarModel parcelasEnviarModel : ListRegistrosErro) {
                corpoMensagem += "<tr>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getPontoFilial() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getCodCliente() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getCpfCnpj() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getNomeRazaoSocial() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getNumeroDoContrato() + "</td>\n"
                        + "             <td align=\"left\">" + Utilitarios.converteDataString(parcelasEnviarModel.getDataVencimento(), "dd/MM/yyyy") + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getAvalista() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getNomeRazaoSocialAval() + "</td>\n"
                        + "             <td align=\"left\">" + parcelasEnviarModel.getValorParcela().setScale(2) + "</td>\n"
                        + "             <td align=\"left\"><font color=\"#ff0000\"><b>" + parcelasEnviarModel.getStatusSpc().trim() + "</b></font></td>"
                        + "         </tr>\n";
            }
            if (corpoMensagem.trim().length() > 0) {
                corpoMensagem = "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"utf-8\"/>\n"
                        + "    <title>Teste Email</title>\n"
                        + "    <style>\n"
                        + "        hr {\n"
                        + "            border: 0; height: 1px; \n"
                        + "            background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0));\n"
                        + "        }\n"
                        + "        table \n"
                        + "        {\n"
                        + "            font-family: arial, sans-serif;border-collapse: collapse;\n"
                        + "            width:100%;\n"
                        + "        }\n"
                        + "        th, td \n"
                        + "        {\n"
                        + "            border: 1px solid #dddddd;\n"
                        + "            padding: 3px;\n"
                        + "        }\n"
                        + "        tr:nth-child(even) \n"
                        + "        {\n"
                        + "            background-color: #dddddd;\n"
                        + "        }\n"
                        + "         #outer {\n"
                        + "             margin:0 100px;\n"
                        + "             border:2px ; \n"
                        + "        }\n"
                        + "    </style> \n"
                        + "</head>\n"
                        + "    <body>\n"
                        + "        <div id=\"outer\">\n"
                        + "        <div style=\"margin-left:15%;margin-right:15%;text-align:center; margin-top:1%\">\n"
                        + "            <p style=\"font-size:30px;font-family:Verdana\"><b><I>INFORMATIVO DE CRÉDITO COBRANÇA</I></b></p>\n"
                        + "        </div>\n"
                        + "        <div style=\"background-color:#4682B4;text-align:center;height:35px;margin-top:5px\">\n"
                        + "            <p style=\"font-size:25px; color:white; font-family:Verdana;height:7px\"><B>REGISTROS COM DIVERGÊNCIAS</B></p>\n"
                        + "        </div>"
                        + "         </div>\n"
                        + "       <tr>\n"
                        + "      " + msg + "\n"
                        + "       </tr>\n"
                        + "        <div id=\"outer\"><table width 100% style=\"font-size:11px;\">\n"
                        + "         " + corpoMensagem + "\n"
                        + "        </table><hr>\n"
                        + "        <p style=\"text-align:center; font-family:Verdana; font-size:15px;\">Esse <b>email foi enviado automaticamente</b> pelo sistema,<b> não é necessário respondê-lo.</b><br> Em caso de dúvidas responda este email ou entre em contato com Crédito Cobrança.</p>\n"
                        + "        <div style=\"background-color:#4682B4;text-align:center;height:25px;\">\n"
                        + "            <p style=\"font-size:15px; color:white; font-family:Verdana;height:7px\">Eletromóveis Martinello - Km, MT 449, 6 - Bandeirantes, Lucas do Rio Verde - MT, 78455-000</p>\n"
                        + "        </div>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html> ";

            }

            //Cria o objeto que agrupa as multiplas partes do email
            MimeMultipart multipart = new MimeMultipart("related");

            //Cria a parte da mensagem que contém HTML
            BodyPart part = new MimeBodyPart();
            part.setContent(corpoMensagem + "<img src=\"cid:image\">", "text/html");
            //Adiciona a primeira parte
            multipart.addBodyPart(part);

            //Cria a parte do email que contém a imagem
            // String PathTillProject = System.getProperty("user.home");
            File file = new File(assEmail);
            part = new MimeBodyPart();
            part.setDataHandler(new DataHandler(new FileDataSource(file)));
            part.setFileName(file.getName());
            part.setDisposition("inline");
            part.setHeader("Content-ID", "<image>");

            System.out.println("html " + corpoMensagem);
            //Adiciona a segunda parte
            multipart.addBodyPart(part);

            message.setContent(multipart);

            //Envia a Mensagem
            Transport.send(message);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    public Session SendEmailTeste(String erro_teste, String emailLojas, String userEmail, String senhaEmail, String emailPcopia, String email, String assEmail, String diretorio) {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Hotmail
         */
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.martinello.com.br");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // return new PasswordAuthentication(userEmail, senhaEmail);
                return new PasswordAuthentication(userEmail, senhaEmail);
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);
        //Store store = new POP3SSLStore(session, urlName);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email)); //Remetente
            message.setRecipients(Message.RecipientType.TO,
                    internetaddress.parse(emailLojas)); //Destinatário(s)
            message.setRecipients(Message.RecipientType.BCC,
                    internetaddress.parse(emailPcopia)); //Destinatário(s)        
            message.setSubject("ERRO - NEGATIVAÇÃO AUTOMÁTICA");
            String msg = "<div id=\"outer\">\n"
                    + "        <div style=\"background-color:#B0C4DE;text-align:center;height:150px;margin-top:5px\">\n"
                    + "		 <p style=\"font-size:15px; color:Black; font-family:Verdana;\">Segue clientes para atualização dos dados cadastrais no sistema de frente de loja..</p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\">Se o caso do seu cliente não for</font><b> CEP</b>\n"
                    + "          <font color=\"#000000\">verificar se a&nbsp;</font> <b>DATA DE NASCIMENTO ou NOME DA MÃE</b></font></u><font color=\"#ff0000\">&nbsp;\n"
                    + "          </font><font color=\"#ff0000\"><font color=\"#000000\">está incorreto. <br>\n"
                    + "          </font></font></font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><font color=\"#ff0000\">- Verificar também o cadastro do AVALISTA.</font><br>\n"
                    + "          </font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><font color=\"#ff0000\"><font color=\"#000000\">-<font color=\"#3333ff\"><i>\n"
                    + "          </i><u>Se já tiver algum contrato pago, me avisar</u><u>.</u></font></font></font></font></p>\n"
                    + "          <p style=\"font-size:15px; color:Black; font-family:Verdana;\"><u><i><br>\n"
                    + "          </i></u></font></p>\n"
                    + "        </div>\n"
                    + "    </div>\n";

            String corpoMensagem = "<tr>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Filial</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Cod.Cliente</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Documento</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Nome</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Contrato</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Data Vencimento</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Cod.Avalista</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Nome Avalista</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Valor</b></td>\n"
                    + "                 <td style=\"font-size:15px;\" align=\"left\"><b>Status Erro</b></td>"
                    + "             </tr>\n";

            corpoMensagem += "<tr>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\">" + erro_teste + "</td>\n"
                    + "             <td align=\"left\"><font color=\"#ff0000\"><b>" + erro_teste + "</b></font></td>"
                    + "         </tr>\n";

            if (corpoMensagem.trim().length() > 0) {
                corpoMensagem = "<html>\n"
                        + "<head>\n"
                        + "    <meta charset=\"utf-8\"/>\n"
                        + "    <title>Teste Email</title>\n"
                        + "    <style>\n"
                        + "        hr {\n"
                        + "            border: 0; height: 1px; \n"
                        + "            background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.75), rgba(0, 0, 0, 0));\n"
                        + "        }\n"
                        + "        table \n"
                        + "        {\n"
                        + "            font-family: arial, sans-serif;border-collapse: collapse;\n"
                        + "            width:100%;\n"
                        + "        }\n"
                        + "        th, td \n"
                        + "        {\n"
                        + "            border: 1px solid #dddddd;\n"
                        + "            padding: 3px;\n"
                        + "        }\n"
                        + "        tr:nth-child(even) \n"
                        + "        {\n"
                        + "            background-color: #dddddd;\n"
                        + "        }\n"
                        + "         #outer {\n"
                        + "             margin:0 100px;\n"
                        + "             border:2px ; \n"
                        + "        }\n"
                        + "    </style> \n"
                        + "</head>\n"
                        + "    <body>\n"
                        + "        <div id=\"outer\">\n"
                        + "        <div style=\"margin-left:15%;margin-right:15%;text-align:center; margin-top:1%\">\n"
                        + "            <p style=\"font-size:30px;font-family:Verdana\"><b><I>INFORMATIVO DE CRÉDITO COBRANÇA</I></b></p>\n"
                        + "        </div>\n"
                        + "        <div style=\"background-color:#4682B4;text-align:center;height:35px;margin-top:5px\">\n"
                        + "            <p style=\"font-size:25px; color:white; font-family:Verdana;height:7px\"><B>REGISTROS COM DIVERGÊNCIAS</B></p>\n"
                        + "        </div>"
                        + "         </div>\n"
                        + "       <tr>\n"
                        + "      " + msg + "\n"
                        + "       </tr>\n"
                        + "        <div id=\"outer\"><table width 100% style=\"font-size:11px;\">\n"
                        + "         " + corpoMensagem + "\n"
                        + "        </table><hr>\n"
                        + "        <p style=\"text-align:center; font-family:Verdana; font-size:15px;\">Esse <b>email foi enviado automaticamente</b> pelo sistema,<b> não é necessário respondê-lo.</b><br> Em caso de dúvidas responda este email ou entre em contato com Crédito Cobrança.</p>\n"
                        + "        <div style=\"background-color:#4682B4;text-align:center;height:25px;\">\n"
                        + "            <p style=\"font-size:15px; color:white; font-family:Verdana;height:7px\">Eletromóveis Martinello - Km, MT 449, 6 - Bandeirantes, Lucas do Rio Verde - MT, 78455-000</p>\n"
                        + "        </div>\n"
                        + "        </div>\n"
                        + "    </body>\n"
                        + "</html> ";

            }
            //Cria o objeto que agrupa as multiplas partes do email
            MimeMultipart multipart = new MimeMultipart("related");
            //Cria a parte da mensagem que contém HTML
            BodyPart part = new MimeBodyPart();
            part.setContent(corpoMensagem + "<img src=\"cid:image\">", "text/html");
            //Adiciona a primeira parte
            multipart.addBodyPart(part);
            //Cria a parte do email que contém a imagem
            // String PathTillProject = System.getProperty("user.home");
            File file = new File(assEmail);
            part = new MimeBodyPart();
            part.setDataHandler(new DataHandler(new FileDataSource(file)));
            part.setFileName(file.getName());
            part.setDisposition("inline");
            part.setHeader("Content-ID", "<image>");
            System.out.println("html " + corpoMensagem);
            //Adiciona a segunda parte
            multipart.addBodyPart(part);
            message.setContent(multipart);
            //Envia a Mensagem
            Transport.send(message);

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return session;
    }

}
