package com.mcmcnet.microsphere.security.core.userdetails;

import com.mcmcnet.microsphere.security.core.UserDetails;

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
     * @throws com.mcmcnet.microsphere.security.core.exception.UsernameNotFoundException 当账号不存在则抛出此异常
     */
    UserDetails loadByUsername(String username);

}
