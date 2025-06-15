package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SelfTradePreventionMode implements EnumAbility<String>{

    NONE("NONE"),

    EXPIRE_TAKER("EXPIRE_TAKER"),

    EXPIRE_MAKER("EXPIRE_MAKER"),

    EXPIRE_BOTH("EXPIRE_BOTH"),

    DECREMENT("DECREMENT"),

    NON_REPRESENTABLE("NON_REPRESENTABLE");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
