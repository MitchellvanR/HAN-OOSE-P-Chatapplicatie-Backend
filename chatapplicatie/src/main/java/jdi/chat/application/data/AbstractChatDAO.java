package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;

import java.util.ArrayList;

public abstract class AbstractChatDAO {

    public abstract ArrayList<MessageDTO> getChatHistory(String chatId);

    public abstract void saveMessage(String message, String senderId, String chatId);

    public MessageDTO formatMessage(String senderId, String content, String time){
        MessageDTO messageDTO = new MessageDTO(senderId, content, time);
        return messageDTO;
    }
}
