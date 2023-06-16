package com.microsphere.faulttolerance.spring.advice;

import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class CompositeMethodFilter extends StaticMethodMatcher {

    private final Class<? extends Annotation>[] methodAnnotationTypes;

    private final boolean checkInherited;

    public CompositeMethodFilter(Class<? extends Annotation>[] methodAnnotationTypes, boolean checkInherited) {
        this.methodAnnotationTypes = methodAnnotationTypes;
        this.checkInherited = checkInherited;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (matchesMethod(method)) {
            return true;
        }

        if (Proxy.isProxyClass(targetClass)) {
            return false;
        }

        final Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        return specificMethod != method && matchesMethod(specificMethod);
    }

    private boolean matchesMethod(Method method) {
        for (Class<? extends Annotation> annotationType : methodAnnotationTypes) {
            if (this.checkInherited && AnnotatedElementUtils.hasAnnotation(method, annotationType)) {
                return true;
            }
            if (method.isAnnotationPresent(annotationType)) {
                return true;
            }
        }

        return false;
    }
}
