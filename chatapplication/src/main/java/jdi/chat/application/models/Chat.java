package jdi.chat.application.models;

import jdi.chat.application.data.IChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;

import java.util.ArrayList;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String chatId;
    private static IChatDAO chatDAO = new SQLChatDAO();
    private String chatType;
    private String latestMessage;

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

    public static ArrayList<String> getChatIdFromUserId(String userId) throws SQLException {
        return chatDAO.getChatIdFromUserId(userId);
    }

    public static String getUserHelplineChatId(String userId) {
        return chatDAO.getUserHelplineChatId(userId);
    }

    public static ArrayList<ChatDTO> getHelplineChats() {
        return chatDAO.getHelplineChats();
    }

    public void addChatToDatabase(String userId, String type){ setChatId(chatDAO.addChatToDatabase(userId, type)); }

    public ArrayList<String> getUsers() throws SQLException { return chatDAO.getUsersInChat(getChatId()); }

    public void defineChatType() throws SQLException { chatType = chatDAO.getChatType(chatId); }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) { this.chatId = chatId; }

    public static void setChatDAO(IChatDAO chatDAO) { Chat.chatDAO = chatDAO; }

    public String getChatType() { return chatType; }

    public String getLatestMessage() { return latestMessage; }

    public void setLatestMessage(String latestMessage) { this.latestMessage = latestMessage; }
}
