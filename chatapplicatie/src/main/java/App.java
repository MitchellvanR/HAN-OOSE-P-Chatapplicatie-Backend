import data.AbstractChatDAO;
import data.SQLChatDAO;

public class App {
    public static void main(String[] args) {
        AbstractChatDAO dao = new SQLChatDAO();
        dao.saveMessage("TestSender", "testReceiver", "TestMessage");
    }
}
