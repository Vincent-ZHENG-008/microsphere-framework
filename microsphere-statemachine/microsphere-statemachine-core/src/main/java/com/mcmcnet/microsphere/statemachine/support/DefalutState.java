package com.mcmcnet.microsphere.statemachine.support;

import com.mcmcnet.microsphere.statemachine.State;
import com.mcmcnet.microsphere.statemachine.listener.StateListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class DefalutState<S, E> implements State<S, E> {

    private final BiFunction<S, E, Boolean> supporter;

    private final Collection<StateListener<S, E>> listeners;

    public DefalutState() {
        this((s, e) -> true);
    }

    public DefalutState(S state, E event) {
        this((c, e) -> Objects.equals(c, state) && Objects.equals(e, event));
    }

    public DefalutState(BiFunction<S, E, Boolean> supporter) {
        this.supporter = supporter;
        this.listeners = new ArrayList<>(0);
    }

    @Override
    public boolean support(S current, E event) {
        return this.supporter.apply(current, event);
    }

    @Override
    public void addListener(StateListener<S, E> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(StateListener<S, E> listener) {
        this.listeners.remove(listener);
    }
}
