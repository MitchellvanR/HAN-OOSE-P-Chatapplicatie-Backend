package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class SQLAnnouncementDAOTest {
    private SQLAnnouncementDAO sut;
    private Connection mockedConnection;
    private PreparedStatement mockedStatement;
    private Queries queries;
    private String announcement;
    private String endDate;
    private String databaseAnnouncement;
    private ArrayList<String> announcements;

    @BeforeEach
    void setup() {
        sut = new SQLAnnouncementDAO();
        queries = Queries.getInstance();
        announcements = new ArrayList<>();

        announcement = "This is an announcement";
        endDate = "16-02-2024";
        databaseAnnouncement = "This is a database announcement";
        announcements.add(databaseAnnouncement);

        mockedConnection = Mockito.mock(Connection.class);
        mockedStatement = Mockito.mock(PreparedStatement.class);

        SQLConnection.setConnection(mockedConnection);
    }

    @AfterAll
    static void tearDown() {
        SQLConnection.setConnection(null);
    }

    @Test
    void saveAnnouncementSuccessTest() {
        try {
            // Arrange
            Mockito.when(mockedConnection.prepareStatement(queries.getQuery("saveAnnouncementQuery"))).thenReturn(mockedStatement);

            // Act
            sut.saveAnnouncement(announcement, endDate);

            // Assert
            verify(mockedStatement).executeUpdate();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }
    }

    @Test
    void saveAnnouncementSQLExceptionTest() throws SQLException {
        // Arrange
        Mockito.when(mockedConnection.prepareStatement(queries.getQuery("saveAnnouncementQuery"))).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeUpdate()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.saveAnnouncement(announcement, endDate));
    }

    @Test
    void getAnnouncementsSuccessTest() {
        // Arrange
        var expected = announcements;
        var mockedResults = Mockito.mock(ResultSet.class);

        try {
            when(mockedConnection.prepareStatement(queries.getQuery("getAnnouncementsQuery"))).thenReturn(mockedStatement);
            doReturn(mockedResults).when(mockedStatement).executeQuery();

            when(mockedResults.next()).thenReturn(true).thenReturn(false);

            doReturn(databaseAnnouncement).when(mockedResults).getString("announcement");
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }

        // Act
        ArrayList<String> actual = null;
        try {
            actual = sut.getAnnouncements();
        } catch (SQLException e) {
            fail("An exception was thrown in success test case: " + e.getMessage());
        }

        // Assert
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    void getAnnouncementsSQLExceptionTest() throws SQLException {
        //Arrange
        Mockito.when(mockedConnection.prepareStatement(queries.getQuery("getAnnouncementsQuery"))).thenReturn(mockedStatement);
        Mockito.when(mockedStatement.executeQuery()).thenThrow(new SQLException());

        // Assert
        assertThrows(DatabaseRequestException.class, () -> sut.getAnnouncements());
    }
}
