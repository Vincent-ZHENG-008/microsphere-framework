package com.microsphere.security.core.authentication.exception;

import com.microsphere.common.error.external.ExternalError;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class UsernameNotFoundException extends ExternalError {

    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
