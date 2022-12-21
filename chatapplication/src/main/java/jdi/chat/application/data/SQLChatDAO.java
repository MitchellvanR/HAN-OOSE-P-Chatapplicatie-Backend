package jdi.chat.application.data;

import jakarta.inject.Inject;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLChatDAO implements IChatDAO {

    @Inject
    private ConnectionDAO connectionDAO;

    @Override
    public ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException {
        String sql = Queries.getInstance().getQuery("getChatHistoryQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = connectionDAO.getConnection().prepareStatement(sql)) {
            statement.setString(1, chatId);
            resultSet = statement.executeQuery();
            ArrayList<MessageDTO> chatHistory = new ArrayList<>();
            while (resultSet.next()) {
                chatHistory.add(formatMessage(
                    resultSet.getString("senderId"),
                    resultSet.getString("message"),
                    resultSet.getString("time")
                ));
            }
            return chatHistory;
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        } finally {
            assert resultSet != null;
            resultSet.close();
        }
    }

    @Override
    public void saveMessage(String message, String senderId, String chatId){
        String sql = Queries.getInstance().getQuery("sendMessageQuery");
        try (PreparedStatement statement = connectionDAO.getConnection().prepareStatement(sql)) {
            statement.setString(1, message);
            statement.setString(2, senderId);
            statement.setString(3, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public void addUserToChat(String chatId, String userId) {
        String sql = Queries.getInstance().getQuery("addUserToChatQuery");
        try (PreparedStatement statement = connectionDAO.getConnection().prepareStatement(sql);) {
            statement.setString(1, userId);
            statement.setString(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    public void setConnectionDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

}
