package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class testSendMessage {

    private Chat sut;
    private AbstractChatDAO mockedChatDAO;
    private String senderId;
    private String receiverId;
    private String message;

    @BeforeEach
    public void setup(){
        this.sut = new Chat(senderId, receiverId);
        this.mockedChatDAO = Mockito.mock(SQLChatDAO.class);
        sut.setChatDAO(mockedChatDAO);
        this.message = "Hello World";
        this.senderId = "1";
        this.receiverId = "2";
    }

    @Test
    void testSendMessage() {
        // Arrange
        Mockito.doNothing().when(mockedChatDAO).saveMessage(senderId, receiverId, message);

        // Act
        sut.sendMessage(message);

        // Assert
        Mockito.verify(mockedChatDAO).saveMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
