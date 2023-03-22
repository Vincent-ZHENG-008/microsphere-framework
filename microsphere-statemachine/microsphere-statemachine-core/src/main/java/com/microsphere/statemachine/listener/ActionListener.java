package com.microsphere.statemachine.listener;

import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.StateMachine;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface ActionListener<S, E> {

    void onExecute(StateMachine<S, E> stateMachine, StateContext<S, E> ctx, long duration);

}
