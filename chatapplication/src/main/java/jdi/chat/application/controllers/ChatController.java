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
import java.util.Objects;

@Path("/chats")
public class ChatController {
    private List<Chat> chatList = new ArrayList<>();

    @GET
    @Path("/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@PathParam("chatId") String chatId){
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response addChatToDatabase(@PathParam("userId") String userId, @PathParam("currentUser") String otherUserId){
        this.createChat("standaard", userId, otherUserId);
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
    @Path("/newChat/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkIfUserExists(@PathParam("userId") String userId){
        int amountOfUsers = Chat.checkIfUserExists(userId);
        boolean result = amountOfUsers > 0;
        JSONObject doesUserExistJSON = new JSONObject();
        doesUserExistJSON.put("result", result);
        return Response.ok().entity(doesUserExistJSON).build();
    }

    @GET
    @Path("/newChat/{userId}/{currentUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStandardChatWithUsers(@PathParam("userId") String userId, @PathParam("currentUser") String otherUserId){
        int amountOfChats = Chat.getStandardChatWithUsers(userId, otherUserId);
        boolean result = amountOfChats > 0;
        JSONObject standardChatsWithUsersJSON = new JSONObject();
        standardChatsWithUsersJSON.put("result", result);
        return Response.ok().entity(standardChatsWithUsersJSON).build();
    }

    @GET
    @Path("/helplineList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdministratorHelplineChats() {
        ArrayList<ChatDTO> helplineChats = Chat.getHelplineChats();
        JSONObject helplineChatsJSON = new JSONObject();
        helplineChatsJSON.put("helplineChats", helplineChats);
        return Response.ok().entity(helplineChatsJSON).build();
    }

    @GET
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChats(@PathParam("userId") String userId) throws SQLException {
        List<ChatDTO> chats = Chat.getChatIdFromUserId(userId);
        JSONObject chatIdsJSON = new JSONObject();
        chatIdsJSON.put("chats", chats);
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
        if (chatList.isEmpty()) { return createNewChat(chatId); }
        for (Chat chat : chatList) {
            if (chat.getChatId().equals(chatId)) {
                return chat;
            }
        }
        return createNewChat(chatId);
    }

    private Chat createNewChat(String chatId) {
        Chat chat = new Chat(chatId);
        chatList.add(chat);
        return chat;
    }

    public void setChats(List<Chat> chats) { this.chatList = chats; }
}
