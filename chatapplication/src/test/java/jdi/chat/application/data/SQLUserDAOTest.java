package jdi.chat.application.data;

import jdi.chat.application.data.dto.UserDTO;
import jdi.chat.application.data.dto.UsersDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SQLUserDAOTest {
    private SQLUserDAO sut;

    @BeforeEach
    void setUp() {
        sut = new SQLUserDAO();
    }

    @Test
    void getAllUsersSuccessTest() throws SQLException  {

        // Arrange
        String statement = "TestQuery";
        String userId = "123";

        try(MockedStatic<Queries> mockedStaticQueries = Mockito.mockStatic(Queries.class)) {
            Queries mockedQueries = Mockito.mock(Queries.class);
            Mockito.when(mockedQueries.getQuery("getUsersQuery")).thenReturn(statement);
            mockedStaticQueries.when(Queries::getInstance).thenReturn(mockedQueries);

            Connection mockedConnection = Mockito.mock(Connection.class);
            PreparedStatement mockedPreparedStatement = Mockito.mock(PreparedStatement.class);
            ResultSet mockedResultSet = Mockito.mock(ResultSet.class);
            SQLConnection.setConnection(mockedConnection);
            Mockito.when(mockedConnection.prepareStatement(statement)).thenReturn(mockedPreparedStatement);
            Mockito.when(mockedResultSet.next()).thenReturn(true, false);
            Mockito.when(mockedResultSet.getString("id")).thenReturn(userId);
            Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

            // Act
            try(MockedStatic<SQLConnection> mockedStaticSQLConnection = Mockito.mockStatic(SQLConnection.class)) {
                UsersDTO result = sut.getAllUsers();
                UserDTO userResult = result.getUsers().get(0);
                mockedStaticSQLConnection.verify(SQLConnection::connectToDatabase);

                // Assert
                Assertions.assertEquals(userId, userResult.getId());
            }
        }
    }

    @Test
    void getAllUsersSQLExceptionTest() throws SQLException{

        // Arrange
        String statement = "TestQuery";
        String userId = "123";

        try(MockedStatic<Queries> mockedStaticQueries = Mockito.mockStatic(Queries.class)) {
            Queries mockedQueries = Mockito.mock(Queries.class);
            Mockito.when(mockedQueries.getQuery("getUsersQuery")).thenReturn(statement);
            mockedStaticQueries.when(Queries::getInstance).thenReturn(mockedQueries);

            Connection mockedConnection = Mockito.mock(Connection.class);
            PreparedStatement mockedPreparedStatement = Mockito.mock(PreparedStatement.class);
            ResultSet mockedResultSet = Mockito.mock(ResultSet.class);
            SQLConnection.setConnection(mockedConnection);

            Mockito.when(mockedConnection.prepareStatement(statement)).thenReturn(null);
            Mockito.when(mockedResultSet.next()).thenReturn(true, false);
            Mockito.when(mockedResultSet.getString("id")).thenReturn(userId);
            Mockito.when(mockedPreparedStatement.executeQuery()).thenReturn(mockedResultSet);

            // Assert
            Assertions.assertThrows(DatabaseRequestException.class, () -> sut.getAllUsers());
        }
    }
}