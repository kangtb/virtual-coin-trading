package com.binance.connector.client;

import lombok.Data;

@Data
public class SignatureConfiguration {
    private String apiKey;
    private String secretKey;
    private String privateKey;
    private String privateKeyPass;
}
