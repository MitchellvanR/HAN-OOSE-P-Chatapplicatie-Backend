package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;

public abstract class AbstractChatDAO extends ConnectionDAO{

    public abstract ArrayList<MessageDTO> getChatHistory(String senderId, String receiverId);

    public abstract void saveMessage(String senderId, String receiverId, String message);

    private MessageDTO formatMessage(String senderId, String receiverId, String content){
        // code
        return null;
    }
}
