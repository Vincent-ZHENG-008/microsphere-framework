package com.microsphere.statemachine.state;

import com.microsphere.statemachine.listener.StateListener;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface State<S, E> extends StateListener<S, E> {

    S getState();

    E getEvent();

    State<S, E> addListener(StateListener<S, E> listener);

    State<S, E> removeListener(StateListener<S, E> listener);

}
