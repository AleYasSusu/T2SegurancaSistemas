package com.aleDev;

import java.math.BigInteger;

public class DiffieHellmanService {

    // Parâmetros do protocolo Diffie-Hellman
    private final BigInteger p; // Um número primo grande, usado como o módulo
    private final BigInteger g; // Um gerador (base), geralmente pequeno, usado como base exponencial

    /**
     * Construtor para inicializar os parâmetros do Diffie-Hellman.
     * @param p O número primo grande utilizado como módulo.
     * @param g O gerador utilizado como base.
     */
    public DiffieHellmanService(BigInteger p, BigInteger g) {
        this.p = p; // Armazena o número primo
        this.g = g; // Armazena o gerador
    }

    /**
     * Calcula a chave pública a partir de uma chave privada.
     * Fórmula: A = g^a mod p, onde:
     * - `g` é o gerador,
     * - `a` é a chave privada do usuário,
     * - `p` é o módulo (primo grande).
     *
     * @param privateKey A chave privada do usuário.
     * @return A chave pública correspondente.
     */
    public BigInteger calculatePublicKey(BigInteger privateKey) {
        // Eleva o gerador (g) à potência da chave privada (privateKey), módulo p
        return g.modPow(privateKey, p);
    }

    /**
     * Calcula o segredo compartilhado entre dois participantes.
     * Fórmula: S = B^a mod p, onde:
     * - `B` é a chave pública do outro participante,
     * - `a` é a chave privada do usuário,
     * - `p` é o módulo (primo grande).
     *
     * @param otherPublicKey A chave pública do outro participante.
     * @param privateKey A chave privada do usuário.
     * @return O segredo compartilhado (S).
     */
    public BigInteger calculateSharedSecret(BigInteger otherPublicKey, BigInteger privateKey) {
        // Eleva a chave pública do outro participante à potência da chave privada, módulo p
        return otherPublicKey.modPow(privateKey, p);
    }
}
