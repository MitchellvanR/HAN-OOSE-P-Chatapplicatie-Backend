package jdi.chat.application.data.exceptions;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException() {
        super("Unable to connect to database");
    }

}
