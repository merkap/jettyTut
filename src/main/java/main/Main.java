package main;

import frontend.Exit;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            port = Integer.valueOf(args[0]);
        }
        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        Server server = new Server(port);

        Exit exit = new Exit();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(exit), "/exit");

        server.setHandler(context);

        server.start();
        server.join();

    }
}
