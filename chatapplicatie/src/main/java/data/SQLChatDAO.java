package data;

import data.dto.MessageDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLChatDAO extends AbstractChatDAO {
    public ArrayList<MessageDTO> getChatMessages(String senderId, String receiverId) {
        try {
            String sql = "SELECT * FROM bericht WHERE verzenderId = ? AND ontvangerId = ?";
            PreparedStatement statement = createConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<MessageDTO> messages = new ArrayList<>();
            while(resultSet.next()) {
                messages.add(formatMessage(resultSet.getString("verzenderId"), resultSet.getString("ontvangerId"), resultSet.getString("bericht")));
            }
            return messages;
        } catch(Exception e) {
            throw SQLException();
        }
    }

    public void saveMessage(String senderId, String receiverId, String message){
        // code
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        MessageDTO messageDTO = new MessageDTO(senderId,receiverId, content);
        return messageDTO;
    }
}
