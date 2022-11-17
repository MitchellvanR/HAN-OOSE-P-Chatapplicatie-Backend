package controllers;

import data.dto.MessageDTO;
import models.Chat;

import java.util.ArrayList;

public class ChatController {
    private Chat[] chats;

    public ArrayList<MessageDTO> getChatHistory(Object sender, Object receiver){
        Chat chat = new Chat(sender.id, receiver.id);
        return chat.getChatHistory();
    }

    public void sendMessage(String message){
        // code
    }
}
