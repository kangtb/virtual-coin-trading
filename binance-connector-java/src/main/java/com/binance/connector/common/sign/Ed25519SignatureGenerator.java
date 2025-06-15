package com.binance.connector.common.sign;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.util.Base64;

@Slf4j
public class Ed25519SignatureGenerator implements SignatureGenerator {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

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
        log.info("plaintext:[{}]", input);
        return sign(input.getBytes(), apiSecret);
    }

    @Override
    public byte[] sign(byte[] input, String apiSecret) throws CryptoException {
        try {
            // Base64解码私钥
            byte[] privateKeyBytes = Base64.getDecoder().decode(apiSecret);
            // 解析PKCS8格式私钥
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            // 提取ED25519私钥参数
            AsymmetricKeyParameter privateKey = PrivateKeyFactory.createKey(privateKeyInfo);

            // 创建Ed25519签名器
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



    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 初始化 Ed25519 密钥对生成器
        Ed25519KeyPairGenerator keyGen = new Ed25519KeyPairGenerator();
        keyGen.init(new KeyGenerationParameters(new SecureRandom(), 256)); // Ed25519 使用 256 位

        // 生成密钥对
        AsymmetricCipherKeyPair keyPair = keyGen.generateKeyPair();
        Ed25519PrivateKeyParameters privateKey = (Ed25519PrivateKeyParameters) keyPair.getPrivate();
        Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) keyPair.getPublic();

        // 转换为 Base64
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("privateKey:" + privateKeyBase64);
        System.out.println(toPemPublicKey(publicKeyBase64));
    }


    public static String toPemPublicKey(String publicKeyBase64) {
        String header = "-----BEGIN PUBLIC KEY-----\n";
        String footer = "\n-----END PUBLIC KEY-----";
        String rawKey = publicKeyBase64.replaceAll("\n", "");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < rawKey.length(); i += 64) {
            formatted.append(rawKey, i, Math.min(i + 64, rawKey.length())).append("\n");
        }
        return header + formatted + footer;
    }
}