package com.binance.connector.client.derivatives_trading_usds_futures.rest.api;

import com.binance.connector.client.ApiClient;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.req.ModifyIsolatedPositionMarginRequest;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.req.NewOrderRequest;
import com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp.*;
import com.binance.connector.common.generator.BinanceApiGenerator;
import com.binance.connector.common.utils.JacksonUtils;
import retrofit2.Call;

/**
 * U本位合约客户端
 */
public class UsdsFuturesApiRestClient extends ApiClient {

    AccountApi accountApi;

    MarketDataApi marketDataApi;

    TradeApi tradeApi;

    public UsdsFuturesApiRestClient(String apiKey, Boolean isTestnet) {
        super(apiKey, isTestnet);
        // 生成 api
        accountApi = BinanceApiGenerator.getInstance().createApi(AccountApi.class, isTestnet);
        marketDataApi = BinanceApiGenerator.getInstance().createApi(MarketDataApi.class, isTestnet);
        tradeApi = BinanceApiGenerator.getInstance().createApi(TradeApi.class, isTestnet);
    }

    /**
     * 获取账户余额
     *
     * @return 账户余额信息
     */
    public FuturesAccountBalanceV3Response getBalance() {
        Call<FuturesAccountBalanceV3Response> getBalanceCall = accountApi.getBalanceCall(getApiKey());
        return execute(getBalanceCall);
    }

    /**
     * 获取合约价格和资金费率
     *
     * @param symbol 交易对
     * @return 价格和资金费率
     */
    public MarkPriceResponse getPremiumIndex(String symbol) {
        Call<MarkPriceResponse> premiumIndexCall = marketDataApi.getPremiumIndexCall(symbol, getApiKey());
        return execute(premiumIndexCall);
    }

    /**
     * 调整逐仓合约保证金
     *
     * @param request 参数
     * @return 结果
     */
    public ModifyIsolatedPositionMarginResponse modifyIsolatedPositionMargin(ModifyIsolatedPositionMarginRequest request) {
        Call<ModifyIsolatedPositionMarginResponse> modifyIsolatedPositionMarginCall = tradeApi.modifyIsolatedPositionMarginCall(JacksonUtils.convertToMap(request), getApiKey());
        return execute(modifyIsolatedPositionMarginCall);
    }

    /**
     * 合约下单
     *
     * @param request 下单参数
     * @return 下单结果
     */
    public NewOrderResponse newOrder(NewOrderRequest request) {
        Call<NewOrderResponse> newOrderCall = tradeApi.newOrderCall(JacksonUtils.convertToMap(request), getApiKey());
        return execute(newOrderCall);
    }

    /**
     * 获取用户手续费
     *
     * @param symbol 交易对
     * @return      手续费信息
     */
    public UserCommissionRateResponse getCommissionRate(String symbol) {
        Call<UserCommissionRateResponse> userCommissionRateCall = accountApi.getCommissionRateCall(symbol, getApiKey());
        return execute(userCommissionRateCall);
    }



}

