package jdi.chat.application.controllers;

import jdi.chat.application.data.IAnnouncementDAO;
import jdi.chat.application.data.SQLAnnouncementDAO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnouncementControllerTest {
    private AnnouncementController sut;
    private String announcement;
    private String endDate;
    private IAnnouncementDAO mockedAnnouncementDAO;
    private ArrayList<String> announcements;

    @BeforeEach
    void setup(){
        sut = new AnnouncementController();
        announcement = "This test will pass.";
        endDate = "16-02-2024";
        mockedAnnouncementDAO = Mockito.mock(SQLAnnouncementDAO.class);
        sut.announcementDAO = mockedAnnouncementDAO;
        announcements = new ArrayList<>();
        announcements.add("This is an announcement from the database!");
    }

    @Test
    void saveAnnouncementSuccessTest(){
        // Arrange
        Mockito.doNothing().when(mockedAnnouncementDAO).saveAnnouncement(announcement, endDate);

        // Act
        sut.saveAnnouncement(announcement, endDate);

        // Assert
        Mockito.verify(mockedAnnouncementDAO).saveAnnouncement(announcement, endDate);
    }

    @Test
    void getAnnouncementsSuccessTest() throws SQLException {
        // Arrange
        Mockito.doReturn(announcements).when(mockedAnnouncementDAO).getAnnouncements();

        // Act
        sut.getAnnouncements();

        // Assert
        Mockito.verify(mockedAnnouncementDAO).getAnnouncements();
    }

    @Test
    void getAnnouncementsSQLExceptionTest() throws SQLException {
        // Arrange
        Mockito.when(mockedAnnouncementDAO.getAnnouncements()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () ->sut.getAnnouncements());
    }
}
