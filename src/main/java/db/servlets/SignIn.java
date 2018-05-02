package db.servlets;

import db.dbServiceJDBC.DBException;
import db.dbServiceJDBC.DBService;
import db.dbServiceJDBC.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignIn extends HttpServlet {
    private final DBService dbService;
    private String contentType = "text/html;charset=utf-8";

    public SignIn(DBService dbService) {
        this.dbService = dbService;
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
            profile = dbService.getUser(login);
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
        resp.getWriter().append("Authorized: ").append(login).append('\n');
    }
}
