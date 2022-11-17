package models;

import data.AbstractChatDAO;
import data.SQLChatDAO;
import data.dto.MessageDTO;

public class Chat {
    private User sender = new User();
    private User receiver = new User();
    private AbstractChatDAO chatDAO = new SQLChatDAO();

    public MessageDTO[] getChatHistory(String senderId, String receiverId){
        // code
        return null;
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
