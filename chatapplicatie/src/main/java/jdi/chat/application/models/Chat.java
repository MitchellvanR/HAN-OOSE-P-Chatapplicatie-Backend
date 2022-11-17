package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;

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
