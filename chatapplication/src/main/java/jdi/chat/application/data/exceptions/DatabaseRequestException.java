package jdi.chat.application.data.exceptions;

public class DatabaseRequestException extends RuntimeException {

    public DatabaseRequestException() {
        super("A problem occurred while sending a request to database. Check the database connection or the executed query");
    }

}
