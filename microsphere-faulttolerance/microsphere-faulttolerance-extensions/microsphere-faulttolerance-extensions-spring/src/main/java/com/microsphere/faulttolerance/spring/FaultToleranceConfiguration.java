package com.microsphere.faulttolerance.spring;

import com.microsphere.faulttolerance.spring.advice.FaultToleranceAdvicePostProcessor;
import com.microsphere.faulttolerance.spring.matcher.CircuitBreakerFaultToleranceMatcher;
import com.microsphere.faulttolerance.spring.matcher.RetryFaultToleranceMatcher;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
@Configuration(proxyBeanMethods = false)
public class FaultToleranceConfiguration {

    @Bean
    @ConditionalOnClass(CircuitBreaker.class)
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnClass(CircuitBreaker.class)
    public CircuitBreakerFaultToleranceMatcher circuitBreakerFaultToleranceMatcher(CircuitBreakerRegistry circuitBreakerRegistry) {
        return new CircuitBreakerFaultToleranceMatcher(circuitBreakerRegistry);
    }

    @Bean
    @ConditionalOnClass(Retry.class)
    public RetryRegistry retryRegistry() {
        return RetryRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnClass(Retry.class)
    public RetryFaultToleranceMatcher retryFaultToleranceMatcher(RetryRegistry retryRegistry) {
        return new RetryFaultToleranceMatcher(retryRegistry);
    }

    @Bean
    public FaultToleranceAdvicePostProcessor faultToleranceAdvicePostProcessor(Collection<FaultToleranceMatcher> faultToleranceMatchers) {
        return new FaultToleranceAdvicePostProcessor(faultToleranceMatchers);
    }

}
