package com.mcmcnet.microsphere.common.serializable.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mcmcnet.microsphere.common.serializable.ObjectSerializable;
import com.mcmcnet.microsphere.common.serializable.SerializableException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wunhwantseng@gmail.com
 */
public final class JsonJacksonObjectSerializable implements ObjectSerializable {

    private volatile ObjectMapper mapper;

    public JsonJacksonObjectSerializable() {

    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String serialized(Object obj) {
        instanceChecker();

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public <T> T deserialized(byte[] values, Class<T> type) {
        instanceChecker();

        try {
            return mapper.readValue(new String(values, ObjectSerializable.CHARSET), type);
        } catch (JsonProcessingException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode readTree(byte[] values) {
        instanceChecker();

        try {
            return (ObjectNode) mapper.readTree(values);
        } catch (IOException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode valueToTree(Object value) {
        instanceChecker();

        try {
            return mapper.valueToTree(value);
        } catch (IllegalArgumentException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public <T> T treeToValue(ObjectNode node, Class<T> type) {
        instanceChecker();

        try {
            return mapper.treeToValue(node, type);
        } catch (JsonProcessingException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode createNode() {
        return mapper.createObjectNode();
    }

    private void instanceChecker() {
        if (mapper == null) {
            synchronized (this) {
                if (mapper == null) {
                    mapper = getInstance();
                }
            }
        }
    }

    private static ObjectMapper getInstance() {
        final ObjectMapper mapper = new ObjectMapper();
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用遇到未知属性抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // register time module
        final JavaTimeModule timeModule = new JavaTimeModule();
        final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(localDateFormatter));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(localDateFormatter));
        mapper.registerModule(timeModule);

        return mapper;
    }
}
