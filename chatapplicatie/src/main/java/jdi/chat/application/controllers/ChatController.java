package jdi.chat.application.controllers;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;

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
        return Response.accepted().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("senderId") String senderId, @QueryParam("receiverId") String receiverId, @QueryParam("message") String message){
        System.out.println("send help");
        chats.get(0).sendMessage(message);
        return Response.accepted().build();
    }

//    @Inject
//    public void setChats(ArrayList<Chat> chats) { this.chats = chats; }
}
