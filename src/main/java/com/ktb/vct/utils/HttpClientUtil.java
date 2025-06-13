package com.ktb.vct.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * http工具类
 *
 * @author zhangzy
 * @version 1.0.0
 * @date 2023/06/23
 */
@Slf4j
public class HttpClientUtil {
    /**
     * 链路请求头
     */
    public static String TRACE_ID_HEADER = "nxcloud-trace-id";

    public static OkHttpClient okHttpClient;

    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HostnameVerifier hostnameVerifier = (hostname, session) -> true;

            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(hostnameVerifier)
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    // 默认连接池 可用连接：200，存活：5分钟
                    .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
                    // 统一记录调用日志
                    .addInterceptor(new LogInterceptor())
                    .build();
        } catch (Exception e) {
            log.error("OkHttp client init error", e);
        }
    }


    /**
     * 用于响应缓慢的http请求
     */
    public static OkHttpClient slowOkHttpClient;

    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            HostnameVerifier hostnameVerifier = (hostname, session) -> true;

            slowOkHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(hostnameVerifier)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    // 默认连接池 可用连接：200，存活：5分钟
                    .connectionPool(new ConnectionPool(200, 5, TimeUnit.MINUTES))
                    // 统一记录调用日志
                    .addInterceptor(new LogInterceptor())
                    .build();
        } catch (Exception e) {
            log.error("OkHttp client init error", e);
        }
    }

    /**
     * http日志拦截器，统一输出日志
     * 请求路径，http响应码，耗时
     */
    private static class LogInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            // 包装request后，获取请求头
            // 异步一定要包装，同步请求自然会有；异步请求拦截器执行在前，无traceId
//            String traceIdHeader = request.header(TRACE_ID_HEADER);
//            if (traceIdHeader != null && !traceIdHeader.isEmpty()) {
//                TraceIdUtil.setLogTraceId(traceIdHeader);
//            }
            long start = System.currentTimeMillis();
            Response response = chain.proceed(request);
            long time = System.currentTimeMillis() - start;
            log.info("OkHttp invoke url[{}], header:[{}] status[{}], time[{}]ms", request.url(), request.headers().toString(), response.code(), time);
            return response;
        }
    }
}
