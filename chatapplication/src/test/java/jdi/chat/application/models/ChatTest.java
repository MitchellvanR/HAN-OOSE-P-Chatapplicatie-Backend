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

public class ChatTest {
    private Chat sut;
    private String chatId;
    private IChatDAO mockedChatDao;
    private String message;
    private String userId;
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
    }

    @Test
    void testGetChatHistorySucces () throws SQLException {
        // Arrange
        Mockito.doReturn(mockedDTO).when(mockedChatDao).getChatHistory(chatId);

        // Act
        sut.getChatHistory();

        // Assert
        Mockito.verify(mockedChatDao).getChatHistory(chatId);
    }

    @Test
    void testGetChatHistoryError() throws SQLException {
        // Arrange
        Mockito.when(mockedChatDao.getChatHistory(chatId)).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () ->sut.getChatHistory());
    }

    @Test
    void testSendMessage(){
        // Arrange
        Mockito.doNothing().when(mockedChatDao).saveMessage(message, userId, chatId, "");

        // Act
        sut.sendMessage(message, userId, "");

        // Assert
        Mockito.verify(mockedChatDao).saveMessage(message, userId, chatId, "");
    }

    @Test
    void testAddUserToChat(){
        // Arrange
        Mockito.doNothing().when(mockedChatDao).addUserToChat(chatId, userId);

        // Act
        sut.addUserToChat(userId);

        // Assert
        Mockito.verify(mockedChatDao).addUserToChat(chatId, userId);
    }

    @Test
    void testSetChatId(){
        // Arrange
        String newId = "1337";

        // Act
        sut.setChatId(newId);

        // Assert
        assertEquals(newId, sut.getChatId());
    }
}
