package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection {

    protected static Connection connection;

    protected static void connectToDatabase() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Properties properties = new Properties();
                properties.load(SQLChatDAO.class.getClassLoader().getResourceAsStream("database.properties"));
                String url = properties.getProperty("connectionString");
                connection = DriverManager.getConnection(url);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                throw new DatabaseRequestException(e);
            }
        }
    }

    protected static void setConnection(Connection connection) {
        SQLConnection.connection = connection;
    }
}
