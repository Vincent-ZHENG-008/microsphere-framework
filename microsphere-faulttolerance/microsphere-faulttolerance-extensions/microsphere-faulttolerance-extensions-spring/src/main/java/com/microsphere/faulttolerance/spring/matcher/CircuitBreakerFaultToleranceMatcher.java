package com.microsphere.faulttolerance.spring.matcher;

import com.microsphere.faulttolerance.core.CircuitBreaker;
import com.microsphere.faulttolerance.core.ProxyCallable;
import com.microsphere.faulttolerance.core.resilience4j.Resilience4jCircuitBreaker;
import com.microsphere.faulttolerance.spring.FaultToleranceMatcher;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class CircuitBreakerFaultToleranceMatcher implements FaultToleranceMatcher {

    private static final Class<org.eclipse.microprofile.faulttolerance.CircuitBreaker> ANNOTATION_CLASS = org.eclipse.microprofile.faulttolerance.CircuitBreaker.class;

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public CircuitBreakerFaultToleranceMatcher(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public boolean match(Method method) {
        return method.isAnnotationPresent(ANNOTATION_CLASS);
    }

    @Override
    public ProxyCallable build(Method method) {
        final Map<String, Object> metadata = loadMetadata(method, ANNOTATION_CLASS);
        final CircuitBreaker.CircuitBreakerOptions options = new CircuitBreaker.CircuitBreakerOptions(metadata);

        return new Resilience4jCircuitBreaker(circuitBreakerRegistry, method.getName(), options);
    }
}
