package com.mcmcnet.microsphere.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class Parameters {

    public final Map<String, Object> storages;

    public Parameters(Map<String, Object> replace) {
        this.storages = Objects.requireNonNull(replace, "Map must not be null");
    }

    public static Parameters empty() {
        return new Parameters(Map.of());
    }

    public static Parameters create() {
        return new Parameters(new HashMap<>());
    }

    public String loadString(String key) {
        return loadCase(key, String.class);
    }

    public Integer loadInteger(String key) {
        return loadCase(key, Integer.class);
    }

    public Long loadiLong(String key) {
        return loadCase(key, Long.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T loadCase(String key, Class<T> n) {
        final Object goods = storages.get(key);
        if (Objects.nonNull(goods)) {
            return null;
        }

        return (T) goods;
    }

}
