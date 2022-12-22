package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQLChatDAO extends AbstractChatDAO {
    @Override
    public ArrayList<MessageDTO> getChatHistory(String chatId) {
        try {
            String sql = Queries.getInstance().getQuery("getChatHistoryQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<MessageDTO> chatHistory = new ArrayList<>();
            while(resultSet.next()) {
                MessageDTO message = formatMessage(
                        resultSet.getString("senderId"),
                        resultSet.getString("message"),
                        resultSet.getString("time")
                );
                chatHistory.add(message);
            }
            return chatHistory;
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public void saveMessage(String message, String senderId, String chatId){
        try {
            String sql = Queries.getInstance().getQuery("sendMessageQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, message);
            statement.setString(2, senderId);
            statement.setString(3, chatId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public void addUserToChat(String chatId, String userId) {
        try {
            String sql = Queries.getInstance().getQuery("addUserToChatQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, chatId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }

    }

    @Override
    public String addChatToDatabase(String userId, String type){
        try {
            String sql = Queries.getInstance().getQuery("createChatQuery");
            CallableStatement statement = ConnectionDAO.getInstance().getConnection().prepareCall(sql);
            statement.setString(1, userId);
            statement.setString(2, type);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public ArrayList<String> getUsersInChat(String chatId) {
        try {
            String sql = Queries.getInstance().getQuery("getUsersInChat");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, chatId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> usersInChat = new ArrayList<>();
            while(resultSet.next()) {
               String user = resultSet.getString(1);
               usersInChat.add(user);
            }
            return usersInChat;
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }
}
