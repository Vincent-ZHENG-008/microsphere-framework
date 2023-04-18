package com.microsphere.security.core;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface SecurityContext {

    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

}
