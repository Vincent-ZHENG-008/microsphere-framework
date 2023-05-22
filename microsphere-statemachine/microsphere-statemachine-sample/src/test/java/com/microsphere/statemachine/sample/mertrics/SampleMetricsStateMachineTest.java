package com.microsphere.statemachine.sample.mertrics;

import com.microsphere.common.logging.Log;
import com.microsphere.statemachine.Parameter;
import com.microsphere.statemachine.StateMachine;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.exception.GuardNonPassedException;
import com.microsphere.statemachine.metric.MetricsExtensionStateMachine;
import com.microsphere.statemachine.state.DefalutState;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.transition.DefaultTransition;
import com.microsphere.statemachine.transition.Transition;
import com.microsphere.statemachine.trigger.ObjectTrigger;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class SampleMetricsStateMachineTest {

    private static final Log LOG = Log.getFactory(SampleMetricsStateMachineTest.class);

    private static volatile MeterRegistry registry;

    @BeforeAll
    static void init() {
        registry = new LoggingMeterRegistry(new LoggingRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(1);
            }
        }, Clock.SYSTEM, m -> LOG.warn(m));
    }

    @Test
    void sample() throws InterruptedException {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> {
            // save to database

            // print log create succeed
            LOG.info("initial created");
        };
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig()
                .observationHandler(new DefaultMeterObservationHandler(registry));

        StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        statemachine = new MetricsExtensionStateMachine<>(statemachine, observationRegistry);

        final StateContext<String, String> result = statemachine.fire("initial", Parameter.empty());
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);

        Thread.sleep(2000);
    }

    @Test
    void rejectSample() throws InterruptedException {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> {
            // save to database

            // print log create succeed
            LOG.info("initial created");
        };
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig()
                .observationHandler(new DefaultMeterObservationHandler(registry));

        StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        statemachine = new MetricsExtensionStateMachine<>(statemachine, observationRegistry);

        final StateContext<String, String> result = statemachine.fire("initial1", Parameter.empty());
        Assertions.assertEquals(result.getResult(), FireResult.Rejected);

        Thread.sleep(2000);
    }

    @Test
    void noguardSample() throws InterruptedException {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> {
            // save to database

            // print log create succeed
            LOG.info("initial created");
        };
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), x -> false, List.of(action)
        );

        final ObservationRegistry observationRegistry = ObservationRegistry.create();
        observationRegistry.observationConfig()
                .observationHandler(new DefaultMeterObservationHandler(registry));

        StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        statemachine = new MetricsExtensionStateMachine<>(statemachine, observationRegistry);

        try {
            statemachine.fire("initial", Parameter.empty());
        } catch (GuardNonPassedException ex) {
            LOG.info("guard cannot pass.");
        }

        Thread.sleep(2000);
    }

}
