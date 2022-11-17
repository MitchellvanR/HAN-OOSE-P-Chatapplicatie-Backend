package controllers;

import data.dto.MessageDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Chat;

import java.util.ArrayList;

@Path("/chats")
public class ChatController {

    private ArrayList<Chat> chats;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@QueryParam("senderId") String senderId, @QueryParam("receiverId") String receiverId){
        Chat chat = new Chat(/*sender, receiver*/ "1", "2");
        ArrayList<MessageDTO> chatHistory = chat.getChatHistory();
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("message") String message){
        chats.get(0).sendMessage(message);
        return null;
    }

    @Inject
    public void setChats(ArrayList<Chat> chats) { this.chats = chats; }
}
