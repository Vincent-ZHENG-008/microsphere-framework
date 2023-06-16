package com.microsphere.faulttolerance.spring;

import com.microsphere.common.logging.Log;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
class CircuitBreakerMethodInterceptorTest {

    @Test
    void testing1() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(InnerConfiguration.class)) {
            UserService userService = applicationContext.getBean(UserService.class);
            System.out.println(userService.loadName());
        }
    }

    @Test
    void testing2() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(InnerConfiguration.class)) {
            UserService userService = applicationContext.getBean(UserService.class);
            System.out.println(userService.loadRole());
        }
    }

    @EnableFaultTolerance
    @Configuration(proxyBeanMethods = false)
    private static class InnerConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

    }

    @Component
    public static class UserServiceImpl implements UserService {

        private static final Log log = Log.getFactory(UserServiceImpl.class);

        @Override
        @CircuitBreaker
        public String loadName() {
            return "zhangsan";
        }

        @Override
        @CircuitBreaker
        @Fallback(fallbackMethod = "fallback")
        public String loadRole() {
            throw new RuntimeException("fast failed");
        }

        private static String fallback(RuntimeException ex) {
            log.debug("fallback invoke.");


            return "lisi";
        }
    }

}