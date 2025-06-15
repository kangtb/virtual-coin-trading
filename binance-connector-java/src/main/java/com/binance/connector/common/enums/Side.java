package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.http.GET;

/**
 * side enum
 */

@AllArgsConstructor
public enum Side implements EnumAbility<String> {

    BUY("BUY"),

    SELL("SELL");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
