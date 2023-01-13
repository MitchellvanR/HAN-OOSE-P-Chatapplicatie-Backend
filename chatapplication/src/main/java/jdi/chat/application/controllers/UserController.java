package jdi.chat.application.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.IUserDAO;
import jdi.chat.application.data.SQLUserDAO;
import jdi.chat.application.data.dto.UsersDTO;
import net.minidev.json.JSONObject;

@Path("Users")
public class UserController {
    private final IUserDAO userDAO = new SQLUserDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UsersDTO users = userDAO.getAllUsers();
        JSONObject usersJSON = new JSONObject();
        usersJSON.put("users", users);
        return Response.ok().entity(usersJSON).build();
    }
}
