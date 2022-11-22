package jdi.chat.application.data.exceptions;

public class DatabasePropertiesException extends RuntimeException {

    public DatabasePropertiesException() {
        super("A problem has occurred while loading the database properties");
    }

}
