package com.binance.connector.client.derivatives_trading_usds_futures.rest.api;

import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.MarkPriceResponse;
import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.sign.SignRequired;
import com.binance.connector.common.sign.SignatureType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MarketDataApi {

    @GET("/fapi/v1/premiumIndex")
    @SignRequired(SignatureType.Hmac)
    Call<MarkPriceResponse> getPremiumIndexCall(@Query("symbol") String symbol,
                                            @Header(BinanceApiConstant.API_KEY_HEADER) String apikey);


}
