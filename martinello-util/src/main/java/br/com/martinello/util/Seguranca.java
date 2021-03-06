package br.com.martinello.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/*
 * Principal.java
 *
 * Created on 09/06/2009, 21:28:53
 */
/**
 *
 * @author Sidnei Favoreto
 */
public class Seguranca {

    public String status;
    static Cipher ecipher;
    static Cipher dcipher;

    public Seguranca() {
    }

    Seguranca(SecretKey key) {
        try {
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }

    public static String encrypt(String str) {
        try {

            byte[] iso = str.getBytes("ISO-8859-1");

            // criptografa
            byte[] enc = ecipher.doFinal(iso);

            // Retorna um string com o encode base64
            //return new sun.misc.BASE64Encoder().encode(enc);
            return Base64.getEncoder().encodeToString(enc);
        } catch (javax.crypto.BadPaddingException | IllegalBlockSizeException | java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String str) {
        try {
            // Decodifica o string em bytes
            //byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            byte[] dec = Base64.getDecoder().decode(str);

            // descriptografa
            byte[] iso = dcipher.doFinal(dec);

            // Retorna o string em utf8
            return new String(iso, "ISO-8859-1");
        } catch (javax.crypto.BadPaddingException | IllegalBlockSizeException | java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*      public static byte[] hexToBytes(char[] hex) {
    int length = hex.length / 2;
    byte[] raw = new byte[length];
    for (int i = 0; i < length; i++) {
    int high = Character.digit(hex[i * 2], 16);
    int low = Character.digit(hex[i * 2 + 1], 16);
    int value = (high << 4) | low;
    if (value > 127)
    value -= 256;
    
    raw[i] = (byte) value;
    }
    return raw;
    }
    
    public static byte[] hexToBytes(String hex) {
    return hexToBytes(hex.toCharArray());
    }
     */
    public static byte[] hexBytes(String hexText) {

        String decodedText = null;
        String chunk = null;

        int numBytes = hexText.length() / 2;

        byte[] rawToByte = new byte[numBytes];
        int offset = 0;
        int bCounter = 0;
        for (int i = 0; i < numBytes; i++) {
            chunk = hexText.substring(offset, offset + 2);
            offset += 2;
            rawToByte[i] = (byte) (Integer.parseInt(chunk, 16));
        }
        return rawToByte;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public static String criptografar(String texto) {
        String chave = "5446615264454F49";
        byte[] rawkey = hexBytes(chave);
        SecretKey key = new SecretKeySpec(rawkey, "DES");

        // Cria a classe que criptografa
        Seguranca encrypter = new Seguranca(key);

        // Criptografa
        String encrypted = encrypter.encrypt(texto);

        return encrypted;

    }

    public static String descriptografar(String texto) {
        String chave = "5446615264454F49";

        byte[] rawkey = hexBytes(chave);
        SecretKey key = new SecretKeySpec(rawkey, "DES");

        Seguranca encrypter = new Seguranca(key);
        //String encrypted = encrypter.encrypt(jTextField1.getText());
        String decrypted = encrypter.decrypt(texto);

        return decrypted;
    }
}
