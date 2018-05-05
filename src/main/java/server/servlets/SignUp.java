package server.servlets;

import server.dbServiceJDBC.DBException;
import server.dbServiceJDBC.DBService;
import server.serviceContext.ServiceContext;
import server.sessionService.SessionService;
import server.sessionService.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends HttpServlet {
    private final ServiceContext serviceContext;
    private String contentType = "text/html;charset=utf-8";

    public SignUp(ServiceContext serviceContext) {
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

        try {
            ((DBService) serviceContext.get(DBService.class)).addUser(login, pass);
            ((SessionService) serviceContext.get(SessionService.class))
                    .addSession(req.getSession().getId(), new UserProfile(login, pass));
        } catch (DBException e) {
            e.printStackTrace();
        }
        resp.setContentType(contentType);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append("Hi, " + login + "!<br/>Your registration is successful!");
    }
}
