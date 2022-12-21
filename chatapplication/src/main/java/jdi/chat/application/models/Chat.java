package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import java.util.List;

public class Chat {
    private String chatId;
    private AbstractChatDAO chatDAO = new SQLChatDAO();

    public Chat(String id) {
        chatId = id;
    }

    public Chat(){}

    public List<MessageDTO> getChatHistory() {
        return chatDAO.getChatHistory(chatId);
    }

    public void sendMessage(String message, String senderId){
        chatDAO.saveMessage(message, senderId, chatId);
    }

    public void addUserToChat(String userId) { chatDAO.addUserToChat(chatId, userId); }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) { this.chatId = chatId; }

    public void setChatDAO(AbstractChatDAO chatDAO) { this.chatDAO = chatDAO; }

    public void addChatToDatabase(String userId, String type){ chatDAO.addChatToDatabase(userId, type); }
}
