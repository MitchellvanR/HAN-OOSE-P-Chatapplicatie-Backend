package jdi.chat.application.controllers;

import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatControllerTest {
    private ChatController sut;
    private Chat mockedChat;
    private String chatType;
    private String chatId;
    private String userId;
    private String addedUserId;
    private String messageAndIv;
    private String[] messageAndIvArray;
    private ArrayList<MessageDTO> mockDTO;
    private ArrayList<String> userlist;
    private ArrayList<String> chatIdList;
    private MockedConstruction<Chat> chatMockController;
    @BeforeEach
    void setup() {
        sut = new ChatController();
        mockDTO = new ArrayList<>();
        userlist = new ArrayList<>();
        ArrayList<Chat> chatList = new ArrayList<>();
        chatIdList = new ArrayList<>();
        mockedChat = Mockito.mock(Chat.class);
        String iv = "23,91,173,185,232,253,67,46,157,2,233,184,163,162,104,197";
        messageAndIv = "Hello World" + "^" + iv;
        messageAndIvArray = messageAndIv.split("\\^");
        userId = "1";
        addedUserId = "3";
        chatId = "1";
        userlist.add("1");
        userlist.add("2");
        chatIdList.add("1");
        chatType = "standaard";
        chatList.add(mockedChat);
        sut.setChats(chatList);

        when(mockedChat.getChatId()).thenReturn("45");
        chatMockController = Mockito.mockConstruction(Chat.class, (mock, context) ->{
            when(mock.getChatId()).thenReturn(chatId);
            when(mock.getChatHistory()).thenReturn(mockDTO);
            when(mock.getChatType()).thenReturn(chatType);
            when(mock.getUsers()).thenReturn(userlist);
            doNothing().when(mock).sendMessage(anyString(), anyString(), anyString());
            doNothing().when(mock).defineChatType();
            doNothing().when(mock).addUserToChat(anyString());
            doNothing().when(mock).addChatToDatabase(anyString(), anyString());
        });
    }

    @AfterEach
    void tearDown(){ chatMockController.close(); }

    @Test
    void getChatHistorySuccessTest() {
        // Act
        Response response = sut.getChatHistory(chatId);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void getChatHistoryChatNotExistingCorrectResponseStatusTest() {
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
    void sendMessageSuccessTest() {
        // Act
        sut.sendMessage(chatId, userId, messageAndIv);

        // Assert
        Chat newChatMock = chatMockController.constructed().get(0);
        Mockito.verify(newChatMock).sendMessage(messageAndIvArray[0], userId, messageAndIvArray[1]);
    }

    @Test
    void SendMessageFailedTest() {
        // Act
        int actual = sut.sendMessage(chatId, userId, "").getStatus();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), actual);
    }

    @Test
    void addUserToChatSuccessTest() throws SQLException {
        // Act
        sut.addUserToChat(chatId, addedUserId);

        // Assert
        Chat newChatMock = chatMockController.constructed().get(1);
        Mockito.verify(newChatMock, times(2)).addUserToChat(Mockito.anyString());
    }

    @Test
    void AddChatToDatabaseSuccessTest(){
        // Act
        sut.addChatToDatabase(userId, addedUserId);

        // Assert
        Chat newChatMock = chatMockController.constructed().get(0);
        verify(newChatMock, times(1)).addUserToChat(anyString());
    }

    @Test void getChatIdsSuccessTest() throws SQLException {
        // Arrange
        try (MockedStatic<Chat> chatMockedStatic = Mockito.mockStatic(Chat.class)) {
            chatMockedStatic.when(() -> Chat.getChatIdFromUserId(anyString())).thenReturn(chatIdList);

            // Act
            int actual = sut.getChats(userId).getStatus();

            // Assert
            assertEquals(Response.Status.OK.getStatusCode(), actual);
        }
    }

    @Test void getStandardChatWithUsersTest() {
        // Arrange
        try (MockedStatic<Chat> chatMockedStatic = Mockito.mockStatic(Chat.class)) {
            chatMockedStatic.when(() -> Chat.getStandardChatWithUsers(anyString(), anyString())).thenReturn(1);

            // Act
            int actual = sut.getStandardChatWithUsers(userId, addedUserId).getStatus();

            // Assert
            assertEquals(Response.Status.OK.getStatusCode(), actual);
        }
    }

    @Test void checkIfUserExistsTest() {
        // Arrange
        try (MockedStatic<Chat> chatMockedStatic = Mockito.mockStatic(Chat.class)) {
            chatMockedStatic.when(() -> Chat.getStandardChatWithUsers(anyString(), anyString())).thenReturn(1);

            // Act
            int actual = sut.checkIfUserExists(userId).getStatus();

            // Assert
            assertEquals(Response.Status.OK.getStatusCode(), actual);
        }
    }
}