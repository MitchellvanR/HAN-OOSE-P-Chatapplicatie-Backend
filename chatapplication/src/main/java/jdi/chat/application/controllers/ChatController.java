package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import net.minidev.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Path("/chats")
public class ChatController {
    private List<Chat> chats = new ArrayList<>();

    @GET
    @Path("/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@PathParam("chatId") String chatId) {
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

    @GET
    @Path("/helplineList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorHelplineChats() {
        Chat chat = new Chat("2", true);
        chats.add(chat);
        ArrayList<Chat> helplineChats = getHelplineChatsFromChats();
        findLatestMessage(helplineChats);
        JSONObject helplineChatsJSON = new JSONObject();
        helplineChatsJSON.put("helplineChats", helplineChats);
        return Response.ok().entity(helplineChatsJSON).build();
    }

    @GET
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatIds(@PathParam("userId") String userId) {
        ArrayList<String> chatIds = Chat.getChatIdFromUserId(userId);
        JSONObject chatIdsJSON = new JSONObject();
        chatIdsJSON.put("chatIds", chatIds);
        return Response.ok().entity(chatIdsJSON).build();
    }

    @GET
    @Path("/helpline/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserHelplineChatId (@PathParam("userId") String userId) {
        String chatId = Chat.getUserHelplineChatId(userId);
        JSONObject chatIdJSON = new JSONObject();
        chatIdJSON.put("chatId", chatId);
        return Response.ok().entity(chatIdJSON).build();
    }

    private ArrayList<Chat> getHelplineChatsFromChats() {
        ArrayList<Chat> helplineChats = new ArrayList<>();

        if(chats.isEmpty()) {
            return null;
        } else {
            for(Chat chat : chats) {
                if (chat.isHelpline()) {
                    helplineChats.add(chat);
                }
            }
            return helplineChats;
        }
    }

    private void findLatestMessage(ArrayList<Chat> helplineChats) {
        for (Chat chat : helplineChats) {
            chat.findLatestMessage();
        }
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
