package com.microsphere.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public final class ReflectionUtils {

    public static <T> T newInstance(Class<T> type, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Assert.notNull(type, () -> "class type cannot be null");

        final Class<?>[] argsTypes = new Class[args.length];
        if (!Assert.isEmpty(argsTypes)) {
            for (int i = 0; i < args.length; i++) {
                argsTypes[i] = args[i].getClass();
            }
        }
        final Constructor<T> constructor = type.getDeclaredConstructor(argsTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }

}
