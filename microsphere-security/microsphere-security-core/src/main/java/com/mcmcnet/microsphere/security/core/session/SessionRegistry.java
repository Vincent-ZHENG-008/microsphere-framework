package com.mcmcnet.microsphere.security.core.session;

import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface SessionRegistry {

    Optional<SessionInformation> getSessionInformation(String sessionId);

    void registryNewSession(String sessionId, Object principal);

    void removeSessionInformation(String sessionId);

}
