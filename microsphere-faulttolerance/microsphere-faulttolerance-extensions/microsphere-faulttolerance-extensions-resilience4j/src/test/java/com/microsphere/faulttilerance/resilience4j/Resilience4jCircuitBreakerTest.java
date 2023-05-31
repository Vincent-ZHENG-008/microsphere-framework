package com.microsphere.faulttilerance.resilience4j;

import com.microsphere.faulttolerance.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class Resilience4jCircuitBreakerTest {

    static CircuitBreaker.CircuitBreakerOptions ops = null;

    @BeforeAll
    static void before() {
        ops = new CircuitBreaker.CircuitBreakerOptions();
    }

    @Test
    void testDecorate() {
        CircuitBreaker circuitBreaker = new Resilience4jCircuitBreaker("Test", ops);

        for (int i = 0; i < 21; i++) {
            try {
                circuitBreaker.execute(() -> {
                    throw new RuntimeException("fault");
                });
            }catch (RuntimeException ex) {
                if (i == 20) {
                    Assertions.assertTrue(ex instanceof CallNotPermittedException);
                }
            }
        }
    }

}