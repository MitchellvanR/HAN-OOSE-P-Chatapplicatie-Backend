package jdi.chat.application.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageDTOTest {
    private MessageDTO sut;
    private String sender;
    private String message;
    private String time;
    private String iv;

    @BeforeEach
    void setup(){
        sut = new MessageDTO("3", "Hey", "12:00", "123");
        sender = "1";
        message = "Hello test world";
        time = "09:00";
        iv = "456";
    }

    @Test
    void settersAndGettersTest(){
        // Act
        sut.setSenderId(sender);
        sut.setMessage(message);
        sut.setDate(time);
        sut.setIv(iv);

        // Assert
        assertEquals(sut.getMessage(), message);
        assertEquals(sut.getSenderId(), sender);
        assertEquals(sut.getTime(), time);
        assertEquals(sut.getIv(), iv);
    }
}
