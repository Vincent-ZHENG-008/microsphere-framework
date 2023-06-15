package com.microsphere.faulttolerance.spring.util;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public final class AopUtils {

    public static interface AopWrapper {

        Object proceed() throws Throwable;

    }

    public static Object callWrapper(AopWrapper wrapper){
        try {
            return wrapper.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
