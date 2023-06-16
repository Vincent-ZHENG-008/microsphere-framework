package com.microsphere.faulttolerance.spring.matcher;

import com.microsphere.faulttolerance.core.Bulkhead;
import com.microsphere.faulttolerance.core.ProxyCallable;
import com.microsphere.faulttolerance.core.resilience4j.Resilience4jBulkhead;
import com.microsphere.faulttolerance.spring.FaultToleranceMatcher;
import io.github.resilience4j.bulkhead.BulkheadRegistry;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class BulkheadFaultToleranceMatcher implements FaultToleranceMatcher {

    private static final Class<org.eclipse.microprofile.faulttolerance.Bulkhead> ANNOTATION_CLASS = org.eclipse.microprofile.faulttolerance.Bulkhead.class;

    private final BulkheadRegistry bulkheadRegistry;

    public BulkheadFaultToleranceMatcher(BulkheadRegistry bulkheadRegistry) {
        this.bulkheadRegistry = bulkheadRegistry;
    }

    @Override
    public boolean match(Method method) {
        return method.isAnnotationPresent(ANNOTATION_CLASS);
    }

    @Override
    public ProxyCallable build(Method method) {
        final Map<String, Object> metadata = loadMetadata(method, ANNOTATION_CLASS);
        final Bulkhead.BulkheadOptions options = new Bulkhead.BulkheadOptions(metadata);

        return new Resilience4jBulkhead(method.getName(), bulkheadRegistry, options);
    }
}
