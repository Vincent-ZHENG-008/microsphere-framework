package com.microsphere.faulttolerance.spring.retry;

import com.microsphere.common.logging.Log;
import com.microsphere.core.util.Assert;
import com.microsphere.faulttolerance.core.Retry;
import com.microsphere.faulttolerance.spring.util.AopUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.eclipse.microprofile.faulttolerance.Fallback;
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
public class MethodRetryInterceptor implements MethodInterceptor {

    private static final Log log = Log.getFactory(MethodRetryInterceptor.class);

    private final RetryFactory retryFactory;

    public MethodRetryInterceptor(RetryFactory retryFactory) {
        this.retryFactory = retryFactory;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation pmi)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }

        final String delegateMethodName = mi.getMethod().getName();
        final Object target = pmi.getThis();
        Assert.notNull(target, () -> "Target must not be null");

        final Method delegateMethod = target.getClass().getMethod(delegateMethodName);
        final Map<String, Object> metadata = AnnotationUtils.getAnnotationAttributes(delegateMethod.getAnnotation(RetryAdvicePostProcessor.ANNOTATION_CLASS));
        if (Assert.isEmpty(metadata)) {
            return pmi.proceed();
        }

        final Retry retry = retryFactory.getObject(new Retry.RetryOptions(metadata));
        try {
            return retry.execute(() -> AopUtils.callWrapper(mi::proceed));
        } catch (Throwable ex) {
            try {
                return callFallbackIfNecessary(target, delegateMethod, new RuntimeException(ex));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException reflectionException) {
                throw new FaultToleranceException(reflectionException);
            }
        }
    }

    private static Object callFallbackIfNecessary(Object invoker, Method delegateMethod, RuntimeException ex) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Fallback fallback = delegateMethod.getAnnotation(Fallback.class);
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
