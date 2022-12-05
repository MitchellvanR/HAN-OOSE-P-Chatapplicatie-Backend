package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;

public abstract class AbstractChatDAO {

    public abstract ArrayList<MessageDTO> getChatHistory(String chatId);

    public abstract void saveMessage(String senderId, String receiverId, String message);

    public MessageDTO formatMessage(String senderId, String content){
        MessageDTO messageDTO = new MessageDTO(senderId, content);
        return messageDTO;
    }
}
