package com.microsphere.common.serializable.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsphere.common.serializable.ObjectSerializable;
import com.microsphere.common.serializable.SerializableException;

import java.io.IOException;

/**
 * @author wunhwantseng@gmail.com
 */
public class XmlJacksonObjectSerializable implements ObjectSerializable {

    private final XmlMapper mapper;

    public XmlJacksonObjectSerializable() {
        this.mapper = getInstance();
    }

    @Override
    public String serialized(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public <T> T deserialized(byte[] values, Class<T> type) {
        try {
            return mapper.readValue(values, type);
        } catch (IOException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode readTree(byte[] values) {
        try {
            return (ObjectNode) mapper.readTree(values);
        } catch (IOException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode valueToTree(Object value) {
        try {
            return mapper.valueToTree(value);
        } catch (IllegalArgumentException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public <T> T treeToValue(ObjectNode node, Class<T> type) {
        try {
            return mapper.treeToValue(node, type);
        } catch (IOException exception) {
            throw new SerializableException(exception.getMessage());
        }
    }

    @Override
    public ObjectNode createNode() {
        return mapper.createObjectNode();
    }

    private static XmlMapper getInstance() {
        final XmlMapper mapper = new XmlMapper();
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用遇到未知属性抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // register module
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
