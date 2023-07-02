package com.microsphere.statemachine;

import com.microsphere.common.logging.Log;
import com.microsphere.core.value.Params;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.listener.ActionListener;
import com.microsphere.statemachine.listener.StateListener;
import com.microsphere.statemachine.state.DefalutState;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.transition.DefaultTransition;
import com.microsphere.statemachine.transition.Transition;
import com.microsphere.statemachine.trigger.ObjectTrigger;
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
class StateMachineTest {

    private static final Log LOG = Log.getFactory(StateMachineTest.class);

    @Test
    void sampleTest() {
        final StateMachine<Object, String> statemachine = StateMachine.of(List.of());

        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());
    }

    @Test
    void transitionTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        final StateContext<String, String> result = statemachine.fire("initial", Params.create());
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);
    }

    @Test
    void transitionWrongEventTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        final StateContext<String, String> result = statemachine.fire("end", Params.create());
        Assertions.assertEquals(result.getResult(), FireResult.Rejected);
    }

    @Test
    void parameterTest() {
        final String key = "ID";
        final String UserId = "USER_ID";
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created and get id with {0}", ctx.getParams().get(key, Integer.class), ctx.getParams().get(UserId, Long.class));
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        final Params params = Params.create();
        params.bind(key, 1);
        params.bind(UserId, 2L);
        final StateContext<String, String> result = statemachine.fire("initial", params);
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);
    }

    static class DefaultStateListener<S, E> implements StateListener<S, E> {

        private final Log log = Log.getFactory(getClass());

        @Override
        public void onEntry(StateContext<S, E> context) {
            log.info("state entered");
        }

        @Override
        public void onExit(StateContext<S, E> context) {
            log.info("state existed");
        }

        @Override
        public void onComplete(StateContext<S, E> context) {
            log.info("state completed");
        }
    }

    static class DefaultActionListener<S, E> implements ActionListener<S, E> {

        private final Log log = Log.getFactory(getClass());

        @Override
        public void onExecute(StateMachine<S, E> stateMachine, StateContext<S, E> ctx, long duration) {
            log.info("Action accepted");
        }
    }

}