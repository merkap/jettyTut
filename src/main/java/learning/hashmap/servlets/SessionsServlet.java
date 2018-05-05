package learning.hashmap.servlets;

import com.google.gson.Gson;
import learning.hashmap.accounts.AccountService;
import learning.hashmap.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;
    private String contentType = "text/html;charset=utf-8";

    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            resp.setContentType(contentType);
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    //sign in
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (login == null || pass == null) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            return;
        }
        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(pass)) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.addSession(req.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.setContentType(contentType);
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            resp.setContentType(contentType);
            resp.getWriter().println("Goodbye!");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
