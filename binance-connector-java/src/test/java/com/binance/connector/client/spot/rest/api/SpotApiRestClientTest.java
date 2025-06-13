package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import org.junit.Test;

public class SpotApiRestClientTest {

    @Test
    public void getAccountTest() {

        SpotApiRestClient spotApiRestClient = new SpotApiRestClient("ABrKGus7iKl2EM5SV8QtfTACRafKNErrNPmPVrAHmipTH0YKvMuNydGojZTOR3UD", true);
        GetAccountResponse account = spotApiRestClient.getAccount(true, null);
        System.out.println(account.toString());
    }

}
