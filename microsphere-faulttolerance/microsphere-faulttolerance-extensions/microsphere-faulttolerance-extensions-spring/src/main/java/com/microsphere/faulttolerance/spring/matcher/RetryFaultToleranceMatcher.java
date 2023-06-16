package com.microsphere.faulttolerance.spring.matcher;

import com.microsphere.faulttolerance.core.ProxyCallable;
import com.microsphere.faulttolerance.core.Retry;
import com.microsphere.faulttolerance.core.resilience4j.Resilience4jRetry;
import com.microsphere.faulttolerance.spring.FaultToleranceMatcher;
import io.github.resilience4j.retry.RetryRegistry;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class RetryFaultToleranceMatcher implements FaultToleranceMatcher {

    private static final Class<org.eclipse.microprofile.faulttolerance.Retry> ANNOTATION_CLASS = org.eclipse.microprofile.faulttolerance.Retry.class;

    private final RetryRegistry retryRegistry;

    public RetryFaultToleranceMatcher(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @Override
    public boolean match(Method method) {
        return method.isAnnotationPresent(ANNOTATION_CLASS);
    }

    @Override
    public ProxyCallable build(Method method) {
        final Map<String, Object> metadata = loadMetadata(method, ANNOTATION_CLASS);
        final Retry.RetryOptions options = new Retry.RetryOptions(metadata);

        return new Resilience4jRetry(method.getName(), retryRegistry, options);
    }
}
