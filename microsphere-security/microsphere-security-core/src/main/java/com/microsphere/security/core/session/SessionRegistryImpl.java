package com.microsphere.security.core.session;

import com.microsphere.core.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class SessionRegistryImpl implements SessionRegistry {

    private final ConcurrentMap<Object, Set<String>> principals;

    private final Map<String, SessionInformation> sessionIds;

    public SessionRegistryImpl() {
        this.principals = new ConcurrentHashMap<>();
        this.sessionIds = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<SessionInformation> getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required must input");

        return Optional.ofNullable(this.sessionIds.get(sessionId));
    }

    @Override
    public void registryNewSession(String sessionId, Object principal) {
        if (sessionIds.containsKey(sessionId)) {
            removeSessionInformation(sessionId);
        }

        this.sessionIds.put(sessionId, new SessionInformation(principal, sessionId));
        this.principals.compute(principal, (key, sessions) -> {
            if (sessions == null) {
                sessions = new CopyOnWriteArraySet<>();
            }

            sessions.add(sessionId);
            return sessions;
        });
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        final SessionInformation information = this.sessionIds.remove(sessionId);
        if (information != null) {
            this.principals.computeIfPresent(information.getPrincipal(), (key, sessions) -> {
                sessions.remove(sessionId);

                if (sessions.isEmpty()) {
                    sessions = null;
                }
                return sessions;
            });
        }
    }
}
