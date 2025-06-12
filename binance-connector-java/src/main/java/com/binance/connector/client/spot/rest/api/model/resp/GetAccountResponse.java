package com.binance.connector.client.spot.rest.api.model.resp;

import lombok.Data;

import java.util.List;


@Data
public class GetAccountResponse {

    /**
     * 市场佣金
     */
    private Long makerCommission;

    /**
     *
     */
    private Long takerCommission;

    private Long buyerCommission;

    private Long sellerCommission;

    private GetAccountResponseCommissionRates commissionRates;

    private Boolean canTrade;

    private Boolean canWithdraw;

    private Boolean canDeposit;

    private Boolean brokered;

    private Boolean requireSelfTradePrevention;

    private Boolean preventSor;

    private Long updateTime;

    private String accountType;

    private List<GetAccountResponseBalancesInner> balances;

    private List<String> permissions;

    public static final String SERIALIZED_NAME_UID = "uid";

    private Long uid;

}
