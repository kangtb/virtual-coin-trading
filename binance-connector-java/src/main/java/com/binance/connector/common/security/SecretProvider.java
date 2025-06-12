package com.binance.connector.common.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecretProvider {

    private static final ConcurrentHashMap<String, String> API_SECRET_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取密钥
     *
     * @return 密钥
     */
    public static String getSecret(String apikey) {
        return API_SECRET_CACHE.get(apikey);
    }

    public static void cacheSecret(Map<String,String> secretMap) {
        API_SECRET_CACHE.putAll(secretMap);
    }

    public static void addSecret(String apikey, String secret) {
        API_SECRET_CACHE.put(apikey, secret);
    }


}
