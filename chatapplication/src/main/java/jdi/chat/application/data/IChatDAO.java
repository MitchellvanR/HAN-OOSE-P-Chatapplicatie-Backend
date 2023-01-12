package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IChatDAO {

    ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException;

    void saveMessage(String message, String senderId, String chatId, String iv);

    void addUserToChat(String chatId, String userId);

    default MessageDTO formatMessage(String senderId, String content, String time, String iv){
        return new MessageDTO(senderId, content, time, iv);
    }

    default ChatDTO formatChatList(String chatId, List<String> users){
        return new ChatDTO(chatId, users);
    }

    String addChatToDatabase(String userId, String type);

    ArrayList<String> getUsersInChat(String chatId) throws SQLException;

    String getChatType(String chatId) throws SQLException;

    ArrayList<ChatDTO> getChatIdFromUserId(String userId) throws SQLException;

    int getStandardChatWithUsers(String userId, String otherUserId);
}
