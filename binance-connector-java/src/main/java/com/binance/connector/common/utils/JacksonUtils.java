package com.binance.connector.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson 序列化、反序列化工具
 *
 * @author jiang.tao
 * @since 2023/5/31
 */
@Slf4j
public class JacksonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    /**
     * 对象转Json格式字符串
     *
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String toJsonString(T obj) {
        if (obj == null) {
            return null;
        }
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Parse Object to String error", e);
            return null;
        }
    }


    /**
     * 对象转Json格式字符串, 忽略为null的字段
     *
     * @param obj 对象
     * @return Json格式字符串
     */
    public static <T> String toJsonStringIgnoreNullField(T obj) {
        if (obj == null) {
            return null;
        }
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param str   要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T parse(String str, Class<T> clazz) {
        if (str == null || str.isEmpty() || clazz == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * 字符串转换成带泛型对象
     *
     * @param str           要转换的字符串
     * @param typeReference 泛型具体类型
     * @param <T>           泛型
     * @return 泛型对象
     */
    public static <T> T parse(String str, TypeReference<T> typeReference) {
        if (str == null || str.isEmpty() || typeReference == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(str, typeReference);
        } catch (IOException e) {
            log.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * map转bean
     *
     * @param map   集合
     * @param clazz 转换bean类型
     * @param <T>   泛型
     * @return bean对象
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.isEmpty() || clazz == null) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    /**
     * bean转map
     *
     * @param src bean对象
     * @return map
     */
    public static Map<String, Object> beanToMap(Object src) {
        if (src == null) {
            return new HashMap<>();
        }
        return OBJECT_MAPPER.convertValue(src, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * bean转map
     *
     * @param src bean对象
     * @return map
     */
    public static Map<String, Object> beanToMapIgnoreNull(Object src) {
        if (src == null) {
            return new HashMap<>();
        }
        return OBJECT_MAPPER.convertValue(src, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 字符串 数组转换成mapList
     * @param jsonStr 字符串
     * @return mapList
     */
    public static List<Map<String, Object>> arrayToMapList(String jsonStr) {

        if(jsonStr == null || jsonStr.isEmpty()){
            return null;
        }
        try {
            return new ObjectMapper().readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            log.error("Parse String to MapList JsonProcessingException error", e);
            return null;
        }
    }

    /**
     * 字符串转换成JsonNode
     *
     * @param jsonStr           要转换的字符串
     * @return                  json node
     */
    public static JsonNode readTree(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (IOException e) {
            log.error("readTree error", e);
            return null;
        }
    }

    /**
     * 字符串转换成JsonNode
     *
     * @param jsonNode  要转换的jsonNode
     * @param clazz     自定义对象的class对象
     * @return          实体
     */
    public static <T> T treeToValue(JsonNode jsonNode, Class<T> clazz) {
        if (jsonNode == null || clazz == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, clazz);
        } catch (IOException e) {
            log.error("treeToValue error", e);
            return null;
        }
    }
}
