package mx.edu.utez.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLConnection {

    private static Logger logger = LogManager.getLogger();
    private static ResourceBundle properties;

    private static String host;
    private static String port;
    private static String database;
    private static String user;
    private static String password;

    public static Connection getConnection() {
        if (properties == null) {
            properties = ResourceBundle.getBundle("MySQL");

            host = properties.getString("host");
            port = properties.getString("port");
            database = properties.getString("database");
            user = properties.getString("user");
            password = properties.getString("password");
        }

        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        logger.info(String.format("Conecting to %s", url));

        try {
            BasicDataSource datasource = new BasicDataSource();
            datasource.setDriverClassName("com.mysql.jdbc.Driver");
            datasource.setUsername(user);
            datasource.setPassword(password);
            datasource.setUrl(url);
            datasource.setMaxActive(125);
            datasource.setMaxIdle(25);
            datasource.setMaxWait(5000);

            Connection connection = datasource.getConnection();
            logger.info(String.format("Connection to %s established", url));

            return connection;
        } catch (SQLException ex) {
            logger.error(String.format("Connection to %s failed", url), ex);
            return null;
        }
    }
}