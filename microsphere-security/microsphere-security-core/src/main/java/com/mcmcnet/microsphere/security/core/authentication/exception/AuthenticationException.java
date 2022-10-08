package com.mcmcnet.microsphere.security.core.authentication.exception;

import com.mcmcnet.microsphere.common.error.external.ExternalError;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
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
