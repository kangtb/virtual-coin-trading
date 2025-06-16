package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.spot.rest.api.model.resp.DepthResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface MarketDataApi {

    @GET("/api/v3/depth")
    Call<DepthResponse> getDepthCall(@QueryMap Map<String, Object> map);

}
