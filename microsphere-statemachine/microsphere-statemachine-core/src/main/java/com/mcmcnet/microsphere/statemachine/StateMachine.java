package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.statemachine.support.DefaultStateMachine;

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

    E getInitialEvent();

    Collection<Transition<S, E>> getTransitions();

    boolean fire(E event, Parameters params);

    static <S, E> StateMachine<S, E> of(E initialEvent, Collection<Transition<S, E>> transitions) {
        return new DefaultStateMachine<S, E>(initialEvent, transitions);
    }

}
