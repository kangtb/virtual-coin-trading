package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CancelRestrictions implements EnumAbility<String>{

    ONLY_NEW("ONLY_NEW"),
    ONLY_PARTIALLY_FILLED("ONLY_PARTIALLY_FILLED");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
