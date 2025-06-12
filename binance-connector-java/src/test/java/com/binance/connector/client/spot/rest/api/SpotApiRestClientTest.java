package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import org.junit.Test;

public class SpotApiRestClientTest {

    @Test
    public void getAccountTest() {

        SpotApiRestClient spotApiRestClient = new SpotApiRestClient("123355", true);
        GetAccountResponse account = spotApiRestClient.getAccount(true, null);
        System.out.println(account.toString());
    }

}
