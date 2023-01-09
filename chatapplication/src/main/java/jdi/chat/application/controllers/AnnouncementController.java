package jdi.chat.application.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdi.chat.application.data.IAnnouncementDAO;
import jdi.chat.application.data.SQLAnnouncementDAO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import net.minidev.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

@Path ("/announcement")
public class AnnouncementController {
    IAnnouncementDAO announcementDAO = new SQLAnnouncementDAO();
    @POST
    @Path("/{message}/{endDate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveAnnouncement (@PathParam("message") String message, @PathParam("endDate") String endDate){
        announcementDAO.saveAnnouncement(message,endDate);
        return Response.ok().build();
    }

    @GET
    @Path("/getAnnouncements")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnnouncements () {
        try{
            ArrayList<String> announcements = announcementDAO.getAnnouncements();
            JSONObject announcementJSON = new JSONObject();
            announcementJSON.put("announcements", announcements);
            return Response.ok().entity(announcementJSON).build();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }
}
