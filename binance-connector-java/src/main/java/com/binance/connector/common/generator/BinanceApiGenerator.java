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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Generates a Binance API implementation based on all api interface
 *
 */
public class BinanceApiGenerator implements ApiGenerator {

    private static volatile BinanceApiGenerator instance;
    private final OkHttpClient sharedClient;
    private final Converter.Factory converterFactory = JacksonConverterFactory.create();

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

    {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        // 添加日志拦截器
        sharedClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(new AuthenticationInterceptor())
                .addInterceptor(new HttpLogInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .pingInterval(20, TimeUnit.SECONDS).build();
    }

    @Override
    public <T> T createApi(Class<T> apiClass, Boolean isTestnet) {
        if (apiClass == null) {
            throw new IllegalArgumentException("API class cannot be null");
        }

        String baseUrl = isTestnet ? BinanceApiConstant.TESTNET_URL : BinanceApiConstant.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .client(sharedClient)
                .build();
        return retrofit.create(apiClass);

    }
}
