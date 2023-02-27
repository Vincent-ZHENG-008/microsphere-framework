package com.mcmcnet.microsphere.common.error.external;

import java.net.http.HttpClient;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class ExternalError extends RuntimeException {

    public ExternalError(String msg) {
        super(msg);
    }

    public ExternalError(String msg, Throwable cause) {
        super(msg, cause);
    }
}
