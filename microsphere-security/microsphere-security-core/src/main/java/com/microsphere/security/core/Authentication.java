package com.microsphere.security.core;

import java.io.Serializable;
import java.security.Principal;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface Authentication extends Principal, Serializable {

    UserDetails getDetails();

    boolean isAuthenticated();

    void setAuthenticated(boolean isAuthenticated);

}
