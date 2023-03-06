package com.mcmcnet.microsphere.statemachine.support;


import com.mcmcnet.microsphere.statemachine.Parameter;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.exception.GuardNonPassedException;
import com.mcmcnet.microsphere.statemachine.state.DefaultStateContext;
import com.mcmcnet.microsphere.statemachine.state.State;
import com.mcmcnet.microsphere.statemachine.state.StateContext;
import com.mcmcnet.microsphere.statemachine.transition.Transition;
import com.mcmcnet.microsphere.statemachine.trigger.DefaultTriggerContext;
import com.mcmcnet.microsphere.statemachine.trigger.TriggerContext;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefaultStateMachine<S, E> implements StateMachine<S, E> {

    /**
     * StateMachine ID
     * Default used uuid generator
     */
    private final UUID id;

    private final Collection<Transition<S, E>> transitions;

    public DefaultStateMachine(Collection<Transition<S, E>> transitions) {
        this.id = UUID.randomUUID();
        this.transitions = transitions;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Collection<Transition<S, E>> getTransitions() {
        return List.copyOf(this.transitions);
    }

    @Override
    public boolean fire(E event, Parameter param) {
        final TriggerContext<S, E> triggerContext = new DefaultTriggerContext<>(event);

        for (Transition<S, E> transition : transitions) {
            if (transition.getTrigger().evaluate(triggerContext)) {
                final DefaultStateContext<S, E> stateContext = new DefaultStateContext<>(this, transition, param);

                final Function<StateContext<S, E>, Boolean> guard = transition.getGuard();
                if (guard != null) {
                    if (!guard.apply(stateContext)) {
                        throw new GuardNonPassedException();
                    }
                }

                final State<S, E> source = transition.getSource();
                try {
                    // State entry event published
                    source.onEntry(stateContext);

                    // Traversal all action
                    transition.getActions().forEach(consumer -> consumer.accept(stateContext));

                    // Action listener published
                    transition.publishEvent(this, stateContext, -1);

                    // State completed event published
                    source.onComplete(stateContext);
                } finally {
                    // State exist event published
                    source.onExist(stateContext);
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "DefaultStateMachine{" +
                "id=" + id +
                ", transitions=" + transitions +
                '}';
    }
}
