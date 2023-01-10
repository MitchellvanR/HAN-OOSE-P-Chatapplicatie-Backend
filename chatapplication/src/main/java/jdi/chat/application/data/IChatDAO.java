package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IChatDAO {

    ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException;

    void saveMessage(String message, String senderId, String chatId, String iv);

    void addUserToChat(String chatId, String userId);

    default MessageDTO formatMessage(String senderId, String content, String time, String iv){
        return new MessageDTO(senderId, content, time, iv);
    }

    String addChatToDatabase(String userId, String type);

    ArrayList<String> getUsersInChat(String chatId);

    String getChatType(String chatId);

    ArrayList<String> getChatIdFromUserId(String userId);
}
