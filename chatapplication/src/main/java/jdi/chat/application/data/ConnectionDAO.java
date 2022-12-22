package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDAO {
    protected Connection connection;
    private static final ConnectionDAO instance = new ConnectionDAO();

    public ConnectionDAO() {
        try {
            connection = createConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseRequestException(e);
        }
    }

    private Connection createConnection() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        return connection;
    }

    public static ConnectionDAO getInstance() { return instance; }
    public Connection getConnection() { return connection; }
}
