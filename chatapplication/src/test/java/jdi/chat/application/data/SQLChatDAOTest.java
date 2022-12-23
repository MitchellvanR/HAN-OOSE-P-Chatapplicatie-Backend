package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class SQLChatDAOTest {

    private SQLChatDAO sut;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private Queries queries;


    @BeforeEach
    void setup() {
        sut = new SQLChatDAO();
        queries = Queries.getInstance();

        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);

        SQLChatDAO.setConnection(mockedConnection);
    }

    @Test
    void testGetChatHistoryHappyFlow() {
        // Arrange
        var chatId = "1";
        var senderId = "1";
        var message = "test";
        var time = "00:00";
        var expected = new ArrayList<MessageDTO>();
        var mockedResults = Mockito.mock(ResultSet.class);

        expected.add(new MessageDTO(senderId, message, time));

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("getChatHistoryQuery"))).thenReturn(mockedStatement);
            doReturn(mockedResults).when(mockedStatement).executeQuery();

            when(mockedResults.next()).thenReturn(true).thenReturn(false);

            doReturn(senderId).when(mockedResults).getString("senderId");
            doReturn(message).when(mockedResults).getString("message");
            doReturn(time).when(mockedResults).getString("time");
        } catch (Exception e) {
            fail("An exception was thrown in success test case");
        }

        // Act
        ArrayList<MessageDTO> actual = null;
        try {
            actual = sut.getChatHistory(chatId);
        } catch (SQLException e) {
            fail(e.getMessage());
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
        // Act
        // Assert
    }
}