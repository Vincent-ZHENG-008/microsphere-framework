package com.microsphere.statemachine.support;


import com.microsphere.statemachine.Parameter;
import com.microsphere.statemachine.StateMachine;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.exception.GuardNonPassedException;
import com.microsphere.statemachine.state.DefaultStateContext;
import com.microsphere.statemachine.state.State;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.transition.Transition;

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

    /**
     * List of transition
     */
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
    public StateContext<S, E> fire(E event, Parameter param) {
        for (Transition<S, E> transition : transitions) {
            final DefaultStateContext<S, E> stateContext = new DefaultStateContext<>(this, transition, param);

            if (transition.transit(stateContext)) {
                final Function<StateContext<S, E>, Boolean> guard = transition.getGuard();
                if (guard != null) {
                    if (!guard.apply(stateContext)) {
                        throw new GuardNonPassedException("Transition guard does not passed.");
                    }
                }

                final State<S, E> source = transition.getSource();
                try {
                    // State entry event published
                    source.onEntry(stateContext);

                    // Traversal all action
                    transition.getActions().forEach(consumer -> consumer.accept(stateContext));

                    // Action listener published
                    // Todo：wait to writing logic for computing time
                    transition.publishEvent(this, stateContext, -1);

                    // State completed event published
                    source.onComplete(stateContext);

                    // Result stage upgrade
                    stateContext.resultRegistration(FireResult.Accepted);
                } catch (Exception ex) {
                    // StateContext error registration
                    stateContext.abnormalRegistration(ex);

                    // Result stage upgrade
                    stateContext.resultRegistration(FireResult.Abnormal);
                } finally {
                    // State exist event published
                    source.onExist(stateContext);
                }

                // Return StateContext
                return stateContext;
            }
        }

        // Not found transition matcher, fallback return default result
        final DefaultStateContext<S, E> fallback = new DefaultStateContext<>(this, null, param);
        // Result stage upgrade
        fallback.resultRegistration(FireResult.Rejected);
        // Return StateContext
        return fallback;
    }


    @Override
    public String toString() {
        return "DefaultStateMachine{" +
                "id=" + id +
                ", transitions=" + transitions +
                '}';
    }
}
