package data;

import data.dto.MessageDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLChatDAO extends AbstractChatDAO {

    @Override
    public ArrayList<MessageDTO> getChatHistory(String senderId, String receiverId) {
        try {
            String sql = "SELECT * FROM bericht WHERE verzenderId = ? AND ontvangerId = ?";
            PreparedStatement statement = createConnection().prepareStatement(sql);
            statement.setString(1, senderId);
            statement.setString(2, receiverId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<MessageDTO> chatHistory = new ArrayList<>();
            while(resultSet.next()) {
                MessageDTO message = (formatMessage(resultSet.getString("verzenderId"), resultSet.getString("ontvangerId"), resultSet.getString("bericht")));
                chatHistory.add(message);
            }
            return chatHistory;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveMessage(String senderId, String receiverId, String message){
        // code
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        MessageDTO messageDTO = new MessageDTO(senderId,receiverId, content);
        return messageDTO;
    }
}
