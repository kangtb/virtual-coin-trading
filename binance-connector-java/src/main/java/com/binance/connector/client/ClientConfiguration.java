package com.binance.connector.client;

import lombok.Data;
import okhttp3.Authenticator;

@Data
public class ClientConfiguration {

    public static final int DEFAULT_RECONNECT_INTERVAL_TIME = (23 * 60 * 60 * 1000);
    public static final int DEFAULT_POOL_SIZE = 10;
    public static final int DEFAULT_RECONNECT_BATCH_SIZE = 2;
    public static final int DEFAULT_RETRIES = 3;
    public static final int DEFAULT_BACKOFF = 200;
    public static final int DEFAULT_CONNECT_TIMEOUT = 1000;
    public static final int DEFAULT_READ_TIMEOUT = 5000;

    /** Base URL */
    protected String url = "https://api.binance.com";

    /** Proxy Auth configuration */
    protected Authenticator proxyAuthenticator;

    private SignatureConfiguration signatureConfiguration;


}
