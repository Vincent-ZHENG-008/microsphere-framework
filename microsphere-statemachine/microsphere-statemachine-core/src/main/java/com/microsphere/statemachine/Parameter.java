package com.microsphere.statemachine;

import com.microsphere.statemachine.support.Parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface Parameter {

    static Parameter empty() {
        return new Parameters(Map.of());
    }

    static Parameter create() {
        return new Parameters(new HashMap<>());
    }

    static Parameter from(Map<String, Object> source) {
        return new Parameters(Map.copyOf(source));
    }

    Parameter put(String key, Object value);

    <T> T load(String key, Class<T> n);

}
