package com.binance.connector.common.constant;

public class BinanceApiConstant {

    public static final String BASE_URL = "https://api.binance.com/";

    public static final String SPOT_TESTNET_URL = "https://testnet.binance.vision/api/";

    public static final String TESTNET_URL = "https://testnet.binancefuture.com/";

    /**
     * HTTP Header to be used for API-KEY authentication.
     */
    public static final String API_KEY_HEADER = "X-MBX-APIKEY";

    /**
     * Decorator to indicate that an endpoint requires a signature.
     */
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED = "SIGNED";

    /**
     * Default receiving window.
     */
    public static final long DEFAULT_RECEIVING_WINDOW = 60_000L;

    /**
     * Default margin receiving window.
     */
    public static final long DEFAULT_MARGIN_RECEIVING_WINDOW = 5_000L;


}
