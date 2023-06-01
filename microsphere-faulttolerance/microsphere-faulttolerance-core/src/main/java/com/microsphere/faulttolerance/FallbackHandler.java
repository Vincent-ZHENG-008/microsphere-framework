package com.microsphere.faulttolerance;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface FallbackHandler<T> {

    T handle(ExecutionContext context);

}
