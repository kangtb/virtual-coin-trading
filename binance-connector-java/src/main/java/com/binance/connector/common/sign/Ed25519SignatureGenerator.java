package com.binance.connector.common.sign;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

import java.util.Base64;

public class Ed25519SignatureGenerator implements SignatureGenerator {

    private static volatile Ed25519SignatureGenerator instance;

    private Ed25519SignatureGenerator() {

    }

    public static SignatureGenerator getInstance() {
        // 第一次检查
        if (instance == null) {
            synchronized (Ed25519SignatureGenerator.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new Ed25519SignatureGenerator();
                }
            }
        }
        return instance;
    }

    @Override
    public byte[] sign(String input, String apiSecret) throws CryptoException {
        return sign(input.getBytes(), apiSecret);
    }

    @Override
    public byte[] sign(byte[] input, String apiSecret) throws CryptoException {
        try {
            // 解码 API Secret（假设为 Base64 编码的 Ed25519 私钥）
            byte[] privateKeyBytes = Base64.getDecoder().decode(apiSecret);
            Ed25519PrivateKeyParameters privateKey = new Ed25519PrivateKeyParameters(privateKeyBytes, 0);

            // 创建 Ed25519 签名器
            Signer signer = new Ed25519Signer();
            signer.init(true, privateKey);
            signer.update(input, 0, input.length);

            // 生成签名
            return signer.generateSignature();
        } catch (Exception e) {
            throw new CryptoException("Failed to generate Ed25519 signature", e);
        }
    }

    @Override
    public String signAsString(String input, String apiSecret) throws CryptoException {
        byte[] signature = sign(input, apiSecret);
        return Base64.getEncoder().encodeToString(signature);
    }

    @Override
    public String signAsString(byte[] input, String apiSecret) throws CryptoException {
        byte[] signature = sign(input, apiSecret);
        return Base64.getEncoder().encodeToString(signature);
    }
}