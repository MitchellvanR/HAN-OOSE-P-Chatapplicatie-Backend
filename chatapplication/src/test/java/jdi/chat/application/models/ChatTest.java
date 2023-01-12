package jdi.chat.application.models;

import jdi.chat.application.data.IChatDAO;
import jdi.chat.application.data.SQLChatDAO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private Chat sut;
    private String chatId;
    private IChatDAO mockedChatDao;
    private String message;
    private String userId;
    private String iv;
    private ArrayList<MessageDTO> mockedDTO;
    @BeforeEach
    void setup() {
        chatId = "0";
        sut = new Chat(chatId);
        mockedChatDao = Mockito.mock(SQLChatDAO.class);
        sut.setChatDAO(mockedChatDao);
        message = "Test";
        userId = "0";
        mockedDTO = new ArrayList<>();
        iv = "23,91,173,185,232,253,67,46,157,2,233,184,163,162,104,197";
    }

    @Test
    void getChatHistorySuccessTest () throws SQLException {
        // Arrange
        Mockito.doReturn(mockedDTO).when(mockedChatDao).getChatHistory(chatId);

        // Act
        sut.getChatHistory();

        // Assert
        Mockito.verify(mockedChatDao).getChatHistory(chatId);
    }

    @Test
    void getChatHistorySQLExceptionTest() throws SQLException {
        // Arrange
        Mockito.when(mockedChatDao.getChatHistory(chatId)).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () ->sut.getChatHistory());
    }

    @Test
    void sendMessageSuccessTest(){
        // Arrange
        Mockito.doNothing().when(mockedChatDao).saveMessage(message, userId, chatId, iv);

        // Act
        sut.sendMessage(message, userId, iv);

        // Assert
        Mockito.verify(mockedChatDao).saveMessage(message, userId, chatId, iv);
    }

    @Test
    void addUserToChatSuccessTest(){
        // Arrange
        Mockito.doNothing().when(mockedChatDao).addUserToChat(chatId, userId);

        // Act
        sut.addUserToChat(userId);

        // Assert
        Mockito.verify(mockedChatDao).addUserToChat(chatId, userId);
    }

    @Test
    void setChatIdSuccessTest(){
        // Arrange
        String newId = "1337";

        // Act
        sut.setChatId(newId);

        // Assert
        assertEquals(newId, sut.getChatId());
    }
}
