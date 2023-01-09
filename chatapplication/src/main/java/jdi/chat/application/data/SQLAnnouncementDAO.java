package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLAnnouncementDAO implements IAnnouncementDAO{
    @Override
    public void saveAnnouncement(String announcement, String endDate) {
        String sql = Queries.getInstance().getQuery("saveAnnouncementQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            statement.setString(1, announcement);
            statement.setString(2, endDate);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public ArrayList<String> getAnnouncements() throws SQLException {
        String sql = Queries.getInstance().getQuery("getAnnouncementsQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            resultSet = statement.executeQuery();
            ArrayList<String> announcements = new ArrayList<>();
            while (resultSet.next()){
                announcements.add(resultSet.getString("announcement"));
            }
            return announcements;
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }
}
