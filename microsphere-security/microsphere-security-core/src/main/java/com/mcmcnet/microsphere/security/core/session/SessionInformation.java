package com.mcmcnet.microsphere.security.core.session;

import java.io.Serializable;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class SessionInformation implements Serializable {

    private final Object principal;

    private final String sessionId;

    public SessionInformation(Object principal, String sessionId) {
        this.principal = principal;
        this.sessionId = sessionId;
    }

    public Object getPrincipal() {
        return principal;
    }

    public String getSessionId() {
        return sessionId;
    }
}
