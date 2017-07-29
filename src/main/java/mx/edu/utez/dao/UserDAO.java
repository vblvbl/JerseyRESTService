package mx.edu.utez.dao;

import mx.edu.utez.model.User;
import mx.edu.utez.util.MySQLConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static Logger logger = LogManager.getLogger();

    private final String SELECT_ALL_USERS = "SELECT id, name, lastname FROM users";
    private final String SELECT_USER_BY_ID = "SELECT id, name, lastname FROM users WHERE id = ?";
    private final String REGISTER_USER = "INSERT INTO users(name, lastname) VALUES (?, ?)";
    private final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private final String UPDATE_USER_BY_ID = "UPDATE users SET name = ?, lastname = ? WHERE id = ?";

    public boolean registerUser(User user) {
        boolean result = false;

        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastname());
            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error(e);
        }

        return result;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));

                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        return users;
    }

    public User getUserByID(int id) {
        User user = new User();

        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
            }
        } catch (SQLException e) {
            logger.error(e);
        }

        return user;
    }

    public boolean deleteUser(int id) {
        boolean result = false;

        try (Connection connection = MySQLConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.error(e);
        }

        return result;
    }
}
