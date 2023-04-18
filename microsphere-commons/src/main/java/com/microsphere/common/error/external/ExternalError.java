package com.microsphere.common.error.external;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public class ExternalError extends RuntimeException {

    public ExternalError(String msg) {
        super(msg);
    }

    public ExternalError(String msg, Throwable cause) {
        super(msg, cause);
    }
}
