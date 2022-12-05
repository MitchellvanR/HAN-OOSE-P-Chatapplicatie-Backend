package jdi.chat.application.util.files;

import jdi.chat.application.util.files.exceptions.ConfigNotFoundException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileLoader {

    private HashMap<String, String> configurations = new HashMap<>();

    public String getValue(String key) {
        return configurations.get(key);
    }

    protected void readConfigFile(String filepath) {
        InputStream configInputStream = getClass().getClassLoader().getResourceAsStream(filepath);
        if (configInputStream == null) { throw new ConfigNotFoundException(); }
        InputStreamReader configReader = new InputStreamReader(configInputStream);
        BufferedReader configBufferedReader = new BufferedReader(configReader);
        configBufferedReader.lines().forEach(this::mapConfigurationLine);
    }

    protected void mapConfigurationLine(String configLine) {
        if (!configLine.startsWith("#") && !configLine.isEmpty()) {
            String[] modifiedConfigLine = configLine.split("_:");
            configurations.put(modifiedConfigLine[0].trim(), modifiedConfigLine[1].trim());
        }
    }

}
