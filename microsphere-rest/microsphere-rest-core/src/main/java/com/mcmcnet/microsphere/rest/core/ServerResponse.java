package com.mcmcnet.microsphere.rest.core;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface ServerResponse {

    ServerResponse.HttpStatus code();

    int raw();

    enum HttpStatus {

    }

}
