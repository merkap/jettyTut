package server.sessionService;

public interface SessionService {
    public void addSession(String sessionId, UserProfile userProfile);

    public UserProfile getUserBySessionId(String sessionId);

    public UserProfile deleteSession(String sessionId);
}