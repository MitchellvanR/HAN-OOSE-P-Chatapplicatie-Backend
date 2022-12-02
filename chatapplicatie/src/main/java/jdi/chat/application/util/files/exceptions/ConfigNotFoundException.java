package jdi.chat.application.util.files.exceptions;

public class ConfigNotFoundException extends RuntimeException {

    public ConfigNotFoundException() {
        super("Could not find configuration file for application. Make sure this file is located in the resources/configuration directory. Also make sure the file is not empty.");
    }

}
