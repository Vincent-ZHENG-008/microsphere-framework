package com.microsphere.faulttolerance.spring.circuitbreaker;

import org.aopalliance.aop.Advice;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class CircuitBreakerAdvicePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {

    private static final Class<CircuitBreaker> ANNOTATION_CLASS = CircuitBreaker.class;

    private final CircuitBreakerFactory circuitBreakerFactory;

    public CircuitBreakerAdvicePostProcessor(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Component.class, ANNOTATION_CLASS, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodAdvice());
    }

    protected Advice createMethodAdvice() {
        return new MethodCircuitBreakerInterceptor(circuitBreakerFactory);
    }
}
