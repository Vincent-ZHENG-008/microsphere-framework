package com.mcmcnet.microsphere.core.util;

import java.util.Collection;
import java.util.Objects;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class Assert {

    public static boolean isNotNull(Object source) {
        return !isNull(source);
    }

    public static boolean isNull(Object source) {
        return source == null;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.size() == 0;
    }

    public static <T> T notNull(T source, String msg) {
        if (isNull(source)) {
            throw new IllegalArgumentException(msg);
        }
        return source;
    }

    public static String hasText(String text, String msg) {
        if (!hasText(text)) {
            throw new IllegalArgumentException(msg);
        }
        return text;
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

    public static boolean contains(Enum<?> source, Enum<?>... enums) {
        for (Enum<?> e : enums) {
            if (Objects.equals(e, source)) {
                return true;
            }
        }
        return false;
    }

}
