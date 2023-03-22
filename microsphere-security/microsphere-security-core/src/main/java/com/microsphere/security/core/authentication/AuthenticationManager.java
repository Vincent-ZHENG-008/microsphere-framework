package com.microsphere.security.core.authentication;

import com.microsphere.security.core.authentication.exception.AuthenticationException;
import com.microsphere.security.core.Authentication;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface AuthenticationManager {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

}
