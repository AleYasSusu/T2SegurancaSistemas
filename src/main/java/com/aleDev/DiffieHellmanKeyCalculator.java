package com.aleDev;

import java.math.BigInteger;


public class DiffieHellmanKeyCalculator {

    // Método para calcular a chave secreta compartilhada
    public static BigInteger calculateSharedSecret(BigInteger privateKey, BigInteger publicKeyFromProfessor) {



        // Calcular o segredo compartilhado
        BigInteger sharedSecret = publicKeyFromProfessor.modPow(privateKey, KeyParameters.getPrimeP());

        // Log da chave secreta compartilhada
        System.out.println("Chave secreta compartilhada (shared secret) calculada: " + sharedSecret.toString(16));
        return sharedSecret;
    }
}
