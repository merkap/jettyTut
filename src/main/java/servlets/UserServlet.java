package servlets;

import accounts.AccountService;

import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet {
    private final AccountService accountService;

    public UserServlet(AccountService accountService) {
        this.accountService = accountService;
    }
}
