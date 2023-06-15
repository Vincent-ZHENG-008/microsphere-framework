package com.microsphere.faulttolerance.spring.circuitbreaker;

import com.microsphere.faulttolerance.core.CircuitBreaker;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface CircuitBreakerFactory {

    CircuitBreaker getObject(String name, CircuitBreaker.CircuitBreakerOptions ops);

}
