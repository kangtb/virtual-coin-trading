package com.binance.connector.common.auth;

import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.security.SecretProvider;
import com.binance.connector.common.sign.SignRequired;
import com.binance.connector.common.sign.SignatureGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.bouncycastle.crypto.CryptoException;
import retrofit2.Invocation;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拦截接口签名
 */
@Slf4j
public class AuthenticationInterceptor implements Interceptor {

    private static volatile AuthenticationInterceptor instance;

    private final SignatureGenerator signatureGenerator;

    private AuthenticationInterceptor(SignatureGenerator signatureGenerator) {
        this.signatureGenerator = signatureGenerator;
    }

    public static Interceptor getInstance(SignatureGenerator signatureGenerator) {
        // 第一次检查
        if (instance == null) {
            synchronized (AuthenticationInterceptor.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new AuthenticationInterceptor(signatureGenerator);
                }
            }
        }
        return instance;
    }

    private final ConcurrentHashMap<String, Boolean> signatureRequiredCache = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("all")
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (isSignatureRequired(request)) {
            // 添加公共参数和签名
            request = joinSignatureCommonParams(request);
        }
        return chain.proceed(request);
    }

    /**
     * 封装添加公共参数和签名
     * @param request   请求
     * @return          新的请求
     * @throws IOException  io异常
     */
    private Request joinSignatureCommonParams(Request request) throws IOException {
        String apiKey = request.header(BinanceApiConstant.API_KEY_HEADER);
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IOException("Apikey is missing");
        }

        // 提取查询参数
        TreeMap<String, String> queryParams = new TreeMap<>();
        for (String name : request.url().queryParameterNames()) {
            String value = request.url().queryParameter(name);
            if (value != null) {
                queryParams.put(name, value);
            }
        }
        // 添加公共参数
        long timestamp = System.currentTimeMillis();
        queryParams.put("timestamp", String.valueOf(timestamp));
        if(queryParams.containsKey("recvWindow")){
            queryParams.put("recvWindow", String.valueOf(60000));
        }

        // 构造签名铭文
        StringBuilder payload = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (payload.length() > 0) {
                payload.append("&");
            }
            payload.append(entry.getKey()).append("=").append(entry.getValue());
        }
        // 生成签名
        String signature;
        try {
            String apiSecret = SecretProvider.getSecret(apiKey);
            if (apiSecret == null) {
                throw new IOException("API Secret not found for key: " + apiKey);
            }
            signature = signatureGenerator.signAsString(payload.toString(), apiSecret);
        } catch (CryptoException e) {
            log.error("Failed to generate signature for payload: {}", payload, e);
            throw new IOException("Signature generation failed", e);
        }

        // 更新 URL
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            urlBuilder.setQueryParameter(entry.getKey(), entry.getValue());
        }
        urlBuilder.addQueryParameter("signature", signature);
        // 构建新 Request
        return request.newBuilder()
                .url(urlBuilder.build())
                .build();
    }

    /**
     * 检查请求是否需要签名
     *
     * @param request   请求request
     * @return          是否需要签名
     */
    private boolean isSignatureRequired(Request request) {
        // 从 request.tag 获取 Retrofit 的 Invocation 对象
        Invocation invocation = request.tag(retrofit2.Invocation.class);
        if (invocation == null) {
            return false;
        }
        // 缓存签名检查结果，提高性能
        String methodKey = invocation.method().toString();
        return signatureRequiredCache.computeIfAbsent(methodKey, key ->
                invocation.method().isAnnotationPresent(SignRequired.class));
    }

}