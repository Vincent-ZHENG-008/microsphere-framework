package com.microsphere.security.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface UserDetails {

    String getUsername();

    String getPassword();

    boolean accountNonBlocked();

    boolean accountNonExpired();

}
