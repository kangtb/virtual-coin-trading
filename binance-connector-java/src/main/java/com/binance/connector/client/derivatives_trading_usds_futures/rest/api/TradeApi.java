package com.binance.connector.client.derivatives_trading_usds_futures.rest.api;

import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.ModifyIsolatedPositionMarginResponse;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.NewOrderResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.sign.SignRequired;
import com.binance.connector.common.sign.SignatureType;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface TradeApi {

    @SignRequired(SignatureType.Hmac)
    @POST("/fapi/v1/positionMargin")
    Call<ModifyIsolatedPositionMarginResponse> modifyIsolatedPositionMarginCall(@QueryMap Map<String, Object> map,
                                                                            @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);

    @SignRequired(SignatureType.Hmac)
    @POST("/fapi/v1/order")
    Call<NewOrderResponse> newOrderCall(@QueryMap Map<String, Object> map,
                                        @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);
}
