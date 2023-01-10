package jdi.chat.application.data;

import jdi.chat.application.data.dto.ChatDTO;
import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

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
    public ArrayList<String> getChatIdFromUserId(String userId) {
        try {
            String sql = Queries.getInstance().getQuery("getChatId");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> chatIds = new ArrayList<>();
            while(resultSet.next()){
                String chatId = resultSet.getString("chatId");
                chatIds.add(chatId);
            }
            return chatIds;
        } catch (Exception e) {
            throw new DatabaseRequestException();
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