package com.microsphere.faulttilerance.resilience4j;

import com.microsphere.faulttolerance.CircuitBreaker;
import com.microsphere.faulttolerance.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.eclipse.microprofile.faulttolerance.exceptions.FaultToleranceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class Resilience4jRetryTest {

    @Test
    void execute() {
        final Retry.RetryOptions ops = new Retry.RetryOptions();
        final Retry retry = new Resilience4jRetry("Test", RetryRegistry.ofDefaults(), ops);

        try {
            retry.execute(() -> {
                System.out.println("executor entry");

                throw new RuntimeException("fault");
            });
        } catch (FaultToleranceException ex) {
            System.out.println("FaultTolerance trigger succeed");
        }
    }

    @Test
    void executeWithCircuitBreaker() {
        final Retry.RetryOptions ops = new Retry.RetryOptions();
        final CircuitBreaker.CircuitBreakerOptions circuitBreakerOptions = new CircuitBreaker.CircuitBreakerOptions();

        final CircuitBreaker circuitBreaker = new Resilience4jCircuitBreaker("Test", circuitBreakerOptions);
        final Retry retry = new Resilience4jRetry("Test", RetryRegistry.ofDefaults(), ops);
        final AtomicInteger counter = new AtomicInteger(0);

        try {
            retry.execute(() -> {
                for (int i = 0; i < 31; i++) {
                    try {
                        circuitBreaker.execute(() -> {
                            throw new RuntimeException("fault");
                        });
                    } catch (CircuitBreakerOpenException ex) {
                        counter.incrementAndGet();

                        throw ex;
                    } catch (RuntimeException ex) {
                        // ...
                    }
                }
            });
        } catch (FaultToleranceException ex) {
            Assertions.assertEquals(1, counter.get());

            System.out.println("FaultTolerance trigger succeed");
        }
    }

}