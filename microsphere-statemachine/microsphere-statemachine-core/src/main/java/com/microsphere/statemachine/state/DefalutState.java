package com.microsphere.statemachine.state;

import com.microsphere.statemachine.listener.StateListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefalutState<S, E> implements State<S, E> {

    private final S state;

    private final E event;

    private final Collection<StateListener<S, E>> listeners;

    public DefalutState(S state, E event) {
        this.state = state;
        this.event = event;
        this.listeners = new ArrayList<>(0);
    }

    @Override
    public S getState() {
        return this.state;
    }

    @Override
    public E getEvent() {
        return this.event;
    }

    @Override
    public State<S, E> addListener(StateListener<S, E> listener) {
        this.listeners.add(listener);

        return this;
    }

    @Override
    public State<S, E> removeListener(StateListener<S, E> listener) {
        this.listeners.remove(listener);

        return this;
    }

    @Override
    public void onEntry(StateContext<S, E> context) {
        this.listeners.forEach(listener -> listener.onEntry(context));
    }

    @Override
    public void onExist(StateContext<S, E> context) {
        this.listeners.forEach(listener -> listener.onExist(context));
    }

    @Override
    public void onComplete(StateContext<S, E> context) {
        this.listeners.forEach(listener -> listener.onComplete(context));
    }
}
