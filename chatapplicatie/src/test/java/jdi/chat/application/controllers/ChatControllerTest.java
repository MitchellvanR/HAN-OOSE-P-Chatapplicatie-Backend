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
    private Chat chatMock;
    private ArrayList<Chat> chatList;
    private String senderId;
    private String receiverId;

    @BeforeEach
    public void setup() {
        this.sut = new ChatController();
        this.chatMock = Mockito.mock(Chat.class);
        this.chatList = new ArrayList<>();
        this.chatList.add(chatMock);
        this.senderId = "1";
        this.receiverId = "2";
    }

    @Test
    public void returnArrayListMessages() {
        // Arrange
        Mockito.doNothing().when(chatMock).getChatHistory();
        Mockito.doReturn(senderId).when(chatMock).getSenderId();
        Mockito.doReturn(receiverId).when(chatMock).getReceiverId();

        // Act
        sut.getChatHistory(senderId, receiverId);

        // Assert
        Mockito.verify(chatMock).getChatHistory();
    }
}