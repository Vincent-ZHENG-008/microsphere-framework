package com.mcmcnet.microsphere.rest.core.filter;

import com.mcmcnet.microsphere.rest.core.ServerRequest;
import com.mcmcnet.microsphere.rest.core.ServerResponse;

import java.io.IOException;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface FilterChain {

    void doFilter(ServerRequest request, ServerResponse response) throws IOException;

}
