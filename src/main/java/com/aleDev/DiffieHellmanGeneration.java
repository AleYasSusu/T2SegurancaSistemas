package com.aleDev;

import java.math.BigInteger;


public class DiffieHellmanGeneration {

    public static void main(String[] args) {
        // Valores fornecidos (p e g em hexadecimal)
        String pHex = """
                B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B61
                6073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BF
                ACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0
                A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371
                """;
        String gHex = """
                A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406CFF14266D31
                266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4
                D7FBD7D3B9A92EE1909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28A
                D662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B2E5
                """;

        // Convertendo os valores fornecidos para BigInteger
        BigInteger p = new BigInteger(pHex.replace("\n", "").replace(" ", ""), 16);
        BigInteger g = new BigInteger(gHex.replace("\n", "").replace(" ", ""), 16);

        // Valor fixo de 'a' fornecido
        BigInteger a = new BigInteger("1179384367866944962701170917197320572924798963556873971958304443979154591845677174190613581118814432035661344670984319756742925295640982913397441690376024917967445622577274761166639357060674284056878097937114193036961094567556356894279455858784040014544473324470018056719570570534418388285723752403200707717");

        // Calcular A = g^a mod p
        BigInteger A = g.modPow(a, p);

        // Exibir os valores
        System.out.println("Valor de 'a' (secreto): " + a);
        System.out.println("Valor de 'A' (em hexadecimal): " + A.toString(16).toUpperCase());
    }
}