package data;

import data.dto.MessageDTO;

public class SQLChatDAO extends AbstractChatDAO {
    public MessageDTO[] getChatMessages(String senderId, String receiverId){
        // code
        return null;
    }

    public void saveMessage(String senderId, String receiverId, String message){
        String sql = "Insert into Bericht Values ('" + senderId + "', '" + receiverId + "', '" + message + "')";
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        // code
        return null;
    }
}
