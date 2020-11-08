package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException,
            ClassNotFoundException {
        UserDaoJDBCImpl userConnect = new UserDaoJDBCImpl();
        userConnect.saveUser("Антоша", "Мантоша", (byte) 75);
        userConnect.dropUsersTable();
        userConnect.dropUsersTable();
        userConnect.createUsersTable();
    }
}
