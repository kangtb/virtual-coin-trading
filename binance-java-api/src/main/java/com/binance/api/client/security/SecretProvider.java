package com.binance.api.client.security;

public interface SecretProvider {

    /**
     * 获取密钥
     *
     * @return 密钥
     */
    String getSecret();

}
