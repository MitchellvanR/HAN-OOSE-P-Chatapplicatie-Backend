package jdi.chat.application.controllers;

import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {
    private ChatController sut;
    private Chat mockedChat;
    private String message;
    private String chatId;
    private String senderId;
    private ArrayList<MessageDTO> mockDTO;
    @BeforeEach
    void setup() {
        this.sut = new ChatController();

        this.mockedChat = Mockito.mock(Chat.class);
        this.mockDTO = new ArrayList<>();
        this.message = "Hello World";
        this.senderId = "1";
        this.chatId = "1";

        ArrayList<Chat> chatList = new ArrayList<>();
        chatList.add(mockedChat);
        sut.setChats(chatList);
    }

    @Test
    void testGetChatHistorySUCCESS() {
        // Arrange
        Mockito.doReturn(mockDTO).when(mockedChat).getChatHistory();
        Mockito.doReturn(chatId).when(mockedChat).getChatId();

        // Act
        sut.getChatHistory(chatId);

        // Assert
        Mockito.verify(mockedChat).getChatHistory();
    }

    @Test
    void testSendMessageSUCCESS() {
        // Arrange
        Mockito.doNothing().when(mockedChat).sendMessage(message, senderId);
        Mockito.doReturn(chatId).when(mockedChat).getChatId();

        // Act
        sut.sendMessage(chatId, senderId, message);

        // Assert
        Mockito.verify(mockedChat).sendMessage(message, senderId);
    }
    @Test
    void testSendMessageFAILED() {
        // Arrange
        Mockito.doReturn(chatId).when(mockedChat).getChatId();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), sut.sendMessage(chatId, senderId, "").getStatus());
    }
}