package com.binance.connector.client.spot.rest.api.model.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepthRequest {

    /**
     * 交易对 如BTCUSDT、ETHUSDT
     */
    private String symbol;

    /**
     * 获取的条数 最大5000，不传默认100
     */
    private Integer limit;

}
