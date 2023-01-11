package jdi.chat.application.data;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAnnouncementDAO {
    void saveAnnouncement(String announcement, String endDate);

    ArrayList<String> getAnnouncements() throws SQLException;
}
