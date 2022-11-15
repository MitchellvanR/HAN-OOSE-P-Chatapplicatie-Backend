package src.main.data;

import src.main.data.dto.MessageDTO;

public abstract class AbstractChatDAO {
    public MessageDTO[] getChatHistory(String senderId, String receiverId){
        // code
        return null;
    }

    public void saveMessage(String senderId, String receiverId, String message){
        // code
    }

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        // code
        return null;
    }
}
