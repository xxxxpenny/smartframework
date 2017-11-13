package com.aiyi.smartframework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj) {
        String json;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("fail", e);
            throw new RuntimeException();
        }

        return json;
    }

    @SuppressWarnings("unchecked")
    public static <T> T jsonToPojo(String json, Class<T> cls) {
        Object obj;
        try {
            obj = OBJECT_MAPPER.readValue(json, cls);
        } catch (IOException e) {
            LOGGER.error("json to pojo fail", e);
            throw new RuntimeException();
        }


        return (T) obj;
    }
}
