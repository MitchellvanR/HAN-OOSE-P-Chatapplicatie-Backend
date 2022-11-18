package models;

import data.AbstractChatDAO;
import data.SQLChatDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class testSendMessage {

    private Chat sut;
    private AbstractChatDAO mockedChatDAO;
    private String senderId = "1";
    private String receiverId = "2";

    @BeforeEach
    public void setup(){
        this.sut = new Chat(senderId, receiverId);
        this.mockedChatDAO = Mockito.mock(SQLChatDAO.class);
        sut.setChatDAO(mockedChatDAO);
    }

    @Test
    void sendMessageTest() {
        // Arrange
        String message = "Hello World";
        Mockito.doNothing().when(mockedChatDAO).saveMessage(senderId, receiverId, message);

        // Act
        sut.sendMessage(message);

        // Assert
        Mockito.verify(mockedChatDAO).saveMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
