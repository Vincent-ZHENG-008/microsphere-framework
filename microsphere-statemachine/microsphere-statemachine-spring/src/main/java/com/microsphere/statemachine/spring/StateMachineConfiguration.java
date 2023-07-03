package com.microsphere.statemachine.spring;

import com.microsphere.statemachine.spring.support.StateMachineBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
@Configuration(proxyBeanMethods = false)
public class StateMachineConfiguration {

    @Bean
    public StateMachineBeanPostProcessor filteredBeanStateMachinePostProcessor() {
        return new StateMachineBeanPostProcessor();
    }

}
