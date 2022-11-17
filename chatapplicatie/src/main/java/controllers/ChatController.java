package controllers;

import data.dto.MessageDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import models.Chat;

@Path("/chats")
public class ChatController {

    private Chat[] chats;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MessageDTO[] getChatHistory(Object sender, Object receiver){
        // code
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void sendMessage(String message){
        // code
    }
}
