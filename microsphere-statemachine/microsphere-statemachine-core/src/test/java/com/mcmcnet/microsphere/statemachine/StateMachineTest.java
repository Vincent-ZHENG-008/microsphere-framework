package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.common.logging.Log;
import com.mcmcnet.microsphere.statemachine.listener.ActionListener;
import com.mcmcnet.microsphere.statemachine.listener.StateListener;
import com.mcmcnet.microsphere.statemachine.state.DefalutState;
import com.mcmcnet.microsphere.statemachine.state.StateContext;
import com.mcmcnet.microsphere.statemachine.transition.DefaultTransition;
import com.mcmcnet.microsphere.statemachine.transition.Transition;
import com.mcmcnet.microsphere.statemachine.trigger.EventTrigger;
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
    void of() {
        final StateMachine<Object, String> statemachine = StateMachine.of(List.of());

        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());
    }

    @Test
    void transitionTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new EventTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        boolean passed = statemachine.fire("initial", Parameter.empty());
        Assertions.assertTrue(passed);
    }

    @Test
    void stateEventTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final StateListener<String, String> stateListener = new DefaultStateListener<>();
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent).addListener(stateListener), new DefalutState<>("created", null), new EventTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        boolean passed = statemachine.fire("initial", Parameter.empty());
        Assertions.assertTrue(passed);
    }

    @Test
    void actionListenerTest() {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final ActionListener<String, String> actionListener = new DefaultActionListener<>();
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent), new DefalutState<>("created", null), new EventTrigger<>(initialEvent), null, List.of(action)
        );
        transition.addActionListener(actionListener);

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        boolean passed = statemachine.fire("initial", Parameter.empty());
        Assertions.assertTrue(passed);

        transition.removeActionListener(actionListener);
    }

    @Test
    void parameterTest() {
        final String key = "ID";
        final String UserId = "USER_ID";
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created and get id with {0}", ctx.getParameter().loadInteger(key), ctx.getParameter().loadLong(UserId));
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>("", initialEvent), new DefalutState<>("created", null), new EventTrigger<>(initialEvent), null, List.of(action)
        );

        final StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        Assertions.assertNotNull(statemachine);
        Assertions.assertNotNull(statemachine.getId());

        boolean passed = statemachine.fire("initial", Parameter.create().put(key, 1).put(UserId, 2L));
        Assertions.assertTrue(passed);
    }

    static class DefaultStateListener<S, E> implements StateListener<S, E> {

        private final Log log = Log.getFactory(getClass());

        @Override
        public void onEntry(StateContext<S, E> context) {
            log.info("state entered");
        }

        @Override
        public void onExist(StateContext<S, E> context) {
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