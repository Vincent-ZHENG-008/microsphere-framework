package com.microsphere.core.value;

import com.microsphere.core.util.Assert;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Value {

    Object get();

    <T> T get(Class<T> type);

    <T> T get(TypeSupplier<T> typeSupplier);

    boolean isInstanceOf(Class<?> type);

    static Value of(Object value) {
        Assert.notNull(value, "value is required");

        return null;
    }

    class DefaultValue implements Value {

        private final Object value;

        public DefaultValue(Object value) {
            this.value = value;
        }

        @Override
        public Object get() {
            return value;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Class<T> type) {
            if (type.isInstance(value)) {
                return (T) value;
            }

            // todo: wait to implement converter
            return null;
        }

        @Override
        public <T> T get(TypeSupplier<T> typeSupplier) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isInstanceOf(Class<?> type) {
            return type.isInstance(value);
        }
    }

}
