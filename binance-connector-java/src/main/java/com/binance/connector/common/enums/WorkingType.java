package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkingType implements EnumAbility<String>{

    MARK_PRICE("MARK_PRICE"),

    CONTRACT_PRICE("CONTRACT_PRICE");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
