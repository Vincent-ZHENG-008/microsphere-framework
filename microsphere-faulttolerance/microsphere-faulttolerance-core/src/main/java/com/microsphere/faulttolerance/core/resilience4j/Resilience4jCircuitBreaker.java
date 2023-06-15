package com.microsphere.faulttolerance.core.resilience4j;

import com.microsphere.faulttolerance.core.CircuitBreaker;
import com.microsphere.faulttolerance.core.ConfigConverter;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class Resilience4jCircuitBreaker implements CircuitBreaker, ConfigConverter<CircuitBreaker.CircuitBreakerOptions,CircuitBreakerConfig> {

    private final io.github.resilience4j.circuitbreaker.CircuitBreaker delegate;

    public Resilience4jCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry, String name, CircuitBreaker.CircuitBreakerOptions ops) {
        this.delegate = circuitBreakerRegistry.circuitBreaker(name, convert(ops));
    }

    @Override
    public <T> T execute(Supplier<T> supplier) {
        try {
            return this.delegate.executeSupplier(supplier);
        } catch (CallNotPermittedException ex) {
            throw new CircuitBreakerOpenException();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            this.delegate.executeRunnable(runnable);
        } catch (CallNotPermittedException ex) {
            throw new CircuitBreakerOpenException();
        }
    }

    @Override
    public CircuitBreakerConfig convert(CircuitBreakerOptions ops) {
        return CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(ops.getRequestVolumeThreshold())
                .failureRateThreshold((float) 20)
                .waitDurationInOpenState(Duration.of(ops.getDelay(), ops.getDelayUnit()))
                .permittedNumberOfCallsInHalfOpenState(ops.getSuccessThreshold())
                .recordExceptions(ops.getFailOn())
                .ignoreExceptions(ops.getSkipOn())
                .build();
    }
}
