package server.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.chat.WebSocketChatServlet;
import server.dbServiceJDBC.DBService;
import server.dbServiceJDBC.DBServiceImpl;
import server.serviceContext.ServiceContext;
import server.servlets.*;
import server.sessionService.SessionService;
import server.sessionService.SessionServiceImpl;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        DBService dbService = new DBServiceImpl();
        SessionService sessions = new SessionServiceImpl();
        ServiceContext serviceContext = new ServiceContext();

        serviceContext.add(DBService.class, dbService);
        serviceContext.add(SessionService.class, sessions);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Exit()), "/exit");
        context.addServlet(new ServletHolder(new Mirror()), "/mirror");
        context.addServlet(new ServletHolder(new SignUp(serviceContext)), "/signup");
        context.addServlet(new ServletHolder(new SignIn(serviceContext)), "/signin");
        context.addServlet(new ServletHolder(new AllRequestsServlet()), "/*");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("templates");
        resourceHandler.setDirectoriesListed(false);


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        int port = 8080;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
//        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');
        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        server.join();

    }
}
