package db.servlets;

import db.dbServiceJDBC.DBException;
import db.dbServiceJDBC.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUp extends HttpServlet {
    private final DBService dbService;
    private String contentType = "text/html;charset=utf-8";

    public SignUp(DBService dbService) {
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

        try {
            dbService.addUser(login, pass);
        } catch (DBException e) {
            e.printStackTrace();
        }
        resp.setContentType(contentType);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
