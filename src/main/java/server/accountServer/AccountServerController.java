package server.accountServer;

public class AccountServerController implements AccountServerControllerMBean {
    private final AccountServer accountServer;

    public AccountServerController(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    public int getUsers() {
        return accountServer.getUsersCount();
    }

    public int getUsersLimit() {
        return accountServer.getUsersLimit();
    }

    public void setUsersLimit(int usersLimit) {
        accountServer.setUsersLimit(usersLimit);
    }
}
