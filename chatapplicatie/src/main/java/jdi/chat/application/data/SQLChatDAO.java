package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQLChatDAO extends AbstractChatDAO {

    @Override
    public ArrayList<MessageDTO> getChatHistory(String senderId, String receiverId) {
        try {
            String sql = "SELECT * FROM bericht WHERE (VerzenderId = ? AND OntvangerId = ?) OR (VerzenderId = ? AND OntvangerId = ?)";
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
            connection.close();
            return chatHistory;
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public void saveMessage(String senderId, String receiverId, String message){
        try {
            String sql = "Insert into Bericht Values ('" + senderId + "', '" + receiverId + "', '" + message + "')";
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        MessageDTO messageDTO = new MessageDTO(senderId,receiverId, content);
        return messageDTO;
    }
}
