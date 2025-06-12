package com.binance.connector.client.spot.rest.api.model.resp;

import lombok.Data;

@Data
public class GetAccountResponseCommissionRates {
    public static final String SERIALIZED_NAME_MAKER = "maker";

    private String maker;

    private String taker;

    private String buyer;

    private String seller;

}
