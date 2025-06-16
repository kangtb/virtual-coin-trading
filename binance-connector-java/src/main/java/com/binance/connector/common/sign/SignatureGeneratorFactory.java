package com.binance.connector.common.sign;

import java.util.HashMap;
import java.util.Map;

public class SignatureGeneratorFactory {

    private final static Map<SignatureType, SignatureGenerator> signatureGeneratorMap = new HashMap<>();

    static {
        signatureGeneratorMap.put(SignatureType.Hmac,HmacSignatureGenerator.getInstance());
        signatureGeneratorMap.put(SignatureType.Ed25519, Ed25519SignatureGenerator.getInstance());
    }

    public static SignatureGenerator getByType(SignatureType signatureType) {
        return signatureGeneratorMap.get(signatureType);
    }

}
