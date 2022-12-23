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
    private String chatId;
    private String userId;
    private String messageAndIv;
    private String[] messageAndIvArray;
    private ArrayList<MessageDTO> mockDTO;
    @BeforeEach
    void setup() {
        this.sut = new ChatController();

        this.mockedChat = Mockito.mock(Chat.class);
        this.mockDTO = new ArrayList<>();
        String iv = "23,91,173,185,232,253,67,46,157,2,233,184,163,162,104,197";
        this.messageAndIv = "Hello World" + "^" + iv;
        this.messageAndIvArray = messageAndIv.split("\\^");
        this.userId = "1";
        this.chatId = "1";


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
        Mockito.doNothing().when(mockedChat).sendMessage(messageAndIvArray[0], userId, messageAndIvArray[1]);

        // Act
        sut.sendMessage(chatId, userId, messageAndIv);

        // Assert
        Mockito.verify(mockedChat).sendMessage(messageAndIvArray[0], userId, messageAndIvArray[1]);
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
        sut.addUserToChat(chatId, userId);

        // Assert
        Mockito.verify(mockedChat).addUserToChat(Mockito.anyString());
    }
}