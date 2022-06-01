package com.example.androidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Message_table")
public class Message {
@PrimaryKey
    private int mesid;
    private int id;
    private String content;
    private String created;
    private Boolean sent;

    public Message(int id, String content, String created, Boolean sent) {
        this.mesid = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }
//lets try to push this comment
    public Message() {
    }

    public int getId() {
        return mesid;
    }

    public void setId(int id) {
        this.mesid = id;
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
}

