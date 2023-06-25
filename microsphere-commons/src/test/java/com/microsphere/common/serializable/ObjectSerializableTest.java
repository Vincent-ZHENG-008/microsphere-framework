package com.microsphere.common.serializable;

import com.microsphere.core.extension.ExtensionLoader;
import com.microsphere.core.extension.support.ApplicationModel;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class ObjectSerializableTest {

    @Test
    void serialized() {
        final ExtensionLoader<ObjectSerializable> loader = ApplicationModel.get(ObjectSerializable.class);
        final ObjectSerializable serializable = loader.load(ObjectSerializable.Scope.JSON.getName());

        final String value = serializable.serialized(Map.of("name", "zhangsan"));
        System.out.println(value);
    }
}