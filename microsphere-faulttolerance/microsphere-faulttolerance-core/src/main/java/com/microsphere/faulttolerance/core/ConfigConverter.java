package com.microsphere.faulttolerance.core;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface ConfigConverter<T, R> {

    R convert(T config);

}
