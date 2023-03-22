package com.microsphere.security.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface SecurityContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

}
