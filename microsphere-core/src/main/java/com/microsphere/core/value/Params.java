package com.microsphere.core.value;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;

import java.util.List;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public final class Params {

    private final MutableMap<String, ParamValue> paramValues = Maps.mutable.empty();

    private Params() {
    }

    public boolean isNotEmpty() {
        return !paramValues.isEmpty();
    }

    public Value bind(String name, Object value) {
        final ParamValue param = new ParamValue(value);

        return paramValues.putIfAbsent(name, param);
    }

    public Value get(String name) {
        return paramValues.get(name);
    }

    public List<String> getParametersName() {
        return List.copyOf(paramValues.keySet());
    }

    public static Params create() {
        return new Params();
    }

}
