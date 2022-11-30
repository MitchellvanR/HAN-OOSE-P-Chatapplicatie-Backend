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
    private User mockedSender;
    private User mockedReceiver;
    private String senderId;
    private String receiverId;
    private String message;

    @BeforeEach
    void setup(){
        this.sut = new Chat(senderId, receiverId);
        this.mockedChatDAO = Mockito.mock(SQLChatDAO.class);
        this.mockedSender = Mockito.mock(User.class);
        this.mockedReceiver = Mockito.mock(User.class);
        sut.setChatDAO(mockedChatDAO);
        sut.setReceiver(mockedReceiver);
        sut.setSender(mockedSender);
        this.message = "Hello World";
        this.senderId = "1";
        this.receiverId = "2";
    }

    @Test
    void testSendMessage() {
        // Arrange
        Mockito.doReturn(senderId).when(mockedSender).getId();
        Mockito.doReturn(receiverId).when(mockedReceiver).getId();
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