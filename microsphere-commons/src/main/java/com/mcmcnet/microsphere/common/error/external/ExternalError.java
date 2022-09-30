package com.mcmcnet.microsphere.common.error.external;

import java.net.http.HttpClient;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
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
