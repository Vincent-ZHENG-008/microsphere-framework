package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttilerance.resilience4j.Resilience4jCircuitBreaker;
import com.microsphere.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class AnnotationFinderTest {

    @Test
    void adviceFinderTest() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(FaultToleranceConfiguration.class)) {
            final UserService userService = applicationContext.getBean(UserService.class);

            for (int i = 0; i < 30; i++) {
                try {
                    userService.run();
                } catch (CircuitBreakerOpenException ex) {
                    System.out.println("CircuitBreaker open succeed");
                } catch (RuntimeException ex) {
                    // ...
                }
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan(basePackages = "com.microsphere.faulttolerance.spring")
    private static class FaultToleranceConfiguration {

        @Bean
        public CircuitBreaker.CircuitBreakerOptions circuitBreakerOptions() {
            return new CircuitBreaker.CircuitBreakerOptions();
        }

        @Bean
        public CircuitBreaker circuitBreaker(CircuitBreaker.CircuitBreakerOptions options) {
            return new Resilience4jCircuitBreaker("Default", options);
        }

        @Bean
        public CircuitBreakerAdvicePostProcessor circuitBreakerAdvicePostProcessor(CircuitBreaker circuitBreaker) {
            return new CircuitBreakerAdvicePostProcessor(circuitBreaker);
        }

    }

}
