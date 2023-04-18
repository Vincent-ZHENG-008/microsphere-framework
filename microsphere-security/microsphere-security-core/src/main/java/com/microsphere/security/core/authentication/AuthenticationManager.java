package com.microsphere.security.core.authentication;

import com.microsphere.security.core.authentication.exception.AuthenticationException;
import com.microsphere.security.core.Authentication;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface AuthenticationManager {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

}
