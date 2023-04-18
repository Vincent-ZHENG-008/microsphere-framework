package com.microsphere.security.core.authentication;

import com.microsphere.security.core.Authentication;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication);

    boolean support(Class<?> authentication);

}
