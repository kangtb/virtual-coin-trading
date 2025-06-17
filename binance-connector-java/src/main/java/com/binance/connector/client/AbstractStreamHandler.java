package com.binance.connector.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractStreamHandler<T> implements StreamHandler<T>{

    @Override
    public void onFailure(Throwable cause) {
        // 回调时发生错误
        log.error("call back error", cause);
    }

}
