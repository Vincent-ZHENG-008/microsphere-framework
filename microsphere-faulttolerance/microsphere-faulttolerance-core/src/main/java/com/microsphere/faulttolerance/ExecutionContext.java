package com.microsphere.faulttolerance;

import java.lang.reflect.Method;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface ExecutionContext {

    Method getMethod();

    Object[] getParameters();

    Throwable getFailure();

}
