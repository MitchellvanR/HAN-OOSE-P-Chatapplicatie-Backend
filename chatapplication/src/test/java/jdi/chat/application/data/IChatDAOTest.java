package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
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
        public ChatDTO formatChatDTO(String chatId, List<String> users) {
            return IChatDAO.super.formatChatDTO(chatId, users);
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
        public ArrayList<ChatDTO> getChatDTOFromUserId(String userId) {
            return null;
        }

        @Override
        public int getStandardChatWithUsers(String userId, String otherUserId) {
            return 0;
        }

        @Override
        public int checkIfUserExists(String userId) {
            return 0;
        }

        @Override
        public String getUserHelplineChatId(String userId) {
            return null;
        }

        @Override
        public ArrayList<ChatDTO> getHelplineChats() {
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