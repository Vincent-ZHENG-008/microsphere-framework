package com.mcmcnet.microsphere.statemachine.support;

import com.mcmcnet.microsphere.core.util.Assert;
import com.mcmcnet.microsphere.statemachine.Parameter;

import java.util.Map;
import java.util.Objects;

/**
 * Parameters is local store of {@link  Parameter} implement.
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public final class Parameters implements Parameter {

    public final Map<String, Object> storages;

    public Parameters(Map<String, Object> replace) {
        this.storages = Objects.requireNonNull(replace, "Map must not be null");
    }

    @Override
    public Parameter put(String key, Object value) {
        storages.put(key, value);

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(String key, Class<T> n) {
        final Object goods = storages.get(key);
        if (Assert.isNull(goods)) {
            return null;
        }

        return (T) goods;
    }

}
