package server.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import server.accountServer.AccountServer;
import server.accountServer.AccountServerController;
import server.accountServer.AccountServerControllerMBean;
import server.accountServer.AccountServerImpl;
import server.chat.WebSocketChatServlet;
import server.dbServiceJDBC.DBService;
import server.dbServiceJDBC.DBServiceImpl;
import server.serviceContext.ServiceContext;
import server.servlets.*;
import server.sessionService.SessionService;
import server.sessionService.SessionServiceImpl;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        String startedMessage;
        int port;
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream("test.properties")) {
            properties.load(inputStream);
            port = Integer.valueOf(properties.getProperty("port", "8080"));
            startedMessage = properties.getProperty("startedMessage", "Server started");
        }


        AccountServer accountServer = new AccountServerImpl(10);
        AccountServerControllerMBean accountServerController = new AccountServerController(accountServer);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ServerManager:type=AccountServerController");
        mBeanServer.registerMBean(accountServerController, name);

        DBService dbService = new DBServiceImpl();
        SessionService sessions = new SessionServiceImpl();
        ServiceContext serviceContext = new ServiceContext();

        serviceContext.add(DBService.class, dbService);
        serviceContext.add(SessionService.class, sessions);
        serviceContext.add(AccountServer.class, accountServer);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Exit()), "/exit");
        context.addServlet(new ServletHolder(new Mirror()), "/mirror");
        context.addServlet(new ServletHolder(new SignUp(serviceContext)), "/signup");
        context.addServlet(new ServletHolder(new SignIn(serviceContext)), "/signin");
        context.addServlet(new ServletHolder(new AllRequestsServlet(serviceContext)), "/*");
        context.addServlet(new ServletHolder(new WebSocketChatServlet(serviceContext)), "/chat");
        context.addServlet(new ServletHolder(new Admin(serviceContext)), "/admin");


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("templates");
        resourceHandler.setDirectoriesListed(false);


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        System.out.println(startedMessage);
        server.join();

    }
}
