package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class SQLChatDAOTest {

    private SQLChatDAO sut;
    private ConnectionDAO mockedConnectionDAO;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private Queries queries;


    @BeforeEach
    void setup() {
        sut = new SQLChatDAO();
        mockedConnectionDAO = Mockito.mock(ConnectionDAO.class);
        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);
        queries = Queries.getInstance();

        System.out.println(mockedConnection.toString());

        mockedConnectionDAO.setConnection(mockedConnection);
        when(mockedConnectionDAO.getConnection()).thenReturn(mockedConnection);

        sut.setConnectionDAO(mockedConnectionDAO);
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
            when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
            doReturn(mockedResults).when(mockedStatement).executeQuery();

            when(mockedResults.next()).thenReturn(true).thenReturn(false);

            doReturn(senderId).when(mockedResults).getString("senderId");
            doReturn(message).when(mockedResults).getString("message");
            doReturn(time).when(mockedResults).getString("time");
        } catch (Exception e) {
            fail("An exception was thrown in success test case");
        }

        // Act
        var actual = sut.getChatHistory(chatId);

        // Assert
        assertEquals(expected.get(0).getSenderId(), actual.get(0).getSenderId());
        assertEquals(expected.get(0).getMessage(), actual.get(0).getMessage());
        assertEquals(expected.get(0).getTime(), actual.get(0).getTime());
    }

}