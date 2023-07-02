package com.microsphere.statemachine;

import com.microsphere.common.logging.Log;
import com.microsphere.core.value.Params;
import com.microsphere.statemachine.exception.GuardNonPassedException;
import com.microsphere.statemachine.state.DefalutState;
import com.microsphere.statemachine.state.State;
import com.microsphere.statemachine.transition.DefaultTransition;
import com.microsphere.statemachine.transition.Transition;
import com.microsphere.statemachine.trigger.ObjectTrigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class GuardStateMachineTest {

    private static final Log LOG = Log.getFactory(GuardStateMachineTest.class);

    @Test
    void guardNopassedTest() {
        final State<String, String> source = new DefalutState<>("init", null);
        final State<String, String> target = new DefalutState<>("created", null);

        final Transition<String, String> transition = new DefaultTransition<>(source, target, new ObjectTrigger<>("init"), ctx -> false, List.of());

        try {
            final StateMachine<String, String> sm = StateMachine.of(List.of(transition));
            sm.fire("init", Params.create());
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof GuardNonPassedException);

            LOG.info("catch `GuardNonPassedException` ex succeed");
        }
    }

}
