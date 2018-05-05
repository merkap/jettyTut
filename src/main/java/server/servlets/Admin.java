package server.servlets;

import server.accountServer.AccountServer;
import server.serviceContext.ServiceContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Admin extends HttpServlet {
    private ServiceContext serviceContext;

    public Admin(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(((AccountServer) serviceContext.get(AccountServer.class)).getUsersLimit());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
