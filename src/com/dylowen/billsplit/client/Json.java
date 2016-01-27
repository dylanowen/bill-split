package com.dylowen.billsplit.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class Json {

    private static final Json SINGLETON = new Json();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Json() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static Json get() {
        return SINGLETON;
    }
}
