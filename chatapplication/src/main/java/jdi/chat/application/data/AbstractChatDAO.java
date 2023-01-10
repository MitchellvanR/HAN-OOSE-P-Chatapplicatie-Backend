package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;

import java.util.ArrayList;

public abstract class AbstractChatDAO {

    public abstract ArrayList<MessageDTO> getChatHistory(String chatId);

    public abstract void saveMessage(String message, String senderId, String chatId);

    public abstract void addUserToChat(String chatId, String userId);

    public MessageDTO formatMessage(String senderId, String content, String time){
        MessageDTO messageDTO = new MessageDTO(senderId, content, time);
        return messageDTO;
    }

    public ChatDTO formatChat(String chatId, String latestMessage) {
        ChatDTO chatDTO = new ChatDTO(chatId, latestMessage);
        return chatDTO;
    }

    public abstract ArrayList<String> getChatIdFromUserId(String userId);

    public abstract String getUserHelplineChatId (String userId);

    public abstract ArrayList<ChatDTO> getHelplineChats();
}
