package models;

import data.AbstractChatDAO;
import data.SQLChatDAO;
import data.dto.MessageDTO;

public class Chat {
    private User sender = new User();
    private User receiver = new User();
    private AbstractChatDAO chatDAO = new SQLChatDAO();

    public MessageDTO[] getChatHistory(Object sender, Object receiver){
        // code
        return null;
    }

    public void sendMessage(String message){
        // code
    }
}
