package com.aleDev;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class AES {

    // Método para descriptografar a mensagem
    public static String decrypt(byte[] aesKey, byte[] iv, byte[] ciphertext) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Key key = new javax.crypto.spec.SecretKeySpec(aesKey, "AES");

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(ciphertext);

        // Log da chave, IV e da mensagem descriptografada
        System.out.println("Chave AES utilizada para a descriptografia: " + bytesToHex(aesKey));
        System.out.println("IV utilizado na descriptografia: " + bytesToHex(iv));
        System.out.println("Texto descriptografado: " + new String(decrypted));

        return new String(decrypted);
    }

    // Método para criptografar a mensagem
    public static byte[] encrypt(byte[] aesKey, byte[] iv, String message) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Key key = new javax.crypto.spec.SecretKeySpec(aesKey, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(message.getBytes());

        // Log da chave, IV e da mensagem criptografada
        System.out.println("Chave AES utilizada para a criptografia: " + bytesToHex(aesKey));
        System.out.println("IV utilizado na criptografia: " + bytesToHex(iv));
        System.out.println("Texto criptografado: " + bytesToHex(encrypted));

        return encrypted;
    }

    // Método auxiliar para converter bytes em formato hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
