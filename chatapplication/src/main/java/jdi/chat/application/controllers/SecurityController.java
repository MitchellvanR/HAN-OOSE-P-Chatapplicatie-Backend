package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.AbstractSecurityDAO;
import jdi.chat.application.data.SQLSecurityDAO;
import net.minidev.json.JSONObject;

@Path("/security")
public class SecurityController {
    AbstractSecurityDAO securityDAO = new SQLSecurityDAO();

    @POST
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("userId") String userId){
        securityDAO.addUser(userId);
        return Response.ok().build();
    }

    @POST
    @Path("/{userId}/{publicKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePublicKey(@PathParam("userId") String userId, @PathParam("publicKey") String publicKey){
        securityDAO.savePublicKey(userId,publicKey);
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}/getOtherKey/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOtherPublicKey(@PathParam("userId") String userId, @PathParam("chatId") String chatId){
        String otherPublicKey = securityDAO.getOtherPublicKey(userId, chatId);
        JSONObject publicKeyJSON = new JSONObject();
        publicKeyJSON.put("publicKey", otherPublicKey);
        return Response.ok().entity(publicKeyJSON).build();
    }
}
