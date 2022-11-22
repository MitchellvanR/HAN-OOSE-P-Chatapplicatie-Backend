package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabasePropertiesException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SQLChatDAO extends AbstractChatDAO {

    @Override
    public ArrayList<MessageDTO> getChatHistory(String senderId, String receiverId) {
        try {
            String sql = "SELECT * FROM Bericht WHERE (VerzenderId = ? AND OntvangerId = ?) OR (VerzenderId = ? AND OntvangerId = ?)";
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.setString(1, senderId);
            statement.setString(2, receiverId);
            statement.setString(3, receiverId);
            statement.setString(4, senderId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<MessageDTO> chatHistory = new ArrayList<>();
            while(resultSet.next()) {
                MessageDTO message = formatMessage(resultSet.getString("VerzenderId"), resultSet.getString("OntvangerId"), resultSet.getString("Bericht"));
                chatHistory.add(message);
            }
            return chatHistory;
        } catch (IOException e) {
            throw new DatabasePropertiesException();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void saveMessage(String senderId, String receiverId, String message){
        try {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));

        String url = properties.getProperty("connectionString");

        Connection connection = DriverManager.getConnection(url);
        String sql = "Insert into Bericht Values ('" + senderId + "', '" + receiverId + "', '" + message + "')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        MessageDTO messageDTO = new MessageDTO(senderId,receiverId, content);
        return messageDTO;
    }
}
