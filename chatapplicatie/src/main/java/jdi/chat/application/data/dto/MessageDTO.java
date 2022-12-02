package jdi.chat.application.data.dto;

public class MessageDTO {
    private String senderId;
    private String message;

    public MessageDTO(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
