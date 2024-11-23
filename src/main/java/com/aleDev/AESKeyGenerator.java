package com.aleDev;

import java.math.BigInteger;
import java.security.MessageDigest;

public class AESKeyGenerator {

    // Método para gerar a chave AES a partir da chave secreta compartilhada
    public static byte[] generateKeyFromSharedSecret(BigInteger sharedSecret) throws Exception {
        // Converte o segredo compartilhado para um array de bytes
        byte[] sharedSecretBytes = sharedSecret.toByteArray();

        // Gera o hash SHA-256 para derivar a chave AES
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] aesKey = sha256.digest(sharedSecretBytes);

        // Log da chave AES gerada
        System.out.println("Chave AES gerada a partir do segredo compartilhado: " + bytesToHex(aesKey));

        return aesKey;
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

