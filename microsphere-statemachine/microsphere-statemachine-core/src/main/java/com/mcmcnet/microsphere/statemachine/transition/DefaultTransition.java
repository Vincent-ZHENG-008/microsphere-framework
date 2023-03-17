package com.mcmcnet.microsphere.statemachine.transition;

import com.mcmcnet.microsphere.statemachine.state.State;
import com.mcmcnet.microsphere.statemachine.state.StateContext;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.listener.ActionListener;
import com.mcmcnet.microsphere.statemachine.trigger.DefaultTriggerContext;
import com.mcmcnet.microsphere.statemachine.trigger.Trigger;
import com.mcmcnet.microsphere.statemachine.trigger.TriggerContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefaultTransition<S, E> implements Transition<S, E> {

    private final State<S, E> source;

    private final State<S, E> target;

    private final Trigger<S, E> trigger;

    private final Function<StateContext<S, E>, Boolean> guard;

    private final Collection<Consumer<StateContext<S, E>>> actions;

    private final Collection<ActionListener<S, E>> listeners;

    public DefaultTransition(State<S, E> source, State<S, E> target, Trigger<S, E> trigger, Function<StateContext<S, E>, Boolean> guard, Collection<Consumer<StateContext<S, E>>> actions) {
        this.source = source;
        this.target = target;
        this.trigger = trigger;
        this.guard = guard;
        this.actions = actions;
        this.listeners = new ArrayList<>(0);
    }

    @Override
    public boolean transit(StateContext<S, E> context) {
        final TriggerContext<S, E> triggerContext = new DefaultTriggerContext<>(context.getEvent());

        return this.trigger.evaluate(triggerContext);
    }

    @Override
    public State<S, E> getSource() {
        return source;
    }

    @Override
    public State<S, E> getTarget() {
        return target;
    }

    @Override
    public Trigger<S, E> getTrigger() {
        return trigger;
    }

    @Override
    public Function<StateContext<S, E>, Boolean> getGuard() {
        return guard;
    }

    @Override
    public Collection<Consumer<StateContext<S, E>>> getActions() {
        return this.actions;
    }

    @Override
    public void addActionListener(ActionListener<S, E> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void addActionListener(Collection<ActionListener<S, E>> listeners) {
        this.listeners.addAll(listeners);
    }

    @Override
    public void removeActionListener(ActionListener<S, E> listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void publishEvent(StateMachine<S, E> machine, StateContext<S, E> ctx, long duration) {
        for (ActionListener<S, E> listener : this.listeners) {
            listener.onExecute(machine, ctx, duration);
        }
    }

    @Override
    public String toString() {
        return "DefaultTransition{" +
                "source=" + source +
                ", target=" + target +
                ", trigger=" + trigger +
                ", guard=" + guard +
                ", actions=" + actions +
                ", listeners=" + listeners +
                '}';
    }
}
