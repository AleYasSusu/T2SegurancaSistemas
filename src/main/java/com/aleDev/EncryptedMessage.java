package com.aleDev;


import java.util.Arrays;

public class EncryptedMessage {
    private byte[] ciphertext;
    private byte[] iv;

    // Construtor para inicializar com ciphertext e iv
    public EncryptedMessage(byte[] ciphertext, byte[] iv) {
        this.ciphertext = ciphertext;
        this.iv = iv;
    }

    // Método para obter o ciphertext
    public byte[] getCiphertext() {
        return ciphertext;
    }

    // Método para obter o IV
    public byte[] getIv() {
        return iv;
    }

    // Método para criar um objeto EncryptedMessage a partir de um texto cifrado hexadecimal
    public static EncryptedMessage fromHex(String hex) {
        byte[] data = Utils.hexToBytes(hex);
        byte[] iv = Arrays.copyOfRange(data, 0, 16);  // Supondo que o IV tenha 16 bytes
        byte[] ciphertext = Arrays.copyOfRange(data, 16, data.length);
        return new EncryptedMessage(ciphertext, iv);
    }

    // Método para obter o ciphertext em formato hexadecimal
    public String getCiphertextHex() {
        return Utils.bytesToHex(ciphertext);
    }
}
