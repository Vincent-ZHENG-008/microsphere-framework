package com.mcmcnet.microsphere.security.core;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface SecurityContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

}
