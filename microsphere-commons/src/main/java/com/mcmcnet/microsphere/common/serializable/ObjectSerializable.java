package com.mcmcnet.microsphere.common.serializable;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wunhwantseng@gmail.com
 */
public interface ObjectSerializable {

    Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * @param obj Target
     * @return Serialized data
     * @throws SerializableException if serialized failed, will throwable SerializableException
     */
    String serialized(Object obj);

    /**
     * @param values Serialized data
     * @param type   Class of instance
     * @param <T>    Type of instance
     * @return Instance of type
     * @throws SerializableException if deserialized failed or instantiate object failed, will throwable SerializableException
     */
    <T> T deserialized(byte[] values, Class<T> type);

    /**
     * @param values Serialized data
     * @return After deserialize object node
     * @throws SerializableException if deserialized failed or instantiate object failed, will throwable SerializableException
     */
    ObjectNode readTree(byte[] values);

    /**
     * @param value Serialized data
     * @return After deserialize object node
     * @throws SerializableException if deserialized failed or instantiate object failed, will throwable SerializableException
     */
    ObjectNode valueToTree(Object value);

    <T> T treeToValue(ObjectNode node, Class<T> type);

    /**
     * 构建 Jackson 数据绑定的 ObjectNode 对象
     *
     * @return ObjectNode
     */
    ObjectNode createNode();

    enum Scope {
        JSON("json"),
        XML("xml");

        private final String name;

        Scope(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Scope{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
