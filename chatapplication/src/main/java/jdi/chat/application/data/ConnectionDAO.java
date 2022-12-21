package jdi.chat.application.data;

import jakarta.inject.Inject;
import jdi.chat.application.data.exceptions.DatabaseRequestException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDAO {

    private Connection connection;

    public ConnectionDAO() {
        try {
            connection = createConnection();
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    private Connection createConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        return connection;
    }

    public Connection getConnection() { return connection; }

}
