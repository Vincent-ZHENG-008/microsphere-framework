package com.microsphere.faulttolerance.spring.retry;

import com.microsphere.faulttolerance.core.resilience4j.Resilience4jRetry;
import com.microsphere.faulttolerance.spring.UserService;
import io.github.resilience4j.retry.RetryRegistry;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class RetryTest {

    @Test
    void adviceFinderTest() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(RetryConfiguration.class)) {
            final UserService userService = applicationContext.getBean(UserService.class);

            try {
                final String name = userService.loadName();
                System.out.println(name);
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan("com.microsphere.faulttolerance.spring.retry")
    private static class RetryConfiguration {

        @Bean
        public RetryFactory retryFactory(Optional<RetryRegistry> retryRegistryOptional) {
            return ops -> new Resilience4jRetry("Default", retryRegistryOptional.orElse(RetryRegistry.ofDefaults()), ops);
        }

        @Bean
        public RetryAdvicePostProcessor retryAdvicePostProcessor(RetryFactory retryFactory) {
            return new RetryAdvicePostProcessor(retryFactory);
        }

    }

    @Component
    public static class UserServiceImpl implements UserService {

        @Retry
        @Override
        @Fallback(fallbackMethod = "loadNameFallback")
        public String loadName() {
            System.out.println("loadName");

            throw new RuntimeException("fast failed");
        }

        public static String loadNameFallback(Exception ex) {
            System.out.println("loadNameFallback trigger");

            return "wangwu";
        }

    }

}
