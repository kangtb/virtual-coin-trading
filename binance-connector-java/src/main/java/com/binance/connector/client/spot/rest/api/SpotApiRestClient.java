package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.ApiClient;
import com.binance.connector.client.spot.rest.api.model.req.CancelOrderRequest;
import com.binance.connector.client.spot.rest.api.model.req.DepthRequest;
import com.binance.connector.client.spot.rest.api.model.resp.CancelOrderResponse;
import com.binance.connector.client.spot.rest.api.model.resp.DepthResponse;
import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import com.binance.connector.client.spot.rest.api.model.resp.NewOrderResponse;
import com.binance.connector.client.spot.rest.api.model.req.NewOrderRequest;
import com.binance.connector.common.generator.BinanceApiGenerator;
import com.binance.connector.common.utils.JacksonUtils;
import retrofit2.Call;

/**
 * 现货 api
 */
public class SpotApiRestClient extends ApiClient {

    /**
     * 账户api
     */
    AccountApi accountApi;

    /**
     * 市场api
     */
    MarketApi marketApi;

    /**
     * 交易api
     */
    TradeApi tradeApi;


    public SpotApiRestClient(String apiKey, Boolean isTestnet) {
        super(apiKey, isTestnet);
        // 生成 api
        accountApi = BinanceApiGenerator.getInstance().createApi(AccountApi.class, isTestnet);
        marketApi = BinanceApiGenerator.getInstance().createApi(MarketApi.class, isTestnet);
        tradeApi = BinanceApiGenerator.getInstance().createApi(TradeApi.class, isTestnet);
    }

    /**
     * 获取账户信息
     *
     * @param omitZeroBalances 小额余额是否显示
     * @param recvWindow       接收的时间窗口
     * @return 账户信息
     */
    GetAccountResponse getAccountCall(Boolean omitZeroBalances, Long recvWindow) {
        recvWindow = recvWindow == null ? 10000 : recvWindow;
        Call<GetAccountResponse> accountCall = accountApi.getAccountCall(omitZeroBalances, recvWindow, getApiKey());
        return execute(accountCall);
    }

    /**
     * 下委托单
     *
     * @param newOrderRequest 订单参数
     * @return resp
     */
    NewOrderResponse newOrderCall(NewOrderRequest newOrderRequest) {
        Call<NewOrderResponse> newOrderResponseCall = tradeApi.newOrderCall(JacksonUtils.convertToMap(newOrderRequest), getApiKey());
        return execute(newOrderResponseCall);
    }

    /**
     * 撤销委托单
     *
     * @param cancelOrderRequest 撤单参数
     * @return 撤单信息
     */
    CancelOrderResponse cancelOrderCall(CancelOrderRequest cancelOrderRequest) {
        Call<CancelOrderResponse> cancelOrderResponseCall = tradeApi.cancelOrderCall(JacksonUtils.convertToMap(cancelOrderRequest), getApiKey());
        return execute(cancelOrderResponseCall);
    }

    /**
     * 获取交易对的深度
     *
     * @param depthRequest 请求参数
     * @return 深度信息
     */
    DepthResponse getDepth(DepthRequest depthRequest) {
        Call<DepthResponse> depthCall = marketApi.getDepthCall(JacksonUtils.convertToMap(depthRequest));
        return execute(depthCall);
    }

}
