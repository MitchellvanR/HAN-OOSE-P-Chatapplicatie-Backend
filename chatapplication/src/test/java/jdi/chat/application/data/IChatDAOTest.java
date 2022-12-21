package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IChatDAOTest {

    private IChatDAO sut = new IChatDAO() {
        @Override
        public ArrayList<MessageDTO> getChatHistory(String chatId) { return null; }

        @Override
        public void saveMessage(String message, String senderId, String chatId) {}

        @Override
        public void addUserToChat(String chatId, String userId) {}
    };

    @Test
    void testFormatMessageCorrectReturn() {
        // Arrange
        var senderId = "1";
        var content = "test";
        var time = "00:00";

        // Act
        var actual = sut.formatMessage(senderId, content, time);

        // Assert
        assertEquals("1", actual.getSenderId());
        assertEquals("test", actual.getMessage());
        assertEquals("00:00", actual.getTime());
    }
}