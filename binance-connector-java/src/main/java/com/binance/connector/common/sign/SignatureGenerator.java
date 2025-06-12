package com.binance.connector.common.sign;

import org.bouncycastle.crypto.CryptoException;

public interface SignatureGenerator {

    byte[] sign(String input, String apiSecret) throws CryptoException;

    byte[] sign(byte[] input, String apiSecret) throws CryptoException;

    String signAsString(String input, String apiSecret) throws CryptoException;

    String signAsString(byte[] input, String apiSecret) throws CryptoException;
}
