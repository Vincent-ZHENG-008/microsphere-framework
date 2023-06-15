package com.microsphere.faulttolerance.spring;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
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
class FaultToleranceAdvicePostProcessorTest {

    @Test
    void testing1() {
        try (GenericApplicationContext applicationContext = new AnnotationConfigApplicationContext(InnerConfiguration.class)) {
            UserService userService = applicationContext.getBean(UserService.class);
            System.out.println(userService.loadName());
        }
    }

    @Configuration(proxyBeanMethods = false)
    private static class InnerConfiguration {

        @Bean
        public FaultToleranceAdvicePostProcessor faultToleranceAdvicePostProcessor(){
            return new FaultToleranceAdvicePostProcessor();
        }

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

    }

    @Component
    public static class UserServiceImpl implements UserService {

        @Override
        @CircuitBreaker
        public String loadName() {
            return "zhangsan";
        }
    }

}