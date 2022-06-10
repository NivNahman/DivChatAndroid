package com.example.androidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Message_table")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int Mesid;
    private int id;
    private String content;
    private String created;
    private Boolean sent;

    public Message(String content, String created, Boolean sent) {
        this.content = content;
        this.created = created;
        this.sent = sent;
    }
  public int getMesid() {
        return mesid;
    }

    public void setMesid(int mesid) {
        this.mesid = mesid;
    }
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
}

