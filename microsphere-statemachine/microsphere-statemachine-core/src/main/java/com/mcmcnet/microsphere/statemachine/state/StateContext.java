package com.mcmcnet.microsphere.statemachine.state;

import com.mcmcnet.microsphere.statemachine.Parameter;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.transition.Transition;

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

    Parameter getParameter();

    Exception getException();

}
