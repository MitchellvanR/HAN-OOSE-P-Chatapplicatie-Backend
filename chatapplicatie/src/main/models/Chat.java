package src.main.models;

import src.main.data.AbstractChatDAO;
import src.main.data.dto.MessageDTO;

public class Chat {
    private User sender = new User();
    private User receiver = new User();
    private AbstractChatDAO chatDAO = new AbstractChatDAO();

    public MessageDTO[] getChatHistory(Object sender, Object receiver){
        // code
        return null;
    }

    public void sendMessage(String message){
        // code
    }
}
