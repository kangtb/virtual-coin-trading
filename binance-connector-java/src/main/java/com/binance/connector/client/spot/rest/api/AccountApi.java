package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.GetOrderResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import com.binance.connector.common.sign.SignRequired;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AccountApi {


    @GET("/api/v3/account")
    @SignRequired
    Call<GetAccountResponse> getAccountCall(@Query("omitZeroBalances") Boolean omitZeroBalances,
                                        @Query("recvWindow") Long recvWindow,
                                        @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


    @GET("/api/v3/order")
    Call<GetOrderResponse> getOrderCall(@Query("symbol") String symbol,
                                    @Query("orderId") Long orderId,
                                    @Query("origClientOrderId") String origClientOrderId,
                                    @Query("recvWindow") Long recvWindow,
                                    @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);



}
