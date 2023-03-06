package com.mcmcnet.microsphere.statemachine.state;

import com.mcmcnet.microsphere.statemachine.listener.StateListener;

import java.util.Collection;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface State<S, E> extends StateListener<S, E> {

    State<S, E> addListener(StateListener<S, E> listener);

    State<S, E> removeListener(StateListener<S, E> listener);

}
