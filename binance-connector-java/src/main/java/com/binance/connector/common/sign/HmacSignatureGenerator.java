package com.binance.connector.common.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.util.encoders.Hex;

public class HmacSignatureGenerator implements SignatureGenerator {
    private static final String HMAC_SHA256 = "HmacSHA256";


    @Override
    public byte[] sign(String input, String apiSecret) throws CryptoException {
        return sign(input.getBytes(), apiSecret);
    }

    @Override
    public byte[] sign(byte[] input, String apiSecret) throws CryptoException {
        byte[] hmacSha256;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(), HMAC_SHA256);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmacSha256;
    }

    @Override
    public String signAsString(String input, String apiSecret) throws CryptoException {
        return signAsString(input.getBytes(), apiSecret);
    }

    @Override
    public String signAsString(byte[] input, String apiSecret) throws CryptoException {
        return Hex.toHexString(sign(input, apiSecret));
    }
}
