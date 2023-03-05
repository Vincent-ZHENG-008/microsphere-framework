package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.statemachine.support.DefalutState;
import com.mcmcnet.microsphere.statemachine.support.DefaultTransition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void transitionTest() {
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(), new DefalutState<>("created", ""), null, List.of(System.out::println)
        );

        final StateMachine<String, String> statemachine = StateMachine.of("init", List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        statemachine.fire("init", Parameters.empty());
    }

}