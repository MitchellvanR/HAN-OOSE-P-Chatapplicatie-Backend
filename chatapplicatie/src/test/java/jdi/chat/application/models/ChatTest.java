package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ChatTest {
    private Chat sut;
    private AbstractChatDAO mockedChatDAO;
    private String senderId;
    private String receiverId;
    private String message;

    @BeforeEach
    public void setup() {
        this.sut = new Chat(senderId, receiverId);
        this.mockedChatDAO = Mockito.mock(SQLChatDAO.class);
        sut.setChatDAO(mockedChatDAO);
        this.senderId = "1";
        this.receiverId = "2";
        this.message = "Hello World";
    }

    @Test
    public void testSendMessage() {
        Mockito.doReturn(senderId).when(sut).getSenderId();
        Mockito.doReturn(receiverId).when(sut).getReceiverId();
        Mockito.doNothing().when(mockedChatDAO).saveMessage(senderId, receiverId, message);

        // Act
        sut.sendMessage(message);

        // Assert
        Mockito.verify(mockedChatDAO).saveMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

//    @Test
//    public void testGetChatHistory() {
//        // Arrange
//        Mockito.doNothing().when(mockedChatDAO).getChatHistory(senderId, receiverId);
//
//        // Act
//        sut.getChatHistory();
//
//        // Assert
//        Mockito.verify(mockedChatDAO).getChatHistory(Mockito.anyString(), Mockito.anyString());
//    }
}