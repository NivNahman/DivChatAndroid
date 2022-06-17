package com.example.androidapp;

public class Addmsg {

    private String connecteduser;

    private String contactname;

    private String content;

    public Addmsg(String connecteduser, String contactname, String content) {
        this.connecteduser = connecteduser;
        this.contactname = contactname;
        this.content = content;
    }

    public Addmsg() {
    }

    public String getConnecteduser() {
        return connecteduser;
    }

    public void setConnecteduser(String connecteduser) {
        this.connecteduser = connecteduser;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
