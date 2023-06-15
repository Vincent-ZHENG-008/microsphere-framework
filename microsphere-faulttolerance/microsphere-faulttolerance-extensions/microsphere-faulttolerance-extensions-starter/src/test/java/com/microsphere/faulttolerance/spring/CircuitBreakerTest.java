package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttolerance.core.resilience4j.Resilience4jCircuitBreaker;
import com.microsphere.faulttolerance.spring.circuitbreaker.CircuitBreakerFactory;
import com.microsphere.faulttolerance.spring.circuitbreaker.CircuitBreakerAdvicePostProcessor;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.time.temporal.ChronoUnit;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class CircuitBreakerTest {

    @Test
    void adviceFinderTest() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(FaultToleranceConfiguration.class)) {
            final UserService userService = applicationContext.getBean(UserService.class);

            for (int i = 0; i < 100; i++) {
                try {
                    userService.loadName();
                } catch (CircuitBreakerOpenException ex) {
                    System.out.println("CircuitBreaker open succeed");
                } catch (RuntimeException ex) {
                    // ...
                }
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    private static class FaultToleranceConfiguration {

        @Bean
        public CircuitBreakerRegistry circuitBreakerRegistry() {
            return CircuitBreakerRegistry.ofDefaults();
        }

        @Bean
        public CircuitBreakerFactory circuitBreakerFactory(CircuitBreakerRegistry circuitBreakerRegistry) {
            return (name, ops) -> new Resilience4jCircuitBreaker(circuitBreakerRegistry, name, ops);
        }

        @Bean
        public CircuitBreakerAdvicePostProcessor circuitBreakerAdvicePostProcessor(CircuitBreakerFactory circuitBreakerFactory) {
            return new CircuitBreakerAdvicePostProcessor(circuitBreakerFactory);
        }

    }

    public static class UserServiceImpl implements UserService {

        @Override
        @Fallback(fallbackMethod = "loadNameFallback")
        @CircuitBreaker(requestVolumeThreshold = 10, failureRatio = 0.2, delay = 1, delayUnit = ChronoUnit.SECONDS)
        public String loadName() {
            throw new RuntimeException("fast failed");
        }

        public static String loadNameFallback(Exception ex) {
            return "wangwu";
        }

    }

}
