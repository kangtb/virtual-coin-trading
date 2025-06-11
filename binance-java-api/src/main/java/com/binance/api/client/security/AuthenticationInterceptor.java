package com.binance.api.client.security;

import com.binance.api.client.constant.BinanceApiConstants;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 拦截接口签名
 */
public class AuthenticationInterceptor implements Interceptor {


    public AuthenticationInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder newRequestBuilder = original.newBuilder();

        boolean isApiKeyRequired = original.header(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY) != null;
        boolean isSignatureRequired = original.header(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED) != null;
        newRequestBuilder.removeHeader(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_APIKEY)
            .removeHeader(BinanceApiConstants.ENDPOINT_SECURITY_TYPE_SIGNED);

        final String apiKey = original.header("api");
        final String secret = original.header("secret");

        newRequestBuilder.removeHeader("api").removeHeader("secret");

        // Endpoint requires sending a valid API-KEY
        if (isApiKeyRequired || isSignatureRequired) {
            newRequestBuilder.addHeader(BinanceApiConstants.API_KEY_HEADER, apiKey);
        }

        // Endpoint requires signing the payload
        if (isSignatureRequired) {
            String payload = original.url().query();
            if (!StringUtils.isEmpty(payload)) {
                String signature = HmacSHA256Signer.sign(payload, secret);
                HttpUrl signedUrl = original.url().newBuilder().addQueryParameter("signature", signature).build();
                newRequestBuilder.url(signedUrl);
            }
        }
        // Build new request after adding the necessary authentication information
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }

}