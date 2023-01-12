package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class IChatDAOTest {
    private IChatDAO sut = new IChatDAO() {
        @Override
        public ArrayList<MessageDTO> getChatHistory(String chatId) {
            return null;
        }

        @Override
        public void saveMessage(String message, String senderId, String chatId, String iv) {
        }

        @Override
        public void addUserToChat(String chatId, String userId) {
        }

        @Override
        public MessageDTO formatMessage(String senderId, String content, String time, String iv) {
            return IChatDAO.super.formatMessage(senderId, content, time, iv);
        }

        @Override
        public String addChatToDatabase(String userId, String type) {
            return null;
        }

        @Override
        public ArrayList<String> getUsersInChat(String chatId) {
            return null;
        }

        @Override
        public String getChatType(String chatId) {
            return null;
        }

        @Override
        public ArrayList<String> getChatIdFromUserId(String userId) {
            return null;
        }
    };

    @Test
    void formatMessageCorrectReturnTest() {
        // Arrange
        var senderId = "1";
        var content = "test";
        var time = "00:00";
        var iv = "11111";

        // Act
        var actual = sut.formatMessage(senderId, content, time, iv);

        // Assert
        assertEquals("1", actual.getSenderId());
        assertEquals("test", actual.getMessage());
        assertEquals("00:00", actual.getTime());
    }
}