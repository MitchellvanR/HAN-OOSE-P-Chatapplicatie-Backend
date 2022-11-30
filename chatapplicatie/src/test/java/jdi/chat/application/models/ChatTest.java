package jdi.chat.application.models;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;

public class ChatTest {
    private Chat sut;
    private AbstractChatDAO mockedChatDAO;
    private User mockedSender;
    private User mockedReceiver;
    private String senderId;
    private String receiverId;
    private String message;
    private ArrayList<MessageDTO> mockDTO;

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

//    @Test
//    void testGetChatHistory() {
//        // Arrange
//        Mockito.doReturn(mockDTO).when(mockedChatDAO).getChatHistory(senderId, receiverId);
//
//        // Act
//        sut.getChatHistory();
//
//        // Assert
//        Assertions.assertEquals(mockDTO, mockedChatDAO.getChatHistory(senderId, receiverId));
//    }

//    @Test
//    void testSendMessage() {
//        // Arrange
//        Mockito.doReturn(senderId).when(mockedSender).getId();
//        Mockito.doReturn(receiverId).when(mockedReceiver).getId();
//        Mockito.doNothing().when(mockedChatDAO).saveMessage(senderId, receiverId, message);
//
//        // Act
//        sut.sendMessage(message);
//
//        // Assert
//        Mockito.verify(mockedChatDAO).saveMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//    }
}