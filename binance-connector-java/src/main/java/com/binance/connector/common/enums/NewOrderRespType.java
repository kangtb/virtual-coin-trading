package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NewOrderRespType implements EnumAbility<String>{

    ACK("ACK"),

    RESULT("RESULT"),

    FULL("FULL"),

    MARKET("MARKET"),

    LIMIT("LIMIT");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
