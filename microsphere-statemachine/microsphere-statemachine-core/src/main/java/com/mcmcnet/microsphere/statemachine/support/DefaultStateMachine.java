package com.mcmcnet.microsphere.statemachine.support;


import com.mcmcnet.microsphere.statemachine.Parameters;
import com.mcmcnet.microsphere.statemachine.StateContext;
import com.mcmcnet.microsphere.statemachine.StateMachine;
import com.mcmcnet.microsphere.statemachine.Transition;
import com.mcmcnet.microsphere.statemachine.exception.GuardNonPassedException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */
public class DefaultStateMachine<S, E> implements StateMachine<S, E> {

    private final UUID id;

    private final E initialEvent;

    private final Collection<Transition<S, E>> transitions;

    public DefaultStateMachine(E initialEvent, Collection<Transition<S, E>> transitions) {
        this.id = UUID.randomUUID();
        this.initialEvent = initialEvent;
        this.transitions = transitions;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public E getInitialEvent() {
        return initialEvent;
    }

    @Override
    public Collection<Transition<S, E>> getTransitions() {
        return List.copyOf(this.transitions);
    }

    @Override
    public boolean fire(E event, Parameters params) {
        S currentState = null;
        if (!Objects.equals(initialEvent, event)) {
            // todo：wait to impl logic
            currentState = null;
        }

        Transition<S, E> trigger = null;
        for (Transition<S, E> transition : transitions) {
            if (transition.getSource().support(currentState, event)) {
                final Function<StateContext<S, E>, Boolean> guard = transition.getGuard();
                if (guard != null) {
                    final DefaultStateContext<S, E> stateContext = new DefaultStateContext<>(transition);
                    if (!guard.apply(stateContext)) {
                        throw new GuardNonPassedException();
                    }
                }

                trigger = transition;
                break;
            }
        }

        if (trigger != null) {
            final DefaultStateContext<S, E> stateContext = new DefaultStateContext<>(trigger);
            trigger.getActions().forEach(consumer -> consumer.accept(stateContext));

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "DefaultStateMachine{" +
                "id=" + id +
                ", initialEvent=" + initialEvent +
                ", transitions=" + transitions +
                '}';
    }
}
