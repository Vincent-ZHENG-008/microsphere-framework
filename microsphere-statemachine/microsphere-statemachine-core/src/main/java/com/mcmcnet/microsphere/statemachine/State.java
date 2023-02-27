package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.statemachine.listener.StateListener;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface State<S, E> {

    boolean support(S current, E event);

    void addStateListener(StateListener<S, E> listener);

    void removeStateListener(StateListener<S, E> listener);

}
