package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import net.minidev.json.JSONObject;

import java.util.ArrayList;

@Path("/chats")
public class ChatController {
    private final ArrayList<Chat> chats = new ArrayList<>();

    @GET
    @Path("/{senderId}/{receiverId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@PathParam("senderId") String senderId, @PathParam("receiverId") String receiverId){
        ArrayList<MessageDTO> chatHistory = openChat(senderId, receiverId).getChatHistory();
        JSONObject chatHistoryJSON = new JSONObject();
        chatHistoryJSON.put("messages", chatHistory);
        return Response.ok().entity(chatHistoryJSON).build();
    }

    @POST
    @Path("/{senderId}/{receiverId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@PathParam("senderId") String senderId, @PathParam("receiverId") String receiverId, String message){
        Chat chat = openChat(senderId, receiverId);
        chat.sendMessage(message);
        return Response.ok().build();
    }

    private Chat openChat(String senderId, String receiverId) {
        if (chats.isEmpty()) { return createNewChat(senderId, receiverId); }
        for (Chat chat : chats) {
            if (chat.getSenderId().equals(senderId) && chat.getReceiverId().equals(receiverId)) {
                return chat;
            }
        }
        return createNewChat(senderId, receiverId);
    }

    private Chat createNewChat(String senderId, String receiverId) {
        Chat chat = new Chat(senderId, receiverId);
        chats.add(chat);
        return chat;
    }

    public void setChats(ArrayList<Chat> chatList) {
    }
}
