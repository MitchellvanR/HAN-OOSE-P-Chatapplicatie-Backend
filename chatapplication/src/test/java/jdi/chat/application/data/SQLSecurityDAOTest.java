package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SQLSecurityDAOTest {
    private SQLSecurityDAO sut;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private ResultSet mockedResults;
    private Queries queries;
    private String userId;
    private String publicKey;
    private String chatId;

    @BeforeEach
    void setup() {
        sut = new SQLSecurityDAO();
        queries = Queries.getInstance();

        userId = "1";
        publicKey = "10294123";
        chatId = "1";

        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);
        mockedResults = Mockito.mock(ResultSet.class);

        SQLConnection.setConnection(mockedConnection);
    }

    @AfterAll
    static void tearDown() {
        SQLConnection.setConnection(null);
    }

    @Test
    void addUserSuccessTest() {
        try {
            // Arrange
            Mockito.when(mockedConnection.prepareStatement(queries.getQuery("addUserQuery"))).thenReturn(mockedStatement);

            // Act
            sut.addUser(userId);

            // Assert
            verify(mockedStatement).executeUpdate();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void addUserSQLExceptionTest() throws SQLException {
        // Arrange
        Mockito.when(mockedConnection.prepareStatement(queries.getQuery("addUserQuery"))).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeUpdate()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.addUser(userId));
    }

    @Test
    void savePublicKeySuccessTest() {
        try {
            // Arrange
            Mockito.when(mockedConnection.prepareStatement(queries.getQuery("savePublicKeyQuery"))).thenReturn(mockedStatement);

            // Act
            sut.savePublicKey(userId, publicKey);

            // Assert
            verify(mockedStatement).executeUpdate();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void savePublicKeySQLExceptionTest() throws SQLException {
        // Arrange
        Mockito.when(mockedConnection.prepareStatement(queries.getQuery("savePublicKeyQuery"))).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeUpdate()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.savePublicKey(userId, publicKey));
    }

    @Test
    void getOtherPublicKeySuccessTest() {
        try {
            // Arrange
            var expected = publicKey;

            try {
                when(mockedConnection.prepareStatement(queries.getQuery("getOtherPublicKeyQuery"))).thenReturn(mockedStatement);
                doReturn(mockedResults).when(mockedStatement).executeQuery();

                when(mockedResults.next()).thenReturn(true).thenReturn(false);

                doReturn(publicKey).when(mockedResults).getString(1);
            } catch (SQLException e) {
                fail("An exception was thrown in success test case: " + e.getMessage());
            }

            // Act
            String actual = "";
            try {
                actual = sut.getOtherPublicKey(userId, chatId);
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
    void getOtherPublicKeySQLExceptionTest() throws SQLException {
        Mockito.when(mockedConnection.prepareStatement(queries.getQuery("getOtherPublicKeyQuery"))).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeUpdate()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getOtherPublicKey(userId, chatId));
    }
}

