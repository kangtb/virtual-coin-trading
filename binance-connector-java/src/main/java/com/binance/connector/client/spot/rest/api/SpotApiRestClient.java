package com.binance.connector.client.spot.rest.api;

import com.binance.connector.client.ApiClient;
import com.binance.connector.client.spot.rest.api.model.resp.GetAccountResponse;
import com.binance.connector.common.generator.BinanceApiGenerator;
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
     * @param omitZeroBalances  小额余额是否显示
     * @param recvWindow        接收的时间窗口
     * @return                  账户信息
     */
    GetAccountResponse getAccount(Boolean omitZeroBalances, Long recvWindow) {
        recvWindow = recvWindow == null ? 10000 : recvWindow;
        Call<GetAccountResponse> account = accountApi.getAccount(omitZeroBalances, recvWindow, getApiKey());
        return execute(account);
    }






}
