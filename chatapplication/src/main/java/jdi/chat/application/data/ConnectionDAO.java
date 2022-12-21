package jdi.chat.application.data;

import jakarta.inject.Inject;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDAO {
    @Inject
    protected Connection connection;

    public ConnectionDAO() {
        try {
            connection = createConnection();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    private Connection createConnection() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        return connection;
    }

    public Connection getConnection() { return connection; }

    public void setConnection(Connection connection) { this.connection = connection; }

}
