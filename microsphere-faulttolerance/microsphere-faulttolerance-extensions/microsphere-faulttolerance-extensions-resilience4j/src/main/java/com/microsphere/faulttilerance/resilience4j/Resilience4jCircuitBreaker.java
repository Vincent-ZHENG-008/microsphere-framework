package com.microsphere.faulttilerance.resilience4j;

import com.microsphere.faulttolerance.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import java.util.function.Supplier;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
public class Resilience4jCircuitBreaker implements CircuitBreaker {

    private final io.github.resilience4j.circuitbreaker.CircuitBreaker delegate;

    public Resilience4jCircuitBreaker(String name, CircuitBreaker.CircuitBreakerOptions ops) {
        this.delegate = io.github.resilience4j.circuitbreaker.CircuitBreaker.of(name, configConverter(ops));
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

    private static CircuitBreakerConfig configConverter(CircuitBreaker.CircuitBreakerOptions ops) {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold((float) ops.getFailureRatio())
                .minimumNumberOfCalls(ops.getRequestVolumeThreshold())
                .build();
    }
}
