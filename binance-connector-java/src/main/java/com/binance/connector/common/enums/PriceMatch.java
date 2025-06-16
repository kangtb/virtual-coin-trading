package com.binance.connector.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PriceMatch implements EnumAbility<String>{

    NONE("NONE"),

    OPPONENT("OPPONENT"),

    OPPONENT_5("OPPONENT_5"),

    OPPONENT_10("OPPONENT_10"),

    OPPONENT_20("OPPONENT_20"),

    QUEUE("QUEUE"),

    QUEUE_5("QUEUE_5"),

    QUEUE_10("QUEUE_10"),

    QUEUE_20("QUEUE_20");

    private final String value;

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
}
