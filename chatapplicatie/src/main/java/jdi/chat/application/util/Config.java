package jdi.chat.application.util;

import jdi.chat.application.util.exceptions.ConfigNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

    private static final Config instance = new Config();

    private HashMap<String, String> config;

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
        try {
            File configFile = new File("../../../config.txt");
            Scanner configScanner = new Scanner(configFile);
            while (configScanner.hasNextLine()) {
                String configLine = configScanner.nextLine();
                mapConfigurationLine(configLine);
            }
            configScanner.close();
        } catch (FileNotFoundException e) {
            throw new ConfigNotFoundException();
        }
    }

    private void mapConfigurationLine(String configLine) {
        if (!configLine.startsWith("#") && !configLine.isEmpty()) {
            String[] modifiedConfigLine = configLine.split(":");
            config.put(modifiedConfigLine[0].trim(), modifiedConfigLine[1].trim());
        }
    }

}
