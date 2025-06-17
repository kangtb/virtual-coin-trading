package com.binance.connector.client;

/**
 * api回调
 * @param <T>
 */
public interface StreamHandler<T> {

    /**
     * 处理接口返回
     *
     * @param response  返回
     */
    void onResponse(T response);

    /**
     * Called whenever an error occurs.
     *
     * @param cause the cause of the failure
     */
    void onFailure(Throwable cause);

}
