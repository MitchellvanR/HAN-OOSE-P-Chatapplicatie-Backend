package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SQLChatDAOTest {
    private SQLChatDAO sut;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private CallableStatement mockedCall;
    private Queries queries;
    private ResultSet mockedResults;
    private String chatId;
    private String senderId;
    private String message;
    private String time;
    private String iv;
    private String type;

    @BeforeEach
    void setup() {
        sut = new SQLChatDAO();
        queries = Queries.getInstance();

        chatId = "1";
        senderId = "1";
        message = "test";
        time = "00:00";
        iv = "11111";
        type = "standard";

        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);
        mockedCall = Mockito.mock(CallableStatement.class);
        mockedResults = Mockito.mock(ResultSet.class);

        SQLConnection.setConnection(mockedConnection);
    }

    @AfterAll
    static void tearDown() {
        SQLConnection.setConnection(null);
    }

    @Test
    void testGetChatHistoryHappyFlow() {
        // Arrange
        var expected = new ArrayList<MessageDTO>();
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
        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> sut.getChatHistory(chatId));
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testSaveMessageHappyFlow() {
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
        try {
            when(mockedConnection.prepareStatement(queries.getQuery("sendMessageQuery"))).thenReturn(mockedStatement);
            when(mockedStatement.executeUpdate()).thenThrow(SQLException.class);
        } catch (SQLException e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> sut.saveMessage(message, senderId, chatId, iv));
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testSaveMessageDatabaseRequestExceptionWhenStatementIsNull() {
        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> sut.saveMessage(message, senderId, chatId, iv));
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testAddUserToChatHappyFlow() {
        // Arrange
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
        var userId = "1";

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("addUserToChatQuery"))).thenReturn(mockedStatement);
            when(mockedStatement.executeUpdate()).thenThrow(SQLException.class);
        } catch (SQLException e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> sut.addUserToChat(chatId, userId));
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void testAddUserToChatDatabaseRequestExceptionWhenStatementIsNull() {
        // Arrange
        var userId = "1";

        // Act
        Exception e = assertThrows(DatabaseRequestException.class, () -> sut.addUserToChat(chatId, userId));
        var actual = e.getMessage();

        // Assert
        assertTrue(actual.contains("database connection"));
    }

    @Test
    void addChatToDatabaseTest(){
        try {
            // Arrange
            var expected = chatId;

            try {
                when(mockedConnection.prepareCall(queries.getQuery("createChatQuery"))).thenReturn(mockedCall);
                doReturn(mockedResults).when(mockedCall).executeQuery();

                when(mockedResults.next()).thenReturn(true).thenReturn(false);

                doReturn(chatId).when(mockedResults).getString(1);
            } catch (SQLException e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Act
            String actual = "";
            try {
                actual = sut.addChatToDatabase(senderId, type);
            } catch (Exception e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Assert
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }
    }

    @Test
    void addChatToDatabaseErrorTest() throws SQLException {
        // Arrange
        when(mockedConnection.prepareCall(queries.getQuery("createChatQuery"))).thenReturn(mockedCall);
        when(mockedCall.executeQuery()).thenThrow(SQLException.class);

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.addChatToDatabase(senderId, type));
    }

    @Test
    void getUsersInChatTest(){
        try {
            // Arrange
            var expected = new ArrayList<String>();
            expected.add(senderId);

            try {
                when(mockedConnection.prepareStatement(queries.getQuery("getUsersInChatQuery"))).thenReturn(mockedStatement);
                doReturn(mockedResults).when(mockedStatement).executeQuery();

                when(mockedResults.next()).thenReturn(true).thenReturn(false);

                doReturn(senderId).when(mockedResults).getString(1);
            } catch (SQLException e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Act
            ArrayList<String> actual = null;
            try {
                actual = sut.getUsersInChat(chatId);
            } catch (Exception e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Assert
            assertEquals(expected.get(0), actual.get(0));
        } catch (Exception e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }
    }

    @Test
    void getUsersInChatErrorTest() throws SQLException {
        // Arrange
        when(mockedConnection.prepareStatement(queries.getQuery("getUsersInChatQuery"))).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getUsersInChat(chatId));
    }

    @Test
    void getChatTypeTest() {
        try {
            // Arrange
            var expected = type;

            try {
                when(mockedConnection.prepareStatement(queries.getQuery("getChatTypeQuery"))).thenReturn(mockedStatement);
                doReturn(mockedResults).when(mockedStatement).executeQuery();

                when(mockedResults.next()).thenReturn(true).thenReturn(false);

                doReturn(type).when(mockedResults).getString(1);
            } catch (SQLException e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Act
            String actual = null;
            try {
                actual = sut.getChatType(chatId);
            } catch (Exception e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Assert
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }
    }

    @Test
    void getChatTypeErrorTest() throws SQLException {
        // Arrange
        when(mockedConnection.prepareStatement(queries.getQuery("getChatTypeQuery"))).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getChatType(chatId));
    }

    @Test
    void getChatIdFromUserIdTest() {
        try {
            // Arrange
            var expected = new ArrayList<String>();
            expected.add(chatId);

            try {
                when(mockedConnection.prepareStatement(queries.getQuery("getChatIdQuery"))).thenReturn(mockedStatement);
                doReturn(mockedResults).when(mockedStatement).executeQuery();

                when(mockedResults.next()).thenReturn(true).thenReturn(false);

                doReturn(chatId).when(mockedResults).getString("chatId");
            } catch (SQLException e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Act
            ArrayList<String> actual = null;
            try {
                actual = sut.getChatIdFromUserId(senderId);
            } catch (Exception e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Assert
            assertEquals(expected.get(0), actual.get(0));
        } catch (Exception e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }
    }

    @Test
    void getChatIdFromUserIdErrorTest() throws SQLException {
        // Arrange
        when(mockedConnection.prepareStatement(queries.getQuery("getChatIdQuery"))).thenReturn(mockedStatement);
        when(mockedStatement.executeQuery()).thenThrow(SQLException.class);

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getChatIdFromUserId(senderId));
    }
}