package com.microsphere.faulttolerance.core;

import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface ProxyCallable {

    <T> T execute(Supplier<T> supplier);

}
