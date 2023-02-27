package com.mcmcnet.microsphere.security.core.authentication.exception;

import com.mcmcnet.microsphere.common.error.external.ExternalError;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class AuthenticationException extends ExternalError {

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
