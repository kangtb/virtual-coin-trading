package com.binance.api.client.security;

public interface Signer {

    /**
     * 签名
     * @param message   消息
     * @param secret    密钥
     * @return          签名
     */
    String getSignature(String message, String secret);
}
