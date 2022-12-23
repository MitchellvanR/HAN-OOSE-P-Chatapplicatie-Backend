package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SQLChatDAOTest {

    private SQLChatDAO sut;
    private SQLConnection mockedSQLConnection;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private Queries queries;


    @BeforeEach
    void setup() {
        sut = new SQLChatDAO();
        queries = Queries.getInstance();

        mockedSQLConnection = Mockito.mock(SQLConnection.class);
        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);

        SQLConnection.setConnection(mockedConnection);
    }

    @AfterAll
    static void tearDown() {
        SQLConnection.setConnection(null);
    }

    @Test
    void testGetChatHistoryHappyFlow() {
        // Arrange
        var chatId = "1";
        var senderId = "1";
        var message = "test";
        var time = "00:00";
        var iv = "11111";
        var expected = new ArrayList<MessageDTO>();
        var mockedResults = Mockito.mock(ResultSet.class);

        expected.add(new MessageDTO(senderId, message, time, iv));

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("getChatHistoryQuery"))).thenReturn(mockedStatement);
            doReturn(mockedResults).when(mockedStatement).executeQuery();

            when(mockedResults.next()).thenReturn(true).thenReturn(false);

            doReturn(senderId).when(mockedResults).getString("senderId");
            doReturn(message).when(mockedResults).getString("message");
            doReturn(time).when(mockedResults).getString("time");
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }

        // Act
        ArrayList<MessageDTO> actual = null;
        try {
            actual = sut.getChatHistory(chatId);
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }

        // Assert
        assertEquals(expected.get(0).getSenderId(), actual.get(0).getSenderId());
        assertEquals(expected.get(0).getMessage(), actual.get(0).getMessage());
        assertEquals(expected.get(0).getTime(), actual.get(0).getTime());
    }

    @Test
    void testGetChatHistoryDatabaseRequestExceptionWhenStatementIsNull() {
        // Arrange
        var chatId = "1";

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> {
            sut.getChatHistory(chatId);
        });
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testSaveMessageHappyFlow() {
        // Arrange
        var message = "test";
        var senderId = "1";
        var chatId = "1";
        var iv = "11111";

        // Act
        try {
            when(mockedConnection.prepareStatement(queries.getQuery("sendMessageQuery"))).thenReturn(mockedStatement);
            sut.saveMessage(message, senderId, chatId, iv);
        // Assert
            verify(mockedStatement).executeUpdate();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void testSaveMessageDatabaseRequestExceptionWhenUnableToExecuteUpdate() {
        // Arrange
        var message = "test";
        var senderId = "1";
        var chatId = "1";
        var iv = "11111";

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("sendMessageQuery"))).thenReturn(mockedStatement);
            when(mockedStatement.executeUpdate()).thenThrow(SQLException.class);
        } catch (SQLException e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> {
            sut.saveMessage(message, senderId, chatId, iv);
        });

        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testSaveMessageDatabaseRequestExceptionWhenStatementIsNull() {
        // Arrange
        var message = "test";
        var senderId = "1";
        var chatId = "1";
        var iv = "11111";

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> {
            sut.saveMessage(message, senderId, chatId, iv);
        });

        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testAddUserToChatHappyFlow() {
        // Arrange
        var chatId = "1";
        var userId = "1";

        // Act
        try {
            when(mockedConnection.prepareStatement(queries.getQuery("addUserToChatQuery"))).thenReturn(mockedStatement);
            sut.addUserToChat(chatId, userId);
        // Assert
            verify(mockedStatement).executeUpdate();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void testAddUserToChatDatabaseRequestExceptionWhenUnableToExecuteUpdate() {
        // Arrange
        var chatId = "1";
        var userId = "1";

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("addUserToChatQuery"))).thenReturn(mockedStatement);
            when(mockedStatement.executeUpdate()).thenThrow(SQLException.class);
        } catch (SQLException e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> {
            sut.addUserToChat(chatId, userId);
        });

        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testAddUserToChatDatabaseRequestExceptionWhenStatementIsNull() {
        // Arrange
        var chatId = "1";
        var userId = "1";

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> {
            sut.addUserToChat(chatId, userId);
        });

        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }
}