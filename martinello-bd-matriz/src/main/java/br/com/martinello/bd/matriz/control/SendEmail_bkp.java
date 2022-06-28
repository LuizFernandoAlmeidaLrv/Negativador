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
public class SendEmail_bkp {

    private InternetAddress internetaddress = new InternetAddress();

    public void SendEmail(List<ParcelasEnviarModel> ListRegistrosErro, String emailLojas, String userEmail, String senhaEmail, String emailPcopia, String email, String assEmail, String diretorio) {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Hotmail
         */
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.martinello.com.br");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               // return new PasswordAuthentication(userEmail, senhaEmail);
                return new PasswordAuthentication("informatica9.mart", senhaEmail);
            }
        });

        /**
         * Ativa Debug para sessão
         */
        //session.setDebug(true);
        //Store store = new POP3SSLStore(session, urlName);
        Message message = new MimeMessage(session);

        try {

           /* message.setFrom(new InternetAddress(email)); //Remetente
            message.setRecipients(Message.RecipientType.TO,
                    internetaddress.parse(emailLojas)); //Destinatário(s)
            message.setRecipients(Message.RecipientType.BCC,
                    internetaddress.parse(emailPcopia)); //Destinatário(s)*/
           message.setFrom(new InternetAddress("informatica9@martinello.com.br")); //Remetente
            message.setRecipients(Message.RecipientType.TO,
                    internetaddress.parse("informatica9@martinello.com.br")); //Destinatário(s)
            message.setRecipients(Message.RecipientType.BCC,
                    internetaddress.parse("informatica9@martinello.com.br")); //Destinatário(s)
            message.setSubject("ERRO - NEGATIVAÇÃO AUTOMÁTICA");//Assunto
            // message.setText("Você tem uma nova mensagem.");
            String msg = "<p align=center><font size=\"+1\" face=\"Liberation Sans\">Segue clientes para atualização no SGL.\n"
                    + "</font></p>\n"
                    + "<p align=center><font size=\"+1\" face=\"Liberation Sans\">- <u><font color=\"#ff0000\"><font color=\"#000000\">Se o caso do seu cliente não for</font><b> CEP</b>,\n"
                    + "<font color=\"#000000\">verificar se a&nbsp;</font> <b>DATA DE NASCIMENTO ou NOME DA MÃE</b></font></u><font color=\"#ff0000\">&nbsp;\n"
                    + "</font><font color=\"#ff0000\"><font color=\"#000000\">está incorreto. <br>\n"
                    + "</font></font></font></p>\n"
                    + "<p align=center><font size=\"+1\" face=\"Liberation Sans\"><b><font color=\"#ff0000\">- Verificar também o cadastro do AVALISTA.</font></b><br>\n"
                    + "</font></p>\n"
                    + "<p align=center><font size=\"+1\" face=\"Liberation Sans\"><font color=\"#ff0000\"><font color=\"#000000\">-<font color=\"#3333ff\"><i>\n"
                    + "</i><u>Se já tiver algum contrato pago, me avisar</u><u>.</u></font></font></font></font></p>\n"
                    + "<p align=center><font size=\"+1\" face=\"Liberation Sans\"><u><i>Conseguimos catalogar alguns erros.</i></u></font></p>\n"
                    + "<p align=center><font size=\"+1\" face=\"Liberation Sans\"><u><i><br>\n"
                    + "</i></u></font></p>\n"
                    + "<p><font size=\"+1\" face=\"Liberation Sans\"><u><i></i></u></font></p>\n"
                    + "<tbody>";

            String corpoMensagem = "<td height=\"17\" align=\"left\">Filial</td>\n"
                    + "<td align=\"left\">Cod.Cliente</td>\n"
                    + "<td align=\"left\">Documento</td>\n"
                    + "<td align=\"left\">Nome</td>\n"
                    + "<td align=\"left\">Contrato</td>\n"
                    + "<td align=\"left\">Data Vencimento</td>\n"
                    + "<td align=\"left\">Cod.Avalista</td>\n"
                    + "<td align=\"left\">Nome Avalista</td>\n"
                    + "<td align=\"left\">Valor</td>\n"
                    + "<td align=\"left\">Status Erro</td>\n"
                    + "</tr>";
            for (ParcelasEnviarModel parcelasEnviarModel : ListRegistrosErro) {
                corpoMensagem += "<td height=\"17\" align=\"left\">" + parcelasEnviarModel.getPontoFilial() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getCodCliente() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getCpfCnpj() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getNomeRazaoSocial() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getNumeroDoContrato() + "</td>\n"
                        + "<td align=\"left\">" + Utilitarios.converteDataString(parcelasEnviarModel.getDataVencimento(), "dd/MM/yyyy") + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getAvalista() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getNomeRazaoSocialAval() + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getValorParcela().setScale(2) + "</td>\n"
                        + "<td align=\"left\">" + parcelasEnviarModel.getStatusSpc() + "</td>\n"
                        + "</tr>";
            }
            if (corpoMensagem.trim().length() > 0) {
                corpoMensagem = "<table border=1 cellspacing=0 cellpadding=1 bordercolor=\"666633\" align=\"center\" bgcolor=\"#696969> " + msg + " " + corpoMensagem + "  </table> ";

            }

            //Cria o objeto que agrupa as multiplas partes do email
            MimeMultipart multipart = new MimeMultipart("related");

            //Cria a parte da mensagem que contém HTML
            BodyPart part = new MimeBodyPart();
            part.setContent("<h1></h1><p align=center>MENSAGEM AUTOMÁTICA!<b align=center>" + corpoMensagem + "</b> </p>" + "<img src=\"cid:image\">", "text/html");
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

}
