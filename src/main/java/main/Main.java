package main;

import accounts.AccountService;
import accounts.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.Exit;
import servlets.Mirror;
import servlets.UserServlet;

public class Main {
    public static void main(String[] args) throws Exception {

        AccountService accountService = new AccountService();

        accountService.addUser("admin", new UserProfile("admin"));

        Exit exit = new Exit();
        Mirror mirror = new Mirror();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(exit), "/exit");
        context.addServlet(new ServletHolder(mirror), "/mirror");
        context.addServlet(new ServletHolder(new UserServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new UserServlet(accountService)), "/api/v1/sessions");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("templates");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, context});

        int port = 8080;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        Server server = new Server(port);


        server.setHandler(handlerList);

        server.start();
        server.join();

    }
}
