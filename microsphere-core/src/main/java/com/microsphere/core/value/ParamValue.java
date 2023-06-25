package com.microsphere.core.value;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class ParamValue implements Value {

    private final Object value;

    public ParamValue(Object value) {
        this.value = value;
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public <T> T get(Class<T> type) {
        return Value.of(value).get(type);
    }

    @Override
    public <T> T get(TypeSupplier<T> typeSupplier) {
        return Value.of(value).get(typeSupplier);
    }

    @Override
    public boolean isInstanceOf(Class<?> type) {
        if (value == null) {
            return true;
        }
        return type.isInstance(value);
    }
}
