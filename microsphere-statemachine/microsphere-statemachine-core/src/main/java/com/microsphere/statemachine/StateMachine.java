package com.microsphere.statemachine;

import com.microsphere.common.container.context.Lifecycle;
import com.microsphere.common.value.ID;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.support.DefaultStateMachine;
import com.microsphere.statemachine.transition.Transition;

import java.util.Collection;
import java.util.UUID;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface StateMachine<S, E> extends Lifecycle {

    /**
     * Default stateMachine name
     */
    String DEFAULT_STATEMACHINE_NAME = "DefaultStateMachine";

    /**
     * StateMachine running state
     */
    int RUNNING = 0;

    /**
     * StateMachine stop state
     */
    int STOPPED = 1;

    /**
     * StateMachine Id number
     *
     * @return UUID
     */
    UUID getId();

    /**
     * Name of StateMachine
     * Can use to be service name, like metrics collector or listener
     *
     * @return name of statemachine, should not return empty string or null. can use `DEFAULT_STATEMACHINE_NAME` of default statemacine name
     */
    String getName();

    Collection<Transition<S, E>> getTransitions();

    StateContext<S, E> fire(E event, Parameter param);

    static <S, E> StateMachine<S, E> of(Collection<Transition<S, E>> transitions) {
        return new DefaultStateMachine<>(transitions);
    }

    record MachineId(UUID uuid) implements ID<UUID> {

        @Override
        public UUID getId() {
            return uuid;
        }
    }

}
