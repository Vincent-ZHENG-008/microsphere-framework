package com.microsphere.statemachine.support;


import com.microsphere.common.value.ID;
import com.microsphere.core.util.ObjectUtils;
import com.microsphere.core.value.Params;
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
import java.util.concurrent.TimeUnit;
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
    private final ID<UUID> id;

    /**
     * StateMachine Name
     * Default use {@linkplain DEFAULT_STATEMACHINE_NAME}
     */
    private final String name;

    /**
     * List of transition
     */
    private final Collection<Transition<S, E>> transitions;

    /**
     * Statemachine state
     */
    private volatile int running;

    public DefaultStateMachine(Collection<Transition<S, E>> transitions) {
        this(DEFAULT_STATEMACHINE_NAME, transitions);
    }

    public DefaultStateMachine(String name, Collection<Transition<S, E>> transitions) {
        this.name = ObjectUtils.replaceIfNull(name, () -> DEFAULT_STATEMACHINE_NAME);
        this.id = new MachineId(UUID.randomUUID());
        this.transitions = transitions;
        start();
    }

    @Override
    public UUID getId() {
        return this.id.getId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Transition<S, E>> getTransitions() {
        return List.copyOf(this.transitions);
    }

    @Override
    public void start() {
        running = StateMachine.RUNNING;
    }

    @Override
    public void stop() {
        if (running == StateMachine.RUNNING) {
            running = StateMachine.STOPPED;
        }
    }

    @Override
    public boolean isRunning() {
        return running == StateMachine.RUNNING;
    }

    @Override
    public StateContext<S, E> fire(E event, Params param) {
        for (Transition<S, E> transition : transitions) {
            final DefaultStateContext<S, E> stateContext = new DefaultStateContext<>(this, transition, event, param);
            if (!transition.transit(stateContext)) {
                continue;
            }

            final Function<StateContext<S, E>, Boolean> guard = transition.getGuard();
            if (guard != null) {
                if (!guard.apply(stateContext)) {
                    throw new GuardNonPassedException("Transition guard does not passed.");
                }
            }

            final State<S, E> source = transition.getSource();
            try {
                long startTime = System.nanoTime();

                // State entry event published
                source.onEntry(stateContext);

                // Traversal all action
                transition.getActions().forEach(consumer -> consumer.accept(stateContext));

                // Action listener published
                long usedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                transition.publishEvent(this, stateContext, usedTime);

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
                source.onExit(stateContext);
            }

            // Return StateContext
            return stateContext;
        }

        // Not found transition matcher, fallback return default result
        final DefaultStateContext<S, E> fallback = new DefaultStateContext<>(this, null, event, param);
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
