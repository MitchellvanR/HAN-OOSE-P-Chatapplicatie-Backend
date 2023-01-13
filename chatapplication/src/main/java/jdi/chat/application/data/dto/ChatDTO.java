package jdi.chat.application.data.dto;

import java.util.ArrayList;

public class ChatDTO {
    private String chatId;
    private String latestMessage;

    public ChatDTO(String chatId,  String latestMessage) {
        this.chatId = chatId;
        this.latestMessage = latestMessage;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
