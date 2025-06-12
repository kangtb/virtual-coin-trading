package com.binance.connector.common.generator;

import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.auth.AuthenticationInterceptor;
import com.binance.connector.common.log.HttpLogInterceptor;
import com.binance.connector.common.sign.Ed25519SignatureGenerator;
import com.binance.connector.common.sign.SignatureGenerator;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Generates a Binance API implementation based on all api interface
 *
 */
public class BinanceApiGenerator implements ApiGenerator {

    private static volatile BinanceApiGenerator instance;

    private BinanceApiGenerator() {

    }

    public static ApiGenerator getInstance() {
        // 第一次检查
        if (instance == null) {
            synchronized (BinanceApiGenerator.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new BinanceApiGenerator();
                }
            }
        }
        return instance;
    }

    private final OkHttpClient sharedClient;
    private final Converter.Factory converterFactory = JacksonConverterFactory.create();

    {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = new OkHttpClient.Builder().dispatcher(dispatcher).pingInterval(20, TimeUnit.SECONDS).build();
    }

    @Override
    public <T> T createApi(Class<T> apiClass, Boolean isTestnet) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(isTestnet ? BinanceApiConstant.TESTNET_URL :BinanceApiConstant.BASE_URL)
                .addConverterFactory(converterFactory);
        // 采用 ed25519签名
        SignatureGenerator signatureGenerator = Ed25519SignatureGenerator.getInstance();
        Interceptor authInterceptor = AuthenticationInterceptor.getInstance(signatureGenerator);
        // 添加日志打印
        HttpLogInterceptor httpLogInterceptor = new HttpLogInterceptor();
        // 添加日志拦截器
        OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(authInterceptor).addInterceptor(httpLogInterceptor).build();
        retrofitBuilder.client(adaptedClient);
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(apiClass);
    }
}
