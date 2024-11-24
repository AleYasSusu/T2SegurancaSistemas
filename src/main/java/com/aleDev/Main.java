package com.aleDev;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            // Dados iniciais
            String pHex = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371";
            String gHex = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406CFF14266D31266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28AD662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B2E5";

            BigInteger p = new BigInteger(pHex, 16);
            BigInteger g = new BigInteger(gHex, 16);
            BigInteger a = new BigInteger("1179384367866944962701170917197320572924798963556873971958304443979154591845677174190613581118814432035661344670984319756742925295640982913397441690376024917967445622577274761166639357060674284056878097937114193036961094567556356894279455858784040014544473324470018056719570570534418388285723752403200707717");

            DiffieHellmanService dhService = new DiffieHellmanService(p, g);
            CryptoService cryptoService = new CryptoService();

            // 1. Calcular chave pública A
            BigInteger A = dhService.calculatePublicKey(a);
            System.out.println("Chave pública (A): " + A.toString(16));

            // 2. Simular recebimento de B e calcular chave compartilhada
            String bHex = "5D7AF139233F5DC33B214519476273E90B2122748FB5AA4E6A0740E319DFE7411C6E6200B655869F0EF18ABFDBFB656C02452D61E2263559FE24BC354C8E27A60648776EFCC2739EB180A1360AEAFE08B5146C0F56EA6A9A42543891D00E3F5F367E46579B972D441C2DC43542641F13BEFF765DF202CAC161D482C9AC37F856";
            BigInteger B = new BigInteger(bHex, 16);
            BigInteger V = dhService.calculateSharedSecret(B, a);

            // 3. Gerar chave de sessão S
            byte[] vBytes = V.toByteArray();
            byte[] sHash = cryptoService.hashSHA256(vBytes);
            byte[] sKey = Arrays.copyOfRange(sHash, sHash.length - 16, sHash.length);

            // 4. Decifrar mensagem do professor
            String encryptedMessageWithIVHex = "9686b5abb530844a2d2102ff4b04baf58ca6be39fa28eeaeb42dff8dfbc40f3a9413e95797fdb619b5e35ae3d2451bf454310069f83068d8cec2b73f0da6aa1d40580184703961b03b7d2c77dbc105c4";

            // Extrair IV (primeiros 16 bytes = 32 caracteres hexadecimais)
            String ivHex = encryptedMessageWithIVHex.substring(0, 32);
            String encryptedMessageHex = encryptedMessageWithIVHex.substring(32);

            // Converter para byte arrays
            byte[] iv = hexStringToByteArray(ivHex);
            byte[] encryptedMessage = hexStringToByteArray(encryptedMessageHex);

            System.out.println("IV extraído: " + ivHex);
            System.out.println("Mensagem cifrada extraída: " + encryptedMessageHex);

            // Decifrar mensagem
            byte[] decryptedMessage = cryptoService.decryptAES(encryptedMessage, sKey, iv);
            String message = new String(decryptedMessage, StandardCharsets.UTF_8).trim();
            System.out.println("Mensagem decifrada: " + message);


            // 5. Reverter mensagem e cifrar novamente
            String reversedMessage = new StringBuilder(message).reverse().toString();
            System.out.println("Mensagem invertida: " + reversedMessage); // Log adicional

            byte[] randomIV = cryptoService.generateRandomIV();
            byte[] encryptedResponse = cryptoService.encryptAES(reversedMessage.getBytes(StandardCharsets.UTF_8), sKey, randomIV);

            // 6. Exibir mensagem final no formato correto
            String responseHex = byteArrayToHex(randomIV) + byteArrayToHex(encryptedResponse);
            System.out.println("Mensagem cifrada de resposta: " + responseHex);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
