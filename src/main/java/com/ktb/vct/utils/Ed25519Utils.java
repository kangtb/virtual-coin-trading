package com.ktb.vct.utils;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class BinanceSignUtils {

    /**
     * 加密
     * @param payload 消息
     * @param privateKey  密钥
     * @return        密文
     */
    public static String signData(String payload, PrivateKey privateKey)  {
        Signature signer = null;
        try {
            signer = Signature.getInstance("Ed25519", "BC");
            signer.initSign(privateKey);
            signer.update(payload.getBytes());
            byte[] signature = signer.sign();
            Base64.getEncoder().encodeToString(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
