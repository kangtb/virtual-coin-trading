package com.binance.connector.client.derivatives_trading_usds_futures.rest.api;

import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.FuturesAccountBalanceV3Response;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.UserCommissionRateResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.sign.SignRequired;
import com.binance.connector.common.sign.SignatureType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AccountApi {

    @GET("/fapi/v3/balance")
    @SignRequired(SignatureType.Hmac)
    Call<FuturesAccountBalanceV3Response> getBalanceCall(@Header(BinanceApiConstant.API_KEY_HEADER) String apikey);

    @GET("/fapi/v1/commissionRate")
    @SignRequired(SignatureType.Hmac)
    Call<UserCommissionRateResponse> getCommissionRateCall(@Query("symbol") String symbol,
                                                        @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


}
