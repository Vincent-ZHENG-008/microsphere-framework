package com.mcmcnet.microsphere.security.core.session;

import java.util.Optional;

/**
 * todo...
 *
 * @author zhengwenhuan@gdmcmc.cn
 * @since todo...
 **/
public interface SessionRegistry {

    Optional<SessionInformation> getSessionInformation(String sessionId);

    void registryNewSession(String sessionId, Object principal);

    void removeSessionInformation(String sessionId);

}
