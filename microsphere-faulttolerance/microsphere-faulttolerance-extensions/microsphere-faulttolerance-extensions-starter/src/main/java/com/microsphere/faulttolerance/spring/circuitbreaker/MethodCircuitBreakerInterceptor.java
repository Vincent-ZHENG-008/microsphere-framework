package com.microsphere.faulttolerance.spring.circuitbreaker;

import com.microsphere.common.logging.Log;
import com.microsphere.core.util.Assert;
import com.microsphere.faulttolerance.core.CircuitBreaker;
import com.microsphere.faulttolerance.spring.util.AopUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceDefinitionException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class MethodCircuitBreakerInterceptor implements MethodInterceptor {

    private static final Class<org.eclipse.microprofile.faulttolerance.CircuitBreaker> CIRCUIT_BREAKER_CLASS = org.eclipse.microprofile.faulttolerance.CircuitBreaker.class;
    private static final Class<Fallback> FALLBACK_CLASS = Fallback.class;

    private static final Log log = Log.getFactory(MethodCircuitBreakerInterceptor.class);

    private final CircuitBreakerFactory circuitBreakerFactory;

    public MethodCircuitBreakerInterceptor(CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation pmi)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }

        final String delegateMethodName = mi.getMethod().getName();
        final Object target = pmi.getThis();
        final Class<?> delegateClass = target.getClass();

        final Method delegateMethod = delegateClass.getMethod(delegateMethodName);
        final Map<String, Object> metadata = AnnotationUtils.getAnnotationAttributes(delegateMethod.getAnnotation(CIRCUIT_BREAKER_CLASS));
        if (Assert.isEmpty(metadata)) {
            return pmi.proceed();
        }

        final String methodName = delegateClass.getName() + delegateMethodName;
        final CircuitBreaker circuitBreaker = circuitBreakerFactory.getObject(methodName, new CircuitBreaker.CircuitBreakerOptions(metadata));

        try {
            return circuitBreaker.execute(() -> AopUtils.callWrapper(mi::proceed));
        } catch (CircuitBreakerOpenException ex) {
            if (log.isDebugEnabled()) {
                log.debug("CircuitBreaker open...");
            }

            throw ex;
        } catch (Throwable ex) {
            try {
                return callFallbackIfNecessary(target, delegateMethod, ex);
            } catch (NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException reflectionException) {
                throw new FaultToleranceException(reflectionException);
            }
        }
    }

    private static Object callFallbackIfNecessary(Object invoker, Method delegateMethod, Throwable ex) throws Throwable {
        final Fallback fallback = delegateMethod.getAnnotation(FALLBACK_CLASS);
        if (fallback == null) {
            throw ex;
        }

        final Class<?> invokerClass = invoker.getClass();
        Method caller;
        try {
            caller = invokerClass.getMethod(fallback.fallbackMethod());
        } catch (NoSuchMethodException e) {
            caller = invokerClass.getMethod(fallback.fallbackMethod(), Exception.class);
        }

        caller.setAccessible(true);
        if (!delegateMethod.getReturnType().equals(caller.getReturnType())) {
            throw new FaultToleranceDefinitionException("Fallback return-type is not same.");
        }

        return caller.invoke(invoker, ex);
    }
}
