package com.mcmcnet.microsphere.statemachine;

import com.mcmcnet.microsphere.statemachine.listener.ActionListener;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public interface Transition<S, E> {

    boolean transit(StateContext<S, E> context);

    State<S, E> getSource();

    State<S, E> getTarget();

    Function<StateContext<S, E>, Boolean> getGuard();

    Collection<Consumer<StateContext<S, E>>> getActions();

    void addActionListener(ActionListener<S, E> listener);

    void addActionListener(Collection<ActionListener<S, E>> listeners);

    void removeActionListener(ActionListener<S, E> listener);

    void publishEvent(StateMachine<S, E> machine, StateContext<S, E> ctx, long duration);

}
