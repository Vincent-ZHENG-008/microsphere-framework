package com.microsphere.faulttolerance.spring.advice;

import com.microsphere.common.logging.Log;
import com.microsphere.core.util.Assert;
import com.microsphere.faulttolerance.core.ProxyCallable;
import com.microsphere.faulttolerance.spring.FaultToleranceMatcher;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceDefinitionException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;
import org.springframework.aop.ProxyMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class CompositeMethodInterceptor implements MethodInterceptor {

    private static final Class<Fallback> FALLBACK_CLASS = Fallback.class;

    private final Map<Method, ProxyCallable> invokers = new ConcurrentHashMap<>();

    private final Collection<FaultToleranceMatcher> faultToleranceMatchers;

    public CompositeMethodInterceptor(Collection<FaultToleranceMatcher> faultToleranceMatchers) {
        this.faultToleranceMatchers = faultToleranceMatchers;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation pmi)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }

        final Object invoker = pmi.getThis();
        if (invoker == null) {
            throw new IllegalArgumentException("Cannot get real target.");
        }

        final Class<?> invokerClass = invoker.getClass();
        final Method pmiMethod = pmi.getMethod();
        final Method invokerMethod = invokerClass.getMethod(pmiMethod.getName(), pmiMethod.getParameterTypes());

        final ProxyCallable proxyCallable = invokers.computeIfAbsent(invokerMethod, k -> {
            for (FaultToleranceMatcher matcher : faultToleranceMatchers) {
                if (matcher.match(invokerMethod)) {
                    return Assert.notNull(matcher.build(invokerMethod), () -> "FaultToleranceMatcher build callable failed.");
                }
            }

            throw new IllegalArgumentException("FaultToleranceMatcher not match.");
        });

        try {
            return proxyCallable.execute(() -> {
                try {
                    return mi.proceed();
                } catch (RuntimeException ex) {
                    throw ex;
                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (Throwable ex) {
            try {
                return callFallbackIfNecessary(invokerClass, invoker, invokerMethod, ex);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException reflectionException) {
                throw new FaultToleranceException(reflectionException);
            }
        }
    }

    private static Object callFallbackIfNecessary(Class<?> invokerClass, Object invoker, Method invokerMethod, Throwable ex) throws Throwable {
        final Fallback fallback = invokerMethod.getAnnotation(FALLBACK_CLASS);
        if (fallback == null) {
            throw ex;
        }

        Method caller;
        try {
            caller = invokerClass.getDeclaredMethod(fallback.fallbackMethod());
        } catch (NoSuchMethodException e) {
            try {
                caller = invokerClass.getDeclaredMethod(fallback.fallbackMethod(), ex.getClass());
            } catch (NoSuchMethodException mex) {
                throw new FaultToleranceException("Cannot match fallback method.", mex);
            }
        }

        caller.setAccessible(true);
        if (!invokerMethod.getReturnType().equals(caller.getReturnType())) {
            throw new FaultToleranceDefinitionException("Fallback return-type is not same.");
        }

        return caller.invoke(invoker, ex);
    }
}
