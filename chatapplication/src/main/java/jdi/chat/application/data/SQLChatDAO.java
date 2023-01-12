package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

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
    public ArrayList<String> getUsersInChat(String chatId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getUsersInChatQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)){
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            resultSet = statement.executeQuery();
            ArrayList<String> usersInChat = new ArrayList<>();
            while(resultSet.next()) {
               String user = resultSet.getString(1);
               usersInChat.add(user);
            }
            return usersInChat;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    @Override
    public String getChatType(String chatId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatTypeQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)){
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            resultSet = statement.executeQuery();
            String chatType = "";
            while(resultSet.next()) {
                chatType = resultSet.getString(1);
            }
            return chatType;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    @Override
    public ArrayList<ChatDTO> getChatIdFromUserId(String userId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatIdQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            ArrayList<ChatDTO> chats = new ArrayList<>();
            while(resultSet.next()){
                ArrayList<String> users = new ArrayList<>();
                String chat = resultSet.getString("chatId");
                String usersString = resultSet.getString("users");
                String[] usersArray = usersString.split(",");
                for (String user : usersArray){
                    if (!Objects.equals(user, userId)) {
                        users.add(user);
                    }
                }
                chats.add(formatChatList(chat, users));
            }
            return chats;
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    @Override
    public int getStandardChatWithUsers(String userId, String otherUserId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getStandardChatsWithUsersQuery");
        ResultSet resultSet = null;
        int result = 0;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, userId);
            statement.setString(2, otherUserId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result = resultSet.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }
}
