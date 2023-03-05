package com.mcmcnet.microsphere.security.core.authentication;

import com.mcmcnet.microsphere.security.core.Authentication;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication);

    boolean support(Class<?> authentication);

}
