package jdi.chat.application.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDAO {
    protected Connection connection;

    public Connection createConnection() throws Exception {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        return connection;
    }
}
