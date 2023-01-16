package jdi.chat.application.data;

import java.sql.SQLException;

public interface ISecurityDAO {
    void addUser(String userId);

    String savePublicKey(String userId, String publicKey) throws SQLException;

    String getOtherPublicKey(String userId, String chatId) throws SQLException;
}
