package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PositionSide implements EnumAbility<String>{

    BOTH("BOTH"),

    LONG("LONG"),

    SHORT("SHORT");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
