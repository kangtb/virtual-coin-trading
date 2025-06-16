package com.binance.connector.client.derivatives_trading_usds_futures.rest.api.model.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarkPriceResponse {

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 标记价格
     */
    private BigDecimal markPrice;

    /**
     * 指数价格
     */
    private BigDecimal indexPrice;

    /**
     * 预估结算价，仅在交割开始前最后一小时有意义
     */
    private BigDecimal estimatedSettlePrice;

    /**
     * 最近更新的资金费率
     */
    private BigDecimal lastFundingRate;

    /**
     * 标的资产的基础利率
     */
    private BigDecimal interestRate;

    /**
     * 下次资金费时间 （资金费率结算时间）
     */
    private Long nextFundingTime;

    /**
     * 更新时间
     */
    private Long time;

}
