package com.microsphere.faulttolerance.spring;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.springframework.stereotype.Component;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since todo - since from which version
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    @CircuitBreaker
    public void run() {
        throw new RuntimeException("fast failed");
    }

}