package com.microsphere.statemachine.sample.mertrics;

import com.microsphere.common.logging.Log;
import com.microsphere.statemachine.Parameter;
import com.microsphere.statemachine.StateMachine;
import com.microsphere.statemachine.enumerate.FireResult;
import com.microsphere.statemachine.metric.MetricsExtensionStateMachine;
import com.microsphere.statemachine.state.DefalutState;
import com.microsphere.statemachine.state.StateContext;
import com.microsphere.statemachine.transition.DefaultTransition;
import com.microsphere.statemachine.transition.Transition;
import com.microsphere.statemachine.trigger.ObjectTrigger;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxApiVersion;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
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
        InfluxConfig config = new InfluxConfig() {

            @Override
            public String uri() {
                return "https://ap-southeast-2-1.aws.cloud2.influxdata.com";
            }

            @Override
            public String org() {
                return "wunhwantseng@gmail.com";
            }

            @Override
            public String bucket() {
                return "sample";
            }

            @Override
            public String token() {
                return "19LykqKNo4Ug0wMdQNJ0lTHZbNxF9mzBT1IL0jmCQYTUu-sOZQ7Ce2vjgQFwYAijf2BhahHL7fD0mBjIarcekw==";
            }

            @Override
            public String get(String key) {
                return null; // accept the rest of the defaults
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(1);
            }

            @Override
            public InfluxApiVersion apiVersion() {
                return InfluxApiVersion.V2;
            }
        };
        registry = new InfluxMeterRegistry(config, Clock.SYSTEM);
    }

    @Test
    void sample() throws InterruptedException {
        final String initialEvent = "initial";
        final Consumer<StateContext<String, String>> action = ctx -> LOG.info("initial created");
        final Transition<String, String> transition = new DefaultTransition<>(
                new DefalutState<>(null, initialEvent), new DefalutState<>("created", null), new ObjectTrigger<>(initialEvent), null, List.of(action)
        );

        final ObservationRegistry observationRegistry = ObservationRegistry.create();
        StateMachine<String, String> statemachine = StateMachine.of(List.of(transition));
        statemachine = new MetricsExtensionStateMachine<>(statemachine, observationRegistry);

        final StateContext<String, String> result = statemachine.fire("initial", Parameter.empty());
        Assertions.assertEquals(result.getResult(), FireResult.Accepted);

        Thread.sleep(20000);
    }

}
