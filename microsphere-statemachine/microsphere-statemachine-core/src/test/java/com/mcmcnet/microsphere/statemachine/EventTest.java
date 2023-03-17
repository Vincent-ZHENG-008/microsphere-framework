package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.common.logging.Log;
import com.mcmcnet.microsphere.statemachine.enumerate.FireResult;
import com.mcmcnet.microsphere.statemachine.listener.ActionListener;
import com.mcmcnet.microsphere.statemachine.listener.StateListener;
import com.mcmcnet.microsphere.statemachine.state.DefalutState;
import com.mcmcnet.microsphere.statemachine.state.StateContext;
import com.mcmcnet.microsphere.statemachine.transition.DefaultTransition;
import com.mcmcnet.microsphere.statemachine.transition.Transition;
import com.mcmcnet.microsphere.statemachine.trigger.ObjectTrigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class EventTest {

    private static final Log log = Log.getFactory(EventTest.class);

    @Test
    void actionListenerTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> log.info("initial created");
        final ActionListener<String, String> actionListener = new StateMachineTest.DefaultActionListener<>();
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );
        transition.addActionListener(actionListener);

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        final StateContext<String, String> result = statemachine.fire("initial", Parameter.empty());
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);

        transition.removeActionListener(actionListener);
    }

    @Test
    void stateEventTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> log.info("initial created");
        final StateListener<String, String> stateListener = new StateMachineTest.DefaultStateListener<>();
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent).addListener(stateListener), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        final StateContext<String, String> result = statemachine.fire("initial", Parameter.empty());
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);
    }

}
