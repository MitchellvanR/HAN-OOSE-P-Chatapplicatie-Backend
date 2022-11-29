package jdi.chat.application.controllers.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jdi.chat.application.util.exceptions.ConfigNotFoundException;

@Provider
public class ConfigNotFoundMapper implements ExceptionMapper<ConfigNotFoundException> {
    @Override
    public Response toResponse(ConfigNotFoundException e) {
        return Response.status(404).entity(e.getMessage()).type("text/plain").build();
    }
}
