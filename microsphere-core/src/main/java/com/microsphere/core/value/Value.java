package com.microsphere.core.value;

import com.microsphere.core.util.Assert;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface Value {

    static Value of(Object value) {
        Assert.notNull(value, () -> "value must not be null");

        return new DefaultValue(value);
    }

    Object get();

    <T> T get(Class<T> type);

    <T> T get(TypeSupplier<T> typeSupplier);

    boolean isInstanceOf(Class<?> type);

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
            if (isInstanceOf(type)) {
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
            Assert.notNull(type, () -> "Type cannot be null.");
            return type.isInstance(value);
        }
    }

}
