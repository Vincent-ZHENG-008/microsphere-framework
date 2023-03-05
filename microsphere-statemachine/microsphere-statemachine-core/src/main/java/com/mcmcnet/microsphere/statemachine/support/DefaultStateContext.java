package com.mcmcnet.microsphere.statemachine.support;

import com.mcmcnet.microsphere.statemachine.StateContext;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.Transition;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class DefaultStateContext<S, E> implements StateContext<S, E> {

    private final Transition<S, E> transition;

    public DefaultStateContext(Transition<S, E> transition) {
        this.transition = transition;
    }

    @Override
    public E getEvent() {
        return null;
    }

    @Override
    public Transition<S, E> getTransition() {
        return transition;
    }

    @Override
    public StateMachine<S, E> getStateMachine() {
        return null;
    }

    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public String toString() {
        return "DefaultStateContext{" +
                "transition=" + transition +
                '}';
    }
}
