package com.mcmcnet.microsphere.security.core.userdetails;

import com.mcmcnet.microsphere.security.core.UserDetails;
import com.mcmcnet.microsphere.security.core.authentication.exception.UsernameNotFoundException;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
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
