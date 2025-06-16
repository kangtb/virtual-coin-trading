package com.binance.connector.common.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecretProvider {

    private static final ConcurrentHashMap<String, String> API_SECRET_CACHE = new ConcurrentHashMap<>();

    static {
        addSecret("WKP7oOXkG2DCzmz57GtzUbXoSS1L3nVNxtdwvQDliLhq6zzS8O18yTNBdhXBQ30Z", "MC4CAQAwBQYDK2VwBCIEIILIn/Ys4x85c1vAUgFZNa6OXKKdkzLoBt8gw3k2yk6E");
        addSecret("ABrKGus7iKl2EM5SV8QtfTACRafKNErrNPmPVrAHmipTH0YKvMuNydGojZTOR3UD", "MC4CAQAwBQYDK2VwBCIEIB5RkLPPaY25NPHceQnlX8RjK6xlt6yHZo5EpbEPAYc1");
        addSecret("c5c11db85aa7da72ca8a5a83bf2512a89daa232a271bfaf53d72ca4c66e24550", "60ff089abca643647ac02e4bbb51ef71099670b59d80f9a4bdd6b076cfa572af");
    }

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
