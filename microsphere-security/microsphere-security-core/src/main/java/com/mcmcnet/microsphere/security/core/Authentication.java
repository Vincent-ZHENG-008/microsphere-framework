package com.mcmcnet.microsphere.security.core;

import java.io.Serializable;
import java.security.Principal;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface Authentication extends Principal, Serializable {

    UserDetails getDetails();

    boolean isAuthenticated();

}
