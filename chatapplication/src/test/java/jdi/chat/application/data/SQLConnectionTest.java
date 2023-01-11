package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLConnectionTest {
    @BeforeEach
    void setup() {
        SQLConnection.setConnection(null);
        SQLConnection.setProperties(new Properties());
    }

    @AfterAll
    static void tearDown() {
        SQLConnection.setConnection(null);
        SQLConnection.setProperties(new Properties());
    }

    @Test
    void connectToDatabaseTest(){
        try {
            // Act
            SQLConnection.connectToDatabase();
        } catch (Exception e){
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void connectToDatabaseErrorTest() {
        // Arrange
        Properties mockedProperties = Mockito.mock(Properties.class);
        SQLConnection.setProperties(mockedProperties);
        Mockito.when(mockedProperties.getProperty("jdbc:mysql://localhost/prolog-jdi?user=chatUser&password=chatUserPass&serverTimezone=UTC")).thenThrow(RuntimeException.class);

        // Assert
        assertThrows(DatabaseRequestException.class, SQLConnection::connectToDatabase);
    }
}
