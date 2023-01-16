package jdi.chat.application.data;

import jdi.chat.application.data.dto.UserDTO;
import jdi.chat.application.data.dto.UsersDTO;
import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLUserDAO implements IUserDAO {
    @Override
    public UsersDTO getAllUsers() throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getUsersQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            resultSet = statement.executeQuery();
            ArrayList<UserDTO> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(formatUser(resultSet.getString("id")));
            }
            return new UsersDTO(users);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    @Override
    public UsersDTO getUsersFromChat(String chatId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getUsersInChatQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            if (statement == null) { throw new DatabaseRequestException(); }
            statement.setString(1, chatId);
            resultSet = statement.executeQuery();
            ArrayList<UserDTO> usersInChat = new ArrayList<>();
            while (resultSet.next()) {
                usersInChat.add(formatUser(resultSet.getString("userId")));
            }
            return new UsersDTO(usersInChat);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
