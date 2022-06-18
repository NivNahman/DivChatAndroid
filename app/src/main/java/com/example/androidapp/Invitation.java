package com.example.androidapp;

public class Invitation {

    private String from;

    private String to;

    private String server;

    public Invitation(String connecteduser, String contactname, String server) {
        this.from = connecteduser;
        this.to = contactname;
        this.server = server;
    }

    public Invitation() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String connecteduser) {
        this.from = connecteduser;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String contactname) {
        this.to = contactname;
    }

    public String getContent() {
        return server;
    }

    public void setContent(String server) {
        this.server = server;
    }
}
