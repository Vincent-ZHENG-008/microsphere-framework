package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttolerance.CircuitBreaker;
import org.aopalliance.aop.Advice;
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

    private final CircuitBreaker circuitBreaker;

    public CircuitBreakerAdvicePostProcessor(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Component.class, org.eclipse.microprofile.faulttolerance.CircuitBreaker.class, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodAdvice());
    }

    protected Advice createMethodAdvice() {
        return new MethodCircuitBreakerInterceptor(circuitBreaker);
    }
}
