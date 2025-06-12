package com.binance.connector.common.auth;

import com.binance.connector.common.constant.BinanceApiConstant;
import com.binance.connector.common.security.SecretProvider;
import com.binance.connector.common.sign.SignRequired;
import com.binance.connector.common.sign.SignatureGenerator;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.bouncycastle.crypto.CryptoException;
import retrofit2.Invocation;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 拦截接口签名
 */
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
        Request.Builder newRequestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();

        // 添加公共参数时间戳
        String payload = request.url().query();
        if(payload != null && !payload.isEmpty()) {
            urlBuilder.addQueryParameter("timestamp", System.currentTimeMillis() + "");
        }
        // 添加签名
        if (isSignatureRequired(request)) {
            if (payload != null && !payload.isEmpty()) {
                String apikey = request.header(BinanceApiConstant.API_KEY_HEADER);
                String signature = null;
                try {
                    signature = signatureGenerator.signAsString(payload, SecretProvider.getSecret(apikey));
                } catch (CryptoException e) {
                    throw new RuntimeException(e);
                }
                HttpUrl signedUrl = urlBuilder.addQueryParameter("signature", signature).build();
                newRequestBuilder.url(signedUrl);
            }
        }
        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
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