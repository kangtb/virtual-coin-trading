package com.binance.connector.client.derivatives_trading_usds_futures.websocket;

import com.binance.connector.client.AbstractStreamHandler;

public class MarketPriceStreamHandler extends AbstractStreamHandler<MarketPriceEvent> {

    @Override
    public void onResponse(MarketPriceEvent response) {
        System.out.println(response);
    }
}
