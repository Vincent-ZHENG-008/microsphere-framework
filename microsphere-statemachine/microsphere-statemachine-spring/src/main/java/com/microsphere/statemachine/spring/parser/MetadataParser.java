package com.microsphere.statemachine.spring.parser;

import com.microsphere.core.util.Assert;
import com.microsphere.statemachine.spring.support.StateMachineBeanPostProcessor;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public final class MetadataParser {

    private final com.microsphere.statemachine.spring.annotation.StateMachine machine;

    private final String machineName;

    public MetadataParser(String defaultName, Class<?> targetClass) {
        this.machine = lookupMachine(targetClass);
        this.machineName = lookupMachineName(machine, defaultName);
    }

    private static com.microsphere.statemachine.spring.annotation.StateMachine lookupMachine(Class<?> targetClass) {
        final com.microsphere.statemachine.spring.annotation.StateMachine stateMachine = targetClass.
                getAnnotation(StateMachineBeanPostProcessor.ANNOTATION_CLASS);
        if (stateMachine == null) {
            throw new IllegalArgumentException("Target class cannot be null");
        }

        return stateMachine;
    }

    private static String lookupMachineName(com.microsphere.statemachine.spring.annotation.StateMachine machine, String defaultName) {
        String machineName = machine.value();
        if (!Assert.hasText(machineName)) {
            machineName = defaultName;
        }
        return machineName;
    }
}
