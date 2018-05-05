package learning.hashmap.servlets;

import learning.hashmap.accounts.AccountService;
import learning.hashmap.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignIn extends HttpServlet {
    private final AccountService accountService;
    private String contentType = "text/html;charset=utf-8";

    public SignIn(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(pass)) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType(contentType);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append("Authorized: ").append(login).append('\n');
    }
}
