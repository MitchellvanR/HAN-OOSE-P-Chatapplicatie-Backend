package jdi.chat.application.controllers;

import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {
    private ChatController sut;
    private Chat mockedChat;
    private String chatId;
    private String userId;
    private String addedUserId;
    private String messageAndIv;
    private String[] messageAndIvArray;
    private ArrayList<MessageDTO> mockDTO;
    private ArrayList<Chat> chatList;
    private MockedConstruction<Chat> chatMockController;
    @BeforeEach
    void setup() {
        this.sut = new ChatController();

        this.mockedChat = Mockito.mock(Chat.class);
        this.mockDTO = new ArrayList<>();
        String iv = "23,91,173,185,232,253,67,46,157,2,233,184,163,162,104,197";
        this.messageAndIv = "Hello World" + "^" + iv;
        this.messageAndIvArray = messageAndIv.split("\\^");
        this.userId = "1";
        this.addedUserId = "3";
        this.chatId = "1";

        chatList = new ArrayList<>();
        chatList.add(mockedChat);


        chatMockController = Mockito.mockConstruction(Chat.class, (mock, context) ->{
            when(mock.getChatId()).thenReturn(chatId);
            when(mock.getChatHistory()).thenReturn(mockDTO);
            when(mock.getChatType()).thenReturn("groep");
            doNothing().when(mock).sendMessage(anyString(), anyString(), anyString());
            doNothing().when(mock).defineChatType();
        });
    }
    
    @AfterEach
    void close(){
        chatMockController.close();
    }

    @Test
    void testGetChatHistorySUCCESS() {
        // Act
        Response response = sut.getChatHistory(chatId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testGetChatHistoryChatNotExistingCorrectResponseStatus() {
        // Arrange
        var nonExistingChatId = "4";
        var mockedDao = Mockito.mock(SQLChatDAO.class);
        mockedChat.setChatDAO(mockedDao);
        Mockito.doThrow(DatabaseRequestException.class).when(mockedChat).getChatHistory();

        // Act
        Response actual = sut.getChatHistory(nonExistingChatId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), actual.getStatus());
    }

    @Test
    void testSendMessageSUCCESS() {
        // Act
        sut.sendMessage(chatId, userId, messageAndIv);

        // Assert
        Chat newChatMock = chatMockController.constructed().get(0);
        Mockito.verify(newChatMock).sendMessage(messageAndIvArray[0], userId, messageAndIvArray[1]);
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
        // Act
        sut.addUserToChat(chatId, addedUserId);

        // Assert
        Chat newChatMock = chatMockController.constructed().get(0);
        Mockito.verify(newChatMock).addUserToChat(Mockito.anyString());
    }
}