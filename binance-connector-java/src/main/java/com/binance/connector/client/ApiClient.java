package com.binance.connector.client;

import com.binance.connector.common.exception.BinanceApiError;
import com.binance.connector.common.exception.BinanceApiException;
import lombok.Data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;

@Data
public abstract class ApiClient {

    /**
     * apIkey
     */
    String apiKey;

    /**
     * 是否是测试网
     */
    Boolean isTestnet;

    static final Converter.Factory converterFactory = JacksonConverterFactory.create();

    public ApiClient(String apiKey, Boolean isTestnet) {
        this.apiKey = apiKey;
        this.isTestnet = isTestnet;
    }

    public <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                BinanceApiError apiError = getBinanceApiError(response);
                throw new BinanceApiException(apiError);
            }
        } catch (IOException e) {
            throw new BinanceApiException(e);
        }
    }

    public BinanceApiError getBinanceApiError(Response<?> response) throws IOException, BinanceApiException {
        return errorBodyConverter.convert(response.errorBody());
    }

    @SuppressWarnings("unchecked")
    final Converter<ResponseBody, BinanceApiError> errorBodyConverter = (Converter<ResponseBody, BinanceApiError>) converterFactory
            .responseBodyConverter(BinanceApiError.class, new Annotation[0], null);



}
