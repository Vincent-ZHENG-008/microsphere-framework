package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttolerance.core.ProxyCallable;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public interface FaultToleranceMatcher {

    boolean match(Method method);

    ProxyCallable build(Method method);

    default Map<String, Object> loadMetadata(Method method, Class<? extends Annotation> type) {
        return AnnotationUtils.getAnnotationAttributes(method.getAnnotation(type));
    }

}
