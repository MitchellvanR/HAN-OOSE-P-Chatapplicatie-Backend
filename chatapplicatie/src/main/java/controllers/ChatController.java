package controllers;

import data.dto.MessageDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Chat;

@Path("/chats")
public class ChatController {

    private Chat[] chats;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(Object sender, Object receiver){
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(String message){
        chats[0].sendMessage(message);
        return null;
    }
}
