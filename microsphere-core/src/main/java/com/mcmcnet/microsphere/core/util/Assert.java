package com.mcmcnet.microsphere.core.util;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class Assert {

    public static <T> T notNull(T source, String msg) {
        if (source == null) {
            throw new IllegalArgumentException(msg);
        }

        return source;
    }

    public static String hasTest(String text, String msg) {
        if (!hasTest(text)) {
            throw new IllegalArgumentException(msg);
        }

        return text;
    }

    public static boolean hasTest(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

}
