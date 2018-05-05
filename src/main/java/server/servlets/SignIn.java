package server.servlets;

import server.dbServiceJDBC.DBException;
import server.dbServiceJDBC.DBService;
import server.dbServiceJDBC.dataSets.UsersDataSet;
import server.serviceContext.ServiceContext;
import server.sessionService.SessionService;
import server.sessionService.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignIn extends HttpServlet {
    private final ServiceContext serviceContext;
    private String contentType = "text/html;charset=utf-8";

    public SignIn(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
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

        UsersDataSet profile = null;
        try {
            profile = ((DBService) serviceContext.get(DBService.class)).getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (profile == null || !profile.getPass().equals(pass)) {
            resp.setContentType(contentType);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType(contentType);
        resp.setStatus(HttpServletResponse.SC_OK);

        ((SessionService) serviceContext.get(SessionService.class))
                .addSession(req.getSession().getId(), new UserProfile(profile.getName(), profile.getPass()));
        resp.getWriter().append("Authorized: ").append(login).append('\n');
    }
}
