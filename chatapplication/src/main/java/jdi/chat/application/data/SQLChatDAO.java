package jdi.chat.application.data;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class SQLChatDAO implements IChatDAO, IConnectionDAO {
    private static Connection connection;

    @Override
    public ArrayList<MessageDTO> getChatHistory(String chatId) throws SQLException {
        connectToDatabase();
        String sql = Queries.getInstance().getQuery("getChatHistoryQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
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
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Override
    public void saveMessage(String message, String senderId, String chatId){
        connectToDatabase();
        String sql = Queries.getInstance().getQuery("sendMessageQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
        connectToDatabase();
        String sql = Queries.getInstance().getQuery("addUserToChatQuery");
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, userId);
            statement.setString(2, chatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            String url = properties.getProperty("connectionString");
            return DriverManager.getConnection(url);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseRequestException(e);
        }

    }

    public static void setConnection(Connection connection) {
        SQLChatDAO.connection = connection;
    }

    private void connectToDatabase() {
        if (SQLChatDAO.connection == null) {
            SQLChatDAO.connection = createConnection();
        }
    }
}
