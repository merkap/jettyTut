package server.dbServiceJDBC.dao;

import server.dbServiceJDBC.dataSets.UsersDataSet;
import server.dbServiceJDBC.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {
    Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet getId(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id,
                resultSet -> {
                    resultSet.next();
                    return new UsersDataSet(resultSet.getLong(1),
                            resultSet.getString(2), resultSet.getString(3));
                });
    }

    public long getUserId(String name) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + name + "'",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong(1);
                });
    }

    public UsersDataSet getUser(String name) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + name + "'",
                resultSet -> {
                    resultSet.next();
                    return new UsersDataSet(resultSet.getLong(1),
                            resultSet.getString(2), resultSet.getString(3));
                });
    }

    public void insertUser(String name, String pass) throws SQLException {
        executor.execUpdate("insert into users (user_name, user_pass) values ('" + name + "','" + pass + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, " +
                "user_name varchar(256), user_pass varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}


