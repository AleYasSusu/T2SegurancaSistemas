package com.aleDev;

import java.math.BigInteger;

public class DiffieHellmanService {
    private final BigInteger p; // Primo grande
    private final BigInteger g; // Base (gerador)

    public DiffieHellmanService(BigInteger p, BigInteger g) {
        this.p = p;
        this.g = g;
    }

    public BigInteger calculatePublicKey(BigInteger privateKey) {
        return g.modPow(privateKey, p);
    }

    public BigInteger calculateSharedSecret(BigInteger otherPublicKey, BigInteger privateKey) {
        return otherPublicKey.modPow(privateKey, p);
    }
}
