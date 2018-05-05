package server.main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.dbServiceJDBC.DBService;
import server.dbServiceJDBC.DBServiceImpl;
import server.serviceContext.ServiceContext;
import server.servlets.Exit;
import server.servlets.Mirror;
import server.servlets.SignIn;
import server.servlets.SignUp;
import server.sessionService.SessionService;
import server.sessionService.SessionServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception {

        DBService dbService = new DBServiceImpl();
        SessionService sessions = new SessionServiceImpl();
        ServiceContext serviceContext = new ServiceContext();

        serviceContext.add(DBService.class, dbService);
        serviceContext.add(SessionService.class, sessions);

//        dbService.addUser("admin","admin");
//        dbService.addUser("test","test");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Exit()), "/exit");
        context.addServlet(new ServletHolder(new Mirror()), "/mirror");
        context.addServlet(new ServletHolder(new SignUp(serviceContext)), "/signup");
        context.addServlet(new ServletHolder(new SignIn(serviceContext)), "/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("templates");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        int port = 8080;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
//        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');
        System.out.println("Server started");
        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();

    }
}
