package models;

import data.AbstractChatDAO;
import data.SQLChatDAO;
import data.dto.MessageDTO;

import java.util.ArrayList;

public class Chat {
    private User sender = new User();
    private User receiver = new User();
    private SQLChatDAO chatDAO = new SQLChatDAO();

    public Chat(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public ArrayList<MessageDTO> getChatHistory() {
        return chatDAO.getChatMessages(sender.getId(), receiver.getId());
        // liveChat.connect();
    }

    public void sendMessage(String message){
        // code
    }
}
