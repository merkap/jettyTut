package server.dbServiceJDBC;

import server.dbServiceJDBC.dataSets.UsersDataSet;

public interface DBService {
    UsersDataSet getUser(long id) throws DBException;

    UsersDataSet getUser(String name) throws DBException;

    long addUser(String name, String pass) throws DBException;

    void cleanUp() throws DBException;

    void printConnectionInfo();


}

