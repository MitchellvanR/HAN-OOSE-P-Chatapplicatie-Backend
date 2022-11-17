package controllers;

import data.dto.MessageDTO;
import models.Chat;

import java.util.ArrayList;

public class ChatController {
    private Chat[] chats;

    public ArrayList<MessageDTO> getChatHistory(String senderId, String receiverId){
        Chat chat = new Chat(/*sender, receiver*/ "1", "2");
        ArrayList<MessageDTO> chatHistory = chat.getChatHistory();
        return chatHistory;
    }

    public void sendMessage(String message){
        // code
    }
}
