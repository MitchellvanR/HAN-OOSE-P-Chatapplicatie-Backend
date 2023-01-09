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
    private String userId;
    private ArrayList<MessageDTO> mockDTO;
    private ArrayList<String> userList;
    @BeforeEach
    void setup() {
        this.sut = new ChatController();

        this.mockedChat = Mockito.mock(Chat.class);
        this.mockDTO = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.message = "Hello World";
        this.userId = "3";
        this.chatId = "1";

        this.userList.add("1");
        this.userList.add("2");

        ArrayList<Chat> chatList = new ArrayList<>();
        chatList.add(mockedChat);
        sut.setChats(chatList);

        Mockito.doReturn(chatId).when(mockedChat).getChatId();
    }

    @Test
    void testGetChatHistorySUCCESS() {
        // Arrange
        Mockito.doReturn(mockDTO).when(mockedChat).getChatHistory();


        // Act
        sut.getChatHistory(chatId);

        // Assert
        Mockito.verify(mockedChat).getChatHistory();
    }

    @Test
    void testSendMessageSUCCESS() {
        // Arrange
        Mockito.doNothing().when(mockedChat).sendMessage(message, userList.get(0));

        // Act
        sut.sendMessage(chatId, userList.get(0), message);

        // Assert
        Mockito.verify(mockedChat).sendMessage(message, userList.get(0));
    }

    @Test
    void testSendMessageFAILED() {
        // Act
        int actual = sut.sendMessage(chatId, userId, "").getStatus();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), actual);
    }

    @Test
    void testAddUserToChat() {
        // Arrange
        Mockito.doNothing().when(mockedChat).defineChatType();
        Mockito.doReturn("group").when(mockedChat).getChatType();

        // Act
        sut.addUserToChat(chatId, userId);

        // Assert
        Mockito.verify(mockedChat).addUserToChat(Mockito.anyString());
    }


}