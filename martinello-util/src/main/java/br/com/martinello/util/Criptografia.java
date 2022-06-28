/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Sidnei
 */
public class Criptografia {

    protected Cipher desCipher;

    public Criptografia() {
        try {
            KeyGenerator gerador = KeyGenerator.getInstance("DES");
            SecretKey chaveDES = gerador.generateKey();

            //Criação do cipher que conterá os objetos de criptografia
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Iniciando cipher para criptografia
            desCipher.init(Cipher.ENCRYPT_MODE, chaveDES);

            //Dados a serem cripografados
            byte[] mensagemOriginal = "Mensagem oculta!".getBytes();

            System.out.println("Conteúdo byte: " + mensagemOriginal);
            System.out.println("Conteúdo texto: " + new String(mensagemOriginal));

            // Excriptando os dados
            byte[] mensagemCriptografada = desCipher.doFinal(mensagemOriginal);

            System.out.println("Conteúdo criptografado : " + mensagemCriptografada);

            // Iniciando o chipher para realizar a descriptografia
            desCipher.init(Cipher.DECRYPT_MODE, chaveDES);

            // Texto a ser descriptografado
            byte[] textoDescriptografado = desCipher.doFinal(mensagemCriptografada);

            System.out.println("Texto Descriptografado: " + new String(textoDescriptografado));

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Não foi possível localizar o algorítmo de criptografia!" + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.err.println("O mecanismo de preenchimento solicitado não existe no ambiente (Sistema Operacional)!" + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println("Chave inválida!" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.err.println("Tamanho do bloco da mensagem inválido!" + e.getMessage());
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.err.println("Preenchimento incorreto de dados!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public String criptografar(String texto) {
        try {
            if (texto.trim().length() > 0) {
                byte[] mensagemCriptografada = desCipher.doFinal(texto.getBytes());
                return mensagemCriptografada.toString();
            }
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String descriptografar(String texto) {
        try {
            if (texto.trim().length() > 0) {
                byte[] textoDescriptografado = desCipher.doFinal(texto.getBytes());
                return textoDescriptografado.toString();
            }
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
