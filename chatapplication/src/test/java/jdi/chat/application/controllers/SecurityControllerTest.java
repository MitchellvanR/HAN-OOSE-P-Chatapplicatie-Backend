package jdi.chat.application.controllers;

import jdi.chat.application.data.SQLSecurityDAO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SecurityControllerTest {
    private SecurityController sut;
    private String userId;
    private String publicKey;
    private String chatId;
    private SQLSecurityDAO mockedSecurityDAO;
    private String empty;

    @BeforeEach
    void setup(){
        sut = new SecurityController();
        userId = "0";
        publicKey = "100000";
        chatId = "0";
        mockedSecurityDAO = Mockito.mock(SQLSecurityDAO.class);
        sut.securityDAO = mockedSecurityDAO;
        empty = "";
    }

    @Test
    void addUserTest(){
        // Arrange
        Mockito.doNothing().when(mockedSecurityDAO).addUser(userId);

        // Act
        sut.addUser(userId);

        // Assert
        Mockito.verify(mockedSecurityDAO).addUser(userId);
    }

    @Test
    void savePublicKeyTest(){
        // Arrange
        Mockito.doNothing().when(mockedSecurityDAO).savePublicKey(userId, publicKey);

        // Act
        sut.savePublicKey(userId, publicKey);

        // Assert
        Mockito.verify(mockedSecurityDAO).savePublicKey(userId, publicKey);
    }

    @Test
    void getOtherPublicKeyTest(){
        // Arrange
        try {
            Mockito.doReturn(empty).when(mockedSecurityDAO).getOtherPublicKey(userId, chatId);

            // Act
            sut.getOtherPublicKey(userId, chatId);

            // Assert
            Mockito.verify(mockedSecurityDAO).getOtherPublicKey(userId, chatId);
        } catch (SQLException e) {
            fail("An exception was thrown in test case: " + e.getMessage());
        }
    }

    @Test
    void getOtherPublicKeyCatchesExceptionAndThrowsDatabaseRequestExceptionTest() throws SQLException {
        //Arrange
        Mockito.when(mockedSecurityDAO.getOtherPublicKey(Mockito.anyString(), Mockito.anyString())).thenThrow(SQLException.class);

        //Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getOtherPublicKey(userId, chatId));
    }
}
