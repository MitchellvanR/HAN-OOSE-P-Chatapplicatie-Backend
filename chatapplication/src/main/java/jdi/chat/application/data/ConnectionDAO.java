package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDAO {
    protected Connection connection;
    private static final ConnectionDAO instance = new ConnectionDAO();

    public ConnectionDAO() {
        try {
            connection = createConnection();
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    private Connection createConnection() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        return connection;
    }

    public static ConnectionDAO getInstance() { return instance; }
    public Connection getConnection() { return connection; }
}
