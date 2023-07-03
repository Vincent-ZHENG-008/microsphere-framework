package com.microsphere.statemachine.spring.support;

import com.microsphere.core.util.Assert;
import com.microsphere.statemachine.StateMachine;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class StateMachineBeanPostProcessor implements BeanPostProcessor {

    public static final Class<com.microsphere.statemachine.spring.annotation.StateMachine> ANNOTATION_CLASS = com.microsphere.statemachine.spring.annotation.StateMachine.class;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> targetClass = AopUtils.getTargetClass(bean);
        final com.microsphere.statemachine.spring.annotation.StateMachine stateMachine = targetClass.getAnnotation(ANNOTATION_CLASS);
        if (Assert.isNull(stateMachine)) {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }

        String machineName = stateMachine.value();
        if (!Assert.hasText(machineName)) {
            machineName = beanName;
        }
        StateMachine.of(machineName, null);

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
