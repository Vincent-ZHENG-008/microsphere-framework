package com.mcmcnet.microsphere.statemachine.state;

import com.mcmcnet.microsphere.statemachine.Parameter;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.transition.Transition;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class DefaultStateContext<S, E> implements StateContext<S, E> {

    private final StateMachine<S, E> stateMachine;

    private final Transition<S, E> transition;

    private final Parameter parameter;

    public DefaultStateContext(StateMachine<S, E> stateMachine, Transition<S, E> transition, Parameter parameter) {
        this.stateMachine = stateMachine;
        this.transition = transition;
        this.parameter = parameter;
    }

    @Override
    public E getEvent() {
        return getTransition().getTrigger().getEvent();
    }

    @Override
    public Transition<S, E> getTransition() {
        return transition;
    }

    @Override
    public StateMachine<S, E> getStateMachine() {
        return stateMachine;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
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
