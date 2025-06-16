package com.binance.connector.client.derivatives_trading_usds_futures.rest.api;

import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.FuturesAccountBalanceV3Response;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.MarkPriceResponse;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.UserCommissionRateResponse;
import org.junit.Test;

public class UsdsFuturesApiRestClientTest {

    UsdsFuturesApiRestClient usdsFuturesApiRestClient = new UsdsFuturesApiRestClient("c5c11db85aa7da72ca8a5a83bf2512a89daa232a271bfaf53d72ca4c66e24550", true);

    @Test
    public void getAccountTest() {
        FuturesAccountBalanceV3Response balance = usdsFuturesApiRestClient.getBalance();
        System.out.println(balance);
    }

    @Test
    public void getPremiumIndexTest() {
        MarkPriceResponse markPriceResponse = usdsFuturesApiRestClient.getPremiumIndex("BTCUSDT");
        System.out.println(markPriceResponse);
    }

    @Test
    public void getCommissionRateTest() {
        UserCommissionRateResponse userCommissionRateResponse = usdsFuturesApiRestClient.getCommissionRate("BTCUSDT");
        System.out.println(userCommissionRateResponse);
    }

}
