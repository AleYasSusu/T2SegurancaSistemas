package com.aleDev;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

public class CryptoService {

    /**
     * Gera o hash SHA-256 de uma entrada de bytes.
     * SHA-256 é uma função de hash criptográfica que gera um resumo fixo de 256 bits.
     * @param input Array de bytes a ser "hasheado".
     * @return O hash SHA-256 da entrada.
     * @throws Exception Se ocorrer um erro no processo de hashing.
     */
    public byte[] hashSHA256(byte[] input) throws Exception {
        // Obtém uma instância do algoritmo SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // Calcula o hash e retorna como um array de bytes
        return digest.digest(input);
    }

    /**
     * Cifra uma mensagem (plaintext) usando AES no modo CBC com preenchimento PKCS5.
     * @param plaintext A mensagem em texto plano que será cifrada.
     * @param key A chave de criptografia de 128 bits.
     * @param iv O vetor de inicialização (IV) de 16 bytes usado no modo CBC.
     * @return O texto cifrado como um array de bytes.
     * @throws Exception Se ocorrer um erro durante o processo de criptografia.
     */
    public byte[] encryptAES(byte[] plaintext, byte[] key, byte[] iv) throws Exception {
        // Cria uma instância do algoritmo AES no modo CBC com preenchimento PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // Define a chave de criptografia
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // Define o vetor de inicialização
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        // Inicializa o modo de operação (cifrar) com a chave e o IV
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        // Aplica a criptografia à mensagem e retorna o texto cifrado
        return cipher.doFinal(plaintext);
    }

    /**
     * Decifra uma mensagem cifrada (ciphertext) usando AES no modo CBC com preenchimento PKCS5.
     * @param ciphertext O texto cifrado que será decifrado.
     * @param key A chave de criptografia de 128 bits.
     * @param iv O vetor de inicialização (IV) de 16 bytes usado no modo CBC.
     * @return O texto decifrado como um array de bytes.
     * @throws Exception Se ocorrer um erro durante o processo de descriptografia.
     */
    public byte[] decryptAES(byte[] ciphertext, byte[] key, byte[] iv) throws Exception {
        // Cria uma instância do algoritmo AES no modo CBC com preenchimento PKCS5
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // Define a chave de criptografia
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // Define o vetor de inicialização
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        // Inicializa o modo de operação (decifrar) com a chave e o IV
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        // Aplica a descriptografia ao texto cifrado e retorna o texto em claro
        return cipher.doFinal(ciphertext);
    }

    /**
     * Gera um vetor de inicialização (IV) aleatório de 16 bytes.
     * O IV é usado para garantir que a mesma mensagem cifrada com a mesma chave
     * produza resultados diferentes no modo CBC.
     * @return Um vetor de inicialização (IV) de 16 bytes.
     */
    public byte[] generateRandomIV() {
        // Cria um array de 16 bytes para o IV
        byte[] iv = new byte[16];
        // Preenche o array com valores aleatórios usando SecureRandom
        new java.security.SecureRandom().nextBytes(iv);
        // Retorna o IV gerado
        return iv;
    }
}
