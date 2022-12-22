package jdi.chat.application.models;

import jdi.chat.application.data.IChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;

import java.sql.SQLException;
import java.util.List;

public class Chat {
    private String chatId;
    private IChatDAO chatDAO = new SQLChatDAO();

    public Chat(String id) {
        chatId = id;
    }

    public List<MessageDTO> getChatHistory() {
        try {
            return chatDAO.getChatHistory(chatId);
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    public void sendMessage(String message, String senderId, String iv){
        chatDAO.saveMessage(message, senderId, chatId, iv);
    }

    public void addUserToChat(String userId) { chatDAO.addUserToChat(chatId, userId); }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) { this.chatId = chatId; }

    public void setChatDAO(IChatDAO chatDAO) { this.chatDAO = chatDAO; }
}
