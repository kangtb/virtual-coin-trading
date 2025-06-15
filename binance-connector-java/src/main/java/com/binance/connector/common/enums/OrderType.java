package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * 订单类型
 */
@AllArgsConstructor
public enum OrderType implements EnumAbility<String>{

    MARKET("MARKET"),

    LIMIT("LIMIT"),

    STOP_LOSS("STOP_LOSS"),

    STOP_LOSS_LIMIT("STOP_LOSS_LIMIT"),

    TAKE_PROFIT("TAKE_PROFIT"),

    TAKE_PROFIT_LIMIT("TAKE_PROFIT_LIMIT"),

    LIMIT_MAKER("LIMIT_MAKER"),

    NON_REPRESENTABLE("NON_REPRESENTABLE");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }

}
