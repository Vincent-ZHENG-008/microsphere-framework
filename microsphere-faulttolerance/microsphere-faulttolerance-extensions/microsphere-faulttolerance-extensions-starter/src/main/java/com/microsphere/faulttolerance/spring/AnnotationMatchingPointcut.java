package com.microsphere.faulttolerance.spring;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;

import java.lang.annotation.Annotation;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class AnnotationMatchingPointcut implements Pointcut {

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    public AnnotationMatchingPointcut(Class<? extends Annotation> classAnnotationType, Class<? extends Annotation>[] methodAnnotationTypes, boolean checkInherited) {
        this.classFilter = new AnnotationClassFilter(classAnnotationType);
        this.methodMatcher = new CompositeMethodFilter(methodAnnotationTypes, checkInherited);
    }

    @Override
    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }
}
