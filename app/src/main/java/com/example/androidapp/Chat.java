package com.example.androidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Chat_table")
public class Chat {
    @PrimaryKey
    private int id;
    private String name;
//
//    private List<Message> messages;

    public Chat(int id) {
        this.id = id;
//        this.contact = contact;
//        this.messages = messages;
    }

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


