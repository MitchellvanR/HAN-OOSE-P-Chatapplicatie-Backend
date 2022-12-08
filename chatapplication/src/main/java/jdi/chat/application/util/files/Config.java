package jdi.chat.application.util.files;

public class Config extends FileLoader {

    private static final Config instance = new Config();

    private Config() {
        readConfigFile("configuration/config.txt");
    }

    public static Config getInstance() {
        return instance;
    }

    public String getConfiguration(String configKey) { return getValue(configKey); }

}
