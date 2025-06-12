package com.binance.connector.common.generator;

public interface ApiGenerator {

    /**
     * 生成api代码
     *
     * @param apiClass  api类
     * @param isTestnet 是否是测试网
     * @return api类
     */
    <T> T createApi(Class<T> apiClass, Boolean isTestnet);

}
