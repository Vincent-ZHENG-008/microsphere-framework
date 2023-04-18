package com.microsphere.security.core.userdetails;

import com.microsphere.security.core.UserDetails;
import com.microsphere.security.core.Authentication;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface AuthenticationUserDetailsService<T extends Authentication> {

    UserDetails loadUserDetails(T token);

}
