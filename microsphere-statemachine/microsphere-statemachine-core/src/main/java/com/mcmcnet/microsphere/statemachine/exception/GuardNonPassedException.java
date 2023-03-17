package com.mcmcnet.microsphere.statemachine.exception;

/**
 * #: todo - what is this
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 */

public class GuardNonPassedException extends RuntimeException {

    public GuardNonPassedException(String message) {
        super(message);
    }

    public GuardNonPassedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuardNonPassedException(Throwable cause) {
        super(cause);
    }

    public GuardNonPassedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}