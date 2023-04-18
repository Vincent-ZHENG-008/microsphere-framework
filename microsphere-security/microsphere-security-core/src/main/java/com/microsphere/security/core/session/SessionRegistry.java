package com.microsphere.security.core.session;

import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 0.0.1
 **/
public interface SessionRegistry {

    Optional<SessionInformation> getSessionInformation(String sessionId);

    void registryNewSession(String sessionId, Object principal);

    void removeSessionInformation(String sessionId);

}
