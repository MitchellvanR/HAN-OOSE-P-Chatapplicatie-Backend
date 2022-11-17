package models;

import data.AbstractChatDAO;
import data.SQLChatDAO;
import data.dto.MessageDTO;

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
        // liveChat.connect();
    }

    public void sendMessage(String message){
      String senderId = sender.getId();
      String receiverId = receiver.getId();
      chatDAO.saveMessage(senderId, receiverId, message);
      if (receiver.getOnline()){
          sender.sendMessage(message);
      }
    }
}
