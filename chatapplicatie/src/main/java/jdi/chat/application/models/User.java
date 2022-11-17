package jdi.chat.application.models;

import jdi.chat.application.network.LiveChatServer;

public class User {
    private String id;
    private boolean online;
    private LiveChatServer server = new LiveChatServer();

    public void connect(){
        // code
    }

    public void sendMessage(String message){
        server.sendMessage(message);
    }

    public String getId() {
        return id;
    }

    public boolean getOnline(){
        return online;
    }
}
