package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import net.minidev.json.JSONObject;
import java.sql.SQLException;
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
        chatHistoryJSON.put("chatId", chatId);
        return Response.ok().entity(chatHistoryJSON).build();
    }

    @POST
    @Path("/{senderId}/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@PathParam("senderId") String senderId, @PathParam("chatId") String chatId, String message){
        if (!message.isEmpty()){
            Chat chat = openChat(chatId);
            if (message.charAt(0) == '"') {
                message = message.substring(1, message.length() - 1);
            }
            String[] messageAndIv = message.split("\\^");
            chat.sendMessage(messageAndIv[0], senderId, messageAndIv[1]);
        }
        return Response.ok().build();
    }

    @POST
    @Path("/{chatId}/addUser/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUserToChat(@PathParam("chatId") String chatId, @PathParam("userId") String userId) throws SQLException {
        Chat chat = openChat(chatId);
        chat.defineChatType();
        String chatType = chat.getChatType();
        if ("standaard".equals(chatType)){
            ArrayList<String> users = chat.getUsers();
            Chat groupChat = createNewChat("0");
            groupChat.addChatToDatabase(users.get(0), "groep");
            groupChat.addUserToChat(users.get(1));
            groupChat.addUserToChat(userId);
        } else {
            chat.addUserToChat(userId);
        }
    }

    @POST
    @Path("/newChat/{userId}/{currentUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addChatToDatabase(@PathParam("userId") String userId, @PathParam("currentUser") String otherUserId){
        this.createChatInDatabase("standaard", userId, otherUserId);
        return Response.ok().build();
    }

    @POST
    @Path("/newHelpLineChat/{userId}/{currentUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHelplineChatToDatabase(@PathParam("userId") String userId, @PathParam("currentUser") String otherUserId){
        this.createChatInDatabase("hulplijn", userId, otherUserId);
        return Response.ok().build();
    }

    @GET
    @Path("/helplineList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorHelplineChats() {
        ArrayList <ChatDTO> helplineChats = Chat.getHelplineChats();
        JSONObject helplineChatsJSON = new JSONObject();
        helplineChatsJSON.put("helplineChats", helplineChats);
        return Response.ok().entity(helplineChatsJSON).build();
    }

    @GET
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatIds(@PathParam("userId") String userId) throws SQLException {
        ArrayList<String> chatIds = Chat.getChatIdFromUserId(userId);
        JSONObject chatIdsJSON = new JSONObject();
        chatIdsJSON.put("chatIds", chatIds);
        return Response.ok().entity(chatIdsJSON).build();
    }

    @GET
    @Path("/getChatType/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatType(@PathParam("chatId") String chatId) throws SQLException {
        Chat chat = openChat(chatId);
        chat.defineChatType();
        String type = chat.getChatType();
        JSONObject chatTypeJSON = new JSONObject();
        chatTypeJSON.put("chatType", type);
        return Response.ok().entity(chatTypeJSON).build();
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

    private void createChatInDatabase(String type, String userId, String otherUserId){
        Chat chat = createNewChat("0");
        chat.addChatToDatabase(userId, type);
        chat.addUserToChat(otherUserId);
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

    public void setChats(List<Chat> chats) { this.chats = chats; }
}
