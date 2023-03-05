package com.mcmcnet.microsphere.rest.core.filter;

import com.mcmcnet.microsphere.common.container.context.Lifecycle;
import com.mcmcnet.microsphere.rest.core.ServerRequest;
import com.mcmcnet.microsphere.rest.core.ServerResponse;

import java.io.IOException;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface RestFilter extends Lifecycle {

    void doFilter(ServerRequest request, ServerResponse response, FilterChain chain) throws IOException;

}
