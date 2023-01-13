package jdi.chat.application.data.dto;

public class UserDTO {
    private String id;

    public UserDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
