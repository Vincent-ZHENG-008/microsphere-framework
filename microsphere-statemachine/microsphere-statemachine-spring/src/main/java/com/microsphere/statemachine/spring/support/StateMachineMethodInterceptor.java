package com.microsphere.statemachine.spring.support;

import com.microsphere.core.util.Assert;
import com.microsphere.statemachine.StateMachine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class StateMachineMethodInterceptor implements MethodInterceptor {

    private final Map<Class<?>, StateMachine<?, ?>> stateMachines = new ConcurrentHashMap<>(16);

    @Override
    @SuppressWarnings("ConstantConditions")
    public Object invoke(MethodInvocation mi) throws Throwable {
        if (!(mi instanceof ProxyMethodInvocation)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
        }

        final ProxyMethodInvocation pmi = (ProxyMethodInvocation) mi;
        final Object proxy = Assert.notNull(pmi.getThis(), "Cannot get proxy target");
        final Class<?> proxyClass = proxy.getClass();

        StateMachine<?, ?> stateMachine = stateMachines.get(proxyClass);
        if (stateMachine == null) {
            stateMachine = stateMachines.computeIfAbsent(proxyClass, k -> {
                return StateMachine.of("", null);
            });
        }

        return pmi.proceed();
    }
}
