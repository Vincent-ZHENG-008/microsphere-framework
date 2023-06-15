package com.microsphere.faulttolerance.spring.retry;

import org.aopalliance.aop.Advice;
import org.eclipse.microprofile.faulttolerance.Retry;
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
public class RetryAdvicePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {

    protected static final Class<Retry> ANNOTATION_CLASS = Retry.class;

    private final RetryFactory retryFactory;

    public RetryAdvicePostProcessor(RetryFactory retryFactory) {
        this.retryFactory = retryFactory;
    }

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Component.class, ANNOTATION_CLASS, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodAdvice());
    }

    protected Advice createMethodAdvice() {
        return new MethodRetryInterceptor(retryFactory);
    }
}
