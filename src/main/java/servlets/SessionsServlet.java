package servlets;

import accounts.AccountService;

import javax.servlet.http.HttpServlet;

public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;

    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }
}
