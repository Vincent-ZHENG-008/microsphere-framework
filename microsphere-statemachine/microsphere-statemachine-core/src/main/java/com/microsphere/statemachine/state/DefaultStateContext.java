package com.microsphere.statemachine.state;

import com.microsphere.core.util.ObjectUtils;
import com.microsphere.statemachine.Parameter;
import com.microsphere.statemachine.StateMachine;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.transition.Transition;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefaultStateContext<S, E> implements StateContext<S, E> {

    private final StateMachine<S, E> stateMachine;

    private final Transition<S, E> transition;

    private final Parameter parameter;

    private Exception error;

    private FireResult result;

    public DefaultStateContext(StateMachine<S, E> stateMachine, Transition<S, E> transition, Parameter parameter) {
        this.stateMachine = stateMachine;
        this.transition = transition;
        this.parameter = parameter;
        this.error = null;
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
        return error;
    }

    @Override
    public FireResult getResult() {
        return ObjectUtils.replaceIfNull(this.result, () -> FireResult.Rejected);
    }

    public void abnormalRegistration(Exception ex) {
        this.error = ex;
    }

    public void resultRegistration(FireResult rs) {
        this.result = rs;
    }

    @Override
    public String toString() {
        return "DefaultStateContext{" +
                "transition=" + transition +
                '}';
    }
}
