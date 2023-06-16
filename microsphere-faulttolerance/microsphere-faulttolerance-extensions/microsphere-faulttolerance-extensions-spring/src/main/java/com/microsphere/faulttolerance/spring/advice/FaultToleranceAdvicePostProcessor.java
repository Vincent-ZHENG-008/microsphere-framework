package com.microsphere.faulttolerance.spring.advice;

import com.microsphere.faulttolerance.spring.FaultToleranceMatcher;
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
import java.util.Collection;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class FaultToleranceAdvicePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] ANNOTATION_CLASS = new Class[]{CircuitBreaker.class, Timeout.class, Retry.class, Bulkhead.class};

   private final Collection<FaultToleranceMatcher> faultToleranceMatchers ;

    public FaultToleranceAdvicePostProcessor(Collection<FaultToleranceMatcher> faultToleranceMatchers) {
        this.faultToleranceMatchers = faultToleranceMatchers;
    }

    @Override
    public void afterPropertiesSet() {
        final Pointcut pointcut = new AnnotationMatchingPointcut(Component.class, ANNOTATION_CLASS, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodAdvice(faultToleranceMatchers));
    }

    protected Advice createMethodAdvice(Collection<FaultToleranceMatcher> faultToleranceMatchers) {
        return new CompositeMethodInterceptor(faultToleranceMatchers);
    }
}
