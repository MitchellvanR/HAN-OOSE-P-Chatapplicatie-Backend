package jdi.chat.application.data.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseRequestExceptionTest {
    @Test
    void testDatabaseRequestExceptionSUCCESS() {
        //Arrange
        DatabaseRequestException e = new DatabaseRequestException();


        //Assert
        assertThrows(
            DatabaseRequestException.class,
            () -> {
                throw new DatabaseRequestException();
            }
        );

        assertThrows(
            DatabaseRequestException.class,
            () -> {
                throw new DatabaseRequestException(e);
            }
        );
    }
}