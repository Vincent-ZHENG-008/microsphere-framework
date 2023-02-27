package com.mcmcnet.microsphere.core.text;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public abstract class FormatUtil {

    public static final String DEFAULT_PLACEHOLDER = "{}";

    public static String formatWithPlaceholder(String pattern, String placeholder, Object... args) {
        final int offset = placeholder.length();
        final StringBuilder builder = new StringBuilder();

        return null;
    }

    public static String format(String pattern, Object... args) {
        return formatWithPlaceholder(pattern, DEFAULT_PLACEHOLDER, args);
    }

}
