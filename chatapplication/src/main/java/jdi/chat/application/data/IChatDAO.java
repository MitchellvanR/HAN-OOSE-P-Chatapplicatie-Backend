package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IChatDAO {

    ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException;

    void saveMessage(String message, String senderId, String chatId);

    void addUserToChat(String chatId, String userId);

    default MessageDTO formatMessage(String senderId, String content, String time){
        return new MessageDTO(senderId, content, time);
    }

    ArrayList<String> getChatIdFromUserId(String userId);
}
