package models;

import network.LiveChatServer;

public class User {
    private String id;
    private boolean online;
    private LiveChatServer server = new LiveChatServer();

    public User(String id) {
        this.id = id;
    }

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
