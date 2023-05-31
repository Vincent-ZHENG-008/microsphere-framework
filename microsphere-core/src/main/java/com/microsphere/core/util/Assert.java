package com.microsphere.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class Assert {

    public static void isTrue(Supplier<Boolean> judgement, Supplier<String> msg) {
        isTrue(judgement.get(), msg);
    }

    public static void isTrue(boolean judgement, Supplier<String> msg) {
        if (!judgement) {
            throw new IllegalArgumentException(msg.get());
        }
    }

    public static boolean isNotNull(Object source) {
        return !isNull(source);
    }

    public static boolean isNull(Object source) {
        return source == null;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return isNull(collection) || collection.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> dict) {
        return isNull(dict) || dict.size() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return isNull(array) || array.length == 0;
    }

    public static <T> T notNull(T source, String msg) {
        return notNull(source, () -> msg);
    }

    public static <T> T notNull(T source, Supplier<String> msg) {
        if (isNull(source)) {
            throw new IllegalArgumentException(msg.get());
        }
        return source;
    }

    public static String hasText(String text, String msg) {
        return hasText(text, () -> msg);
    }

    public static String hasText(String text, Supplier<String> msg) {
        if (!hasText(text)) {
            throw new IllegalArgumentException(msg.get());
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
