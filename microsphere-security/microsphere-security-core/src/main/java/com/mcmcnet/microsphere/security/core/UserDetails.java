package com.mcmcnet.microsphere.security.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface UserDetails {

    String getUsername();

    boolean accountNonBlocked();

    boolean accountNonExpired();

}
