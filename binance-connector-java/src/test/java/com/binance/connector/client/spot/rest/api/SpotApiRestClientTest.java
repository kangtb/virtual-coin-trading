package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.req.DepthRequest;
import com.binance.connector.client.spot.rest.api.model.resp.DepthResponse;
import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import com.binance.connector.client.spot.rest.api.model.resp.NewOrderResponse;
import com.binance.connector.client.spot.rest.api.model.req.NewOrderRequest;
import com.binance.connector.common.enums.Side;
import org.junit.Test;

public class SpotApiRestClientTest {

    SpotApiRestClient spotApiRestClient = new SpotApiRestClient("ABrKGus7iKl2EM5SV8QtfTACRafKNErrNPmPVrAHmipTH0YKvMuNydGojZTOR3UD", true);

    @Test
    public void getAccountTest() {
        GetAccountResponse account = spotApiRestClient.getAccount(true);
        System.out.println(account.toString());
    }

    @Test
    public void newOrderTest() {
        NewOrderRequest newOrderRequest = NewOrderRequest.builder().newClientOrderId("234555")
                .side(Side.BUY).build();
        NewOrderResponse newOrderResponse = spotApiRestClient.newOrder(newOrderRequest);
        System.out.println(newOrderResponse.toString());
    }

    @Test
    public void getDepthTest(){
        DepthRequest depthRequest = DepthRequest.builder().symbol("BTCUSDT")
                .limit(100).build();
        DepthResponse depthCall = spotApiRestClient.getDepth(depthRequest);
        System.out.println(depthCall);
    }


}
