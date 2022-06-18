package com.example.androidapp;

public class AddContact {
    private String connecteduser;

    private String id;

    private String name;

    private String server;

    public AddContact(String connecteduser, String id, String name, String server) {
        this.connecteduser = connecteduser;
        this.id = id;
        this.name = name;
        this.server = server;
    }

    public AddContact() {
    }

    public String getFrom() {
        return connecteduser;
    }

    public void setFrom(String connecteduser) {
        this.connecteduser = connecteduser;
    }

    public String getTo() {
        return id;
    }

    public void setTo(String contactname) {
        this.id = contactname;
    }

    public String getContent() {
        return name;
    }

    public void setContent(String content) {
        this.name = content;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
