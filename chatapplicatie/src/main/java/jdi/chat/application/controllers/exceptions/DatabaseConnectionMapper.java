package jdi.chat.application.controllers.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jdi.chat.application.data.exceptions.DatabaseConnectionException;

@Provider
public class DatabaseConnectionMapper implements ExceptionMapper<DatabaseConnectionException> {

    @Override
    public Response toResponse(DatabaseConnectionException e) {
        return Response.status(503).entity(e.getMessage()).type("text/plain").build();
    }

}
