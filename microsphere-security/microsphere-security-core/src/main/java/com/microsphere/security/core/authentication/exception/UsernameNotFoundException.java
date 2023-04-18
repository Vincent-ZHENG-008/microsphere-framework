package com.microsphere.security.core.authentication.exception;

import com.microsphere.common.error.external.ExternalError;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class UsernameNotFoundException extends ExternalError {

    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
