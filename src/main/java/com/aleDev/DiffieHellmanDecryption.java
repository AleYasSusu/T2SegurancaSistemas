package com.aleDev;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DiffieHellmanDecryption {

    public static void main(String[] args) throws Exception {
        // Valores fornecidos (p e g em hexadecimal)
        String pHex = """
                B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B61
                6073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BF
                ACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0
                A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371
                """;


        // Convertendo os valores fornecidos para BigInteger
        BigInteger p = new BigInteger(pHex.replace("\n", "").replace(" ", ""), 16);

        // Valor fixo de 'a' fornecido
        BigInteger a = new BigInteger("1179384367866944962701170917197320572924798963556873971958304443979154591845677174190613581118814432035661344670984319756742925295640982913397441690376024917967445622577274761166639357060674284056878097937114193036961094567556356894279455858784040014544473324470018056719570570534418388285723752403200707717");

        // Calcular A = g^a mod p
        String AHex = "183DB190C75D5315F0C8F416A2EC14A71FC1B88FC5FE85C83D48378FB5D7F62CA10EFC9C3773131F12FD53FE356D7885945636FD6E85753781AA51E599F6C0B926739B200BC6EA84BEAC5D8717D59AD007367FCB3F12925E3B1E986E9EEB59AD23AD45EAB9EF7E99CBDDE498DEF5AFAF0093540AFF159B5278D54E7EACD898AE";
        BigInteger A = new BigInteger(AHex, 16);
        System.out.println("Valor de 'a' (secreto): " + a);
        System.out.println("Valor de 'A' (em hexadecimal): " + A.toString(16).toUpperCase());

        // Agora, calcular B^a mod p (assumindo que B seja fornecido)
        String BHex = "00AF500EC6CBAF03F5C539855D13F84831ECFD0CD855EF79BBBFE45BD1A62111A1E98A7D49A47412F7E564A45239B12FEDC5C366570325C0E5E1F4C64E1FC736EA8C89F725A96AF9F859DDF96CC2582670680F849A5305B598DF6400381FA0D15A9B59DD29477BDE659948223C9A52DE95EF8895187CD966239E2F28D3EE058C6906358FDA653E7883925F21F6B5D457B0FD004D052DF4222D44B80E09229F6B287BD48CE22647F0FBFFE96CDAA5A6A21C5989309E4B15A54DE030282F170307F3BD070F7CC1421028AA8E85EC80806071BB29C4BEF2E04A713E289FC3DBFBF7188874D2BD0916643185D6D500C66BEAA420D571A21093CED1D8D60A88";

        // Convertendo o valor de 'B' de hexadecimal para BigInteger
        BigInteger B = new BigInteger(BHex, 16);

        // Verificando o valor de 'B'
        System.out.println("Valor de 'B' (em hexadecimal): " + B.toString(16).toUpperCase());
        BigInteger V = B.modPow(a, p);  // Calcular V = B^a mod p
        System.out.println("Valor de 'V' (em hexadecimal): " + V.toString(16).toUpperCase());

        // Calcular SHA-256 de V para derivar a chave AES
        byte[] aesKey = calculateKeyFromV(V);
        System.out.println("Chave de AES (128 bits): " + bytesToHex(aesKey));

        // Mensagem cifrada fornecida (IV + EMsg)
        String encryptedMessageHex = "49a4508207f0fd2d97fc1b116b6ac83d1ec21b7454bef94b5f303596db2b32c0849327bb4058ef39fabd6523b93dfd6317816aa76d64da8eae06d4720989b884a4d1a1ef1f8e35704659c5c8c67f0fc2";

        // Decodificando a mensagem cifrada
        byte[] encryptedMessage = hexToBytes(encryptedMessageHex);
        byte[] iv = Arrays.copyOfRange(encryptedMessage, 0, 16);  // IV é o primeiro bloco de 128 bits
        byte[] cipherText = Arrays.copyOfRange(encryptedMessage, 16, encryptedMessage.length);  // O restante é a mensagem cifrada

        // Decodificando a mensagem com AES CBC
        String decryptedMessage = decryptAES_CBC(aesKey, iv, cipherText);
        System.out.println("Mensagem decifrada: " + decryptedMessage);

        // Inverter a mensagem decifrada
        String reversedMessage = new StringBuilder(decryptedMessage).reverse().toString();
        System.out.println("Mensagem invertida: " + reversedMessage);

        // Re-cifrar a mensagem invertida com um IV aleatório
        byte[] newIv = generateRandomIv();
        byte[] encryptedReversedMessage = encryptAES_CBC(aesKey, newIv, reversedMessage.getBytes());
        String encryptedReversedMessageHex = bytesToHex(newIv) + bytesToHex(encryptedReversedMessage);
        System.out.println("Mensagem cifrada novamente com IV aleatório: " + encryptedReversedMessageHex);
    }

    // Função para calcular a chave de AES a partir de V
    public static byte[] calculateKeyFromV(BigInteger V) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] VBytes = V.toByteArray();
        byte[] hash = digest.digest(VBytes);
        // Log para ver o valor de SHA-256 de V (S)
        System.out.println("Valor de S (SHA-256 de V, em hexadecimal): " + bytesToHex(hash));

        // Pegar os 128 bits menos significativos
        byte[] key = new byte[16]; // 128 bits = 16 bytes
        System.arraycopy(hash, 0, key, 0, 16);
        return key;
    }

    // Função para converter bytes para string hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString().toUpperCase();
    }

    // Função para converter hexadecimal para bytes
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    // Função para gerar um IV aleatório (128 bits)
    public static byte[] generateRandomIv() {
        byte[] iv = new byte[16]; // 128 bits
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }

    // Função para decifrar a mensagem com AES CBC
    public static String decryptAES_CBC(byte[] key, byte[] iv, byte[] cipherText) throws Exception {
        // Verifica se o IV e o CipherText possuem o tamanho adequado
        if (iv.length != 16) {
            throw new IllegalArgumentException("IV deve ter 16 bytes.");
        }
        if (cipherText.length % 16 != 0) {
            throw new IllegalArgumentException("CipherText deve ser um múltiplo de 16 bytes.");
        }

        // Inicializando o Cipher para decriptação
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new javax.crypto.spec.SecretKeySpec(key, "AES"), ivSpec);

        try {
            // Decriptando a mensagem
            byte[] decryptedBytes = cipher.doFinal(cipherText);
            return new String(decryptedBytes, "UTF-8");  // Usando UTF-8 para evitar problemas com encoding
        } catch (javax.crypto.BadPaddingException e) {
            System.err.println("Erro de padding durante a decriptação: " + e.getMessage());
            throw new Exception("Erro de padding. Verifique se a chave e o IV estão corretos.");
        }
    }

    // Função para cifrar a mensagem com AES CBC
    public static byte[] encryptAES_CBC(byte[] key, byte[] iv, byte[] plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, new javax.crypto.spec.SecretKeySpec(key, "AES"), ivSpec);
        return cipher.doFinal(plainText);
    }
}
