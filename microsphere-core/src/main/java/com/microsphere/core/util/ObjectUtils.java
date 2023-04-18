package com.microsphere.core.util;

import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public final class ObjectUtils {

    public static <T> T replaceIfNull(T source, Supplier<T> replace) {
        if (source == null) {
            return replace.get();
        }
        return source;
    }

}
