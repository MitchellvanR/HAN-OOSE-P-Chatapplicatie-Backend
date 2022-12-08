package jdi.chat.application.controllers.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jdi.chat.application.data.exceptions.DatabaseRequestException;

@Provider
public class DatabaseRequestMapper implements ExceptionMapper<DatabaseRequestException> {

    @Override
    public Response toResponse(DatabaseRequestException e) {
        return Response.status(400).entity(e.getMessage()).type("text/plain").build();
    }

}
