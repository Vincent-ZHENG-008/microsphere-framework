package com.mcmcnet.microsphere.rest.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface ServerResponse {

    ServerResponse.HttpStatus code();

    int raw();

    enum HttpStatus {

    }

}
