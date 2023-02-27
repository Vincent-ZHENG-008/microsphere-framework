package com.mcmcnet.microsphere.statemachine.listener;

import com.mcmcnet.microsphere.statemachine.StateContext;
import com.mcmcnet.microsphere.statemachine.StateMachine;

import java.util.function.Consumer;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface ActionListener<S, E> {

    void onExecute(StateMachine<S, E> stateMachine, Consumer<StateContext<S, E>> action, long duration);

}
