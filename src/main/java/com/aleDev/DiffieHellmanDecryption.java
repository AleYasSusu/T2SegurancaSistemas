package com.aleDev;

import java.math.BigInteger;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DiffieHellmanDecryption {

    public static void main(String[] args) throws Exception {
        // Etapa 1: Diffie-Hellman
        String P_HEX = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C6" +
                "9A6A9DCA52D23B616073E28675A23D189838EF1EEE652C0" +
                "13ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD70" +
                "98488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0" +
                "A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708" +
                "DF1FB2BC 2E4A4371";
        String G_HEX = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507F" +
                "D6406CFF14266D31266FEA1E5C41564B777E690F5504F213" +
                "160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1" +
                "909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28A" +
                "D662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24" +
                "855E6EEB22B3B2E5";

        BigInteger p = new BigInteger(P_HEX.replaceAll("\\s", ""), 16);
        BigInteger g = new BigInteger(G_HEX.replaceAll("\\s", ""), 16);

        BigInteger a = new BigInteger("1179384367866944962701170917197320572924798963556873971958304443979154591845677174190613581118814432035661344670984319756742925295640982913397441690376024917967445622577274761166639357060674284056878097937114193036961094567556356894279455858784040014544473324470018056719570570534418388285723752403200707717");
        BigInteger A = g.modPow(a, p);

        // Receber B (substitua pelo valor recebido)
        String BHex = "00AF500EC6CBAF03F5C539855D13F84831ECFD0CD855EF79BBBFE45BD1A62111A1E98A7D49A47412F7E564A45239B12FEDC5C366570325C0E5E1F4C64E1FC736EA8C89F725A96AF9F859DDF96CC2582670680F849A5305B598DF6400381FA0D15A9B59DD29477BDE659948223C9A52DE95EF8895187CD966239E2F28D3EE058C6906358FDA653E7883925F21F6B5D457B0FD004D052DF4222D44B80E09229F6B287BD48CE22647F0FBFFE96CDAA5A6A21C5989309E4B15A54DE030282F170307F3BD070F7CC1421028AA8E85EC80806071BB29C4BEF2E04A713E289FC3DBFBF7188874D2BD0916643185D6D500C66BEAA420D571A21093CED1D8D60A88";
        validateHexInput(BHex);
        BigInteger B = new BigInteger(BHex, 16);

        BigInteger V = B.modPow(a, p);

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(V.toByteArray());

        if (hash.length < 16) {
            throw new IllegalStateException("Hash gerado possui menos de 16 bytes");
        }

        byte[] sBytes = new byte[16];
        System.arraycopy(hash, hash.length - 16, sBytes, 0, 16);

        // Etapa 2: Decifrar, inverter, e recifrar
        String receivedData = "49a4508207f0fd2d97fc1b116b6ac83d1ec21b7454bef94b5f303596db2b32c0849327bb4058ef39fabd6523b93dfd6317816aa76d64da8eae06d4720989b884a4d1a1ef1f8e35704659c5c8c67f0fc2";
        processMessage(receivedData, sBytes);
    }

    private static void processMessage(String encryptedData, byte[] key) throws Exception {
        // Extrair IV e mensagem criptografada
        String ivHex = encryptedData.substring(0, 32);  // Verifique se o IV tem exatamente 16 bytes
        String encryptedMessageHex = encryptedData.substring(32);  // O restante é a mensagem cifrada

        byte[] iv = hexStringToBytes(ivHex);  // Conversão do IV
        byte[] encryptedMessage = hexStringToBytes(encryptedMessageHex);  // Conversão da mensagem cifrada

        // Verifique o comprimento da mensagem cifrada para garantir que seja múltiplo de 16 bytes
        if (encryptedMessage.length % 16 != 0) {
            throw new IllegalStateException("A mensagem cifrada não tem um comprimento adequado para AES.");
        }

        // Decifrar a mensagem
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));  // Configuração da cifra para decifração
        byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessage);  // Descriptografar a mensagem

        String decryptedMessage = new String(decryptedMessageBytes);
        System.out.println("Mensagem decifrada: " + decryptedMessage);

        // Inverter a mensagem
        String reversedMessage = new StringBuilder(decryptedMessage).reverse().toString();
        System.out.println("Mensagem invertida: " + reversedMessage);

        // Cifrar novamente a mensagem invertida
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));  // Reconfiguração da cifra para cifragem
        byte[] encryptedReversedMessageBytes = cipher.doFinal(reversedMessage.getBytes());

        System.out.println("Mensagem cifrada para envio (hex): " + ivHex + bytesToHex(encryptedReversedMessageBytes));
    }

    private static byte[] hexStringToBytes(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    private static void validateHexInput(String hex) {
        if (!hex.matches("[0-9a-fA-F]+")) {
            throw new IllegalArgumentException("Entrada hexadecimal inválida: " + hex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
