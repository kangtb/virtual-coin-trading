package com.binance.connector.common.exception;

import lombok.Data;

/**
 * Binance API error object.
 */
@Data
public class BinanceApiError {

  /**
   * Error code.
   */
  private int code;

  /**
   * Error message.
   */
  private String msg;



}
