package com.mcmcnet.microsphere.statemachine;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface StateContext<S, E> {

    E getEvent();

    Transition<S, E> getTransition();

    StateMachine<S, E> getStateMachine();

    Exception getException();

}
