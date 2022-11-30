package jdi.chat.application.controllers;

import jdi.chat.application.data.AbstractChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatControllerTest {
    private ChatController sut;
    private ArrayList<Chat> chatList;
    private Chat mockedChat;
    private String message;
    private String senderId;
    private String receiverId;

    @BeforeEach
    public void setup() {
        this.sut = new ChatController();
        this.mockedChat = Mockito.mock(Chat.class);
        this.chatList = new ArrayList<>();
        this.chatList.add(mockedChat);
        this.message = "Hello World";
        this.senderId = "1";
        this.receiverId = "2";
    }

    @Test
    public void testSendMessageChatController() {
        // Arrange
        sut.setChats(chatList);
        Mockito.doNothing().when(mockedChat).sendMessage(message);
        Mockito.doReturn(senderId).when(mockedChat).getSenderId();
        Mockito.doReturn(receiverId).when(mockedChat).getReceiverId();

        // Act
        sut.sendMessage(senderId, receiverId, message);

        // Assert
        Mockito.verify(mockedChat).sendMessage(Mockito.anyString());
    }

//    @Test
//    public void returnArrayListMessages() {
//        // Arrange
//        Mockito.doNothing().when(chatMock).getChatHistory();
//        Mockito.doReturn(senderId).when(chatMock).getSenderId();
//        Mockito.doReturn(receiverId).when(chatMock).getReceiverId();
//
//        // Act
//        sut.getChatHistory(senderId, receiverId);
//
//        // Assert
//        Mockito.verify(chatMock).getChatHistory();
//    }
}