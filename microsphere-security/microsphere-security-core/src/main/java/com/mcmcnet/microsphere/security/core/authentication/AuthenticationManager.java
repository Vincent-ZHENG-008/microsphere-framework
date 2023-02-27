package com.mcmcnet.microsphere.security.core.authentication;

import com.mcmcnet.microsphere.security.core.Authentication;
import com.mcmcnet.microsphere.security.core.exception.AuthenticationException;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface AuthenticationManager {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

}
