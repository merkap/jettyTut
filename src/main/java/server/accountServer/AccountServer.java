package server.accountServer;

public interface AccountServer {
    public void addUser();

    public void removeUser();

    public int getUsersCount();

    public int getUsersLimit();

    public void setUsersLimit(int usersLimit);
}
