package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;

public class Chat {
    private User sender;
    private User receiver;
    private AbstractChatDAO chatDAO = new SQLChatDAO();

    public Chat(String senderId, String receiverId) {
        this.sender = new User(senderId);
        this.receiver = new User(receiverId);
    }

    public ArrayList<MessageDTO> getChatHistory() {
        ArrayList<MessageDTO> chatHistory = chatDAO.getChatHistory(sender.getId(), receiver.getId());
        return chatHistory;
    }

    public void sendMessage(String message){
        var senderId = sender.getId();
        var receiverId = receiver.getId();
        chatDAO.saveMessage(senderId, receiverId, message);
    }

    public String getSenderId() {
        return sender.getId();
    }

    public String getReceiverId() {
        return receiver.getId();
    }
}
