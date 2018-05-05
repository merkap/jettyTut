package server.servlets;

import server.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("username") != null) {
            PageGenerator.instance().pageVariables
                    .put("username", request.getSession().getAttribute("username"));
        }


        response.getWriter().println(server.templater.PageGenerator.instance()
                .getPage("page.html", PageGenerator.instance().pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws ServletException, IOException {
//        Map<String, Object> pageVariables = createPageVariablesMap(request);
//
//        String message = request.getParameter("message");
//
//        response.setContentType("text/html;charset=utf-8");
//
//        if (message == null || message.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        } else {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//        pageVariables.put("message", message == null ? "" : message);
//        response.getWriter().println(server.templater.PageGenerator.instance().getPage("page.html", pageVariables));
//    }

//    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
//        Map<String, Object> pageVariables = new HashMap<>();
//        String username = request.getParameter("username");
//        username = username == null ? "Anonymous" : username;
//        pageVariables.put("URL", request.getRequestURL().toString());
//        pageVariables.put("pathInfo", request.getPathInfo());
//        pageVariables.put("sessionId", request.getSession().getId());
//        pageVariables.put("username", request.getSession().getAttribute("username"));
//        System.out.println(request.getSession().getAttribute("username"));
//        return pageVariables;
//
//    }
}
