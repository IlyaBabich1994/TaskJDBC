package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getMySQLConnection;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;
    public UserDaoJDBCImpl() {
            try {
                connection = getMySQLConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void createUsersTable() {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS `user` (" +
                    "  `ID` bigint NOT NULL AUTO_INCREMENT," +
                    "  `Name` varchar(20) DEFAULT NULL," +
                    "  `Surname` varchar(20) DEFAULT NULL," +
                    "  `Age` int DEFAULT NULL," +
                    "  PRIMARY KEY (`ID`)," +
                    "  UNIQUE KEY `ID` (`ID`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            String query = "DROP TABLE IF EXISTS User;";
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            String query  = "INSERT INTO `users`.`user`(" +
                    "`Name`," +
                    "`Surname`," +
                    "`Age`) " +
                    "VALUES(" +
                    "'" + name+ "', " +
                    "'"+ lastName+"', " +
                    age + ");";
            statement.executeUpdate(query);
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM 'users'.'user '" +
                    "WHERE 'user'.'id' = " + id);
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users.user");
            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.executeQuery("DELETE FROM 'user'.'users' where id != 0");
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }
}
