package com.binance.connector.client.spot.rest.api.model.req;

import com.binance.connector.common.enums.CancelRestrictions;
import lombok.Data;

@Data
public class CancelOrderRequest {

    /**
     * 交易对
     */
    private String symbol;

    private Long orderId;

    private String origClientOrderId;

    private String newClientOrderId;

    private CancelRestrictions cancelRestrictions;

}
