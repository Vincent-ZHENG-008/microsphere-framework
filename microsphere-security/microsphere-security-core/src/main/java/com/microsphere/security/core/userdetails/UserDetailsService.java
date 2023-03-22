package com.microsphere.security.core.userdetails;

import com.microsphere.security.core.UserDetails;
import com.microsphere.security.core.authentication.exception.UsernameNotFoundException;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface UserDetailsService {

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException 当账号不存在则抛出此异常
     */
    UserDetails loadByUsername(String username);

}
