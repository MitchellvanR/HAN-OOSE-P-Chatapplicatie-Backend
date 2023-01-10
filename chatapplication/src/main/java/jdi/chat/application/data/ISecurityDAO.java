package jdi.chat.application.data;

import java.sql.SQLException;

public interface ISecurityDAO {
    void addUser(String userId);

    void savePublicKey(String userId, String publicKey);

    String getOtherPublicKey(String userId, String chatId) throws SQLException;
}
