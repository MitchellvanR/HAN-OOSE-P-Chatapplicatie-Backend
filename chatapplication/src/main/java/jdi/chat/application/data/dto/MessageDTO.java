package jdi.chat.application.data.dto;

public class MessageDTO {
    private String senderId;
    private String message;
    private String time;

    public MessageDTO(String senderId, String message, String time) {
        this.senderId = senderId;
        this.message = message;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getTime() {
        return time;
    }

    public void setDate(String time) {
        this.time = time;
    }
}
