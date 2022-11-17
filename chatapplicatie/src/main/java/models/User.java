package models;

import network.LiveChatServer;

public class User {
    private String id;
    private boolean online;
    private LiveChatServer server = new LiveChatServer();

    public void connect(){
        // code
    }

    public void sendMessage(String message){
        // code
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
