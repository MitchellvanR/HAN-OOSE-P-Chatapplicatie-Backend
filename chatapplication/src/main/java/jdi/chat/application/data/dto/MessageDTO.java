package jdi.chat.application.data.dto;

public class MessageDTO {
    private String senderId;
    private String message;
    private String time;
    private String iv;

    public MessageDTO(String senderId, String message, String time, String iv) {
        this.senderId = senderId;
        this.message = message;
        this.time = time;
        this.iv = iv;
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

    public String getIv() { return iv; }

    public void setIv(String iv) { this.iv = iv; }
}
