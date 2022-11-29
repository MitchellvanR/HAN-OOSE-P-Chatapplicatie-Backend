package jdi.chat.application.util;

import jdi.chat.application.util.exceptions.ConfigNotFoundException;

import java.io.*;
import java.util.HashMap;

public class Config {

    private static final Config instance = new Config();

    private HashMap<String, String> config = new HashMap<>();

    private Config() {
        readConfigFile();
    }

    public static Config getInstance() {
        return instance;
    }

    public String getConfiguration(String configurationKey) {
        return config.get(configurationKey);
    }

    private void readConfigFile() {
        InputStream configInputStream = getClass().getClassLoader().getResourceAsStream("configuration/config.txt");
        if (configInputStream == null) { throw new ConfigNotFoundException(); }
        InputStreamReader configReader = new InputStreamReader(configInputStream);
        BufferedReader configBufferedReader = new BufferedReader(configReader);
        configBufferedReader.lines().forEach(this::mapConfigurationLine);
    }

    private void mapConfigurationLine(String configLine) {
        if (!configLine.startsWith("#") && !configLine.isEmpty()) {
            String[] modifiedConfigLine = configLine.split(":");
            config.put(modifiedConfigLine[0].trim(), modifiedConfigLine[1].trim());
        }
    }

}
