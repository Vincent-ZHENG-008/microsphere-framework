package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttolerance.core.ProxyCallable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class CompositeMethodInterceptor implements MethodInterceptor {

    private final Map<Method, ProxyCallable> invokers = new HashMap<>();

    private final Object lock = new Object();

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation pmi)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }

        final Method method = mi.getMethod();
        ProxyCallable proxyCallable = invokers.get(method);
        if (proxyCallable == null) {
            synchronized (lock) {
                proxyCallable = invokers.get(method);
                if (proxyCallable == null){
                    invokers.computeIfAbsent(method,m->{
                        return null;
                    });
                }
            }
        }

        return mi.proceed();
    }
}
