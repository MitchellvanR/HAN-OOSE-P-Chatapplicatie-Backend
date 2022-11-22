package jdi.chat.application.controllers.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jdi.chat.application.data.exceptions.DatabasePropertiesException;

@Provider
public class DatabasePropertiesMapper implements ExceptionMapper<DatabasePropertiesException> {

    @Override
    public Response toResponse(DatabasePropertiesException e) {
        return Response.status(403).entity(e.getMessage()).type("text/plain").build();
    }

}
