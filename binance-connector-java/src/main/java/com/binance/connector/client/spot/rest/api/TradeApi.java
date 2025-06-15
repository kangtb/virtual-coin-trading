package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.CancelOrderResponse;
import com.binance.connector.client.spot.rest.api.model.resp.NewOrderResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.sign.SignRequired;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface TradeApi {

    /**
     * 下委托单
     *
     * @param map    参数
     * @param apikey apikey
     * @return 委托详情
     */
    @POST("/api/v3/order")
    @SignRequired
    Call<NewOrderResponse> newOrderCall(@QueryMap Map<String, Object> map,
                                    @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);

    /**
     * 撤销委托单
     *
     * @param map    参数
     * @param apikey apikey
     * @return 撤销详情
     */
    @DELETE("/api/v3/order")
    Call<CancelOrderResponse> cancelOrderCall(@QueryMap Map<String, Object> map,
                                          @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


}
