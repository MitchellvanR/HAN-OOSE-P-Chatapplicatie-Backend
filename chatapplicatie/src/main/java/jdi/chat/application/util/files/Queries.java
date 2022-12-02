package jdi.chat.application.util.files;

public class Queries extends FileLoader {

    private static final Queries instance = new Queries();

    private Queries() {
        readConfigFile("configuration/queries.txt");
    }

    public static Queries getInstance() {
        return instance;
    }

    public String getQuery(String queryKey) { return getValue(queryKey); }

}
