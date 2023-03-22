package com.microsphere.security.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface UserDetails {

    String getUsername();

    String getPassword();

    boolean accountNonBlocked();

    boolean accountNonExpired();

}
