package jdi.chat.application.util;

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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void mapConfigurationLine(String configLine) {

    }

}
