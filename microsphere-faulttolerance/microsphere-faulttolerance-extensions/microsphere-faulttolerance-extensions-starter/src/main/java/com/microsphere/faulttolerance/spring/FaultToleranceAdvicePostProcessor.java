package com.microsphere.faulttolerance.spring;

import org.aopalliance.aop.Advice;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class FaultToleranceAdvicePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] ANNOTATION_CLASS = new Class[]{
            CircuitBreaker.class, Timeout.class, Retry.class, Bulkhead.class
    };

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Component.class, ANNOTATION_CLASS, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodAdvice());
    }

    protected Advice createMethodAdvice() {
        return new CompositeMethodInterceptor();
    }
}
