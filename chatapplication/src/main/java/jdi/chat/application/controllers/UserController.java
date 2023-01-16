package jdi.chat.application.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.IUserDAO;
import jdi.chat.application.data.SQLUserDAO;
import jdi.chat.application.data.dto.UsersDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import java.sql.SQLException;

@Path("/users")
public class UserController {
    private IUserDAO userDAO = new SQLUserDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UsersDTO users;
        try {
            users = userDAO.getAllUsers();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
        return Response.ok().entity(users).build();
    }

    @GET
    @Path("/{chatId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersFromChat(@PathParam("chatId") String chatId) {
        UsersDTO usersInChat;
        try {
            usersInChat = userDAO.getUsersFromChat(chatId);
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
        return Response.ok().entity(usersInChat).build();
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
