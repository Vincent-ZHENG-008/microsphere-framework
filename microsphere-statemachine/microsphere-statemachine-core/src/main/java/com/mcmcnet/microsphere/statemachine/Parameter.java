package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.statemachine.support.Parameters;

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

    Parameter put(String key, Object value);

    <T> T loadCase(String key, Class<T> n);

    default String loadString(String key) {
        return loadCase(key, String.class);
    }

    default Integer loadInteger(String key) {
        return loadCase(key, Integer.class);
    }

    default Long loadLong(String key) {
        return loadCase(key, Long.class);
    }

}
