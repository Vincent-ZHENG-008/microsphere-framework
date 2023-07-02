package com.microsphere.statemachine.listener;

import com.microsphere.statemachine.state.StateContext;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface StateListener<S, E> {

    void onEntry(StateContext<S, E> context);

    void onExit(StateContext<S, E> context);

    void onComplete(StateContext<S, E> context);

}
