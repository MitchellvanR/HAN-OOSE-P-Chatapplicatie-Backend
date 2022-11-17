package controllers;

import data.dto.MessageDTO;
import models.Chat;

public class ChatController {
    private Chat[] chats;

    public MessageDTO[] getChatHistory(Object sender, Object receiver){
        // code
        return null;
    }

    public void sendMessage(String message){
        chats[0].sendMessage(message);
    }
}
