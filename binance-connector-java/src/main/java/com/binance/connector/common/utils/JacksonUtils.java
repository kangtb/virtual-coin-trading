package com.binance.connector.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JacksonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static Map<String, Object> convertToMap(Object entity) {
        try {
            return objectMapper.convertValue(entity, new TypeReference<Map<String,Object>>(){});
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to convert entity to map: " + entity.getClass().getName(), e);
        }
    }

}
