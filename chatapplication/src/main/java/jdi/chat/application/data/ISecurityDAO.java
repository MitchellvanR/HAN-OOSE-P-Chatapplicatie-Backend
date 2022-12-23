package jdi.chat.application.data;

public interface ISecurityDAO {
    void addUser(String userId);

    void savePublicKey(String userId, String publicKey);

    String getOtherPublicKey(String userId, String chatId);
}
