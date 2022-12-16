package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private String chatId;
    private static AbstractChatDAO chatDAO = new SQLChatDAO();
    private boolean helpline;
    private String latestMessage;

    public Chat (String id) {
        chatId = id;
    }

    public Chat (String id, boolean helpline) {
        this.chatId = id;
        this.helpline = helpline;
    }

    public List<MessageDTO> getChatHistory() {
        return chatDAO.getChatHistory(chatId);
    }

    public void sendMessage(String message, String senderId){
        chatDAO.saveMessage(message, senderId, chatId);
    }

    public void addUserToChat(String userId) { chatDAO.addUserToChat(chatId, userId); }

    public void findLatestMessage() {
        setLatestMessage(chatDAO.findLatestMessage(chatId));
    }

    public static ArrayList<String> getChatIdFromUserId(String userId) {
        return chatDAO.getChatIdFromUserId(userId);
    }

    public static String getUserHelplineChatId(String userId) {
        return chatDAO.getUserHelplineChatId(userId);
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) { this.chatId = chatId; }

    public void setChatDAO(AbstractChatDAO chatDAO) { this.chatDAO = chatDAO; }

    public boolean isHelpline() {
        return helpline;
    }

    public void setHelpline(boolean helpline) {
        this.helpline = helpline;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
