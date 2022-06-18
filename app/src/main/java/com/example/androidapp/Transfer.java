package com.example.androidapp;

public class Transfer {

    private String from;

    private String to;

    private String content;

    public Transfer(String connecteduser, String contactname, String content) {
        this.from = connecteduser;
        this.to = contactname;
        this.content = content;
    }

    public Transfer() {
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
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
