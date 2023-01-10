package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import java.sql.*;
import java.util.ArrayList;

public class SQLChatDAO implements IChatDAO {
    @Override
    public ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatHistoryQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            resultSet = statement.executeQuery();

            ArrayList<MessageDTO> chatHistory = new ArrayList<>();
            while (resultSet.next()) {
                chatHistory.add(formatMessage(
                        resultSet.getString("senderId"),
                        resultSet.getString("message"),
                        resultSet.getString("time"),
                        resultSet.getString("iv")
                ));
            }
            return chatHistory;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Override
    public void saveMessage(String message, String senderId, String chatId, String iv){
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("sendMessageQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)){
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, message);
            statement.setString(2, senderId);
            statement.setString(3, chatId);
            statement.setString(4, iv);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public void addUserToChat(String chatId, String userId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("addUserToChatQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, userId);
            statement.setString(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }

    }

    @Override
    public String addChatToDatabase(String userId, String type){
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("createChatQuery");
        try (CallableStatement statement = SQLConnection.connection.prepareCall(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, userId);
            statement.setString(2, type);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public ArrayList<String> getUsersInChat(String chatId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getUsersInChatQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)){
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> usersInChat = new ArrayList<>();
            while(resultSet.next()) {
               String user = resultSet.getString(1);
               usersInChat.add(user);
            }
            return usersInChat;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public String getChatType(String chatId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatTypeQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)){
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            String chatType = "";
            while(resultSet.next()) {
                chatType = resultSet.getString(1);
            }
            return chatType;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }
    @Override
    public ArrayList<String> getChatIdFromUserId(String userId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatIdQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> chatIds = new ArrayList<>();
            while(resultSet.next()){
                String chatId = resultSet.getString("chatId");
                chatIds.add(chatId);
            }
            return chatIds;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }
    @Override
    public String getUserHelplineChatId(String userId) {
        try {
            String sql = Queries.getInstance().getQuery("getUserHelplineChatId");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            String chatId = "";
            while(resultSet.next()){
                chatId = resultSet.getString("id");
            }

            return chatId;
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public ArrayList<ChatDTO> getHelplineChats() {
        try {
            String sql = Queries.getInstance().getQuery("getHelplineChats");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<ChatDTO> helplineChats = new ArrayList<>();
            while(resultSet.next()){
                helplineChats.add(formatChat(
                        resultSet.getString("chatId"),
                        resultSet.getString("latest message")
                ));
            }
            return helplineChats;
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }
}