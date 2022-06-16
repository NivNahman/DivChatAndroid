package com.example.androidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Message_table")
public class Message {
    @PrimaryKey
    private int id;
    private String content;
    private String created;
    private Boolean sent;
    private String contactID;

    public Message(int id, String content, String created, Boolean sent) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    //  public int getMesid() {
//        return mesid;
//    }
//
//    public void setMesid(int mesid) {
//        this.mesid = mesid;
//    }
    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", sent=" + sent +
                ", contactID='" + contactID + '\'' +
                '}';
    }
}

