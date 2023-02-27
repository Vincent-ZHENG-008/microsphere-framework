package com.mcmcnet.microsphere.security.core.userdetails;

import com.mcmcnet.microsphere.security.core.Authentication;
import com.mcmcnet.microsphere.security.core.UserDetails;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface AuthenticationUserDetailsService<T extends Authentication> {

    UserDetails loadUserDetails(T token);

}
