package jdi.chat.application.util.exceptions;

public class ConfigNotFoundException extends RuntimeException {

    public ConfigNotFoundException() {
        super("Could not find configuration file for application. Make sure this file is found in root project folder");
    }

}
