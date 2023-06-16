package com.microsphere.faulttolerance.spring;

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
class RetryMethodInterceptorTest {

    @Test
    void testing1() {
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

        @Override
        public String loadName() {
            return "zhangsan";
        }

        @Override
        @org.eclipse.microprofile.faulttolerance.Retry
        @Fallback(fallbackMethod = "fallback")
        public String loadRole() {
            System.out.println("loadRole invoke");

            throw new RuntimeException("fast failed");
        }

        private static String fallback(RuntimeException ex) {
            return "lisi";
        }
    }

}