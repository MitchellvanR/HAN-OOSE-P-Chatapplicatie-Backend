package jdi.chat.application.data;

import jdi.chat.application.data.dto.UserDTO;
import jdi.chat.application.data.dto.UsersDTO;
import java.sql.SQLException;

public interface IUserDAO {
    UsersDTO getAllUsers() throws SQLException;

    UsersDTO getUsersFromChat(String chatId) throws SQLException;

    default UserDTO formatUser(String id) {
        return new UserDTO(id);
    }

}
