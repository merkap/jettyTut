package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, UserProfile> loginToProfile;
    private Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addUser(String userName, UserProfile userProfile) {
        if (loginToProfile.containsKey(userName))
//            return false;
            loginToProfile.put(userName, userProfile);
//        return true;
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String login) {
        return sessionIdToProfile.get(login);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public UserProfile getSession(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public UserProfile deleteSession(String sessionId) {
        return sessionIdToProfile.remove(sessionId);
    }
}
