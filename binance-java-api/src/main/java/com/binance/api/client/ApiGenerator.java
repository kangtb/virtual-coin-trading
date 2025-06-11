package com.binance.api.client;

public interface ApiGenerator {

  <T> T createService(Class<T> serviceClass);
  <T> T createTestnetService(Class<T> serviceClass);

}
