package com.binance.connector.common.generator;

import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.auth.AuthenticationInterceptor;
import com.binance.connector.common.log.HttpLogInterceptor;
import com.binance.connector.common.sign.Ed25519SignatureGenerator;
import com.binance.connector.common.sign.SignatureGenerator;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://testnet.binance.vision/ws").build();
        final WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                t.printStackTrace();
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println(text);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {

            }
        });

    }
}
