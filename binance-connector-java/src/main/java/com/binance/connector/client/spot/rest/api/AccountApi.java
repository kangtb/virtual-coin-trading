package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import com.binance.connector.client.spot.rest.api.model.resp.GetOrderResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.sign.SignRequired;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface AccountApi {


    @GET("/api/v3/account")
    @SignRequired
    Call<GetAccountResponse> getAccountCall(@Query("omitZeroBalances") Boolean omitZeroBalances,
                                            @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


    @GET("/api/v3/order")
    Call<GetOrderResponse> getOrderCall(@QueryMap Map<String, Object> map,
                                        @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


}
