package jdi.chat.application.data;

public abstract class AbstractSecurityDAO {
    public abstract void addUser(String userId);

    public abstract void savePublicKey(String userId, String publicKey);

    public abstract String getOtherPublicKey(String userId, String chatId);
}
