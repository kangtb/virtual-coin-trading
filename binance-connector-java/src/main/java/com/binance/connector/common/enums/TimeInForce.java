package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeInForce implements EnumAbility<String>{

    GTC("GTC"),

    IOC("IOC"),

    FOK("FOK"),

    NON_REPRESENTABLE("NON_REPRESENTABLE");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
