package com.microsphere.faulttolerance.spring;

import com.microsphere.common.logging.Log;
import com.microsphere.core.util.Assert;
import com.microsphere.faulttolerance.CircuitBreaker;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class MethodCircuitBreakerInterceptor implements MethodInterceptor {

    private static final Class<org.eclipse.microprofile.faulttolerance.CircuitBreaker> CIRCUIT_BREAKER = org.eclipse.microprofile.faulttolerance.CircuitBreaker.class;

    private final Log log = Log.getFactory(getClass());

    private final CircuitBreaker breaker;

    public MethodCircuitBreakerInterceptor(CircuitBreaker breaker) {
        this.breaker = breaker;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation pmi)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }
        try {
            final String delegateMethodName = mi.getMethod().getName();
            final Object target = pmi.getThis();
            Assert.notNull(target, () -> "Target must not be null");

            final Method delegateMethod = target.getClass().getMethod(delegateMethodName);
            final Map<String, Object> metadata = AnnotationUtils.getAnnotationAttributes(delegateMethod.getAnnotation(CIRCUIT_BREAKER));
            if (Assert.isEmpty(metadata)) {
                return pmi.proceed();
            }

            return breaker.execute(() -> {
                try {
                    return mi.proceed();
                } catch (RuntimeException ex) {
                    throw ex;
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (CircuitBreakerOpenException ex) {
            if (log.isDebugEnabled()) {
                log.debug("CircuitBreaker open...");
            }

            throw ex;
        }
    }
}
