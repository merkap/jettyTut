package server.sessionService;

import java.util.HashMap;
import java.util.Map;

public class SessionServiceImpl implements SessionService {
    private Map<String, UserProfile> sessionIdToProfile;

    public SessionServiceImpl() {
        sessionIdToProfile = new HashMap<>();
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public UserProfile deleteSession(String sessionId) {
        return sessionIdToProfile.remove(sessionId);
    }

}
