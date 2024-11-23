package com.aleDev;

import java.math.BigInteger;

public class DiffieHellmanDecryption {

    public static void main(String[] args) {
        try {
            // Inicialização dos parâmetros
            BigInteger privateKey = KeyParameters.getPrivateKey();
            BigInteger publicKeyFromProfessor = KeyParameters.getPublicKeyFromProfessor();
            System.out.println("Chave privada recebida: " + privateKey.toString(16));
            System.out.println("Chave pública do professor: " + publicKeyFromProfessor.toString(16));

            // Cálculo do segredo compartilhado
            BigInteger sharedSecret = DiffieHellmanKeyCalculator.calculateSharedSecret(privateKey, publicKeyFromProfessor);
            System.out.println("sharedSecret: " + sharedSecret.toString(16));  // Log do segredo compartilhado

            // Geração da chave AES
            byte[] aesKey = AESKeyGenerator.generateKeyFromSharedSecret(sharedSecret);
            System.out.println("Chave AES (128 bits): " + Utils.bytesToHex(aesKey));  // Log da chave AES gerada

            // Mensagem criptografada
            String encryptedMessageHex = "49a4508207f0fd2d97fc1b116b6ac83d1ec21b7454bef94b5f303596db2b32c0849327bb4058ef39fabd6523b93dfd6317816aa76d64da8eae06d4720989b884a4d1a1ef1f8e35704659c5c8c67f0fc2";
            System.out.println("Mensagem cifrada (hexadecimal): " + encryptedMessageHex);  // Log da mensagem cifrada

            // Extração do IV da mensagem cifrada (primeiros 128 bits ou 16 bytes)
            String ivHex = encryptedMessageHex.substring(0, 32);  // Os primeiros 32 caracteres hexadecimais correspondem a 128 bits (16 bytes)
            byte[] iv = Utils.hexToBytes(ivHex);
            System.out.println("IV extraído: " + ivHex);  // Log do IV extraído

            // Criação do objeto EncryptedMessage a partir do hexadecimal
            EncryptedMessage encryptedMessage = EncryptedMessage.fromHex(encryptedMessageHex);  // Remover o IV da mensagem cifrada
            System.out.println("Texto cifrado extraído: " + encryptedMessage.getCiphertextHex());  // Log do texto cifrado extraído

            // Descriptografando a mensagem com o IV extraído
            String decryptedMessage = AES.decrypt(aesKey, iv, encryptedMessage.getCiphertext());  // Corrigido: passando o ciphertext como byte[]
            System.out.println("Mensagem decifrada: " + decryptedMessage);  // Log da mensagem decifrada

            // Invertendo a mensagem decifrada
            String reversedMessage = reverseString(decryptedMessage);
            System.out.println("Mensagem invertida: " + reversedMessage);  // Log da mensagem invertida

            // Cifrando novamente a mensagem invertida com o mesmo IV
            byte[] reEncryptedMessage = AES.encrypt(aesKey, iv, reversedMessage);
            String reEncryptedMessageHex = Utils.bytesToHex(reEncryptedMessage);
            System.out.println("Mensagem cifrada novamente: " + reEncryptedMessageHex);  // Log da mensagem cifrada novamente

        } catch (Exception e) {
            System.err.println("Erro durante o processo de cifragem/decifragem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
