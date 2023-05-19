package com.microsphere.statemachine.metric;

import com.microsphere.core.util.Assert;
import com.microsphere.statemachine.Parameter;
import com.microsphere.statemachine.StateMachine;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.transition.Transition;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import java.util.Collection;
import java.util.UUID;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class MetricsExtensionStateMachine<S, E> implements StateMachine<S, E> {

    private final StateMachine<S, E> delegate;

    private final ObservationRegistry statistics;

    private final String name;

    public MetricsExtensionStateMachine(StateMachine<S, E> delegate, ObservationRegistry statistics) {
        this.delegate = Assert.notNull(delegate, () -> "StateMachine cannot be null.");
        this.statistics = statistics;
        this.name = "statemachine." + delegate.getName();
    }

    @Override
    public void start() {
        this.delegate.start();
    }

    @Override
    public void stop() {
        this.delegate.stop();
    }

    @Override
    public boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override
    public UUID getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Transition<S, E>> getTransitions() {
        return this.delegate.getTransitions();
    }

    @Override
    public StateContext<S, E> fire(E event, Parameter param) {
        final Observation.Context ctx = new Observation.Context();
        final Observation observation = Observation.createNotStarted(getName(), () -> ctx, statistics);

        return observation
                .lowCardinalityKeyValue("statemachine.name", this.getName())
                .observe(() -> {
                    final StateContext<S, E> fired = this.delegate.fire(event, param);

                    if (fired.getResult() == FireResult.Abnormal) {
                        observation.error(fired.getException());
                    }

                    return fired;
                });
    }
}
