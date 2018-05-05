package server.accountServer;

public class AccountServerImpl implements AccountServer {
    private int usersCount;
    private int usersLimit;

    public AccountServerImpl(int usersLimit) {
        this.usersCount = 0;
        this.usersLimit = usersLimit;
    }

    public void addUser() {
        usersCount += 1;
    }

    public void removeUser() {
        usersCount -= 1;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public int getUsersLimit() {
        return usersLimit;
    }

    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
