package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.ISecurityDAO;
import jdi.chat.application.data.SQLSecurityDAO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import net.minidev.json.JSONObject;
import java.sql.SQLException;

@Path("/security")
public class SecurityController {
    ISecurityDAO securityDAO = new SQLSecurityDAO();

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePublicKey(@PathParam("userId") String userId, @PathParam("publicKey") String publicKey) {
        String oldKey;
        try {
            oldKey = securityDAO.savePublicKey(userId, publicKey);
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
        Boolean keysMatch = (oldKey == null || oldKey.equals(publicKey));
        JSONObject oldKeyJSON = new JSONObject();
        oldKeyJSON.put("keysMatch", keysMatch);
        return Response.ok().entity(oldKeyJSON).build();
    }

    @GET
    @Path("/{userId}/getOtherKey/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOtherPublicKey(@PathParam("userId") String userId, @PathParam("chatId") String chatId){
        String otherPublicKey;
        try {
            otherPublicKey = securityDAO.getOtherPublicKey(userId, chatId);
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
        JSONObject publicKeyJSON = new JSONObject();
        publicKeyJSON.put("publicKey", otherPublicKey);
        return Response.ok().entity(publicKeyJSON).build();
    }
}
