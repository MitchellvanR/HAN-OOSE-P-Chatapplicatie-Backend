package jdi.chat.application.data.dto;

public class ChatDTO {
    private String chatId;
    private String type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }
}
