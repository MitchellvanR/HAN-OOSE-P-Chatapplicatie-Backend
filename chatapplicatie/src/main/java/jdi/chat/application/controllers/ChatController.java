package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Path("/chats")
public class ChatController {
    private List<Chat> chats = new ArrayList<>();

    @GET
    @Path("/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@PathParam("chatId") String chatId){
        List<MessageDTO> chatHistory = openChat(chatId).getChatHistory();
        JSONObject chatHistoryJSON = new JSONObject();
        chatHistoryJSON.put("messages", chatHistory);
        return Response.ok().entity(chatHistoryJSON).build();
    }

    @POST
    @Path("/{senderId}/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@PathParam("senderId") String senderId, @PathParam("chatId") String chatId, String message){
        if (!message.isEmpty()){
            Chat chat = openChat(chatId);
            chat.sendMessage(message, senderId);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/{chatId}/addUser/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUserToChat(@PathParam("chatId") String chatId, @PathParam("userId") String userId){
        Chat chat = openChat(chatId);
        chat.addUserToChat(userId);
    }

    private Chat openChat(String chatId) {
        if (chats.isEmpty()) { return createNewChat(chatId); }
        for (Chat chat : chats) {
            if (chat.getChatId().equals(chatId)) {
                return chat;
            }
        }
        return createNewChat(chatId);
    }

    private Chat createNewChat(String chatId) {
        Chat chat = new Chat(chatId);
        chats.add(chat);
        return chat;
    }

    public void setChats(List<Chat> chatList) {
        this.chats = chatList;
    }
}
