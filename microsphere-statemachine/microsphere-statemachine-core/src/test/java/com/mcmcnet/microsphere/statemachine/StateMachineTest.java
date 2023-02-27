package com.mcmcnet.microsphere.statemachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
class StateMachineTest {

    @Test
    void of() {
        final StateMachine<Object, String> statemachine = StateMachine.of("", List.of());

        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());
    }
    
}