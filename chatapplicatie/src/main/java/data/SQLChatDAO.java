package data;

import data.dto.MessageDTO;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SQLChatDAO extends AbstractChatDAO {
    public MessageDTO[] getChatMessages(String senderId, String receiverId){
        // code
        return null;
    }

    public void saveMessage(String senderId, String receiverId, String message){
        try {
        Connection connection;
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

        String url = properties.getProperty("connectionString");

        connection = DriverManager.getConnection(url);
        String sql = "Insert into Bericht Values ('" + senderId + "', '" + receiverId + "', '" + message + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        // code
        return null;
    }
}
