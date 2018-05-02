package hashmap.main;

import hashmap.accounts.AccountService;
import hashmap.accounts.UserProfile;
import hashmap.servlets.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        AccountService accountService = new AccountService();

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new Exit()), "/exit");
        context.addServlet(new ServletHolder(new Mirror()), "/mirror");
        context.addServlet(new ServletHolder(new SignUp(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignIn(accountService)), "/signin");
        context.addServlet(new ServletHolder(new UserServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");

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
