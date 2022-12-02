package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;

public class Chat {
    private String chatId;
    private AbstractChatDAO chatDAO = new SQLChatDAO();

    public Chat(String Id) {
        chatId = Id;
    }

    public ArrayList<MessageDTO> getChatHistory() {
        ArrayList<MessageDTO> chatHistory = chatDAO.getChatHistory(chatId);
        return chatHistory;
    }

    public void sendMessage(String message, String senderId){
        chatDAO.saveMessage(message, senderId, chatId);
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setChatDAO(AbstractChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
}
