package com.microsphere.statemachine;

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
public interface StateMachine<S, E> {

    UUID getId();

    Collection<Transition<S, E>> getTransitions();

    StateContext<S, E> fire(E event, Parameter param);

    static <S, E> StateMachine<S, E> of(Collection<Transition<S, E>> transitions) {
        return new DefaultStateMachine<>(transitions);
    }

}