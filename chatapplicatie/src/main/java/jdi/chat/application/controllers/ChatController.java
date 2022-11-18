package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.models.Chat;

import java.util.ArrayList;

@Path("/chats")
public class ChatController {
    private ArrayList<Chat> chats;
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatHistory(@QueryParam("senderId") String senderId, @QueryParam("receiverId") String receiverId){
        return Response.ok().entity("""             
                    {
                        "messages": [{
                            "senderId": "1",
                            "receiverId": "2",
                            "message": "Hello"
                        }, {
                            "senderId": "2",
                            "receiverId": "1",
                            "message": "Also hello"
                        }]
                    }
                """).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@QueryParam("senderId") String senderId, @QueryParam("receiverId") String receiverId, @QueryParam("message") String message){
//        chats.get(0).sendMessage(message);
        return Response.accepted().build();
    }

//    @Inject
//    public void setChats(ArrayList<Chat> chats) { this.chats = chats; }
}
