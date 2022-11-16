import data.SQLChatDAO;

public class App {
    public static void main(String[] args) {
        SQLChatDAO dao = new SQLChatDAO();
        dao.saveMessage("TestSender", "testReceiver", "TestMessage");
    }
}
