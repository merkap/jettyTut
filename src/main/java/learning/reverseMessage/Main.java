package learning.reverseMessage;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.setCreator((req, resp) -> new WebSocketListener() {
                    Session session;

                    @Override
                    public void onWebSocketBinary(byte[] payload, int offset, int len) {

                    }

                    @Override
                    public void onWebSocketText(String message) {
                        try {
                            session.getRemote().sendString(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onWebSocketClose(int statusCode, String reason) {

                    }

                    @Override
                    public void onWebSocketConnect(Session session) {
                        this.session = session;
                    }

                    @Override
                    public void onWebSocketError(Throwable cause) {

                    }

                });
            }
        }), "/chat");
        server.setHandler(context);
        server.start();
        System.out.println("Server started");
        server.join();
    }

}
