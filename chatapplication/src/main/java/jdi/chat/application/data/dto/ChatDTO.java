package jdi.chat.application.data.dto;

import java.util.List;

public class ChatDTO {
    private String chatId;
    private List<String> users;

    public ChatDTO(String chatId, List<String> users){
        this.chatId = chatId;
        this.users = users;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
