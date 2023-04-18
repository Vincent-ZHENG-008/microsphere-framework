package com.microsphere.security.core.authentication.exception;

import com.microsphere.common.error.external.ExternalError;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class AuthenticationException extends ExternalError {

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
