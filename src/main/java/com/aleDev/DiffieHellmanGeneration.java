package com.aleDev;


import java.math.BigInteger;

    public class DiffieHellmanGeneration {

        public static void main(String[] args) {
            // Valores fornecidos pelo professor
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

            // Converte p e g para BigInteger
            BigInteger p = new BigInteger(pHex.replace("\n", "").replace(" ", ""), 16);
            BigInteger g = new BigInteger(gHex.replace("\n", "").replace(" ", ""), 16);
            BigInteger B = new BigInteger("00AF500EC6CBAF03F5C539855D13F84831ECFD0CD855EF79BBBFE45BD1A62111A1E98A7D49A47412F7E564A45239B12FEDC5C366570325C0E5E1F4C64E1FC736EA8C89F725A96AF9F859DDF96CC2582670680F849A5305B598DF6400381FA0D15A9B59DD29477BDE659948223C9A52DE95EF8895187CD966239E2F28D3EE058C6906358FDA653E7883925F21F6B5D457B0FD004D052DF4222D44B80E09229F6B287BD48CE22647F0FBFFE96CDAA5A6A21C5989309E4B15A54DE030282F170307F3BD070F7CC1421028AA8E85EC80806071BB29C4BEF2E04A713E289FC3DBFBF7188874D2BD0916643185D6D500C66BEAA420D571A21093CED1D8D60A88", 16);
            // Valor fixo de 'a' fornecido
            BigInteger a = new BigInteger("450077921342737676890415149339");

            // Exibir os valores convertidos para verificação
            System.out.println("p (hexadecimal): " + p.toString(16).toUpperCase());
            System.out.println("g (hexadecimal): " + g.toString(16).toUpperCase());
            System.out.println("a (decimal): " + a);
            System.out.println("Tamanho de 'p' (bits): " + p.bitLength());
            System.out.println("Tamanho de 'g' (bits): " + g.bitLength());
            System.out.println("Tamanho de 'a' (bits): " + a.bitLength());

            System.out.println("Tamanho de 'B' (bits): " + B.bitLength());

            // Cálculo de A = g^a mod p
            BigInteger A = g.modPow(a, p);
            System.out.println("Chave pública (A): " + A.toString(16).toUpperCase());
            System.out.println("Tamanho de 'A' (bits): " + A.bitLength());
        }
    }
