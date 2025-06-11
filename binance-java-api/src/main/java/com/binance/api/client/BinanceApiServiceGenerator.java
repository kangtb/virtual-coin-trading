package com.binance.api.client;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.config.BinanceApiConfig;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.security.AuthenticationInterceptor;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Generates a Binance API implementation based on @see
 * {@link BinanceApiService}.
 */
public class BinanceApiServiceGenerator implements ApiGenerator {

    private final OkHttpClient sharedClient;
    private final Converter.Factory converterFactory = JacksonConverterFactory.create();

    {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = new OkHttpClient.Builder().dispatcher(dispatcher).pingInterval(20, TimeUnit.SECONDS).build();
    }

    @Override
    public <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(BinanceApiConfig.getApiBaseUrl())
                .addConverterFactory(converterFactory);

        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BinanceApiConstants.LOG_LEVEL);
        OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).addInterceptor(logging).build();
        retrofitBuilder.client(adaptedClient);

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    @Override
    public <T> T createTestnetService(Class<T> serviceClass) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(BinanceApiConfig.getTestnetBaseUrl())
                .addConverterFactory(converterFactory);

        // `adaptedClient` will use its own interceptor, but share thread pool etc with
        // the 'parent' client
        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();

        OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
        retrofitBuilder.client(adaptedClient);

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }


}
