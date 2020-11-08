package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jm.task.core.jdbc.util.Util.getMySQLConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static final String resetAutoIncrement = "ALTER TABLE tablename AUTO_INCREMENT = 1";
    private static final String queryCountUserId = "SELECT count(id) From users.user";
    private static final String queryExistSchema = "SELECT count(table_name) " +
            "FROM information_schema.tables " +
            "WHERE table_schema = 'users'";
    private static final Logger logger = Logger.getLogger("jm.task.core.jdbc.dao");
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
            //statement.executeUpdate(resetAutoIncrement);
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
            logger.log(Level.INFO, "Пользователь с именем: {0} добавлен в базу", new Object[] {name});
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }
    /*В тесте всегда значение один
    * в реальной базе значения всегда передаются разные,
    * ведь удалить мы хотим определенного персонажа*/
    public void removeUserById(long id) {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            String query = "DELETE FROM Users.user where user.id = " + id;

            ResultSet resultExistSchema = statement.executeQuery(queryExistSchema);
            resultExistSchema.next();
            if(resultExistSchema.getLong(1)>1) {
                ResultSet resultCountUser = statement.executeQuery(queryCountUserId);
                if (resultCountUser.getLong(1) < 1) {
                    logger.log(Level.WARNING, "Попытка удалить несуществующего персонажа");
                } else {
                    statement.executeUpdate(query);
                }
            }
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
                System.out.println(user.toString());
            }
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users.user;");
            //statement.executeUpdate(resetAutoIncrement);
        } catch (SQLException | ClassNotFoundException eSQL) {
            eSQL.printStackTrace();
        }
    }
}
