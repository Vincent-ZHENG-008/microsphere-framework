package com.mcmcnet.microsphere.statemachine.listener;

import com.mcmcnet.microsphere.statemachine.state.StateContext;
import com.mcmcnet.microsphere.statemachine.StateMachine;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface ActionListener<S, E> {

    void onExecute(StateMachine<S, E> stateMachine, StateContext<S, E> ctx, long duration);

}
